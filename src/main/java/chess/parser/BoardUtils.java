package chess.parser;

/**
 * Created by Stanisław Kabaciński.
 */

public class BoardUtils implements PieceColor{

    public static void initBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }
        for (int i = 0, ii = 8; i < ii; i++) {
            board[i][1] = WHITE + Piece.PAWN;
            board[i][6] = BLACK + Piece.PAWN;
        }
        board[0][0] = WHITE + Piece.QS_ROOK;
        board[7][0] = WHITE + Piece.KS_ROOK;
        board[1][0] = board[6][0] = WHITE + Piece.KNIGHT;
        board[2][0] = board[5][0] = WHITE + Piece.BISHOP;
        board[3][0] = WHITE + Piece.QUEEN;
        board[4][0] = WHITE + Piece.KING;

        board[0][7] = BLACK + Piece.QS_ROOK;
        board[7][7] = BLACK + Piece.KS_ROOK;
        board[1][7] = board[6][7] = BLACK + Piece.KNIGHT;
        board[2][7] = board[5][7] = BLACK + Piece.BISHOP;
        board[3][7] = BLACK + Piece.QUEEN;
        board[4][7] = BLACK + Piece.KING;
    }
    
    public static void initBoard(int[][] board, String fen) {
    	String[] cols = fen.split("/");
    	cols[7] = cols[7].split(" ")[0];
        for (int i = 0; i < board.length; i++) {
        	int j = 7;
        	char[] line = cols[i].toCharArray();
        	for(int k=0; k<line.length; k++) {
        		char ch = line[k];
        		if(Character.isDigit(ch)) {
        			int emptySpace = Integer.parseInt(ch+"");
        			for(int l = 0; l<emptySpace; l++) {
        				board[j][i]=0;
        				j--;
        			}
        		}
        		else {
        			int pieceCode = 0;
        			switch(Character.toLowerCase(ch)) {
        			case 'k':
        				pieceCode = Piece.KING;
        				break;
        			case 'q':
        				pieceCode = Piece.QUEEN;
        				break;
        			case 'b':
        				pieceCode = Piece.BISHOP;
        				break;
        			case 'n':
        				pieceCode = Piece.KNIGHT;
        				break;
        			case 'r':
        				pieceCode = Piece.KS_ROOK;
        				break;
        			case 'p':
        				pieceCode = Piece.PAWN;
        				break;
        			default:
        				break;
        			}
        			if(Character.isUpperCase(ch))
        				pieceCode+=PieceColor.WHITE;
        			else
        				pieceCode+=PieceColor.BLACK;
        			board[j][i]=pieceCode;
        			j--;
        		}
        	}
        }
    }
}
