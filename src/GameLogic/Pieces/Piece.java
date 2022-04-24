package GameLogic.Pieces;

import GameLogic.ChessBoard;
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

    protected ArrayList<Position> availableMoves;

    protected Position currentPosition;

    public Piece(int color, Position position) {
        this.chessBoard = ChessBoard.getThisClass();

        this.availableMoves = new ArrayList<>();

        this.pieceTeam = color;

        this.currentPosition = position;

        loadSprite();
    }

    public boolean isAValidMove(Position movePosition) {
        return availableMoves.contains(movePosition);
    }

    public int getPieceTeam() {
        return pieceTeam;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;

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

    public ArrayList<Position> getAvailableMoves() {
        return availableMoves;
    }

    abstract public void computeAvailableMoves();

    abstract protected void onMovement();
}
