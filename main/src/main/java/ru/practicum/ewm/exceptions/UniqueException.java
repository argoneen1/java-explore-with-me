package ru.practicum.ewm.exceptions;

public class UniqueException extends IllegalArgumentException{

    public UniqueException() {
        super();
    }

    public UniqueException(String s) {
        super(s);
    }

    public UniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueException(Throwable cause) {
        super(cause);
    }
}
