package exceptions;

public class UndefinedVariable extends RuntimeException
{
    public UndefinedVariable(String errorMessage)
    {
        super(errorMessage);
    }
}
