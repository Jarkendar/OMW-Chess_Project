package filters;

import chess.parser.Entity;
import chess.parser.Move;
import chess.parser.pgn.PGNGame;

import java.util.LinkedList;
import java.util.List;

/**
 * The filter searches moves in which player don't kill enemy after loss figure. Don't recaptures field.
 */
public class NoRecapture extends Filter {

    @Override
    public List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game) {
        LinkedList<Entity> potentialMoves = new LinkedList<>();
        boolean lastKilled = false;
        int[] lastYX = new int[]{-1, -1};
        for(int i = 1; i < moves.size(); i++){
            Move move = (Move)moves.get(i);
            if (killEnemy(move, game.getFens().get(i-1))){
                if (lastKilled && !isTheSameField(lastYX[1], lastYX[0], move.getToX(), move.getToY())){
                    potentialMoves.addLast(moves.get(i));
                }
                lastKilled = true;
                lastYX = new int[]{move.getToY(), move.getToX()};
            } else {
                lastKilled = false;
                lastYX = new int[]{-1, -1};
            }
        }

        return potentialMoves;
    }

    private boolean isTheSameField(int lastX, int lastY, int x, int y){
        return lastX == x && lastY == y;
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
