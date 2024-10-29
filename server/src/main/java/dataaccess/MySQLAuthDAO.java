package dataaccess;

import exception.ResponseException;
import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class MySQLAuthDAO implements AuthDAO {

    public MySQLAuthDAO() throws ResponseException, SQLException, DataAccessException {
        configDatabase();
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException, ResponseException, SQLException {
        var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        executeUpdate(statement, authData.authToken(), authData.username());
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        var statement = "SELECT authToken, username FROM auth WHERE authToken = ?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    rs.next();
                    String username = rs.getString("username");
                    return new AuthData(authToken, username);
                }
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        return "";
    }

    @Override
    public void deleteAuth(AuthData authData) throws DataAccessException {

    }

    @Override
    public void deleteAllAuths() throws DataAccessException, ResponseException, SQLException {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
    }

    private void executeUpdate(String statement, Object...params) throws ResponseException, DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                String sql = statement.trim();
                if (sql.toUpperCase().startsWith("INSERT")) {
                    ps.setString(1, params[0].toString());
                    ps.setString(2, params[1].toString());
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auth (
             authToken varchar(255) NOT NULL,
             username varchar(255) NOT NULL,
             PRIMARY KEY (authToken)
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
