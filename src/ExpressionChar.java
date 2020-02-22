public enum ExpressionChar
{
	//Standard Characters
	ZERO((char) 48),
	NINE((char) 57),
	DECIMAL((char) 46),

	//Unary 208-212
	SQUARE_ROOT((char) 208),
	LOGARITHM((char) 209),

	//Calculus 213-218
	DERIVATIVE((char) 213),
	INTEGRAL((char) 214);


	private char replacementChar;
	ExpressionChar(char replacementChar)
	{
		this.replacementChar = replacementChar;
	}
}
