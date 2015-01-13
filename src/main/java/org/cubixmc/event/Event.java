package org.cubixmc.event;
/**
 * 
 * @author Giovanni
 * -pluginmanager.java has no value, or is not found.
 */
public abstract class Event {
    private String name;
    private final boolean asyncable;

    public Event() {
        this(false);
    }


    public Event(boolean isAsync) {
        this.asyncable = isAsync;
    }

    /**
     * @return name of the event which is being called
     */
    public String getNameOfEvent() {
        if (name == null) {
            name = getClass().getSimpleName();
        }
        return name;
    }

    public abstract HandlerList getHandlers();

    /**
     * @return false, only return true if the event is called using Async
     */
    public final boolean isAsync() {
        return asyncable;
    }

    public enum Result {
    /**
     * No parenthizes, only use deny if you're denying an event from calling a specific method.
     */
        DENY,
        
        DEFAULT,
        
        ALLOW;
    }
}