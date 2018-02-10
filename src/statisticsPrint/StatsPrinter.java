package statisticsPrint;

import DBHandler.*;
import game.Game;

public class StatsPrinter {

	/**
	 * Default Constructor
	 */
	public StatsPrinter() {

	}

	/**
	 * Method to return a ready-to-display String with the number of wins for the selected player.
	 * @param currentGameNo: the ID of game of interest
	 * @param playerName: the name of the player of interest
	 * @return s: the String to be displayed
	 */
	private String printPlayerWins(int currentGameNo, String playerName) {
		// The string that will contain the information asked
		String s="";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the number of wins returned by the database
		String wins = con.playerRoundsWon(currentGameNo, playerName);

		// Set the string in its final form
		s = playerName + " won " + wins + " round/s.";

		return s;
	}


	/**
	 * Method to return a ready-to-display String with the number of draws in the given game
	 * @param gameNo: the ID of game of interest
	 * @return s: the String to be displayed
	 */
	private String printDraws(int gameNo) {

		// The string that will contain the information asked
		String s="";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the number of draws returned by the database
		String draws = con.viewNoOfDraws(gameNo);

		// Set the string in its final form
		s = "The number of draws in game "+ gameNo +" was: " + draws;

		return s;
	}	


	/**
	 * Method to return a ready-to-display String with the average number of draws per game
	 * @return s: the String to be displayed
	 */
	private String printAvgDraws() {

		// The string that will contain the information asked
		String s = "";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the average number of draws returned by the database
		String drawsAvg = con.avgNoOfDraws();

		// Extract double and format it for display
		String drawsAvgDouble = String.format("%.2f", Double.parseDouble(drawsAvg));

		// Set the string in its final form
		s = "There are " + drawsAvgDouble + " draws per game on average." ;

		return s;
	}


	/**
	 * Method to return a ready-to-display String with the winner of a given game
	 * @param gameNo: the ID of game of interest
	 * @return s: the String to be displayed
	 */
	private String printWinner(int gameNo) {
		// The string that will contain the information asked
		String s = "";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the name of the winner returned by the database
		String winnerName = con.viewWinner(gameNo);	

		// Set the string in its final form
		s = winnerName + " won the game.";

		return s;
	}


	/**
	 *  Method to return a ready-to-display String with the total number of rounds of a given game
	 * @param gameNo
	 * @returns s: the String to be displayed
	 */
	private String printTotalRounds(int gameNo) {

		// The string that will contain the information asked
		String s = "";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the name of the winner returned by the database
		String totalRounds = con.viewTotalRounds(gameNo);	

		// Set the string in its final form
		s = "The total number of rounds played was: " + totalRounds + ".";

		return s;
	}


	/**
	 *  Method to return a ready-to-display String with the total number of games played so far
	 * @returns s: the String to be displayed
	 */
	private String printSumGames() {

		// The string that will contain the information asked
		String s = "";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the total number of games, returned by the database
		String sumGames = con.noOfGamesPlayed();	

		// Set the string in its final form
		s = "The total number of games played so far is: " + sumGames + ".";

		return s;
	}


	/**
	 *  Method to return a ready-to-display String with the total number of AI wins
	 * @returns s: the String to be displayed
	 */
	private String printAiWins() {

		// The string that will contain the information asked
		String s = "";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the total number of AI wins, returned by the database
		String AiWins = con.noOfAIWins();	

		// Set the string in its final form
		s = "The computer has won : " + AiWins + " times.";

		return s;
	}


	/**
	 *  Method to return a ready-to-display String with the total number of Human wins
	 * @returns s: the String to be displayed
	 */
	private String printHumanWins() {

		// The string that will contain the information asked
		String s = "";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the total number of AI wins, returned by the database
		String humanWins = con.noOfHumanWins();	

		// Set the string in its final form
		s = "You have won : " + humanWins + " times.";

		return s;
	}


	/**
	 *  Method to return a ready-to-display String with the longest game, in terms of rounds played
	 * @returns s: the String to be displayed
	 */
	private String printMaxRounds() {

		// The string that will contain the information asked
		String s = "";

		// Database connection object
		PostgresSQL con = new PostgresSQL();

		// String with the total number of AI wins, returned by the database
		String maxRounds = con.maxNoOfRoundsPlayed();	

		// Set the string in its final form
		s = "The longest game had "+ maxRounds + " rounds played.";

		return s;
	}


	/**
	 * Method to print all the statistics in the beginning of the game
	 * @return s: the String containing all the statistics in the beginning of the game
	 */
	public String printStatsStart() {

		// The string that will contain the information asked
		String s = "";

		s= printSumGames() + "\n" +
				printAiWins()+ "\n" +
				printHumanWins()+ "\n" +
				printAvgDraws()+ "\n" +
				printMaxRounds();

		return s;
	}


	/**
	 * Method to print all the statistics in the end of the game
	 * @return s: the String containing all the statistics in the end of the game
	 */
	public String printStatsEnd(Game game) {

		// The string that will contain the information asked
		String s = "";

		// The string for displaying each player's number of wins
		String playersWins = "";

		// Iterate through players and add each player's number of wins on the corresponding String
		for (int i = 0; i < game.getStartingPlayers().size(); i++)
			playersWins += printPlayerWins(game.getGameID(), game.getStartingPlayers().get(i).getName()) + "\n";

		// Add all parts to the final String
		s= "----- GAME OVER -----"+ "\n" +
			"----- STATISTICS -----" + "\n" +
				printDraws(game.getGameID()) + "\n" +
				printWinner(game.getGameID())+ "\n" +
				printTotalRounds(game.getGameID())+ "\n" +
				playersWins;

		return s;		
	}

}
