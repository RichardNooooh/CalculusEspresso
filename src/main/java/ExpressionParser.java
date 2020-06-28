
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

import exceptions.MissingInputException;
import node.*;

/**
 * Parses a raw expression into tokens
 */
public class ExpressionParser
{
	private LinkedList<Node> tokens;
	private HashMap<String, BigDecimal> variableValueMap;

	/**
	 * Constructs a new ExpressionParser
	 */
	public ExpressionParser(String inputString, boolean isPostfix)
	{
		String[] splitExpression = inputString.split(" ~~ ");
		variableValueMap = new HashMap<String, BigDecimal>();
		String expression = splitExpression[0];

		expression = substituteMultiCharOp(expression);
		tokens = parseToTokens(expression);

		if (isPostfix)
			tokens = infixToPostfix(tokens);

		if (splitExpression.length == 2)
		{
			String variableString = splitExpression[1];
			String[] variableList = variableString.split(" ");
			for (String variableValueString : variableList)
			{
				if (variableValueString.length() > 0)
				{
					String[] variableValuePair = variableValueString.split("=");
					String variable = variableValuePair[0];
					BigDecimal value = BigDecimal.valueOf(Double.parseDouble(variableValuePair[1]));
					variableValueMap.put(variable, value);
				}
			}
		}
	}

	public LinkedList<Node> getTokens()
	{
		return tokens;
	}

