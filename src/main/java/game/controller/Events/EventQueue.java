package game.controller.Events;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueue {

    private final Queue<Event> eventQueue = new LinkedList<>();

    public void addEvent(Event event) {
        eventQueue.offer(event);
    }

    public Event getNextEvent() {
        return eventQueue.poll();
    }

    public boolean hasEvents() {
        return !eventQueue.isEmpty();
    }
}
