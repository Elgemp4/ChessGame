package GameLogic.Pieces;

import GameLogic.Position;

public class Bishop extends Piece{
    public Bishop(int color, Position position) {
        super(color, position);
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

                Position checkPosition = currentPosition;
                while(true){
                    checkPosition = new Position(checkPosition.getX() + xDirection, checkPosition.getY() + yDirection);
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
