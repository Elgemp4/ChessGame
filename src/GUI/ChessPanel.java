package GUI;

import GameLogic.ChessBoard;
import GameLogic.Index;
import GameLogic.InputListener;
import GameLogic.Pieces.Piece;
import GameLogic.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessPanel extends JPanel {
    private static ChessPanel chessPanelClass;

    private final ChessBoard CHESS_BOARD;

    private final Color BACKGROUND_COLOR = new Color(84, 84, 84);

    private final Color CHESS_COLOR_1 = new Color(125, 148, 93);
    private final Color CHESS_COLOR_2 = new Color(238, 238, 213);

    private int caseSize = 75;
    private int chessBoardSize = caseSize * 8;

    private int xOffset = 0;
    private int yOffset = 0;

    private Position dragPosition;

    private final InputListener INPUT_LISTENER;

    public ChessPanel() {
        chessPanelClass = this;

        setPreferredSize(new Dimension(chessBoardSize, chessBoardSize));

        CHESS_BOARD = new ChessBoard();

        INPUT_LISTENER = new InputListener();

        addMouseListener(INPUT_LISTENER);

        addMouseMotionListener(INPUT_LISTENER);
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

        moveSelected(graphics2D);

        g.dispose();
    }

    private void trackChessDimensions() {
        chessBoardSize = Math.min(getWidth(), getHeight());

        caseSize = chessBoardSize / 8;

        xOffset = getWidth() / 2 - chessBoardSize / 2;
        yOffset = getHeight() / 2 - chessBoardSize / 2;
    }


    private void moveSelected(Graphics2D g) {
        if(dragPosition != null){
            if(getCHESS_BOARD().isInSelectionMode()){
                drawCase(getCHESS_BOARD().getSelectedPiece().getCurrentIndex(), new Color(246, 246, 104), g);
                drawPiece(g, getCHESS_BOARD().getSelectedPiece(), dragPosition);
            }

        }
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);

        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawGrid(Graphics2D g){
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                drawCase(new Index(col, row), g);
            }
        }
    }

    private void drawCase(Index index, Graphics2D g) {
        drawCase(index, (index.getX() + index.getY()) % 2 == 0 ? CHESS_COLOR_2 : CHESS_COLOR_1, g);
    }

    private void drawCase(Index index, Color color, Graphics2D g) {
        g.setColor(color);

        Position drawPosition = getScreenPosition(index.getX(), index.getY());

        g.fillRect(drawPosition.getX(), drawPosition.getY(), caseSize, caseSize);
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

        Position drawPosition = getScreenPosition(piece.getCurrentIndex().getX(), piece.getCurrentIndex().getY());

        drawPiece(g, piece, drawPosition);
    }

    private void drawPiece(Graphics2D g, Piece piece, Position position) {
        if(piece == null) {
            return;
        }

        g.drawImage(piece.getSprite(), position.getX(), position.getY(), caseSize, caseSize, this);
    }

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
                drawSelectedCase(index, g);
            }
            else{
                drawPieceAttackCircle(index, g);
            }
        }
    }

    private void drawSelectedCase(Index index, Graphics2D g) {
        g.setColor(new Color(10,10,10,50));

        Position drawPosition = getScreenPosition(index.getX(), index.getY(), caseSize / 3);

        g.fillOval(drawPosition.getX(), drawPosition.getY(), caseSize /3, caseSize /3);
    }

    private void drawPieceAttackCircle(Index index, Graphics2D g) {
        Position drawPosition = getScreenPosition(index.getX(), index.getY(), caseSize / 16);

        int diameter = caseSize * 14 / 16;
        int lineThickness = caseSize * 2 / 16;

        g.setColor(new Color(10,10,10,50));

        g.setStroke(new BasicStroke(lineThickness));

        g.drawOval(drawPosition.getX(), drawPosition.getY(), diameter, diameter);
    }

    public void resetDragPosition() {
        dragPosition = null;
    }

    public void setDragPosition(Position dragPosition) {
        this.dragPosition = new Position(dragPosition.getX() - caseSize / 2, dragPosition.getY() - caseSize / 2);
    }

    public Index getBoardIndex(int x, int y) {
        int xIndex = (x - xOffset) / caseSize;
        int yIndex = (y - yOffset) / caseSize;

        if(CHESS_BOARD.getWhomTurn() == Piece.BLACK) {
            yIndex = 7 - yIndex;
        }

        return new Index(xIndex, yIndex);
    }

    public Position getScreenPosition(int x, int y) {
        return getScreenPosition(x, y, 0);
    }

    public Position getScreenPosition(int x, int y, int offset) {
        int posX;
        int posY;

        if(CHESS_BOARD.getWhomTurn() == Piece.BLACK) {
            posX = (x * caseSize + xOffset) + offset;
            posY = (y * caseSize + yOffset) - offset;

            posY = getHeight() - caseSize - posY;
        }
        else{
            posX = (x * caseSize + xOffset) + offset;
            posY = (y * caseSize + yOffset) + offset;
        }

        return new Position(posX, posY);
    }

    public ChessBoard getCHESS_BOARD() {
        return CHESS_BOARD;
    }

    public static ChessPanel getChessPanelClass() {
        return chessPanelClass;
    }

    public int getChessBoardSize() {
        return chessBoardSize;
    }
}
