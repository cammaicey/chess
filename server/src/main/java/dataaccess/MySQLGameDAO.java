package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws ResponseException, SQLException, DataAccessException {
        configDatabase();
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        var statement = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        var statmentTwo = "INSERT INTO game (gameID, game) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            int gameID = generateRandomNumber(1, 9999);
            try (var ps = conn.prepareStatement(statement)) {
                var ps2 = conn.prepareStatement(statmentTwo);
                ps.setInt(1, gameID);
                ps.setString(2, null);
                ps.setString(3, null);
                ps.setString(4, gameName);
                ps2.setInt(1, gameID);
                ps2.setString(2, serializeGame(new ChessGame()));
                return gameID;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min; // Generates a random number between min and max
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
    public void deleteAllGames() throws DataAccessException {
    }

    private String serializeGame(ChessGame game) {
        var serializer = new Gson();
        return serializer.toJson(game);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS gameData (
             gameID INT NOT NULL,
             whiteUsername varchar(255),
             blackUsername varchar(255),
             gameName varchar(255),
             PRIMARY KEY (gameID)
            )
            """,
            """
             CREATE TABLE IF NOT EXISTS game (
             gameID INT NOT NULL,
             chessGame TEXT,
             FOREIGN KEY (gameID) REFERENCES gameData(gameID)
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