package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class REPL {

    public REPL() {
    }

    public void run() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        boolean loggedIn = false;

        Scanner scanner = new Scanner(System.in);

        while (!loggedIn) {
            prelogin(out);
            String line = scanner.nextLine();

            if (Objects.equals(line, "1")) {
                menuRegister(out, scanner);
                //call register
                loggedIn = true;
            }
            else if (Objects.equals(line, "2")) {
                menuLogin(out, scanner);
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
                out.println("Invalid selection.");
            }
        }
        while (loggedIn) {
            postlogin(out);
            String line = scanner.nextLine();
            if (Objects.equals(line, "1")) {
                loggedIn = false;
            }
            else if (Objects.equals(line, "2")) {
                out.println("Please enter a name for your game.");
                scanner.nextLine();
            }
            else if (Objects.equals(line, "3")) {
                //numbered list (doesn't correspond to game id)
                //game name + player names
            }
            else if (Objects.equals(line, "4")) {
                out.println("Please enter the number of the game you wish to join.");
                scanner.nextLine();
                out.println("What color do you wish to be?");
                scanner.nextLine();
                //join based on input
            }
            else if (Objects.equals(line, "5")) {
                out.println("Please enter the number of the game you wish to observe.");
                scanner.nextLine();
            }
            else {
                out.println("Invalid selection.");
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
        scanner.nextLine();
        out.println("Please enter and email address.");
        scanner.nextLine();
        out.println("Please enter a password.");
        scanner.nextLine();
    }

    private void menuLogin (PrintStream out, Scanner scanner) {
        out.println("Please enter your username.");
        scanner.nextLine();
        out.println("Please enter your password.");
        scanner.nextLine();
    }

    private void postlogin(PrintStream out) {
        out.println("Please type a number corresponding to the ones below.");
        out.println("\t1. Logout");
        out.println("\t2. Create Game");
        out.println("\t3. List Games");
        out.println("\t4. Play Game");
        out.println("\t5. Observe Game");
    }

}
