
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
	protected double eval(Map<String, Double> env)
	{
		double leftVal = left.eval(env);
		double rightVal = right.eval(env);

		switch(operator)
		{
			case ADDITION:
				return leftVal + rightVal;
			case SUBTRACTION:
				return leftVal - rightVal;
			case MULTIPLICATION:
				return leftVal * rightVal;
			case DIVISION:
				return leftVal / rightVal;
			case EXPONENTIAL:
				return Math.pow(leftVal, rightVal);
			default:
				return 0;
		}
	}

}
