package game;
import java.util.ArrayList;

public abstract class CardPile {

	//instance variables
	protected ArrayList<Card> pile;
	private int cardCount;
	
	/**
	 * Constructor initialises the card array
	 */
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
	 * Removes the card at index i from the pile
	 * @param i
	 */
	public void remove(int i) {
		
		pile.remove(i);
	}

	/**
	 * Gets the number of cards in the pile
	 * @return card count
	 */
	public int getCardCount() {
		cardCount = pile.size();
		return cardCount;
	}

	/**
	 * returns Card object at index position i
	 * @param i
	 * @return Card at index position i
	 */
	public Card getCard(int i) {
		
		return pile.get(i);
	}
	/**
	 * Prints the contents of the deck to the log file
	 */
	public void printPileToLog() {

		//This needs to be changes to print to the log instead of the console
		System.out.println(pile);
	}
}
