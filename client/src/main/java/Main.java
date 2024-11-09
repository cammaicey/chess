import chess.*;
import ui.DrawBoard;
import ui.REPL;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        //DrawBoard drawBoard = new DrawBoard(new ChessGame());
        //drawBoard.drawBoard();
        REPL repl = new REPL();
        repl.run();
    }
}