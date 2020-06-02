package node;

import java.util.Map;

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

	@Override
	public double eval(Map<String, Double> env)
	{
		double thisVal = env.get(variable + "");
		return thisVal;
	}

	@Override
	public String toString()
	{
		return variable + "";
	}
}
