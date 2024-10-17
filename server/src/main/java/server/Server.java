package server;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import exception.ResponseException;
import service.GameService;
import service.UserService;
import spark.*;
import spark.route.Routes;

public class Server {
    UserService userService;
    GameService gameService;
    AuthDAO authDAO;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clearAll);
        /*
        Spark.post("/user", this::createUser);
        Spark.post("/session", this::loginUser);
        Spark.delete("/session", this::logoutUser);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        */
        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }

    public Object clearAll(Request req, Response res) throws ResponseException, DataAccessException {
        userService.clearUsers();
        gameService.clearGames();
        authDAO.deleteAllAuths();
        res.status(200);
        return "";
    }
}
