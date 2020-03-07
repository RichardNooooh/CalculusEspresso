package Main;

/**
 * Supports all unary operations within the expression tree
 */
public class UnaryNode extends OperatorNode
{
	public UnaryNode(Operator bOperator)
	{
		this.operator = bOperator;
	}
}
