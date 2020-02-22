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

	private String preprocess(String expression)
	{
		//TODO is there a way to simplify this with lambda expressions?
		for (UnaryOperator bOperator : UnaryOperator.values())
		{
			String abbrev = UnaryOperator.getAbbrev(bOperator);
			expression = changeToAbbrev(expression, abbrev);
		}

		for (CalculusOperator cOperator : CalculusOperator.values())
		{
			String abbrev = CalculusOperator.getAbbrev(cOperator);
			expression = changeToAbbrev(expression, abbrev);
		}

		return expression;
	}

	private String changeToAbbrev(String expression, String abbrev)
	{
		int index = expression.indexOf(abbrev);
		if (index >= 0)
		{
			try
			{
				expression = expression.substring(0, index) + abbrev + expression.substring(abbrev.length() + index + 1);
			} catch (Exception e) //TODO make my own exception? that'll be interesting
			{
				System.out.println("Unary and Calculus Operators should not be at the end");
			}
		}

		return expression;
	}

	private void parse(String expression)
	{
		expression = preprocess(expression);
		String currentNum = "";
		boolean onNum = false;
		char prevChar = 0;
		char[] expressionArray = expression.toCharArray();

		LinkedList<Node> tokens = new LinkedList<Node>();
		for (char c : expressionArray)
		{
			// 0 through 9 or '.'
			if ((c >= ExpressionChar.ZERO.getChar() && c <= ExpressionChar.NINE.getChar())
					|| c == ExpressionChar.DECIMAL.getChar()) //TODO substitute these numbers with enums
			{
				currentNum += c;
				onNum = true;
			}
			else //variables or symbols
			{
				//add the number as a node
				if (onNum)
				{
					onNum = false;
					NumNode newNum = new NumNode(Double.parseDouble(currentNum));
					currentNum = "";

					tokens.add(newNum);
				}
				//see if 'c' is an operator


			}
		}
	}

}