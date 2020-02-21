package filters.filter_classes;

import chess.parser.Entity;
import chess.parser.Move;
import chess.parser.pgn.PGNGame;

import java.util.LinkedList;
import java.util.List;

/**
 * The filters look for all queen beats.
 */
public class QueenBeat extends Filter {

    @Override
    public List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game) {
        LinkedList<Entity> potentialMove = new LinkedList<>();

        for(int i = 1; i<moves.size(); i++){
            if (killEnemy((Move) moves.get(i), game.getFens().get(i-1)) && isQueen((Move) moves.get(i))){
                ((Move) moves.get(i)).setBoardBefore(game.getFens().get(i-1));
                ((Move) moves.get(i)).setBoardAfter(game.getFens().get(i));
                potentialMove.addLast(moves.get(i));
            }
        }

        return potentialMove;
    }

    private boolean isQueen(Move move){
        return move.getPiece() == 5;
    }

    private boolean killEnemy(Move move, String fen){
        char[] targetRow = fen.split(" ")[0].split("/")[getReverseIndex(move.getToY())].toCharArray();

        boolean[] row = new boolean[8];

        for (int i = 0, x = 0; i<targetRow.length; i++){
            if (isShift(targetRow[i])){
                x += Integer.parseInt(String.valueOf(targetRow[i]));
            } else {
                row[x] = true;
                x++;
            }
        }

        return row[move.getToX()];
    }

    private int getReverseIndex(int index){
        return 7-index;
    }

    private boolean isShift(char c){
        return String.valueOf(c).matches("[0-9]");
    }
}
