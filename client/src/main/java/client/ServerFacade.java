package client;

import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;
import model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private Communicator communicator;

    public ServerFacade(String serverURL) {
        communicator = new Communicator(this, serverURL);
    }


    public Object register(UserData user) throws ResponseException {
        return communicator.register(user);
    }

    public Object login(UserData user) throws ResponseException {
        return communicator.login(user);
    }

    public void logout(UserData user) throws ResponseException {
        communicator.logout(user);
    }

//    public Object listgames(GameData game) throws ResponseException {
//        var path = "/game";
//        return this.makeRequest("GET", path, game, GameData.class);
//    }
//
//    public Object creategame(GameData game) throws ResponseException {
//        var path = "/game";
//        return this.makeRequest("POST", path, game, GameData.class);
//    }
//
//    public Object joingame(GameData game) throws ResponseException {
//        var path = "/game";
//        return this.makeRequest("POST", path, game, GameData.class);
//    }
}
