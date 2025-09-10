package snakegame;

/**
 *
 * @author Cecília
 */
public class Position {
    
    private int x;
    private int y;
    
    /**
     * Constructs a Position object with specified x and y coordinates.
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of this position.
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this position.
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of this position.
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this position.
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
    
}
