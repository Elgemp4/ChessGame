package GUI;

import Logic.Pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class PromotionSelection extends JDialog { //TODO
    private JButton[] selectionButtons;
    private Piece[] piecesToSelect;

    final private String[] avaiblesPiece;

    private String pieceColor;

    private Piece pieceToPromote;

    private Piece choosenPiece;

    public PromotionSelection() {
        super();

        this.avaiblesPiece = new String[]{"bishop", "knight", "rook", "queen"};

        this.setLayout(new GridLayout(1,4));
        this.setPreferredSize(new Dimension(50, 200));

        initiateButtons();
    }

    private void initiateButtons() {
        selectionButtons = new JButton[4];

        for (int i = 0; i < 3; i++) {
            selectionButtons[i] = new JButton();
            add(selectionButtons[i]);
        }
    }

    public void showSelectionDialog(Piece promotedPiece){
        this.pieceToPromote = promotedPiece;
        this.pieceColor = this.pieceToPromote.getPieceTeam() == Piece.WHITE ? "white" : "black";

        createSelectionPiece();
        modifyButtons();

        setVisible(true);
    }

    private void createSelectionPiece() {
        piecesToSelect = new Piece[4];

        piecesToSelect[0] = new Bishop(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
        piecesToSelect[1] = new Knight(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
        piecesToSelect[2] = new Rook(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
        piecesToSelect[3] = new Queen(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
    }

    private void modifyButtons() {
        try {
            for(int i = 0 ; i<3; i++) {
                selectionButtons[i] = new JButton();
                selectionButtons[i].setPreferredSize(new Dimension(50, 50));
                selectionButtons[i].setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Sprites/"+this.pieceColor +"_" + avaiblesPiece[i] + ".png"))));

                int finalI = i;
                selectionButtons[i].addActionListener( (event) -> {setChoosenPromotion(event, finalI);});
            }
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    private void setChoosenPromotion(ActionEvent actionEvent, int i) {
        this.choosenPiece = piecesToSelect[i];
        this.dispose();
    }

    public Piece getChoosenPromotion() {
        return this.choosenPiece;
    }
}
