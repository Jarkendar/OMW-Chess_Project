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

    Comparator<RatedEntity> compareByCp = new Comparator<RatedEntity>() {
        @Override
        public int compare(RatedEntity o1, RatedEntity o2) {
            return o1.getCentiPawsRate() - o2.getCentiPawsRate();
        }
    };

    public RatedEntity getBestMove(){

        if (!ratedEntities.isEmpty()) {
            Collections.sort(ratedEntities, compareByCp.reversed());
            return ratedEntities.get(0);
        }
        else return null;
    }
}
