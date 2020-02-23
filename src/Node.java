public abstract class Node
{
	Node left;
	Node right;
	//I could do without this
	Node parent;

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
