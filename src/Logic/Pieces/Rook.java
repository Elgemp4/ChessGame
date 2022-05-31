package Logic.Pieces;

import Logic.Index;

public class Rook extends Piece{
    public Rook(int color, Index index) {
        super(color, index);
    }

    @Override
    protected String getPieceName() {
        return "rook";
    }

    @Override
    public void computeAvailableMoves() {
        availableMoves.clear();

        for (int axis = 0; axis < 2; axis++) {
            for (int direction = -1; direction < 2; direction+=2) {
                Index checkIndex = currentIndex;
                while(true){
                    checkIndex = new Index(checkIndex.getX() + ((axis == 0) ? direction : 0), checkIndex.getY() + ((axis == 1) ? direction : 0));
                    if(!chessBoard.isInGrid(checkIndex) ){
                        break;
                    }
                    if(chessBoard.getPieceAtIndex(checkIndex) != null) {
                        if (chessBoard.getPieceAtIndex(checkIndex).getPieceTeam() != chessBoard.getWhomTurn()) {
                            availableMoves.add(checkIndex);
                        }
                        break;
                    }
                    availableMoves.add(checkIndex);
                }
            }
        }

    }

    @Override
    protected void onMovement() {

    }
}
