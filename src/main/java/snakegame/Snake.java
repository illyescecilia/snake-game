package snakegame;

import java.util.ArrayList;

/**
 *
 * @author Cecília
 */
public class Snake {
    
    private ArrayList<Position> snake;

    /**
     * Constructs a Snake object and initializes its starting position.
     * The snake starts at the center of the game grid, with a random direction.
     * It grows by one segment after initialization.
     */
    public Snake() {
        snake = new ArrayList<>();
        GameEngine.direction = GameEngine.random.nextInt(4);
        
        snake.add(new Position((GameEngine.SCREEN_WIDTH / 2) / GameEngine.CELL_SIZE * GameEngine.CELL_SIZE,
                               (GameEngine.SCREEN_HEIGHT / 2) / GameEngine.CELL_SIZE * GameEngine.CELL_SIZE));
        grow();
    }

    /**
     * Gets the position of a specific segment of the snake.
     * @param index the index of the segment.
     * @return the Position of the specified segment.
     */
    public Position getPart(int index) {
        return snake.get(index);
    }
    
    /**
     * Gets the size of the snake (number of segments).
     * @return the size of the snake.
     */
    public int getSize() {
        return snake.size();
    }
    
    /**
     * Moves the snake in the current direction, shifting all segments appropriately.
     * The movement is based on the direction stored in the GameEngine.direction.
     */
    public void move(){
        for(int i = snake.size() - 1; i > 0; i--) {
            snake.set(i, new Position(snake.get(i - 1).getX(),
                                      snake.get(i - 1).getY()));
        }

        switch(GameEngine.direction) {
            case 0:
                snake.get(0).setY(snake.get(0).getY() - GameEngine.CELL_SIZE);
                break;
            case 1:
                snake.get(0).setX(snake.get(0).getX() - GameEngine.CELL_SIZE);
                break;
            case 2:
                snake.get(0).setY(snake.get(0).getY() + GameEngine.CELL_SIZE);
                break;
            case 3:
                snake.get(0).setX(snake.get(0).getX() + GameEngine.CELL_SIZE);
                break;
        }
    }
    
    /**
     * Checks for collisions involving the snake.
     * - Collisions with the snake's own body stop the game.
     * - Collisions with stones stop the game.
     * - Moving out of bounds stops the game.
     * If a collision occurs, the game stops and enters the score-saving mode.
     */
    public void checkCollisions() {
        Position snakeHead = snake.get(0);
        
        // collides with body?
        for(int i = 1; i < snake.size(); i++) {
            if (snakeHead.getX() == snake.get(i).getX() &&
                snakeHead.getY() == snake.get(i).getY()) {
                GameEngine.running = false;
            }
        }
        
        // collides with stone?
        for(int i = 0; i < GameEngine.stones.size(); i++) {
            if (snakeHead.getX() == GameEngine.stones.get(i).getX() &&
                snakeHead.getY() == GameEngine.stones.get(i).getY()) {
                GameEngine.running = false;
            }
        }
        
        // out of bound?
        if (snakeHead.getY() < 0 || snakeHead.getX() < 0 ||
            snakeHead.getY() >= GameEngine.SCREEN_HEIGHT ||
            snakeHead.getX() >= GameEngine.SCREEN_WIDTH) {
            GameEngine.running = false;
        }
        
        if(!GameEngine.running) {
            GameEngine.timer.stop();
            GameEngine.savingScore = true;
        }
    }
    
    /**
     * Checks if the snake's head is on the apple.
     * If the snake eats the apple:
     * - The snake grows.
     * - The score is increased.
     * - A new apple is generated.
     * Otherwise, the snake moves forward.
     */
    public void checkApple() {
        if (snake.get(0).getX() == GameEngine.apple.getX() &&
            snake.get(0).getY() == GameEngine.apple.getY()) {
            grow();
            GameEngine.score++;
            GameEngine.apple = new Apple();
        } else {
            move();
        }
    }

    /**
     * Grows the snake by adding a new segment to its tail.
     * The new segment is initialized at the position of the previous tail segment.
     * The entire snake shifts forward after growth.
     */
    public void grow() {
        snake.add(snake.size(), new Position(snake.get(snake.size() - 1).getX(),
                                             snake.get(snake.size() - 1).getY()));
        for(int i = snake.size() - 1; i > 0; i--) {
                snake.set(i, new Position(snake.get(i - 1).getX(),
                                          snake.get(i - 1).getY()));
        }

        switch(GameEngine.direction) {
            case 0:
                snake.get(0).setY(snake.get(0).getY() - GameEngine.CELL_SIZE);
                break;
            case 1:
                snake.get(0).setX(snake.get(0).getX() - GameEngine.CELL_SIZE);
                break;
            case 2:
                snake.get(0).setY(snake.get(0).getY() + GameEngine.CELL_SIZE);
                break;
            case 3:
                snake.get(0).setX(snake.get(0).getX() + GameEngine.CELL_SIZE);
                break;
        }
    }
}
