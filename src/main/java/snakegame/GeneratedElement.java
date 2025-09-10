package snakegame;

/**
 *
 * @author Cecília
 */
public abstract class GeneratedElement {
    
    /**
     * The x-coordinate of the generated element.
     */
    protected int x;

    /**
     * The y-coordinate of the generated element.
     */
    protected int y;

    /**
     * Constructs a GeneratedElement object with randomized x and y coordinates.
     * The coordinates are generated within the bounds of the game grid and ensure 
     * that the element does not overlap with the snake or stones.
     */
    public GeneratedElement() {
        do {
            this.x = GameEngine.random.nextInt((int)(
                GameEngine.SCREEN_WIDTH / GameEngine.CELL_SIZE)) * GameEngine.CELL_SIZE;
            this.y = GameEngine.random.nextInt((int)(
                GameEngine.SCREEN_HEIGHT / GameEngine.CELL_SIZE)) * GameEngine.CELL_SIZE;
        } while (isOnSnake() || isOnStone());
    }

    /**
     * Gets the x-coordinate of this generated element.
     * @return the x-coordinate of the element.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this generated element.
     * @return the y-coordinate of the element.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if this generated element overlaps with any part of the snake.
     * @return true if the element is on the snake; false otherwise.
     */
    public boolean isOnSnake() {
        for (int i = 0; i < GameEngine.snake.getSize(); i++) {
            if (x == GameEngine.snake.getPart(i).getX() &&
                y == GameEngine.snake.getPart(i).getY()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if this generated element overlaps with any stone on the grid.
     * @return true if the element is on a stone; false otherwise.
     */
    public boolean isOnStone() {
        for (int i = 0; i < GameEngine.stones.size(); i++) {
            if (x == GameEngine.stones.get(i).getX() &&
                y == GameEngine.stones.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

}
