public enum CalculusOperator
{
	DERIVATIVE ("der"),
	INTEGRAL ("int");

	private String symbol;
	CalculusOperator(String symbol)
	{
		this.symbol = symbol;
	}
}
