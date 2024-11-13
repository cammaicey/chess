package client;

import com.google.gson.Gson;
import exception.ResponseException;
import model.JoinData;
import model.ListData;
import model.UserData;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServerFacade {
    private Communicator communicator;
    String auth;

    public ServerFacade(String serverURL) {
        communicator = new Communicator(this, serverURL);
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }

    public void register(UserData user) throws ResponseException {
        var path = "/user";
        communicator.register("POST", path, user);
    }

    public void login(UserData user) throws ResponseException, URISyntaxException, IOException {
        var path = "/session";
        communicator.login("POST", path, user);
    }

    public void logout() throws ResponseException {
        var path = "/session";
        communicator.logout("DELETE", path);
    }

    public ListData listgames() throws ResponseException {
        var path = "/game";
        return communicator.listgames("GET", path);
    }

    public void creategame(String name) throws ResponseException {
        var path = "/game";
        communicator.creategame("POST", path, name);
    }

    public void joingame(JoinData join) throws ResponseException {
        var path = "/game";
        communicator.joingame("PUT", path, join);
    }
}
