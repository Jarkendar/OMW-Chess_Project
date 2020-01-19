package chess.parser;

import java.util.List;

/**
 * Created by Stanisław Kabaciński.
 */

public class ChessGameUtils {

    public static String getChessGameMovesAsPAN(ChessGame chessGame) {
        List<Move> moves = chessGame.getMoveList();
        String pan = "";
        for (Move m : moves) {
            pan += m.getPANRepresentation() + " ";
        }
        return pan;
    }

}
