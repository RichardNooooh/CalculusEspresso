package org.ideaman.calculus_espresso.core.node;

import org.ideaman.calculus_espresso.exceptions.InvalidInputException;
import org.ideaman.calculus_espresso.util.BigFunctionsPlus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Supports all calculus operations within the expression tree
 */
public class CalculusNode extends ParameterFunctionNode
{
	/**
	 * 'h' is the del-x value for derivative operations
	 *
	 * The smaller the value, the more accurate the derivative.
	 * The performance of the derivative is not affected a lot by this value.
	 */
	private static final BigDecimal h = new BigDecimal("0.000000001");

	/**
	 * 'del_x' is the value of integral operations
	 *
	 * The smaller the value, the more accurate the integral, but
	 * the performance of the integral is severely affected by this value.
	 */
	private static final BigDecimal del_x = new BigDecimal("0.0001");


	/**
	 * Calculus node.Node Constructor
	 * @param cOperator is a calculus operator.
	 */
	public CalculusNode(Operator cOperator, String parameters)
	{
		super(cOperator, parameters);
	}

	/**
	 * Evaluates the current node and its subtrees.
	 * @param env is the set of all variables and their corresponding values
	 * @return the result of the operation
	 */
	@Override
	public BigDecimal eval(Map<String, BigDecimal> env)
	{
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
		String var = parseDerivativePoint(env);

		BigDecimal atX = env.get(var);
		BigDecimal originalVal = right.eval(env);

		HashMap<String, BigDecimal> newEnv = new HashMap<String, BigDecimal>();
		for (String str : env.keySet())
		{
			BigDecimal val = env.get(str);
			newEnv.put(str, val);
		}

		newEnv.put(var, atX.add(h));
		BigDecimal adjacentVal = right.eval(newEnv);

		return BigFunctionsPlus.divide(adjacentVal.subtract(originalVal), h);
	}

	/**
	 * Parses the point at which the derivative is evaluated
	 */
	private String parseDerivativePoint(Map<String, BigDecimal> env)
	{
		String[] variableValuePair = params.split("=");
		String variable = variableValuePair[0].trim();
		BigDecimal value = BigDecimal.valueOf(Double.parseDouble(variableValuePair[1].trim()));
		env.put(variable, value);

		return variable;
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

		int n = BigFunctionsPlus.divide(b.subtract(a), del_x).intValue();
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
			BigDecimal midPoint = del_x.add(x_i.multiply(new BigDecimal(2))).divide(BigFunctionsPlus.TWO);
			newEnv.put(var, midPoint);

			sum = sum.add(right.eval(newEnv));
			x_i = x_i.add(del_x);
		}

		return sum.multiply(del_x);
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
			String a = rawString[0].trim();
			String b = rawString[1].trim();
			String var = rawString[2].trim();
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
