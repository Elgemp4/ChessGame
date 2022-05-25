package GameLogic;

import GameLogic.Pieces.Piece;

public class Index {
    private ChessBoard chessBoard;

    private int x;
    private int y;

    public Index(int x, int y) {
        this.chessBoard = ChessBoard.getChessBoardClass();

        setY(y);
        setX(x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index position = (Index) o;
        return x == position.x && y == position.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;

    }
}
