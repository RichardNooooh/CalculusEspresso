package node;

import exceptions.UndefinedVariable;

import java.math.BigDecimal;
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
	public BigDecimal eval(Map<String, BigDecimal> env) throws UndefinedVariable {
		BigDecimal thisVal = env.get(variable + "");
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
