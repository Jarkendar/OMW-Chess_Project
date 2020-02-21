import chess.parser.*;
import chess.parser.pgn.Meta;
import chess.parser.pgn.PGNGame;
import chess.parser.pgn.PGNReader;
import configuration.Header;
import filters.RatedEntity;

import java.io.*;
import java.util.ArrayList;
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
        games = filterMovesFromGames(games);
        games = setMovesPositions(games);
        for (PGNGame game : games)
        	filterMeta(game, header);
        return games;
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
	
    public void savePgnFile(String outputPath, int minCp, LinkedList<RatedGame> ratedGames){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(outputPath)))){
            for(RatedGame ratedGame: ratedGames) {
                bufferedWriter.write(createStringFromRatedGames(ratedGame, minCp));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            System.out.println("Output file generated successfully");
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

    private String createStringFromRatedGames(RatedGame ratedGame, int minCp){
        String output = "";
        RatedEntity bestMove = ratedGame.getBestMove();
        if (bestMove != null) {
        	List<Meta> metas = ratedGame.getGames().getMeta();
        	for (Meta m : metas) {
            	if(m.isRequired())
            		output += '[' + m.getKey() + " \"" + m.getValue() + "\"]\n";
            }
            //todo: czy byl uzyty w grze {G}
            String fen = bestMove.getBoardBefore();
        	ChessGame chessGame = new ChessGameImpl();
            int moveNb = ratedGame.getGames().getFens().indexOf(fen);
            List<Entity> movesToPlay = ratedGame.getGames().getEntities().subList(0, moveNb);
            PossibleMovesProviderImpl possibleMovesProvider = new PossibleMovesProviderImpl(chessGame);
            SANMoveMaker sanMoveMaker = new SANMoveMaker(chessGame, possibleMovesProvider);
            sanMoveMaker.processMoves(movesToPlay);
            
            String originalMove = ((Move)(ratedGame.getGames().getEntities().get(moveNb))).getPANRepresentation();
            output += "[FEN \""+ fen + "\"]\n";
            output += ((Move) bestMove.getEntity()).getSan(); //todo: Zmienic na poprawna notacje
            output += " {" + bestMove.getCentiPawsRate() + "} ";
            if(originalMove.equals(((Move) bestMove.getEntity()).getPANRepresentation()))
				output+="{G} ";
			ArrayList<String> variationsPv = bestMove.getVariationsPv();
            ArrayList<Integer> variationsCp = bestMove.getVariationsCp();
            for (int k = 0; k < Math.min(variationsCp.size(), 2); k++){
                String goodMovePv = variationsPv.get(k);
                String sanRepresentation = SANHelper.replaceSymbolsWithLetters(SANHelper.getSanFromLAN(goodMovePv, chessGame));
                chessGame.undoLastMove();
                Integer goodMoveCp = variationsCp.get(k);
                if( bestMove.getCentiPawsRate() - goodMoveCp >= minCp ){
                    output += "(" + sanRepresentation; //todo: zamienić na poprawną notację
                    if(originalMove.equals(goodMovePv))
                    	output += "{" + goodMoveCp.toString() + "}{G})";
                    else
                    	output += "{" + goodMoveCp.toString() + "})";
                }
            }
            output += "\n";
        }

        return output;
    }
}
