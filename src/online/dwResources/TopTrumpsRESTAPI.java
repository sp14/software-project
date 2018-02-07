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
    String bestAttribute;


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

        game = new Game(false,4);
        game.drawCards();

        currentPlayer = game.getCurrentPlayer();
        players = game.getPlayers();

    }






    // ----------------------------------------------------
    // Add relevant API methods here
    // ----------------------------------------------------
    @GET
    @Path("/startGame")
    public String startGame() throws IOException{
        //Start the game logic

        //round1
//        game = new Game(false,4);






        //--------------------------------------------------
        // record game number here, connect to database later
        //--------------------------------------------------

        int gameNum = 0;
        String stringAsJSONString = oWriter.writeValueAsString(gameNum);

        return stringAsJSONString;
    }



    //draw Card
    @GET
    @Path("/drawCard")
    public String drawCard() throws IOException{
        game.drawCards();
        return "";
    }

    //displayUserCard
    @GET
    @Path("/displayUserCard")
    public String displayUserCard() throws IOException{

        players = game.getPlayers();

        Card card = players.get(0).getCurrentCard();
        String cardName = card.getName();
        System.out.println("P1 CARD NAME IS " + cardName);

        String cardNameAsJSONString = oWriter.writeValueAsString(cardName);
        return cardNameAsJSONString;
    }


    //displayAICard
    @GET
    @Path("/displayAICard")
    public String displayAICard() throws IOException{
        String[] AICardName = new String[4];
//        String[] AICardName = {players.get(1).getCurrentCard().getName(),players.get(2).getCurrentCard().getName() };

        for (int i = 1; i <5 ; i++) {
            AICardName[(i-1)]= players.get(i).getCurrentCard().getName();
        }

        String AICardNameAsJSONString = oWriter.writeValueAsString(AICardName);

        return AICardNameAsJSONString;
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


    @GET
    @Path("/getAITopCardCategories")
    public String getAITopCardCategories() throws IOException{
        ArrayList<Player> players = game.getPlayers();

        Card card ;

        String[][] categoryArray= new String[4][5];

//        categoryArray[0] = {"Size: "+card.getSize(),"Speed: "+card.getSpeed(), "Range: " + card.getRange(),"Firepower: " + card.getFirepower(),"Cargo: " + card.getCargo()};

//        String[][] categoryArray ={};
        for (int i = 0; i < 4; i++) {
            card = players.get(i+1).getCurrentCard();
            categoryArray[i][0] = ("Size: "+card.getSize());
            categoryArray[i][1] = ("Speed: "+card.getSpeed());
            categoryArray[i][2] = ("Range: " + card.getRange());
            categoryArray[i][3] = ("Firepower: " + card.getFirepower());
            categoryArray[i][4] = ("Cargo: " + card.getCargo());

        }



        String categoryArrayAsJSONString = oWriter.writeValueAsString(categoryArray);
        return categoryArrayAsJSONString;
    }



    //getCurrentInfo : currentPlayer , currentRound
    @GET
    @Path("/getCurrentInfo")
    public String getCurrentInfo() throws IOException{

        Player currentPlayer = game.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();
        System.out.println("current player from method :" + currentPlayerName);

        int currentRound = game.getRoundCounter()  ;
        System.out.println("current round from method is " +currentRound);

        Object[] currentInfo = {currentPlayerName,currentRound};

        String currentPlayerNameAsJSONString = oWriter.writeValueAsString(currentInfo);

        return currentPlayerNameAsJSONString;

    }


    //AISelectCategory
    @GET
    @Path("/AISelectCategory")
    public String AISelectCategory() throws IOException{

        String currentPlayerBestAttribute = currentPlayer.getBestAttribute();

        String currentPlayerBestAttributeAsJSONString = oWriter.writeValueAsString(currentPlayerBestAttribute);
        return currentPlayerBestAttributeAsJSONString;
    }



    @GET
    @Path("/showWinner")
    public String showWinner() throws IOException{


        bestAttribute = currentPlayer.getBestAttribute();

        System.out.println("best attribute from method" + bestAttribute);


        // compare
        Player winner = game.playRound(bestAttribute);
//        show the winner



        if (winner == null) {

            System.out.println("The round was a draw. Cards added to the communal pile.");

            String draw = "draw";
            String drawASJSONString = oWriter.writeValueAsString(draw);

            return drawASJSONString;
        } else {

            String winnerName = winner.getName();

            String winnerNameASJSONString = oWriter.writeValueAsString(winnerName);
            return winnerNameASJSONString;

        }



    }


    @GET
    @Path("/nextRound")
    public String nextRound() throws IOException{
        game.clearPlayers();

        game.drawCards();

        return " ";
    }








    // =============
    //   example
    // ==============
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
