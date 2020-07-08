package org.ideaman.calculus_espresso;

import org.ideaman.calculus_espresso.core.*;
import org.ideaman.calculus_espresso.core.node.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * This is the primary calculator tree
 */
final public class CalculusEspresso
{
	Node root;
	ExpressionParser parser;

	/**
	 * Constructs a new CalculusEspresso object
	 * @param expression is the String to build the expression tree
	 */
	public CalculusEspresso(String expression)
	{
		//TODO isPostfix boolean assumed to be true for now.
		parser = new ExpressionParser(expression, true);
		LinkedList<Node> tokens = parser.getTokens();
		root = setTree(tokens);
	}

	/**
	 * Constructs the expression tree
	 * @param tokens is the list of operators and operands in postfix notation
	 * @return the root node of the tree
	 */
	private Node setTree(LinkedList<Node> tokens)
	{
		Stack<Node> values = new Stack<Node>();

		Iterator<Node> tokenIterator = tokens.iterator();
		while (tokenIterator.hasNext())
		{
			Node n = tokenIterator.next();

			if (n instanceof OperandNode)
				values.push(n);
			else if (n instanceof BinaryNode)
			{
				n.setRight(values.pop());
				n.setLeft(values.pop());
				values.push(n);
			}
			else if (n instanceof WallNode)
			{
				//n must be Unary/Calculus node.Node
				//assume wall notation implementation
				LinkedList<Node> innerExpression = new LinkedList<Node>();
				Node nextNode = null;
				Node innerRoot = null;
				boolean foundEndOfInnerExpression = false;
				int innerExpressionsCounter = 1;

				while (!foundEndOfInnerExpression && tokenIterator.hasNext()) //TODO implement an exception here instead of checking if tokenIterator has next
				{
					nextNode = tokenIterator.next();
					innerExpression.add(nextNode);

					if (nextNode instanceof FunctionNode)
					{
						innerExpressionsCounter--;
						if (innerExpressionsCounter == 0)
						{
							foundEndOfInnerExpression = true;
							innerRoot = setTree(innerExpression);
						}
					}
					else if (nextNode instanceof WallNode)
						innerExpressionsCounter++;
				}

				nextNode.setRight(innerRoot);
				values.push(nextNode);
			}
		}
		return values.pop();
	}

	/**
	 * Evaluate the expression based on initial values
	 * @return the value of the expression at the initial variable values, if applicable
	 * @throws org.ideaman.calculus_espresso.exceptions.UndefinedVariableException if the map is empty and needs those variables
	 */
	public double evaluate()
	{
		BigDecimal value = root.eval(parser.getVariableValueMap());
		return value.doubleValue();
	}

	/**
	 * Evaluate the expression based on given values
	 * @param variableMap is a map containing the variables and their corresponding BigDecimal values
	 * @return the value of the expression at the given variable values, if applicable
	 */
	public double evaluate(Map<String, BigDecimal> variableMap)
	{
		BigDecimal value = root.eval(variableMap);
		return value.doubleValue();
	}

}
