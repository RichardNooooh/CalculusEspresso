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
