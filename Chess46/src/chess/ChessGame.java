package chess;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
/**
 *
 * @author Mark Hirons
 * @author Andre Pereira
 *
 */

public class ChessGame {

	private Player white;
	private Player black;
	private Board board;
	private boolean isGameOver;
	private Player currentPlayer;

	/**
	 * Creates a new white player, a new black player, and initializes the
	 * board with all the pieces.
	 *
	 */
	public ChessGame(){
		this.board = new Board();
		this.white = new Player("white",this.board);
		this.black = new Player("black",this.board);
	}

	/**
	 * Takes input from user.
	 * Current player makes a move.
	 * Then calls method to check if move is valid.
	 * Checks for captures or check/checkmate.
	 * Updates board.
	 * If game over prints winner and ends.
	 *
	 */
	public void play(){

		boolean isPlayerWhite = true;
		Move move;

		while(!isGameOver){
			printBoard();
			if (isPlayerWhite){
				currentPlayer = white;
				if (!hasAnyMoves(white)){
					isCheckOrStaleMate();
					continue;
				}
				System.out.print("White's move: ");
				move = white.playTurn();
				isResign();
				if(wantsDraw()){
					continue;
				}
				else{
					isPlayerWhite = true;
				}
				if(move != null && move.getPiece().color.equals("black"))
				{
					System.out.println("Illegal move, try again");
					move = null;
					continue;
				}
				System.out.println();
			}
			else{
				currentPlayer = black;
				if (!hasAnyMoves(black)){
					isCheckOrStaleMate();
					continue;
				}
				System.out.print("Black's move:");
				move = black.playTurn();
				isResign();
				if (wantsDraw()){
					continue;
				}
				else{
					isPlayerWhite = false;
				}
				if(move != null && move.getPiece().color.equals("white"))
				{
					System.out.println("Illegal move, try again");
					move = null;
					continue;
				}
				System.out.println();
			}
			if (!isValidMove(currentPlayer, move)){
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
			}
			updateBoard(move);
			isCheck();
			isPlayerWhite = !isPlayerWhite;
		}

	}

