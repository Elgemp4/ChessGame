package Logic;

import GUI.GamePanel;
import Logic.Pieces.*;


public class ChessBoard {
    private static ChessBoard chessBoardClass;

    private GamePanel gamePanel;

    private Piece[][] chessBoard;

    private Piece selectedPiece = null;

    private int whomTurn = Piece.WHITE;

    private boolean isGameOver = false;

    public ChessBoard() {
        chessBoardClass = this;

        gamePanel = GamePanel.getGamePanelClass();

        placePiecesOnBoard();

        computeAllMoves();
    }

    /**
     * Place les pièces sur le plateau
     */
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
            chessBoard[firstLine][3] = new King(color, new Index(3, firstLine));

            //Reine
            chessBoard[firstLine][4] = new Queen(color, new Index(4, firstLine));
        }
    }

    /**
     * Gère la manière dont le click doit-être interprété en fonction de la situation
     * @param clickedIndex
     * @param canSelect
     */
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
                    computeAllMoves();
                    return;
                }
            }
        }

        if(canSelect){
            setSelectedPiece(pieceWhereClicked);
        }

    }

    /**
     * Calculs tous les déplacements possibles pour toutes les pièces
     */
    public void computeAllMoves() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (chessBoard[y][x] != null){
                    chessBoard[y][x].computeAvailableMoves();
                }
            }
        }
    }

    /**
     * Déplace la pièce fournie à l'index fourni
     * @param piece Pièce à déplacer
     * @param index Position où déplacer la pièce
     */
    public void movePiece(Piece piece, Index index) {
        if(piece.isAValidMove(index)){
            Piece pieceWhereMove = getPieceAtIndex(index);

            if(pieceWhereMove!=null){
                if(pieceWhereMove instanceof King){
                    isGameOver = true;
                }
            }

            removePiece(piece);

            piece.setCurrentIndex(index);

            addPiece(piece);

            gamePanel.repaint();

            piece.onMovement();

            if(!isGameOver){
                whomTurn*=-1;
            }

        }

        selectedPiece = null;
    }

    /**
     * Renvoie vrai si la pièce cliquée peut être sélectionnée
     * @param piece
     * @return
     */
    public boolean canPieceBeSelected(Piece piece) {
        if(piece == null) {
            return true;
        }
        else{
            return piece.getPieceTeam() == whomTurn;
        }
    }

    /**
     * Retire la pièce du jeu
     * @param piece
     */
    public void removePiece(Piece piece) {
        Index pieceIndex = piece.getCurrentIndex();

        chessBoard[pieceIndex.getY()][pieceIndex.getX()] = null;
    }

    /**
     * Ajoute la pièce au jeu
     * @param piece
     */
    public void addPiece(Piece piece){
        Index pieceIndex = piece.getCurrentIndex();

        chessBoard[pieceIndex.getY()][pieceIndex.getX()] = piece;
    }

    /**
     * Redémarre une partie
     */
    public void restartGame() {
        whomTurn = Piece.WHITE;
        selectedPiece = null;
        isGameOver = false;

        placePiecesOnBoard();
        computeAllMoves();

        gamePanel.repaint();
    }

    /**
     * Renvoie la pièce à l'index fourni
     * @param index
     * @return
     */
    public Piece getPieceAtIndex(Index index) {
        return chessBoard[index.getY()][index.getX()];
    }

    /**
     * Renvoie vrai si l'index fourni est dans les limites du plateau
     * @param index
     * @return
     */
    public boolean isInGrid(Index index) {

        return !(index.getX() < 0 || index.getX() > 7 || index.getY() < 0 || index.getY() > 7);
    }

    /**
     * Renvoie vrai si le joueur a sélectionné une pièce
     * @return
     */
    public boolean isInSelectionMode() {
        return selectedPiece != null;
    }

    /**
     * Renvoie vrai si la pièce est null
     * @param piece
     * @return
     */
    public boolean isEmpty(Piece piece) {
        return piece == null;
    }

    /**
     * Renvoie la pièce actuellement sélectionnée
     * @return
     */
    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Définis quelle pièce est séléctionnée
     * @param piece
     */
    public void setSelectedPiece(Piece piece) {
        if(canPieceBeSelected(piece)) {
            this.selectedPiece = piece;
        }
        else{
            this.selectedPiece = null;
        }
    }

    /**
     * Renvoie vrai si le jeu est fini
     * @return
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Renvoie le tableau du jeu
     * @return
     */
    public Piece[][] getChessBoardArray() {
        return chessBoard;
    }

    /**
     * Renvoie la couleur du joueur à qui c'est le tour
     * @return
     */
    public int getWhomTurn() {
        return whomTurn;
    }

    public static ChessBoard getChessBoardClass() {
        return chessBoardClass;
    }



}