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

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessMove move;
        ChessPosition pos;
        ChessPosition tempPos;
        int srow = myPosition.getRow()-1;
        int scol = myPosition.getColumn()-1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pos = new ChessPosition(row+1, col+1);
                if (pieceType == ChessPiece.PieceType.KING) {
                    if (((row == srow && (col == scol+1 || col == scol-1)) || //left or right
                            (col == scol && (row == srow+1 || row == srow-1))) && //up or down
                            (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor)) { //and empty or enemy
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                    else if (((row == srow+1 || row == srow-1) && (col == scol-1 || col == scol+1)) &&
                            (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                else if (pieceType == ChessPiece.PieceType.QUEEN) {
                    if ((row == srow && col != scol) || (col == scol && row != srow)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                    else {
                        int trow = row;
                        int tcol = col;
                        if (row != srow && col != scol) {
                            while (tcol < 8 && trow < 8 && tcol > -1 && trow > -1) {
                                if (trow == srow && tcol == scol) {
                                    move = new ChessMove(myPosition, pos, null);
                                    moves.add(move);
                                    break;
                                } else if (row > srow) {
                                    trow--;
                                    if (col > scol) {
                                        tcol--;
                                    } else {tcol++;}
                                }
                                else {
                                    trow++;
                                    if (col > scol) {
                                        tcol--;
                                    } else {tcol++;}
                                }
                            }
                        }
                    }
                }
                else if (pieceType == ChessPiece.PieceType.BISHOP) {
                    int trow = row;
                    int tcol = col;
                    if (row != srow && col != scol) {
                        while (tcol < 8 && trow < 8 && tcol > -1 && trow > -1) {
                            tempPos = new ChessPosition(trow+1, tcol+1);
                            if (board.getPiece(tempPos) != null && //piece here
                                    (trow != srow && tcol != scol) && //it is not start
                                    (trow != row && tcol != col)) { //it is not this position
                                break;
                            }
                            else if (trow == srow && tcol == scol && //we reached the start
                                    (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor)) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                                break;
                            } else if (row > srow) {
                                trow--;
                                if (col > scol) {
                                    tcol--;
                                } else {tcol++;}
                            }
                            else {
                                trow++;
                                if (col > scol) {
                                    tcol--;
                                } else {tcol++;}
                            }
                        }
                    }
                }
                else if (pieceType == ChessPiece.PieceType.KNIGHT) {
                    if (((row == srow+2 || row == srow-2) && (col == scol+1 || col == scol-1)) ||
                            ((col == scol+2 || col == scol-2) && (row == srow-1 || row == srow+1))) {
                        if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor) {
                            move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                }
                else if (pieceType == ChessPiece.PieceType.ROOK) {
                    if ((row == srow && col != scol) || (col == scol && row != srow)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                else if (pieceType == ChessPiece.PieceType.PAWN) {
                    if (teamColor == ChessGame.TeamColor.WHITE) {
                        if ((row == srow+1 && (col == scol+1 || col == scol-1)) //capture
                                && board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != teamColor) {
                            if (row == 7) {
                                move = new ChessMove(myPosition, pos, PieceType.ROOK);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.QUEEN);
                                moves.add(move);
                            }
                            else {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (col == scol) {
                            if (srow == 1 && row == srow+2) {
                                if (board.getPiece(pos) == null) {
                                    move = new ChessMove(myPosition, pos, null);
                                    moves.add(move);
                                }
                            }
                            else if (row == srow+1 && board.getPiece(pos) == null) {
                                if (row == 7) {
                                    move = new ChessMove(myPosition, pos, PieceType.ROOK);
                                    moves.add(move);
                                    move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                    moves.add(move);
                                    move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                    moves.add(move);
                                    move = new ChessMove(myPosition, pos, PieceType.QUEEN);
                                    moves.add(move);
                                }
                                else {
                                    move = new ChessMove(myPosition, pos, null);
                                    moves.add(move);
                                }
                            }
                        }
                    }
                    else {
                        if ((row == srow-1 && (col == scol+1 || col == scol-1)) //capture
                                && board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != teamColor) {
                            if (row == 0) {
                                move = new ChessMove(myPosition, pos, PieceType.ROOK);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.QUEEN);
                                moves.add(move);
                            }
                            else {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (col == scol) {
                            if (srow == 6 && row == srow-2) {
                                tempPos = new ChessPosition(srow, scol+1);
                                if (board.getPiece(tempPos) == null && board.getPiece(pos) == null) {
                                    move = new ChessMove(myPosition, pos, null);
                                    moves.add(move);
                                }
                            }
                            else if (row == srow-1 && board.getPiece(pos) == null) {
                                if (row == 0) {
                                    move = new ChessMove(myPosition, pos, PieceType.ROOK);
                                    moves.add(move);
                                    move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                    moves.add(move);
                                    move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                    moves.add(move);
                                    move = new ChessMove(myPosition, pos, PieceType.QUEEN);
                                    moves.add(move);
                                }
                                else {
                                    move = new ChessMove(myPosition, pos, null);
                                    moves.add(move);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (pieceType != PieceType.KING && pieceType != PieceType.KNIGHT && pieceType != PieceType.PAWN && pieceType != PieceType.BISHOP) {
            moves = validMoves(board, myPosition, moves);
        }
        return moves;
    }

    public ArrayList<ChessMove> validMoves(ChessBoard board, ChessPosition myPosition, ArrayList<ChessMove> currMoves) {
        ArrayList<ChessMove> newMoves = new ArrayList<>();
        ArrayList<ChessMove> occupied = new ArrayList<>();
        if (notClear(board, currMoves)) {
            for (ChessMove move : currMoves) {
                if (board.getPiece(move.getEndPosition()) != null) {
                    occupied.add(move);
                }
            }

            //actual stuff
            for (ChessMove piece1 : occupied) {
                int rowP1 = piece1.getEndPosition().getRow();
                int colP1 = piece1.getEndPosition().getColumn();
                for (ChessMove piece2 : currMoves) {
                    int rowP2 = piece2.getEndPosition().getRow();
                    int colP2 = piece2.getEndPosition().getColumn();
                    if (!inMoves(piece2, newMoves)) {
                        if (pieceType == PieceType.ROOK) {
                            if (rowP2 == rowP1) {
                                if ((colP2 >= colP1 && colP2 >= myPosition.getColumn()) ||
                                        (colP2 <= colP1 && colP2 <= myPosition.getColumn())) {
                                    if (colP2 == colP1) {
                                        if (board.getPiece(piece2.getEndPosition()) != null &&
                                                board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) {
                                            newMoves.add(piece2);
                                        }
                                    }
                                }
                                else {
                                    newMoves.add(piece2);
                                }
                            }
                            else if (colP2 == colP1) {
                                if ((rowP2 >= rowP1 && rowP2 >= myPosition.getRow()) ||
                                        (rowP2 <= rowP1 && rowP2 <= myPosition.getRow())) {
                                    if (rowP2 == rowP2) {
                                        if (board.getPiece(piece2.getEndPosition()) != null &&
                                                board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) {
                                            newMoves.add(piece2);
                                        }
                                    }
                                }
                                else {
                                    newMoves.add(piece2);
                                }
                            }
                        }
                        else {
                            if (rowP2 != myPosition.getRow() && colP2 != myPosition.getColumn()) {
                                if (rowP2 == rowP1 && colP2 == colP1
                                        && board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) {
                                    newMoves.add(piece2);
                                }
                                else {
                                    if ((colP2 >= colP1 && colP2 > myPosition.getColumn()) ||
                                            (colP2 <= colP1 && colP2 < myPosition.getColumn())) {
                                        if (colP2 == colP1) { //weird edge case
                                            if (colP2 == 1 || colP2 == 8) {
                                                if (board.getPiece(piece2.getEndPosition()) == null) {
                                                    newMoves.add(piece2);
                                                }
                                            }
                                        }

                                    }
                                    else if (board.getPiece(piece2.getEndPosition()) == null || board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) {
                                        newMoves.add(piece2);
                                    }
                                }
                            }
                            else {
                                if (rowP2 == rowP1) {
                                    if ((colP2 >= colP1 && colP2 >= myPosition.getColumn()) ||
                                            (colP2 <= colP1 && colP2 <= myPosition.getColumn())) {
                                        if (colP2 == colP1) {
                                            if (board.getPiece(piece2.getEndPosition()) != null &&
                                                    board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) {
                                                newMoves.add(piece2);
                                            }
                                        }
                                    }
                                    else {
                                        newMoves.add(piece2);
                                    }
                                }
                                else if (colP2 == colP1) {
                                    if ((rowP2 >= rowP1 && rowP2 >= myPosition.getRow()) ||
                                            (rowP2 <= rowP1 && rowP2 <= myPosition.getRow())) {
                                        if (rowP2 == rowP2) {
                                            if (board.getPiece(piece2.getEndPosition()) != null &&
                                                    board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) {
                                                newMoves.add(piece2);
                                            }
                                        }
                                    }
                                    else {
                                        newMoves.add(piece2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return newMoves;
        }
        return currMoves;
    }

    public boolean notClear(ChessBoard board, ArrayList<ChessMove> currMoves) {
        for (ChessMove move : currMoves) {
            if (board.getPiece(move.getEndPosition()) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean inMoves(ChessMove piece, ArrayList<ChessMove> currMoves) {
        for (ChessMove move : currMoves) {
            if (piece == move) {
                return true;
            }
        }
        return false;
    }
}
