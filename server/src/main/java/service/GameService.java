package service;

import dataaccess.DataAccess;
import model.GameData;

import java.util.Collection;

public class GameService {

    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
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
