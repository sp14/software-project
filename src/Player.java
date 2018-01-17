
public class Player {

	//Instance variables
	private Hand hand;
	
	public void setHand(Hand hand) {
		this.hand = hand;
	}

	String name;
	boolean AI = false;
	
	/**
	 * Constructor
	 * @param name of player
	 */
	public Player(String name, boolean AI) {
		
		hand = new Hand();
		this.name = name;
		this.AI = AI;
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
	
	
	/**
	 * getters and setters for all Player attributes
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAI() {
		return AI;
	}

	public void setAI(boolean aI) {
		AI = aI;
	}

	public Hand getHand() {
		return hand;
	}
	
}
