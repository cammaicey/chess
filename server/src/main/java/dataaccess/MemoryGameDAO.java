package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO {
    final private HashSet<GameData> games = new HashSet<>();

    public void createGame(GameData game) {
        games.add(game);
    }

    public GameData getGame(int gameId) {
        for (GameData game : games) {
            if (game.gameID() == gameId) {
                return game;
            }
        }
        return null;
    }

    public Collection<GameData> listGames() {
        return games;
    }

    public void updateGame(String playerColor, int gameID) {
        
    }

    public void deleteAllGames() {
        games.clear();
    }
}
