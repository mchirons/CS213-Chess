package chess;

import java.util.ArrayList;

/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public abstract class Piece {

	/**
	 * Each class of Pawn,  Rook, Bishop, Knight, Queen, and King
	 * extends the Piece class and is therefore enumerated as such.
	 *
	 */
	public enum Type
	{
		PAWN, ROOK, BISHOP, KNIGHT, QUEEN, KING
	}

	public Type type;
	public String color;

	/**
	 * Each class that implements this method takes in a Move object and
	 * a Board object. This method returns an ArrayList of valid moves to
	 * then be used for methods such as isMoveValid.
	 *
	 * @param move
	 * @param board
	 * @return ArrayList of validMoves
	 */
	public abstract ArrayList<Move> validMoves(Move move, Board board);

	/**
	 * Generates a list of valid moves and compares given move to the list
	 * returns true if given move is in the list of valid moves.
	 *
	 * @return boolean value depicting whether or not the move is valid
	 */
	public abstract boolean isMoveValid(ArrayList<Move> validMoves, Move move, Board board);



}
