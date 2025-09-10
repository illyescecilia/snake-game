package snakegame;

/**
 *
 * @author Cecília
 */
public class HighScore {
    
    private final String name;
    private final int score;

    /**
     * Constructs a HighScore object with the given name and score.
     * @param name name  the name of the player.
     * @param score score the player's score.
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the name of the player associated with this high score.
     * @return the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the score associated with this high score.
     * @return the player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns a string representation of the high score in the format:
     * "HighScore{name='name', score=score}".
     * @return a string representing the high score.
     */
    @Override
    public String toString() {
        return "HighScore{" + "name=" + name + ", score=" + score + '}';
    }
    
}
