package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    int createGame(String gameName) throws DataAccessException;

    GameData getGame(int gameId) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(String playerColor, int gameID, String auth) throws DataAccessException;

    void deleteAllGames() throws DataAccessException;
}
