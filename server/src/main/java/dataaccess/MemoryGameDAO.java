package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {
    final private HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame(String gameName) {
        return null;
    }

    public GameData getGame(int gameId) {
        return null;
    }

    public Collection<GameData> listGames() {
        return null;
    }

    public void updateGame(String playerColor, int gameID) {}

    public void deleteAllGames() {}
}
