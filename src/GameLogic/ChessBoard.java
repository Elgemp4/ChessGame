package GameLogic;

import GameLogic.Pieces.*;


public class ChessBoard {
    private static ChessBoard thisClass;

    private Piece[][] chessBoard;

    private Piece selectedPiece = null;

    private int whomTurn = Piece.WHITE;

    public ChessBoard() {
        thisClass = this;

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
                chessBoard[secondLine][x] = new Pawn(color, new Position(x, secondLine));
            }

            //Tours
            for (int x = 0; x <= 7; x+=7) {
                chessBoard[firstLine][x] = new Rook(color, new Position(x, firstLine));
            }

            //Chevaliers
            for (int x = 1; x <= 6; x+=5) {
                chessBoard[firstLine][x] = new Knight(color, new Position(x, firstLine));
            }

            //Fous
            for (int x = 2; x <= 5; x+=3) {
                chessBoard[firstLine][x] = new Bishop(color, new Position(x, firstLine));
            }

            //Roi
            chessBoard[firstLine][3] = new King(color, new Position(3, firstLine));

            //Reine
            chessBoard[firstLine][4] = new Queen(color, new Position(4, firstLine));
        }
    }


    public void handleClick(Position clickedPosition, boolean canSelect) {
        Piece pieceWhereClicked = getPieceAtPosition(clickedPosition);
        Piece selectedPiece = getSelectedPiece();

        if(selectedPiece != null) {
            if(selectedPiece.isAValidMove(clickedPosition)){
                if(pieceWhereClicked == null || pieceWhereClicked.getPieceTeam() != selectedPiece.getPieceTeam()){
                    movePiece(selectedPiece, clickedPosition);
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

    public void movePiece(Piece piece, Position position) {
        if(piece.isAValidMove(position)){
            Piece pieceWhereMove = getPieceAtPosition(position);

            if(pieceWhereMove!=null){
                if(pieceWhereMove instanceof King){
                    //TODO Gameover
                    placePiecesOnBoard();
                    selectedPiece = null;
                    return;
                }
            }

            removePiece(piece);

            piece.setCurrentPosition(position);

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
        Position piecePosition = piece.getCurrentPosition();

        chessBoard[piecePosition.getY()][piecePosition.getX()] = null;
    }

    private void addPiece(Piece piece){
        Position piecePosition = piece.getCurrentPosition();

        chessBoard[piecePosition.getY()][piecePosition.getX()] = piece;
    }

    public Piece getPieceAtPosition(Position position) {
        return chessBoard[position.getY()][position.getX()];
    }

    public boolean isInGrid(Position position) {

        return !(position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7);
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

    public static ChessBoard getThisClass() {
        return thisClass;
    }
}