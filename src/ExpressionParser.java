import java.util.LinkedList;

/*
 * Parses a raw expression into tokens
 */
public class ExpressionParser
{
	public ExpressionParser(String expression)
	{
		LinkedList<Node> tokens = parse(expression);
		System.out.println(tokens);
		//createTree();
	}

	private String preprocessor(String expression)
	{
		for (Operator operator : Operator.values())
		{
			String abbrev = operator.getAbbrev();
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

	private LinkedList<Node> parse(String expression)
	{
		expression = preprocessor(expression);
		char[] expressionArray = expression.toCharArray();
		LinkedList<Node> tokens = new LinkedList<Node>();

		String currentNum = "";
		boolean onNum = false;

		for (char c : expressionArray)
		{
			if (c == ' ')
				continue;

			// 0 through 9 or '.'
			if ((c >= ExpressionChar.ZERO.getChar() && c <= ExpressionChar.NINE.getChar())
					|| c == ExpressionChar.DECIMAL.getChar())
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
				boolean isOperator = matchOperators(tokens, c);
				if (!isOperator)
				{
					VarNode varNode = new VarNode(c);
					tokens.add(varNode);
				}

			}
		}
		//TODO fix, because this is jank.
		if (onNum)
		{
			NumNode newNum = new NumNode(Double.parseDouble(currentNum));
			tokens.add(newNum);
		}
		return tokens;
	}

	private boolean matchOperators(LinkedList<Node> tokens, char c)
	{
		for (Operator op : Operator.values())
		{
			char opChar = op.getChar();
			if (c == opChar)
			{
				OperationType type = op.getType();
				switch (type)
				{
					case BINARY:
						BinaryNode bNode = new BinaryNode(op);
						tokens.add(bNode);
						break;
					case UNARY:
						UnaryNode uNode = new UnaryNode(op);
						tokens.add(uNode);
						break;
					case CALCULUS:
						CalculusNode cNode = new CalculusNode(op);
						tokens.add(cNode);
						break;
				}
				return true;
			}
		}
		return false;
	}

}