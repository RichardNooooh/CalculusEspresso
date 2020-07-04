package core.node;

import util.BigFunctionsPlus;
import util.tareknaj.BigFunctions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

/**
 * Supports all unary operations within the expression tree
 */
public class UnaryNode extends FunctionNode
{
	private final static int SCALE = 10;

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
				return val.sqrt(new MathContext(SCALE));
			case NATURAL_LOGARITHM:
				return BigFunctions.ln(val, SCALE);
			case NEGATIVE:
				return val.negate();
			case SIN:
				return BigFunctionsPlus.sin(val);
			case COS:
				return BigFunctionsPlus.cos(val);
			default:
				return BigDecimal.ZERO;
		}
	}
}
