import chess.*;
import com.google.gson.Gson;
import server.Server;
import spark.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.run(8080);
    }
}