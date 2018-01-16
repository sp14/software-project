//Debugging class to be removed

public class Debugger {

	public static void main(String[] args) {

		Game game = new Game();
		game.populateDeck();
		game.selectPlayers(4);
		game.shuffleAndDeal();
	}	
}
