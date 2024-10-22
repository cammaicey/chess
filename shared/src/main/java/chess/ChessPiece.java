package chess;

import jdk.dynalink.Operation;

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
                    bishopMoves(row, col, board, myPosition, moves);
                    rookMoves(row, col, board, myPosition, moves);
                }
                else if (pieceType == ChessPiece.PieceType.BISHOP) {
                    bishopMoves(row, col, board, myPosition, moves);
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
                    rookMoves(row, col, board, myPosition, moves);
                }
                else if (pieceType == ChessPiece.PieceType.PAWN) {
                    if ((row == rowOperation(srow) && (col == scol+1 || col == scol-1)) //capture
                            && board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != teamColor) {
                        if (edgePromotion(row)) {
                            for (PieceType type : PieceType.values()) {
                                if (type != PieceType.PAWN && type != PieceType.KING) {
                                    move = new ChessMove(myPosition, pos, type);
                                    moves.add(move);
                                }
                            }
                        }
                        else {
                            move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                    else if (col == scol) {
                        if ((srow == 1 && row == srow+2 && teamColor == ChessGame.TeamColor.WHITE) ||
                                (srow == 6 && row == srow-2 && teamColor == ChessGame.TeamColor.BLACK)) {
                            tempPos = new ChessPosition(srow, scol+1);
                            if ((board.getPiece(pos) == null && teamColor == ChessGame.TeamColor.WHITE) ||
                                    (board.getPiece(tempPos) == null && board.getPiece(pos) == null && teamColor == ChessGame.TeamColor.BLACK)) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (row == rowOperation(srow) && board.getPiece(pos) == null) {
                            if ((row == 7 && teamColor == ChessGame.TeamColor.WHITE) ||
                                    (row == 0 && teamColor == ChessGame.TeamColor.BLACK)) {
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
        return moves;
    }

    public void bishopMoves(int row, int col, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int trow = row;
        int tcol = col;
        ChessMove move;
        ChessPosition tempPos;
        ChessPosition pos = new ChessPosition( row+1, col+1);
        if (row != myPosition.getRow()-1 && col != myPosition.getColumn()-1) {
            while (tcol < 8 && trow < 8 && tcol > -1 && trow > -1) {
                tempPos = new ChessPosition(trow+1, tcol+1);
                if (board.getPiece(tempPos) != null && //piece here
                        (trow != myPosition.getRow()-1 && tcol != myPosition.getColumn()-1) && //it is not start
                        (trow != row && tcol != col)) { //it is not this position
                    break;
                }
                else if (trow == myPosition.getRow()-1 && tcol == myPosition.getColumn()-1 && //we reached the start
                        (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor)) {
                    move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                    break;
                } else if (row > myPosition.getRow()-1) {
                    trow--;
                    if (col > myPosition.getColumn()-1) {
                        tcol--;
                    } else {tcol++;}
                }
                else {
                    trow++;
                    if (col > myPosition.getColumn()-1) {
                        tcol--;
                    } else {tcol++;}
                }
            }
        }
    }

    public void rookMoves(int row, int col, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int trow = row;
        int tcol = col;
        ChessMove move;
        ChessPosition tempPos;
        ChessPosition pos = new ChessPosition( row+1, col+1);
        if ((row == myPosition.getRow()-1 && col != myPosition.getColumn()-1) || (row != myPosition.getRow()-1 && col == myPosition.getColumn()-1)) {
            while (tcol < 8 && trow < 8 && tcol > -1 && trow > -1) {
                tempPos = new ChessPosition(trow+1, tcol+1);
                if (board.getPiece(tempPos) != null && //piece here
                        ((trow == myPosition.getRow()-1 && tcol != myPosition.getColumn()-1) || (trow != myPosition.getRow()-1 && tcol == myPosition.getColumn()-1)) && //it is not start
                        (!tempPos.equals(pos))) { //it is not this position
                    break;
                }
                else if (trow == myPosition.getRow()-1 && tcol == myPosition.getColumn()-1 && //we reached the start
                        (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor)) {
                    move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                    break;
                } else if (row != myPosition.getRow()-1 && col == myPosition.getColumn()-1) {
                    if (row > myPosition.getRow()-1) {
                        trow--;
                    } else {trow++;}
                }
                else if (row == myPosition.getRow()-1 && col != myPosition.getColumn()-1) {
                    if (col > myPosition.getColumn()-1) {
                        tcol--;
                    } else {tcol++;}
                }
            }
        }
    }

    public int rowOperation(int srow) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            return srow+1;
        }
        else {
            return srow-1;
        }
    }

    public boolean edgePromotion(int row) {
        if ((getTeamColor() == ChessGame.TeamColor.WHITE && row == 7) ||
                (getTeamColor() == ChessGame.TeamColor.BLACK && row == 0)) {
            return true;
        }
        return false;
    }

}
