package dataaccess;

import model.AuthData;
import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {
    final private HashMap<Integer, AuthData> auths = new HashMap<>();

    public AuthData createAuth(AuthData authData) {
        return null;
    }

    public AuthData getAuth(String authToken) {
        return null;
    }

    public void deleteAuth(AuthData authData) {}

    public void deleteAllAuths() {}
}
