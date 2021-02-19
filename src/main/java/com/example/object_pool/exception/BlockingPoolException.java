package com.example.object_pool.exception;

public class BlockingPoolException extends Exception {

    public BlockingPoolException() {
    }

    public BlockingPoolException(String message) {
        super(message);
    }

    public BlockingPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockingPoolException(Throwable cause) {
        super(cause);
    }
}
