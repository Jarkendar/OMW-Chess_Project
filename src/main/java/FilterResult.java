import chess.parser.Entity;
import chess.parser.pgn.PGNGame;

import java.util.List;

public class FilterResult {

    private PGNGame pgnGame;
    private List<Entity> potentialMoves;

    public FilterResult(PGNGame pgnGame, List<Entity> potentialMoves) {
        this.pgnGame = pgnGame;
        this.potentialMoves = potentialMoves;
    }

    public PGNGame getPgnGame() {
        return pgnGame;
    }

    public void setPgnGame(PGNGame pgnGame) {
        this.pgnGame = pgnGame;
    }

    public List<Entity> getPotentialMoves() {
        return potentialMoves;
    }

    public void setPotentialMoves(List<Entity> potentialMoves) {
        this.potentialMoves = potentialMoves;
    }
}
