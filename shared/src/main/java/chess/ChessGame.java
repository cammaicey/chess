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

    public ChessGame() {
        board = new ChessBoard();
        setTeamTurn(TeamColor.WHITE);
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
        Collection<ChessMove> moves = new ArrayList<>();
        if (piece == null) { //there isn't a piece here
            return null;
        }
        else { //there is a piece
            Collection<ChessMove> pieceMoves = piece.pieceMoves(getBoard(), startPosition); //current pieces "valid" moves
            if (!isInCheck(getTeamTurn())) { //the king is not in check
                return pieceMoves; //so we can make any move
            }
            else { //the king is in check
                return moves;
            }
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
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
                pos = new ChessPosition(row, col); //current position
                piece = getBoard().getPiece(pos); //current piece
                if (piece != null && piece.getTeamColor() != teamColor) { //enemy
                    for (ChessMove move : piece.pieceMoves(getBoard(), pos)) { //go through the piece's moves
                        if (move.equals(king)) { //the king is in the list
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
                pos = new ChessPosition(row, col); //current position
                piece = getBoard().getPiece(pos); //current piece
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
