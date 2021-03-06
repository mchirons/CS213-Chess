package chess;

import java.util.ArrayList;
/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Bishop extends Piece {

	public Bishop(String color){
		this.type = Type.BISHOP;
		this.color = color;
	}

	public String toString()
	{
		if(this.color.equals("black")) return "bB";
		else return "wB";
	}

	public boolean isMoveValid(ArrayList<Move> validMoves, Move move, Board board){


		if (validMoves == null){
			return false;
		}

		// Compare this move to the valid moves
		for (int i = 0; i < validMoves.size(); i++) {
			if (move.compareTo(validMoves.get(i)) == 0) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Move> validMoves(Move move, Board board){
		int c = move.getFromSquare().getColumn();
		int r = move.getFromSquare().getRow();

		Square[][] squares = board.getSquares();
		ArrayList<Square> allPossible = new ArrayList<Square>();
		try{
			//Generate all possible squares piece COULD move to

			//moving diagonally to top left
			for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--){
				if (squares[i][j].piece == null) {
					allPossible.add(squares[i][j]);
				}
				else if (!squares[i][j].piece.color.equals(this.color)){
					allPossible.add(squares[i][j]);
					break;
				}
				else{
					break;
				}
			}
			//moving diagonally to top right
			for (int i = r - 1, j = c + 1; i >= 0 && j < 8; i--, j++){
				if (squares[i][j].piece == null) {
					allPossible.add(squares[i][j]);
				}
				else if (!squares[i][j].piece.color.equals(this.color)){
					allPossible.add(squares[i][j]);
					break;
				}
				else{
					break;
				}
			}
			//moving diagonally to bottom left
			for (int i = r + 1, j = c - 1; i < 8 && j >= 0 ; i++, j-- ){
				if (squares[i][j].piece == null) {
					allPossible.add(squares[i][j]);
				}
				else if (!squares[i][j].piece.color.equals(this.color)){
					allPossible.add(squares[i][j]);
					break;
				}
				else{
					break;
				}
			}
			//moving diagonally to bottom right
			for (int i = r + 1, j = c + 1; i < 8 && j < 8; i++, j++){
				if (squares[i][j].piece == null) {
					allPossible.add(squares[i][j]);
				}
				else if (!squares[i][j].piece.color.equals(this.color)){
					allPossible.add(squares[i][j]);
					break;
				}
				else{
					break;
				}
			}


			//Generate list of valid moves from all possible squares
			ArrayList<Move> validMoves = new ArrayList<Move>();
			for (int i = 0; i < allPossible.size(); i++){
				validMoves.add(new Move(move.getFromSquare(), allPossible.get(i), this));
			}
			return validMoves;


		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}

}
