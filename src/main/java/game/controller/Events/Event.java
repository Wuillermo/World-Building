package game.controller.Events;

public record Event(game.controller.Events.Event.EventType type) {
    public enum EventType {
        NEW_GAME, LOAD_GAME, DEFAULT_INIT, OPEN_MENU, QUIT_GAME,
        CAMERA_UP, CAMERA_DOWN, CAMERA_LEFT, CAMERA_RIGHT,
        CAMERA_UP_RELEASED, CAMERA_DOWN_RELEASED, CAMERA_LEFT_RELEASED, CAMERA_RIGHT_RELEASED
    }
}
