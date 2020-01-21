package filters;

import chess.parser.Entity;
import chess.parser.Move;

public class RatedEntity {

    private Entity entity;
    private int centiPawsRate;

    public RatedEntity(Move entity, int centiPawsRate) {
        this.entity = entity;
        this.centiPawsRate = centiPawsRate;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getCentiPawsRate() {
        return centiPawsRate;
    }
}
