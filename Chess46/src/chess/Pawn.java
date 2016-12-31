package chess;

import java.util.ArrayList;
import java.util.ListIterator;
/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Pawn extends Piece {

	public boolean hasMoved;
	public boolean hasDoubleJumped;
	public boolean isPromoting;

	public Pawn(String color) {
		this.type = Type.PAWN;
		this.color = color;
		this.hasMoved = false;
	}

	public String toString() {
		if (this.color.equals("black"))
			return "bp";
		else
			return "wp";
	}

	public boolean isMoveValid(ArrayList<Move> validMoves, Move move, Board board){

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

	public ArrayList<Move> validMoves(Move move, Board board) {

		int c = move.getFromSquare().getColumn();
		int r = move.getFromSquare().getRow();


		Square[][] squares = board.getSquares();
		ArrayList<Square> allPossible = new ArrayList<Square>();
		try {
			// Generate all possible squares white piece COULD move to
			if (this.color.equals("white")) {
				if (hasMoved == false) {
					allPossible.add(squares[r - 2][c]);
					/*
					if (c == 0){
						if (squares[r - 3][c + 1].piece != null && squares[r - 3][c + 1].piece.color.equals("black")){
							allPossible.add(squares[r - 3][c + 1]);
						}
					}
					else if (c == 7){
						if (squares[r - 3][c - 1].piece != null && squares[r - 3][c - 1].piece.color.equals("black")){
							allPossible.add(squares[r - 3][c - 1]);
						}
					}
					else{
						if (squares[r - 3][c + 1].piece != null && squares[r - 3][c + 1].piece.color.equals("black")){
							allPossible.add(squares[r - 3][c + 1]);
						}
						if (squares[r - 3][c - 1].piece != null && squares[r - 3][c - 1].piece.color.equals("black")){
							allPossible.add(squares[r - 3][c - 1]);
						}
					}
					*/

				}
				if (c == 0) {
					allPossible.add(squares[r - 1][c + 1]);
					allPossible.add(squares[r - 1][c]);
				} else if (c == 7) {
					allPossible.add(squares[r - 1][c - 1]);
					allPossible.add(squares[r - 1][c]);
				} else {
					allPossible.add(squares[r - 1][c - 1]);
					allPossible.add(squares[r - 1][c + 1]);
					allPossible.add(squares[r - 1][c]);
				}
			} else {
				if (hasMoved == false) {
					allPossible.add(squares[r + 2][c]);
					/*
					if (c == 0){
						if (squares[r + 3][c + 1].piece != null && squares[r + 3][c + 1].piece.color.equals("white")){
							allPossible.add(squares[r + 3][c + 1]);
						}
					}
					else if (c == 7){
						if (squares[r + 3][c - 1].piece != null && squares[r + 3][c - 1].piece.color.equals("white")){
							allPossible.add(squares[r + 3][c - 1]);
						}
					}
					else{
						if (squares[r + 3][c + 1].piece != null && squares[r + 3][c + 1].piece.color.equals("white")){
							allPossible.add(squares[r + 3][c + 1]);
						}
						if (squares[r + 3][c - 1].piece != null && squares[r + 3][c - 1].piece.color.equals("white")){
							allPossible.add(squares[r + 3][c - 1]);
						}
					}
					*/

				}
				if (c == 0) {
					allPossible.add(squares[r + 1][c + 1]);
					allPossible.add(squares[r + 1][c]);
				} else if (c == 7) {
					allPossible.add(squares[r + 1][c - 1]);
					allPossible.add(squares[r + 1][c]);
				} else {
					allPossible.add(squares[r + 1][c + 1]);
					allPossible.add(squares[r + 1][c - 1]);
					allPossible.add(squares[r + 1][c]);
				}
			}

			// Remove squares that piece CAN'T move to
			ListIterator<Square> iterator = allPossible.listIterator();

			while (iterator.hasNext()) {
				Square square = iterator.next();
				if (square.getColumn() != c) {
					if (square.piece != null) {
						if (square.piece.color.equals(this.color)) {
							iterator.remove();
						}
					} else {
						iterator.remove();
					}
				}
				else {
					if (square.piece != null) {
						iterator.remove();
					}
				}
			}

			// Generate list of valid moves from all possible squares
			ArrayList<Move> validMoves = new ArrayList<Move>();
			for (int i = 0; i < allPossible.size(); i++) {
				validMoves.add(new Move(move.getFromSquare(), allPossible.get(i), this));
			}

			return validMoves;

		} catch (ArrayIndexOutOfBoundsException e) {
			//System.out.println("Index out of bounds in pawn");
			return null;
		}

	}

}
