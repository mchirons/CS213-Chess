package chess;

/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Move implements Comparable<Move>{

	private Square from;
	private Square to;
	private Piece piece;

	/**
	 * Creates a Move object to be used with various classes ranging from Player to ChessGame.
	 * @param from
	 * @param to
	 * @param piece
	 */
	public Move(Square from, Square to, Piece piece){
		this.from = from;
		this.to = to;
		this.piece = piece;
	}

	public Piece getPiece()
	{
		return this.piece;
	}

	public Square getFromSquare()
	{
		return this.from;
	}

	public Square getToSquare()
	{
		return this.to;
	}

	@Override
	public int compareTo(Move o) {

		if(!(this.getPiece().type == o.getPiece().type))
		{
			return -1;
		}
		if(this.getFromSquare().compareTo(o.getFromSquare()) < 0)
		{
			return -1;
		}
		if(this.getToSquare().compareTo(o.getToSquare()) < 0)
		{
			return -1;
		}

		return 0;
	}
}
