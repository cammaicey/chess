package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import exception.ResponseException;
import model.GameData;

import java.util.Collection;

public class GameService {

    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public GameData getGame() throws ResponseException {
        return null;
    }
    public Collection<GameData> listGames() throws ResponseException {
        return java.util.List.of();
    }
    public void updateGame(GameData game) throws ResponseException {}

    public void clearGames() throws ResponseException, DataAccessException {
        gameDAO.deleteAllGames();
    }
}
