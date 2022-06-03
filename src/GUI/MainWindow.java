package GUI;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static MainWindow mainWindowClass;

    private final PromotionSelection PROMOTION_SELECTION;

    private final GamePanel CHESS_BOARD;

    public MainWindow() {
        super("Chess");

        mainWindowClass = this;

        CHESS_BOARD = new GamePanel();
        add(CHESS_BOARD);

        PROMOTION_SELECTION = new PromotionSelection();

        setMinimumSize(new Dimension(300, 300));

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static MainWindow getMainWindowClass() {
        return mainWindowClass;
    }

    public PromotionSelection getPromotionSelection() {
        return PROMOTION_SELECTION;
    }


}
