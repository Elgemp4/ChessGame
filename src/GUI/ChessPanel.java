package GUI;

import GameLogic.ChessBoard;
import GameLogic.InputListener;
import GameLogic.Pieces.Piece;
import GameLogic.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessPanel extends JPanel {
    private final ChessBoard chessBoard;

    private final Color backgroundColor = new Color(84, 84, 84);

    private final Color chessColor1 = new Color(125, 148, 93);
    private final Color chessColor2 = new Color(238, 238, 213);

    private static int CASE_SIZE = 75;
    private int chessBoardSize = CASE_SIZE * 8;

    private int xOffset = 0;
    private int yOffset = 0;

    private final InputListener inputListener;

    public ChessPanel() {
        setPreferredSize(new Dimension(chessBoardSize, chessBoardSize));

        chessBoard = new ChessBoard();

        inputListener = new InputListener(this);

        addMouseListener(inputListener);
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

        drawPieces(graphics2D);

        drawSelectedPieceAndMoves(graphics2D);

        g.dispose();
    }

    private void trackChessDimensions() {
        chessBoardSize = Math.min(getWidth(), getHeight());

        CASE_SIZE = chessBoardSize / 8;
        System.out.println(CASE_SIZE);

        xOffset = getWidth() / 2 - chessBoardSize / 2;
        yOffset = getHeight() / 2 - chessBoardSize / 2;
    }


    private void drawBackground(Graphics2D g) {
        g.setColor(backgroundColor);

        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawGrid(Graphics2D g){
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                drawCase(new Position(col, row), g);
            }
        }
    }

    private void drawCase(Position position, Graphics2D g) {
        drawCase(position, (position.getX() + position.getY()) % 2 == 0 ? chessColor2 : chessColor1, g);
    }

    private void drawPieces(Graphics2D g) {
        Piece[][] chessBoardArray = chessBoard.getChessBoardArray();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if(chessBoardArray[y][x] != null){
                    drawPiece(g, chessBoardArray[y][x]);
                }
            }
        }
    }

    private void drawPiece(Graphics2D g, Piece piece) {
        if(piece == null) {
            return;
        }

        int drawX = xOffset + piece.getCurrentPosition().getX() * CASE_SIZE;
        int drawY = yOffset + piece.getCurrentPosition().getY() * CASE_SIZE;

        g.drawImage(piece.getSprite(), drawX, drawY, CASE_SIZE, CASE_SIZE, this);
    }

    private void drawCase(Position position, Color color, Graphics2D g) {
        g.setColor(color);

        int x = xOffset + position.getX() * CASE_SIZE;
        int y = yOffset + position.getY() * CASE_SIZE;

        g.fillRect(x, y, CASE_SIZE, CASE_SIZE);
    }

    private void drawSelectedPieceAndMoves(Graphics2D g) {
        Piece selectedPiece = chessBoard.getSelectedPiece();

        if(selectedPiece==null){
            return;
        }

        ArrayList<Position> possibleMoves = selectedPiece.getAvailableMoves();

        drawCase(selectedPiece.getCurrentPosition(), new Color(246, 246, 104), g);
        drawPiece(g, selectedPiece);

        for (Position position : possibleMoves) {
            if(chessBoard.getChessBoardArray()[position.getY()][position.getX()] == null) {
                drawSelectedCase(position, g);
            }
            else{
                drawPieceAttackCircle(position, g);
            }
        }
    }

    private void drawSelectedCase(Position position, Graphics2D g) {
        g.setColor(new Color(10,10,10,50));

        int x = xOffset + position.getX() * CASE_SIZE + CASE_SIZE / 3;
        int y = yOffset + position.getY() * CASE_SIZE + CASE_SIZE / 3;

        g.fillOval(x, y, CASE_SIZE /3, CASE_SIZE /3);
    }

    private void drawPieceAttackCircle(Position position, Graphics2D g) {
        int x = xOffset + position.getX() * CASE_SIZE + CASE_SIZE / 16;
        int y = yOffset + position.getY() * CASE_SIZE + CASE_SIZE / 16;

        int diameter = CASE_SIZE * 14 / 16;
        int lineThickness = CASE_SIZE * 2 / 16;

        g.setColor(new Color(10,10,10,50));

        g.setStroke(new BasicStroke(lineThickness));

        g.drawOval(x, y, diameter, diameter);
    }


    public Position getBoardPosition(int x, int y) {
        return new Position((x - xOffset) / CASE_SIZE, (y - yOffset) / CASE_SIZE);
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public static int getCaseSize() {
        return CASE_SIZE;
    }
}
