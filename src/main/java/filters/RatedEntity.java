package filters;

import chess.parser.Entity;
import chess.parser.Move;

import java.util.ArrayList;

public class RatedEntity {

    private Entity entity;
    private int centiPawsRate;
    private String boardBefore;
    private String boardAfter;
    private ArrayList<String> variationsPv;
    private ArrayList<Integer> variationsCp;

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
	
	public ArrayList<String> getVariationsPv()
	{
		return variationsPv;
	}
	
	public ArrayList<Integer> getVariationsCp()
	{
		return variationsCp;
	}
	
	public void setVariationsPv(ArrayList<String> pv)
	{
		this.variationsPv = pv;
	}
	
	public void setVariationsCp(ArrayList<Integer> cp)
	{
		this.variationsCp = cp;
	}
}
