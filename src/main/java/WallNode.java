
import java.util.Map;

/**
 * Wall Notation
 * Source: https://math.stackexchange.com/questions/1515085/represent-unary-and-functions-in-postfix-notation
 *
 * Wall notation (|) allows the application to identify what portion of the expression belongs to which function.
 * For example:
 *      sqrt(4*5 + 3) -> | 4 5 * 3 + sqrt
 *
 * This node represents that "wall".
 */
public class WallNode extends Node
{
	@Override
	public String toString()
	{
		return "|";
	}

	//shouldn't be evaluated
	@Override
	protected double eval(Map<Character, Double> env)
	{
		return 0;
	}
}
