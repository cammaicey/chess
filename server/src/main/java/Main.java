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
        Server server = new Server();
        server.run(8080);
    }
}