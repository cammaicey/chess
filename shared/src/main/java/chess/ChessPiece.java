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
        int srow = myPosition.getRow()-1; //starting row
        int scol = myPosition.getColumn()-1; //starting col
        ChessPosition pos; //the position we may be adding
        ChessPosition tempPos; //for pawn
        ChessMove move; //the move
        ArrayList<ChessMove> moves = new ArrayList<>(); //store valid moves
        for (int row = 0; row < 8; row++) { //iterate over the row
            for (int col = 0; col < 8; col++) { //iterate over the column
                pos = new ChessPosition(row+1, col+1); //position of possible move
                if (pieceType == PieceType.KING) {
                    //left/right or up/down
                    if ((((col == scol+1 || col == scol-1)
                            && row == srow) || ((row == srow+1 || row == srow-1)
                            && col == scol)) && (!board.taken(pos) || board.getPiece(pos).getTeamColor() != teamColor)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                    //diagonals
                    else if (((row == srow+1 && (col == scol+1 || col == scol-1)) || //is the diagonal up?
                            (row == srow-1 && (col == scol+1 || col == scol-1))) //is the diagonal down?
                            && (!board.taken(pos) || board.getPiece(pos).getTeamColor() != teamColor)) {
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                else if (pieceType == PieceType.QUEEN) {
                    if (col == scol || row == srow) { //we are in the same row or same column
                        if ((col == scol && row != srow) || (col != scol && row == srow)) { //make sure not starting piece
                            move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                    else {
                        int trow = row;
                        int tcol = col;
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
                else if (pieceType == PieceType.BISHOP) {
                    int trow = row;
                    int tcol = col;
                    if (col != scol && row != srow) { //can't move in the row and column
                        while (tcol < 8 && tcol > -1 && trow > -1 && trow < 8) { //stay in bounds
                            if (tcol == scol && trow == srow) { //we reached starting pos
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                                break;
                            }
                            else if (col < scol) { // left side
                                tcol++;
                                if (row < srow) { //bottom side
                                    trow++;
                                }
                                else {trow--;} //upper side
                            }
                            else { // right side
                                tcol--;
                                if (row > srow) { //upper side
                                    trow--;
                                }
                                else {trow++;} //bottom side
                            }
                        }
                    }
                }
                else if (pieceType == PieceType.KNIGHT) {
                    if (((col == scol+2 || col == scol-2) && (row == srow+1 || row == srow-1)) || //spot is on the left or right
                            ((row == srow+2 || row == srow-2) && (col == scol+1 || col == scol-1))) { //spot is up or down
                        if (board.taken(pos) && board.getPiece(pos).getTeamColor() == teamColor) { //team piece is already there
                            continue;
                        }
                        move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                else if (pieceType == PieceType.ROOK) {
                    if (col == scol || row == srow) { //we are in the same row or same column
                        if ((col == scol && row != srow) || (col != scol && row == srow)) { //make sure not starting piece
                            move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                }
                else if (pieceType == PieceType.PAWN) {
                    if (teamColor == ChessGame.TeamColor.WHITE) {
                        //check capture condition
                        if (row == srow+1 && (col == scol-1 || col == scol+1) && board.taken(pos) && board.getPiece(pos).getTeamColor() != teamColor) { //is it a diagonal, spot taken, and enemy
                            if (row == 7) { //we reached the edge
                                move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.ROOK);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.QUEEN);
                                moves.add(move);
                            }
                            else { //add normal piece
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        else if (col == scol) { //pawn can only move forward
                            if (srow == 1 && row == srow+2) { //initial move aka can move twice
                                if (!board.taken(pos)) {
                                    move = new ChessMove(myPosition, pos, null);
                                    moves.add(move);
                                }
                            }
                            else if (row == srow+1) { //not in start
                                if (!board.taken(pos)) { //can move here
                                    if (row == 7) { //reached the end
                                        move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                        moves.add(move);
                                        move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                        moves.add(move);
                                        move = new ChessMove(myPosition, pos, PieceType.ROOK);
                                        moves.add(move);
                                        move = new ChessMove(myPosition, pos, PieceType.QUEEN);
                                        moves.add(move);
                                    }
                                    else { //not at the end yet
                                        move = new ChessMove(myPosition, pos, null);
                                        moves.add(move);
                                    }
                                }
                            }
                        }
                    }
                    else { //the piece is black
                        if (row == srow-1 && (col == scol-1 || col == scol+1) && board.taken(pos) && board.getPiece(pos).getTeamColor() != teamColor) { //on a diagonal, spot take, and opposite color
                            if (row == 0) { //reached the white side
                                move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.ROOK);
                                moves.add(move);
                                move = new ChessMove(myPosition, pos, PieceType.QUEEN);
                                moves.add(move);
                            }
                            else { //not the end so add normal piece
                                move = new ChessMove(myPosition, pos, null);
                                moves.add(move);
                            }
                        }
                        if (col == scol) { //in the right column
                            if (srow == 6 && row == srow - 2) { //initial spot aka can move 2 spaces
                                tempPos = new ChessPosition(srow, scol+1); //keeping track of pos directly in front of pawn
                                if (!board.taken(pos)) { //empty
                                    if (!board.taken(tempPos)) { //the position in front of the pawn is empty
                                        move = new ChessMove(myPosition, pos, null);
                                        moves.add(move);
                                    }
                                }
                            }
                            else if (row == srow-1) { //just one space
                                if (!board.taken(pos)) { //nothing here yippee
                                    if (row == 0) { //it is the edge
                                        move = new ChessMove(myPosition, pos, PieceType.BISHOP);
                                        moves.add(move);
                                        move = new ChessMove(myPosition, pos, PieceType.KNIGHT);
                                        moves.add(move);
                                        move = new ChessMove(myPosition, pos, PieceType.ROOK);
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
        }
        if (pieceType != PieceType.KING && pieceType != PieceType.KNIGHT && pieceType != PieceType.PAWN) { //only checks queen/bishop/rook
            moves = getCaptureBlock(moves, myPosition, board); //gets rid of spots past blocked or captured pieces
        }
        return moves;
    }

    public ArrayList<ChessMove> getCaptureBlock(ArrayList<ChessMove> currMoves, ChessPosition myPosition, ChessBoard board) {
        ArrayList<ChessMove> newMoves = new ArrayList<>(); //actually valid moves
        ArrayList<ChessMove> occupied = new ArrayList<>(); //keep track of spaces that are occupied
        if (noClearMoves(currMoves, board)) { //can we not move anywhere on the board?
            for (ChessMove piece: currMoves) { //get pieces that are taking up space on the board
                if (board.taken(piece.getEndPosition())) { //is there a piece here?
                    occupied.add(piece); //there is a piece there
                }
            }
            for (ChessMove piece1: occupied) { //goes over the occupied pieces
                int rowP1 = piece1.getEndPosition().getRow(); //row that the occupied piece is in
                int colP1 = piece1.getEndPosition().getColumn(); //column that the occupied piece is in
                for (ChessMove piece2: currMoves) { //goes over all the spots in our invalid array
                    int rowP2 = piece2.getEndPosition().getRow(); //row for current piece
                    int colP2 = piece2.getEndPosition().getColumn(); //column for current peace
                    if (!inMoves(newMoves, piece2)) { //check that current piece is not already in valid moves
                        if (this.pieceType == PieceType.QUEEN) {
                            if (colP2 != myPosition.getColumn() && rowP2 != myPosition.getRow()) {
                                if (rowP2 == rowP1 && colP2 == colP1 && board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) { //it is the occupying piece and it is an enemy
                                    newMoves.add(piece2);
                                }
                                //check up and diagonal
                                else if (rowP2 > rowP1) { //piece2 is above piece1
                                    if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) { //piece2 is past piece1 (or equal to piece1) and starting piece
                                        if (colP2 == colP1) { //column is the same
                                            if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) { //checks for edge case
                                                newMoves.add(piece2);
                                            }
                                        }
                                    }
                                    else if (!board.taken(piece2.getEndPosition()) || board.getPiece(piece2.getEndPosition()).getTeamColor() != this.teamColor) { //capture piece
                                        newMoves.add(piece2);
                                    }
                                }
                                //check down and diagonal
                                else { //piece2 is below piece1
                                    if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) { //piece2 is past piece1 (or equal to piece1) and starting piece
                                        if (colP2 == colP1) { //column is the same
                                            if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) { //checks for edge case
                                                newMoves.add(piece2);
                                            }
                                        }
                                    }
                                    else if (!board.taken(piece2.getEndPosition()) || board.getPiece(piece2.getEndPosition()).getTeamColor() != this.teamColor) { //capture piece
                                        newMoves.add(piece2);
                                    }
                                }
                            }
                            else { //it is a rook type move
                                if (rowP2 == rowP1) { //are we checking for row?
                                    if ((colP2 >= myPosition.getColumn() && colP2 >= colP1) ||
                                            (colP2 <= myPosition.getColumn() && colP2 <= colP1)) { //is piece2 piece1 or past it make sure past both start and piece1
                                        if (board.getPiece(piece2.getEndPosition()) != null && board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) { //not empty space but it's enemy
                                            newMoves.add(piece2);
                                        }
                                    }
                                    else {
                                        newMoves.add(piece2);
                                    }
                                }
                                else { //we are checking the column
                                    if ((rowP2 >= myPosition.getRow() && rowP2 >= rowP1) ||
                                            (rowP2 <= myPosition.getRow() && rowP2 <= rowP1)) { //is piece2 piece1 or past it make sure past both start and piece1
                                        if (board.getPiece(piece2.getEndPosition()) != null && board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) { //not empty space but it's enemy
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
                            if (rowP2 == rowP1 && colP2 == colP1 && board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) { //it is the occupying piece and it is an enemy
                                newMoves.add(piece2);
                            }
                            //check up and diagonal
                            else if (rowP2 > rowP1) { //piece2 is above piece1
                                if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) { //piece2 is past piece1 (or equal to piece1) and starting piece
                                    if (colP2 == colP1) { //column is the same
                                        if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) { //checks for edge case
                                            newMoves.add(piece2);
                                        }
                                    }
                                }
                                else if (!board.taken(piece2.getEndPosition()) || board.getPiece(piece2.getEndPosition()).getTeamColor() != this.teamColor) { //capture piece
                                    newMoves.add(piece2);
                                }
                            }
                            //check down and diagonal
                            else { //piece2 is below piece1
                                if (colP2 >= colP1 && colP2 > myPosition.getColumn() || colP2 <= colP1 && colP2 < myPosition.getColumn()) { //piece2 is past piece1 (or equal to piece1) and starting piece
                                    if (colP2 == colP1) { //column is the same
                                        if ((colP2 == 1 || colP2 == 8) && board.getPiece(piece2.getEndPosition()) == null) { //checks for edge case
                                            newMoves.add(piece2);
                                        }
                                    }
                                }
                                else if (!board.taken(piece2.getEndPosition()) || board.getPiece(piece2.getEndPosition()).getTeamColor() != this.teamColor) { //capture piece
                                    newMoves.add(piece2);
                                }
                            }
                        }
                        else if (this.pieceType == PieceType.ROOK) {
                            if (rowP2 == rowP1) { //are we checking for row?
                                if ((colP2 >= myPosition.getColumn() && colP2 >= colP1) ||
                                        (colP2 <= myPosition.getColumn() && colP2 <= colP1)) { //is piece2 piece1 or past it make sure past both start and piece1
                                    if (board.getPiece(piece2.getEndPosition()) != null && board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) { //not empty space but it's enemy
                                        newMoves.add(piece2);
                                    }
                                }
                                else {
                                    newMoves.add(piece2);
                                }
                            }
                            else { //we are checking the column
                                if ((rowP2 >= myPosition.getRow() && rowP2 >= rowP1) ||
                                        (rowP2 <= myPosition.getRow() && rowP2 <= rowP1)) { //is piece2 piece1 or past it make sure past both start and piece1
                                    if (board.getPiece(piece2.getEndPosition()) != null && board.getPiece(piece2.getEndPosition()).getTeamColor() != teamColor) { //not empty space but it's enemy
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
            }
            return newMoves; //actual valid moves
        }
        else { //no pieces are in the way on any direction
            return currMoves; //keep the same moves
        }
    }

    public boolean noClearMoves(ArrayList<ChessMove> currMoves, ChessBoard board) {
        for (ChessMove piece : currMoves) {
            if (board.taken(piece.getEndPosition())) {
                return true;
            }
        }
        return false;
    }

    public boolean inMoves(ArrayList<ChessMove> newMoves, ChessMove piece) {
        for (ChessMove move : newMoves) { //go through our current new moves
            if (move.getEndPosition() == piece.getEndPosition()) { //these have the same end therefore same piece
                return true; //it is in the newMoves
            }
        }
        return false; //not in new
    }
}
