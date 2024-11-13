package ui;

import chess.ChessGame;
import client.ServerFacade;
import exception.ResponseException;
import model.*;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.util.Map;
import java.util.HashMap;

import static ui.EscapeSequences.*;

public class REPL {
    private UserData userData;
    private JoinData joinData;
    ServerFacade client;
    Map<Integer, Integer> allGames = new HashMap<>();

    public REPL(String serverURL) {
        client = new ServerFacade(serverURL);
    }

    public void run() throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);

        while (!loggedIn) {
            prelogin(out);
            String line = scanner.nextLine();

            if (Objects.equals(line, "1")) {
                menuRegister(out, scanner);
                out.println("Successfully logged in.\n");
                loggedIn = true;
            }
            else if (Objects.equals(line, "2")) {
                menuLogin(out, scanner);
                out.println("Successfully logged in.\n");
                loggedIn = true;
            }
            else if (Objects.equals(line, "3")) {
                out.println("Press 1 to register as a new user.");
                out.println("Press 2 to login as an existing user.");
                out.println("Press 3 to exit the program.\n");
            }
            else if (Objects.equals(line, "4")) {
                out.println("Goodbye!");
                out.close();
                System.exit(0);
            }
            else {
                out.println("Invalid selection.\n");
            }
        }
        while (loggedIn) {
            postlogin(out);
            String line = scanner.nextLine();
            if (Objects.equals(line, "1")) {
                client.logout();
                run();
            }
            else if (Objects.equals(line, "2")) {
                out.println("Please enter a name for your game.");
                String name = scanner.nextLine();
                client.creategame(name);
            }
            else if (Objects.equals(line, "3")) {
                int num = 1;
                HashSet<GameData> games = client.listgames().games();

                for (GameData game : games) {
                    out.println(num + ". " + game.gameName());
                    out.println("\tWhite Player: " + game.whiteUsername());
                    out.println("\tBlack Player: " + game.blackUsername());
                    new DrawBoard(new ChessGame()).drawBoard();
                    out.println("\t");
                    allGames.put(num, game.gameID());
                    num++;
                }
            }
            else if (Objects.equals(line, "4")) {
                out.println("Please enter the number of the game you wish to join.");
                String number = scanner.nextLine();
                out.println("What color do you wish to be? Enter WHITE or BLACK.");
                String color = scanner.nextLine();
                joinData = new JoinData(color, allGames.get(Integer.parseInt(number)));
                client.joingame(joinData);
            }
            else if (Objects.equals(line, "5")) {
                out.println("Please enter the number of the game you wish to observe.");
                scanner.nextLine();
            }
            else if (Objects.equals(line, "6")) {
                out.println("Press 1 to logout and return to the previous menu.");
                out.println("Press 2 to creat a new game.");
                out.println("Press 3 to list all games.");
                out.println("Press 4 to join a game.");
                out.println("Press 5 to observe a game.");
            }
            else {
                out.println("Invalid selection.\n");
            }
        }

    }

    private void prelogin(PrintStream out) {
        out.println("Welcome! Please type a number corresponding to the ones below.");
        out.println("\t1. Register");
        out.println("\t2. Login");
        out.println("\t3. Help");
        out.println("\t4. Quit");
    }

    private void menuRegister (PrintStream out, Scanner scanner) {
        out.println("Please enter a username.");
        String username = scanner.nextLine();
        while (username.isEmpty()) {
            out.println("Please enter a username.");
            username = scanner.nextLine();
        }
        out.println("Please enter and email address.");
        String email = scanner.nextLine();
        while (email.isEmpty()) {
            out.println("Please enter an email address.");
            email = scanner.nextLine();
        }
        out.println("Please enter a password.");
        String password = scanner.nextLine();
        while (password.isEmpty()) {
            out.println("Please enter a password.");
            password = scanner.nextLine();
        }
        userData = new UserData(username, password, email);
        try {
            client.register(userData);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    private void menuLogin (PrintStream out, Scanner scanner) {
        out.println("Please enter your username.");
        String username = scanner.nextLine();
        out.println("Please enter your password.");
        String password = scanner.nextLine();
        userData = new UserData(username, password, "");
        try {
            client.login(userData);
        } catch (ResponseException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void postlogin(PrintStream out) {
        out.println("Please type a number corresponding to the ones below.");
        out.println("\t1. Logout");
        out.println("\t2. Create Game");
        out.println("\t3. List Games");
        out.println("\t4. Play Game");
        out.println("\t5. Observe Game");
        out.println("\t6. Help");
    }

}
