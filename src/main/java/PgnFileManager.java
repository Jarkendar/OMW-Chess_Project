import chess.parser.pgn.PGNGame;
import chess.parser.pgn.PGNReader;

import java.io.*;
import java.util.LinkedList;
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

    public void savePgnFile(String outputPath, LinkedList<RatedGame> ratedGames){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(outputPath)))){
            for(RatedGame ratedGame: ratedGames) {
                bufferedWriter.write(createStringFromRatedGames(ratedGame));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String createStringFromRatedGames(RatedGame ratedGame){
        return ratedGame.toString();//todo
    }
}
