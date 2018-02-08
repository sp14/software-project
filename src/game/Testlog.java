package game;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Testlog {

	//Initialise the test log variables
	private String filename = "toptrumps.log";
	private PrintWriter writer = null;

	public Testlog() {

		openLogWriter();
	}

	public void printSelectedAttributeToLog(Player player, String attribute) {

		writer.println(System.lineSeparator() + player + " chose the catagory: " + attribute);
	}

	public void printRoundWinnerToLog(Player roundWinner) {

		if (roundWinner == null) {
			writer.println(System.lineSeparator() + "The round was a draw.");
		}
		else {
			writer.println(System.lineSeparator() + roundWinner + " won the round.");
		}
	}

	public void printWinnerToLog(Player winner) {

		writer.println(System.lineSeparator() + System.lineSeparator() + winner + " won the game");
	}

	public void printDeckToLog(CardPile deck, String description) {

		writer.print(System.lineSeparator() + description + ": ");
		for (int i=0;i<deck.getPile().size();i++)
			writer.print(deck.getPile().get(i).getName() + " ");
		writer.print(System.lineSeparator());
	}

	public void printCurrentCardsToLog(ArrayList<Player> players){

		writer.println("Cards in play");
		for (int i = 0; i < players.size(); i++) {

			writer.println(players.get(i).getName() + ": " + players.get(i).getCurrentCard());
		}
	}

	/**
	 * Prints every players hand to the testlog file
	 */
	public void printHandsToLog(ArrayList<Player> players) {

		writer.print(System.lineSeparator() + "Players hands after cards have been allocated");
		//Loop through each player in the array list
		for (int i = 0; i < players.size(); i++) {

			writer.print(System.lineSeparator() + players.get(i) + ": ");
			for (int j=0; j<players.get(i).getHand().getPile().size();j++) {
				//Prints the players hand to the test log file
				writer.print(players.get(i).getHand().getPile().get(j).getName() + " ");
			}
		}
	}

	/**
	 * Prints a separator with the current round number into the log file
	 * @param round - round number
	 */
	public void printRoundSeparator(int round) {

		writer.println(System.lineSeparator() + "----------- Round" + round + " -----------");
	}

	/**
	 * Opens a print writer to write to the test log file
	 */
	private void openLogWriter() {

		try {

			//Set up the PrintWriter
			writer = new PrintWriter(filename);
		} 
		//Catch exception
		catch (FileNotFoundException e) {

			//Show error message
			System.out.println("The file could not be found");
		}
	}

	/**
	 * Closes the print writer when it is no longer needed
	 */
	public void closeLogWriter(){

		//Close the writer if it has been opened
		if (writer != null) {

			System.out.println("I am here");
			//Close the writer
			writer.close();
		}
	}
}
