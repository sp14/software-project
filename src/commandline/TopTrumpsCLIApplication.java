package commandline;

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

			//			Game game = new Game();
			//			game.populateDeck();
			//			game.shuffleAndDeal();


			userWantsToQuit=true; // use this when the user wants to exit the game

		}
	}

	private static void promptStats(Scanner scanner) {

		//Loop until the user wants to play the game
		for (;;) { 

			//User prompt
			System.out.println("Welcome to Top Trumps.\nType s to see stats or type anything else to play the game!");
			//User input
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

		System.out.println("How many AI players would you like to play against (Max 4)");

		for(;;) {

			try {

				int numPlayers = scanner.nextInt();

				if (numPlayers < 1 || numPlayers > 4) {

					System.out.println("Please enter a number between 1 and 4");
				}
				else {

					return numPlayers;
				}
			}
			catch (InputMismatchException e) {

				System.out.println("Please enter an integer");
			}	
		}
	}
}
