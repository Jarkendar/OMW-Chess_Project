package filters.filter_classes;

import chess.parser.Entity;
import chess.parser.Move;
import chess.parser.pgn.PGNGame;

import java.util.LinkedList;
import java.util.List;

/**
 * The filter looks for moves where figure with greater power is beats by figure (or pawn) with smaller power.
 */
public class Sacrifice extends Filter {

    @Override
    public List<Entity> searchPotentialMoves(List<Entity> moves, PGNGame game) {
        LinkedList<Entity> potentialMoves = new LinkedList<>();

        boolean lastFigure = false;
        int lastFigurePiece = -1;
        int[] lastFigurePositionYX = new int[]{-1, -1};
        for (int i = 0; i<moves.size(); i++){
            Move move = (Move) moves.get(i);
            if (lastFigure
                    && killEnemy(move, game.getFens().get(i-1))
                    && isTheSameField(lastFigurePositionYX[1], lastFigurePositionYX[0], move.getToX(), move.getToY())
                    && lastFigurePiece > move.getPiece()){
                potentialMoves.addLast(moves.get(i));
            }

            if (move.getPiece() > 2){
                lastFigure = true;
                lastFigurePiece = move.getPiece();
                lastFigurePositionYX = new int[]{move.getToY(), move.getToY()};
            } else {
                lastFigure = false;
                lastFigurePiece = -1;
                lastFigurePositionYX = new int[]{-1, -1};
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
