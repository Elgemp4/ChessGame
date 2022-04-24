package GameLogic;

import GUI.ChessPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener implements MouseListener {
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

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CHESS_BOARD.handleClick(CHESS_PANEL.getBoardPosition(e.getX(), e.getY()));
        CHESS_PANEL.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
