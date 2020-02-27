import java.util.ArrayList;

public enum Operator
{
	//Binary Operations
	ADDITION("+", ExpressionChar.ADDITION, OperationType.BINARY),
	SUBTRACTION("-", ExpressionChar.SUBTRACTION, OperationType.BINARY),
	MULTIPLICATION("*", ExpressionChar.MULTIPLICATION, OperationType.BINARY),
	DIVISION("/", ExpressionChar.DIVISION, OperationType.BINARY),
	EXPONENTIAL("^", ExpressionChar.EXPONENTIAL, OperationType.BINARY),

	//Unary Operations
	SQUARE_ROOT("sqrt", ExpressionChar.SQUARE_ROOT, OperationType.UNARY)
	{
		@Override
		public String toString()
		{
			return "SquareRoot";
		}
	},
	LOGARITHM("log", ExpressionChar.LOGARITHM, OperationType.UNARY)
	{
		@Override
		public String toString()
		{
			return "Logarithm";
		}
	},

	//Calculus Operations
	DERIVATIVE("der", ExpressionChar.DERIVATIVE, OperationType.CALCULUS)
	{
		@Override
		public String toString()
		{
			return "Derivative";
		}
	},
	INTEGRAL("int", ExpressionChar.INTEGRAL, OperationType.CALCULUS)
	{
		@Override
		public String toString()
		{
			return "Integral";
		}
	};

	private String abbrev;
	private ExpressionChar chr;
	private OperationType type;
	Operator(String abbrev, ExpressionChar chr, OperationType type)
	{
		this.abbrev = abbrev;
		this.chr = chr;
		this.type = type;
	}

	public static ArrayList<Operator> getMultiCharList()
	{
		ArrayList<Operator> result = new ArrayList<Operator>();
		for (Operator op : values())
		{
			if (op.getType() != OperationType.BINARY)
				result.add(op);
		}
		return result;
	}

	public String getAbbrev()
	{
		return abbrev;
	}

	public char getChar()
	{
		return chr.getChar();
	}

	public OperationType getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return chr.toString();
	}
}
