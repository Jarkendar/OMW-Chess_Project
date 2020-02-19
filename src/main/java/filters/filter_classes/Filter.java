package filters.filter_classes;

import chess.parser.Entity;
import chess.parser.pgn.PGNGame;

import java.util.List;

/**
 * Abstract class filter with methods to inherit.
 */
public abstract class Filter {

    public abstract List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game);
}
