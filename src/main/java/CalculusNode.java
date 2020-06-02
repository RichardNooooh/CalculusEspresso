
import java.util.Map;

/**
 * Supports all calculus operations within the expression tree
 */
public class CalculusNode extends OperatorNode
{
	/**
	 * 'h' is the del-x value of the calculus operations
	 */
	private final int h = 0;

	/**
	 * Calculus Node Constructor
	 * @param cOperator is a calculus operator.
	 */
	public CalculusNode(Operator cOperator)
	{
		this.operator = cOperator;
	}

	/**
	 * Evaluates the current node and its subtrees.
	 * @param env is the set of all variables and their corresponding values
	 * @return the result of the operation
	 */
	@Override
	protected double eval(Map<String, Double> env)
	{
		double val = right.eval(env);
//		double input = left.eval(env);

		switch(operator)
		{
			case DERIVATIVE:
				return val;
//			case INTEGRAL:
//				return val;
			default:
				return 0;
		}
	}

	/**
	 * Calculates the derivative of the function to its right based on the value on its left.
	 * @return the derivative
	 */
	private double evaluateDerivative()
	{
		return 0;
	}

}
