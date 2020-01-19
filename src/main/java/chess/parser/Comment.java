package chess.parser;

/**
 * Created by Stanisław Kabaciński.
 */

public class Comment extends Entity {

    private String value;

    public Comment() {
    }

    public Comment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