	/**
	 * Takes the current player and that player's move in its
	 * parameters.
	 *
	 * Returns a boolean of whether or not the move is valid.
	 *
	 * @param currentPlayer
	 * @param move
	 * @return if move is valid
	 */
	private boolean isValidMove(Player currentPlayer, Move move){
		if (move == null){
			return false;
		}

		Piece piece = move.getPiece();

		Piece fromTemp;
		Piece toTemp;

		ArrayList<Move> validMoves = piece.validMoves(move, board);

		//moves player can make if in check
		ListIterator<Move> iterator = validMoves.listIterator();

			while (iterator.hasNext()){
				Move m = iterator.next();
				Square from = m.getFromSquare();
				//System.out.println("from:   " + from);
				Square to = m.getToSquare();
				//System.out.println("to: " + to);
				fromTemp = from.piece;
				toTemp = to.piece;
				//simulate move
				to.piece = piece;
				from.piece = null;

				//find king after move
				Square[][] squares = board.getSquares();
				Square king = null;

				for (int i = 0; i < 8; i++){
					for (int j = 0; j < 8; j++){
						if (squares[i][j].piece != null){
							if (squares[i][j].piece.type == Piece.Type.KING){
								if (squares[i][j].piece.color.equals(currentPlayer.getColor())){
									king = squares[i][j];
									//System.out.println("found king , isValid: " + king);
								}
							}
						}
					}
				}
				//System.out.println("king: " + king);


				if (isThreat(king)){
					iterator.remove();
					System.out.println("remove after threat check");
				}
				//undo move
				from.piece = fromTemp;
				to.piece = toTemp;
			}


			//check if move is in list of valid moves



			if(move.getFromSquare().piece.type.equals(Piece.Type.KING))
			{
				King king = (King)move.getFromSquare().piece;

				//System.out.println("In castling if");

				if(king.isCastling)
				{
					Piece prev = null;
					Piece curr = null;
					Piece prevK = null;
					Piece currK = null;
					//System.out.println("In castling if");
					Rook rook = null;
					int r = move.getFromSquare().getRow();
					Square tempFrom, tempTo, tempFromK, tempToK;
					tempFrom = null;
					tempTo = null;
					tempFromK = null;
					tempToK = null;

					//If castling with left rook.
					if(king.isCastlingLR)
					{
						//If white piece.
						if(r == 7)
						{
							for(int i = 0; i < white.getPieces().size(); i++)
							{
								if(white.getPieces().get(i).type.equals(Piece.Type.ROOK))
								{
									rook = (Rook) white.getPieces().get(i);
									break;
								}
							}
							tempFrom = board.getSquares()[r][0];
							tempFromK = board.getSquares()[r][4];
							tempTo = board.getSquares()[r][3];
							tempToK = board.getSquares()[r][2];
							prev = tempFrom.piece;
							curr = tempTo.piece;
							prevK = tempFromK.piece;
							currK = tempToK.piece;
							tempTo.piece = tempFrom.piece;
							tempToK.piece = tempFromK.piece;
							tempFrom.piece = null;
							tempFromK.piece = null;
						}
						else
						{
							for(int i = 0; i < black.getPieces().size(); i++)
							{
								if(black.getPieces().get(i).type.equals(Piece.Type.ROOK))
								{
									rook = (Rook) black.getPieces().get(i);
									break;
								}
							}
							tempFrom = board.getSquares()[r][0];
							tempFromK = board.getSquares()[r][4];
							tempTo = board.getSquares()[r][3];
							tempToK = board.getSquares()[r][2];
							prev = tempFrom.piece;
							curr = tempTo.piece;
							prevK = tempFromK.piece;
							currK = tempToK.piece;
							tempTo.piece = tempFrom.piece;
							tempToK.piece = tempFromK.piece;
							tempFrom.piece = null;
							tempFromK.piece = null;
						}
					}
					//If castling with right rook.
					else if(king.isCastlingRR)
					{
						//If white piece.
						if(r == 7)
						{
							for(int i = white.getPieces().size() - 1; i >= 0 ; i--)
							{
								if(white.getPieces().get(i).type.equals(Piece.Type.ROOK))
								{
									rook = (Rook) white.getPieces().get(i);
									break;
								}
							}
							tempFrom = board.getSquares()[r][7];
							tempFromK = board.getSquares()[r][4];
							tempTo = board.getSquares()[r][5];
							tempToK = board.getSquares()[r][6];
							prev = tempFrom.piece;
							curr = tempTo.piece;
							prevK = tempFromK.piece;
							currK = tempToK.piece;
							tempTo.piece = tempFrom.piece;
							tempToK.piece = tempFromK.piece;
							tempFrom.piece = null;
							tempFromK.piece = null;
						}
						else
						{
							for(int i = black.getPieces().size() - 1; i >= 0; i--)
							{
								if(black.getPieces().get(i).type.equals(Piece.Type.ROOK))
								{
									rook = (Rook) black.getPieces().get(i);
									break;
								}
							}
							tempFrom = board.getSquares()[r][7];
							tempFromK = board.getSquares()[r][4];
							tempTo = board.getSquares()[r][5];
							tempToK = board.getSquares()[r][6];
							prev = tempFrom.piece;
							curr = tempTo.piece;
							prevK = tempFromK.piece;
							currK = tempToK.piece;
							tempTo.piece = tempFrom.piece;
							tempToK.piece = tempFromK.piece;
							tempFrom.piece = null;
							tempFromK.piece = null;
						}
					}
					Square[][] squares = board.getSquares();
					Square king1 = null;
					for (int i = 0; i < 8; i++){
						for (int j = 0; j < 8; j++){
							if (squares[i][j].piece != null){
								if (squares[i][j].piece.type == Piece.Type.KING){
									if (squares[i][j].piece.color.equals(currentPlayer.getColor())){
										king1 = squares[i][j];
									}
								}
							}
						}
					}
						if (isThreat(king1)){
							validMoves.remove(validMoves.size()-1);
						}
						tempFrom.piece = prev;
						tempTo.piece = curr;
						tempFromK.piece = prevK;
						tempToK.piece = currK;
					}
				}

			return piece.isMoveValid(validMoves,move,board);
	}

