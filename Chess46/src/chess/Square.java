package chess;

/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class Square implements Comparable<Square>{

	private int column;
	private int row;
	private String color;
	public Piece piece;

	/**
	 * Creates a Square object for the board.
	 *
	 * @param row
	 * @param column
	 * @param color
	 */
	public Square(int row, int column, String color){
		this.column = column;
		this.row = row;
		this.color = color;
		this.piece = null;
	}

	public void placePiece(Piece piece){
		this.piece = piece;
	}

	public void removePiece(){
		this.piece = null;
	}

	public int getColumn()
	{
		return this.column;
	}
	public int getRow()
	{
		return this.row;
	}
	public String getColor()
	{
		return this.color;
	}

	public String toString(){
		if(piece != null)
		{
			return piece.toString();
		}
		else if (color.equals("black")){
			return "##";
		}
		else {
			return "  ";
		}
	}

	/**
	 * Need to override the compareTo method from the Comparable class
	 * in order to compare two different (or similar) squares.
	 */
	@Override
	public int compareTo(Square o) {
		//Taking a precaution by casting.
		if(o.getColumn() != this.getColumn())
		{
			return -1;
		}
		if(o.getRow() != this.getRow())
		{
			return -1;
		}
		if(!o.getColor().equals(this.getColor()))
		{
			return -1;
		}
		return 0;
	}

}
