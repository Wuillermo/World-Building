package game.controller.Events;

import game.model.core.GameManager;
import game.model.ecs.components.MovementComponent;
import game.view.Camera;
import game.view.GamePanel;

public class EventProcessor implements Runnable {

    private final GameManager gameManager;
    private final EventQueue eventQueue;
    private final GamePanel gamePanel;
    private final MovementComponent cameraMovement;

    public EventProcessor(GameManager gameManager, EventQueue eventQueue, GamePanel gamePanel, MovementComponent movementComponent) {
        this.gameManager = gameManager;
        this.eventQueue = eventQueue;
        this.gamePanel = gamePanel;
        this.cameraMovement = movementComponent;
    }

    @Override
    public void run() {
        while(gameManager.isRunning()) {
            if(eventQueue.hasEvents()) {
                Event event = eventQueue.getNextEvent();

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
                    case CAMERA_UP -> cameraMovement.addMove(MovementComponent.CAMERA_MOVES.CAMERA_UP);
                    case CAMERA_DOWN -> cameraMovement.addMove(MovementComponent.CAMERA_MOVES.CAMERA_DOWN);
                    case CAMERA_LEFT -> cameraMovement.addMove(MovementComponent.CAMERA_MOVES.CAMERA_LEFT);
                    case CAMERA_RIGHT -> cameraMovement.addMove(MovementComponent.CAMERA_MOVES.CAMERA_RIGHT);
                    case CAMERA_UP_RELEASED -> cameraMovement.removeMove(MovementComponent.CAMERA_MOVES.CAMERA_UP);
                    case CAMERA_DOWN_RELEASED -> cameraMovement.removeMove(MovementComponent.CAMERA_MOVES.CAMERA_DOWN);
                    case CAMERA_LEFT_RELEASED -> cameraMovement.removeMove(MovementComponent.CAMERA_MOVES.CAMERA_LEFT);
                    case CAMERA_RIGHT_RELEASED -> cameraMovement.removeMove(MovementComponent.CAMERA_MOVES.CAMERA_RIGHT);
                }
            }
        }
    }
}
