import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
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
		
		//Set instant variable
		numberOfPlayers = p;
		//Set the size of the players array
		players = new Player[p];
		
		//Adds all players to the players array
		for (int i = 1; i <= p; i++) {
			
			//Create player object and add to the array
			Player player = new Player("Player " + i);
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
}
