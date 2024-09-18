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
    private PieceType pieceType;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
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
        int srow = myPosition.getRow()-1; //starting row
        int scol = myPosition.getColumn()-1; //starting col
        ChessPosition pos;
        ChessMove move;
        ArrayList<ChessMove> moves = new ArrayList<>(); //store valid moves
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                int tcol = col;
                int trow = row;
                pos = new ChessPosition(row+1, col+1);
                if (pType == PieceType.KING) {
                    //left or right
                    if ((col == scol+1 || col == scol-1) && row == srow) {
                        move = new ChessMove(myPosition, pos, getPieceType());
                        moves.add(move);
                    }
                    //up or down
                    else if ((row == srow+1 || row == srow-1) && col == scol) {
                        move = new ChessMove(myPosition, pos, getPieceType());
                        moves.add(move);
                    }
                    //diagonals
                    else if ((row == srow+1 && (col == scol+1 || col == scol-1)) || (row == srow-1 && (col == scol+1 || col == scol-1))) {
                        move = new ChessMove(myPosition, pos, getPieceType());
                        moves.add(move);
                    }
                }
                else if (pType == PieceType.QUEEN) {
                    if (col == scol || row == srow) { //imported from rook
                        if (col == scol && row == srow) {
                            continue;
                        }
                        else {
                            move = new ChessMove(myPosition, pos, getPieceType());
                            moves.add(move);
                        }
                    }
                    else {
                        if (col != scol && row != srow) { //can't move in the row and column
                            while (tcol < 8 && tcol > -1 && trow > -1 && trow < 8) { //stay in bounds
                                if (tcol == scol && trow == srow) { //we reached starting pos
                                    move = new ChessMove(myPosition, pos, getPieceType());
                                    moves.add(move);
                                    break;
                                } else if (col < scol) { // column less than position
                                    tcol++;
                                    if (row < srow) { //row less than
                                        trow++;
                                    } else {
                                        trow--;
                                    }
                                } else if (col > scol) { //column greater than position
                                    tcol--;
                                    if (row > srow) { //row greater than
                                        trow--;
                                    } else {
                                        trow++;
                                    }
                                }
                            }
                        }
                    }
                }
                else if (pType == PieceType.BISHOP) {
                    //check which diagonal
                    if (col != scol && row != srow) { //can't move in the row and column
                        while (tcol < 8 && tcol > -1 && trow > -1 && trow < 8) { //stay in bounds
                            if (tcol == scol && trow == srow) { //we reached starting pos
                                move = new ChessMove(myPosition, pos, getPieceType());
                                moves.add(move);
                                break;
                            }
                            else if (col < scol) { // column less than position
                                tcol++;
                                if (row < srow) { //row less than
                                    trow++;
                                }
                                else {trow--;}
                            }
                            else if (col > scol) { //column greater than position
                                tcol--;
                                if (row > srow) { //row greater than
                                    trow--;
                                }
                                else {trow++;}
                            }
                        }
                    }
                }
                else if (pType == PieceType.KNIGHT) {
                    if (((col == scol+2 || col == scol-2) && (row == srow+1 || row == srow-1)) ||
                            ((row == srow+2 || row == srow-2) && (col == scol+1 || col == scol-1))) {
                        if (board.taken(pos) && board.getPiece(pos).getTeamColor() == tColor) { //team piece is already there
                            continue;
                        }
                        move = new ChessMove(myPosition, pos, getPieceType());
                        moves.add(move);
                    }
                }
                else if (pType == PieceType.ROOK) {
                    if (col == scol || row == srow) {
                        if (col == scol && row == srow) {
                            continue;
                        }
                        else {
                            move = new ChessMove(myPosition, pos, getPieceType());
                            moves.add(move);
                        }
                    }
                }
                else if (pType == PieceType.PAWN) {
                    if (tColor == ChessGame.TeamColor.WHITE) {
                        //check capture condition
                        if (srow == 1 && (row == srow+1 || row == srow+2) && col == scol) { //start and in reach
                            move = new ChessMove(myPosition, pos, getPieceType());
                            moves.add(move);
                        }
                        else if (row == srow+1 && col == scol) {
                            move = new ChessMove(myPosition, pos, getPieceType());
                            moves.add(move);
                        }
                    }
                    else {
                        //check capture condition
                        if (srow == 6 && (row == srow - 1 || row == srow - 2) && col == scol) { //start and in reach
                            move = new ChessMove(myPosition, pos, getPieceType());
                            moves.add(move);
                        } else if (row == srow-1 && col == scol) {
                            move = new ChessMove(myPosition, pos, getPieceType());
                            moves.add(move);

                        }
                    }
                }
            }
        }
        moves = getCaptureBlock(moves, myPosition, board);
        return moves;
    }

    public ArrayList<ChessMove> getCaptureBlock(ArrayList<ChessMove> currMoves, ChessPosition myPosition, ChessBoard board) {
        ArrayList<ChessMove> newMoves = new ArrayList<>();
        if (notClearMoves(currMoves, board)) {
            if (this.pieceType == PieceType.KING) {}
            else if (this.pieceType == PieceType.QUEEN) {}
            else if (this.pieceType == PieceType.BISHOP) {}
            else if (this.pieceType == PieceType.KNIGHT) {}
            else if (this.pieceType == PieceType.ROOK) {}
            else if (this.pieceType == PieceType.PAWN) {}
            return newMoves;
        }
        else {
            return currMoves;
        }
    }

    public boolean notClearMoves(ArrayList<ChessMove> currMoves, ChessBoard board) {
        for (ChessMove move : currMoves) {
            if (board.taken(move.getEndPosition())) {
                return true;
            }
        }
        return false;
    }
}
