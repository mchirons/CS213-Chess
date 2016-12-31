package chess;

import java.util.ArrayList;
import java.util.ListIterator;
/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Knight extends Piece{

	public Knight(String color){
		this.type = Type.KNIGHT;
		this.color = color;
	}

	public String toString()
	{
		if(this.color.equals("black")) return "bN";
		else return "wN";
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
		int cT = move.getToSquare().getColumn();
		int rT = move.getToSquare().getRow();
		Square[][] squares = board.getSquares();

		ArrayList<Square> allPossible = new ArrayList<Square>();
		try{

			int [][] coord = {{r + 1, c + 2},
					{r + 1, c - 2},
					{r - 1, c + 2},
					{r - 1, c - 2},
					{r + 2, c + 1},
					{r + 2, c - 1},
					{r - 2, c + 1},
					{r - 2, c - 1}};



		for(int i = 0; i < 8; i++)
		{
			if(!(coord[i][0] < 0 || coord[i][0] > 7 || coord[i][1] < 0 || coord[i][1] > 7))
			{
				allPossible.add(squares[coord[i][0]][coord[i][1]]);
			}
		}

			//Remove squares that piece CAN'T move to
			ListIterator<Square> iterator = allPossible.listIterator();

			while (iterator.hasNext()){
				Square square = iterator.next();
				if (square.piece != null){
					if (square.piece.color.equals(this.color)){
						iterator.remove();
					}
				}
			}

			//Generate list of valid moves from all possible squares
			ArrayList<Move> validMoves = new ArrayList<Move>();
			for (int i = 0; i < allPossible.size(); i++){
				validMoves.add(new Move(move.getFromSquare(), allPossible.get(i), this));
			}

			return validMoves;

		}catch (IndexOutOfBoundsException e){
			//System.out.println("Index out of bounds in knight");
			return null;
		}
	}

}
