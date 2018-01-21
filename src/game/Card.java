package game;

public class Card 
{

	//Card details
	private String name;
	private int size;
	private int speed;
	private int range;
	private int firepower;
	private int cargo;


	/**
	 * Constructor. Takes a complete line from StarCitizenDeck.txt as input
	 * @param inputLine
	 */
	public Card(String inputLine) {

		//Split input string into tokens
		String[] tokens = inputLine.split("\\s+");

		//Save tokens to relevant instant variables
		this.name = tokens[0];
		this.size = Integer.parseInt(tokens[1]);
		this.speed = Integer.parseInt(tokens[2]);
		this.range = Integer.parseInt(tokens[3]);
		this.firepower = Integer.parseInt(tokens[4]);
		this.cargo = Integer.parseInt(tokens[5]);		
	}

	/**
	 * Default object string is the card name
	 */
	public String toString() {

		return name + ": size " +  size + " speed " + speed + " range " + range + " firepower " + firepower + " cargo " + cargo;
	}

	/**
	 * Returns the name of the card
	 * @return name
	 */
	public String getName() {

		return name;
	}

	/**
	 * Returns the value of the size attribute
	 * @return size
	 */
	public int getSize() {

		return size;
	}

	/**
	 * Returns the value of the speed attribute
	 * @return speed
	 */
	public int getSpeed() {

		return speed;
	}


	/**
	 * Returns the value of the range attribute
	 * @return range
	 */
	public int getRange() {

		return range;
	}


	/**
	 * Returns the value of the firepower attribute
	 * @return firepower
	 */
	public int getFirepower() {

		return firepower;
	}


	/**
	 * Returns the value of the cargo attribute
	 * @return cargo
	 */
	public int getCargo() {

		return cargo;
	}

	/**
	 * universal getter: given a String representing the attribute that the user wants
	 * returns the value of the attribute
	 * @param selected attribute: the attribute asked by the user
	 * @return the value of the selected attribute or -1 if the selected attribute was not found
	 */
public int getAttribute(String selectedAttribute) {
	
	switch (selectedAttribute) {
	case "speed": return this.speed;
	case "firepower":return this.firepower;
	case "cargo":return this.cargo;
	case "range":return this.range;
	case "size":return this.size;
	default: return -1;
	}
}
	
	
	
	
	
	//
//	/**
//	 * compares the given attribute of Cards objects 
//	 * and returns a negative or positive integer or zero according to the result 
//	 **/
//	//@Override
//	public int compareTo(Card card, String attribute) {
//		int result=0;
//		switch (attribute) {
//		case "speed": result =Integer.compare(this.speed, card.speed);
//		case "firepower":return Integer.compare(this.firepower, card.firepower);
//		case "cargo":return Integer.compare(this.cargo, card.cargo);
//		case "range": return Integer.compare(this.range, card.range);
//		case "size":return Integer.compare(this.size, card.size);
//		}
//		return result;
//	}
//
//	@Override
//	public int compareTo(Card o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
