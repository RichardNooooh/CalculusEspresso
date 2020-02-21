public enum CalculusOperator
{
	DERIVATIVE("der")
			{
				@Override
				public String toString()
				{
					return "Derivative";
				}
			},
	INTEGRAL("int")
			{
				@Override
				public String toString()
				{
					return "Derivative";
				}
			};
	//TODO Add Gradient and other multivar operators

	private String abbreviation;

	CalculusOperator(String symbol)
	{
		this.abbreviation = symbol;
	}

	public static String getAbbrev(CalculusOperator operator)
	{
		return operator.abbreviation;
	}

}
