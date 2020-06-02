import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeTest
{
	public static void main(String[] args)
	{
		ExpressionTree tree = new ExpressionTree("y + x ~~ x=27 y=35");
		System.out.println(tree.evaluate(tree.getParser().getVariableValueMap()));
	}
}