	/**
	 * Gets called during each phase of the play method
	 * to check if one or both players want a draw.
	 *
	 * @return a boolean depicting whether or not player wants draw
	 */
	private boolean wantsDraw(){
		Scanner sc = new Scanner(System.in);
		String answer;
		boolean keepAsking = true;
		if (black.wantsDraw){
			while (keepAsking){
				System.out.println();
				System.out.print("Draw?");
				answer = sc.nextLine();
				System.out.println();
				if (answer.toLowerCase().equals("draw")){
					System.out.print("Draw");
					isGameOver = true;
					return true;
				}else if (answer.toLowerCase().equals("no") || answer.toLowerCase().equals("n")){
					black.wantsDraw = false;
					keepAsking = false;
					return false;
					//continue with game
				}else{
					System.out.println("Incorrect input");
				}
			}
		}
		else if (white.wantsDraw){
			while (keepAsking){
				System.out.println();
				System.out.print("Draw?");
				answer = sc.nextLine();
				System.out.println();
				if (answer.toLowerCase().equals("draw")){
					System.out.print("Draw");
					isGameOver = true;
					return true;
				}else if (answer.toLowerCase().equals("no") || answer.toLowerCase().equals("n")){
					white.wantsDraw = false;
					keepAsking = false;
					return false;
					//continue with game
				}else{
					System.out.println("Incorrect input");
				}
			}
		}
		return false;
	}

	/**
	 * Gets called during each phase of the play method
	 * to check if a player wishes to resign.
	 */
	private void isResign(){
		if(white.wantsResign){
			System.out.println();
			System.out.println("Black wins");
			System.exit(0);
		}
		else if (black.wantsResign){
			System.out.println();
			System.out.println("White wins");
			System.exit(0);
		}
		else{

		}
	}

