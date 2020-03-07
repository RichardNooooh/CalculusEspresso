

import java.util.LinkedList;
import java.util.Stack;

public class ExpressionTree
{
	Node root;

	public ExpressionTree(LinkedList<Node> tokens)
	{
		setTree(tokens);
		System.out.println("Done");
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

}
