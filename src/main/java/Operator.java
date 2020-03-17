
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An enum that stores the abbreviations and substitution character for all
 * supported operations.
 */
public enum Operator
{
	//Binary Operations
	ADDITION("+", (char) 43, OperationType.BINARY, (byte) 1),
	SUBTRACTION("-", (char) 45, OperationType.BINARY, (byte) 1),
	MULTIPLICATION("*", (char) 42, OperationType.BINARY, (byte) 2),
	DIVISION("/", (char) 47, OperationType.BINARY, (byte) 2),
	EXPONENTIAL("^", (char) 94, OperationType.BINARY, (byte) 3),

	//Unary Operations
	NEGATIVE("-", (char) 45, OperationType.UNARY, (byte) 3),
	SQUARE_ROOT("sqrt", (char) 208, OperationType.UNARY, (byte) 3) //TODO determine proper precedence for these operators
	{
		@Override
		public String toString()
		{
			return "SquareRoot";
		}
	},
	LOGARITHM("log", (char) 209, OperationType.UNARY, (byte) 3)
	{
		@Override
		public String toString()
		{
			return "Logarithm";
		}
	},

	//Calculus Operations
	DERIVATIVE("der", (char) 213, OperationType.CALCULUS, (byte) 4)
	{
		@Override
		public String toString()
		{
			return "Derivative";
		}
	},
	INTEGRAL("int", (char) 214, OperationType.CALCULUS, (byte) 4)
	{
		@Override
		public String toString()
		{
			return "Integral";
		}
	};

	private String abbrev;
	private char chr;
	private OperationType type;
	private byte precedence;
	Operator(String abbrev, char chr, OperationType type, byte precedence)
	{
		this.abbrev = abbrev;
		this.chr = chr;
		this.type = type;
		this.precedence = precedence;
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
			if (op.getType() != OperationType.BINARY && op != Operator.NEGATIVE)
				result.add(op);
		}
		return result;
	}

	/**
	 * @return a Character, Operator HashMap of all operators
	 */
	public static HashMap<Character, Operator> getRawCharList()
	{
		HashMap<Character, Operator> result = new HashMap<Character, Operator>();
		for (Operator op : values())
			result.put(op.chr, op);

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
		return chr;
	}

	/**
	 * Returns the type of operation, like Unary or Binary
	 */
	public OperationType getType()
	{
		return type;
	}

	public int comparePrecedence(Operator other)
	{
		return this.precedence - other.precedence;
	}

	/**
	 * Return the string representation of this enum.
	 */
	@Override
	public String toString()
	{
		return chr + "";
	}
}
