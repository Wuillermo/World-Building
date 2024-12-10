package game.controller;

import game.GameManager;
import game.view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private final GamePanel gamePanel;
    private final GameManager gameManager;

    public KeyHandler(GamePanel gamePanel, GameManager gameManager) {
        this.gamePanel = gamePanel;
        this.gameManager = gameManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(gamePanel.getState() == GamePanel.STATE.GAME) {
            if(code == KeyEvent.VK_S) {
               gameManager.saveMap();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {

        }
        if(code == KeyEvent.VK_A) {

        }
        if(code == KeyEvent.VK_S) {

        }
        if(code == KeyEvent.VK_D) {

        }
    }
}
