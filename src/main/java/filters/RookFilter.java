package filters;

import chess.parser.Entity;
import chess.parser.Move;
import chess.parser.pgn.PGNGame;

import java.util.LinkedList;
import java.util.List;

public class RookFilter extends Filter {

    @Override
    public List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game) {
        LinkedList<Entity> potentialMoves = new LinkedList<>();

        for (Entity move : moves) {
            if (((Move) move).getPiece() == 1 || ((Move) move).getPiece() == 2) {
                potentialMoves.addLast(move);
            }
        }

        return potentialMoves;
    }
}
