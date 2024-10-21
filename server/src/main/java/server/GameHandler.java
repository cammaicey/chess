package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exception.ResponseException;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static server.UserHandler.convertExceptionToJson;

public class GameHandler {

    GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object listgames(Request req, Response res) throws ResponseException, DataAccessException {
        String token = req.headers("authorization");
        Collection<GameData> games;
        try {
           games = gameService.listgames(token);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return convertExceptionToJson(e);
        }
        res.status(200);
        if (games.isEmpty()) {
            return "{}";
        }
        return new Gson().toJson(games);
    }

    public Object creategame(Request req, Response res) throws DataAccessException {
        var game = new Gson().fromJson(req.body(), GameData.class);
        String token = req.headers("authorization");
        int gameID;
        try {
            gameID = gameService.creategame(token, game.gameName());
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return convertExceptionToJson(e);
        }
        res.status(200);
        Map<String, Integer> response = new HashMap<>();
        response.put("gameID", gameID);
        return new Gson().toJson(response);
    }

    public Object joingame(Request req, Response res) {
        return null;
    }
}
