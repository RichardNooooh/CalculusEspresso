package Main;

/**
 * Represents all variables in the expression tree
 */
public class VarNode extends OperandNode
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
