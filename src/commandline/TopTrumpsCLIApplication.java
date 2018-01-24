package commandline;

import game.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
//import Game;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection

		//Create a scanner object to read user input
		Scanner scanner = new Scanner(System.in);

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			//Ask user if they want to view statistics
			promptStats(scanner);

			//Get the number of players the user wishes to play against
			int numPlayers = chooseAIPlayers(scanner);

			//Start the game logic
			Game game = new Game(writeGameLogsToFile);
			//Sets the number of players
			game.setAIPlayers(numPlayers);
			//Deal the hands 
			game.deal();

			//Randomly select the first player
			Player firstPlayer = game.firstPlayer();

			//Display to the player who is to go first
			if (firstPlayer.isAI()){
				System.out.println(firstPlayer + " (AI) to go first.");
			}
			else {
				System.out.println("You to go first.");
			}

			while (game.continueGame()) {

				Player currentPlayer = game.getCurrentPlayer();

				Card drawn = game.drawCards();

				System.out.println("Your card is " + drawn);

				String selectedAttribute;

				//The current player is an AI
				if (currentPlayer.isAI()) {

					System.out.println(currentPlayer + " to play");

					selectedAttribute = currentPlayer.getBestAttribute();
					System.out.println(currentPlayer + " chooses the catagory " + selectedAttribute);
				}
				else {

					System.out.println("It is your turn. Pick a catagory.");

					for (;;) {

						selectedAttribute = scanner.nextLine();

						//Check the input is valid
						if (!(selectedAttribute.toLowerCase().equals("speed") || selectedAttribute.toLowerCase().equals("firepower")||
								selectedAttribute.toLowerCase().equals("size")|| selectedAttribute.toLowerCase().equals("cargo")||
								selectedAttribute.toLowerCase().equals("range"))) {

							System.out.println("Selected attribute " + selectedAttribute + " does not exist. Please enter one of the following attributes: Speed - Cargo - Firepower - Size - Range.");
						}
						else {

							System.out.println("You have selected " + selectedAttribute + ".");
							break;
						}
					}
				}

				Player winner = game.playRound(selectedAttribute);

				ArrayList<Player> players = game.getPlayers();

				System.out.println("Everbody shows their cards");

				for (int i = 0; i < players.size(); i++) {

					System.out.println(players.get(i) + " - " + players.get(i).getCurrentCard());
				}

				if (winner == null) {

					System.out.println("The round was a draw. Cards added to the communal pile.");
				} else {

					System.out.println(winner + " won the round");
				}

				System.out.println("Type anything to play the next round");

				String nextRound = scanner.nextLine();
			}

			userWantsToQuit=true; // use this when the user wants to exit the game

		}
	}

	private static void promptStats(Scanner scanner) {

		//Loop until the user wants to play the game
		for (;;) { 

			//Prompt user whether they wish to see stats
			System.out.println("Welcome to Top Trumps.\nType s to see stats or type anything else to play the game!");
			//Wait for user input and put to lower case
			String stats = scanner.nextLine().toLowerCase();

			if (stats.equals("s")) {

				//Print statistics here
			}
			else {

				//User wants to play the game. Return to main loop
				return;
			}
		}
	}

	private static int chooseAIPlayers(Scanner scanner) {

		//Prompt user to choose the number of AI players
		System.out.println("How many AI players would you like to play against (Max 4)");

		//Loop until the user enters a valid response
		for(;;) {

			try {

				//Get the user response
				int numPlayers = scanner.nextInt();

				//Check that it is between 1 and 4
				if (numPlayers < 1 || numPlayers > 4) {

					//Inform user that the number is out of range
					System.out.println("Please enter a number between 1 and 4");
				}
				//If valid, return the integer
				else {

					System.out.println("You have chosen " + numPlayers + " AI players. Let the games begin.");
					//Use up carriage return
					scanner.nextLine();
					return numPlayers;
				}
			}
			//Catch any non integer that is entered
			catch (InputMismatchException e) {

				//Tell user input needs to be an integer
				System.out.println("Please enter an integer");
				//Use up carriage return
				scanner.nextLine();
			}	
		}
	}
}
