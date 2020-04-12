package com.addonis.demo.exceptions;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String type) {
        super(String.format("Invalid %s", type));
    }

}
