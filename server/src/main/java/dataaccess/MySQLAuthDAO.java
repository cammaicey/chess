package dataaccess;

import exception.ResponseException;
import model.AuthData;

import java.sql.SQLException;

public class MySQLAuthDAO implements AuthDAO {

    public MySQLAuthDAO() throws ResponseException, SQLException, DataAccessException {
        configDatabase();
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        return "";
    }

    @Override
    public void deleteAuth(AuthData authData) throws DataAccessException {

    }

    @Override
    public void deleteAllAuths() throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auth (
             'authToken' varchar(255) NOT NULL,
             'username' varchar(255) NOT NULL,
             PRIMARY KEY ('authToken')
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
