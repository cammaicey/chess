package server;

import exception.ResponseException;
import service.UserService;
import model.UserData;
import spark.*;
import com.google.gson.Gson;

public class UserHandler {
    UserService userService;

    public Object register(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        var user = gson.fromJson(req.body(), UserData.class);
        userService.register(user);
        res.status(200);
        /*
        [400] { "message": "Error: bad request" }
        [403] { "message": "Error: already taken" }
        [500] { "message": "Error: (description of error)" }
        */
        return new Gson().toJson(user);
    }

    public Object login(Request req, Response res) throws ResponseException {
        return null;
    }

    public Object logout(Request req, Response res) throws ResponseException {
        return null;
    }
}
