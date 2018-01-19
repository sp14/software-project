import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Game {

	//Class instance variables
	private Deck deck;
	private int numberOfPlayers;
	private Player[] players; 


	/**
	 * Constructor initialises deck
	 */
	public Game() {

		deck = new Deck();
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
	public void selectPlayers(int p) {

		//variable to show if player is AI
		boolean AI;
		//Set instant variable
		numberOfPlayers = p;
		//Set the size of the players array
		players = new Player[p];

		//Adds all players to the players array
		for (int i = 1; i <= p; i++) {
			//check if it's human or AI
			if (i==1) 
				AI=false;							
			else AI= true;

			//Create player object and add to the array
			Player player = new Player("Player " + i,AI);
			players[i - 1] = player;
		}	

		System.out.println(Arrays.deepToString(players));
	}

	/**
	 * Shuffles the deck and deals cards to each player
	 */
	public void shuffleAndDeal() {

		deck.shuffle();
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
		System.out.println("first player of the game is player : "+(fp+1));

		return players[fp];
	}

	/**
	 * method with the logic of playing each round
	 */
	public void playRound() {

		//Create a scanner object to read user input
		Scanner scanner = new Scanner(System.in);

		//variable for the temporary Array of cards in the middle
		CardPile tempDeck = new Deck();

		//variable for the communal pile
		CardPile communalPile = new Deck();

		//variable for the attribute the user chooses to compare
		String selectedAttribute;

		//fill the middle deck with the first card of each player's hand	
		for (int i=0;i<numberOfPlayers;i++) {
			tempDeck.add(players[i].drawCard());
		} 

		selectedAttribute= scanner.next();


		// TESTING: print results in console for testing purposes
		System.out.print("middle deck: ");
		tempDeck.printPile();
		System.out.print(selectedAttribute + " ");
		
		findWinner(tempDeck, selectedAttribute);
		for (int i=0;i<numberOfPlayers;i++) {
			System.out.print("player " + (i+1) + " current hand: "); 
			players[i].getHand().printPile();
		}
	}

	/**
	 * finds the player that won
	 * @param tempDeck
	 * @param selectedAttribute
	 */
	public void findWinner(CardPile tempDeck, String selectedAttribute) {

		//variable for the highest value of selected attribute found in tempDeck
		int max=-1;

		//variable for the index of the card with highest value
		int maxindex = -1;

		//variable to store how many times highest value was found 
		int counter=0;

		//variable for the value of the attribute that is currently being tested
		int value=-1;

		//compare according to the given attribute
		switch(selectedAttribute) {

		//if speed was selected
		case "speed": 
			//print speed for all cards for testing purposes
			for (int i=0;i<numberOfPlayers;i++) {
				System.out.print(tempDeck.getCard(i).getSpeed() + " ");
			}
			System.out.println();
			
			//go through the tempDeck, find the max and hold its index
			for (int i=0;i<tempDeck.getCardCount();i++) { 
				value = tempDeck.getCard(i).getSpeed();
				if (value>max) {
					max = value; //if currently tested value is greater than max, set max to currently tested value
					maxindex = i; //update the index of max card
				}
			}
			for (int j=0; j<tempDeck.getCardCount(); j++) {
				if (tempDeck.getCard(j).getSpeed() == max)
					counter++;
			}
			break;

		case "range": 
			//print range for all cards for testing purposes
			for (int i=0;i<numberOfPlayers;i++) {
				System.out.print(tempDeck.getCard(i).getRange() + " ");
			}
			System.out.println();
			for (int i=0;i<tempDeck.getCardCount();i++) {
				value=tempDeck.getCard(i).getRange();
				if (value>max) {
					max = value;
					maxindex=i;
				}
			}
			for (int j=0;j<tempDeck.getCardCount();j++) {
				if (tempDeck.getCard(j).getRange()==max)
					counter++;
			}
			break;

		case "firepower": 
			//print firepower for all cards for testing purposes
			for (int i=0;i<numberOfPlayers;i++) {
				System.out.print(tempDeck.getCard(i).getFirepower() + " ");
			}
			System.out.println();
			for (int i=0;i<tempDeck.getCardCount();i++) {
				value=tempDeck.getCard(i).getFirepower();
				if (value>max) {
					max = value;
					maxindex=i;
				}
			}
			for (int j=0;j<tempDeck.getCardCount();j++) {
				if (tempDeck.getCard(j).getFirepower()==max)
					counter++;
			}
			break;

		case "cargo": 
			//print cargo for all cards for testing purposes
			for (int i=0;i<numberOfPlayers;i++) {
				System.out.print(tempDeck.getCard(i).getCargo() + " ");
			}
			System.out.println();
			
			for (int i=0;i<tempDeck.getCardCount();i++) {
				value=tempDeck.getCard(i).getCargo();
				if (value>max) {
					max = value;
					maxindex=i;
				}
			}
			for (int j=0;j<tempDeck.getCardCount();j++) {
				if (tempDeck.getCard(j).getCargo()==max)
					counter++;
			}
			break;

		case "size": 
			//print size for all cards for testing purposes
			for (int i=0;i<numberOfPlayers;i++) {
				System.out.print(tempDeck.getCard(i).getSize() + " ");
			}
			System.out.println();
			
			for (int i=0;i<tempDeck.getCardCount();i++) {
				value=tempDeck.getCard(i).getSize();
				if (value>max) {
					max = value;
					maxindex=i;
				}
			}
			for (int j=0;j<tempDeck.getCardCount();j++) {
				if (tempDeck.getCard(j).getSize()==max)
					counter++;
			}
			break;
		}

		if (counter>1) 
			System.out.println("it is a draw. the highest value is "+ value +" and was found " + counter+ " times.");

		else System.out.println("winner is player "+ (maxindex+1)+ " with card " +tempDeck.getCard(maxindex).getName());

	}	


	/**
	 * method to determine if the game is over: checks if more than one players have cards left
	 * @return if game should continue
	 */
	public boolean continueGame() {

		//flag variable that shows if game should continue
		boolean continueGame = true;

		//variable that counts how many players have cardrs left
		int counter =0;

		//for each player that has cards left, increment counter
		for (int i=0;i<numberOfPlayers; i++) {
			if (players[i].getHand().getCardCount() != 0) {
				counter++;
			}
		}

		//if only one player has cards left, turn flag variable to false
		if (counter<2) {
			continueGame =false;
		}

		return continueGame;
	}
}
