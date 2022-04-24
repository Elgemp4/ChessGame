package GameLogic;

import GUI.ChessPanel;
import GameLogic.Pieces.Piece;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener implements MouseListener {
    private ChessPanel chessPanel;

    private ChessBoard chessBoard;

    public InputListener(ChessPanel chessPanel){
        this.chessPanel = chessPanel;

        this.chessBoard = chessPanel.getChessBoard();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        chessBoard.handleClick(chessPanel.getBoardPosition(e.getX(), e.getY()));
        chessPanel.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