	public HashMap<String, BigDecimal> getVariableValueMap()
	{
		return variableValueMap;
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
	 * Returns a new LinkedList of token Nodes from an expression String.
	 *
	 * @param expression String that represents a mathematical expression
	 * @return LinkedList of token nodes
	 */
	private LinkedList<Node> parseToTokens(String expression)
	{
		LinkedList<Node> tokensList = new LinkedList<Node>();
		tokensList = initialTokenizer(tokensList, expression);
		tokensList = addUnaryNegativeOperators(tokensList);
		tokensList = addImplicitMultiplication(tokensList);

		return tokensList;
	}

	/**
	 * Produces the initial list of token Nodes for further processing.
	 *
	 * @param tokensList is a blank LinkedList<node.Node>
	 * @param expression is the original mathematical expression String
	 * @return a raw LinkedList of token Nodes
	 */
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
				if (ExpressionChar.isNumber(c))
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
						boolean isOperator = false;
						i = matchCalculusOperators(tokensList, expressionCharArray, i);
						isOperator = matchUnaryBinaryOperators(tokensList, c);
						if (!isOperator)
							tokensList.add(new VarNode(c));
					}
				}
			}
		}
		clearNumBuffer(tokensList, numBuffer.toString());
		return tokensList;
	}

	/**
	 * Adds a new node.NumNode based on what is inside the numBuffer String
	 *
	 * @param tokensList is a LinkedList of Nodes that is currently being processed by
	 *                   initialTokenizer()
	 * @param numBuffer is a buffer String to be converted into a node.NumNode
	 */
	private void clearNumBuffer(LinkedList<Node> tokensList, String numBuffer)
	{
		if (!numBuffer.isEmpty())
			tokensList.add(new NumNode(new BigDecimal(numBuffer)));
	}

	/**
	 * Adds the current character into the tokens list as a calculus node if possible.
	 *
	 * @param tokensList is the list that is added to if the current character is an operator
	 * @param expressionCharArray is the expression
	 * @param i is the index that the initial tokenizer method is currently on
	 * @return The new index for i after iterating through the calculus input. Adds the calculus node into tokensList
	 * 		   if possible.
	 */
	private int matchCalculusOperators(LinkedList<Node> tokensList, char[] expressionCharArray, int i)
	{
		final int EXPRESSION_LENGTH = expressionCharArray.length;

		char c = expressionCharArray[i];
		Operator op = Operator.getOperator(c);
		if (op != null && op.getType() == OperationType.CALCULUS)
		{
			i++;
			if (expressionCharArray[i++] != '[')
				throw new MissingInputException("Calculus operators like \"der\" require an input.)");

			int sizeOfInputArray = findSizeOfCalculusInput(expressionCharArray, i);
			char[] calculusInputArray = new char[sizeOfInputArray];

			int indexInCalculusInput = 0;
			while (i < EXPRESSION_LENGTH && expressionCharArray[i] != ']')
				calculusInputArray[indexInCalculusInput++] = expressionCharArray[i++];

			String calculusInputString = new String(calculusInputArray).strip();
			switch(op)
			{
				case DERIVATIVE:
					tokensList.add(new CalculusNode(Operator.DERIVATIVE, calculusInputString));
					break;
				case INTEGRAL:
					tokensList.add(new CalculusNode(Operator.INTEGRAL, calculusInputString));
					break;
			}
		}
		return i;
	}

	/**
	 * Determines the true size of the calculus parameter input.
	 * @param expressionCharArray is the expression
	 * @param i is the index that the tokenizer is currently on
	 * @return
	 *
	 * @throws MissingInputException if there is not a closing ']' on the input.
	 */
	private int findSizeOfCalculusInput(char[] expressionCharArray, int i)
	{
		final int expressionLength = expressionCharArray.length;
		int result = 0;
		int tempI = i;
		while (tempI < expressionLength && expressionCharArray[tempI] != ']')
		{
			result++;
			tempI++;
		}

		if (tempI == expressionLength && expressionCharArray[tempI - 1] != ']')
			throw new MissingInputException("Calculus input is missing a \"]\".");
		return result;
	}

	/**
	 * Adds the current character into the tokens list as a binary/unary operator if possible
	 * and return true. Otherwise, return false.
	 *
	 * @param c is the current character of the expression string
	 * @return If c was added into tokens, return true. Else, return false
	 */
	private boolean matchUnaryBinaryOperators(LinkedList<Node> tokensList, char c)
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
//					case CALCULUS:
//						CalculusNode cNode = new CalculusNode(op);
//						tokensList.add(cNode);
//						break;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a new LinkedList of token Nodes after iterating through the list twice.
	 * This method first iterates through to identify the UnaryNegative nodes,
	 * then iterates through to produce negative NumNodes.
	 *
	 * @param tokensList is a list of token Nodes from initialTokenizer()
	 * @return a contracted token Nodes list with Unary Negative OperatorNodes
	 */
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

	/**
	 * Implicit multiplication should be added in the following situations:
	 * - {node.VarNode}, {node.NumNode}
	 * - {node.NumNode}, {node.VarNode}
	 * - {node.NumNode/node.VarNode}, {node.UnaryNode/node.CalculusNode}
	 * - {node.VarNode}, {node.VarNode}
	 * - {node.NumNode/node.VarNode}, {ParenthesisNode.left}
	 *
	 * TODO All other implicit multiplication situations will be considered illegal and throw
	 * an Exception.
	 *
	 * @param tokensList is an infix notation tokens list
	 * @return a new LinkedList of Nodes with implicit multiplication converted to explicit
	 */
	private LinkedList<Node> addImplicitMultiplication(LinkedList<Node> tokensList)
	{
		final int tokenListSize = tokensList.size();
		LinkedList<Node> resultList = new LinkedList<Node>();
		for (int i = 0; i < tokenListSize; i++)
		{
			Node thisNode = tokensList.get(i);
			resultList.add(thisNode);

			if (i != tokenListSize - 1)
			{
				if (thisNode instanceof NumNode)
				{
					Node nextNode = tokensList.get(i + 1);
					if (nextNode instanceof VarNode || nextNode instanceof UnaryNode //TODO consider having Unary/Calculus Nodes extend some FunctionNode
													|| nextNode instanceof CalculusNode
													|| (nextNode instanceof ParenthesisNode && ((ParenthesisNode)nextNode).isLeftParenthesis()))
					{
						resultList.add(new BinaryNode(Operator.MULTIPLICATION));
					}
				}
				else if (thisNode instanceof VarNode)
				{
					Node nextNode = tokensList.get(i + 1);
					if (nextNode instanceof OperandNode || nextNode instanceof UnaryNode
														|| nextNode instanceof CalculusNode
														|| (nextNode instanceof ParenthesisNode && ((ParenthesisNode)nextNode).isLeftParenthesis()))
					{
						resultList.add(new BinaryNode(Operator.MULTIPLICATION));
					}
				}
			}
		}

		return resultList;
	}

	/**
	 * Converts the complete processed infix notation tokensList into postFix notation
	 *
	 * @param tokensList is an infix notation list of token Nodes
	 * @return a new LinkedList of Nodes in postfix notation
	 */
	private LinkedList<Node> infixToPostfix(List<Node> tokensList)
	{
		Stack<OperatorNode> valuesStack = new Stack<OperatorNode>();
		LinkedList<Node> result = new LinkedList<Node>();

		valuesStack.push(new ParenthesisNode(ExpressionChar.LEFT_PARENTHESIS));
		tokensList.add(new ParenthesisNode(ExpressionChar.RIGHT_PARENTHESIS)); //probably not thread-safe

		Iterator tokensIterator = tokensList.iterator();
		while (tokensIterator.hasNext())
		{
			Node curNode = (Node) tokensIterator.next();

			if (curNode instanceof OperandNode)
				result.add(curNode);
			else if (curNode instanceof BinaryNode)
			{
				boolean pushedAllOfSamePrec = false;
				OperatorNode curOpNode = (OperatorNode)curNode;

				while (!pushedAllOfSamePrec && !(valuesStack.peek() instanceof ParenthesisNode))
				{
					Operator currentOp = curOpNode.getOperator();
					Operator stackOp = valuesStack.peek().getOperator();

					int comparison = currentOp.comparePrecedence(stackOp);
					if (comparison < 0 || comparison == 0 && currentOp.isLeftAssociative())
						result.add(valuesStack.pop());
					else
						pushedAllOfSamePrec = true;
				}
				valuesStack.push(curOpNode);
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
			else if (curNode instanceof UnaryNode || curNode instanceof CalculusNode)
			{
				LinkedList<Node> innerExpression = new LinkedList<Node>();
				int leftParenCounter = 0;
				boolean foundInnerExpression = false;
				while (!foundInnerExpression)
				{
					Node nextNode = (Node) tokensIterator.next();
					if (nextNode instanceof ParenthesisNode)
					{
						if (((ParenthesisNode)nextNode).isLeftParenthesis())
							leftParenCounter++;
						else
						{
							leftParenCounter--;
							if (leftParenCounter == 0)
								foundInnerExpression = true;
						}
					}

					innerExpression.add(nextNode);
				}
				LinkedList<Node> postFixInnerExpression = infixToPostfix(innerExpression);
				result.add(new WallNode());
				result.addAll(postFixInnerExpression);
				result.add(curNode);
			}
		}

		return result;
	}

	/**
	 * Represents the parenthesis in the expression. It should not be used in the Expression Tree, only for
	 * parsing the expressions.
	 */
	private class ParenthesisNode extends OperatorNode
	{
		private boolean isLeft;

		/**
		 * Constructs a new ParenthesisNode object from a char
		 * @param parenthesis is a parenthesis character
		 */
		public ParenthesisNode(char parenthesis)
		{
			if (parenthesis == ExpressionChar.LEFT_PARENTHESIS.getChar())
				isLeft = true;
			else if (parenthesis == ExpressionChar.RIGHT_PARENTHESIS.getChar())
				isLeft = false;
			else
				throw new IllegalArgumentException("Only parentheses for parenthesis nodes");
		}

		/**
		 * Constructs a new ParenthesisNode object from a ExpressionChar
		 * @param parenthesis is a ExpressionChar parenthesis enum
		 */
		public ParenthesisNode(ExpressionChar parenthesis)
		{
			if (parenthesis == ExpressionChar.LEFT_PARENTHESIS)
				isLeft = true;
			else if (parenthesis == ExpressionChar.RIGHT_PARENTHESIS)
				isLeft = false;
			else
				throw new IllegalArgumentException("Only parentheses for parenthesis nodes");
		}

		/**
		 * @return the type of parenthesis this ParenthesisNode encapsulates
		 */
		public boolean isLeftParenthesis()
		{
			return isLeft;
		}

		/**
		 * @return a left or right parenthesis depending on the isLeft boolean
		 */
		public String toString()
		{
			return isLeft ? "(" : ")";
		}

		/**
		 * Should not be called!
		 * A ParenthesisNode should not exist in the ExpressionTree
		 */
		@Override
		public BigDecimal eval(Map<String, BigDecimal> env)
		{
			throw new IllegalCallerException("ParenthesisNode's eval() method should not be called.");
		}
	}

}