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

	public String getBestAttribute() {

		int[] attributes = {size, speed, range, firepower, cargo};

		int max = 0;
		int maxIndex = 0;

		for (int i = 0; i < attributes.length; i++) {

			if (attributes[i] > max) {

				max = attributes[i];
				maxIndex = i;
			}
		}

		switch (maxIndex) {

		case 0: return "size";
		case 1: return "speed";
		case 2: return "range";
		case 3: return "firepower";
		case 4: return "cargo";
		default: return "";
		}
	}
}
