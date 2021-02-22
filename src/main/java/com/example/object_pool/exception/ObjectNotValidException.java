package com.example.object_pool.exception;

public class ObjectNotValidException extends RuntimeException {

    public ObjectNotValidException() {
    }

    public ObjectNotValidException(String message) {
        super(message);
    }

    public ObjectNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotValidException(Throwable cause) {
        super(cause);
    }
}
