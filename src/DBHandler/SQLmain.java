package DBHandler;
import java.sql.*;


public class SQLmain {

	public static void main(String[] args) {
		
		PostgresSQL sql = new PostgresSQL();
		sql.sqlConnection();
		//sql.insertIntoGameTable(7,18,4,"AI4");	//Test for game 7
		sql.viewNoOfDraws(7);
		sql.viewWinner(7);
		sql.viewTotalRounds(7);
		sql.insertHumanTable(7, 5);
		sql.humanRoundsWon(7);
		sql.insertAI1Table(7, 3);
		sql.ai1RoundsWon(7);
		sql.ai2RoundsWon(7);
		sql.ai3RoundsWon(7);
		sql.ai4RoundsWon(7);
		sql.noOfGamesPlayed();
		sql.noOfAIWins();
		sql.noOfHumanWins();
		sql.avgNoOfDraws();
		sql.maxNoOfRoundsPlayed();
	}

}
