package node;

import java.util.Map;

/**
 * Supports all unary operations within the expression tree
 */
public class UnaryNode extends OperatorNode
{
	public UnaryNode(Operator uOperator)
	{
		this.operator = uOperator;
	}

	@Override
	public double eval(Map<String, Double> env)
	{
		double val = right.eval(env);

		switch(operator)
		{
			case SQUARE_ROOT:
				return Math.sqrt(val);
			case LOGARITHM:
				return Math.log(val);
			case NEGATIVE:
				return -val;
			default:
				return 0;
		}
	}
}
