package filters;

import chess.parser.Entity;
import chess.parser.Move;

public class RatedEntity {

    private Entity entity;
    private int centiPawsRate;
    private String boardBefore;
    private String boardAfter;

    public RatedEntity(Move entity, int centiPawsRate, String board) {
        this.entity = entity;
        this.centiPawsRate = centiPawsRate;
        this.boardBefore = board;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getCentiPawsRate() {
        return centiPawsRate;
    }

    public String getBoardBefore()
    {
        return boardBefore;
    }

    public String getBoardAfter()
    {
        return boardAfter;
    }
}
