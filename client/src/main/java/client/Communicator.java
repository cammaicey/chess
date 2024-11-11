package client;

import model.UserData;

public class Communicator {
    private final String serverUrl;
    private final ServerFacade serverFacade;

    public Communicator(ServerFacade facade, String serverURL) {
        this.serverUrl = serverURL;
        this.serverFacade = facade;
    }

    public Object register(UserData user) {
        return null;
    }

    public Object login(UserData user) {
        return null;
    }

    public void logout(UserData user) {}
}
