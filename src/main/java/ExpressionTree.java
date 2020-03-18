
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * This is the primary calculator tree
 */
public class ExpressionTree
{
	Node root;

	public ExpressionTree(String expression)
	{
		//TODO isPostfix boolean assumed to be true for now.
		ExpressionParser parser = new ExpressionParser(expression, true);
		LinkedList<Node> tokens = parser.getTokens();
		setTree(tokens);
	}

	public ExpressionTree(LinkedList<Node> tokens)
	{
		setTree(tokens);
	}

	private void setTree(LinkedList<Node> tokens)
	{
		Stack<Node> values = new Stack<Node>();
		for (Node n : tokens)
		{
			if (n instanceof NumNode || n instanceof VarNode)
			{
				values.push(n);
			}
			else if (n instanceof OperatorNode)
			{
				n.setRight(values.pop());
				n.setLeft(values.pop());
				values.push(n);
			}
		}
		root = values.pop();
	}

	public double evaluate(HashMap<Character, Double> variableValues)
	{
		return root.eval(variableValues);
	}

}
