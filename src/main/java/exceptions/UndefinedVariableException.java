package exceptions;

public class UndefinedVariableException extends RuntimeException
{
    public UndefinedVariableException(String errorMessage)
    {
        super(errorMessage);
    }
}
