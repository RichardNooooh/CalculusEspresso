public enum BinaryOperator
{
	ADDITION('+'),
	SUBTRACTION('-'),
	MULTIPLICATION('*'),
	DIVISION('/'),
	EXPONENTIAL('^');

	private char symbol;

	BinaryOperator(char symbol)
	{
		this.symbol = symbol;
	}

	public static char getAbbrev(BinaryOperator operator)
	{
		return operator.symbol;
	}

	@Override
	public String toString()
	{
		return symbol + "";
	}
}
