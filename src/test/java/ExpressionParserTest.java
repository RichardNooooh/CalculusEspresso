import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
//NOTE if I want to utilize the @Tag, watch https://www.youtube.com/watch?v=0m-vtBB66cI&list=PLqq-6Pq4lTTa4ad5JISViSb2FVG8Vwa4o&index=25
@DisplayName("When running ExpressionParser")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExpressionParserTest
{
	ExpressionParser parser;

	@BeforeAll
	public void setUp()
	{
		parser = new ExpressionParser("");
	}

	@Nested
	@DisplayName("The substituteMultiCharOp method should a string with")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class substituteMultiCharOpTest
	{
		final char SQRT = Operator.SQUARE_ROOT.getChar();
		final char LOG = Operator.LOGARITHM.getChar();
		final char DER = Operator.DERIVATIVE.getChar();
		final char INT = Operator.INTEGRAL.getChar();

		Method method;

		@BeforeEach
		public void setUp() throws NoSuchMethodException
		{
			method = ExpressionParser.class.getDeclaredMethod("substituteMultiCharOp", String.class);
			method.setAccessible(true);
		}

		/**
		 * Since the invoke method is very long for lambdas, invokeExpr simply shortens the method call.
		 * @return String returned from invoked method
		 */
		private String invokeExpr(String expression) throws InvocationTargetException, IllegalAccessException
		{
			return (String) method.invoke(parser, expression);
		}

		@Test
		@DisplayName((char) 208 + " instead of sqrt")
		public void sqrtTest()
		{
			assertAll("should return substituted char in string with preserved spacing",
					() -> assertEquals(SQRT + "2", invokeExpr("sqrt2")),
					() -> assertEquals(SQRT + "(2)", invokeExpr("sqrt(2)")),
					() -> assertEquals(SQRT + " 2", invokeExpr("sqrt 2")),
					() -> assertEquals(SQRT + " (2)", invokeExpr("sqrt (2)")),
					() -> assertEquals("x" + SQRT + " 24x", invokeExpr("xsqrt 24x")),
					() -> assertEquals("x" + SQRT + "24x", invokeExpr("xsqrt24x")),
					() -> assertEquals("x " + SQRT + "24x", invokeExpr("x sqrt24x")),
					() -> assertEquals("x +" + SQRT + "24x", invokeExpr("x +sqrt24x")),
					() -> assertEquals("x * 3 +" + SQRT + "24+x", invokeExpr("x * 3 +sqrt24+x"))
			);
		}

		@Test
		@DisplayName((char) 209 + " instead of log")
		public void logTest()
		{
			assertAll("should return substituted char in string with preserved spacing",
					() -> assertEquals(LOG + "2", invokeExpr("log2")),
					() -> assertEquals(LOG + "(2)", invokeExpr("log(2)")),
					() -> assertEquals(LOG + " 2", invokeExpr("log 2")),
					() -> assertEquals(LOG + " (2)", invokeExpr("log (2)")),
					() -> assertEquals("x" + LOG + " 24x", invokeExpr("xlog 24x")),
					() -> assertEquals("x" + LOG + "24x", invokeExpr("xlog24x")),
					() -> assertEquals("x " + LOG + "24x", invokeExpr("x log24x")),
					() -> assertEquals("x +" + LOG + "24x", invokeExpr("x +log24x")),
					() -> assertEquals("x * 3 +" + LOG + "24+x", invokeExpr("x * 3 +log24+x"))
			);
		}

		@Test
		@DisplayName((char) 213 + " instead of der")
		public void derTest()
		{

		}

		@Test
		@DisplayName((char) 214 + " instead of int")
		public void intTest()
		{

		}

		@Disabled
		@Test
		@DisplayName("")
		public void gradTest()
		{

		}

	}
}