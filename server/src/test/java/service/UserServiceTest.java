package service;

import dataaccess.*;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private UserService userService;
    private UserDAO userDAO;
    private AuthDAO authDAO;
    //private AuthData expected;

    @BeforeEach
    public void setUp() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        userService = new UserService(userDAO, authDAO);
    }

    //test register
    @Test
    public void testRegister() {
        UserData user = new UserData("csstudent", "schoolisgreat", "cs@gmail.com");
        try {
            userService.register(user);
        } catch (ResponseException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
