package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawBoard {

    ChessGame game;

    public DrawBoard(ChessGame game) {
        this.game = game;
    }

    public void drawBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeader(out);

        for (int row = 0; row < 8; row++) {
            drawRow(out, row);
        }

        drawHeader(out);

    }

    private void drawHeader(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);

        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            if (boardCol == 0) {
                out.print("   ".repeat(1));
            }
            int prefixLength = 1;
            int suffixLength = 1;

            out.print("   ".repeat(prefixLength));
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_MAGENTA);

            out.print(headers[boardCol]);

            out.print(SET_BG_COLOR_DARK_GREY);
            out.print("   ".repeat(suffixLength));

            if (boardCol == 7) {
                out.print("   ".repeat(1));
                out.print(RESET_BG_COLOR);
            }
        }

        out.println();
    }

    private void drawRow(PrintStream out, int row) {}

    private void blackSquare() {}

    private void whiteSquare() {}
}
