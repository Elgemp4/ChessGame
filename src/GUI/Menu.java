package GUI;

import Logic.ChessBoard;
import Logic.Pieces.Piece;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Menu {
    GamePanel gamePanel;
    ChessBoard chessBoard;

    private MenuButton restartButton;
    private MenuButton quitButton;

    final private double widthRatio = 1.0 / 3.0;
    final private double heightRatio = 1.0 / 8.0;
    final private double xOffsetRatio = 1.0 / 8.0;
    final private double yOffsetRatio = 4.0 / 7.0;

    public Menu() {
        gamePanel = GamePanel.getGamePanelClass();
        chessBoard = ChessBoard.getChessBoardClass();

        restartButton = new MenuButton("Redémarrer", 0, 0, 50, 40, chessBoard::restartGame);
        quitButton = new MenuButton("Quitter", 0, 0, 50, 40, this::exitGame);

        actualizeMenuDisposition();
    }

    /**
     * Dessiner le menu de victoire
     *
     * @param graphics2D
     */
    public void drawMenu(Graphics2D graphics2D) {
        drawBackground(graphics2D);

        drawVictoryText(graphics2D);

        drawButtons(graphics2D);
    }

    //Dessiner l'arrière-plan transparent au-dessus du jeu
    private void drawBackground(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(150, 150, 105, 200));
        graphics2D.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
    }

    /**
     * Dessine le texte de la victoire et le centre
     *
     * @param graphics2D
     */
    private void drawVictoryText(Graphics2D graphics2D) {
        String victoryString = "Le joueur " + (chessBoard.getWhomTurn() == Piece.BLACK ? "noir" : "blanc") + " a gagné !";


        graphics2D.setColor(Color.white);
        int textFontSize = gamePanel.getChessBoardSize() / 15;
        graphics2D.setFont(new Font("Arial", Font.PLAIN, textFontSize));
        Rectangle2D titleBound = graphics2D.getFontMetrics(graphics2D.getFont()).getStringBounds(victoryString, graphics2D);

        int textX = gamePanel.getXOffset() + (int) (gamePanel.getChessBoardSize() - titleBound.getWidth()) / 2;
        int textY = gamePanel.getYOffset() + gamePanel.getChessBoardSize() * 1 / 3;

        graphics2D.drawString(victoryString, textX, textY);
    }

    /**
     * Dessines le bouton quitter et le bouton rejouer
     *
     * @param graphics2D
     */
    private void drawButtons(Graphics2D graphics2D) {
        graphics2D.drawImage(restartButton.getButtonImage(), restartButton.getX(), restartButton.getY(), null);
        graphics2D.drawImage(quitButton.getButtonImage(), quitButton.getX(), quitButton.getY(), null);
    }

    /**
     * Vérifie si le click effectué active un des boutons
     *
     * @param x
     * @param y
     */
    public void checkClick(int x, int y) {
        restartButton.executeIfInHitbox(x, y);
        quitButton.executeIfInHitbox(x, y);
    }

    /**
     * Vérifie si la souris passe au-dessus d'un des boutons
     *
     * @param x
     * @param y
     */
    public void checkHover(int x, int y) {
        restartButton.highlightButtonIfHover(x, y);
        quitButton.highlightButtonIfHover(x, y);
    }

    /**
     * Actualize les informations relatives au dessin du menu en fonction de la taille de la fenêtre
     */
    public void actualizeMenuDisposition() {
        int boardSize = gamePanel.getChessBoardSize();

        int offsetX = (int) (boardSize * xOffsetRatio);
        int offsetY = (int) (boardSize * yOffsetRatio);

        int buttonWidth = (int) (boardSize * widthRatio);
        int buttonHeight = (int) (boardSize * heightRatio);

        restartButton.setPosition(offsetX, offsetY);
        restartButton.setDimension(buttonWidth, buttonHeight);

        quitButton.setPosition(boardSize - buttonWidth - offsetX, offsetY);
        quitButton.setDimension(buttonWidth, buttonHeight);
    }

    /**
     * Quite le jeu
     */
    private void exitGame() {
        System.exit(0);
    }
}
