package game.controller.Events;

import game.model.core.GameManager;
import game.view.GamePanel;

public class EventProcessor implements Runnable {

    private final GameManager gameManager;
    private final EventQueue eventQueue;
    private final GamePanel gamePanel;

    public EventProcessor(GameManager gameManager, EventQueue eventQueue, GamePanel gamePanel) {
        this.gameManager = gameManager;
        this.eventQueue = eventQueue;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while(gameManager.isRunning()) {
            if(eventQueue.hasEvents()) {
                Event event = eventQueue.getNextEvent();

                switch(event.getType()) {
                    case NEW_GAME -> {
                        gameManager.newGameButton();
                        gameManager.switchToGameplayContext();
                    }
                    case LOAD_GAME -> {
                        gameManager.loadGameButton();
                        gameManager.switchToGameplayContext();
                    }
                    case OPEN_MENU -> {
                        gamePanel.setState(GamePanel.SCREEN_STATE.MAIN_MENU);
                        gameManager.switchToMenuContext();
                    }
                    case QUIT_GAME -> {
                        gameManager.stopRunning();
                    }
                }
            }
        }
    }
}
