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
		//work needs to be done here
		return players[0];
	}
	
	/**
	 * method with the logic of playing each round
	 */
	public void playRound() {

		//variable for the temporary Array of cards in the middle
		CardPile tempDeck = new Deck();

		//variable for the communal pile
		CardPile communalPile = new Deck();

		//fill the middle deck with the first card of each player's hand	
		for (int i=0;i<numberOfPlayers;i++) {
			tempDeck.add(players[i].drawCard());

		} 




		// TESTING: print results in console for testing purposes
		System.out.print("middle deck: ");
		tempDeck.printPile();

		for (int i=0;i<numberOfPlayers;i++) {
			System.out.print("player " + (i+1) + " current hand: "); 
			players[i].getHand().printPile();
		}
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
