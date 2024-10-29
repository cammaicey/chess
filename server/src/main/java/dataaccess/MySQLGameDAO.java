package dataaccess;

import exception.ResponseException;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws ResponseException, SQLException, DataAccessException {
        configDatabase();
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gameId) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void updateGame(String playerColor, int gameID, String auth) throws DataAccessException {

    }

    @Override
    public void deleteAllGames() throws DataAccessException, ResponseException, SQLException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }

    private void executeUpdate(String statement, Object...params) throws ResponseException, DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                String sql = statement.trim();
                if (sql.toUpperCase().startsWith("INSERT")) {

                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
             gameID INT NOT NULL,
             whiteUsername varchar(255),
             blackUsername varchar(255),
             gameName varchar(255),
             chessGame TEXT,
             PRIMARY KEY (gameID)
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
