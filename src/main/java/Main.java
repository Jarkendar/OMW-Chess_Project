import chess.parser.pgn.PGNGame;
import configuration.CommandParser;
import configuration.Config;
import filters.FilterManager;
import filters.FilterResult;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        //todo parse command args
        //test args src/main/resources/examples/<fileName> output.pgn
        Config config = new CommandParser().parseCommandArguments(args);

        //todo read file
        //todo parse file
        PgnFileManager pgnFileManager = new PgnFileManager();
        List<PGNGame> pgnGameList = pgnFileManager.parsePgnFile(config.getInputPath());

        //todo filters
        FilterManager filterManager = new FilterManager();
        List<FilterResult> filterResults = filterManager.filterGames(pgnGameList);

        //todo save output file || (send to server -> receive result -> save output file)
        ServerConnector serverConnector = new ServerConnector(config.getPathToServerConfig(), config.getMinEngineSearch());
        serverConnector.ratedGames(filterResults);

        //todo save output


    }
}
