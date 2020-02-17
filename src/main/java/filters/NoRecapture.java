package filters;

import chess.parser.Entity;

import java.util.LinkedList;
import java.util.List;

public class NoRecapture extends Filter {

    @Override
    public List<Entity> searchPotentialMoves(List<Entity> moves) {
        LinkedList<Entity> potentialMoves = new LinkedList<>(moves);

        return potentialMoves;
    }
}
