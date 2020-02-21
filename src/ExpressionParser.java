import java.util.LinkedList;

/*
 * Parses a raw expression into tokens
 */
public class ExpressionParser
{
	public ExpressionParser(String expression)
	{
		//parse(expression);
		//createTree();
	}

	private void parse(String expression)
	{
		String currentNum = "";
		char[] expressionArray = expression.toCharArray();

		LinkedList<Node> tokens = new LinkedList<Node>();
		for (char c : expressionArray)
		{
			switch (c)
			{

			}
		}
	}

}
