package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }

    private ChessBoard board;
    private TeamColor teamTurn;
    private ChessBoard cloneBoard;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        cloneBoard = clone(board);
        setTeamTurn(TeamColor.WHITE);
    }

    private ChessBoard clone(ChessBoard board) {
        ChessBoard clone = new ChessBoard();
        ChessPosition pos;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pos = new ChessPosition(row+1, col+1);
                if (board.getPiece(pos) != null) {
                    clone.addPiece(pos, board.getPiece(pos));
                }
            }
        }
        return clone;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = getBoard().getPiece(startPosition);
        setTeamTurn(piece.getTeamColor());
        Collection<ChessMove> moves = new ArrayList<>();
        if (piece == null) { //there isn't a piece here
            return null;
        }
        else { //there is a piece
            Collection<ChessMove> pieceMoves = piece.pieceMoves(getBoard(), startPosition); //current pieces "valid" moves
            for (ChessMove move : pieceMoves) {
                cloneBoard = clone(getBoard());
                cloneBoard.addPiece(move.getStartPosition(), null); //remove piece
                cloneBoard.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition())); //moves piece to new location
                if (!isInCheckmate(getTeamTurn())) {
                    moves.add(move);
                }
            }
            return moves;

        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        /*
            move is illegal if the chess piece cannot move there,
            if the move leaves the team’s king in danger,
            or if it’s not the corresponding team's turn.
         */
        if ((board.getPiece(move.getStartPosition()) != null && board.getPiece(move.getStartPosition()).getTeamColor() == getTeamTurn()) &&
                inValidMoves(move, validMoves(move.getStartPosition()))) {
            if (isInCheck(getTeamTurn())) {
                throw new InvalidMoveException("King is in danger");
            }
            else {
                if (move.getPromotionPiece() != null) {
                    board.addPiece(move.getEndPosition(), new ChessPiece(getTeamTurn(), move.getPromotionPiece()));
                }
                else {
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                }
                board.addPiece(move.getStartPosition(), null);
                if (getTeamTurn() == TeamColor.BLACK) {
                    setTeamTurn(TeamColor.WHITE);
                }
                else {
                    setTeamTurn(TeamColor.BLACK);
                }
            }
        }
        else {
            throw new InvalidMoveException();
        }

    }

    public boolean inValidMoves(ChessMove move, Collection<ChessMove> validMoves) {
        for (ChessMove validMove : validMoves) { //go through what the valid moves are
            if (move.equals(validMove)) { //this move is valid
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king = getKingPos(teamColor); //king's pos
        ChessPosition pos;
        ChessPiece piece;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pos = new ChessPosition(row+1, col+1); //current position
                piece = cloneBoard.getPiece(pos); //current piece
                if (piece != null && piece.getTeamColor() != teamColor) { //enemy
                    Collection<ChessMove> moves = piece.pieceMoves(cloneBoard, pos); //calcs new moves after test move
                    for (ChessMove move : moves) { //go through the piece's moves
                        if (move.getEndPosition().equals(king)) { //the king is in the list
                            return true; //this is check
                        }
                    }
                }
            }
        }
        return false;
    }

    public ChessPosition getKingPos(TeamColor teamColor) {
        ChessPosition pos;
        ChessPiece piece;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pos = new ChessPosition(row+1, col+1); //current position
                piece = cloneBoard.getPiece(pos); //current piece
                if (piece != null && //piece is there
                        piece.getTeamColor() == teamColor && //same team
                        piece.getPieceType() == ChessPiece.PieceType.KING) { //it is a king
                    return pos;
                }
            }
        }
        return null;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor) && isInStalemate(teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (getTeamTurn() == teamColor && isInCheck(teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
