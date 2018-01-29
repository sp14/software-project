package game;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Game {

	//Class instance variables
	private Deck deck;
	private int numberOfPlayers;
	private ArrayList<Player> players = new ArrayList<Player>(); 	
	private ArrayList<Player> startingPlayers = new ArrayList<Player>();
	private CardPile communalPile = new Deck(); //variable for the communal pile
	private boolean testlog = false;
	private int roundCounter = 0; //variable for the number of rounds
	private int drawCounter = 0; //variable for the number of draws


	//The player whose turn it is
	private Player currentPlayer;

	/**
	 * Constructor initialises deck
	 */
	public Game(boolean testlog) {

		this.testlog=testlog;

		//Create a new deck and populate it
		deck = new Deck();
		populateDeck();

		//Shuffle the deck
		deck.shuffle();
	}

	/**
	 * Populates the deck with cards from the input file
	 */
	public void populateDeck() {

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
	public void setAIPlayers(int p) {

		//total players is AI player + human player
		numberOfPlayers = p + 1;

		////	Set the size of the players array
		//		players = new Player[numberOfPlayers];

		//Adds all players to the players array
		for (int i = 0; i <= (numberOfPlayers-1); i++) {

			//check if it's human or AI
			//Put human player in first index
			if (i==0) { 

				Player player = new Player("You",false);
				players.add(0, player);
				startingPlayers.add(0, player);
			}
			else {

				//Create an AI player object and add to the array
				Player player = new Player("Player " + i,true);
				players.add(player);
				startingPlayers.add(player);
			}

			//set player IDs
			players.get(i).setID(i+1);
			startingPlayers.get(i).setID(i+1);
		}	
	}

	/**
	 * Deals the cards to each player
	 */
	public void deal() {

		deck.dealCards(players);
	}

	/**
	 * returns the random first player
	 * @return
	 */
	public Player firstPlayer() {

		int fp;
		Random rn = new Random();

		fp = rn.nextInt(numberOfPlayers);

		//Set the first player as the current player.
		currentPlayer = players.get(fp);

		//Return the first player
		return players.get(fp);
	}

	public Player getCurrentPlayer() {

		return currentPlayer;
	}

	//Experimenting with this method
	public Card drawCards() {

		//fill the middle deck with the first card of each player's hand	
		for (int i=0;i<players.size();i++) {
			players.get(i).drawCard();
		} 

		//Returns the human players card
		return players.get(0).getCurrentCard();
	}

	public ArrayList<Player> getPlayers() {

		return players;
	}

	/**
	 * method with the logic of playing each round
	 * returns winner of the round, if no winner, returns null
	 */
	public Player playRound(String attribute) {
		testHands("before");

		//variable for the attribute the user chooses to compare
		String selectedAttribute = attribute;

		//find who wins the round
		int winnerindex = findWinner(selectedAttribute);

		//allocate the cards to the winner/communal pile
		allocateDeck(winnerindex);

		//variable for the player who won the round. 
		Player winningPlayer = null;

		//if there is a winner, store the winner in winningPlayer variable		
		if (winnerindex >= 0)
			winningPlayer = players.get(winnerindex);

		//remove eliminated players
		clearPlayers();

		//update game statistics
		updateGameStats(winningPlayer);

		//print game stats
		printStats();

		testHands("after");

		return winningPlayer;
	}

	/**
	 * Finds the player that won
	 * @param CardPile tempDeck
	 * @param String selectedAttribute
	 * @return the index of player with the winning card 
	 * OR -1 if draw
	 */
	private int findWinner(String selectedAttribute) {
		//variable for the highest value of selected attribute found in tempDeck
		int max = 0;

		//variable for the index of the card with highest value
		int winnerindex = 0;

		//variable to store how many times highest value was found 
		int counter = 0;

		//variable for the value of the attribute that is currently being tested
		int value = -1;

		//go through each player's first card, find the max and hold its index
		for (int i=0;i<players.size();i++) { 
			value = players.get(i).getCurrentCard().getAttribute(selectedAttribute);
			if (value>max) {
				max = value; //if the currently-tested value is greater than max, set max to currently-tested value
				winnerindex = i; //update the index of max card
			}
		}

		//check if more than one card have the highest value 
		for (int j=0; j<players.size(); j++) {
			if (players.get(j).getCurrentCard().getAttribute(selectedAttribute) == max)
				counter++;
		}

		// check if it's a draw. return -1 if yes, else return winner index
		if (counter>1) {
			winnerindex = -1;

			// TESTING: print highest value and number of occurences for testing purposes
			System.out.println("it is a draw. the highest value is "+ value +" and was found " + counter+ " times.");
		}
		else {

		}

		return winnerindex;
	}

	/**
	 * Proceeds the result of the round;
	 * @param selectedAttribute
	 */
	private void allocateDeck(int winnerindex) {


		// variable that stores the index of the winner (if any)
		int winner = winnerindex;

		// if it is a draw, put cards from tempDeck to communalPile
		if (winner==-1) {
			for (int i = 0; i < players.size(); i++)
				communalPile.add(players.get(i).getCurrentCard());
		}

		// if there is a winner
		else {

			// add the cards from tempDeck to his hand
			for (int i = 0; i < players.size(); i++) {
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
		
		//if there is only one player left, end game
		if (players.size()<2)
			continueGame = false;
		
		return continueGame;
	}

	/**
	 * removes players with no cards left in their hand
	 */
	public void clearPlayers() {
		for (int i=0; i < players.size() ; i++)
			if (players.get(i).getRemainingCards() == 0 ) {
				players.remove(i);
			}
	}

	/**
	 * testing method, to be deleted. prints players hands.
	 * @param s
	 */
	private void testHands(String s) {
		System.out.println(s);
		for (int i =0 ; i < players.size();i++) {
			System.out.print(players.get(i).getName()+"	");
			for(int j=0;j<players.get(i).getRemainingCards();j++) {
				System.out.print(players.get(i).getHand().getCard(j).getName()+" ");
			}
			System.out.println();
		}
		System.out.print("communal pile: ");
		for (int i =0 ; i < communalPile.getCardCount();i++) {
			System.out.print(communalPile.getCard(i).getName()+ " ");
		}
		System.out.println();
	}

	/**in the end of every round, updates the number of rounds played
	 * and returns a String if player was AI or Human
	 * @param p : the winning player
	 */
	private String updateGameStats(Player p) {
		String s="";
		if (p != null) {
			p.setWinCounter(p.getWinCounter()+1);
			if (p.isAI()==true) {
				s = "AI won";
			}
			else s = "Human won";
		}
		
		else drawCounter++;
		roundCounter++;
		
		//print out variables for testing
		System.out.println("round " + roundCounter);
		System.out.println(s);
		return s;
		}
	
	public void printStats() {
		for (int i=0;i < startingPlayers.size();i++) {
			System.out.println(startingPlayers.get(i).getName() + ": " + startingPlayers.get(i).getWinCounter()+ " victories." );						
		}
		System.out.println("Draws: " + drawCounter);
		
	}
		
}
