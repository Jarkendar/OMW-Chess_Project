package filters.filter_classes;

import chess.parser.Entity;
import chess.parser.Move;
import chess.parser.pgn.PGNGame;

import java.util.LinkedList;
import java.util.List;

/**
 * The filter looks for all rooks.
 */
public class RookFilter extends Filter {

    @Override
    public List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game) {
        LinkedList<Entity> potentialMoves = new LinkedList<>();

        for(int i = 1; i<moves.size(); i++){
            if (((Move) moves.get(i)).getPiece() == 1 || ((Move) moves.get(i)).getPiece() == 2) {
                ((Move) moves.get(i)).setBoardBefore(game.getFens().get(i-1));
                ((Move) moves.get(i)).setBoardAfter(game.getFens().get(i));
                potentialMoves.addLast(moves.get(i));
            }
        }

        return potentialMoves;
    }
}
