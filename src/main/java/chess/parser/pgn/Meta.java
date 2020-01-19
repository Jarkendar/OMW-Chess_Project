package chess.parser.pgn;

/**
 * Created by Stanisław Kabaciński.
 */

public class Meta {

    private String key;
    private String value;
    private boolean required;

    public Meta() {

    }

    public Meta(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public Meta(String key, String value) {
        this(key, value, false);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
