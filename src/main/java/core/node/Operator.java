package core.node;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An enum that stores the abbreviations and substitution character for all
 * supported operations.
 */
public enum Operator
{
	//Binary Operations
	ADDITION("+", (char) 43, OperationType.BINARY, (byte) 1, true),
	SUBTRACTION("-", (char) 45, OperationType.BINARY, (byte) 1, true),
	MULTIPLICATION("*", (char) 42, OperationType.BINARY, (byte) 2, true),
	DIVISION("/", (char) 47, OperationType.BINARY, (byte) 2, true),
	EXPONENTIAL("^", (char) 94, OperationType.BINARY, (byte) 3, false),

	//Unary Operations
	NEGATIVE("-", (char) 45, OperationType.UNARY, (byte) 3, true),
	SQUARE_ROOT("sqrt", (char) 208, OperationType.UNARY, (byte) 3, true) //TODO determine proper precedence for these operators
	{
		@Override
		public String toString()
		{
			return "SquareRoot";
		}
	},
	LOGARITHM("log", (char) 209, OperationType.UNARY, (byte) 3, true)
	{
		@Override
		public String toString()
		{
			return "Logarithm";
		}
	},

	//Calculus Operations
	DERIVATIVE("der", (char) 213, OperationType.CALCULUS, (byte) 4, true)
	{
		@Override
		public String toString()
		{
			return "Derivative";
		}
	},
	INTEGRAL("int", (char) 214, OperationType.CALCULUS, (byte) 4, true)
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
	private boolean isLeftAssociative;
	Operator(String abbrev, char chr, OperationType type, byte precedence, boolean isLeftAssociative)
	{
		this.abbrev = abbrev;
		this.chr = chr;
		this.type = type;
		this.precedence = precedence;
		this.isLeftAssociative = isLeftAssociative;
	}

	public boolean isLeftAssociative()
	{
		return isLeftAssociative;
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
	 * @return a Character, node.Operator HashMap of all operators
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
	 * Each substitution character is represented by the node.ExpressionChar
	 * enum.
	 */
	public char getChar()
	{
		return chr;
	}

	/**
	 * Return the Operator for the given character
	 * Null, if there is no operator for the given character
	 */
	public static Operator getOperator(char c)
	{
		for (Operator op : values())
		{
			if (op.getChar() == c)
				return op;
		}
		return null;
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
