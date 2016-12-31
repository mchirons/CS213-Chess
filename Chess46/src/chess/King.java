package chess;

import java.util.ArrayList;
import java.util.ListIterator;
/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class King extends Piece{


	public boolean hasMoved;
	public boolean isCastling;
	public boolean isCastlingLR;
	public boolean isCastlingRR;

	public King(String color){
		this.type = Type.KING;
		this.color = color;
		this.hasMoved = false;
	}

	public String toString()
	{
		if(this.color.equals("black")) return "bK";
		else return "wK";
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
		int [][] coord =   {{r - 1, c},
							{r - 1, c + 1},
							{r - 1, c - 1},
							{r, c + 1},
							{r, c - 1},
							{r + 1, c},
							{r + 1, c + 1},
							{r + 1, c - 1}};
		try{
			//Generate all possible squares white piece COULD move to

			for (int i = 0; i < 8; i++){
				if (!(coord[i][0] < 0 || coord[i][0] > 7 || coord[i][1] < 0 || coord[i][1] > 7)){
					allPossible.add(squares[coord[i][0]][coord[i][1]]);
					//System.out.println("possible square");
				}

			}

			//Remove squares that piece CAN'T move to
			ListIterator<Square> iterator = allPossible.listIterator();

			while (iterator.hasNext()){
				Square square = iterator.next();
				if (square.piece != null){
					if (square.piece.color.equals(this.color)){
						iterator.remove();
						//System.out.println("removed square");
					}

				}

			}

			//Generate list of valid moves from all possible squares
			ArrayList<Move> validMoves = new ArrayList<Move>();

			for (int i = 0; i < allPossible.size(); i++){
				validMoves.add(new Move(move.getFromSquare(), allPossible.get(i), this));
			}

			if (isCastling){
				validMoves.add(move);
			}
			//System.out.println("valid moves size: " + validMoves.size());
			return validMoves;


		}catch (ArrayIndexOutOfBoundsException e){
			//System.out.println("Index out of bounds in king");
			return null;
		}

	}

	/**
	 * This method accepts a Move object and a Board object. It determines
	 * whether or not the King can be castled. If it can be castled, then the
	 * King class will have a reference to its instantiated object claiming
	 * that it can be castled.
	 *
	 * @param move
	 * @param board
	 * @return boolean value depicting whether or not King can be castled
	 */
	public boolean isCastling(Move move, Board board)
	{
		Square squares [][] = board.getSquares();
		int r,count,c1,c2;
		r = move.getFromSquare().getRow();
		count = 0;
		c1 = move.getFromSquare().getColumn();
		c2 = move.getToSquare().getColumn();

		//Being extra cautious about color checking
		if(!move.getFromSquare().piece.type.equals(Piece.Type.KING))
		{
			return false;
		}
		if(r != move.getToSquare().getRow()) return false;

		//Can't castle if number of squares between rook and king is anything other than three or four

		if(c1 - c2 == 2)
		{
			if(squares[r][0].piece != null)
			{
				if(!squares[r][0].piece.type.equals(Piece.Type.ROOK)) return false;
			}
			for(int i = 0; i <= c1; i++)
			{
				if(squares[r][i].piece != null) count++;
			}
		}
		else if(c1 - c2 == -2)
		{
			if(squares[r][7].piece != null)
			{
				if(!squares[r][7].piece.type.equals(Piece.Type.ROOK)) return false;
			}
			for(int i = 7; i >= c1; i--)
			{
				if(squares[r][i].piece != null) count++;
			}
		}
		else return false;
		//Can't castle without having at least two or three pieces on rank
		if(count != 2) return false;

		//Want to castle on right rook
		if(c1 < c2)
		{
			if(squares[r][0].piece.type.equals(Piece.Type.ROOK))
			{
				Rook rook = (Rook)squares[r][7].piece;
				if(rook.hasMoved == false)
				{
					isCastling = true;
					isCastlingRR = true;
					return true;
				}
			}
		}

		//Want to castle on left rook
		if(c1 > c2)
		{
			if(squares[r][7].piece.type.equals(Piece.Type.ROOK))
			{
				Rook rook = (Rook)squares[r][0].piece;
				if(rook.hasMoved == false)
				{
					isCastling = true;
					isCastlingLR = true;
					return true;
				}
			}
		}

		return false;
	}
}
