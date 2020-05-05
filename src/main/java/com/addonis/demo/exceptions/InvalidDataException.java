package com.addonis.demo.exceptions;

/**
 * InvalidDataException
 * Exception for entering an invalid data
 */
public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String type) {
        super(String.format("Invalid %s", type));
    }

    public InvalidDataException(String type, String message) {
        super(String.format("%s %s", type, message));
    }

}
