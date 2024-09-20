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
        PieceType pType = getPieceType(); //gets piece type
        ChessGame.TeamColor tColor = getTeamColor(); //gets piece color
        int srow = myPosition.getRow()-1; //starting row
        int scol = myPosition.getColumn()-1; //starting col
        ChessPosition pos;
        ChessPosition tempPos;
        ChessMove move;
        ArrayList<ChessMove> moves = new ArrayList<>(); //store valid moves
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                int tcol = col;
                int trow = row;
                pos = new ChessPosition(row+1, col+1);
                if (pType == PieceType.KING) {
                    //left or right
                    if ((col == scol+1 || col == scol-1)
                            && row == srow && (!board.taken(pos) || board.getPiece(pos).getTeamColor() != tColor)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                    //up or down
                    else if ((row == srow+1 || row == srow-1)
                            && col == scol && (!board.taken(pos) || board.getPiece(pos).getTeamColor() != tColor)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                    //diagonals
                    else if (((row == srow+1 && (col == scol+1 || col == scol-1)) ||
                            (row == srow-1 && (col == scol+1 || col == scol-1)))
                            && (!board.taken(pos) || board.getPiece(pos).getTeamColor() != tColor)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                else if (pType == PieceType.QUEEN) {
                    if (col == scol || row == srow) { //imported from rook
                        if (col == scol && row == srow) {
                            continue;
                        }
                        else {
                            move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                    else {
                        if (col != scol && row != srow) { //can't move in the row and column
                            while (tcol < 8 && tcol > -1 && trow > -1 && trow < 8) { //stay in bounds
                                if (tcol == scol && trow == srow) { //we reached starting pos
                                    move = new ChessMove(myPosition, pos, null);
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
                                move = new ChessMove(myPosition, pos, null);
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
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                else if (pType == PieceType.ROOK) {
                    if (col == scol || row == srow) {
                        if (col == scol && row == srow) {
                            continue;
                        }
                        else {
                            move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                }
                else if (pType == PieceType.PAWN) {
                    if (tColor == ChessGame.TeamColor.WHITE) {
                        //check capture condition
                        if (row == srow+1 && (col == scol-1 || col == scol+1)) { //check capture
                            if (board.taken(pos) && board.getPiece(pos).getTeamColor() != tColor) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (srow == 1 && (row == srow+1 || row == srow+2) && col == scol) { //start and in reach
                            if (!board.taken(pos)) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (row == srow+1 && col == scol) {
                            if (!board.taken(pos)) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                    }
                    else {
                        //check capture condition
                        if (row == srow-1 && (col == scol-1 || col == scol+1)) { //check capture
                            if (board.taken(pos) && board.getPiece(pos).getTeamColor() != tColor) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (srow == 6 && (row == srow - 1 || row == srow - 2) && col == scol) { //start and in reach
                            tempPos = new ChessPosition(srow, scol+1);
                            if (row == srow-2 && !board.taken(pos)) {
                                if (!board.taken(tempPos)) {
                                    move = new ChessMove(myPosition, pos, null);
                                    moves.add(move);
                                }
                            }
                            else if (!board.taken(pos)) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (row == srow-1 && col == scol) {
                            if (!board.taken(pos)) {
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }

                        }
                    }
                }
            }
        }
        if (pType != PieceType.KING && pType != PieceType.KNIGHT && pType != PieceType.PAWN) {
            moves = getCaptureBlock(moves, myPosition, board);
        }
        return moves;
    }

    public ArrayList<ChessMove> getCaptureBlock(ArrayList<ChessMove> currMoves, ChessPosition myPosition, ChessBoard board) {
        ArrayList<ChessMove> newMoves = new ArrayList<>();
        ArrayList<ChessMove> occupied = new ArrayList<>(); //keep track of pieces in the way
        if (notClearMoves(currMoves, board)) { //
            for (ChessMove piece: currMoves) { //get pieces that are blockers or enemies
                if (board.taken(piece.getEndPosition())) { //adds existing pieces to array
                    occupied.add(piece);
                }
            }
            for (ChessMove piece1: occupied) {
                int rowP1 = piece1.getEndPosition().getRow();
                int colP1 = piece1.getEndPosition().getColumn();
                for (ChessMove piece2: currMoves) { //iterate over current moves
                    int rowP2 = piece2.getEndPosition().getRow();
                    int colP2 = piece2.getEndPosition().getColumn();
                    if (inMoves(newMoves, piece2.getEndPosition())) { //checks if already in array
                        continue;
                    }
                    else if (this.pieceType == PieceType.QUEEN) {
                        if (colP2 != myPosition.getColumn() && rowP2 != myPosition.getRow()) {
                            if (rowP2 == rowP1 && colP2 == colP1 && board.getPiece(piece2.getEndPosition()).getTeamColor() != this.teamColor) { //it is the piece
                                newMoves.add(piece2);
                            }
                            //check up and diagonal
                            else if (rowP2 > rowP1) {
                                if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) {
                                    if (colP2 == colP1) {
                                        if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) {
                                            newMoves.add(piece2);
                                        }
                                    }
                                }
                                else if (!board.taken(piece2.getEndPosition()) || board.getPiece(piece2.getEndPosition()).getTeamColor() != this.teamColor) {
                                    newMoves.add(piece2);
                                }
                            }
                            //check down and diagonal
                            else if (rowP2 < rowP1) {
                                if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) {
                                    if (colP2 == colP1) {
                                        if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) {
                                            newMoves.add(piece2);
                                        }
                                        continue;
                                    }
                                    continue;
                                }
                                newMoves.add(piece2);
                            }
                        }
                        else {
                            if (rowP2 == rowP1) { //check row
                                if ((colP2 >= myPosition.getColumn() && colP2 >= colP1) ||
                                        (colP2 <= myPosition.getColumn() && colP2 <= colP1)) {
                                    if ( board.getPiece(piece2.getEndPosition()) == null || (board.getPiece(piece2.getEndPosition()).getTeamColor() == this.teamColor)) { //same don't add
                                        continue;
                                    }
                                    else { //capturable
                                        newMoves.add(piece2);
                                    }
                                }
                                else {
                                    newMoves.add(piece2);
                                }
                            }
                            else if (colP2 == colP1) {
                                if ((rowP2 >= myPosition.getRow() && rowP2 >= rowP1) ||
                                        (rowP2 <= myPosition.getRow() && rowP2 <= rowP1)) {
                                    if ( board.getPiece(piece2.getEndPosition()) == null || (board.getPiece(piece2.getEndPosition()).getTeamColor() == this.teamColor)) { //same don't add
                                        continue;
                                    }
                                    else { //capturable
                                        newMoves.add(piece2);
                                    }
                                }
                                else {
                                    newMoves.add(piece2);
                                }
                            }
                        }
                    }
                    else if (this.pieceType == PieceType.BISHOP) {
                        if (rowP2 == rowP1 && colP2 == colP1 && board.getPiece(piece2.getEndPosition()).getTeamColor() != this.teamColor) { //it is the piece
                            newMoves.add(piece2);
                        }
                        //check up and diagonal
                        else if (rowP2 > rowP1) {
                            if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) {
                                if (colP2 == colP1) {
                                    if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) {
                                        newMoves.add(piece2);
                                    }
                                    continue;
                                }
                                continue;
                            }
                            newMoves.add(piece2);
                        }
                        //check down and diagonal
                        else if (rowP2 < rowP1) {
                            if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) {
                                if (colP2 == colP1) {
                                    if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) {
                                        newMoves.add(piece2);
                                    }
                                    continue;
                                }
                                continue;
                            }
                            newMoves.add(piece2);
                        }
                    }
                    else if (this.pieceType == PieceType.ROOK) {
                        if (rowP2 == rowP1) { //check row
                            if ((colP2 >= myPosition.getColumn() && colP2 >= colP1) ||
                                    (colP2 <= myPosition.getColumn() && colP2 <= colP1)) {
                                if ( board.getPiece(piece2.getEndPosition()) == null || (board.getPiece(piece2.getEndPosition()).getTeamColor() == this.teamColor)) { //same don't add
                                    continue;
                                }
                                else { //capturable
                                    newMoves.add(piece2);
                                }
                            }
                            else {
                                newMoves.add(piece2);
                            }
                        }
                        else if (colP2 == colP1) {
                            if ((rowP2 >= myPosition.getRow() && rowP2 >= rowP1) ||
                                    (rowP2 <= myPosition.getRow() && rowP2 <= rowP1)) {
                                if ( board.getPiece(piece2.getEndPosition()) == null || (board.getPiece(piece2.getEndPosition()).getTeamColor() == this.teamColor)) { //same don't add
                                    continue;
                                }
                                else { //capturable
                                    newMoves.add(piece2);
                                }
                            }
                            else {
                                newMoves.add(piece2);
                            }
                        }
                    }
                }
            }
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

    public boolean inMoves(ArrayList<ChessMove> currMoves, ChessPosition piece) {
        for (ChessMove move : currMoves) {
            if (move.getEndPosition().getRow() == piece.getRow() && move.getEndPosition().getColumn() == piece.getColumn()) {
                return true;
            }
        }
        return false;
    }
}
