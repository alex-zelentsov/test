package com.solanteq.test;

/**
 * CalculatingException
 *
 * @author azelentsov
 */
public class CalculatingException extends RuntimeException {
    public CalculatingException(Exception e) {
        super(e);
    }
}
