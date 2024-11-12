package client;

import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;
import model.UserData;

public class ServerFacade {
    private Communicator communicator;
    private String authToken;

    public ServerFacade(String serverURL) {
        communicator = new Communicator(this, serverURL);
    }

    void setAuth(String authToken) {
        this.authToken = authToken;
    }

    String getAuth() {
        return authToken;
    }

    public void register(UserData user) throws ResponseException {
        var path = "/user";
        communicator.register("POST", path, user);
    }

    public void login(UserData user) throws ResponseException {
        var path = "/session";
        communicator.login("POST", path, user);
    }

    public void logout() throws ResponseException {
        var path = "/session";
        communicator.logout("POST", path);
    }

//    public Object listgames(GameData game) throws ResponseException {
//        var path = "/game";
//        return communicator.listgames("GET", path, game);
//    }
//
//    public Object creategame(GameData game) throws ResponseException {
//        var path = "/game";
//        return communicator.creatgame("POST", path, game);
//    }
//
//    public Object joingame(GameData game) throws ResponseException {
//        var path = "/game";
//        return communicator.joingame("POST", path, game);
//    }
}
