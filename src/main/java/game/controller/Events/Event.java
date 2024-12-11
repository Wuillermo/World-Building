package game.controller.Events;

public record Event(game.controller.Events.Event.EventType type) {
    public enum EventType {
        NEW_GAME, LOAD_GAME, OPEN_MENU, QUIT_GAME
    }
}
