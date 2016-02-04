package com.solanteq.test;

/**
 * CalculatingInterruptedException
 *
 * @author azelentsov
 */
public class CalculatingInterruptedException extends RuntimeException {
    public CalculatingInterruptedException(InterruptedException e) {
        super(e);
    }
}
