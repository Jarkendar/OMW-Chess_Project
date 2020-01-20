import chess.parser.pgn.PGNGame;
import configuration.CommandParser;
import configuration.Config;

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
        //todo save output file || (send to server -> receive result -> save output file)
    }
}
