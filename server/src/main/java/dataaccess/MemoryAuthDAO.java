package dataaccess;

import model.AuthData;

public class MemoryAuthDAO implements AuthDAO {
    public AuthData createAuth(AuthData authData) {}

    public AuthData getAuth(String authToken) {}

    public void deleteAuth(AuthData authData) {}
}
