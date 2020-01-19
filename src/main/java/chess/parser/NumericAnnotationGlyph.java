package chess.parser;

//import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stanisław Kabaciński.
 */

public class NumericAnnotationGlyph {

    private static final HashMap<String, Integer> stringToInt = new HashMap<>();
    private static final HashMap<Integer, String> intToString = new HashMap<>();

    static {
        intToString.put(1, "!");
        intToString.put(2, "?");
        intToString.put(3, "‼");
        intToString.put(4, "⁇");
        intToString.put(5, "⁉");
        intToString.put(6, "⁈");
        intToString.put(7, "□");
        intToString.put(10, "=");
        intToString.put(13, "∞");
        intToString.put(14, "⩲");
        intToString.put(15, "⩱");
        intToString.put(16, "±");
        intToString.put(17, "∓");
        intToString.put(18, "+-");
        intToString.put(19, "-+");
        intToString.put(22, "⨀");
        intToString.put(23, "⨀");
        intToString.put(32, "⟳");
        intToString.put(33, "⟳");
        intToString.put(36, "→");
        intToString.put(37, "→");
        intToString.put(40, "↑");
        intToString.put(41, "↑");
        intToString.put(132, "⇆");
        intToString.put(133, "⇆");

        stringToInt.put("!", 1);
        stringToInt.put("?", 2);
        stringToInt.put("‼", 3);
        stringToInt.put("⁇", 4);
        stringToInt.put("⁉", 5);
        stringToInt.put("⁈", 6);
        stringToInt.put("□", 7);
        stringToInt.put("=", 10);
        stringToInt.put("∞", 13);
        stringToInt.put("⩲", 14);
        stringToInt.put("⩱", 15);
        stringToInt.put("±", 16);
        stringToInt.put("∓", 17);
        stringToInt.put("+-", 18);
        stringToInt.put("-+", 19);
        stringToInt.put("⨀", 22);
        stringToInt.put("⟳", 32);
        stringToInt.put("→", 36);
        stringToInt.put("↑", 40);
        stringToInt.put("⇆", 132);
    }

    public final int no;
    public final String string;

    public NumericAnnotationGlyph(int no, String string) {
        this.no = no;
        this.string = string;
    }

    public static List<NumericAnnotationGlyph> getGlyphs() {
        List<NumericAnnotationGlyph> glyphs = new ArrayList<>();
        for (int key : intToString.keySet()) {
            glyphs.add(new NumericAnnotationGlyph(key, intToString.get(key)));
        }
        Collections.sort(glyphs, (o1, o2) -> o1.no > o2.no ? 1 : o1.no == o2.no ? 0 : -1);
        return glyphs;
    }

    public static String getAsString(int no) {
        if (no == 0) return "";
        if (intToString.containsKey(no)) {
            return intToString.get(no);
        } else {
            return "$" + no;
        }
    }

    //@NonNull
    public static String getAsString(int[] nos) {
        if (nos == null || nos.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int n : nos) {
            builder.append(getAsString(n));
        }
        return builder.toString();
    }

    public static int getAsInt(String s) {
        if (stringToInt.containsKey(s)) {
            return stringToInt.get(s);
        }
        try {
            return Integer.parseInt(s.substring(1));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
