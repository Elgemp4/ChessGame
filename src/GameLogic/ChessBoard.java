package GameLogic;

import GameLogic.Pieces.*;


public class ChessBoard {
    private static ChessBoard chessBoardClass;

    private Piece[][] chessBoard;

    private Piece selectedPiece = null;

    private int whomTurn = Piece.WHITE;

    public ChessBoard() {
        chessBoardClass = this;

        placePiecesOnBoard();

        computeMoves();
    }

    private void placePiecesOnBoard() {
        chessBoard = new Piece[8][8];
        for (int color = -1; color <= 1; color+=2) {

            int firstLine = (color == Piece.WHITE) ? 7 : 0;
            int secondLine = (7 + color) % 7;

            //Ligne de pions
            for (int x = 0; x <= 7; x++) {
                chessBoard[secondLine][x] = new Pawn(color, new Index(x, secondLine));
            }

            //Tours
            for (int x = 0; x <= 7; x+=7) {
                chessBoard[firstLine][x] = new Rook(color, new Index(x, firstLine));
            }

            //Chevaliers
            for (int x = 1; x <= 6; x+=5) {
                chessBoard[firstLine][x] = new Knight(color, new Index(x, firstLine));
            }

            //Fous
            for (int x = 2; x <= 5; x+=3) {
                chessBoard[firstLine][x] = new Bishop(color, new Index(x, firstLine));
            }

            //Roi
            chessBoard[firstLine][4] = new King(color, new Index(4, firstLine));

            //Reine
            chessBoard[firstLine][3] = new Queen(color, new Index(3, firstLine));
        }
    }


    public void handleClick(Index clickedIndex, boolean canSelect) {
        if(!isInGrid(clickedIndex)){
            return;
        }

        Piece pieceWhereClicked = getPieceAtIndex(clickedIndex);
        Piece selectedPiece = getSelectedPiece();

        if(isInSelectionMode()) {
            if(selectedPiece.isAValidMove(clickedIndex)){
                if(isEmpty(pieceWhereClicked) || pieceWhereClicked.getPieceTeam() != whomTurn){
                    movePiece(selectedPiece, clickedIndex);
                    computeMoves();
                    return;
                }
            }
        }

        if(canSelect){
            setSelectedPiece(pieceWhereClicked);
        }

    }

    public void computeMoves() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (chessBoard[y][x] != null){
                    chessBoard[y][x].computeAvailableMoves();
                }
            }
        }
    }

    public void movePiece(Piece piece, Index index) {
        if(piece.isAValidMove(index)){
            Piece pieceWhereMove = getPieceAtIndex(index);

            if(pieceWhereMove!=null){
                if(pieceWhereMove instanceof King){
                    //TODO Gameover
                    placePiecesOnBoard();
                    selectedPiece = null;
                    return;
                }
            }

            removePiece(piece);

            piece.setCurrentIndex(index);

            addPiece(piece);

            whomTurn*=-1;
        }

        selectedPiece = null;
    }

    public boolean canPieceBeSelected(Piece piece) {
        if(piece == null) {
            return true;
        }
        else{
            return piece.getPieceTeam() == whomTurn;
        }
    }

    private void removePiece(Piece piece) {
        Index pieceIndex = piece.getCurrentIndex();

        chessBoard[pieceIndex.getY()][pieceIndex.getX()] = null;
    }

    private void addPiece(Piece piece){
        Index pieceIndex = piece.getCurrentIndex();

        chessBoard[pieceIndex.getY()][pieceIndex.getX()] = piece;
    }

    public Piece getPieceAtIndex(Index index) {
        return chessBoard[index.getY()][index.getX()];
    }

    public boolean isInGrid(Index index) {

        return !(index.getX() < 0 || index.getX() > 7 || index.getY() < 0 || index.getY() > 7);
    }

    public boolean isInSelectionMode() {
        return selectedPiece != null;
    }

    public boolean isEmpty(Position position) {
        return chessBoard[position.getY()][position.getX()] == null;
    }

    public boolean isEmpty(Piece piece) {
        return piece == null;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece piece) {
        if(canPieceBeSelected(piece)) {
            this.selectedPiece = piece;
        }
        else{
            this.selectedPiece = null;
        }
    }

    public Piece[][] getChessBoardArray() {
        return chessBoard;
    }



    public int getWhomTurn() {
        return whomTurn;
    }

    public static ChessBoard getChessBoardClass() {
        return chessBoardClass;
    }
}