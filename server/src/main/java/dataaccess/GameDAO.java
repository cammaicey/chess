package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    GameData createGame(String gameName) throws DataAccessException;

    GameData getGame(int gameId) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(String playerColor, int gameID) throws DataAccessException;

    void deleteGame(GameData gameData) throws DataAccessException;
}
