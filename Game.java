package TicTacToe;
import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 

/**
 * Tictactoe game
 *  @author Thomas Vy
 *  @since January 31 2018
 *  @version 4.0
 */
public class Game implements Runnable{
	/**
	 * The game board
	 */
	private Board theBoard;
	/**
	 * The Referee
	 */
	private Referee theRef;
	/**
	 * Constructor of the game
	 */
    public Game( ) {
        theBoard  = new Board();
	}
    /**
     * Sets the referees
     * @param r The referee for for the game
     */
    public void appointReferee(Referee r){
        theRef = r;
        theRef.setBoard(theBoard);
        theRef.getxPlayer().setBoard(theBoard);
        theRef.getoPlayer().setBoard(theBoard);
  
    	
    }
    /**
     * runs the game on a thread and starts the game
     */ 
	@Override
	public void run() {
		try {
		theRef.runTheGame();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
