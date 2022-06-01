package Logic.Pieces;

import Logic.Index;

public class Bishop extends Piece{
    public Bishop(int color, Index index) {
        super(color, index);
    }

    @Override
    protected String getPieceName() {
        return "bishop";
    }

    @Override
    public void computeAvailableMoves() {
        availableMoves.clear();

        for (int xDirection = -1; xDirection < 2; xDirection+=2) {
            for (int yDirection = -1; yDirection < 2; yDirection+=2) {

                Index checkIndex = currentIndex;
                while(true){
                    checkIndex = new Index(checkIndex.getX() + xDirection, checkIndex.getY() + yDirection);
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
    public void onMovement() {

    }
}