	/**
	 * Updates the current status of the board with a given move.
	 *
	 * @param move
	 */
	private void updateBoard(Move move){
		Square from = move.getFromSquare();
		Square to = move.getToSquare();
		Piece piece = move.getPiece();

		/*
		 * If the piece is a king, we check if the player
		 * is castling.
		 */
		if(from.piece.type.equals(Piece.Type.KING))
		{
			King king = (King)move.getFromSquare().piece;

			if(king.isCastling)
			{
				Rook rook = null;
				int r = from.getRow();
				Square tempFrom, tempTo;
				tempFrom = null;
				tempTo = null;
				king.isCastling = false;

				//If castling with left rook.
				if(king.isCastlingLR)
				{
					//If white piece.
					if(r == 7)
					{
						for(int i = 0; i < white.getPieces().size(); i++)
						{
							if(white.getPieces().get(i).type.equals(Piece.Type.ROOK))
							{
								rook = (Rook) white.getPieces().get(i);
								break;
							}
						}
						tempFrom = board.getSquares()[r][0];
						tempTo = board.getSquares()[r][3];
					}
					else
					{
						for(int i = 0; i < black.getPieces().size(); i++)
						{
							if(black.getPieces().get(i).type.equals(Piece.Type.ROOK))
							{
								rook = (Rook) black.getPieces().get(i);
								break;
							}
						}
						tempFrom = board.getSquares()[r][0];
						tempTo = board.getSquares()[r][3];
					}
					updateBoard(new Move(tempFrom,tempTo,rook));
				}
				//If castling with right rook.
				else if(king.isCastlingRR)
				{
					//If white piece.
					if(r == 7)
					{
						for(int i = white.getPieces().size() - 1; i >= 0 ; i--)
						{
							if(white.getPieces().get(i).type.equals(Piece.Type.ROOK))
							{
								rook = (Rook) white.getPieces().get(i);
								break;
							}
						}
						tempFrom = board.getSquares()[r][7];
						tempTo = board.getSquares()[r][5];
					}
					else
					{
						for(int i = black.getPieces().size() - 1; i >= 0; i--)
						{
							if(black.getPieces().get(i).type.equals(Piece.Type.ROOK))
							{
								rook = (Rook) black.getPieces().get(i);
								break;
							}
						}
						tempFrom = board.getSquares()[r][7];
						tempTo = board.getSquares()[r][5];
					}
					updateBoard(new Move(tempFrom,tempTo,rook));
				}
			}
		}
		Scanner sc = new Scanner(System.in);
		if ((piece.type == Piece.Type.PAWN)){
			Pawn pawn = (Pawn)piece;
			if (pawn.isPromoting){
				Piece promotion;
				if (currentPlayer.promotionChoice.equals("r")){
					promotion = new Rook(currentPlayer.getColor());
				}
				else if (currentPlayer.promotionChoice.equals("n")){
					promotion = new Knight(currentPlayer.getColor());
				}
				else if (currentPlayer.promotionChoice.equals("q")){
					promotion = new Queen(currentPlayer.getColor());
				}
				else if (currentPlayer.promotionChoice.equals("b")){
					promotion = new Bishop(currentPlayer.getColor());
				}
				else{
					promotion = new Queen(currentPlayer.getColor());
				}
				piece = promotion;
			}
		}

		if (piece.type == Piece.Type.PAWN){

		}

		to.piece = piece;
		from.piece = null;



		for (int i = 0; i < white.getPieces().size(); i++){
			if (white.getPieces().get(i) == null){
				white.getPieces().remove(i);
			}
		}

		for (int i = 0; i < black.getPieces().size(); i++){
			if (black.getPieces().get(i) == null){
				black.getPieces().remove(i);
			}
		}

	}

