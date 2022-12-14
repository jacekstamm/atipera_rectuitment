package com.atipera.recruitment.exception;

public class WrongInputException extends RuntimeException {
    public WrongInputException(String input) {
        super("Wrong input: " + input + ". Please consider to change your input.");
    }
}