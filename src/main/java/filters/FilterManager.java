package filters;

import chess.parser.Entity;
import chess.parser.pgn.PGNGame;

import java.util.ArrayList;
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
                    filters.addLast(new NoRecapture());
                    break;
                }
                case 2: {
                    filters.addLast(new FigureBeat());
                    break;
                }
                case 3: {
                    filters.addLast(new QueenBeat());
                    break;
                }
            }
        }
        return filters.toArray(new Filter[0]);
    }

    public LinkedList<FilterResult> filterGames(List<PGNGame> games) {
        LinkedList<FilterResult> filterResults = new LinkedList<>();
        for (PGNGame game: games){
            filterResults.addLast(filterGame(game));
        }
        return filterResults;
    }

    private FilterResult filterGame(PGNGame pgnGame){
        ArrayList<List<Entity>> arrayOfFilteredList = new ArrayList<>(filters.length);
        for (Filter filter: filters){
            arrayOfFilteredList.add(filter.searchPotentialMoves(pgnGame.getEntities(), pgnGame));
        }

        LinkedList<Entity> intersectMoves = intersectFiltersPotentialMoves(arrayOfFilteredList);

        return new FilterResult(pgnGame, intersectMoves);
    }

    private LinkedList<Entity> intersectFiltersPotentialMoves(ArrayList<List<Entity>> arrayList){
        LinkedList<Pair<Entity, Integer>> pairs = new LinkedList<>();

        for (List<Entity> filterEntity: arrayList){
            for (Entity entity: filterEntity){
                if (containEntityInPair(pairs, entity)){
                    Pair<Entity, Integer> pair = getPairWithEntity(pairs, entity);
                    if (pair != null) {
                        pair.setSecond(pair.getSecond() + 1);
                    }
                } else {
                    pairs.addFirst(new Pair<>(entity, 1));
                }
            }
        }

        LinkedList<Entity> intersectPairs = new LinkedList<>();
        for (Pair<Entity, Integer> pair: pairs){
            if (pair.getSecond() == filters.length){
                intersectPairs.addFirst(pair.getFirst());
            }
        }
        return intersectPairs;
    }

    private boolean containEntityInPair(LinkedList<Pair<Entity, Integer>> pairs, Entity element){
        for (Pair<Entity, Integer> pair: pairs){
            if (pair.getFirst() == element){
                return true;
            }
        }
        return false;
    }

    private Pair<Entity, Integer> getPairWithEntity(LinkedList<Pair<Entity, Integer>> pairs, Entity element){
        for (Pair<Entity, Integer> pair: pairs){
            if (pair.getFirst() == element){
                return pair;
            }
        }
        return null;
    }
}
