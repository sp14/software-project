//Debugging class to be removed

import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Debugger {

	public static void main(String[] args) {

		String filename = "StarCitizenDeck.txt";
		Deck deck = new Deck();

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
		
		deck.printPile();
		deck.shuffle();
		deck.printPile();
		
		Player one = new Player ("Player one");
		Player two = new Player ("Player two");
		Player three = new Player ("Player three");
		
		Player[] players = {one, two, three};
		
		deck.dealCards(players);
		
		
	}	
}
