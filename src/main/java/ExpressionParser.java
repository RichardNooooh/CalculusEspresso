
import java.beans.Expression;
import java.util.*;
//TODO this class should be used by the ExpressionTree
/*
 * Parses a raw expression into tokens
 */
public class ExpressionParser
{
	LinkedList<Node> tokens;

	/**
	 * Constructs a new ExpressionParser
	 */
	public ExpressionParser(String expression)
	{
		LinkedList<Node> tokens = parse(expression);
//		ExpressionTree tree = new ExpressionTree(tokens);
	}

	public LinkedList<Node> getTokens()
	{
		return tokens;
	}

	/**
	 * Alters given expression and returns a new String in postfix notation with
	 * substituted character operators
	 *
	 * @param expression
	 * @return
	 */
	private String preprocessor(String expression)
	{
		expression = substituteMultiCharOp(expression);
//		expression = infixToPostfix(expression);
		return expression;
	}

	/**
	 * Converts all multi-character operations into a single character
	 * based on their corresponding enum val
	 * Ex: sqrt -> ExpressionChar.SQUARE_ROOT
	 *
	 * @param expression
	 * @return condensed expression
	 */
	private String substituteMultiCharOp(String expression)
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

		return expression;
	}

	//TEMP
	private String infixToPostfix(String expression)
	{
		List<Node> tokens = parse(expression);
		String result = "";
		for (Node token : tokens)
		{
			result += " " + token.toString() + " ";
		}

		return result;

	}

	/**
	 * Return a list of token nodes of all operators and operands in the given expression
	 *
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

		for (int i = 0; i < expressionArray.length; i++)
		{
			char c = expressionArray[i];

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
					if (!currentNum.equals(""))
					{
						NumNode newNum = new NumNode(Double.parseDouble(currentNum));
						currentNum = "";
						tokens.add(newNum);
					}
				}
				//check if there's a unary negative sign
				if (c == Operator.SUBTRACTION.getChar())
				{
					HashMap<Character, Operator> map = Operator.getRawCharList();
					if (i == 0 || map.get(expressionArray[i - 1]) != null || expressionArray[i - 1] == ExpressionChar.LEFT_PARENTHESIS.getChar())
					{
						currentNum += Operator.SUBTRACTION.getChar();
						continue;
					}
				}
				boolean isOperator = matchOperators(tokens, c);
				if (!isOperator)
				{
					if (c == ExpressionChar.LEFT_PARENTHESIS.getChar() || c == ExpressionChar.RIGHT_PARENTHESIS.getChar())
					{
						ParenthesisNode parenNode = new ParenthesisNode(c);
						tokens.add(parenNode);
					}
					else
					{
						VarNode varNode = new VarNode(c);
						tokens.add(varNode);
					}
				}

			}

		} //TODO 'tis real bad. please refactor into something pretty. omfg this is so god damn bad
		return tokens;
	}

	/**
	 * Adds the current character into the tokens list as an operator if possible.
	 * Otherwise, return false.
	 *
	 * @param c      is the current character of the expression string
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

	/**
	 * Represents the parenthesis in the expression. It should not be used in the Expression Tree, only for
	 * parsing the expressions.
	 */
	private class ParenthesisNode extends Node
	{
		boolean isLeft;

		public ParenthesisNode(char parenthesis)
		{
			if (parenthesis == '(')
				isLeft = true;
		}

		public boolean isLeftParenthesis()
		{
			return isLeft;
		}

		public String toString()
		{
			return isLeft ? "(" : ")";
		}
	}

}