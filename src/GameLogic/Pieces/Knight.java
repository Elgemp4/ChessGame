package GameLogic.Pieces;

import GameLogic.Position;

public class Knight extends Piece{
    public Knight(int color, Position position) {
        super(color, position);
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
                    Position checkPosition = new Position(currentPosition.getX() + ((axis == 0) ? direction * 2 : i), currentPosition.getY() + ((axis == 1) ? direction * 2 : i));
                    if(!chessBoard.isInGrid(checkPosition) ){
                        continue;
                    }
                    if(chessBoard.getPieceAtPosition(checkPosition) != null) {
                        if (chessBoard.getPieceAtPosition(checkPosition).getPieceTeam() == chessBoard.getWhomTurn()) {
                            continue;
                        }
                    }
                    availableMoves.add(checkPosition);
                }
            }
        }

    }

    @Override
    protected void onMovement() {

    }
}
