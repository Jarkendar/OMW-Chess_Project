import chess.parser.*;
import chess.parser.pgn.Meta;
import chess.parser.pgn.PGNGame;
import chess.parser.pgn.PGNReader;
import configuration.Header;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PgnFileManager {

    public List<PGNGame> parsePgnFile(String inputPath, Header header){
        List<PGNGame> games;
        try (Reader reader = new BufferedReader(new FileReader(new File(inputPath)))) {
            PGNReader pgnReader = new PGNReader();
            games = pgnReader.read(reader);
        } catch (IOException e) {
            return null;
        }
        games = setMovesPositions(games);
        for (PGNGame game : games)
        	filterMeta(game, header);
        System.out.println(games.get(0).getEntities().size());
        return filterMovesFromGames(games);
    }

	private void filterMeta(PGNGame game, Header header) {
		if(header == Header.ALL)
			for(Meta meta : game.getMeta())
				meta.setRequired(true);

		if(header == Header.CONCISE)
			for(Meta meta : game.getMeta())
				if(meta.getKey().equals("Site") || meta.getKey().equals("Date")
						|| meta.getKey().equals("White") || meta.getKey().equals("Black"))
					meta.setRequired(true);
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
                List<String> fens = sanMoveMaker.processMoves(pgnGame.getEntities());
                pgnGame.setFens(fens);
            }
        }
        return games;
    }



    private String createStringFromRatedGames(RatedGame ratedGame){
        return ratedGame.toString();//todo
    }
}
