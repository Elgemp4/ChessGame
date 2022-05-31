package GUI;

import Logic.Pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class PromotionSelection extends JDialog {
    private JButton bishopSelection;
    private JButton rookSelection;
    private JButton knightSelection;
    private JButton queenSelection;

    public PromotionSelection() {
        super();

        this.setLayout(new GridLayout(1,4));
        this.setPreferredSize(new Dimension(50, 200));
    }

    public void showSelectionDialog(){
        setVisible(true);
    }

    public Piece getChoosenPromotion() {

    }

    //ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Sprites/"+colorString +"_"+ getPieceName() + ".png"));
}
