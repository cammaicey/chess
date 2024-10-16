package dataaccess;

import model.UserData;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO {
    final private HashSet<UserData> users = new HashSet<>();

    public void createUser(UserData user) {
        if (getUser(user.username()) == null) { //user doesn't exist
            users.add(user);
        }
    }

    public UserData getUser(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void deleteAllUsers() {
        users.clear();
    }
}
