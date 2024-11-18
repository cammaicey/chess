package client;

import exception.ResponseException;
import model.JoinData;
import model.ListData;
import model.UserData;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServerFacade {
    private HttpCommunicator httpCommunicator;
    String auth;
    int gameID;

    public ServerFacade(String serverURL) {
        httpCommunicator = new HttpCommunicator(this, serverURL);
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public void register(UserData user) throws ResponseException, IOException {
        var path = "/user";
        httpCommunicator.register("POST", path, user);
    }

    public void login(UserData user) throws ResponseException, URISyntaxException, IOException {
        var path = "/session";
        httpCommunicator.login("POST", path, user);
    }

    public void logout() throws ResponseException, IOException {
        var path = "/session";
        httpCommunicator.logout("DELETE", path);
    }

    public ListData listgames() throws ResponseException, IOException {
        var path = "/game";
        return httpCommunicator.listgames("GET", path);
    }

    public int creategame(String name) throws ResponseException, IOException {
        var path = "/game";
        httpCommunicator.creategame("POST", path, name);
        return gameID;
    }

    public void joingame(JoinData join) throws ResponseException, IOException {
        var path = "/game";
        httpCommunicator.joingame("PUT", path, join);
    }
}
