package com.addonis.demo.exceptions;

/**
 * DuplicateEntityException
 * Exception for data that already exists.
 */
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String type) {
        super(String.format("This %s already exist", type));
    }


    public DuplicateEntityException(String type, String tag) {
        super(String.format("This %s already has this %s", type, tag));
    }

    public DuplicateEntityException(String type, String param, String value) {
        super(String.format("%s with %s %s already exist", type, param, value));
    }

    public DuplicateEntityException(String type, String param, String value, String place) {
        super(String.format("%s with %s %s already exist in %s", type, param, value, place));
    }
}
