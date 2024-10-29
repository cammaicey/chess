package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws ResponseException, SQLException, DataAccessException {
        configDatabase();
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        var statement = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            int gameID = generateRandomNumber(1, 9999);
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                ps.setString(2, null);
                ps.setString(3, null);
                ps.setString(4, gameName);
                ps.setString(5, serializeGame(new ChessGame()));
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
    public GameData getGame(int gameID) throws DataAccessException {
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM user WHERE gameID=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    rs.next();
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameName = rs.getString("gameName");
                    ChessGame game = deserializeGame(rs.getString("game"));
                    return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
                }
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException, ResponseException {
        var result = new HashSet<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM user WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var game = deserializeGame(rs.getString("game"));
        return new GameData(id, whiteUsername, blackUsername, gameName, game);
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

    private ChessGame deserializeGame(String json) {
        return new Gson().fromJson(json, ChessGame.class);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS gameData (
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