package Logic.Pieces;

import Logic.Index;

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
                if (!(x == 0 && y == 0)) {
                    Index surroundingIndex = new Index(currentIndex.getX() + x, currentIndex.getY() + y);
                    if (chessBoard.isInGrid(surroundingIndex)) {
                        availableMoves.add(surroundingIndex);
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

        if (!this.hasMoved) { //Si ce pion n'a jamais bougé
            for (int extremity = 0; extremity < 8; extremity += 7) { //Boucler à chaque extrémité
                Index searchIndex = new Index(extremity, chessBoard.getFirstLineForColor(pieceTeam));
                Piece possibleRook = chessBoard.getPieceAtIndex(searchIndex);

                if (possibleRook == null) {
                    return;
                }

                if (!possibleRook.hasMoved) { //Si la pièce à l'extrémité n'a jamais bougée on sait que c'est une tour
                    Rook rook = (Rook) possibleRook;
                    if (isSpaceBetweenEmpty(rook.getCurrentIndex().getX(), this.getCurrentIndex().getX(), searchIndex.getY())) {
                        int xOffset = this.getCurrentIndex().getX() > rook.getCurrentIndex().getX() ? -2 : 2;

                        castlingMoves.add(new Index(currentIndex.getX() + xOffset, currentIndex.getY()));
                    }
                }
            }
        }
    }

    private boolean isSpaceBetweenEmpty(int firstX, int secondX, int ySearch) {
        int minX = Math.min(firstX, secondX);
        int maxX = Math.max(firstX, secondX);

        for (int checkForEmptyX = minX + 1; checkForEmptyX < maxX; checkForEmptyX++) { //Et qu'il n'y pas de pièce entre la tour et le roi
            if (!chessBoard.isEmpty(chessBoard.getPieceAtIndex(new Index(checkForEmptyX, ySearch)))) {
                return false;
            }
        }
        return true;
    }

//    private boolean isSpaceBetweenAttack

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
