package org.ideaman.calculus_espresso.core.node;

/**
 * Represents all operations in the expression tree
 */
public abstract class OperatorNode extends Node
{
	Operator operator;

	/**
	 * @return the operation that this node contains
	 */
	public Operator getOperator()
	{
		return operator;
	}

	/**
	 * @return the operator as a string
	 */
	@Override
	public String toString()
	{
		return operator.toString();
	}

}
