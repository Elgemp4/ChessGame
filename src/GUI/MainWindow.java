package GUI;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private ChessPanel chessBoard;

    public MainWindow() {
        super("Chess");

        chessBoard = new ChessPanel();
        add(chessBoard);

        setMinimumSize(new Dimension(300, 300));

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
