package server;

import dataaccess.DataAccessException;
import exception.ResponseException;
import model.AuthData;
import service.UserService;
import model.UserData;
import spark.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Object register(Request req, Response res) throws ResponseException, DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        AuthData authData;
        try {
            authData = userService.register(user);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return convertExceptionToJson(e);
        }
        res.status(200);
        return new Gson().toJson(authData);
    }

    public Object login(Request req, Response res) throws ResponseException, DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        AuthData authData;
        try {
            authData = userService.login(user);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            return convertExceptionToJson(e);
        }
        res.status(200);
        return new Gson().toJson(authData);
    }

    public Object logout(Request req, Response res) throws ResponseException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        /*
        [200] {}
        [401] { "message": "Error: unauthorized" }
        [500] { "message": "Error: (description of error)" }
         */
        return new Gson().toJson(user);
    }

    public static String convertExceptionToJson(Exception e) {
        // Create a map to hold exception details
        Map<String, String> exceptionDetails = new HashMap<>();
        exceptionDetails.put("message", e.getMessage());
        exceptionDetails.put("cause", (e.getCause() != null) ? e.getCause().toString() : "null");
        exceptionDetails.put("class", e.getClass().getName());

        // Convert the map to JSON using Gson
        Gson gson = new Gson();
        return gson.toJson(exceptionDetails);
    }
}
