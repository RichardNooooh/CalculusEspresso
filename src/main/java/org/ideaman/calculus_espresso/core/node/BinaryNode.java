package org.ideaman.calculus_espresso.core.node;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import org.ideaman.calculus_espresso.util.BigFunctionsPlus;

/**
 * Supports all binary operations within the expression tree
 */
public class BinaryNode extends OperatorNode
{
	public BinaryNode(Operator bOperator)
	{
		this.operator = bOperator;
	}

	@Override
	public BigDecimal eval(Map<String, BigDecimal> env)
	{
		BigDecimal leftVal = left.eval(env);
		BigDecimal rightVal = right.eval(env);

		switch(operator)
		{
			case ADDITION:
				return leftVal.add(rightVal);
			case SUBTRACTION:
				return leftVal.subtract(rightVal);
			case MULTIPLICATION:
				return leftVal.multiply(rightVal);
			case DIVISION:
				return BigFunctionsPlus.divide(leftVal, rightVal);
			case EXPONENTIAL:
				return BigFunctionsPlus.pow(leftVal, rightVal);
			case MODULUS:
				return leftVal.remainder(rightVal, new MathContext(10));
			default:
				return BigDecimal.ZERO;
		}
	}

}
