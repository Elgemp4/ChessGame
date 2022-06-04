package Logic.Pieces;


import GUI.MainWindow;
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
                if(chessBoard.getPieceAtIndex(cornerPosition) != null){
                    availableMoves.add(cornerPosition);
                }
            }
        }
    }

    @Override
    public void onMovement() {
        int firstLine = (pieceTeam == Piece.WHITE) ? 0 : 7;

        if(currentIndex.getY() == firstLine) {
            MainWindow.getMainWindowClass().getPromotionSelection().showSelectionDialog(this);

            chessBoard.removePiece(this);
            chessBoard.addPiece(MainWindow.getMainWindowClass().getPromotionSelection().getChoosenPromotion());
        }
    }
}
