import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

/*
 * Parses a raw expression into tokens
 */
public class ExpressionParser
{
	/**
	 * Constructs a new ExpressionParser
	 */
	public ExpressionParser(String expression)
	{
		LinkedList<Node> tokens = parse(expression);
		System.out.println(tokens);
		//createTree();
	}

	/**
	 * Converts all multi-character operations into a single character
	 * based on their corresponding enum val
	 * Ex: sqrt -> ExpressionChar.SQUARE_ROOT
	 * @param expression
	 * @return condensed expression
	 */
	private String preprocessor(String expression)
	{
		List<Operator> multiCharOpList = Operator.getMultiCharList();
		for (Operator operator : multiCharOpList)
		{
			String abbrev = operator.getAbbrev();
			char substituteChar = operator.getChar();
			int index = expression.indexOf(abbrev);
			while (index >= 0)
			{
				try
				{
					expression = expression.substring(0, index) + substituteChar + expression.substring(abbrev.length() + index);
				} catch (Exception e) //TODO make my own exception? that'll be interesting
				{
					throw new InputMismatchException("Unary and Calculus Operators should not be at the end");
				}
				index = expression.indexOf(abbrev);
			}
		}
		//TODO turn expression into postfix notation

		return expression;
	}

	/**
	 * Return a list of token nodes of all operators and operands in the given expression
	 * @param expression
	 * @return List of token nodes
	 */
	private LinkedList<Node> parse(String expression)
	{
		expression = preprocessor(expression);

		LinkedList<Node> tokens = new LinkedList<Node>();
		String currentNum = "";
		char[] expressionArray = expression.toCharArray();
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

	/**
	 * Adds the current character into the tokens list as an operator if possible.
	 * Otherwise, return false.
	 * @param c is the current character of the expression string
	 * @param tokens is the tokens list
	 * @return If c was added into tokens, return true. Else, return false
	 */
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