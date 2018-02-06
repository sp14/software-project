package DBHandler;
import java.sql.*;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

public class PostgresSQL {

	private Connection connection = null;
	int currentGameNo=5;

	public PostgresSQL() {	//Constructor

	}

	public void sqlConnection() {	//Establish connection to the database

		/*//connection for home
		String databaseName = "postgres";
		String userName = "postgres";
		String password = "kats1kampee?";

		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName,
					userName, password);*/

			//connection for uni
					String databaseName = "m_17_2352834c";
					String userName = "m_17_2352834c";
					String password = "2352834c";
			
					try {
						connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + databaseName,
								userName, password);
		}

		catch (SQLException e) {

			System.err.println("Connection Failed");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("Connection successful");
		} else {
			System.err.println("Failed to make connection!");
		}
	}

	public void close() {	//Close the connection to the database
		try {
			connection.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection could not be closed � SQL exception");
		}
	}



	//So the program would need to insert values every step of the game into the SQL database. 
	//This begins when the game starts up it inserts a value which is one greater than the previous game number.
	//This would mean all methods would need parameters which are from the counters in the main Game class.
	//These would be currentGameNo to be the primary key in all the SQL tables
	//currentGameNo is one more than the previous so at the start of each game it would need to be incremented. 
	//We would need a variable to keep track of total number of rounds played in that game - totalRounds.
	//Need one to keep track of how many rounds each player has won. roundsWon.
	//Need a variable to keep track of the number of draws in the current game. totalDraws.
	//Need a variable of type STRING to keep track of the winner. "Human" / "AI1" / "AI2" / "AI3" / "AI4"



	//String winner needs to be - "Human" "AI1" "AI2" "AI3" "AI4"


	public int setCurrentGameNo() { // Sets current game number for the SQL table. There needs to be an instance
		// currentGameNo variable
		System.err.println(currentGameNo);
		sqlConnection();

		Statement stmt = null;
		String query = "SELECT * FROM toptrumps.game ORDER BY gameno DESC LIMIT 1;";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String gameno = rs.getString("gameno");
				
				currentGameNo = Integer.parseInt(gameno); // Getting the most recent entry game number in the table

				currentGameNo++; // Incrementing it by one

				System.out.println("The current game number is: " + currentGameNo);

				System.err.println(currentGameNo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		close();
		return currentGameNo;
	}



	public void insertIntoGameTable(int currentGameNo, int totalRounds, int totalDraws, String winner) {			
sqlConnection();
		Statement stmt = null;
		String query = "INSERT INTO toptrumps.game(gameno, totalrounds, totaldraws, winner) VALUES ('" + currentGameNo + "','" + totalRounds + "','" + totalDraws + "','" + winner + "') ;";
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
	}


	public void viewNoOfDraws(int currentGameNo) {	//Pass current game number 
		
		sqlConnection();
		Statement stmt = null;
		String query = "SELECT totaldraws FROM toptrumps.game WHERE gameno='" + currentGameNo + "' ;"; //REPLACE X WITH CURRENT GAME NUMBER

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String noOfDraws = rs.getString("totaldraws");
				System.out.println("The number of draws in this game was: " + noOfDraws);	//Game no?
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
		close();
	}



	public void viewWinner(int currentGameNo) {	//PASS CURRENT GAME NUMBER
		sqlConnection();
		Statement stmt = null;
		String query = "SELECT winner FROM toptrumps.game WHERE gameno='" + currentGameNo + "';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String viewWinner = rs.getString("winner");
				System.out.println("The winner was: " + viewWinner);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
		close();
	}

	public void viewTotalRounds(int currentGameNo) {	//Pass current game number
		sqlConnection();
		Statement stmt = null;
		String query = "SELECT totalrounds FROM toptrumps.game WHERE gameno='" + currentGameNo + "';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String viewTotalRounds = rs.getString("totalrounds");
				System.out.println("The total number of rounds played was: " + viewTotalRounds);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
		close();
	}

	public void humanRoundsWon(int currentGameNo) {	//pass current game number
		sqlConnection();
		Statement stmt = null;
		String query = "SELECT roundswon FROM toptrumps.human WHERE gameno='" + currentGameNo + "';";	//Cos its null atm

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String humanRoundsWon = rs.getString("roundswon");
				System.out.println("The number of rounds you won was: " + humanRoundsWon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
		close();
	}

	public void insertHumanTable(int currentGameNo, int roundsWon) {	//pass current game no and rounds human has won
		Statement stmt = null;
		String query = "INSERT INTO toptrumps.human (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')"; //Game no and rounds won
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

	}

	public void ai1RoundsWon(int currentGameNo) {	//Pass current game no
		Statement stmt = null;
		String query = "SELECT roundswon FROM toptrumps.ai1 WHERE gameno='" + currentGameNo + "';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String ai1RoundsWon = rs.getString("roundswon");
				System.out.println("AI1 won this many rounds: " + ai1RoundsWon);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
	}

	public void insertAI1Table(int currentGameNo, int roundsWon) {	//pass current game no and rounds AI1 has won (player 2)
		Statement stmt = null;
		String query = "INSERT INTO toptrumps.ai1 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')"; //Game no and rounds won
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

	}


	public void ai2RoundsWon(int currentGameNo) {	//Pass current game no
		Statement stmt = null;
		String query = "SELECT roundswon FROM toptrumps.ai2 WHERE gameno='" + currentGameNo + "';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String ai2RoundsWon = rs.getString("roundswon");
				System.out.println("AI2 won this many rounds: " + ai2RoundsWon);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
	}

	public void insertAI2Table(int currentGameNo, int roundsWon) {	//Pass current game no and how many rounds AI2 won (player 3)
		Statement stmt = null;
		String query = "INSERT INTO toptrumps.ai2 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')"; //Game no and rounds won
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

	}

	public void ai3RoundsWon(int currentGameNo) {	//pass current game no
		Statement stmt = null;
		String query = "SELECT roundswon FROM toptrumps.ai3 WHERE gameno='" + currentGameNo + "';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String ai3RoundsWon = rs.getString("roundswon");
				System.out.println("AI3 won this many rounds: " + ai3RoundsWon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
	}

	public void insertAI3Table(int currentGameNo, int roundsWon) {	//pass game no and ai3 rounds won (player 4)
		Statement stmt = null;
		String query = "INSERT INTO toptrumps.ai3 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')"; //Game no and rounds won
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

	}

	public void ai4RoundsWon(int currentGameNo) {	//pass current game no
		Statement stmt = null;
		String query = "SELECT roundswon FROM toptrumps.ai4 WHERE gameno='" + currentGameNo + "';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String ai4RoundsWon = rs.getString("roundswon");
				System.out.println("AI4 won this many rounds: " + ai4RoundsWon);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 
	}

	public void insertAI4Table(int currentGameNo, int roundsWon) { //pass current game no and how many ai4 rounds won (player 5)
		Statement stmt = null;
		String query = "INSERT INTO toptrumps.ai4 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')"; //Game no and rounds won
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

	}



	public void noOfGamesPlayed() { //Nothing passed
		Statement stmt = null;
		String query = "SELECT COUNT (gameno) FROM toptrumps.game;";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String noOfGamesPlayed = rs.getString("count");
				System.out.println("This many games have been played so far: " + noOfGamesPlayed);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 		
	}


	public void noOfAIWins() { //Nothing passed
		Statement stmt = null;
		String query = "SELECT COUNT (winner) FROM toptrumps.game WHERE winner = 'AI1' OR winner = 'AI2' OR winner = 'AI3' OR winner = 'AI4';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String noOfAIWins = rs.getString("count");
				System.out.println("The computer has won this many times: " + noOfAIWins);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 		
	}


	public void noOfHumanWins() {	//Nothing passed
		Statement stmt = null;
		String query = "SELECT COUNT (winner) FROM toptrumps.game WHERE winner = 'Human';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String noOfHumanWins = rs.getString("count");
				System.out.println("You have won this many times: " + noOfHumanWins);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 		

	}


	public void avgNoOfDraws() { //Nothing passed
		Statement stmt = null;
		String query = "SELECT AVG (totaldraws) FROM toptrumps.game;";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String avgDraws = rs.getString("avg");
				System.out.println("The average number of draws in a game is: " + avgDraws);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 		
	}

	public void maxNoOfRoundsPlayed() {	//Nothing passed
		Statement stmt = null;
		String query = "SELECT MAX (totalrounds) FROM toptrumps.game;";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String maxRounds = rs.getString("max");
				System.out.println("The maximum number of rounds played so far is: " + maxRounds);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 		

	}




}
