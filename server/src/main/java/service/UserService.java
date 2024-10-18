package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import exception.ResponseException;

import java.util.UUID;

public class UserService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData register(UserData user) throws ResponseException, DataAccessException {
        if (userDAO.getUser(user.username()) == null) {
            userDAO.createUser(user);
        }
        else if (userDAO.getUser(user.username()) != null) {
            DataAccessException e = new DataAccessException("Error: already taken");
            ResponseException r = new ResponseException(403, e.getMessage());
            throw r;
        }
        AuthData authData = new AuthData(UUID.randomUUID().toString(), user.username());
        authDAO.createAuth(authData);
        return authData;
    }
    public AuthData login(UserData user) throws ResponseException {
        return null;
    }
    public void logout(AuthData auth) throws ResponseException {}

    public void clearUsers() throws ResponseException, DataAccessException {
        userDAO.deleteAllUsers();
    }
}
