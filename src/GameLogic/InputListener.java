package GameLogic;

import GUI.ChessPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputListener implements MouseListener, MouseMotionListener {
    private final ChessPanel CHESS_PANEL;

    private final ChessBoard CHESS_BOARD;

    public InputListener(ChessPanel chessPanel){
        this.CHESS_PANEL = chessPanel;

        this.CHESS_BOARD = chessPanel.getCHESS_BOARD();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        CHESS_BOARD.handleClick(CHESS_PANEL.getBoardPosition(e.getX(), e.getY()), true);
        CHESS_PANEL.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CHESS_PANEL.resetDragPosition();
        CHESS_BOARD.handleClick(CHESS_PANEL.getBoardPosition(e.getX(), e.getY()), false);
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
        System.out.println("ici");
        CHESS_PANEL.setDragPosition(new Position(e.getX(), e.getY()));
        CHESS_PANEL.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
