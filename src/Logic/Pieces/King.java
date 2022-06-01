package Logic.Pieces;

import Logic.Index;

public class King extends Piece {
    public King(int color, Index index) {
        super(color, index);
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
    }

    @Override
    public void onMovement() {

    }
}
