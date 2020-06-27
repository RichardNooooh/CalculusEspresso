package exceptions;

public class MissingInputException extends RuntimeException
{
    public MissingInputException(String errorMessage)
    {
        super(errorMessage);
    }
}
