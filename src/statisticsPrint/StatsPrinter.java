package statisticsPrint;

import DBHandler.*;

public class StatsPrinter {

	/**
	 * Default Constructor
	 */
	public StatsPrinter() {

	}

	/**
	 * Method to return a ready-to-display String with the number of wins for the selected player.
	 * @param currentGameNo
	 * @param playerName
	 * @return String containing the number of wins for the selected player
	 */
	public String showPlayerWins(int currentGameNo, String playerName) {
		// The string that will contain the information asked
		String s="";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the number of wins returned by the database
		String wins = con.playerRoundsWon(currentGameNo, playerName);

		// Set the string in its final form
		s = playerName + " won this many rounds: " + wins;

		return s;
	}

	/**
	 * Method to return a ready-to-display String with the number of draws in the given game
	 * @param gameNo
	 * @return
	 */
	public String showDraws(int gameNo) {

		// The string that will contain the information asked
		String s="";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the number of wins returned by the database
		String wins = con.viewNoOfDraws(gameNo);
		
		// Set the string in its final form
		s = "The number of draws in game "+ gameNo +" was: " + s;
		
		return s;
	}	
}
