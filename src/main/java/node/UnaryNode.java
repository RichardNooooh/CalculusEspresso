package node;

import java.math.BigDecimal;
import java.math.MathContext;
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
	public BigDecimal eval(Map<String, BigDecimal> env)
	{
		BigDecimal val = right.eval(env);

		switch(operator)
		{
			case SQUARE_ROOT:
				return val.sqrt(new MathContext(10));
			case LOGARITHM:
				return BigDecimal.ZERO; //TODO use Newton's method
			case NEGATIVE:
				return val.negate();
			default:
				return BigDecimal.ZERO;
		}
	}
}
