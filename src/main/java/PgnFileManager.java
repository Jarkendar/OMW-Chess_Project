import chess.parser.*;
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
        games = setMovesPositions(games);
        return filterMovesFromGames(games);
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

    private List<PGNGame> filterMovesFromGames(List<PGNGame> games){
        for(PGNGame game: games){
            game.setEntities(filterRealMoves(game.getEntities()));
        }
        return games;
    }

    /**
     * Filter all variants, comments, gamebegin. Leave only moves.
     * @param entities list of entity
     * @return filtered entity list only moves contains
     */
    private List<Entity> filterRealMoves(List<Entity> entities){
        LinkedList<Entity> newEntities = new LinkedList<>();
        int variantDepth = 0;
        for (Entity entity: entities){
            if (entity instanceof VariantBegin){
                variantDepth++;
            }
            if (variantDepth == 0 && entity instanceof Move){
                newEntities.addLast(entity);
            } else if (entity instanceof VariantEnd){
                 variantDepth--;
            }
        }
        return newEntities;
    }

    private List<PGNGame> setMovesPositions(List<PGNGame> games){
        if (games != null) {
            SANMoveMaker sanMoveMaker;
            for (PGNGame pgnGame : games) {
                ChessGame chessGame = new ChessGameImpl();
                PossibleMovesProviderImpl possibleMovesProvider = new PossibleMovesProviderImpl(chessGame);
                sanMoveMaker = new SANMoveMaker(chessGame, possibleMovesProvider);
                sanMoveMaker.processMoves(pgnGame.getEntities());
            }
        }
        return games;
    }



    private String createStringFromRatedGames(RatedGame ratedGame){
        return ratedGame.toString();//todo
    }
}
