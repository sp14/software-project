package online.dwResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import game.Card;
import game.Deck;
import game.Game;
import game.Player;
import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {



	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();


	//
    Game game;
    int gameNum;
    int numAIPlayers;
    Player currentPlayer;
    ArrayList<Player> players;
	
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------
        gameNum = 0;
        numAIPlayers = conf.getNumAIPlayers();


		game = new Game(false,4);





	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	@GET
	@Path("/startGame")
	public String startGame() throws IOException{
        //Start the game logic

        //round1
        game.drawCards();
        players = game.getPlayers();
        currentPlayer = game.getCurrentPlayer();

        Card card = players.get(0).getCurrentCard();
        Card card1 = players.get(4).getCurrentCard();
        String cardName = card.getName();
        System.out.println("player1's card name:"+cardName);
        System.out.println("round number is "+ game.getRoundCounter()+1);
        String cardName2 = card1.getName();
        System.out.println(cardName2);

        int size = card.getSize();
        int cargo = card.getCargo();
        int speed = card.getSpeed();
        int range = card.getRange();
//        System.out.println("player1 current card size: " + size);
//        System.out.println("player1 current card cargo: "+ cargo);
//        System.out.println("player1 current card speed: "+ speed);
//        System.out.println("player1 current card range: "+ range);




        String attribute1 = currentPlayer.getBestAttribute();

        // compare
        Player winner = game.playRound(attribute1);
//        show the winner
        System.out.println("  winner name: "+winner.getName());

        //--------------------------------------------------
        // record game number here, connect to database later
        //--------------------------------------------------

        int gameNum = 0;
        String stringAsJSONString = oWriter.writeValueAsString(gameNum);

		return stringAsJSONString;
	}


	//displayUserCard
    @GET
    @Path("/displayUserCard")
    public String displayUserCard() throws IOException{

        Card card = players.get(0).getCurrentCard();
        String cardName = card.getName();

        String cardNameAsJSONString = oWriter.writeValueAsString(cardName);
	    return cardNameAsJSONString;
    }



    //getUserTopCardCategories
    @GET
    @Path("/getUserTopCardCategories")
    public String getUserTopCardCategories() throws IOException{
        ArrayList<Player> players = game.getPlayers();

        Card card = players.get(0).getCurrentCard();
//	    Size Speed Range Firepower Cargo
//        int size = card.getSize();
//        int cargo = card.getCargo();
//        int speed = card.getSpeed();
//        int range = card.getRange();
        String[] categoryArray = {"Size: "+card.getSize(),"Speed: "+card.getSpeed(), "Range: " + card.getRange(),"Firepower: " + card.getFirepower(),"Cargo: " + card.getCargo()};
        String categoryArrayAsJSONString = oWriter.writeValueAsString(categoryArray);

	    return categoryArrayAsJSONString;
    }



    //getCurrentInfo : currentPlayer , currentRound
    @GET
    @Path("/getCurrentInfo")
    public String getCurrentInfo() throws IOException{

	    String currentPlayerName = currentPlayer.getName();
        System.out.println("current player from method :" + currentPlayerName);

        int currentRound = game.getRoundCounter()  ;

        Object[] currentInfo = {currentPlayerName,currentRound};

        String currentPlayerNameAsJSONString = oWriter.writeValueAsString(currentInfo);

        return currentPlayerNameAsJSONString;

    }

	//get p1 card name
    @GET
    @Path("/getP1CardName")
    public String getP1CardName() throws IOException{

        ArrayList<Player> players = game.getPlayers();


        String p1CardName = String.valueOf(players.get(0).getCurrentCard());
        String p1NameAsJSONString = oWriter.writeValueAsString(p1CardName);
	    return "Avenger";
    }
	
	@GET
	@Path("/helloJSONList")
	/**
	 * Here is an example of a simple REST get request that returns a String.
	 * We also illustrate here how we can convert Java objects to JSON strings.
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String helloJSONList() throws IOException {
		
		List<String> listOfWords = new ArrayList<String>();
		listOfWords.add("Hello~~~~");
//		listOfWords.add("!");

		// We can turn arbatory Java objects directly into JSON strings using
		// Jackson seralization, assuming that the Java objects are not too complex.
		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
		
		return "test~~~~";
	}
	
	@GET
	@Path("/helloWord")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String helloWord(@QueryParam("Word") String Word) throws IOException {
		return "Hello "+Word;
	}
	
}
