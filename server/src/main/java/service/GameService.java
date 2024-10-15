package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.GameData;

import java.util.Collection;

public class GameService {

    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public GameData getGame() {
        return null;
    }
    public Collection<GameData> listGames() {
        return java.util.List.of();
    }
    public void updateGame(GameData game) {}
    public GameData clear(){
        return null;
    }
}
