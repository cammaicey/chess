package dataaccess;

import exception.ResponseException;
import com.google.gson.Gson;
import model.UserData;

import java.sql.SQLException;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLUserDAO implements UserDAO {

    public MySQLUserDAO() throws ResponseException, SQLException, DataAccessException {
        configDatabase();
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAllUsers() throws DataAccessException {

    }

    private int executeUpdate(String statement, Object...params) throws ResponseException, DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof UserData p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            } catch (SQLException e) {
                throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
            }
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS user (
             username varchar(255) NOT NULL,
             password varchar(255) NOT NULL,
             email varchar(255),
             PRIMARY KEY (username),
            )
            """
    };

    private void configDatabase() throws ResponseException, DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}
