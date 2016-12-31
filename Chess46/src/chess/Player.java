package chess;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Player {

	private ArrayList<Piece> pieces;
	private String color;
	private Board board;
	public boolean isCheck;
	public boolean wantsDraw;
	public boolean wantsResign;
	public String promotionChoice;

	/**
	 * Initializes player objects and creates pieces
	 * for player's side of the board.
	 */
	public Player(String color, Board board){
		this.pieces = new ArrayList<Piece>();
		this.color = color;
		this.board = board;
		Square[][] squares = board.getSquares();
		this.wantsDraw = false;
		if (color.equals("white")){
			pieces.add(squares[6][0].piece);
			pieces.add(squares[6][1].piece);
			pieces.add(squares[6][2].piece);
			pieces.add(squares[6][3].piece);
			pieces.add(squares[6][4].piece);
			pieces.add(squares[6][5].piece);
			pieces.add(squares[6][6].piece);
			pieces.add(squares[6][7].piece);
			pieces.add(squares[7][0].piece);
			pieces.add(squares[7][1].piece);
			pieces.add(squares[7][2].piece);
			pieces.add(squares[7][3].piece);
			pieces.add(squares[7][4].piece);
			pieces.add(squares[7][5].piece);
			pieces.add(squares[7][6].piece);
			pieces.add(squares[7][7].piece);
		}else{
			pieces.add(squares[1][0].piece);
			pieces.add(squares[1][1].piece);
			pieces.add(squares[1][2].piece);
			pieces.add(squares[1][3].piece);
			pieces.add(squares[1][4].piece);
			pieces.add(squares[1][5].piece);
			pieces.add(squares[1][6].piece);
			pieces.add(squares[1][7].piece);
			pieces.add(squares[0][0].piece);
			pieces.add(squares[0][1].piece);
			pieces.add(squares[0][2].piece);
			pieces.add(squares[0][3].piece);
			pieces.add(squares[0][4].piece);
			pieces.add(squares[0][5].piece);
			pieces.add(squares[0][6].piece);
			pieces.add(squares[0][7].piece);
		}
	}

	/**
	 * Generates a move based on user input and returns
	 * a valid move if the input is valid.
	 */
	public Move playTurn(){


		int row, column;
		int count = 0;
		Square to, from;
		Square[][] squares;
		Piece piece;


		Scanner sc = new Scanner(System.in);
		String[] tokens = sc.nextLine().split(" ");
		String f = null;
		String t = null;
		String token1, token2, token3;

		/*
		 * Checks the input for moves, draw, and resign.
		 */
		if (tokens.length == 2){
			token1 = tokens[0].toLowerCase();
			token2 = tokens[1].toLowerCase();
			f = token1;
			t = token2;
		}
		else if (tokens.length == 1){
			token1 = tokens[0].toLowerCase();

			if (token1.toLowerCase().equals("resign")){
				wantsResign = true;
				return null;
			}
			return null;
		}
		else if (tokens.length == 3){
			token1 = tokens[0].toLowerCase();
			token2 = tokens[1].toLowerCase();
			token3 = tokens[2].toLowerCase();
			f = token1;
			t = token2;
			if (token3.toLowerCase().equals("draw")){
				wantsDraw = true;
			}else if (token3.equals("n") || token3.equals("q") ||
					token3.equals("r") || token3.equals("b")){
				promotionChoice = token3;
			}
			else{
				return null;
			}

		}
		else{
			return null;
		}



		squares = board.getSquares();


		try {

			//locate square where piece is
			row =  8 - Integer.parseInt(f.substring(1));
			column = (f.charAt(0) - 96) - 1;
			from = squares[row][column];

			row =  8 - Integer.parseInt(t.substring(1));
			column = (t.charAt(0) - 96) - 1;
			to = squares[row][column];

			if ((from.piece.type == Piece.Type.PAWN) && (row == 0 || row == 7)){
				Pawn pawn = (Pawn)from.piece;
				pawn.isPromoting = true;
			}



			piece = from.piece;
		} catch (ArrayIndexOutOfBoundsException e){
			return null;
		} catch (NumberFormatException e){
			return null;
		} catch (NullPointerException e){
			return null;
		}
		if (piece == null){
			return null;
		}

		return new Move(from, to, piece);

	}

	/**
	 * Returns the player's pieces as an ArrayList.
	 *
	 * @return ArrayList of player's pieces
	 */
	public ArrayList<Piece> getPieces(){
		return this.pieces;
	}
	/**
	 * Passes a reference from the current board to this class.
	 * @param board
	 */
	public void getBoard(Board board){
		this.board = board;
	}
	/**
	 * Returns this player's color to then be used in other classes.
	 *
	 * @return a string denoting the player's color.
	 */
	public String getColor()
	{
		return this.color;
	}

}

