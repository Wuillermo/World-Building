package game.controller.Events;

public class Event {
    public enum EventType {
        NEW_GAME, LOAD_GAME, OPEN_MENU, QUIT_GAME
    }

    private final EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
