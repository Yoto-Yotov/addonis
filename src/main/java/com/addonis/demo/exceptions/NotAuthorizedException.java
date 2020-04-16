package com.addonis.demo.exceptions;

/**
 * NotAuthorizedException
 * Exception for entering data, when user is not authenticated.
 */
public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String name) {
        super(String.format("%s does not have rights to do this", name));
    }
}
