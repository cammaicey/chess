import chess.*;
import client.ServerFacade;
import exception.ResponseException;
import ui.DrawBoard;
import ui.REPL;

public class Main {
    public static void main(String[] args) throws ResponseException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        var serverURL = "http://localhost:8080";
        if (args.length == 1) {
            serverURL = args[0];
        }

        new REPL(serverURL).run();
    }
}