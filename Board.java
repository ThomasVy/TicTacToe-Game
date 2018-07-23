package TicTacToe;
//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 
/**
 * The board of the game.
 * @author Thomas Vy
 * @since January 31 2018
 * @verion 4.0
 */

public class Board implements Constants{
	/**
	 * The board
	 */
	private char theBoard[][];
	/**
	 * The number of used spaces on the board.
	 */
	private int markCount;
	/**
	 * creates blank 3x3 board
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}
	/**
	 * Returns the letter at row and column on the board.
	 * @param row The row of the wanted character.
	 * @param col The column of the wanted character
	 * @return Gets the character at the location on the board.
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}
	/**
	 * Checks if the board is filled.
	 * @return Returns true if the board is full, else it will return false.
	 */
	public boolean isFull() {
		return markCount == 9;
	}
	/**
	 * Checks if PlayerX wins
	 * @return Returns true if PlayerX wins, else returns false.
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}
	/**
	 * Checks if PlayerO wins
	 * @return Returns true if player
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}
	/**
	 * Combines all the strings of the board so it can be displayed on each player gui's.
	 * @return -The string of the board.
	 */
	public String display() {
		String n ="START";
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++)
				n+=getMark(row, col);
		}
		return n;
	}
	/**
	 * Places a mark(either X or O) onto row and column on the board.
	 * Increases the number of pieces on the board.
	 * @param row The row that the mark is going to be placed.
	 * @param col The column of the board that the mark is going to be place.
	 * @param mark The letter that is going to place.
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}
	/**
	 * Resets the board.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}
	/**
	 * Checks the winning condition for the mark that it was given
	 * @param mark the letter that is being checked for the for the winning condition
	 * @return Return 1 if the player won or 0 otherwise
	 */
	int checkWinner(char mark) {
		int row, col;
		/**
		 * result = 1 if won and zero otherwise.
		 */
		int result = 0;
		/**
		 * checks the winning of the rows
		 */
		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		/**
		 * Checks the columns for winning condition
		 */
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}
		/**
		 * Checks the diagonals for the winning condition
		 */
		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}
	/**
	 * Checks if the space is taken or not
	 * @param row the row that was chosen
	 * @param col the column that was chosen
	 * @return true if the space is avaliable or false if the space isn't.
	 */
	boolean isAvaliable (int row, int col) {
		if(theBoard[row][col]==' ')
			return true;
		return false;
	}
}
