
import java.util.*;
import java.util.regex.Pattern;

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
	public ExpressionParser(String expression, boolean isPostfix)
	{
		expression = substituteMultiCharOp(expression);
		tokens = parseToTokens(expression);

		if (isPostfix)
			tokens = infixToPostfix(tokens);
	}

	public LinkedList<Node> getTokens()
	{
		return tokens;
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

	/**
	 * Return a list of token nodes of all operators and operands in the given expression
	 *
	 *
	 * @param expression
	 * @return List of token nodes
	 */
	private LinkedList<Node> parseToTokens(String expression)
	{
		LinkedList<Node> tokensList = new LinkedList<Node>();
		tokensList = initialTokenizer(tokensList, expression);
		tokensList = addUnaryNegativeOperators(tokensList);

		return tokensList;
	}

	private LinkedList<Node> addUnaryNegativeOperators(LinkedList<Node> tokensList)
	{
		LinkedList<Node> newTokens = new LinkedList<Node>();
		boolean nextUnaryNeg = true;
		for (int i = 0; i < tokensList.size(); i++)
		{
			Node n = tokensList.get(i);
			if (n instanceof OperatorNode)
			{
				Operator operator = ((OperatorNode)n).getOperator();
				if (operator == Operator.SUBTRACTION && nextUnaryNeg)
					n = new UnaryNode(Operator.NEGATIVE);
				else if (n instanceof ParenthesisNode)
					nextUnaryNeg = ((ParenthesisNode)n).isLeftParenthesis();
				else
					nextUnaryNeg = true;
			}
			else
				nextUnaryNeg = false;

			newTokens.add(n);
		}

		boolean hadAddedNumNode = false;
		LinkedList<Node> resultTokens = new LinkedList<>();
		for (int i = 0; i < newTokens.size(); i++)
		{
			if (!hadAddedNumNode)
			{
				Node n = newTokens.get(i);
				if (n instanceof OperatorNode
						&& ((OperatorNode) n).getOperator() == Operator.NEGATIVE
						&& newTokens.get(i + 1) instanceof NumNode)
				{
					((NumNode) newTokens.get(i + 1)).setNeg();
					n = newTokens.get(i + 1);
					hadAddedNumNode = true;
				}
				resultTokens.add(n);
			}
			else
				hadAddedNumNode = false;
		}

		return resultTokens;
	}

	private LinkedList<Node> initialTokenizer(LinkedList<Node> tokensList, String expression)
	{
		StringBuilder numBuffer = new StringBuilder();
		boolean buffHasDecimal = false;
		char[] expressionCharArray = expression.toCharArray();
		for (int i = 0; i < expressionCharArray.length; i++)
		{
			char c = expressionCharArray[i];
			if (!Pattern.matches("\\s", "" + c))
			{
				//Check numbers/decimal
				if (c >= ExpressionChar.ZERO.getChar() && c <= ExpressionChar.NINE.getChar())
					numBuffer.append(c);
				else if (c == ExpressionChar.DECIMAL.getChar())
				{
					if (!buffHasDecimal)
					{
						numBuffer.append(c);
						buffHasDecimal = true;
					}
					else
						throw new IllegalArgumentException("Numbers can only have 1 decimal.");
				}
				//Non-numerical character
				else
				{
					//reset numBuffer and add number to tokens
					clearNumBuffer(tokensList, numBuffer.toString());
					numBuffer = new StringBuilder();
					buffHasDecimal = false;

					if (c == ExpressionChar.LEFT_PARENTHESIS.getChar() || c == ExpressionChar.RIGHT_PARENTHESIS.getChar())
						tokensList.add(new ParenthesisNode(c));
					else
					{
						boolean isOperator = matchOperators(tokensList, c);
						if (!isOperator)
							tokensList.add(new VarNode(c));
					}
				}
			}
		}
		clearNumBuffer(tokensList, numBuffer.toString());
		return tokensList;
	}

	private void clearNumBuffer(LinkedList<Node> tokensList, String numBuffer)
	{
		if (!numBuffer.isEmpty())
			tokensList.add(new NumNode(Double.parseDouble(numBuffer)));
	}

	/**
	 * Converts the parsed tokens into postfix notation
	 * @return Converted tokens list in postfix notation
	 */
	private LinkedList<Node> infixToPostfix(List<Node> tokensList)
	{
		Stack<OperatorNode> valuesStack = new Stack<OperatorNode>();
		LinkedList<Node> result = new LinkedList<Node>();

		valuesStack.push(new ParenthesisNode(ExpressionChar.LEFT_PARENTHESIS));
		tokensList.add(new ParenthesisNode(ExpressionChar.RIGHT_PARENTHESIS)); //probably not thread-safe

		for (Node curNode : tokensList)
		{
			if (curNode instanceof OperandNode)
				result.add(curNode);
			else if (curNode instanceof OperatorNode && !(curNode instanceof ParenthesisNode))
			{
				boolean pushedAllOfSamePrec = false;
				OperatorNode curOpNode = (OperatorNode)curNode;

				while (!pushedAllOfSamePrec && !(valuesStack.peek() instanceof ParenthesisNode))
				{
					Operator currentOp = curOpNode.getOperator();
					Operator stackOp = valuesStack.peek().getOperator();

					if (currentOp.comparePrecedence(stackOp) < 0)
						pushedAllOfSamePrec = true;
					else
						result.add(valuesStack.pop());
				}
			}
			else if (curNode instanceof ParenthesisNode)
			{
				if (((ParenthesisNode)curNode).isLeft)
					valuesStack.push(new ParenthesisNode(ExpressionChar.LEFT_PARENTHESIS.getChar()));
				else
				{
					boolean foundLeftParen = false;
					while (!foundLeftParen)
					{
						OperatorNode curValueNode = valuesStack.peek();
						if (curValueNode instanceof ParenthesisNode)
						{
							valuesStack.pop();
							foundLeftParen = true;
						}
						else
							result.add(valuesStack.pop());
					}
				}
			}
		}

		return result;
	}

	/**
	 * Adds the current character into the tokens list as an operator if possible.
	 * Otherwise, return false.
	 *
	 * @param c      is the current character of the expression string
	 * @return If c was added into tokens, return true. Else, return false
	 */
	private boolean matchOperators(LinkedList<Node> tokensList, char c)
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
						tokensList.add(bNode);
						break;
					case UNARY:
						UnaryNode uNode = new UnaryNode(op);
						tokensList.add(uNode);
						break;
					case CALCULUS:
						CalculusNode cNode = new CalculusNode(op);
						tokensList.add(cNode);
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
	private class ParenthesisNode extends OperatorNode
	{
		private boolean isLeft;

		public ParenthesisNode(char parenthesis)
		{
			if (parenthesis == ExpressionChar.LEFT_PARENTHESIS.getChar())
				isLeft = true;
			else if (parenthesis == ExpressionChar.RIGHT_PARENTHESIS.getChar())
				isLeft = false;
			else
				throw new IllegalArgumentException("Only parentheses for parenthesis nodes");
		}

		public ParenthesisNode(ExpressionChar parenthesis)
		{
			if (parenthesis == ExpressionChar.LEFT_PARENTHESIS)
				isLeft = true;
			else if (parenthesis == ExpressionChar.RIGHT_PARENTHESIS)
				isLeft = false;
			else
				throw new IllegalArgumentException("Only parentheses for parenthesis nodes");
		}

		public boolean isLeftParenthesis()
		{
			return isLeft;
		}

		public String toString()
		{
			return isLeft ? "(" : ")";
		}

		/**
		 * Should not be called!
		 */
		@Override
		protected double eval(Map<Character, Double> env) {
			return 0;
		}
	}

}