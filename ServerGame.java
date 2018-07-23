package TicTacToe;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server that the tic tac toe games are to be played on.
 * 
 * Multiple games can be played on threads. Waits for 2 players to connect before a game starts.
 * @author Thomas Vy
 * @since March 8, 2018
 * @version 3.0
 *
 */
public class ServerGame implements Constants{
	ServerSocket serverSocket;
	/**
	 * The multi-threading pool
	 */
	private ExecutorService pool;
	/**
	 * The constructor of the server
	 * @param portNumber - The port number for the server to listen on.
	 */
	public ServerGame (int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
		}
			catch (IOException e) {
			System.err.println("Create new socket error");
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Server is running...");
	}
	/**
	 * Runs the server and awaits for 2 connections before starting a game.
	 * 
	 */
	public void runServer() {
		try {
			while (true) {
				Player xPlayer = new Player (serverSocket.accept(),'X');
				Player oPlayer = new Player (serverSocket.accept(),'O');
				Referee theRef = new Referee();
				
				theRef.setxPlayer(xPlayer);
				theRef.setoPlayer(oPlayer);
				
				Game theGame = new Game();
				theGame.appointReferee(theRef);
				
				System.out.println("Started a game!");
				
				pool.execute(theGame);
			}

		} 
		catch(Exception e) {
			pool.shutdown();
		}
	}
	public static void main(String[] args) {
		ServerGame sGame = new ServerGame(9098);
		sGame.runServer();
	}
}
