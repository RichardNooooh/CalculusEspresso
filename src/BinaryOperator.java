public enum BinaryOperator
{
	ADDITION(ExpressionChar.ADDITION),
	SUBTRACTION(ExpressionChar.SUBTRACTION),
	MULTIPLICATION(ExpressionChar.MULTIPLICATION),
	DIVISION(ExpressionChar.DIVISION),
	EXPONENTIAL(ExpressionChar.EXPONENTIAL);

	private ExpressionChar symbol;

	BinaryOperator(ExpressionChar symbol)
	{
		this.symbol = symbol;
	}

	public static ExpressionChar getAbbrev(BinaryOperator operator)
	{
		return operator.symbol;
	}

	@Override
	public String toString()
	{
		return symbol + "";
	}
}
