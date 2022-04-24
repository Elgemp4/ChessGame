package GameLogic.Pieces;

import GameLogic.Position;

public class Rook extends Piece{
    public Rook(int color, Position position) {
        super(color, position);
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
                Position checkPosition = currentPosition;
                while(true){
                    checkPosition = new Position(checkPosition.getX() + ((axis == 0) ? direction : 0), checkPosition.getY() + ((axis == 1) ? direction : 0));
                    if(!chessBoard.isInGrid(checkPosition) ){
                        break;
                    }
                    if(chessBoard.getPieceAtPosition(checkPosition) != null) {
                        if (chessBoard.getPieceAtPosition(checkPosition).getPieceTeam() != chessBoard.getWhomTurn()) {
                            availableMoves.add(checkPosition);
                        }
                        break;
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
