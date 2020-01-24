package com.cs.etrading.csbooking.common.exception;

public class EntityExistsException extends  RuntimeException {
    private  static final String ENTITY_EXISTS_MESSAGE = "%s with NAME=%s exists";
    public EntityExistsException(String entity, String bookName) {
        super(String.format(ENTITY_EXISTS_MESSAGE,entity, bookName)) ;
    }
}
