package com.cs.etrading.csbooking.common.exception;

import javax.swing.text.html.parser.Entity;

public class NotFoundException extends  RuntimeException {
    private  static final String NOT_FOUND = "%s with ID=%s Not Found";
    public NotFoundException(String entity, Long id) {
        super(String.format(NOT_FOUND,entity, id.toString())) ;
    }
}
