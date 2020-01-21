import chess.parser.pgn.PGNGame;

import java.util.LinkedList;
import java.util.List;

public class FilterManager {

    private Filter[] filters;

    public FilterManager() {
        this.filters = createFiltersList(new int[]{1, 2, 3});
    }

    public FilterManager(int[] filtersKey) {
        this.filters = createFiltersList(filtersKey);
    }

    private Filter[] createFiltersList(int[] filtersKey) {
        LinkedList<Filter> filters = new LinkedList<>();

        for (int key : filtersKey) {
            switch (key) {
                case 1: {
                    // filters.addLast(new Filters());
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    break;
                }
            }
        }
        return filters.toArray(new Filter[0]);
    }

    public List<FilterResult> filterGames(List<PGNGame> games) {
        return null;
    }
}
