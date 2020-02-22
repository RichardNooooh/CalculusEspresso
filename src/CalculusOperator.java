public enum CalculusOperator
{
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
	//TODO Add Gradient and other multivar operators

	private String abbreviation;
	private ExpressionChar rc;

	CalculusOperator(String abbreviation, ExpressionChar rc)
	{
		this.abbreviation = abbreviation;
		this.rc = rc;
	}

	public static String getAbbrev(CalculusOperator operator)
	{
		return operator.abbreviation;
	}

}
