package com.addonis.demo.exceptions;

/**
 * NoDataException
 * Exception for record not found
 */
public class NoDataException extends RuntimeException {

    public NoDataException(String type) {
        super(String.format("There are no %s records", type));
    }
}
