package node;

import java.math.BigDecimal;
import java.util.Map;

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
				return leftVal.divide(rightVal);
			case EXPONENTIAL:
				return BigDecimal.ZERO; //TODO this does not work yet, pow() only works with integer exponents
			default:
				return BigDecimal.ZERO;
		}
	}

}
