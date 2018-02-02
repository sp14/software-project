package commandline;

import game.*;
import DBHandler.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 * @param args
	 */
	public static void main(String[] args, PostgresSQL con) {

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

			//Create new Game
			Game game = new Game(con);

			//initiate the game 
			game.initGame(writeGameLogsToFile, numPlayers);
			//Play rounds until there is a winner
			roundLogic(scanner, game);

			//The game is over. Display winner
			System.out.println("Game over. The winner is " + game.getWinner() + "\nWould you like to play a new game? type y for a new game or anything else to quit");

			// update DB
			updateDBStats(game, game.getWinner(),con);

			//Ask user whether they wish to continue with game
			String keepPlaying = scanner.nextLine();

			//Keep playing?
			if (!keepPlaying.equals("y")) {

				userWantsToQuit=true;
			}
		}
	}

	private static void updateDBStats(Game game, Player p, PostgresSQL con) {

		int currentGameNo = game.getRoundCounter();
		int totalRounds = game.getRoundCounter();
		int totalDraws = game.getDrawCounter();
		String winner = game.getWinnerString(p);

		con.insertIntoGameTable(currentGameNo, totalRounds, totalDraws, winner);
	}

	/**
	 * Plays rounds until a there is a winner
	 * @param scanner
	 * @param game
	 */
	private static void roundLogic(Scanner scanner, Game game) {

		while (game.continueGame()) {

			//Whose turn is it to play
			Player currentPlayer = game.getCurrentPlayer();
			//Draw cards and add them to players hands
			game.drawCards();
			//Get all the remaining players for output purposes
			ArrayList<Player> players = game.getPlayers();

			//If the human player is still in the game
			if (!players.get(0).isAI()) {

				//The human will always be at index 0 if they are in the game. Display their card	
				System.out.println("Your card is " + players.get(0).getCurrentCard());
			}

			//Get the attribute that is to be compared
			String selectedAttribute = getSelectedAttribute(scanner, currentPlayer);

			//Display everyones cards
			displayCards(players);

			//Get the winner of the round and display in the console
			Player winner = game.playRound(selectedAttribute);
			displayWinner(winner);

			//Get a list of all eliminated players and display in the console
			ArrayList<Player> eliminated = game.clearPlayers();
			displayEliminatedPlayers(eliminated);

			//displayRemaingCardCount

			//Wait for user input to start the next round
			System.out.println("Type anything to play the next round");
			String nextRound = scanner.nextLine();
		}
	}

	/**
	 * Ask the user whether they wish to see the stats of previous games before they play
	 * @param scanner
	 */
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

	/**
	 * Allows the user to choose how many AI player they wish to play against
	 * @param scanner
	 * @return the number of AI players the user has chosen
	 */
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

	private static String getSelectedAttribute(Scanner scanner, Player currentPlayer) {

		String selectedAttribute;

		//The current player is an AI
		if (currentPlayer.isAI()) {

			//Tell the user which AI is playing 
			System.out.println(currentPlayer + " to play");
			//Select the strongest attribute on the AI's card
			selectedAttribute = currentPlayer.getBestAttribute();
			//Tell the user which category the AI has chosen
			System.out.println(currentPlayer + " chooses the catagory " + selectedAttribute);
		}
		//It is the users turn to play
		else {

			selectedAttribute = getUserAttributeInput(scanner);
		}

		return selectedAttribute;
	}

	private static String getUserAttributeInput(Scanner scanner) {

		//Prompt the user to pick a category
		System.out.println("It is your turn. Pick a catagory to compare.");

		String selectedAttribute;

		//Loop until the user provides a valid input
		for (;;) {

			//Read the users input
			selectedAttribute = scanner.nextLine();

			//Check the input is valid
			if (!(selectedAttribute.toLowerCase().equals("speed") || selectedAttribute.toLowerCase().equals("firepower")||
					selectedAttribute.toLowerCase().equals("size")|| selectedAttribute.toLowerCase().equals("cargo")||
					selectedAttribute.toLowerCase().equals("range"))) {

				//If the input is not valid, tell user and ask again
				System.out.println("Selected attribute " + selectedAttribute + " does not exist. Please enter one of the following attributes: Speed - Cargo - Firepower - Size - Range.");
			}
			else {

				//The input is valid. Tell user and break the loop
				System.out.println("You have selected " + selectedAttribute + ".");
				break;
			}
		}

		return selectedAttribute;
	}

	private static void displayWinner(Player winner){

		//No winner. The round was a draw, inform user
		if (winner == null) {

			System.out.println("The round was a draw. Cards added to the communal pile.");
		} else {

			System.out.println(winner + " won the round");
		}
	}

	private static void displayEliminatedPlayers(ArrayList<Player> eliminated) {

		if (!(eliminated == null)) {
			for (int i = 0; i < eliminated.size(); i++) {

				Player elimPlayer = eliminated.get(i);

				if (elimPlayer.isAI()) {

					System.out.println(elimPlayer + " has been eliminated");	
				}
				else {

					System.out.println("You have been eliminated");
				}
			}
		}
	}

	private static void displayCards(ArrayList<Player> players) {

		System.out.println("Everbody shows their cards");

		//Print out the current cards of all the players
		for (int i = 0; i < players.size(); i++) {

			System.out.println(players.get(i) + " - " + players.get(i).getCurrentCard());
		}
	}
}
