package org.ideaman.calculus_espresso.exceptions;

public class InvalidInputException extends RuntimeException
{
    public InvalidInputException(String errorMessage)
    {
        super(errorMessage);
    }
}
