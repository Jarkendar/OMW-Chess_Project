package filters;

import chess.parser.Entity;
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

    public LinkedList<FilterResult> filterGames(List<PGNGame> games) {
        LinkedList<FilterResult> filterResults = new LinkedList<>();
        for (PGNGame game: games){
            LinkedList<List<Entity>> filtersOutput = new LinkedList<>();
            for (int i = 0; i<filters.length; i++){
                filtersOutput.addLast(filters[i].searchPotentialMoves(game.getEntities()));
            }
            filterResults.addLast(new FilterResult(game, joinFilterOutputList(filtersOutput)));
        }
        return filterResults;
    }

    private LinkedList<Entity> joinFilterOutputList(LinkedList<List<Entity>> filtersOutput){
        //todo maybe voting? on moves all filters are par
        return new LinkedList<>();
    }
}
