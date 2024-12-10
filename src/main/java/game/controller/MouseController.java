package game.controller;

import game.view.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener {

    private final GamePanel gamePanel;
    public MouseController(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        String buttonPressed;
        if(gamePanel.getState() == GamePanel.STATE.MENU) {
            // PLAY button
            if((mx >= gamePanel.getMenu().playButton.x) && (mx <= (gamePanel.getMenu().playButton.x + gamePanel.getMenu().playButton.width))
                &&(my >= gamePanel.getMenu().playButton.y) && (my <= (gamePanel.getMenu().playButton.y + gamePanel.getMenu().playButton.height))) {
                buttonPressed = "PLAY";
                gamePanel.menuButtonPressed(buttonPressed);
            }
            // LOAD button
            else if((mx >= gamePanel.getMenu().loadButton.x) && (mx <= (gamePanel.getMenu().loadButton.x + gamePanel.getMenu().loadButton.width))
                    &&(my >= gamePanel.getMenu().loadButton.y) && (my <= (gamePanel.getMenu().loadButton.y + gamePanel.getMenu().loadButton.height))) {
                buttonPressed = "LOAD";
                gamePanel.menuButtonPressed(buttonPressed);
            }
            // DEFAULT button
            else if((mx >= gamePanel.getMenu().initialMapButton.x) && (mx <= (gamePanel.getMenu().initialMapButton.x + gamePanel.getMenu().initialMapButton.width))
                    &&(my >= gamePanel.getMenu().initialMapButton.y) && (my <= (gamePanel.getMenu().initialMapButton.y + gamePanel.getMenu().initialMapButton.height))) {
                buttonPressed = "DEFAULT";
                gamePanel.menuButtonPressed(buttonPressed);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
