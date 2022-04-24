package GUI;

import GameLogic.ChessBoard;
import GameLogic.InputListener;
import GameLogic.Pieces.Piece;
import GameLogic.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessPanel extends JPanel {
    private final ChessBoard CHESS_BOARD;

    private final Color BACKGROUND_COLOR = new Color(84, 84, 84);

    private final Color CHESS_COLOR_1 = new Color(125, 148, 93);
    private final Color CHESS_COLOR_2 = new Color(238, 238, 213);

    private int caseSize = 75;
    private int chessBoardSize = caseSize * 8;

    private int xOffset = 0;
    private int yOffset = 0;

    private final InputListener INPUT_LISTENER;

    public ChessPanel() {
        setPreferredSize(new Dimension(chessBoardSize, chessBoardSize));

        CHESS_BOARD = new ChessBoard();

        INPUT_LISTENER = new InputListener(this);

        addMouseListener(INPUT_LISTENER);
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

        caseSize = chessBoardSize / 8;
        System.out.println(caseSize);

        xOffset = getWidth() / 2 - chessBoardSize / 2;
        yOffset = getHeight() / 2 - chessBoardSize / 2;
    }


    private void drawBackground(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);

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
        drawCase(position, (position.getX() + position.getY()) % 2 == 0 ? CHESS_COLOR_2 : CHESS_COLOR_1, g);
    }

    private void drawPieces(Graphics2D g) {
        Piece[][] chessBoardArray = CHESS_BOARD.getChessBoardArray();

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

        int drawX = xOffset + piece.getCurrentPosition().getX() * caseSize;
        int drawY = yOffset + piece.getCurrentPosition().getY() * caseSize;

        g.drawImage(piece.getSprite(), drawX, drawY, caseSize, caseSize, this);
    }

    private void drawCase(Position position, Color color, Graphics2D g) {
        g.setColor(color);

        int x = xOffset + position.getX() * caseSize;
        int y = yOffset + position.getY() * caseSize;

        g.fillRect(x, y, caseSize, caseSize);
    }

    private void drawSelectedPieceAndMoves(Graphics2D g) {
        Piece selectedPiece = CHESS_BOARD.getSelectedPiece();

        if(selectedPiece==null){
            return;
        }

        ArrayList<Position> possibleMoves = selectedPiece.getAvailableMoves();

        drawCase(selectedPiece.getCurrentPosition(), new Color(246, 246, 104), g);
        drawPiece(g, selectedPiece);

        for (Position position : possibleMoves) {
            if(CHESS_BOARD.getChessBoardArray()[position.getY()][position.getX()] == null) {
                drawSelectedCase(position, g);
            }
            else{
                drawPieceAttackCircle(position, g);
            }
        }
    }

    private void drawSelectedCase(Position position, Graphics2D g) {
        g.setColor(new Color(10,10,10,50));

        int x = xOffset + position.getX() * caseSize + caseSize / 3;
        int y = yOffset + position.getY() * caseSize + caseSize / 3;

        g.fillOval(x, y, caseSize /3, caseSize /3);
    }

    private void drawPieceAttackCircle(Position position, Graphics2D g) {
        int x = xOffset + position.getX() * caseSize + caseSize / 16;
        int y = yOffset + position.getY() * caseSize + caseSize / 16;

        int diameter = caseSize * 14 / 16;
        int lineThickness = caseSize * 2 / 16;

        g.setColor(new Color(10,10,10,50));

        g.setStroke(new BasicStroke(lineThickness));

        g.drawOval(x, y, diameter, diameter);
    }


    public Position getBoardPosition(int x, int y) {
        return new Position((x - xOffset) / caseSize, (y - yOffset) / caseSize);
    }

    public ChessBoard getCHESS_BOARD() {
        return CHESS_BOARD;
    }
}
