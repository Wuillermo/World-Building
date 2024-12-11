package game.view;

import javax.swing.JPanel;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private final int width;
    private final String title;
    private final String[] buttonsText;
    public final Rectangle playButton;
    public final Rectangle loadButton;
    public final Rectangle initialMapButton;
    public final Rectangle quitButton;
    private final Font font0;
    private final Font font1;

    // CONSTRUCTOR
    public MainMenuPanel(int width) {
        // ATTRIBUTES
        this.width = width;
        this.title = "World Building";
        this.buttonsText = new String[]{"Play", "Load", "Default", "Quit"};

        this.font0 = new Font("arial", Font.BOLD, 50);
        this.font1 = new Font("arial", Font.BOLD, 30);

        int buttonWidth = 150;
        int buttonX = (width / 2) - (buttonWidth / 2);

        playButton =        new Rectangle(buttonX, 150, buttonWidth, 50);
        loadButton =        new Rectangle(buttonX, 250, buttonWidth, 50);
        initialMapButton =  new Rectangle(buttonX, 350, buttonWidth, 50);
        quitButton =        new Rectangle(buttonX, 450, buttonWidth, 50);
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int titleWidth = g.getFontMetrics(font0).stringWidth(title);
        int titleX = (width / 2) - (titleWidth / 2);
        FontMetrics fontMetrics1 = g.getFontMetrics(font1);
        int textWidth;
        int textX;

        g.setFont(font0);
        g.setColor(Color.WHITE);
        g.drawString(title, titleX, 100);

        g.setFont(font1);
        textWidth = fontMetrics1.stringWidth(buttonsText[0]);
        textX = (width / 2) - (textWidth / 2);
        g.drawString(buttonsText[0], textX, playButton.y + 35);
        g2d.draw(playButton);

        textWidth = fontMetrics1.stringWidth(buttonsText[1]);
        textX = (width / 2) - (textWidth / 2);
        g.drawString(buttonsText[1], textX, loadButton.y + 35);
        g2d.draw(loadButton);

        textWidth = fontMetrics1.stringWidth(buttonsText[2]);
        textX = (width / 2) - (textWidth / 2);
        g.drawString(buttonsText[2], textX, initialMapButton.y + 35);
        g2d.draw(initialMapButton);

        textWidth = fontMetrics1.stringWidth(buttonsText[3]);
        textX = (width / 2) - (textWidth / 2);
        g.drawString(buttonsText[3], textX, quitButton.y + 35);
        g2d.draw(quitButton);
    }
}
