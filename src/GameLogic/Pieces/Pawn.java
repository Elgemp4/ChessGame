package GameLogic.Pieces;


import GameLogic.Position;

public class Pawn extends Piece{
    public Pawn(int color, Position position) {
        super(color, position);
    }

    @Override
    protected String getPieceName() {
        return "pawn";
    }

    @Override
    public void computeAvailableMoves() {
        availableMoves.clear();

        Position frontPosition = new Position(currentPosition.getX(), currentPosition.getY() + pieceTeam);
        Position doubleFrontPosition = new Position(currentPosition.getX(), currentPosition.getY() + pieceTeam * 2);

        if(chessBoard.isInGrid(frontPosition)){
            if(chessBoard.getPieceAtPosition(frontPosition) == null){
                availableMoves.add(frontPosition);
                if(!hasMoved && chessBoard.getPieceAtPosition(doubleFrontPosition) == null){
                    availableMoves.add(doubleFrontPosition);
                }
            }
        }

        //Prise de pièces sur le côté
        for (int i = -1; i <= 1; i+=2) {
            Position cornerPosition = new Position(currentPosition.getX() + i, currentPosition.getY() + pieceTeam);

            if(chessBoard.isInGrid(cornerPosition)){
                if(chessBoard.getPieceAtPosition(cornerPosition) != null && chessBoard.getPieceAtPosition(cornerPosition).getPieceTeam() != chessBoard.getWhomTurn()){
                    availableMoves.add(cornerPosition);
                }
            }
        }
    }

    @Override
    protected void onMovement() {

    }
}
