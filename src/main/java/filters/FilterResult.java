package filters;

import chess.parser.Entity;
import chess.parser.pgn.PGNGame;

import java.util.LinkedList;

public class FilterResult {

    private PGNGame pgnGame;
    private LinkedList<Entity> potentialMoves;
    private LinkedList<RatedEntity> ratedEntities;

    public FilterResult(PGNGame pgnGame, LinkedList<Entity> potentialMoves) {
        this.pgnGame = pgnGame;
        this.potentialMoves = potentialMoves;
        ratedEntities = new LinkedList<>();
    }

    public void addRatedEntity(RatedEntity ratedEntity){
        ratedEntities.addLast(ratedEntity);
    }

    public LinkedList<RatedEntity> getRatedEntities() {
        return ratedEntities;
    }

    public PGNGame getPgnGame() {
        return pgnGame;
    }

    public void setPgnGame(PGNGame pgnGame) {
        this.pgnGame = pgnGame;
    }

    public LinkedList<Entity> getPotentialMoves() {
        return potentialMoves;
    }

    public void setPotentialMoves(LinkedList<Entity> potentialMoves) {
        this.potentialMoves = potentialMoves;
    }
}
