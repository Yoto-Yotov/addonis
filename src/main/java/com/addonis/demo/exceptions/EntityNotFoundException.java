package com.addonis.demo.exceptions;

/**
 * EntityNotFoundException
 * Exception for entering data, that can not be found.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type, int id) {
        super(String.format("%s with id %d does not exist", type, id));
    }

    public EntityNotFoundException(String type, String name) {
        super(String.format("%s with name %s does not exist", type, name));
    }

    public EntityNotFoundException(String type, String atribute, int id) {
        super(String.format("%s with %s %d does not exist", type, atribute, id));
    }
}