	/**
	 * Gets called during each move to see if the player's
	 * king is in check.
	 *
	 */
	private void isCheck(){

		//find square with Kings
		Square[][] squares = board.getSquares();
		Square wKing = null;
		Square bKing = null;

		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (squares[i][j].piece != null){
					if (squares[i][j].piece.type == Piece.Type.KING){
						if (squares[i][j].piece.color.equals("black")){
							bKing = squares[i][j];
						}
						else{
							wKing = squares[i][j];
						}
					}
				}
			}
		}

		if (isThreat(wKing)){
			white.isCheck = true;
			System.out.println("Check");
			System.out.println();

		}
		else if (isThreat(bKing)){
			black.isCheck = true;
			System.out.println("Check");
			System.out.println();

		}
		else{
			white.isCheck = false;
			black.isCheck = false;
		}
	}

	/**
	 * A helper method to see if the king will be in a
	 * threat position, i.e. may be in check.
	 *
	 * @param kingSquare
	 * @return boolean depicting whether or not king is in danger.
	 */
	private boolean isThreat(Square kingSquare){


		Square[][] squares = board.getSquares();
		int r = kingSquare.getRow();
		int c = kingSquare.getColumn();
		String color = kingSquare.piece.color;

		//check rows and columns

		for (int i = r + 1; i < 8; i++){
			if (squares[i][c].piece != null && !squares[i][c].piece.color.equals(color)) {
				if (squares[i][c].piece.type == Piece.Type.ROOK || squares[i][c].piece.type == Piece.Type.QUEEN){
					//System.out.println("rows, columns 1");
					return true;
				}else{break;}
			}
			else if(squares[i][c].piece != null){
				break;
			}
			else{}
		}
		for(int i = r - 1; i >= 0; i--){

			if (squares[i][c].piece != null && !squares[i][c].piece.color.equals(color)) {
				if (squares[i][c].piece.type == Piece.Type.ROOK || squares[i][c].piece.type == Piece.Type.QUEEN){
					//System.out.println("rows, columns 2");
					return true;
				}
				else{break;}
			}
			else if(squares[i][c].piece != null){
				break;
			}
			else{}
		}
		for (int i = c + 1; i < 8; i++){
			if (squares[r][i].piece != null && !squares[r][i].piece.color.equals(color)) {
				if (squares[r][i].piece.type == Piece.Type.ROOK || squares[r][i].piece.type == Piece.Type.QUEEN){
					//System.out.println("rows, columns 3");
					return true;
				}else{break;}
			}
			else if(squares[r][i].piece != null){
				break;
			}
			else{}
		}
		for (int i = c - 1; i >= 0; i--){
			if (squares[r][i].piece != null && !squares[r][i].piece.color.equals(color)) {
				if (squares[r][i].piece.type == Piece.Type.ROOK || squares[r][i].piece.type == Piece.Type.QUEEN){
					//System.out.println("rows, columns 4");
					return true;
				}else{break;}
			}
			else if(squares[r][i].piece != null){
				break;
			}
			else{}
		}

		//check diagonals
		for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--){
			if (squares[i][j].piece != null && !squares[i][j].piece.color.equals(color)) {
				if (squares[i][j].piece.type == Piece.Type.BISHOP || squares[i][j].piece.type == Piece.Type.QUEEN){
					//System.out.println("diagonals 1 A");
					return true;
				}
				else if (squares[i][j].piece.type == Piece.Type.PAWN){
					if (i == r - 1 && j == c - 1){
						//System.out.println("diagonals 1 B");
						return true;
					}
					else{break;}
				}else{break;}
			}
			else if(squares[i][j].piece != null){
				break;
			}
			else{}
		}
		//moving diagonally to top left
		for (int i = r - 1, j = c + 1; i >= 0 && j < 8; i--, j++){
			if (squares[i][j].piece != null && !squares[i][j].piece.color.equals(color)) {
				if (squares[i][j].piece.type == Piece.Type.BISHOP || squares[i][j].piece.type == Piece.Type.QUEEN){
					//System.out.println("diagonals 2 A");
					return true;
				}
				else if (squares[i][j].piece.type == Piece.Type.PAWN){
					if (i == r - 1 && j == c + 1){
						//System.out.println("diagonals 2 B");
						return true;
					}
					else{break;}
				}else{break;}
			}
			else if(squares[i][j].piece != null){
				break;
			}
			else{}
		}
		//moving diagonally to bottom right
		for (int i = r + 1, j = c - 1; i < 8 && j >= 0 ; i++, j-- ){
			if (squares[i][j].piece != null && !squares[i][j].piece.color.equals(color)) {
				if (squares[i][j].piece.type == Piece.Type.BISHOP || squares[i][j].piece.type == Piece.Type.QUEEN){
					//System.out.println("diagonals 3 A");
					return true;
				}
				else if (squares[i][j].piece.type == Piece.Type.PAWN){
					if (i == r + 1 && j == c - 1){
						//System.out.println("diagonals 3 B");
						return true;
					}
					else{break;}
				}else{break;}
			}
			else if(squares[i][j].piece != null){
				break;
			}
			else{}
		}
		//moving diagonally to bottom left
		for (int i = r + 1, j = c + 1; i < 8 && j < 8; i++, j++){
			if (squares[i][j].piece != null && !squares[i][j].piece.color.equals(color)) {
				if (squares[i][j].piece.type == Piece.Type.BISHOP || squares[i][j].piece.type == Piece.Type.QUEEN){
					//System.out.println("diagonals 4 A");
					return true;
				}
				else if (squares[i][j].piece.type == Piece.Type.PAWN){
					if (i == r + 1 && j == c + 1){
						//System.out.println("diagonals 4 B");
						return true;
					}else{break;}
				}else{break;}
			}
			else if(squares[i][j].piece != null){
				break;
			}
			else{}
		}

		//check knights
		int [][] coord = {{r + 1, c + 2},
					{r + 1, c - 2},
					{r - 1, c + 2},
					{r - 1, c - 2},
					{r + 2, c + 1},
					{r + 2, c - 1},
					{r - 2, c + 1},
					{r - 2, c - 1}};

		ArrayList<Square> temp = new ArrayList<Square>();

		for(int i = 0; i < 8; i++)
		{
			if(!(coord[i][0] < 0 || coord[i][0] > 7 || coord[i][1] < 0 || coord[i][1] > 7))
			{
				temp.add(squares[coord[i][0]][coord[i][1]]);
			}
		}

		for(int i = 0; i < temp.size(); i++)
		{
			if(temp.get(i).piece != null)
			{
				if((temp.get(i).piece.type == Piece.Type.KNIGHT) && !(color.equals(temp.get(i).piece.color))) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Ends the game based on whether or not there is a checkmate
	 * or a stalemate.
	 */
	private void isCheckOrStaleMate(){

		if (white.isCheck){
			//can't exit check
			System.out.println("Checkmate");
			System.out.println();
			System.out.println("Black wins");

			isGameOver = true;
		}
		else if (black.isCheck){
			//can't exit check
			System.out.println("Checkmate");
			System.out.println();
			System.out.println("White wins");
			isGameOver = true;
		}
		else{
			System.out.println("Draw");
			isGameOver = true;
		}
		isGameOver = true;
	}

	/**
	 * Prints current state of the board.
	 */

	private void printBoard(){
		System.out.println(board);
	}

	/**
	 * Determines if player has any moves that don't end in check.
	 * @param current
	 * @return a boolean value depicting whether or not the player has any moves left
	 */
	private boolean hasAnyMoves(Player current){
		ArrayList<Move> totalMoves = new ArrayList<Move>();
		ArrayList<Piece> pieces = current.getPieces();
		Square square = null;
		Piece piece = null;

		//for each piece in pieces list
		//find square piece is in
		Square[][] squares = board.getSquares();
		for (int k = 0; k < pieces.size(); k++){
			for (int i = 0; i < 8; i++){
				for (int j = 0; j < 8; j++){
					if (squares[i][j].piece != null){
						if (squares[i][j].piece == pieces.get(k)){
							piece = pieces.get(k);
							square = squares[i][j];
							break;
						}
					}
				}
			}
			//add valid moves to totalMoves
			//to square doesn't matter
			Move move = new Move(square, squares[0][0], piece);
			ArrayList<Move> validMoves = piece.validMoves(move, board);


			ListIterator<Move> iterator = validMoves.listIterator();
			Piece fromTemp;
			Piece toTemp;

				while (iterator.hasNext()){
					Move m = iterator.next();
					Square from = m.getFromSquare();
					Square to = m.getToSquare();
					fromTemp = from.piece;
					toTemp = to.piece;
					//simulate move
					to.piece = piece;
					from.piece = null;

					//find king after move
					Square king = null;

					for (int i = 0; i < 8; i++){
						for (int j = 0; j < 8; j++){
							if (squares[i][j].piece != null){
								if (squares[i][j].piece.type == Piece.Type.KING){
									if (squares[i][j].piece.color.equals(current.getColor())){
										king = squares[i][j];
										//System.out.println("found king, hasAnyMoves: " + king);
									}
								}
							}
						}

					}
					//System.out.println("king, hasAnyMoves, before isThreat: " + king);
					if (!isThreat(king)){
						totalMoves.add(m);
					}
					//if(king != null)
					//{
						if (!isThreat(king)){
							totalMoves.add(m);
						}
					//}
					//undo move
					from.piece = fromTemp;
					to.piece = toTemp;
				}


		}

		if (totalMoves.size() == 0){
			return false;
		}
		else{
			return true;
		}
	}
}
