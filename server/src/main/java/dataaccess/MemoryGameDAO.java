package dataaccess;

import model.GameData;

import java.util.Collection;

public class MemoryGameDAO implements GameDAO {
    public GameData createGame(String gameName) {}

    public GameData getGame(int gameId) {}

    public Collection<GameData> listGames() {}

    public void updateGame(String playerColor, int gameID) {}

    public void deleteGame(GameData gameData) {}
}
