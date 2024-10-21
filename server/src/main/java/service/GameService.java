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

    public Collection<GameData> listgames(String auth) throws ResponseException, DataAccessException {
        if (authDAO.getAuth(auth) == null) {
            DataAccessException e = new DataAccessException("Error: unauthorized");
            ResponseException r = new ResponseException(401, e.getMessage());
            throw r;
        }
        return gameDAO.listGames();
    }

    public int creategame(String auth, String gameName) throws ResponseException, DataAccessException {
        if (authDAO.getAuth(auth) == null) {
            DataAccessException e = new DataAccessException("Error: unauthorized");
            ResponseException r = new ResponseException(401, e.getMessage());
            throw r;
        }
        else if (gameName == null) {
            DataAccessException e = new DataAccessException("Error: bad request");
            ResponseException r = new ResponseException(400, e.getMessage());
            throw r;
        }
        return gameDAO.createGame(gameName);
    }

    public void joinGame(GameData game) throws ResponseException {}

    public void clearGames() throws ResponseException, DataAccessException {
        gameDAO.deleteAllGames();
    }
}
