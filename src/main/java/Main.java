import chess.parser.pgn.PGNGame;
import configuration.CommandParser;
import configuration.Config;
import filters.FilterManager;
import filters.FilterResult;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //test args: src/main/resources/examples/<fileName> output.pgn
        //extends args: -h all -cp 100 -d 10 -n 3 -e src/main -f 1,2,3,4 src/main/resources/examples/<fileName> output.pgn
        Config config = new CommandParser().parseCommandArguments(args);

        //todo read file
        //todo parse file
        PgnFileManager pgnFileManager = new PgnFileManager();
        List<PGNGame> pgnGameList = pgnFileManager.parsePgnFile(config.getInputPath(), config.getHeader());

        FilterManager filterManager = new FilterManager(config.getFiltersNumber());
        LinkedList<FilterResult> filterResults = filterManager.filterGames(pgnGameList);

        ServerConnector serverConnector = new ServerConnector(config.getPathToServerConfig(), config.getMinEngineSearch());
        LinkedList<RatedGame> ratedGames = serverConnector.ratedGames(filterResults);

        //todo save output
        pgnFileManager.savePgnFile(config.getOutputPath(), config.getMinCentiPawns(), ratedGames);

        serverConnector.closeConnection();
    }
}
