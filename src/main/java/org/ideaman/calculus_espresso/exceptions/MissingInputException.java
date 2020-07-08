package org.ideaman.calculus_espresso.exceptions;

public class MissingInputException extends RuntimeException
{
    public MissingInputException(String errorMessage)
    {
        super(errorMessage);
    }
}
