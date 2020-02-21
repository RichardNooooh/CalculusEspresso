public enum UnaryOperator
{
	//standard unary operators
	SQUARE_ROOT("sqrt")
			{
				@Override
				public String toString()
				{
					return "SquareRoot";
				}
			},
	LOGARITHM("log")
			{
				@Override
				public String toString()
				{
					return "Logarithm";
				}
			};
	//TODO add root

	private String symbol;

	UnaryOperator(String symbol)
	{
		this.symbol = symbol;
	}

	public static String getAbbrev(UnaryOperator operator)
	{
		return operator.symbol;
	}
}
