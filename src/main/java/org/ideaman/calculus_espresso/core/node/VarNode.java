package org.ideaman.calculus_espresso.core.node;

import org.ideaman.calculus_espresso.exceptions.UndefinedVariableException;

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
	public BigDecimal eval(Map<String, BigDecimal> env) throws UndefinedVariableException {
		BigDecimal thisVal = env.get(variable + "");
		if (thisVal == null)
			throw new UndefinedVariableException("The variable: " + variable + " is not defined in the env.");
		return thisVal;
	}

	@Override
	public String toString()
	{
		return variable + "";
	}
}
