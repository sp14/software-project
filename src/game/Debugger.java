package game;
//Debugging class to be removed

import java.util.ArrayList;
import java.util.Scanner;

public class Debugger {

	public static void main(String[] args) {
		
		// State
				boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		Scanner scanner = new Scanner(System.in);
		
		//Start the game logic
		Game game = new Game(false);
		
		System.out.println("select number of players: ");
		//Sets the number of players
		int numPlayers = scanner.nextInt();
		game.setAIPlayers(numPlayers);
		//Deal the hands 
		game.deal();

		//Randomly select the first player
		Player firstPlayer = game.firstPlayer();
		
		game.firstPlayer();
			String attribute;
		
			//Display to the player who is to go first
			if (firstPlayer.isAI()){
				System.out.println(firstPlayer + " (AI) to go first.");
			}
			else {
				System.out.println("You to go first.");
			}

			while (game.continueGame()) {

				//TESTING: print out players' hands after changes
				for (int i =0 ; i < game.getPlayers().size();i++) {
					System.out.print("player " +(i) + " ");
					for (int j = 0; j < game.getPlayers().get(i).getHand().getCardCount(); j++) {
						System.out.print(game.getPlayers().get(i).getHand().getCard(j).getName());
						System.out.print(" ");
					}
					System.out.println();
				}

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
