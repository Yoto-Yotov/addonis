package com.addonis.demo.exceptions;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String name) {
        super(String.format("%s has not the rights to do this", name));
    }
}
