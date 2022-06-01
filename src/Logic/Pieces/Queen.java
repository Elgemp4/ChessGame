package Logic.Pieces;

import Logic.Index;

public class Queen extends Piece{
    public Queen(int color, Index index) {
        super(color, index);
    }

    @Override
    protected String getPieceName() {
        return "queen";
    }

    @Override
    public void computeAvailableMoves() {
        availableMoves.clear();

        for (int turn = 0; turn < 2; turn++) {
            for (int direction = -1; direction < 2; direction+=2) {
                Index checkIndex = currentIndex;
                while(true){
                    checkIndex = new Index(checkIndex.getX() + ((turn == 0) ? direction : 0), checkIndex.getY() + ((turn == 1) ? direction : 0));
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
