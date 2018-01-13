import java.util.ArrayList;

public abstract class CardPile {

	//instance variables
	protected ArrayList<Card> pile;
	private int cardCount;
	
	public CardPile() {
		
		pile = new ArrayList<Card>();
		cardCount = 0;
	}

	/**
	 * Adds a card to the pile
	 * @param card
	 */
	public void add(Card card) {

		pile.add(card);
		cardCount++;
	}

	/**
	 * Gets the number of cards in the pile
	 * @return card count
	 */
	public int getCardCount() {

		return cardCount;
	}

	/**
	 * Prints the contents of the deck to the log file
	 */
	public void printPile() {

		//This needs to be changes to print to the log instead of the console
		System.out.println(pile);
	}
}
