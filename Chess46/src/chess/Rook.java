package chess;

import java.util.ArrayList;
/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Rook extends Piece{

	public boolean hasMoved = false;

	public Rook(String color){
		this.type = Type.ROOK;
		this.color = color;

	}

	public String toString()
	{
		if(this.color.equals("black")) return "bR";
		else return "wR";
	}

	public boolean isMoveValid(ArrayList<Move> validMoves, Move move, Board board){
		validMoves = validMoves(move, board);

		if (validMoves == null){
			return false;
		}

		// Compare this move to the valid moves
		for (int i = 0; i < validMoves.size(); i++) {
			if (move.compareTo(validMoves.get(i)) == 0) {
				hasMoved = true;
				return true;
			}
		}
		return false;
	}

	public ArrayList<Move> validMoves(Move move, Board board){

		int c = move.getFromSquare().getColumn();
		int r = move.getFromSquare().getRow();
		King king = null;
		boolean isCastling = false;

		Square[][] squares = board.getSquares();
		ArrayList<Square> allPossible = new ArrayList<Square>();

		try{

			//To see if rook should be allowed to jump over king.
			for(int i = 0; i < squares.length; i++)
			{
				for(int j = 0; j < squares[i].length; j++)
				{
					if(squares[i][j].piece != null)
					{
						if(squares[i][j].piece.color.equals(this.color) && squares[i][j].piece.type.equals(Piece.Type.KING))
						{
							king = (King) squares[i][j].piece;
							if(king.isCastling) isCastling = true;
						}
					}
				}
			}
			//Generate all possible squares piece COULD move to
			for (int i = r + 1; i < 8; i++){
				if (squares[i][c].piece == null) {
					allPossible.add(squares[i][c]);
				}
				else if (!squares[i][c].piece.color.equals(this.color)){
					allPossible.add(squares[i][c]);
					break;
				}
				else{
					break;
				}
			}

			for(int i = r - 1; i >= 0; i--){
				if (squares[i][c].piece == null) {
					allPossible.add(squares[i][c]);
				}
				else if (!squares[i][c].piece.color.equals(this.color)){
					allPossible.add(squares[i][c]);
					break;
				}
				else{
					break;
				}
			}

			for (int i = c + 1; i < 8; i++){
				if (squares[r][i].piece == null) {
					allPossible.add(squares[r][i]);
				}
				else if(squares[r][i].piece.type.equals(Piece.Type.KING) && squares[r][i].piece.color.equals(this.color) && isCastling)
				{
					allPossible.add(squares[r][i]);
				}
				else if (!squares[r][i].piece.color.equals(this.color)){
					allPossible.add(squares[r][i]);
					break;
				}
				else{
					break;
				}
			}

			for (int i = c - 1; i >= 0; i--){
				if (squares[r][i].piece == null) {
					allPossible.add(squares[r][i]);
				}
				else if(squares[r][i].piece.type.equals(Piece.Type.KING) && squares[r][i].piece.color.equals(this.color) && isCastling)
				{
					allPossible.add(squares[r][i]);
				}
				else if (!squares[r][i].piece.color.equals(this.color)){
					allPossible.add(squares[r][i]);
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

		}catch (ArrayIndexOutOfBoundsException e){
			//System.out.println("Index out of bounds in rook");
			return null;
		}
	}

}
