package TicTacToe;
import java.io.*;
import java.net.Socket;
/**
 * The players that play the game.
 * @author Thomas Vy
 * @since January 31 2018
 * @verion 4.0
 */
public class Player{
	private String name;
	/**
	 * The board that the players play on.
	 */
	private Board board;
	/**
	 * The opponent of the player.
	 */
	private Player opponent;
	/**
	 * The letter that the player is
	 */
	private char mark;
	/**
	 * The socket of the player
	 */
	private Socket aSocket;
	/**
	 * The input reader of the player
	 */
	private BufferedReader socketIn;
	/**
	 * The output writer of the player
	 */
	private PrintWriter socketOut;
	/**
	 * Constructs the Player. 
	 * Gives it a mark and Socket.
	 * @param s The socket of the player.
	 * @param markA The letter of the player.
	 */
	public Player(Socket s, char markA)
	{
		aSocket =s;
		mark = markA;
		try {
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream());
			sendString("Connected to a Game. Waiting for Opponent...");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Sends strings to the client
	 * @param toSend - the string to be sent
	 */
	private void sendString(String toSend)
	{
		socketOut.println(toSend);
		socketOut.flush();
	}
	/**
	 * Getting the name of the player
	 */
	public void getPlayerName () {
		try {
			sendString(Character.toString(mark));
			sendString("Please enter your name.\0");
			name =socketIn.readLine();
			while(name ==null)
			{
				sendString("Please try again.\0");
				name =socketIn.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Starts the game. 
	 * Lets the game go back and forth between the players.
	 */
	public void play () throws IOException
	{
		String a =board.display();
		sendString(a);
		opponent.sendString(a);
		while(true)
		{
			makeMove();
			a =board.display();
			sendString(a);
			opponent.sendString(a);
			if(checkEnd())
				break;
			opponent.makeMove();
			a =board.display();
			sendString(a);
			opponent.sendString(a);
			if(checkEnd())
				break;
						
		}
			
	}
	/**
	 * Checks if the game has and ended and sends the appropriate ending.
	 * @return true if game ended. false if game didn't end.
	 */
	private boolean checkEnd()
	{
		boolean result = false;
		if(board.oWins()) {
			result =true;
			oWin();
		}
		else if(board.xWins()) {
			result =true;
			xWin();
		}
		else if(board.isFull()) {
			result =true;
			Tie();
		}
		return result;
	}
	/**
	 * Lets the player make moves by inputing valid row and column spaces.
	 * Doesn't allow any invalid moves and players to overwrite 
	 */
	private void makeMove() throws IOException
	{
		sendString(name +" it is your turn("+mark+")\0");
		opponent.sendString("It is "+name+"'s turn. Please wait.");
		int row,col;
		while(true)
		{
			String s = socketIn.readLine();
			String [] temp = s.split(" ");
			row = Integer.parseInt(temp[0]);
			col = Integer.parseInt(temp[1]);
			if(board.isAvaliable(row, col))
				break;
			sendString("Space not avaliable. Please try again.\0");
		}
		board.addMark(row, col, mark);
	}
	/**
	 * Sets the opponent of the player
	 * @param player The opponent of this.player
	 */
	public void setOpponent(Player player)
	{
		opponent = player;
		player.opponent = this;
	}
	/**
	 * Sets the board to play on
	 * @param theBoard the board to play on.
	 */
	public void setBoard(Board theBoard)
	{
		board = theBoard;
	}
	/**
	 * prints the game over screen.
	 */
	private void GameOver()
	{
		sendString("\n"+"GAME OVER");
		opponent.sendString("\n"+"GAME OVER");
	}
	/**
	 * prints the tie screen
	 */
	private void Tie()
	{
		GameOver();
		sendString("Game ended in a tie!");
		opponent.sendString("Game ended in a tie!");
		end();
	}
	/**
	 * prints the x is winner screen.
	 */
	private void xWin()
	{
		GameOver();
		if(mark == 'X') {
			sendString(name +"(X) has won the game!");
			opponent.sendString(name +"(X) has won the game!");
		}
		else {
			sendString(opponent.name + "(X) has won the game!");
			opponent.sendString(opponent.name +"(X) has won the game!");
		}
		end();
		
	}
	/**
	 * prints the o winner screen.
	 */
	private void oWin()
	{
		GameOver();
		if(mark == 'O') {
			sendString(name +"(O) has won the game!");
			opponent.sendString(name +"(O) has won the game!");
		}
		else {
			sendString(opponent.name + "(O) has won the game!");
			opponent.sendString(opponent.name + "(O) has won the game!");
		}
		end();
	}
	/**
	 * Tells the client that the game is now over.
	 */
	private void end()
	{
		sendString("QUIT");
		opponent.sendString("QUIT");
	}
}

