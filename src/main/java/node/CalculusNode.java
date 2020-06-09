package node;

import java.util.HashMap;
import java.util.Map;

/**
 * Supports all calculus operations within the expression tree
 */
public class CalculusNode extends OperatorNode
{
	/**
	 * 'h' is the del-x value of the calculus operations
	 */
	private static final double h = 0.0001;

	/**
	 * Calculus node.Node Constructor
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
	public double eval(Map<String, Double> env)
	{
		double val = right.eval(env);
//		double input = left.eval(env);

		switch(operator)
		{
			case DERIVATIVE:
				return evaluateDerivative(env);
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
	private double evaluateDerivative(Map<String, Double> env)
	{
		double atX = env.get("x");
		double originalVal = right.eval(env);

		HashMap<String, Double> newEnv = new HashMap<String, Double>();
		for (String str : env.keySet())
		{
			Double val = env.get(str);
			newEnv.put(str, val);
		}

		newEnv.put("x", atX + h);
		double adjacentVal = right.eval(newEnv);

		return (adjacentVal - originalVal) / h;
	}

}
