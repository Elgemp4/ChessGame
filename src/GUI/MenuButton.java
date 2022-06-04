package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MenuButton {
    private GamePanel gamePanel;

    private String title;
    private int x;
    private int y;
    private int width;
    private int height;

    private Color buttonColor;

    private Runnable actionWhenClicked;

    private BufferedImage buttonImage;

    public MenuButton(String title, int x, int y, int width, int height, Runnable actionWhenClicked) {
        this.gamePanel = GamePanel.getGamePanelClass();

        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.actionWhenClicked = actionWhenClicked;

        this.buttonColor = Color.DARK_GRAY;

        this.recreateButtonImage();
    }

    /**
     * Recréer une image du bouton pour pouvoir le dessiner dans le menu
     */
    private void recreateButtonImage() {
        buttonImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) buttonImage.getGraphics();

        graphics2D.setColor(buttonColor);
        graphics2D.fillRoundRect(0, 0, this.width, this.height, 2, 2);

        int textFontSize = width / 8;
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font("Arial", Font.PLAIN, textFontSize));

        Rectangle2D titleBound = graphics2D.getFontMetrics(graphics2D.getFont()).getStringBounds(title, graphics2D);
        int textX = (int) (width - titleBound.getWidth()) / 2;
        int textY = (int) (height + titleBound.getHeight() / 2) / 2;

        graphics2D.drawString(title, textX, textY);

        graphics2D.dispose();
    }

    /**
     * Execute le code du bouton si le bouton est bien cliqué
     *
     * @param x
     * @param y
     */
    public void executeIfInHitbox(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            actionWhenClicked.run();
        }
    }

    /**
     * Surligne le bouton si jamais la souris passe au-dessus du bouton
     *
     * @param x
     * @param y
     */
    public void highlightButtonIfHover(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            if (buttonColor == Color.DARK_GRAY) {
                buttonColor = Color.GRAY;
                gamePanel.repaint();
            }
        } else {
            if (buttonColor == Color.GRAY) {
                buttonColor = Color.DARK_GRAY;
                gamePanel.repaint();
            }
        }
    }

    /**
     * Renvoie l'image du bouton
     *
     * @return
     */
    public BufferedImage getButtonImage() {
        return buttonImage;
    }

    /**
     * Défini la position du bouton
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.x = gamePanel.getXOffset() + x;
        this.y = gamePanel.getYOffset() + y;
        recreateButtonImage();
    }

    /**
     * Défini la taille du bouton
     *
     * @param width
     * @param height
     */
    public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;
        recreateButtonImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
