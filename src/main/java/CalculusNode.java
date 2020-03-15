import java.util.Map;

/**
 * Supports all calculus operations within the expression tree
 */
public class CalculusNode extends OperatorNode
{
	public CalculusNode(Operator bOperator)
	{
		this.operator = bOperator;
	}

	@Override
	protected double eval(Map<Character, Double> env)
	{
		double val = right.eval(env);

		switch(operator)
		{
			case DERIVATIVE:
				return val;
			case INTEGRAL:
				return val;
			default:
				return 0;
		}
	}
}
