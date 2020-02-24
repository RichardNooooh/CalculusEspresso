public class NumNode extends OperandNode
{
	//left and right nodes must be null
	private double val;

	public NumNode(double value)
	{
		val = value;
	}

	@Override
	public String toString()
	{
		return val + "";
	}
}
