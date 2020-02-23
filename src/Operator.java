public enum Operator
{
	//Binary Operations
	ADDITION("+", ExpressionChar.ADDITION),
	SUBTRACTION("-", ExpressionChar.SUBTRACTION),
	MULTIPLICATION("*", ExpressionChar.MULTIPLICATION),
	DIVISION("/", ExpressionChar.DIVISION),
	EXPONENTIAL("^", ExpressionChar.EXPONENTIAL),

	//Unary Operations
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
	},

	//Calculus Operations
	DERIVATIVE("der", ExpressionChar.DERIVATIVE)
	{
		@Override
		public String toString()
		{
			return "Derivative";
		}
	},
	INTEGRAL("int", ExpressionChar.INTEGRAL)
	{
		@Override
		public String toString()
		{
			return "Integral";
		}
	};

	private String abbrev;
	private ExpressionChar chr;
	Operator(String abbrev, ExpressionChar chr)
	{
		this.abbrev = abbrev;
		this.chr = chr;
	}

	public String getAbbrev()
	{
		return abbrev;
	}

	@Override
	public String toString()
	{
		return chr.toString();
	}
}
