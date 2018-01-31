package game;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Game {

	//Class instance variables
	private Deck deck = new Deck();
	private int numberOfPlayers;
	private ArrayList<Player> players = new ArrayList<Player>(); 	
	private CardPile communalPile = new Deck(); //variable for the communal pile
	private Player currentPlayer;
	private Player winner;

	//Initialise the test log variables
	private String filename = "toptrumps.log";
	private boolean testlog = false;
	private PrintWriter writer = null;

	/**
	 * Constructor initialises deck and sets up game
	 */
	public Game(boolean testlog, int AIPlayers) {

		//If the user wishes to print to the test log, open a print writer
		this.testlog=testlog;
		if (testlog) openLogWriter();

		//Populate the deck
		populateDeck();

		//If testlog mode is active, print the initial deck to the log
		if (testlog) {
			writer.print("Deck after loading: ");
			for (int i=0;i<deck.getPile().size();i++)
				writer.print(" " + deck.getPile().get(i).getName() + " ");
		}

		//Shuffle the deck
		deck.shuffle();

		//If testlog mode is active, print the shuffled deck to the log
		if (testlog) {
			writer.print("\nDeck after shuffling: ");
			for (int i=0;i<deck.getPile().size();i++)
				writer.print(" " + deck.getPile().get(i).getName() + " ");
		}

		//Set the number of AI players
		setAIPlayers(AIPlayers);

		//Deal cards to all players
		deal();

		//Choose a random first player
		setFirstPlayer();
	}

	/**
	 * Populates the deck with cards from the input file
	 */
	private void populateDeck() {

		//File in which card info is contained
		String filename = "StarCitizenDeck.txt";

		//Initialise scanner and reader to null
		FileReader reader = null;
		Scanner scanner = null;

		try {

			try {

				//Set up reader and scanner
				reader = new FileReader(filename);
				scanner = new Scanner(reader);

				//Counter to skip first line
				int lineNumber = 0;

				//Scan all lines in the file
				while (scanner.hasNextLine()) {

					//Get a complete line from the file
					String data = scanner.nextLine();

					//If it is not the first line of the file
					if (lineNumber != 0) {

						//Create a new card object
						Card card = new Card(data);

						//Add the card to the deck
						deck.add(card);

					}

					//Increment the line number
					lineNumber++;
				}
			}

			finally {

				//Close the reader and scanner if they have been opened
				if (reader != null) {
					//close the reader
					reader.close();
				}
				if (scanner != null) {
					//close the scanner
					scanner.close();
				}
			}
		}

		catch (IOException e) {

			//File loading error
			JOptionPane.showMessageDialog(null, "Error loading file", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (InputMismatchException e) {

			//File reading error
			JOptionPane.showMessageDialog(null, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Sets the number of players for the game
	 * @param The number of players - p
	 */
	private void setAIPlayers(int p) {

		//total players is AI player + human player
		numberOfPlayers = p + 1;

		//Adds all players to the players array
		for (int i = 0; i <= (numberOfPlayers-1); i++) {

			//check if it's human or AI
			//Put human player in first index
			if (i==0) { 

				Player player = new Player("You",false);
				players.add(0, player);
			}
			else {

				//Create an AI player object and add to the array
				Player player = new Player("Player " + i,true);
				players.add(player);
			}

			//set player IDs
			players.get(i).setID(i+1);
		}	
	}

	/**
	 * Deals the cards to each player
	 */
	private void deal() {

		//Deal cards from the deck to the player
		deck.dealCards(players);

		//If the user wishes to print to the testlog, print each hand
		if (testlog) printHandsToLog();
	}

	/**
	 * returns the random first player
	 * @return first player
	 */
	private Player setFirstPlayer() {

		//integer to store random index
		int fp;
		//generate random number
		Random rn = new Random();

		//Get a random integer in the correct range
		fp = rn.nextInt(numberOfPlayers);

		//Set the first player as the current player.
		currentPlayer = players.get(fp);

		//Return the first player
		return players.get(fp);
	}

	/**
	 * Method to get the player who's turn it is to play
	 * @return the player who's turn it is
	 */
	public Player getCurrentPlayer() {

		return currentPlayer;
	}

	/**
	 * Method to get the number of players still in the game
	 * @return number of player still in game
	 */
	public int getNumberOfPlayer() {

		return numberOfPlayers;
	}

	/**
	 * Draws the first card of all remaining players hands so that it is ready to play.
	 * Must be called before playRound().
	 */
	public void drawCards() {

		//Each player draws a new card	
		for (int i=0;i<numberOfPlayers;i++) {

			//Draw a new card
			players.get(i).drawCard();
		} 

		//If test log mode is active, write the cards in play to the file
		if (testlog) {

			for (int i = 0; i < numberOfPlayers; i++) {

				writer.println(players.get(i).getCurrentCard());
			}
		}
	}

	/**
	 * Method to get a list of all the remaining players in the game
	 * @return list of remaining players
	 */
	public ArrayList<Player> getPlayers() {

		return players;
	}

	/**
	 * method with the logic of playing each round
	 * @param attribute that has been selected to compare
	 * @return the player who has won the round
	 */
	public Player playRound(String attribute) {

		//variable for the attribute the user chooses to compare
		String selectedAttribute = attribute;

		//find who wins the round
		int winnerIndex = findWinner(selectedAttribute);

		//allocate the cards to the winner/communal pile
		allocateDeck(winnerIndex);

		if (testlog) printHandsToLog();

		//findWinner returns -1 if there has been a draw
		if (winnerIndex == -1) {

			//No winner, return null
			return null;
		}
		else {

			//Get the winning player from the index and return
			Player winningPlayer = players.get(winnerIndex);
			return winningPlayer;
		}
	}

	/**
	 * Finds the player that won
	 * @param String selectedAttribute
	 * @return the index of player with the winning card 
	 * OR -1 if draw
	 */
	private int findWinner(String selectedAttribute) {

		//variable for the highest value of selected attribute
		int max = 0;

		//variable for the index of the card with highest value
		int winnerIndex = 0;

		//variable to store how many times highest value was found 
		int counter = 0;

		//variable for the value of the attribute that is currently being tested
		int value = -1;

		//go through each player's first card, find the max and hold its index
		for (int i=0;i<numberOfPlayers;i++) { 
			value = players.get(i).getCurrentCard().getAttribute(selectedAttribute);
			if (value>max) {
				max = value; //if the currently-tested value is greater than max, set max to currently-tested value
				winnerIndex = i; //update the index of max card
			}
		}

		//check if more than one card have the highest value 
		for (int j=0; j<numberOfPlayers; j++) {
			if (players.get(j).getCurrentCard().getAttribute(selectedAttribute) == max)
				counter++;
		}

		// check if it's a draw. return -1 if yes, else return winner index
		if (counter>1) {
			winnerIndex = -1;
		}

		return winnerIndex;
	}

	/**
	 * Proceeds the result of the round;
	 * @param selectedAttribute
	 */
	private void allocateDeck(int winnerIndex) {


		// variable that stores the index of the winner (if any)
		int winner = winnerIndex;

		// if it is a draw, put all current cards in the communalPile
		if (winner==-1) {
			for (int i = 0; i < numberOfPlayers; i++)
				communalPile.add(players.get(i).getCurrentCard());
		}

		// if there is a winner
		else {

			// adds all the current cards to his hand
			for (int i = 0; i < numberOfPlayers; i++) {
				players.get(winner).addToHand(players.get(i).getCurrentCard());
			}

			// add the cards from communalPile (if any) to his hand 
			if (communalPile.getCardCount()>0) {
				for (int i = 0; i< communalPile.getCardCount(); i++) {
					players.get(winner).addToHand(communalPile.getCard(i));
				}

				// empty communal Pile
				clearCommunalPile();
			}

			//Set the winner to go next
			currentPlayer = players.get(winner);
		}

		//If testlog mode is active, print the communal pile to the testlog
		if (testlog) writer.println("Communal Pile: " + communalPile);
	}

	/**
	 * Removes all cards from communal pile
	 */
	private void clearCommunalPile() {
		for (int i = (communalPile.getCardCount()-1); i > -1 ; i--) {
			communalPile.remove(i);
		}
	}

	/**
	 * method to determine if the game is over: checks if more than one players have cards left
	 * @return if game should continue
	 */
	public boolean continueGame() {

		//flag variable that shows if game should continue
		boolean continueGame = true;

		//variable that counts how many players have cards left
		int counter =0;

		//for each player that has cards left, increment counter
		for (int i=0;i<players.size(); i++) {
			if (players.get(i).getRemainingCards() != 0) {
				counter++;
			}
		}

		//if only one player has cards left, turn flag variable to false
		if (counter<2) {
			//The winner will be the only player left in the players array
			winner = players.get(0);
			//End the game
			continueGame =false;

			//If testlog is active, print the winner and then close the writer
			if (testlog) {
				writer.println("Winner: " + winner);
				closeLogWriter();
			}
		}

		//Return whether the game should continue
		return continueGame;
	}

	/**
	 * Removes eliminated players from the list of players
	 * Must be called at the end of every round
	 * @return a list of all the eliminated players
	 */
	public ArrayList<Player> clearPlayers() {

		//Array list to store all of the eliminated players
		ArrayList<Player> eliminated = new ArrayList<Player>();

		//Loop through all remaining players
		for (int i=numberOfPlayers-1; i >=0 ; i--) {

			//If the player has no cards remaining
			if (players.get(i).getRemainingCards() == 0 ) {

				//Add to the list of eliminated players
				eliminated.add(players.get(i));
				//Removes the player from the list of remaining players 
				players.remove(i);
				//Decrement the number of players still in the game
				numberOfPlayers--;
			}
		}

		//Return the list of eliminated players
		return eliminated;
	}

	/**
	 * To be called when the game is finished
	 * @return the winning player
	 */
	public Player getWinner(){

		return winner;
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
	private void closeLogWriter(){

		//Close the writer if it has been opened
		if (writer != null) {

			System.out.println("I am here");
			//Close the writer
			writer.close();
		}
	}


	/**
	 * Prints every players hand to the testlog file
	 */
	private void printHandsToLog() {

		//Loop through each player in the array list
		for (int i = 0; i < numberOfPlayers; i++) {

			writer.print(System.lineSeparator() + players.get(i) + ": ");
			for (int j=0; j<players.get(i).getHand().getPile().size();j++) {
				//Prints the players hand to the test log file
				writer.print(players.get(i).getHand().getPile().get(j).getName() + " ");
			}
		}
	}
}
