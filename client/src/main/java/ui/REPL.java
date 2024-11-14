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
    boolean listed = false;
    ServerFacade client;
    Map<Integer, Integer> allGames = new HashMap<>();

    public REPL(String serverURL) {
        client = new ServerFacade(serverURL);
    }

    public void run() throws ResponseException, IOException, URISyntaxException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);

        while (!loggedIn) {
            prelogin(out);
            String line = scanner.nextLine();

            if (Objects.equals(line, "1")) {
                loggedIn = menuRegister(out, scanner);
            } else if (Objects.equals(line, "2")) {
                loggedIn = menuLogin(out, scanner);
            } else if (Objects.equals(line, "3")) {
                out.println("Press 1 to register as a new user.");
                out.println("Press 2 to login as an existing user.");
                out.println("Press 3 to exit the program.\n");
            } else if (Objects.equals(line, "4")) {
                out.println("Goodbye!");
                out.close();
                System.exit(0);
            } else {
                out.println("Invalid selection.\n");
            }
        }
        while (loggedIn) {
            postlogin(out);
            String line = scanner.nextLine();
            if (Objects.equals(line, "1")) {
                loggedIn = false;
                listed = false;
                client.logout();
                run();
            } else if (Objects.equals(line, "2")) {
                out.println("Please enter a name for your game.");
                String name = scanner.nextLine();
                client.creategame(name);
            } else if (Objects.equals(line, "3")) {
                int num = 1;
                HashSet<GameData> games = client.listgames().games();

                for (GameData game : games) {
                    out.println(num + ". " + game.gameName());
                    out.println("\tWhite Player: " + game.whiteUsername());
                    out.println("\tBlack Player: " + game.blackUsername());
                    out.println("\t");
                    allGames.put(num, game.gameID());
                    num++;
                }
                listed = true;
            } else if (Objects.equals(line, "4")) {
                if (!listed) {
                    out.println("\nPlease list the games first.\n");
                    continue;
                }
                joinGame(out, scanner);
            } else if (Objects.equals(line, "5")) {
                if (!listed) {
                    out.println("\nPlease list the games first.\n");
                    continue;
                }
                observeGame(out, scanner);
            } else if (Objects.equals(line, "6")) {
                out.println("Press 1 to logout and return to the previous menu.");
                out.println("Press 2 to create a new game.");
                out.println("Press 3 to list all games.");
                out.println("Press 4 to join a game.");
                out.println("Press 5 to observe a game.\n");
            } else {
                out.println("Invalid selection.\n");
            }
        }
    }

    private void prelogin(PrintStream out) {
        out.println("Welcome! Please enter a number corresponding to one of the ones below.");
        out.println("\t1. Register");
        out.println("\t2. Login");
        out.println("\t3. Help");
        out.println("\t4. Quit");
    }

    private boolean menuRegister (PrintStream out, Scanner scanner) throws IOException {
        out.println("Please enter a username.");
        String username = scanner.nextLine();
        while (username.isEmpty()) {
            out.println("Please enter a username.");
            username = scanner.nextLine();
        }
        out.println("Please enter an email address.");
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
            return true;
        } catch (ResponseException e) {
            int status = e.statusCode();
            switch (status) {
                case 400:
                    out.println("Bad request.\n");
                    break;
                case 401:
                    out.println("Unauthorized.\n");
                    break;
                case 403:
                    out.println("Already taken.\n");
                    break;
            }
            return false;
        }
    }

    private boolean menuLogin (PrintStream out, Scanner scanner) throws ResponseException, URISyntaxException, IOException {
        out.println("Please enter your username.");
        String username = scanner.nextLine();
        out.println("Please enter your password.");
        String password = scanner.nextLine();
        userData = new UserData(username, password, "");
        try {
            client.login(userData);
        } catch (ResponseException e) {
            int status = e.statusCode();
            if (status == 401) {
                out.println("Unauthorized username or password.\n");
                return false;
            }
        }
        return true;
    }

    private void postlogin(PrintStream out) {
        out.println("Please enter a number corresponding to one of the ones below.");
        out.println("\t1. Logout");
        out.println("\t2. Create Game");
        out.println("\t3. List Games");
        out.println("\t4. Play Game");
        out.println("\t5. Observe Game");
        out.println("\t6. Help");
    }

    private void joinGame(PrintStream out, Scanner scanner) throws ResponseException, IOException {
        out.println("Please enter the number of the game you wish to join.");
        String number = scanner.nextLine();
        boolean isNumeric = true;
        while (true) {
            isNumeric = checkNonNumeric(number);
            if (!isNumeric) {
                out.println("Invalid game number.\nPlease enter the number of the game you wish to join.");
                number = scanner.nextLine();
                isNumeric = true;
                continue;
            }
            if (!allGames.containsKey(Integer.parseInt(number))) {
                out.println("Invalid game number.\nPlease enter the number of the game you wish to join.");
                number = scanner.nextLine();
                continue;
            }
            break;
        }
        out.println("What color do you wish to be? Enter WHITE or BLACK.");
        String color = scanner.nextLine();
        while (true) {
            if (Objects.equals(color, "WHITE") || Objects.equals(color, "BLACK")) {
                break;
            }
            out.println("Invalid color. Enter WHITE or BLACK.");
            color = scanner.nextLine();
        }
        joinData = new JoinData(color, allGames.get(Integer.parseInt(number)));
        try {
            client.joingame(joinData);
            new DrawBoard(new ChessGame()).drawBoard();
        } catch (ResponseException e) {
            int status = e.statusCode();
            switch (status) {
                case 400:
                    out.println("Bad request.\n");
                    break;
                case 401:
                    out.println("Unauthorized username or password.\n");
                    break;
                case 403:
                    out.println("Already taken.\n");
                    break;
            }
        }
    }

    private void observeGame(PrintStream out, Scanner scanner) {
        out.println("Please enter the number of the game you wish to observe.");
        String number = scanner.nextLine();
        boolean isNumeric = true;
        while (true) {
            isNumeric = checkNonNumeric(number);
            if (!isNumeric) {
                out.println("Invalid game number.\nPlease enter the number of the game you wish to observe.");
                number = scanner.nextLine();
                isNumeric = true;
                continue;
            }
            if (!allGames.containsKey(Integer.parseInt(number))) {
                out.println("Invalid game number.\nPlease enter the number of the game you wish to observe.");
                number = scanner.nextLine();
                continue;
            }
            new DrawBoard(new ChessGame()).drawBoard();;
            break;
        }
    }

    public boolean checkNonNumeric(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
