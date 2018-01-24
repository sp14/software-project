package game;
//Debugging class to be removed

public class Debugger {

	public static void main(String[] args) {

		Game game = new Game();
		game.populateDeck();
		game.setAIPlayers(4);
		game.deal();
		game.firstPlayer();
			
		//play hands for testing
		for (;;)
			game.playRound();
			
	}	
}
