package game.view;

import java.awt.*;

public class MainMenu {

    private final int width;
    public Rectangle playButton;
    public Rectangle loadButton;
    public Rectangle initialMapButton;
    public Rectangle quitButton;

    // CONSTRUCTOR
    public MainMenu(GamePanel gamePanel) {
        // ATTRIBUTES
        this.width = gamePanel.getScreenWidth();
        playButton = new Rectangle(width / 4 + 120, 150, 100, 50);
        loadButton = new Rectangle(width / 4 + 120, 250, 100, 50);
        initialMapButton = new Rectangle(width / 4 + 120, 350, 100, 50);
        quitButton = new Rectangle(width / 4 + 120, 450, 100, 50);
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font font0 = new Font("arial", Font.BOLD, 50);
        g.setFont(font0);
        g.setColor(Color.WHITE);
        g.drawString("World Building", width/4, 100);

        Font font1 = new Font("arial", Font.BOLD, 30);
        g.setFont(font1);
        g.drawString("Play", playButton.x + 19, playButton.y + 35);
        g2d.draw(playButton);
        g.drawString("Load", loadButton.x + 19, loadButton.y + 35);
        g2d.draw(loadButton);
        g.drawString("Default", initialMapButton.x + 19, initialMapButton.y + 35);
        g2d.draw(initialMapButton);
        g.drawString("Quit", quitButton.x + 19, quitButton.y + 35);
        g2d.draw(quitButton);
    }
}
