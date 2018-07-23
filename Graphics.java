package TicTacToe;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;
/**
 * The graphical user interface for the game.
 * @author Thomas Vy
 * @since March 22 2018
 * @version 1.0
 *
 */
public class Graphics extends JFrame  {
	private static final long serialVersionUID = 1L;
	/**
	 * The panel for the board buttons and player letter
	 */
	JPanel board; 
	/**
	 * The tictactoe buttons
	 */
	JButton [][] buttons = new JButton [3][3];
	/**
	 * Button for setting the name
	 */
	JButton set;
	/**
	 * Label for username, messageWindow, and player letter
	 */
	JLabel user, message, player;
	/**
	 * The field for the username, and player letter
	 */
	JTextField userF, playerF;
	/**
	 * The message window scroll pane
	 */
	JScrollPane display;
	/**
	 * The text for the message window scoll pane.
	 */
	JTextArea text;
	/**
	 * The client side of the game
	 */
	ClientGame game;
	/**
	 * Constructor of the gui
	 * @param s - The string for the window 
	 */
	public Graphics (String s) {
		super(s);
		setLayout(new BorderLayout());
		board = new JPanel (new BorderLayout());
		setBoard();
		JPanel userName = new JPanel();
		userName.setBorder(new EmptyBorder(0,0,20,0));
		user = new JLabel ("User Name: ");
		userF = new JTextField(10);
		set = new JButton ("confirm");
		userF.setEnabled(false);
		set.setEnabled(false);
		userName.add(user);
		userName.add(userF);
		userName.add(set);
		message = new JLabel("Message Window");
		text = new JTextArea(20, 30);
		text.setEditable(false);;
		display = new JScrollPane(text);
		JPanel east = new JPanel(new BorderLayout());
		east.setBorder(new EmptyBorder(20,20,20,20));
		east.add(message, BorderLayout.NORTH);
		east.add(display, BorderLayout.CENTER);
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Gets the name and disables the button and textfield
				String n = userF.getText();
				game.send(n);
				userF.setEditable(false);
				set.setEnabled(false);
			}
		});
		addWindowListener(new java.awt.event.WindowAdapter() {
			/**
			 * Disconnects the player when window is closed
			 */
			public void windowClosing(WindowEvent e) {
				try {
					game.in.close();
					game.socketIn.close();
					game.socketOut.close();
					game.pi.close();
					game.po.close();
					game.out.close();
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		add(east, BorderLayout.EAST);
		add(board, BorderLayout.WEST);
		add(userName, BorderLayout.SOUTH);
		setVisible(true);
		
	}
	/**
	 * Creates the board buttons and creates the player letter label and player letter field. 
	 */
	public void setBoard ()
	{
		JPanel grid = new JPanel (new GridLayout(3, 3, 5, 5)); //Button
		for (int i = 0 ; i< 3; i++) {
			for(int j=0; j< 3 ; j++)
			{
				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(new GameListener(i,j));
				buttons[i][j].setPreferredSize(new Dimension(100,100));
				grid.add(buttons[i][j]);
			}
		}
		player = new JLabel ("Player:", JLabel.CENTER);
		playerF = new JTextField(4);
		JPanel flow = new JPanel(); //Panel for player 
		flow.add(player);
		flow.add(playerF);
		playerF.setEditable(false);
		board.setBorder(new EmptyBorder(50, 30, 30, 30));
		board.add(grid, BorderLayout.NORTH);
		board.add(flow, BorderLayout.CENTER);
	}
	/**
	 * Updates the gui text message window.
	 * @param temp - The string to be appended to the message window
	 */
	public void updateDisplay(String temp) {
		if(temp.equals("Please enter your name.")||temp.equals("Please try again."))
		{
			userF.setEnabled(true);
			set.setEnabled(true);
		}
		else {
			changeButtons(true);
		}
		text.append(temp +"\n");
	}
	/**
	 * Updates the player mark
	 * @param mark - mark for the player
	 */
	public void updateMark(String mark)
	{
		playerF.setText(mark);
	}
	/**
	 * Updates the text on the buttons
	 * @param board - the string of the board
	 */
	public void updateButtons(String board)
	{
		for(int i =0, k =0; i<3; i++) {
			for(int j =0; j< 3; j++, k++)
			{
				buttons[i][j].setText(Character.toString(board.charAt(k)));
			}
		}
	}
	/**
	 * Sets the client to the gui
	 * @param game - the client for linking to the gui
	 */
	public void add (ClientGame game)
	{
		this.game = game;
	}
	/**
	 * The Listener class for the buttons
	 * @author Thomas Vy
	 * @since March 22 2018
	 * @version 1.0
	 *
	 */
	private class GameListener implements ActionListener {
		/**
		 * The row and column that indicates the button
		 */
		int row, col;
		/**
		 * Constructor of the listener
		 * @param r -row of button
		 * @param c - column of button
		 */
		public GameListener (int r, int c)
		{
			row =r;
			col = c;
		}
		/**
		 * send the row and column to the server when button pressed
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.send(Integer.toString(row)+" "+Integer.toString(col));
		}
	}
	/**
	 * Changes the state of the button 
	 * @param a - the state of the button
	 */
	public void changeButtons(boolean a)
	{
		for(int i =0; i<3 ; i++)
		{
			for(int j =0; j<3 ;j++)
			{
				buttons[i][j].setEnabled(a);
			}
		}
	}
}
