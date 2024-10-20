package server;

import service.GameService;
import spark.Request;
import spark.Response;

public class GameHandler {

    GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object listgames(Request req, Response res) {
        return null;
    }

    public Object creategame(Request req, Response res) {
        return null;
    }

    public Object joingame(Request req, Response res) {
        return null;
    }
}
