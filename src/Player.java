
public class Player {

	//Instance variables
	protected Hand hand;
	String name;
	
	/**
	 * Constructor
	 * @param name of player
	 */
	public Player(String name) {
		
		hand = new Hand();
		this.name = name;
	}
	
	/**
	 * Default object string is the player name
	 */
	public String toString() {
		
		return name;
	}
	
	/**
	 * Adds a card to the players hand
	 * @param card
	 */
	public void addToHand(Card card) {
		
		hand.add(card);
		hand.printPile();
	}
	
	/**
	 * Draw the first card from the players hand
	 * @return card
	 */
	public Card drawCard() {
		
		return hand.draw();
	}
}
