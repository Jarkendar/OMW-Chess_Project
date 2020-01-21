import chess.parser.pgn.PGNGame;
import filters.RatedEntity;

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
}
