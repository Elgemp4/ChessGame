package GameLogic.Pieces;

import GameLogic.ChessBoard;
import GameLogic.Index;
import GameLogic.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    public boolean isAValidMove(Index moveIndex) {
        return availableMoves.contains(moveIndex);
    }

    public int getPieceTeam() {
        return pieceTeam;
    }

    public Index getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Index currentIndex) {
        this.currentIndex = currentIndex;

        chessBoard.computeMoves();

        hasMoved = true;
    }

    private void loadSprite(){
        String colorString = (pieceTeam == BLACK) ? "black" : "white";

        try {
            this.sprite = ImageIO.read(new File("src/Sprites/"+colorString +"_"+ getPieceName() + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    abstract protected String getPieceName();

    public ArrayList<Index> getAvailableMoves() {
        return availableMoves;
    }

    abstract public void computeAvailableMoves();

    abstract protected void onMovement();
}
