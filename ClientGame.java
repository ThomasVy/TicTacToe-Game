package TicTacToe;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * The Client side of the tic tac toe game.
 * 
 * Only needs board and constants class to be in package.
 * @author Thomas Vy
 * @since March 15, 2018
 * @version 4.0
 * 
 */
public class ClientGame {
	/**
	 * The socket of for communication between server and client
	 */
	Socket gameSocket;
	/**
	 * The reader for the gui inputs
	 */
	BufferedReader in;
	/**
	 * The writer for the socket 
	 */
	PrintWriter socketOut;
	/**
	 * The reader for the socket
	 */
	BufferedReader socketIn;
	/**
	 * The graphical interface 
	 */
	Graphics gui;
	/**
	 * The stream that reads the gui inputs
	 */
	PipedInputStream pi;
	/**
	 * The stream that places in the gui inputs 
	 */
	PipedOutputStream po;
	/**
	 * The writer for the gui outputs
	 */
	PrintWriter out;
	/**
	 * The constructor of the Client. Connects the client to the server.
	 * @param serverName - the IP address of the server
	 * @param portNumber - the port number of the server
	 */
	public ClientGame (String serverName, int portNumber)
	{
		try {
			gameSocket = new Socket(serverName, portNumber);
			pi = new PipedInputStream();
			po = new PipedOutputStream(pi);
			in = new BufferedReader(new InputStreamReader(pi));
			out = new PrintWriter (po, true);
			socketOut = new PrintWriter (gameSocket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * writes to the po stream for the client to read and send to the server.
	 * @param r - The string for the client to send
	 */
	public void send (String r)
	{
		out.println(r);
	}
	/**
	 * Communicates with the server, mostly the player. Uses '\0' as a delimiter to know when it is time to write instead of read.
	 */
	public void communicateServer()
	{
		try {
			//Read and Print Anything received by the server(A Null character marks when to stop reading)
			String read ="";
			while(true) {
				while(true) {
					gui.changeButtons(false); // changes board buttons to disable
					read =socketIn.readLine();
					if(read.contains("START")) //Reads the whole board
					{
						read = read.replace("START", "");
						gui.updateButtons(read);
					}
					else if(read.equals("X")||read.equals("O")) //Reads the player mark
					{
							gui.updateMark(read);
					}
					else 
					{
						if(read.contains("\0"))
						{
							read = read.replace("\0", "");
							gui.updateDisplay(read);
							break;
						}
						//When game is finished, break out both nested loops
						if(read.equals("QUIT")) {
							gui.updateDisplay("Thanks for playing! Goodbye!");
							gui.changeButtons(false);
							return ; //method will execute the finally block before returning
						}
						gui.updateDisplay(read);
					}
				
				}
				read = in.readLine();
				socketOut.println(read);
				socketOut.flush();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 
	}
	/**
	 * sets the gui for the client
	 * @param gui - the gui for the client
	 */
	public void add(Graphics gui)
	{
		this.gui = gui;
	}
	public static void main(String[] args)
	{
		ClientGame game = new ClientGame("localhost", 9098);
		Graphics gui = new Graphics("Game window");
		gui.pack();
		gui.add(game);
		game.add(gui);
		game.communicateServer();
	}
}
