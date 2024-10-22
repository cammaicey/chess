package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void createAuth(AuthData authData) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    String getUsername(String authToken) throws DataAccessException;

    void deleteAuth(AuthData authData) throws DataAccessException;

    void deleteAllAuths() throws DataAccessException;
}
