package Logic;

import GUI.GamePanel;
import GUI.MainWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ChessInputListener implements MouseListener, MouseMotionListener {
    private final MainWindow MAIN_WINDOW;

    private final GamePanel CHESS_PANEL;

    private final ChessBoard CHESS_BOARD;

    private boolean isEnable = true;

    public ChessInputListener() {
        this.MAIN_WINDOW = MainWindow.getMainWindowClass();

        this.CHESS_PANEL = GamePanel.getGamePanelClass();

        this.CHESS_BOARD = CHESS_PANEL.getChessBoard();
    }

    public void setEnable(boolean newEnable) {
        this.isEnable = newEnable;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isEnable) {
            CHESS_BOARD.handleClick(CHESS_PANEL.getBoardIndex(e.getX(), e.getY()), true);
            CHESS_PANEL.repaint();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isEnable) {
            CHESS_PANEL.resetDragPosition();
            CHESS_BOARD.handleClick(CHESS_PANEL.getBoardIndex(e.getX(), e.getY()), false);
            CHESS_PANEL.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isEnable) {
            CHESS_PANEL.setDragPosition(new Position(e.getX(), e.getY()));
            CHESS_PANEL.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
