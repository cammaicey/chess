import chess.*;
import com.google.gson.Gson;
import dataaccess.*;
import server.Server;
import service.GameService;
import service.UserService;
import spark.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        var userService = new UserService(userDAO, authDAO);
        var gameService = new GameService(gameDAO, authDAO);

        Server server = new Server();
        server.run(8080);
    }
}