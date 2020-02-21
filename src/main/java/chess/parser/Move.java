package chess.parser;

/**
 * Created by Stanisław Kabaciński.
 */

public class Move extends Entity {

    private int no;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private boolean isQSCastling = false;
    private boolean isKSCastling = false;
    private boolean enPassant = false;
    private int promotion = 0;
    private int capturedPiece = 0;
    private int piece = 0;
    private int color = 0;
    private int[] nag = {};
    private String san;
    private String boardBefore;
    private String boardAfter;

    public Move() {
    }

    public boolean isCastling() {
        return isKSCastling || isQSCastling;
    }

    public boolean isPromotion() {
        return promotion != 0;
    }

    public int getFromX() {
        return fromX;
    }

    public void setFromX(int fromX) {
        this.fromX = fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public void setFromY(int fromY) {
        this.fromY = fromY;
    }

    public int getToX() {
        return toX;
    }

    public void setToX(int toX) {
        this.toX = toX;
    }

    public int getToY() {
        return toY;
    }

    public void setToY(int toY) {
        this.toY = toY;
    }

    public boolean isQSCastling() {
        return isQSCastling;
    }

    public void setQSCastling(boolean QSCastling) {
        isQSCastling = QSCastling;
    }

    public boolean isKSCastling() {
        return isKSCastling;
    }

    public void setKSCastling(boolean KSCastling) {
        isKSCastling = KSCastling;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(int capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isCapture() {
        return this.capturedPiece != 0;
    }

    public String getSan() {
        return san;
    }

    public void setSan(String san) {
        this.san = san;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public String getBoardBefore(){
        return boardBefore;
    }

    public void setBoardBefore(String board){
        this.boardBefore = board;
    }

    public String getBoardAfter(){
        return boardAfter;
    }

    public void setBoardAfter(String board){
        this.boardAfter = board;
    }

    public String getPANRepresentation() {
        String move = SANHelper.getField(fromX, fromY) + SANHelper.getField(toX, toY);
        switch (promotion) {
            case Piece.QUEEN:
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
            default:
                break;
        }
        return move;
    }

    public int[] getNags() {
        return nag;
    }

    public void setNags(int[] nag) {
        this.nag = nag;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Move)){
            return false;
        }

        Move m = ((Move) obj);
        return no == m.no
                && fromX == m.fromX && fromY == m.fromY
                && toX == m.toX && toY == m.toY
                && promotion == m.promotion;
    }
}
