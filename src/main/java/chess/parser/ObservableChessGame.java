package chess.parser;

import java.util.List;
import java.util.Observable;

/**
 * Created by Stanisław Kabaciński.
 */

public class ObservableChessGame extends Observable implements ChessGame {
    public static final String ACTION_NEW_GAME = "new_game";
    public static final String ACTION_NEW_MOVE = "new_move";
    public static final String ACTION_UNDO = "undo";

    private ChessGame chessGame;

    public ObservableChessGame() {
        this(new ChessGameImpl());
    }

    public ObservableChessGame(ChessGame chessGame) {
        setChessGame(chessGame);
    }

    @Override
    public void makeMove(Move move) {
        chessGame.makeMove(move);
        setChanged();
        notifyObservers(ACTION_NEW_MOVE);
    }

    @Override
    public void makeMove(Move move, int fromX, int fromY, int toX, int toY) {
        chessGame.makeMove(move, fromX, fromY, toX, toY);
    }

    @Override
    public int[][] getBoard() {
        return chessGame.getBoard();
    }

    @Override
    public int getNextMovePlayerColor() {
        return chessGame.getNextMovePlayerColor();
    }

    @Override
    public boolean isBlackQSRookMoved() {
        return chessGame.isBlackQSRookMoved();
    }

    @Override
    public boolean isBlackKSRookMoved() {
        return chessGame.isBlackKSRookMoved();
    }

    @Override
    public boolean isBlackKingMoved() {
        return chessGame.isBlackKingMoved();
    }

    @Override
    public boolean isWhiteKingMoved() {
        return chessGame.isWhiteKingMoved();
    }

    @Override
    public boolean isWhiteKSRookMoved() {
        return chessGame.isWhiteKSRookMoved();
    }

    @Override
    public boolean isWhiteQSRookMoved() {
        return chessGame.isWhiteQSRookMoved();
    }

    @Override
    public boolean wasQSRookMoved(int color) {
        return chessGame.wasQSRookMoved(color);
    }

    @Override
    public boolean wasKSRookMoved(int color) {
        return chessGame.wasKSRookMoved(color);
    }

    @Override
    public boolean wasKingMoved(int color) {
        return chessGame.wasKingMoved(color);
    }

    @Override
    public boolean undoLastMove() {
        boolean flag = chessGame.undoLastMove();
        if (flag) {
            setChanged();
            notifyObservers(ACTION_UNDO);
        }
        return flag;
    }

    @Override
    public void setLocked(boolean locked) {
        chessGame.setLocked(locked);
    }

    @Override
    public boolean isLocked() {
        return chessGame.isLocked();
    }

    @Override
    public void reset() {
        chessGame.reset();
        this.setChanged();
        this.notifyObservers(ACTION_NEW_GAME);
    }

    @Override
    public List<Move> getMoveList() {
        return chessGame.getMoveList();
    }

    @Override
    public ChessGame clone() {
        return chessGame.clone();
    }

    public ChessGame getChessGame() {
        return chessGame;
    }

    public void setChessGame(ChessGame chessGame) {
        this.chessGame = chessGame;
        this.setChanged();
        this.notifyObservers(ACTION_NEW_GAME);
    }
}
