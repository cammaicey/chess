package dataaccess;

import exception.ResponseException;
import com.google.gson.Gson;
import model.UserData;

import java.sql.SQLException;

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

    private int executeUpdate(String statement, Object...params) throws ResponseException {
        return 0;
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
