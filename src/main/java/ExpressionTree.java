
import core.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * This is the primary calculator tree
 */
public class ExpressionTree
{
	Node root;
	ExpressionParser parser;

	public ExpressionTree(String expression) {
		//TODO isPostfix boolean assumed to be true for now.
		parser = new ExpressionParser(expression, true);
		LinkedList<Node> tokens = parser.getTokens();
		root = setTree(tokens);
	}

	public ExpressionTree(LinkedList<Node> tokens)
	{
		setTree(tokens);
	}

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

					if (nextNode instanceof UnaryNode || nextNode instanceof CalculusNode)
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

	public double evaluate()
	{
		BigDecimal value = root.eval(parser.getVariableValueMap());
		return value.doubleValue();
	}

}
