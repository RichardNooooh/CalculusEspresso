public class VarNode extends Node
{
	char variable;

	public VarNode(char variable)
	{
		this.variable = variable;
	}

	public String toString()
	{
		return variable + "";
	}
}
