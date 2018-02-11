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

import game.*;
import online.configuration.TopTrumpsJSONConfiguration;
import DBHandler.*;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    int numAIPlayers = 0;
    Player currentPlayer;
    ArrayList<Player> players;
    ArrayList<Player> changePlayers;
//    PostgresSQL db = new PostgresSQL();

    String bestAttribute;
    String userSelectedCate;
    String userSelectAttribute;


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


       
//        game = new Game(false,4);
//        game.drawCards();
//
//        currentPlayer = game.getCurrentPlayer();
//        players = game.getPlayers();
        numAIPlayers = 4;

//        game = new Game();

    }






    // ----------------------------------------------------
    // Add relevant API methods here
    // ----------------------------------------------------
    @GET
    @Path("/startGame")
    public String startGame() throws IOException{
        //Start the game logic

       game = new Game();
        
        //=============
        //  DB PART
        //=============
        
//        game.setGameID(db.setCurrentGameNo());
//        gameNum = game.getGameID();
//
//        System.out.println("the game id is " + gameNum);


        //round1
        game.initGame(false,numAIPlayers);
        game.drawCards();

        currentPlayer = game.getCurrentPlayer();
        players = game.getPlayers();


        //basic information include gameNumber(gameID) and number of AI players
        int[] basicInfo = new int[2];
        basicInfo[0]=gameNum;
        basicInfo[1]= numAIPlayers;

        //--------------------------------------------------
        // record game number here, connect to database later
        //--------------------------------------------------
        String stringAsJSONString = oWriter.writeValueAsString(basicInfo);

        return stringAsJSONString;
    }



    //draw Card
    @GET
    @Path("/drawCard")
    public String drawCard() throws IOException{

        game.drawCards();

        currentPlayer = game.getCurrentPlayer();
        players = game.getPlayers();

        return "";
    }

    //displayUserCard
    @GET
    @Path("/displayUserCard")
    public String displayUserCard() throws IOException{

        players = game.getStartingPlayers();
        //get user card
        Card card = players.get(0).getCurrentCard();
        //get user card name
        String cardName = card.getName();
        //change to JSONString
        String cardNameAsJSONString = oWriter.writeValueAsString(cardName);
        return cardNameAsJSONString;
    }


    //displayAICard
    @GET
    @Path("/displayAICard")
    public String displayAICard() throws IOException{
        players = game.getStartingPlayers();

        String[] AICardName = new String[players.size()-1];
//        String[] AICardName = {players.get(1).getCurrentCard().getName(),players.get(2).getCurrentCard().getName() };

        for (int i = 1; i < players.size() ; i++) {
            AICardName[(i-1)]= players.get(i).getCurrentCard().getName();
        }

        String AICardNameAsJSONString = oWriter.writeValueAsString(AICardName);

        return AICardNameAsJSONString;
    }


    //getUserTopCardCategories
    @GET
    @Path("/getUserTopCardCategories")
    public String getUserTopCardCategories() throws IOException{
        //get user card
        players = game.getStartingPlayers();
        Card card = players.get(0).getCurrentCard();

        //Categories order:  Size Speed Range Firepower Cargo
        //build the category array
        String[] categoryArray = {"Size: "+card.getSize(),"Speed: "+card.getSpeed(), "Range: " + card.getRange(),"Firepower: " + card.getFirepower(),"Cargo: " + card.getCargo()};
        // change to JSONString
        String categoryArrayAsJSONString = oWriter.writeValueAsString(categoryArray);
        return categoryArrayAsJSONString;
    }


    @GET
    @Path("/getAITopCardCategories")
    public String getAITopCardCategories() throws IOException{
        ArrayList<Player> players = game.getStartingPlayers();

        Card card ;

        String[][] categoryArray= new String[players.size()-1][5];

//        categoryArray[0] = {"Size: "+card.getSize(),"Speed: "+card.getSpeed(), "Range: " + card.getRange(),"Firepower: " + card.getFirepower(),"Cargo: " + card.getCargo()};

//        String[][] categoryArray ={};
        for (int i = 0; i < players.size()-1; i++) {
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

        currentPlayer = game.getCurrentPlayer();

        //0.currentPlayerName 1.currentRound 2.communalPileCount 3.drawCount
        String currentPlayerName = currentPlayer.getName();
        int communalPile = game.getCommunalPile().getCardCount();
        int currentRound = game.getRoundCounter()+1;
        int drawCount = game.getDrawCounter();

        //build current info array
        Object[] currentInfo = {currentPlayerName,currentRound,communalPile,drawCount};
        String currentPlayerNameAsJSONString = oWriter.writeValueAsString(currentInfo);

        return currentPlayerNameAsJSONString;

    }


    //AISelectCategory
    @GET
    @Path("/AISelectCategory")
    public String AISelectCategory() throws IOException{

        currentPlayer = game.getCurrentPlayer();
        System.out.println("AI ROUND: current player is :" + currentPlayer );
        String currentPlayerBestAttribute = currentPlayer.getBestAttribute();
        System.out.println("AI ROUND: current BEST ATTRIBUTE is :" + currentPlayerBestAttribute );

        String currentPlayerBestAttributeAsJSONString = oWriter.writeValueAsString(currentPlayerBestAttribute);
        return currentPlayerBestAttributeAsJSONString;
    }




    @GET
    @Path("/showWinner")
    public String showWinner() throws IOException{

        currentPlayer = game.getCurrentPlayer();
        players = game.getStartingPlayers();

        Player winner;
        // when the current player is user
        if (currentPlayer.getName().equals("You") ){

            System.out.println("current player  is you?????"+currentPlayer.getName()+ "!!!! tHE CATEGORY: " + userSelectAttribute);
            winner = game.playRound(userSelectAttribute);
            return displayWinner(winner);


        }else {
            //when the current player is AI
            bestAttribute = currentPlayer.getBestAttribute();

            System.out.println("current player  is AI?????"+currentPlayer.getName()+ "!!!! tHE CATEGORY: " + bestAttribute);
            winner = game.playRound(bestAttribute);

            return displayWinner(winner);
        }
    }

    @GET
    @Path("/getPlayersLeft")
    public String getPlayersLeft() throws IOException{
        changePlayers = game.getPlayers();
        int playerNumber = changePlayers.size();
        String pnAsJSONString = oWriter.writeValueAsString(playerNumber);



        return pnAsJSONString;
    }




    public String displayWinner(Player winner) throws IOException{


        // when the winner == null, it's a draw
        if (winner == null) {
            String draw = "draw";
            String drawASJSONString = oWriter.writeValueAsString(draw);

            return drawASJSONString;
        }else {
            // has a true winner
            String winnerName = winner.getName();

            String winnerNameASJSONString = oWriter.writeValueAsString(winnerName);
            return winnerNameASJSONString;

        }
    }



    @GET
    @Path("/nextRound")
    public String nextRound() throws IOException{

        //Get a list of all eliminated players and display in the console
//        ArrayList<Player> eliminated = game.clearPlayers();

//        String returnMessage;
//
//        // get eliminated players
//        if (!(eliminated == null)) {
//            for (int i = 0; i < eliminated.size(); i++) {
//
//                Player elimPlayer = eliminated.get(i);
//
//                if (elimPlayer.isAI()) {
//                    returnMessage = elimPlayer + " has been eliminated" ;
//                    String asJSONString = oWriter.writeValueAsString(returnMessage);
//                    return asJSONString;
//                }
//                else {
//
//                    returnMessage = "You have been eliminated";
//
//                    String asJSONString = oWriter.writeValueAsString(returnMessage);
//
//                    return asJSONString;
//                }
//            }
//        }


        game.clearPlayers();
        // draw the cards
        game.drawCards();

        return " ";
    }


    @GET
    @Path("/transferCategory")
    public String transferCategory(@QueryParam("userSelectedCate") String userSelectedCate) throws IOException{

        String cate = "";
        //get the user selected category
        switch (userSelectedCate){
            case "P1Cat1" :
                cate = "size";
                break;
            case "P1Cat2" :
                cate = "speed";
                break;
            case "P1Cat3" :
                cate = "range";
                break;
            case "P1Cat4" :
                cate = "firepower";
                break;
            case "P1Cat5" :
                cate = "cargo";
                break;

        }
        userSelectAttribute = cate;

        return userSelectAttribute;
    }


    @GET
    @Path("/getLeftCards")
    public String getLeftCards() throws IOException{

        // get all the players
        players =game.getStartingPlayers();

        //build the left cards Array
        int[] leftCards =  new int[players.size()];
        for (int i = 0; i < players.size(); i++){
            leftCards[i] = players.get(i).getRemainingCards();
        }


        String leftCardsAsJSONString = oWriter.writeValueAsString(leftCards);
        return leftCardsAsJSONString;
    }


    @GET
    @Path("/playerLeft")
    public String playerLeft() throws IOException{


        ArrayList<Player> leftPlayersArray =game.getPlayers();
        int playerLeft = leftPlayersArray.size();
        System.out.println("player left is" + leftPlayersArray.toString());
        String[] playerLeftArray = new String[leftPlayersArray.size()];

        for (int i = 0; i < leftPlayersArray.size() ; i++) {
            playerLeftArray[i]= leftPlayersArray.get(i).getName();

        }

        String playerLeftAsJSONStirng = oWriter.writeValueAsString(playerLeftArray);

        return playerLeftAsJSONStirng;
    }

    //getStatisticsForTheUser
    @GET
    @Path("/getStatisticsForTheUser")
    public String getStatisticsForTheUser() throws JsonProcessingException {
        String a = PostgresSQL.noOfGamesPlayed();
        String b = PostgresSQL.noOfAIWins();
        String c = PostgresSQL.noOfHumanWins();
        String d = PostgresSQL.avgNoOfDraws();
        String e = PostgresSQL.maxNoOfRoundsPlayed();
        String out = a + " " + b + " " + c + " " + d + " " + e;

        return out;
    }



    //====================
    //  Database Part
    //====================
//    @GET
//    @Path("/updateDatabase")
//    public String updateDatabase() throws IOException{
//
//    	//The game is over. Update Database
//		// Update game table
//		db.insertIntoGameTable(game.getGameID(), game.getRoundCounter(), game.getDrawCounter(), game.getWinner().getName());
//
//		// Update players' tables
//		for (int i=0 ; i < game.getStartingPlayers().size(); i ++) {
//			db.insertPlayersTables(game.getGameID(), game.getStartingPlayers().get(i).getWinCounter(), game.getStartingPlayers().get(i).getName() );
//		}
//
//
//    	return "";
//    }
    
    
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


        System.out.println("the word in rest is"+Word);
        return "Hello "+Word;
    }

}
