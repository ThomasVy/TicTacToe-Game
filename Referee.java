package TicTacToe;
import java.io.IOException;
/**
 * The referee that monitors the game
 * @author Thomas Vy
 * @since January 31, 2018
 * @version 4.0
 */
public class Referee {
/**
 * The X mark player
 */
private Player xPlayer;
/**
 * The O mark player
 */
private Player oPlayer;
/**
 * The board to play on
 */
private Board board;
/**
 * Sets the opponents and grabs the player's names and lets player x start.
 */
public void runTheGame() throws IOException
{
		oPlayer.setOpponent(xPlayer);
		xPlayer.setOpponent(oPlayer); //Sets the opponent of x Player to o Player
		
		xPlayer.getPlayerName();
		oPlayer.getPlayerName();
		System.out.println("\nReferee started the game...");
		xPlayer.play(); //x Player starts
		System.out.println("\nReferee ended the game...");
}
/**
 * Sets the board
 * @param board the board to be set to.
 */
public void setBoard(Board board)
{
	this.board=board;
}
/**
 * Gets the oPlayer
 * @return - returns the oPlayer
 */
public Player getoPlayer()
{
	return oPlayer;
}
/**
 * Gets the xPlayer
 * @return - returns the xPlayer
 */
public Player getxPlayer() {
	return xPlayer;
}
/**
 * sets the player to be oPlayer
 * @param oPlayer The player to be set to be the oPlayer
 */
public void setoPlayer(Player oPlayer)
{
	this.oPlayer=oPlayer;
}
/**
 * sets the player to be xPlayer
 * @param xPlayer The player to be set to be the xPlayer
 */
public void setxPlayer(Player xPlayer)
{
	this.xPlayer=xPlayer;
}
}
