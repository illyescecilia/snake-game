package snakegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Cecília
 */
public class MenuGUI {
    
    private JFrame frame;
    private JPanel panel;

    /**
     * Constructs the main menu GUI.
     * Initializes the JFrame, sets up buttons for starting the game and viewing top scores.
     */
    public MenuGUI() {
        frame = new JFrame("Snake");
        frame.setSize(300, 75);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();

        JButton openWindowButton = new JButton("Start Game");
        JButton showPopupButton = new JButton("Top 10");
        
        openWindowButton.addActionListener(new OpenWindowButtonActionListener());
        showPopupButton.addActionListener(new ShowPopupButtonActionListener());

        panel.add(openWindowButton);
        panel.add(showPopupButton);
        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    class OpenWindowButtonActionListener implements ActionListener {
        
        public OpenWindowButtonActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new GameGUI();
        }
    }

    class ShowPopupButtonActionListener implements ActionListener {

        public ShowPopupButtonActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                HighScores highScores = new HighScores(GameEngine.TOP);
                ArrayList<HighScore> top = highScores.getHighScores();
                StringBuilder scoresMessage = new StringBuilder();
                for(int i = 0; i < Math.min(top.size(), GameEngine.TOP); i++) {
                    scoresMessage.append(String.format("%d. %s - Score: %d\n",
                        i + 1, top.get(i).getName(), top.get(i).getScore()));
                }
                JOptionPane.showMessageDialog(null, scoresMessage, "Top " + GameEngine.TOP, JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(MenuGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
}
