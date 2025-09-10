package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Cecília
 */
public class GameEngine extends JPanel {
    
    /**
     * Width of the game window.
     */
    protected static final int SCREEN_WIDTH = 800;

    /**
     * Height of the game window.
     */
    protected static final int SCREEN_HEIGHT = 800;

    /**
     * Size of each cell in the grid.
     */
    protected static final int CELL_SIZE = 50;

    /**
     * Total number of tiles.
     */
    protected static final int TILES = (SCREEN_WIDTH*SCREEN_HEIGHT)/(CELL_SIZE*CELL_SIZE);

    /**
     * Snake speed in milliseconds.
     */
    protected static final int SPEED = 200;

    /**
     * Number of stones placed on the map.
     */
    protected static final int STONE_AMOUNT = 5;

    /**
     * Maximum number of top scores to keep.
     */
    protected static final int TOP = 10;
    
    /**
     * Random number generator.
     */
    protected static Random random = new Random();

    /**
     * Timer to control the game loop.
     */
    protected static Timer timer;

    /**
     * Current direction of the snake.
     */
    protected static int direction;

    /**
     * Current score of the player.
     */
    protected static int score;
    
    /**
     * Indicates if the game is currently running.
     */
    protected static boolean running = false;

    /**
     * Indicates if the player's score should be saved.
     */
    protected static boolean savingScore = false;
    
    /**
     * The snake object representing the player.
     */
    protected static Snake snake;

    /**
     * The apple object to be collected.
     */
    protected static Apple apple;

    /**
     * The list of stone obstacles.
     */
    protected static ArrayList<Stone> stones;
    
    private long startTime;
    private long elapsedTime;
    
    /**
     * Constructs the GameEngine and initializes the game settings.
     * Sets up the game window, key listeners, and starts the game.
     */
    public GameEngine(){    
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(new Color(246, 230, 150));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }
    
    /**
     * Initializes and starts the game.
     * Creates the snake, places obstacles and the apple, and starts the game loop.
     */
    public void startGame() {
        snake = new Snake();
        placeStones();
        apple = new Apple();
        
        running = true;
        savingScore = false;
        score = 0;
        
        timer = new Timer(SPEED, new NewFrameListener());
        timer.start();
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Places the stone obstacles on the game board.
     */
    public void placeStones() {
        stones = new ArrayList<>();
        
        for (int i = 0; i < STONE_AMOUNT; i++) {
            stones.add(new Stone());
        }
    }
    
    /**
     * Paints the game components such as the snake, apple, and stones.
     * This method is called by the Swing framework to update the display.
     * @param g the Graphics object used for drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    /**
     * Draws the game elements including the grid, snake, apple, and stones.
     * @param g the Graphics object used for rendering.
     */
    public void draw(Graphics g) {
        if (running) {
            // grid
            for(int i=0;i<SCREEN_HEIGHT/CELL_SIZE;i++) {
                g.setColor(new Color(189, 174, 119));
                g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*CELL_SIZE, SCREEN_WIDTH, i*CELL_SIZE);
            }
            
            // apple
            g.setColor(new Color(218, 73, 72));
            g.fillOval(apple.getX(), apple.getY(), CELL_SIZE, CELL_SIZE);

            // stones
            for(int i = 0; i < STONE_AMOUNT; i++) {
                g.setColor(new Color(155, 150, 130));
                g.fillRect(stones.get(i).getX(), stones.get(i).getY(), CELL_SIZE, CELL_SIZE);
            }
            
            // snake
            for(int i = 0; i < snake.getSize(); i++) {
                g.setColor(new Color(112, 172, 111));
                g.fillRect(snake.getPart(i).getX(), snake.getPart(i).getY(), CELL_SIZE, CELL_SIZE);
            }
            
            // score and timer
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = g.getFontMetrics();
            g.drawString("Score: " + score, 10, 30);
            
            elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            g.drawString("Time: " + elapsedTime + "s", SCREEN_WIDTH - 110, 30);
        }
        else if (savingScore) {
            gameOver();
            savingScore = false;
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
            new MenuGUI();
        }
    }
    
    /**
     * Handles the game over scenario where the player inputs their name for the high score.
     */
    public void gameOver() {
        String playerName = JOptionPane.showInputDialog(this, "Enter your name:");
        
        if (playerName == null || playerName.trim().isEmpty()) {
            return;
        }
        
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        
        try {
            HighScores highScores = new HighScores(TOP);
            highScores.putHighScore(playerName, score);
        } catch (Exception ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (running) {
            snake.checkApple();
            snake.checkCollisions();
            }
            repaint();
        }

    }

    /**
     * Inner class for handling key input.
     */
    public class MyKeyAdapter extends KeyAdapter {

        /**
         * Handle direction changes based on arrow keys (W, A, S, D).
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            // 0: W, 1: A, 2: S, 3: D
            switch(e.getKeyCode()) {
                case KeyEvent.VK_W: // ↑
                    if(direction != 2) {
                        direction = 0;
                    }
                break;
                case KeyEvent.VK_A: // ←
                    if(direction != 3) {
                        direction = 1;
                    }
                break;
                case KeyEvent.VK_S: // ↓
                    if(direction != 0) {
                        direction = 2;
                    }
                break;
                case KeyEvent.VK_D: // →
                    if(direction != 1) {
                        direction = 3;
                    }
                break;
            }
        }

    }
    
}
