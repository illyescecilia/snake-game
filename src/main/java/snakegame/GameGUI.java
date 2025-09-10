package snakegame;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Cecília
 */
public class GameGUI extends JFrame {
    
    private JPanel panel;
    
    /**
     * Constructs the Game GUI and initializes the game window.
     * Sets the title, dimensions, and default behavior for the game window.
     */
    public GameGUI() {
        setTitle("Snake");
        setSize(GameEngine.SCREEN_WIDTH, GameEngine.SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        panel = new GameEngine();
        getContentPane().add(panel);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
