package GameLogic.Pieces;

import GameLogic.Position;

public class King extends Piece {
    public King(int color, Position position) {
        super(color, position);
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
                if(x == 0 && y == 0) {
                    continue;
                }
                else{
                    Position surroundingPosition = new Position(currentPosition.getX() + x, currentPosition.getY() + y);
                    if(chessBoard.isInGrid(surroundingPosition)){
                        if(chessBoard.getPieceAtPosition(surroundingPosition) == null || chessBoard.getPieceAtPosition(surroundingPosition).getPieceTeam() != chessBoard.getWhomTurn()){
                            availableMoves.add(surroundingPosition);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onMovement() {

    }
}
