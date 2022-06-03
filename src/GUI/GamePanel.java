package GUI;

import Logic.*;
import Logic.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private static GamePanel gamePanelClass;

    private final ChessBoard CHESS_BOARD;

    private final Color BACKGROUND_COLOR = new Color(84, 84, 84);

    private final Color CHESS_COLOR_1 = new Color(125, 148, 93);
    private final Color CHESS_COLOR_2 = new Color(238, 238, 213);

    private int caseSize = 75;
    private int chessBoardSize = caseSize * 8;

    private int xOffset = 0;
    private int yOffset = 0;

    private Position dragPosition;

    private final ChessInputListener CHESS_LISTENER;
    private final MenuListener MENU_LISTENER;

    private final Menu MENU;

    public GamePanel() {
        gamePanelClass = this;

        setPreferredSize(new Dimension(chessBoardSize, chessBoardSize));

        CHESS_BOARD = new ChessBoard();
        MENU = new Menu();

        CHESS_LISTENER = new ChessInputListener();
        MENU_LISTENER = new MenuListener();

        addMouseListener(CHESS_LISTENER);
        addMouseMotionListener(CHESS_LISTENER);

        addMouseListener(MENU_LISTENER);
        addMouseMotionListener(MENU_LISTENER);


    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        trackChessDimensions();

        drawBackground(graphics2D);

        drawGrid(graphics2D);

        drawAllPieces(graphics2D);

        drawSelectedPieceAndMoves(graphics2D);

        moveSelected(graphics2D);

        if(CHESS_BOARD.isGameOver()){
            MENU.drawMenu(graphics2D);
        }

        g.dispose();
    }


    /**
     * Suit les changements de taille de la fenêtre pour ajuster les valeurs nécessaires à l'affichage
     */
    private void trackChessDimensions() {
        chessBoardSize = Math.min(getWidth(), getHeight());

        caseSize = chessBoardSize / 8;

        xOffset = getWidth() / 2 - chessBoardSize / 2;
        yOffset = getHeight() / 2 - chessBoardSize / 2;

        MENU.actualizeMenuDisposition();
    }

    /**
     * Bouge le point à la position du curseur pour donner l'effet de tenir le pion
     * @param g
     */
    private void moveSelected(Graphics2D g) {
        if(dragPosition != null){
            if(getChessBoard().isInSelectionMode()){
                drawCase(getChessBoard().getSelectedPiece().getCurrentIndex(), new Color(246, 246, 104), g);
                drawPiece(g, getChessBoard().getSelectedPiece(), dragPosition);
            }

        }
    }

    /**
     * Dessin de l'arrière plan (arrière plan présent autour du plateau)
     * @param g
     */
    private void drawBackground(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);

        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Dessine la grille du plateau
     * @param g
     */
    private void drawGrid(Graphics2D g){
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                drawCase(new Index(col, row), g);
            }
        }
    }

    /**
     * Dessine une case à l'index spécifié en calculant automatiquement la couleur nécessaire à cet endroit
     * @param index Index de la case à dessiner
     * @param g
     */
    private void drawCase(Index index, Graphics2D g) {
        drawCase(index, (index.getX() + index.getY()) % 2 == 0 ? CHESS_COLOR_2 : CHESS_COLOR_1, g);
    }

    /**
     * Desinne un case avec la couleur indiquée
     * @param index Index de la case à dessiner
     * @param color Couleur de la case
     * @param g
     */
    private void drawCase(Index index, Color color, Graphics2D g) {
        g.setColor(color);

        Position drawPosition = getScreenPosition(index.getX(), index.getY());

        g.fillRect(drawPosition.getX(), drawPosition.getY(), caseSize, caseSize);
    }

    /**
     * Dessine l'ensemble des pièces du plateau
     * @param g
     */
    private void drawAllPieces(Graphics2D g) {
        Piece[][] chessBoardArray = CHESS_BOARD.getChessBoardArray();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if(chessBoardArray[y][x] != null){
                    drawPiece(g, chessBoardArray[y][x]);
                }
            }
        }
    }

    /**
     * Dessine la pièce fournie
     * @param g
     * @param piece La pièce à dessiner
     */
    private void drawPiece(Graphics2D g, Piece piece) {
        if(piece == null) {
            return;
        }

        Position drawPosition = getScreenPosition(piece.getCurrentIndex().getX(), piece.getCurrentIndex().getY());

        drawPiece(g, piece, drawPosition);
    }

    /**
     * Dessine la pièce au coordonées spécifiées
     * @param g
     * @param piece La pièce à dessiner
     * @param position La position où dessiner la pièce
     */
    private void drawPiece(Graphics2D g, Piece piece, Position position) {
        if(piece == null) {
            return;
        }

        g.drawImage(piece.getSprite(), position.getX(), position.getY(), caseSize, caseSize, this);
    }

    /**
     * Dessine les différents mouvements possibles et les différentes attaques sur les pions ennemis
     * ainsi que colorie en jaune la case du pion sélectionné
     * @param g
     */
    private void drawSelectedPieceAndMoves(Graphics2D g) {
        Piece selectedPiece = CHESS_BOARD.getSelectedPiece();

        if(selectedPiece==null){
            return;
        }

        ArrayList<Index> possibleMoves = selectedPiece.getAvailableMoves();

        drawCase(selectedPiece.getCurrentIndex(), new Color(246, 246, 104), g);
        drawPiece(g, selectedPiece);

        for (Index index : possibleMoves) {
            if(CHESS_BOARD.isEmpty(CHESS_BOARD.getChessBoardArray()[index.getY()][index.getX()])) {
                drawPossibleMove(index, g);
            }
            else{
                drawPieceAttackCircle(index, g);
            }
        }
    }

    /**
     * Dessine un cercle remplis indiquant une case sur laquelle la pièce peut se déplacer
     * @param index
     * @param g
     */
    private void drawPossibleMove(Index index, Graphics2D g) {
        g.setColor(new Color(10,10,10,50));

        Position drawPosition = getScreenPosition(index.getX(), index.getY(), caseSize / 3);

        g.fillOval(drawPosition.getX(), drawPosition.getY(), caseSize /3, caseSize /3);
    }

    /**
     * Dessine les contours d'un cercle pour indiquer une pièce pouvant être attaquée
     * @param index
     * @param g
     */
    private void drawPieceAttackCircle(Index index, Graphics2D g) {
        Position drawPosition = getScreenPosition(index.getX(), index.getY(), caseSize / 16);

        int diameter = caseSize * 14 / 16;
        int lineThickness = caseSize * 2 / 16;

        g.setColor(new Color(10,10,10,50));

        g.setStroke(new BasicStroke(lineThickness));

        g.drawOval(drawPosition.getX(), drawPosition.getY(), diameter, diameter);
    }

    /**
     * Reset la position où le pion est tenu
     */
    public void resetDragPosition() {
        dragPosition = null;
    }

    /**
     * Défini la position où le pion est tenu
     * @param dragPosition Nouvelle position
     */
    public void setDragPosition(Position dragPosition) {
        this.dragPosition = new Position(dragPosition.getX() - caseSize / 2, dragPosition.getY() - caseSize / 2);
    }

    /**
     * Permet d'obtenir l'index où le joueur a cliqué en fonction du tour (lors du tour des noirs, le plateau est
     * inversé
     * @param x
     * @param y
     * @return
     */
    public Index getBoardIndex(int x, int y) {
        int xIndex = (x - xOffset) / caseSize;
        int yIndex = (y - yOffset) / caseSize;

        if(CHESS_BOARD.getWhomTurn() == Piece.BLACK) {
            yIndex = 7 - yIndex;
            xIndex = 7 - xIndex;
        }

        return new Index(xIndex, yIndex);
    }

    /**
     * Permet d'obtenir la position d'un index en fonction du tour
     * @param x
     * @param y
     * @return
     */
    public Position getScreenPosition(int x, int y) {
        return getScreenPosition(x, y, 0);
    }

    /**
     * Permet d'obtenir la position d'un index en fonction du tour et d'y ajouter un décallage
     * @param x
     * @param y
     * @return
     */
    public Position getScreenPosition(int x, int y, int offset) {
        int posX;
        int posY;

        if(CHESS_BOARD.getWhomTurn() == Piece.BLACK) {
            posX = (x * caseSize + xOffset) - offset;
            posY = (y * caseSize + yOffset) - offset;

            posY = getHeight() - caseSize - posY;
            posX = getWidth() - caseSize - posX;
        }
        else{
            posX = (x * caseSize + xOffset) + offset;
            posY = (y * caseSize + yOffset) + offset;
        }

        return new Position(posX, posY);
    }

    public ChessBoard getChessBoard() {
        return CHESS_BOARD;
    }

    public static GamePanel getGamePanelClass() {
        return gamePanelClass;
    }

    public int getChessBoardSize() {
        return chessBoardSize;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public Menu getMenu() {
        return MENU;
    }

    public ChessInputListener getChessListener() {
        return CHESS_LISTENER;
    }

    public MenuListener getMenuListener() {
        return MENU_LISTENER;
    }
}
