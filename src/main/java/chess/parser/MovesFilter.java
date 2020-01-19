package chess.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanisław Kabaciński.
 */

public class MovesFilter {

    public static List<Move> filter(List<Entity> entities, int toPosition) {
        if (entities.size() <= 1) {
            return new ArrayList<>();
        }
        List<Move> out = new ArrayList<>();
        List<RevertedMove> reverted = new ArrayList<>();

        for (int i = 0; i <= toPosition; i++) {
            Entity e = entities.get(i);
            if (e instanceof Move) {
                Move m = (Move) e;
                out.add(m);
            } else if (e instanceof VariantBegin) {
                int pos = out.size() - 1;
                Move prev = out.remove(pos);
                reverted.add(new RevertedMove(prev, pos));
            } else if (e instanceof VariantEnd) {
                RevertedMove rMove = reverted.remove(reverted.size() - 1);
                out.add(rMove.position, rMove.move);
                for (int j = out.size() - 1; j > rMove.position; j--) {
                    out.remove(j);
                }
            }
        }

        return out;
    }

    public static List<Move> filter(List<Entity> entities) {
        return filter(entities, entities.size() - 1);
    }

    public static class RevertedMove {

        public final Move move;

        public final int position;

        public RevertedMove(Move move, int position) {
            this.move = move;
            this.position = position;
        }
    }

}
