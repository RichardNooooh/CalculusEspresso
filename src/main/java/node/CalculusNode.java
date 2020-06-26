package node;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private static final BigDecimal h = new BigDecimal("0.000000000000001");

	private String params;

	/**
	 * Calculus node.Node Constructor
	 * @param cOperator is a calculus operator.
	 */
	public CalculusNode(Operator cOperator, String params)
	{
		this.operator = cOperator;
		this.params = params.strip();
	}

	/**
	 * Evaluates the current node and its subtrees.
	 * @param env is the set of all variables and their corresponding values
	 * @return the result of the operation
	 */
	@Override
	public BigDecimal eval(Map<String, BigDecimal> env)
	{
		BigDecimal val = right.eval(env);
//		BigDecimal input = left.eval(env);

		switch(operator)
		{
			case DERIVATIVE:
				return evaluateDerivative(env, params);
//			case INTEGRAL:
//				return val;
			default:
				return BigDecimal.ZERO;
		}
	}

	/**
	 * Calculates the derivative of the function to its right based on the value on its left.
	 * @return the derivative
	 */
	private BigDecimal evaluateDerivative(Map<String, BigDecimal> env, String variable)
	{
		BigDecimal atX = env.get(variable);
		BigDecimal originalVal = right.eval(env);

		HashMap<String, BigDecimal> newEnv = new HashMap<String, BigDecimal>();
		for (String str : env.keySet())
		{
			BigDecimal val = env.get(str);
			newEnv.put(str, val);
		}

		newEnv.put(params, atX.add(h));
		BigDecimal adjacentVal = right.eval(newEnv);

		return adjacentVal.subtract(originalVal).divide(h, 30, RoundingMode.HALF_UP);
	}

}
