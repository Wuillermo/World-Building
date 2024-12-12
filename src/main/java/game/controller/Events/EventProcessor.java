package game.controller.Events;

import game.model.core.GameManager;
import game.view.Camera;
import game.view.GamePanel;

public class EventProcessor implements Runnable {

    private final GameManager gameManager;
    private final EventQueue eventQueue;
    private final GamePanel gamePanel;
    private final Camera camera;

    public EventProcessor(GameManager gameManager, EventQueue eventQueue, GamePanel gamePanel, Camera camera) {
        this.gameManager = gameManager;
        this.eventQueue = eventQueue;
        this.gamePanel = gamePanel;
        this.camera = camera;
    }

    @Override
    public void run() {
        while(gameManager.isRunning()) {
            if(eventQueue.hasEvents()) {
                Event event = eventQueue.getNextEvent();
                System.out.println("Processing event");

                switch(event.type()) {
                    // Menu Events
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
                    case QUIT_GAME -> gameManager.stopRunning();

                    // Camera movement Events
                    case CAMERA_UP -> camera.cameraUp();
                    case CAMERA_DOWN -> camera.cameraDown();
                    case CAMERA_LEFT -> camera.cameraLeft();
                    case CAMERA_RIGHT -> camera.cameraRight();
                    case CAMERA_UP_RELEASED -> camera.stopUp();
                    case CAMERA_DOWN_RELEASED -> camera.stopDown();
                    case CAMERA_LEFT_RELEASED -> camera.stopLeft();
                    case CAMERA_RIGHT_RELEASED -> camera.stopRight();
                }
            }
        }
    }
}
