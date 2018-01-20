package game;
//Debugging class to be removed

public class Debugger {

	public static void main(String[] args) {

		Game game = new Game();
		game.populateDeck();
		game.selectPlayers(4);
		game.deal();
		game.firstPlayer();
			
		//play 9 hands for testing
		for (int i=0; i<9;i++)
			game.playRound();
			
	}	
}
