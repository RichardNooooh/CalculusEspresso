package node;

import exceptions.InvalidInputException;

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
	private static final BigDecimal h = new BigDecimal("0.001");

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
				return evaluateDerivative(env);
			case INTEGRAL:
				return evaluateIntegral(env);
			default:
				return BigDecimal.ZERO;
		}
	}

	/**
	 * Calculates the derivative of the function to its right based on the value of "params"
	 * @return the derivative
	 */
	private BigDecimal evaluateDerivative(Map<String, BigDecimal> env)
	{
		BigDecimal atX = env.get(params);
		BigDecimal originalVal = right.eval(env);

		HashMap<String, BigDecimal> newEnv = new HashMap<String, BigDecimal>();
		for (String str : env.keySet())
		{
			BigDecimal val = env.get(str);
			newEnv.put(str, val);
		}

		newEnv.put(params, atX.add(h));
		BigDecimal adjacentVal = right.eval(newEnv);

		return (adjacentVal.subtract(originalVal)).divide(h, 30, RoundingMode.HALF_UP);
	}

	/**
	 * Computes the integral of the function to its right based on the value of "params" using a midpoint Riemann sum
	 * @return the integral from a to b
	 */
	private BigDecimal evaluateIntegral(Map<String, BigDecimal> env)
	{
		Object[] bounds = parseBounds();
		BigDecimal a = (BigDecimal) bounds[0];
		BigDecimal b = (BigDecimal) bounds[1];
		String var = (String) bounds[2];

		int n = (b.subtract(a)).divide(h).intValue();
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal x_i = a;
		for (int i = 0; i < n; i++)
		{
			HashMap<String, BigDecimal> newEnv = new HashMap<String, BigDecimal>();
			for (String str : env.keySet())
			{
				BigDecimal val = env.get(str);
				newEnv.put(str, val);
			}
			//(2x_i + h)/2
			BigDecimal midPoint = h.add(x_i.multiply(new BigDecimal(2))).divide(new BigDecimal(2));
			newEnv.put(var, midPoint);

			sum = sum.add(right.eval(newEnv));
			x_i = x_i.add(h);
		}

		return sum.multiply(h);
	}

	/**
	 * Parses the integral bounds from "params"
	 * @return a Object[] of size 3, containing the BigDecimal bounds, "a, b", and the String variable to integrate over
	 */
	private Object[] parseBounds()
	{
		String[] rawString = params.split(",");
		Object[] parameters = new Object[3];
		try
		{
			String a = rawString[0].strip();
			String b = rawString[1].strip();
			String var = rawString[2].strip();
			parameters[0] = new BigDecimal(a);
			parameters[1] = new BigDecimal(b);
			parameters[2] = var;
		} catch(Exception e)
		{
			throw new InvalidInputException("The integral parameter should be in the form: \"a, b, var\", " +
					"where var is the variable you wish to integrate over.");
		}

		return parameters;
	}
}
