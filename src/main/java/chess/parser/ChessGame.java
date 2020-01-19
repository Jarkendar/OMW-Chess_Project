package chess.parser;

import java.util.List;

/**
 * Created by Stanisław Kabaciński.
 */
public interface ChessGame {
    void makeMove(Move move);

    void makeMove(Move move, int fromX, int fromY, int toX, int toY);

    int[][] getBoard();

    int getNextMovePlayerColor();

    boolean isBlackQSRookMoved();

    boolean isBlackKSRookMoved();

    boolean isBlackKingMoved();

    boolean isWhiteKingMoved();

    boolean isWhiteKSRookMoved();

    boolean isWhiteQSRookMoved();

    boolean wasQSRookMoved(int color);

    boolean wasKSRookMoved(int color);

    boolean wasKingMoved(int color);

    boolean undoLastMove();

    void setLocked(boolean locked);

    boolean isLocked();

    void reset();

    List<Move> getMoveList();

    ChessGame clone();
}
