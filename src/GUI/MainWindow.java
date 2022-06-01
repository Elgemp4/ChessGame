package GUI;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static MainWindow mainWindowClass;

    private final MenuPanel MENU_PANEL;

    private final PromotionSelection PROMOTION_SELECTION;

    private final GamePanel CHESS_BOARD;

    public MainWindow() {
        super("Chess");

        mainWindowClass = this;

        CHESS_BOARD = new GamePanel();
        MENU_PANEL = new MenuPanel();
        PROMOTION_SELECTION = new PromotionSelection();


        showChessBoard();

        setMinimumSize(new Dimension(300, 300));

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void showMenu() {
        remove(CHESS_BOARD);
        add(MENU_PANEL);
    }

    public void showChessBoard() {
        remove(MENU_PANEL);
        add(CHESS_BOARD);
    }

    public static MainWindow getMainWindowClass() {
        return mainWindowClass;
    }

    public PromotionSelection getPromotionSelection() {
        return PROMOTION_SELECTION;
    }
}
