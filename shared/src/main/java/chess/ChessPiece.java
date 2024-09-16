package chess;

import java.util.ArrayList;
import java.util.Collection;

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
        //make temporary postion
        for (int col = myPosition.getColumn(); col <= myPosition.getColumn() + 1; col++) {
            for (int row = myPosition.getRow(); row <= myPosition.getRow() + 1; row++) {
                if (pType == PieceType.KING) {}
                else if (pType == PieceType.QUEEN) {}
                else if (pType == PieceType.BISHOP) {}
                else if (pType == PieceType.KNIGHT) {}
                else if (pType == PieceType.ROOK) {}
                else if (pType == PieceType.PAWN) {}
            }
        }
        //if same team is blocking don't continue to or past it
        //if different team is blocking capture and take their place
        return new ArrayList<>();
    }
}
