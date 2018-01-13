import java.util.Collections;

public class Deck extends CardPile {
	
	/**
	 * Default constructor calls super constructor
	 */
	public Deck() {
		
		super();
	}

	/**
	 * Shuffles the deck into a random order
	 */
	public void shuffle() {
		
		//Call the shuffle function in the collections class
		Collections.shuffle(pile);
	}
	
	
	/**
	 * Deal the cards out to the players
	 * @param players
	 */
	public void dealCards(Player[] players) {
		
		int counter = 0;
		
		for (int i = 0; i < pile.size(); i++) {
			
			players[counter].addToHand(pile.get(i));
			
			if (counter >= players.length - 1) {
				
				counter = 0;
			}
			else {
				
				counter++;
			}
		}
	}
}
