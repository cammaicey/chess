package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor teamColor;
    private ChessPiece.PieceType pieceType;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.teamColor = pieceColor;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceType pType = getPieceType(); //gets piece type
        ChessGame.TeamColor tColor = getTeamColor(); //gets piece color
        ChessPosition pos;
        ChessMove move;
        ArrayList<ChessMove> moves = new ArrayList<>(); //store valid moves
        for (int col = 1; col < 9; col++) {
            for (int row = 1; row < 9; row++) {
                int tcol = col;
                int trow = row;
                if (pType == PieceType.KING) {}
                else if (pType == PieceType.QUEEN) {}
                else if (pType == PieceType.BISHOP) {
                    //check which diagonal
                    if (col != myPosition.getColumn() && row != myPosition.getRow()) { //can't move in the row and column
                        while (tcol < 9 && tcol > 0 && trow > 0 && trow < 9) { //stay in bounds
                            if (tcol == myPosition.getColumn() && trow == myPosition.getRow()) { // the move is valid
                                pos = new ChessPosition(row, col);
                                move = new ChessMove(myPosition, pos, getPieceType());
                                moves.add(move);
                                break;
                            }
                            else if (col < myPosition.getColumn() && row < myPosition.getRow()) {
                                tcol++;
                                trow++;
                            } // column and row less than position
                            else if (col < myPosition.getColumn() && row > myPosition.getRow()) {
                                tcol++;
                                trow--;
                            } //column less than position, row greater than position
                            else if (col > myPosition.getColumn() && row > myPosition.getRow()) {
                                tcol--;
                                trow--;
                            } //column and row greater than position
                            else if (col > myPosition.getColumn() && row < myPosition.getRow()) {
                                tcol--;
                                trow++;
                            } //column greater than position, row less tan position
                        }
                    }
                }
                else if (pType == PieceType.KNIGHT) {}
                else if (pType == PieceType.ROOK) {}
                else if (pType == PieceType.PAWN) {}
            }
        }
        //if same team is blocking don't continue to or past it
        //if different team is blocking capture and take their place
        return moves;
    }
}
