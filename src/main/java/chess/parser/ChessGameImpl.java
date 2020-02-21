package chess.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * Created 10/1/2016.
 */

public class ChessGameImpl implements ChessGame, Cloneable {
    private final MoveFactory moveFactory;
    private final List<Move> moveList;
    private final int board[][];

    private boolean whiteKingMoved = false;
    private boolean whiteKSRookMoved = false;
    private boolean whiteQSRookMoved = false;
    private boolean blackKingMoved = false;
    private boolean blackKSRookMoved = false;
    private boolean blackQSRookMoved = false;

    private boolean locked = false;

    private int halfMovesCount=0;
    private int movesCount=0;

    public ChessGameImpl() {
        this.moveFactory = new MoveFactory(new PossibleMovesProviderImpl(this));
        this.moveList = new LinkedList<>();
        this.board = new int[8][8];
        BoardUtils.initBoard(this.board);
    }
    
    public ChessGameImpl(String fen) {
        this.moveFactory = new MoveFactory(new PossibleMovesProviderImpl(this));
        this.moveList = new LinkedList<>();
        this.board = new int[8][8];
        BoardUtils.initBoard(this.board, fen);
    }

    @Override
    public void makeMove(Move move) {
        if (locked) return;
        moveList.add(move);
        move.setNo(moveList.size());
        registerKingAndRookMoves(move);
        updateMovesCount(move);
    }

    @Override
    public void makeMove(Move move, int fromX, int fromY, int toX, int toY) {
        if (locked) return;
        Move m = moveFactory.getMove(getBoard(), fromX, fromY, toX, toY);
        if (move.getPromotion() != 0) {
            getBoard()[move.getToX()][move.getToY()] = move.getPromotion() + move.getColor();
        }
        makeMove(m);
        move.setNo(m.getNo());
    }
    
    private void updateMovesCount(Move m) {
    	if(!m.isCapture() && m.getPiece()!=Piece.PAWN)
    		halfMovesCount++;
    	else
    		halfMovesCount=0;
    	if(m.getColor()==PieceColor.WHITE)
    		movesCount++;
    }

    private void registerKingAndRookMoves(Move move) {
        int piece = move.getPiece();
        int color = move.getColor();
        if (piece == Piece.KING) {
            if (color == PieceColor.BLACK)
                blackKingMoved = true;
            else
                whiteKingMoved = true;
        } else if (piece == Piece.QS_ROOK) {
            if (color == PieceColor.WHITE)
                whiteQSRookMoved = true;
            else if (color == PieceColor.BLACK) {
                blackQSRookMoved = true;
            }
        } else if (piece == Piece.KS_ROOK) {
            if (color == PieceColor.WHITE)
                whiteKSRookMoved = true;
            else if (color == PieceColor.BLACK)
                blackKSRookMoved = true;
        }
    }

    @Override
    public int[][] getBoard() {
        return board;
    }

    @Override
    public int getNextMovePlayerColor() {
        return moveList.size() % 2 == 0 ? PieceColor.WHITE : PieceColor.BLACK;
    }

    @Override
    public boolean isBlackQSRookMoved() {
        return blackQSRookMoved;
    }

    @Override
    public boolean isBlackKSRookMoved() {
        return blackKSRookMoved;
    }

    @Override
    public boolean isBlackKingMoved() {
        return blackKingMoved;
    }

    @Override
    public boolean isWhiteKingMoved() {
        return whiteKingMoved;
    }

    @Override
    public boolean isWhiteKSRookMoved() {
        return whiteKSRookMoved;
    }

    @Override
    public boolean isWhiteQSRookMoved() {
        return whiteQSRookMoved;
    }

    @Override
    public boolean wasQSRookMoved(int color) {
        if (color == PieceColor.WHITE) return whiteQSRookMoved;
        if (color == PieceColor.BLACK) return blackQSRookMoved;
        return true;
    }

    @Override
    public boolean wasKSRookMoved(int color) {
        if (color == PieceColor.WHITE) return whiteKSRookMoved;
        if (color == PieceColor.BLACK) return blackKSRookMoved;
        return true;
    }

    @Override
    public boolean wasKingMoved(int color) {
        if (color == PieceColor.WHITE) return whiteKingMoved;
        if (color == PieceColor.BLACK) return blackKingMoved;
        return true;
    }

