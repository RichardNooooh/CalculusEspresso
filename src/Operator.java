import java.util.ArrayList;

/**
 * An enum that stores the abbreviations and substitution character for all
 * supported operations.
 */
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

	/**
	 * Return an ArrayList of all operators with multiple characters.
	 * Namely the UnaryOperators like 'sqrt' and CalculusOperators like
	 * 'der' or derivative.
	 */
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

	/**
	 * Return the representation "abbreviation" of each operator.
	 * Like 'der' for derivative, 'sqrt' for square root, and etc.
	 */
	public String getAbbrev()
	{
		return abbrev;
	}

	/**
	 * Return the substitution character for the operator.
	 * Each substitution character is represented by the ExpressionChar
	 * enum.
	 */
	public char getChar()
	{
		return chr.getChar();
	}

	/**
	 * Returns the type of operation, like Unary or Binary
	 */
	public OperationType getType()
	{
		return type;
	}

	/**
	 * Return the string representation of this enum.
	 */
	@Override
	public String toString()
	{
		return chr.toString();
	}
}
