public enum UnaryOperator
{
	//standard unary operators
	SQUARE_ROOT("sqrt", ExpressionChar.SQUARE_ROOT)
			{
				@Override
				public String toString()
				{
					return "SquareRoot";
				}
			},
	LOGARITHM("log", ExpressionChar.LOGARITHM)
			{
				@Override
				public String toString()
				{
					return "Logarithm";
				}
			};
	//TODO add root

	private String symbol;
	private ExpressionChar rc;

	UnaryOperator(String symbol, ExpressionChar rc)
	{
		this.symbol = symbol;
	}

	public static String getAbbrev(UnaryOperator operator)
	{
		return operator.symbol;
	}
}
