package chess.parser.pgn;

import java.util.ArrayList;
import java.util.List;

import chess.parser.Entity;

/**
 * Created by Stanisław Kabaciński.
 */

public class PGNGame {

    private int id;

    private List<Meta> meta;

    private List<Entity> entities;

    public PGNGame(List<Meta> meta, List<Entity> entities) {
        this.meta = meta;
        this.entities = entities;
    }

    public PGNGame() {
        meta = new ArrayList<>();
        entities = new ArrayList<>();
    }

    public List<Meta> getMeta() {
        //return MetadataOrderer.order(meta);
    	return meta;
    }

    public void setMeta(List<Meta> meta) {
        this.meta = meta;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
