package org.cubixmc.event;

public class EventCause extends Exception {

    private static final long serialVersionUID = 3532808232324183999L;
    private final Throwable cause;

    /**
     * @param throwable Exception which triggered it's parent
     */
    public EventException(Throwable throwable) {
        cause = throwable;
    }

    /**
     * new Exception
     */
    public EventCause() {
        cause = null;
    }

    /**
     * @param cause The exception that made this happen
     * @param message Message sent
     */
    public EventException(Throwable cause, String message){
        super(message);
        this.cause = cause;
    }

    /**
     * @param message Message sent
     */
    public EventException(String message) {
        super(message);
        cause = null;
    }

    /**
     * @return valid Inner Exception, cause instance is throwable
     */
    @Override
    public Throwable getCause(){
        return cause;
    }

}