package com.addonis.demo.exceptions;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String name) {
        super(String.format("%s does not have rights to do this", name));
    }
}
