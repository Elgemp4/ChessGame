package Logic;

import GUI.GamePanel;
import GUI.Menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MenuInputListener implements MouseListener, MouseMotionListener {
    private ChessBoard chessBoard;
    private Menu menu;

    private boolean isEnable;

    public MenuInputListener() {
        chessBoard = ChessBoard.getChessBoardClass();
        menu = GamePanel.getGamePanelClass().getMenu();
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isEnable) {
            menu.checkClick(e.getX(), e.getY());
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isEnable) {
            menu.checkHover(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }


}
