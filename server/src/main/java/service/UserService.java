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

    public AuthData register(UserData user) throws ResponseException {
        try {
            userDAO.createUser(user);
        } catch (DataAccessException e) {
            throw new ResponseException(403, e.getMessage());
        }
        String token = UUID.randomUUID().toString();
        String uName = user.username();
        AuthData authData = new AuthData(token, uName);
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
