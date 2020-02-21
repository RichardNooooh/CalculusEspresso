public enum BinaryOperator
{
	ADDITION ('+'),
	SUBTRACTION ('-'),
	MULTIPLICATION ('*'),
	DIVISION ('/'),
	EXPONENTIAL ('^');

	private char symbol;
	BinaryOperator(char symbol)
	{
		this.symbol = symbol;
	}
}
