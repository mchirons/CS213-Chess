package chess;

/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Board {

	private Square[][] squares;

	public Board(){

		this.squares = new Square[8][8];
		String color = "black";

		for (int i = 0; i < 8; i++){
			if (i % 2 == 0){
				for (int j = 0; j < 8; j++){
					if (j % 2 == 0){
						color = "white";
					}
					else{
						color = "black";
					}
					squares[i][j] = new Square(i, j, color);
				}
			}
			else{
				for (int j = 0; j < 8; j++){
					if (j % 2 == 1){
						color = "white";
					}
					else{
						color = "black";
					}
					squares[i][j] = new Square(i, j, color);
				}
			}
		}
		/*
		 * To initialize Square Array with pawns.
		 */
		for(int i = 0; i < 8; i++)
		{
			if( i == 1)
			{
				for(int j = 0; j < 8; j++)
				{
					squares[i][j].placePiece(new Pawn("black"));
				}
			}
			else if( i == 6)
			{
				for(int j = 0; j < 8; j++)
				{
					squares[i][j].placePiece(new Pawn("white"));
				}
			}
		}
		/*
		 * To initialize Square Array with non-pawns.
		 */
		for(int i = 0; i < 8; i++)
		{
			if( i == 0)
			{
				squares[i][0].placePiece(new Rook("black"));
				squares[i][1].placePiece(new Knight("black"));
				squares[i][2].placePiece(new Bishop("black"));
				squares[i][3].placePiece(new Queen("black"));
				squares[i][4].placePiece(new King("black"));
				squares[i][5].placePiece(new Bishop("black"));
				squares[i][6].placePiece(new Knight("black"));
				squares[i][7].placePiece(new Rook("black"));
			}
			else if( i == 7)
			{
				squares[i][0].placePiece(new Rook("white"));
				squares[i][1].placePiece(new Knight("white"));
				squares[i][2].placePiece(new Bishop("white"));
				squares[i][3].placePiece(new Queen("white"));
				squares[i][4].placePiece(new King("white"));
				squares[i][5].placePiece(new Bishop("white"));
				squares[i][6].placePiece(new Knight("white"));
				squares[i][7].placePiece(new Rook("white"));
			}
		}
	}

	public Square[][] getSquares(){
		return this.squares;
	}

	public String toString(){
		String s = "";

		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				s = s + " " + squares[i][j];
			}
			s = s + " " + (8 - i) + "\n";
		}
		s = s + "  a  b  c  d  e  f  g  h\n";
		return s;
	}

}
