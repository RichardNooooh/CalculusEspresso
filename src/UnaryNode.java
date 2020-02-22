public class UnaryNode extends Node
{
	UnaryOperator operator;

	public UnaryOperator getOperator()
	{
		return operator;
	}

	@Override
	public String toString()
	{
		return operator.toString();
	}
}
