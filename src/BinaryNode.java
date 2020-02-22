public class BinaryNode extends Node
{
	BinaryOperator operator;

	public BinaryOperator getOperator()
	{
		return operator;
	}

	@Override
	public String toString()
	{
		return operator.toString();
	}
}
