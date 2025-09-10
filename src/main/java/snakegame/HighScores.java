package snakegame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

/**
 *
 * @author Cecília
 */
public class HighScores {

    int maxScores;
    PreparedStatement insertStatement;
    Connection connection;

    /**
     * Constructs a HighScores object with the specified maximum number of high scores.
     * Initializes the database connection and the prepared statement for inserting high scores.
     * @param maxScores the maximum number of high scores to store.
     * @throws SQLException if a database access error occurs.
     */
    public HighScores(int maxScores) throws SQLException {
        this.maxScores = maxScores;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "j0bIK3b7472D");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/snakegame";
        connection = DriverManager.getConnection(dbURL, connectionProps);
        
        String insertQuery = "INSERT INTO SNAKE_HIGHSCORES (TIMESTAMP, NAME, SCORE) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
    }

    /**
     * Retrieves the list of high scores from the database.
     * @return an ArrayList of HighScore objects representing the high scores.
     * @throws SQLException if a database access error occurs.
     */
    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM SNAKE_HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }

    /**
     * Adds a new high score to the database.
     * @param name the name of the player.
     * @param score the player's score.
     * @throws SQLException if a database access error occurs.
     */
    public void putHighScore(String name, int score) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();
        insertScore(name, score);
    }

    /**
     * Sorts the given list of high scores in descending order based on scores.
     * @param highScores the list of high scores to sort.
     */
    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }

    /**
     * Inserts a new high score into the database.
     * @param name  the name of the player.
     * @param score the player's score.
     * @throws SQLException if a database access error occurs.
     */
    private void insertScore(String name, int score) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        insertStatement.setTimestamp(1, ts);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, score);
        insertStatement.executeUpdate();
    }
    
}
