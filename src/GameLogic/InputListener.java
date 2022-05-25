package GameLogic;

import GUI.ChessPanel;
import GUI.MainWindow;

import java.awt.event.*;

public class InputListener implements MouseListener, MouseMotionListener{
    private final MainWindow MAIN_WINDOW;

    private final ChessPanel CHESS_PANEL;

    private final ChessBoard CHESS_BOARD;

    public InputListener(){
        this.MAIN_WINDOW = MainWindow.getMainWindowClass();

        this.CHESS_PANEL = ChessPanel.getChessPanelClass();

        this.CHESS_BOARD = CHESS_PANEL.getCHESS_BOARD();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        CHESS_BOARD.handleClick(CHESS_PANEL.getBoardIndex(e.getX(), e.getY()), true);
        CHESS_PANEL.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CHESS_PANEL.resetDragPosition();
        CHESS_BOARD.handleClick(CHESS_PANEL.getBoardIndex(e.getX(), e.getY()), false);
        CHESS_PANEL.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        CHESS_PANEL.setDragPosition(new Position(e.getX(), e.getY()));
        CHESS_PANEL.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
