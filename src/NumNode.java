/**
 * Represents the values in the expression tree
 */
public class NumNode extends OperandNode
{
	//left and right nodes must be null
	private double val;

	/**
	 * Constructs a new NumNode
	 */
	public NumNode(double value)
	{
		val = value;
	}

	/**
	 * @return the value this node contains
	 */
	public double getVal()
	{
		return val;
	}

	/**
	 * Returns the value of this node as a String
	 */
	@Override
	public String toString()
	{
		return val + "";
	}
}
