package GameLogic.Pieces;

import GameLogic.Index;
import GameLogic.Position;

public class Knight extends Piece{
    public Knight(int color, Index index) {
        super(color, index);
    }

    @Override
    protected String getPieceName() {
        return "knight";
    }

    @Override
    public void computeAvailableMoves() {
        availableMoves.clear();

        for (int axis = 0; axis < 2; axis++) {
            for (int direction = -1; direction < 2; direction+=2) {
                for (int i = -1; i < 2; i+=2) {
                    Index checkIndex = new Index(currentIndex.getX() + ((axis == 0) ? direction * 2 : i), currentIndex.getY() + ((axis == 1) ? direction * 2 : i));
                    if(!chessBoard.isInGrid(checkIndex) ){
                        continue;
                    }
                    if(chessBoard.getPieceAtIndex(checkIndex) != null) {
                        if (chessBoard.getPieceAtIndex(checkIndex).getPieceTeam() == chessBoard.getWhomTurn()) {
                            continue;
                        }
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
