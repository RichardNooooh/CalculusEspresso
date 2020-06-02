package node;

import exceptions.UndefinedVariable;

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
	public double eval(Map<String, Double> env) throws UndefinedVariable {
		Double thisVal = env.get(variable + "");
		if (thisVal == null)
			throw new UndefinedVariable("The variable: " + variable + " is not defined in the env.");
		return thisVal;
	}

	@Override
	public String toString()
	{
		return variable + "";
	}
}
