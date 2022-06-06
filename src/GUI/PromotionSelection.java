package GUI;

import Logic.Pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PromotionSelection extends JDialog {
    private JButton[] selectionButtons;
    private Piece[] piecesToSelect;

    final private String[] avaiblesPiece;

    private String pieceColor;

    private Piece pieceToPromote;

    private Piece choosenPiece;

    private JLabel instructions;

    private JPanel buttonPanel;

    public PromotionSelection() {
        super();

        this.avaiblesPiece = new String[]{"bishop", "knight", "rook", "queen"};

        this.setLayout(new GridLayout(2, 1));
        this.setModalityType(ModalityType.APPLICATION_MODAL);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setUndecorated(true);

        this.instructions = new JLabel("Choisissez la promotion pour votre pion : ");
        this.instructions.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(instructions);

        initiateButtonPanel();
    }

    /**
     * Génère les 4 boutons du dialogue
     */
    private void initiateButtonPanel() {
        selectionButtons = new JButton[4];
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        for (int i = 0; i < 4; i++) {
            selectionButtons[i] = new JButton();
            selectionButtons[i].setFocusPainted(false);

            final int finalI = i; //On ne peut pas directement passer le i dans un fonction lambda
            selectionButtons[i].addActionListener((event) -> setChoosenPromotion(event, finalI));
            buttonPanel.add(selectionButtons[i]);
        }


        this.add(buttonPanel);
    }

    /**
     * Affiche le dialogue qui permet de choisir la pièce à promouvoir
     *
     * @param promotedPiece La pièce devant être promue
     */
    public void showSelectionDialog(Piece promotedPiece) {
        this.pieceToPromote = promotedPiece;
        this.pieceColor = this.pieceToPromote.getPieceTeam() == Piece.WHITE ? "white" : "black";

        createSelectionPiece();
        actualizeDisplay();

        setLocationRelativeTo(MainWindow.getMainWindowClass());
        setVisible(true);
    }


    /**
     * Crée les pièces pouvant être sélectionnées avec la même position et la même couleur que la pièce promue
     */
    private void createSelectionPiece() {
        piecesToSelect = new Piece[4];

        piecesToSelect[0] = new Bishop(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
        piecesToSelect[1] = new Knight(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
        piecesToSelect[2] = new Rook(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
        piecesToSelect[3] = new Queen(pieceToPromote.getPieceTeam(), pieceToPromote.getCurrentIndex());
    }

    /**
     * Change les images des bouttons et la couleur de l'arrière plan en fonction de la couleur du joueur
     */
    private void actualizeDisplay() {
        try {
            for (int i = 0; i < 4; i++) {
                selectionButtons[i].setBackground(pieceToPromote.getPieceTeam() == Piece.WHITE ? Color.DARK_GRAY : Color.WHITE);
                selectionButtons[i].setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Sprites/" + this.pieceColor + "_" + avaiblesPiece[i] + ".png"))
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getContentPane().setBackground(pieceToPromote.getPieceTeam() == Piece.WHITE ? Color.DARK_GRAY : Color.WHITE);
        instructions.setForeground(pieceToPromote.getPieceTeam() == Piece.WHITE ? Color.WHITE : Color.BLACK);

        pack();
        this.setLocationRelativeTo(getParent());
    }

    /**
     * Défini la promotion de la pièce
     *
     * @param actionEvent paramètre indispensable avec le listener mais pas utile dans ce cas-ci.
     * @param i           Index de la pièce choisie
     */
    private void setChoosenPromotion(ActionEvent actionEvent, int i) {
        this.choosenPiece = piecesToSelect[i];
        this.dispose();
    }

    public Piece getChoosenPromotion() {
        return this.choosenPiece;
    }
}
