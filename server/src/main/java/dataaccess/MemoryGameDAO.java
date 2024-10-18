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

    public void updateGame(GameData game) {
        for (GameData g : games) {
            if (g.gameID() == game.gameID()) {
                games.remove(g);
                games.add(game);
            }
        }
    }

    public void deleteGame(GameData game) {
        games.remove(game);
    }

    public void deleteAllGames() {
        games.clear();
    }
}
