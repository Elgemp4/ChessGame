package Logic.Pieces;

import Logic.ChessBoard;
import Logic.Index;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Piece {
    final public static int WHITE = -1;
    final public static int BLACK = 1;

    protected ChessBoard chessBoard;

    protected BufferedImage sprite;

    protected int pieceTeam;

    protected boolean hasMoved = false;

    protected ArrayList<Index> availableMoves;

    protected Index currentIndex;

    public Piece(int color, Index index) {
        this.chessBoard = ChessBoard.getChessBoardClass();

        this.availableMoves = new ArrayList<>();

        this.pieceTeam = color;

        this.currentIndex = index;

        loadSprite();
    }

    /**
     * Renvoie vrai si la pièce peut se déplacée à l'index fourni
     * @param moveIndex
     * @return
     */
    public boolean isAValidMove(Index moveIndex) {
        return availableMoves.contains(moveIndex);
    }

    /**
     * Renvoie la couleur de la pièce
     * @return
     */
    public int getPieceTeam() {
        return pieceTeam;
    }

    /**
     * Renvoie l'index de la pièce
     * @return
     */
    public Index getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Défini l'index de la pièce
     * @param currentIndex
     */
    public void setCurrentIndex(Index currentIndex) {
        this.currentIndex = currentIndex;

        chessBoard.computeAllMoves();

        hasMoved = true;
    }

    /**
     * Charge le sprite de la pièce
     */
    private void loadSprite(){
        String colorString = (pieceTeam == BLACK) ? "black" : "white";

        try {
            this.sprite = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Sprites/"+colorString +"_"+ getPieceName() + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Renvoie le sprite de la pièce
     * @return
     */
    public BufferedImage getSprite() {
        return sprite;
    }

    /**
     * Méthode à override sur les différentes variantes de pièce permettant de savoir où charger le sprite de la pièce
     * @return
     */
    abstract protected String getPieceName();

    /**
     * Renvoie la liste des mouvements possibles par la pièce
     * @return
     */
    public ArrayList<Index> getAvailableMoves() {
        return availableMoves;
    }

    /**
     * Méthode à override qui calcule les différents déplacement possible par la pièce
     */
    abstract public void computeAvailableMoves();

    /**
     * Méthode à override qui permet à la pièce d'exécuter du code lorsqu'elle se déplace s'il en a besoin
     */
    abstract public void onMovement();
}
