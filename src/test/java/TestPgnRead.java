import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import chess.parser.ChessGame;
import chess.parser.ChessGameImpl;
import chess.parser.PossibleMovesProviderImpl;
import chess.parser.SANMoveMaker;
import chess.parser.pgn.PGNGame;
import chess.parser.pgn.PGNReader;

/**
 * Test for reading PGN file.
 * 
 * @author Marcin Szeląg
 */
public class TestPgnRead {

	/**
	 * Application entry point
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		TestPgnRead testPgnRead = new TestPgnRead();

		List<PGNGame> listOfGames = testPgnRead.getGames("src/main/resources/test_examples/largerPGN.pgn");
		
		SANMoveMaker sanMoveMaker;

		if (listOfGames != null) {
			System.out.println("PGN read success!"); //put breakpoint here to inspect *listOfGames* variable
			for (PGNGame pgnGame : listOfGames) {
				ChessGame chessGame = new ChessGameImpl();
				PossibleMovesProviderImpl possibleMovesProvider = new PossibleMovesProviderImpl(chessGame);
				sanMoveMaker = new SANMoveMaker(chessGame, possibleMovesProvider);
				sanMoveMaker.processMoves(pgnGame.getEntities());
			}
			//System.out.println(Arrays.toString(((Move)listOfGames.get(0).getEntities().get(1)).getNags()));
			System.out.println("End of test."); //insert breakpoint here and inspect listOfGames
		}

	}

	/**
	 * Read a list of PGN chess games from disk file with given path.
	 * 
	 * @param path disk path to a PGN file
	 * @return list of PGN chess games read from file
	 */
    public List<PGNGame> getGames(String path) {
        List<PGNGame> games;
        Reader reader;
        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));
            PGNReader pgnReader = new PGNReader();
            games = pgnReader.read(reader);
            reader.close();
        } catch (IOException e) {
            return null;
        }

        return games;
    }

}
