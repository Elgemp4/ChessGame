package Logic;

import GUI.GamePanel;

/**
 * Classe représentant une position sur l'écran
 */
public class Position {
    private ChessBoard chessBoard;
    private GamePanel chessPanel;

    private int x;
    private int y;


    public Position(int x, int y) {
        this.chessBoard = ChessBoard.getChessBoardClass();
        this.chessPanel = GamePanel.getGamePanelClass();

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
