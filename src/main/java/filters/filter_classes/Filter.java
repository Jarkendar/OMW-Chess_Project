package filters.filter_classes;

import chess.parser.Entity;
import chess.parser.pgn.PGNGame;

import java.util.List;

public abstract class Filter {

    public abstract List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game);
}
