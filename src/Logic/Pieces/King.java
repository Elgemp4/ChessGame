package Logic.Pieces;

import Logic.Index;
import Logic.Position;

import java.util.ArrayList;

public class King extends Piece {
    private ArrayList<Index> castlingMoves;

    public King(int color, Index index) {
        super(color, index);

        castlingMoves = new ArrayList<>();
    }

    @Override
    protected String getPieceName() {
        return "king";
    }

    @Override
    public void computeAvailableMoves() {
        availableMoves.clear();

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if(!(x == 0 && y == 0)){
                    Index surroundingIndex = new Index(currentIndex.getX() + x, currentIndex.getY() + y);
                    if(chessBoard.isInGrid(surroundingIndex)){
                        if(chessBoard.getPieceAtIndex(surroundingIndex) == null || chessBoard.getPieceAtIndex(surroundingIndex).getPieceTeam() != chessBoard.getWhomTurn()){
                            availableMoves.add(surroundingIndex);
                        }
                    }
                }
            }
        }

        computeCastling();
    }

    /**
     * Essaye de voir si un roque est possible
     */
    private void computeCastling() {
        castlingMoves.clear();

        if(!this.hasMoved){ //Si ce pion n'a jamais bougé
            for (int extremity = 0; extremity < 8; extremity+=7) { //Boucler à chaque extrémité
                Index searchIndex = new Index(extremity, chessBoard.getFirstLineForColor(pieceTeam));
                Piece searchPiece = chessBoard.getPieceAtIndex(searchIndex);

                if(searchPiece instanceof Rook) { //Si la pièce à l'extrémité est une tour
                    Rook rook = (Rook) searchPiece;
                    if (!rook.hasMoved){ //Et qu'elle n'a jamais bougée
                        int minX = Math.min(rook.getCurrentIndex().getX(), this.getCurrentIndex().getX());
                        int maxX = Math.max(rook.getCurrentIndex().getX(), this.getCurrentIndex().getX());

                        boolean isSpaceBetweenIsEmpty = true;

                        for (int checkForEmptyX = minX + 1; checkForEmptyX < maxX; checkForEmptyX++) { //Et qu'il n'y pas de pièce entre la tour et le roi
                            if(!chessBoard.isEmpty(chessBoard.getPieceAtIndex(new Index(checkForEmptyX, searchIndex.getY())))){
                                isSpaceBetweenIsEmpty = false;
                                break;
                            }
                        }

                        if(isSpaceBetweenIsEmpty){
                            System.out.println("espace vide");

                            int xOffset = this.getCurrentIndex().getX() == maxX ? -2 : 2;

                            Index index = new Index(currentIndex.getX(), currentIndex.getY());
                            index.setX(index.getX() + xOffset);

                            castlingMoves.add(index);
                        }
                    }
                }
            }
        }
    }

    public boolean isACastlingMove(Index index) {
        return castlingMoves.contains(index);
    }

    public ArrayList<Index> getCastlingMoves() {
        return castlingMoves;
    }

    @Override
    public void onMovement() {

    }
}
