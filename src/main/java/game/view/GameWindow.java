package game.view;

import game.model.core.GameManager;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame{

    public GameWindow(GameManager gameManager) {
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("World Building");

        // Add a window listener to handle cleanup on close
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing application...");
                gameManager.stopRunning();
            }
        });
    }

    // Configures the size and shows the window on the screen
    public void showOnScreen() {
        // Causes this window to be size to fit the preferred size of its subcomponents
        this.pack();

        // The window is placed in the center of the screen
        this.setLocationRelativeTo(null);

        // Shows the window
        this.setVisible(true);
    }
}
