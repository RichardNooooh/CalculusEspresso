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
	protected double eval(Map<Character, Double> env)
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
