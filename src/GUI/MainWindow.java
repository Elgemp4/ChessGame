package GUI;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final ChessPanel CHESS_BOARD;

    public MainWindow() {
        super("Chess");

        CHESS_BOARD = new ChessPanel();
        add(CHESS_BOARD);

        setMinimumSize(new Dimension(300, 300));

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
