//Debugging class to be removed

public class Debugger {

	public static void main(String[] args) {

		Game game = new Game();
		game.populateDeck();
		game.selectPlayers(4);
		game.shuffleAndDeal();
		
	//	while (game.continueGame()==true)
		
		//play 5 hands for testing
		for (int i=0; i<5;i++)
			game.playRound();
	}	
}
