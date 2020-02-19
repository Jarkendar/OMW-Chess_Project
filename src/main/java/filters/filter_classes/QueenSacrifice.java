package filters.filter_classes;

import chess.parser.Entity;
import chess.parser.Move;
import chess.parser.pgn.PGNGame;

import java.util.LinkedList;
import java.util.List;

/**
 * The filter looking for all queen sacrifice. Queen sacrifice is all lost queen, doesn't matter queen beats or no beats.
 */
public class QueenSacrifice extends Filter {

    public List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game) {
        LinkedList<Entity> potentialMove = new LinkedList<>();

        int[] lastQueenPositionYX = new int[]{-1, -1};
        boolean lastQueenMoved = false;
        for(int i = 1; i<moves.size(); i++){
            Move move = (Move) moves.get(i);
            if (lastQueenMoved
                    && killEnemy(move, game.getFens().get(i-1))
                    && isTheSameField(lastQueenPositionYX[1], lastQueenPositionYX[0], move.getToX(), move.getToY() )){
                potentialMove.addLast(moves.get(i));
            }
            if (isQueen(move)){
                lastQueenMoved = true;
                lastQueenPositionYX = new int[]{move.getToY(), move.getToX()};
            } else {
                lastQueenMoved = false;
                lastQueenPositionYX = new int[]{-1, -1};
            }
        }

        return potentialMove;
    }

    private boolean isTheSameField(int lastX, int lastY, int x, int y){
        return lastX == x && lastY == y;
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
