package GameLogic;

import GUI.ChessPanel;
import GameLogic.Pieces.Piece;

import java.util.Objects;

public class Position {
    private ChessBoard chessBoard;
    private ChessPanel chessPanel;

    private int x;
    private int y;

    public Position(int x, int y) {
        this.chessBoard = ChessBoard.getChessBoardClass();
        this.chessPanel = ChessPanel.getChessPanelClass();

        setY(y);
        setX(x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
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
