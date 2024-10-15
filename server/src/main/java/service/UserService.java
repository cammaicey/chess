package service;

import dataaccess.DataAccess;
import model.AuthData;
import model.UserData;

public class UserService {
    
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData register(UserData user) {
        return null;
    }
    public AuthData login(UserData user) {
        return null;
    }
    public void logout(AuthData auth) {}
    public UserData clear() {
        return null;
    }
}
