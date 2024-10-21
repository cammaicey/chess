package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class MemoryGameDAO implements GameDAO {
    final private HashSet<GameData> games = new HashSet<>();

    public int createGame(String gameName) {
        int min = 1;
        int max = 9999;
        int id = generateRandomNumber(min, max);
        while (getGame(id) != null) {
            id = generateRandomNumber(min, max);
        }
        GameData game = new GameData(id, null, null, gameName, new ChessGame());
        games.add(game);
        return id;
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min; // Generates a random number between min and max
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
