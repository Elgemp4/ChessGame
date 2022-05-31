package Logic.Pieces;


import Logic.Index;

public class Pawn extends Piece{
    public Pawn(int color, Index index) {
        super(color, index);
    }

    @Override
    protected String getPieceName() {
        return "pawn";
    }

    @Override
    public void computeAvailableMoves() {
        availableMoves.clear();

        Index frontPosition = new Index(currentIndex.getX(), currentIndex.getY() + pieceTeam);
        Index doubleFrontPosition = new Index(currentIndex.getX(), currentIndex.getY() + pieceTeam * 2);

        if(chessBoard.isInGrid(frontPosition)){
            if(chessBoard.getPieceAtIndex(frontPosition) == null){
                availableMoves.add(frontPosition);
                if(!hasMoved && chessBoard.getPieceAtIndex(doubleFrontPosition) == null){
                    availableMoves.add(doubleFrontPosition);
                }
            }
        }

        //Prise de pièces sur le côté
        for (int i = -1; i <= 1; i+=2) {
            Index cornerPosition = new Index(currentIndex.getX() + i, currentIndex.getY() + pieceTeam);

            if(chessBoard.isInGrid(cornerPosition)){
                if(chessBoard.getPieceAtIndex(cornerPosition) != null && chessBoard.getPieceAtIndex(cornerPosition).getPieceTeam() != chessBoard.getWhomTurn()){
                    availableMoves.add(cornerPosition);
                }
            }
        }
    }

    @Override
    protected void onMovement() {

    }
}
