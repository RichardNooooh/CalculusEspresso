public class CalculusNode extends Node
{
	CalculusOperator operator;

	public CalculusOperator getOperator()
	{
		return operator;
	}

	@Override
	public String toString()
	{
		return operator.toString();
	}
}