    @Override
    public boolean undoLastMove() {
        if (moveList.isEmpty()) {
            return false;
        }

        Move move = moveList.get(moveList.size() - 1);
        if (move.isCastling()) {
            if (move.isKSCastling()) {
                int y = move.getFromY();
                board[5][y] = board[6][y] = 0;
                board[4][y] = Piece.KING + move.getColor();
                board[7][y] = Piece.KS_ROOK + move.getColor();
            } else {
                int y = move.getFromY();
                board[2][y] = board[3][y] = 0;
                board[4][y] = Piece.KING + move.getColor();
                board[0][y] = Piece.KS_ROOK + move.getColor();
            }
        } else if (move.isCapture()) {
            if (move.isEnPassant()) {
                board[move.getToX()][move.getFromY()] = move.getCapturedPiece();
                board[move.getToX()][move.getToY()] = 0;
            } else {
                board[move.getToX()][move.getToY()] = move.getCapturedPiece();
            }
            board[move.getFromX()][move.getFromY()] = move.getPiece() + move.getColor();
        } else {
            board[move.getFromX()][move.getFromY()] = move.getPiece() + move.getColor();
            board[move.getToX()][move.getToY()] = 0;
        }
        moveList.remove(moveList.size() - 1);

        if (move.getColor() == PieceColor.BLACK) {
            if (move.getPiece() == Piece.KS_ROOK)
                blackKSRookMoved = wasPieceMoved(PieceColor.BLACK + Piece.KS_ROOK);
            else if (move.getPiece() == Piece.QS_ROOK)
                blackQSRookMoved = wasPieceMoved(PieceColor.BLACK + Piece.QS_ROOK);
            else if (move.getPiece() == Piece.KING)
                blackKingMoved = wasPieceMoved(PieceColor.BLACK + Piece.KING);
        } else {
            if (move.getPiece() == Piece.KS_ROOK)
                whiteKSRookMoved = wasPieceMoved(PieceColor.WHITE + Piece.KS_ROOK);
            else if (move.getPiece() == Piece.QS_ROOK)
                whiteQSRookMoved = wasPieceMoved(PieceColor.WHITE + Piece.QS_ROOK);
            else if (move.getPiece() == Piece.KING)
                whiteKingMoved = wasPieceMoved(PieceColor.WHITE + Piece.KING);
        }
        return true;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void reset() {
        moveList.clear();
        BoardUtils.initBoard(this.board);
        whiteKingMoved = false;
        whiteKSRookMoved = false;
        whiteQSRookMoved = false;
        blackKingMoved = false;
        blackKSRookMoved = false;
        blackQSRookMoved = false;
        locked = false;
    }

    private boolean wasPieceMoved(int pieceWithColor) {
        for (Move m: moveList) {
            if (m.getColor() + m.getPiece() == pieceWithColor) return true;
        }
        return false;
    }

    @Override
    public List<Move> getMoveList() {
        return moveList;
    }

    @Override
    public ChessGame clone() {
        ChessGameImpl chessGame = new ChessGameImpl();
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, chessGame.board[i], 0, board[0].length);
        }
        chessGame.moveList.clear();
        chessGame.moveList.addAll(moveList);
        chessGame.whiteKingMoved = whiteKingMoved;
        chessGame.whiteKSRookMoved = whiteKSRookMoved;
        chessGame.whiteQSRookMoved = whiteQSRookMoved;
        chessGame.blackKingMoved = blackKingMoved;
        chessGame.blackKSRookMoved = blackKSRookMoved;
        chessGame.blackQSRookMoved = blackQSRookMoved;
        return chessGame;
    }

    
    
    public String getFen() {

        StringBuffer fen = new StringBuffer();
        for(int row = 7; row>=0; row--) {
        	int countFreeFields = 0;
        	for(int col=0; col<8; col++) {
        		int fieldValue = this.board[col][row];
        		if(fieldValue==0)
        			countFreeFields++;
        		else {
        			String move = "";
        			int pieceValue = fieldValue>=PieceColor.BLACK?fieldValue-PieceColor.BLACK:fieldValue-PieceColor.WHITE;
        			switch (pieceValue) {
	                    case Consts.KING:
	                        move += 'k';
	                        break;
	                    case Consts.QUEEN:
	                        move += 'q';
	                        break;
	                    case Piece.BISHOP:
	                        move += 'b';
	                        break;
	                    case Piece.KNIGHT:
	                        move += 'n';
	                        break;
	                    case Piece.QS_ROOK:
	                    case Piece.KS_ROOK:
	                        move += 'r';
	                        break;
	                    case Piece.PAWN:
	                        move += 'p';
	                        break;
	                    default:
	                        break;
        			}
        			if(fieldValue<PieceColor.BLACK)
        				move = move.toUpperCase();
        			if(countFreeFields>0) {
        				fen.append(countFreeFields);
        				countFreeFields=0;
        			}
        			fen.append(move);
        		}
        	}
        	if(countFreeFields>0)
        		fen.append(countFreeFields);
        	if(row>0)
        		fen.append("/");
        	else
        		fen.append(" ");
        }

        
        if (this.getNextMovePlayerColor() == PieceColor.WHITE)
            fen.append("w ");
        else
            fen.append("b ");

        
        String castling = "";
        if(!whiteKingMoved) {
        	if(!whiteKSRookMoved)
        		castling+="K";
        	if(!whiteQSRookMoved)
        		castling+="Q";
        }
        if(!blackKingMoved) {
        	if(!blackKSRookMoved)
        		castling+="k";
        	if(!blackQSRookMoved)
        		castling+="q";
        }
        castling = castling.equals("")?"-":castling;
        fen.append(castling);
        
        Move m = moveList.get(moveList.size()-1);
        if(m.getPiece()==Piece.PAWN && Math.abs(m.getFromY()-m.getToY())==2) {
        	String enPassant = "";
        	char col = (char)((int)'a' + m.getFromX());
        	enPassant+=col;
        	if(m.getFromY()==6)
        		enPassant+=6;
        	else
        		enPassant+=3;
        	fen.append(" "+enPassant+" ");
        }
        else 
        	fen.append(" - ");
        
        
        fen.append(halfMovesCount+" "+movesCount);
        
        return fen.toString();
    }
}
