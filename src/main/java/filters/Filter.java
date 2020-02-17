package filters;

import chess.parser.Entity;

import java.util.List;

public abstract class Filter {

    public abstract List<Entity> searchPotentialMoves(List<Entity> moves);
}
