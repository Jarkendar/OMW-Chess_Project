import chess.parser.pgn.PGNGame;
import chess.parser.pgn.PGNReader;

import java.io.*;
import java.util.List;

public class PgnFileManager {

    public List<PGNGame> parsePgnFile(String inputPath){
        List<PGNGame> games;
        try (Reader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            PGNReader pgnReader = new PGNReader();
            games = pgnReader.read(reader);
        } catch (IOException e) {
            return null;
        }
        return games;
    }
}
