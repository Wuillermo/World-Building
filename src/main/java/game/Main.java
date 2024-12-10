package game;

import game.model.core.GameManager;
import game.view.GamePanel;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        // Set up the window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("World Building");

        //Create the Game Manager
        GameManager gameManager = new GameManager();

        // Add the Game Panel
        GamePanel gamePanel = gameManager.getGamePanel();
        window.add(gamePanel);

        // Causes this window to be size to fit the preferred size of its subcomponents
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
