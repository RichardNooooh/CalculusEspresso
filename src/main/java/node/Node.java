package node;

import java.util.Map;

/**
 * Represents the superclass of all node.Node objects
 */
public abstract class Node
{
	Node left;
	Node right;
	//I could do without this?
	Node parent;

	public void setLeft(Node left)
	{
		this.left = left;
	}

	public void setRight(Node right)
	{
		this.right = right;
	}

	public abstract double eval(Map<String, Double> env); //TODO do I need... this env parameter? Can I factor it out?

	//TODO add general tree traversal methods
}
