package dataaccess;

import model.UserData;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    final private HashMap<String, UserData> users = new HashMap<>();

    public UserData createUser(UserData user) {
        return null;
    }

    public UserData getUser(String username) {
        return null;
    }

    public void deleteAllUsers() {}
}
