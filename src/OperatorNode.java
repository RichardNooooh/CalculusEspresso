public abstract class OperatorNode extends Node
{
	Operator operator;
	public Operator getOperator()
	{
		return operator;
	}
	@Override
	public String toString()
	{
		return operator.toString();
	}
}
