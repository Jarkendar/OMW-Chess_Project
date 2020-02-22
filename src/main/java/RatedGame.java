import chess.parser.Entity;
import chess.parser.Move;
import chess.parser.pgn.PGNGame;
import filters.RatedEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class RatedGame {

    private PGNGame games;
    private LinkedList<RatedEntity> ratedEntities;

    public RatedGame(PGNGame games, LinkedList<RatedEntity> ratedEntities) {
        this.games = games;
        this.ratedEntities = ratedEntities;
    }

    public PGNGame getGames() {
        return games;
    }

    public LinkedList<RatedEntity> getRatedEntities() {
        return ratedEntities;
    }

    Comparator<RatedEntity> compareByCp = Comparator.comparingInt(RatedEntity::getCentiPawsRate);

    public RatedEntity getBestMove(){

        if (!ratedEntities.isEmpty()) {
            ratedEntities.sort(compareByCp.reversed());
            return ratedEntities.get(0);
        }
        else return null;
    }
}
