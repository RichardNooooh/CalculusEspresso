import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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
		parser = new ExpressionParser("", true);
	}

	@Nested
	@DisplayName("The substituteMultiCharOp method should return a string with")
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
			assertAll("should return substituted char in string with preserved spacing",
					() -> assertEquals(DER + "2", invokeExpr("der2")),
					() -> assertEquals(DER + "(2)", invokeExpr("der(2)")),
					() -> assertEquals(DER + " 2", invokeExpr("der 2")),
					() -> assertEquals(DER + " (2)", invokeExpr("der (2)")),
					() -> assertEquals("x" + DER + " 24x", invokeExpr("xder 24x")),
					() -> assertEquals("x" + DER + "24x", invokeExpr("xder24x")),
					() -> assertEquals("x " + DER + "24x", invokeExpr("x der24x")),
					() -> assertEquals("x +" + DER + "24x", invokeExpr("x +der24x")),
					() -> assertEquals("x * 3 +" + DER + "24+x", invokeExpr("x * 3 +der24+x"))
			);
		}

		@Test
		@DisplayName((char) 214 + " instead of int")
		public void intTest()
		{
			assertAll("should return substituted char in string with preserved spacing",
					() -> assertEquals(INT + "2", invokeExpr("int2")),
					() -> assertEquals(INT + "(2)", invokeExpr("int(2)")),
					() -> assertEquals(INT + " 2", invokeExpr("int 2")),
					() -> assertEquals(INT + " (2)", invokeExpr("int (2)")),
					() -> assertEquals("x" + INT + " 24x", invokeExpr("xint 24x")),
					() -> assertEquals("x" + INT + "24x", invokeExpr("xint24x")),
					() -> assertEquals("x " + INT + "24x", invokeExpr("x int24x")),
					() -> assertEquals("x +" + INT + "24x", invokeExpr("x +int24x")),
					() -> assertEquals("x * 3 +" + INT + "24+x", invokeExpr("x * 3 +int24+x"))
			);
		}

		@Disabled
		@Test
		@DisplayName("")
		public void gradTest()
		{

		}

	}

	@Nested
	@DisplayName("The parse method should return a LinkedList of Nodes")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class parseToTokensTest
	{
		Method method;

		@BeforeEach
		public void setUp() throws NoSuchMethodException
		{
			method = ExpressionParser.class.getDeclaredMethod("parseToTokens", String.class);
			method.setAccessible(true);
		}

		/**
		 * Since the invoke method is very long for lambdas, invokeExpr simply shortens the method call.
		 * @return String returned from invoked method
		 */
		private String invokeExpr(String expression) throws InvocationTargetException, IllegalAccessException
		{
			return method.invoke(parser, expression).toString();
		}

		@TestFactory
		@DisplayName("When given an expression String with no whitespace")
		public Stream<DynamicTest> additiveParseTest()
		{
			List<String> inputExpressionList = Arrays.asList(
					"a+b+3-2",
					"(-3)+1-4-5",
					"169-5+3+4+52-60+(-3)",
					"-1-1-1-1-1-1",
					"-1+-1-1+-1-1--1",
					"1.00+3+5.09-2+(-4.165)",
					""
			);

			List<String> expectedList = Arrays.asList(
					"[a, +, b, +, 3.0, -, 2.0]",
					"[(, -3.0, ), +, 1.0, -, 4.0, -, 5.0]",
					"[169.0, -, 5.0, +, 3.0, +, 4.0, +, 52.0, -, 60.0, +, (, -3.0, )]",
					"[-1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0]",
					"[-1.0, +, -1.0, -, 1.0, +, -1.0, -, 1.0, -, -1.0]",
					"[1.0, +, 3.0, +, 5.09, -, 2.0, +, (, -4.165, )]",
					"[]"
			);

			Iterator<String> inputGen = inputExpressionList.iterator();
			Function<String, String> displayNameGen = (input) -> "Input: " + input;

			ThrowingConsumer<String> testExecutor = (input) ->
			{
				int i = inputExpressionList.indexOf(input);
				assertEquals(expectedList.get(i), invokeExpr(input));
			};

			return DynamicTest.stream(inputGen, displayNameGen, testExecutor);
		}

		@TestFactory
		@DisplayName("When given an expression String with even spacing")
		public Stream<DynamicTest> additiveEvenSpaceParseTest()
		{
			List<String> inputExpressionList = Arrays.asList(
					"a + b + 3 - 2",
					"( -3 ) + 1 - 4 - 5",
					"169 - 5 + 3 + 4 + 52 - 60 + ( -3 )",
					"-1 - 1 - 1 - 1 - 1 - 1",
					"-1 + -1 - 1 + -1 - 1 - -1",
					"1.00 + 3 + 5.09 - 2 + ( -4.165 )",
					" "
			);

			List<String> expectedList = Arrays.asList(
					"[a, +, b, +, 3.0, -, 2.0]",
					"[(, -3.0, ), +, 1.0, -, 4.0, -, 5.0]",
					"[169.0, -, 5.0, +, 3.0, +, 4.0, +, 52.0, -, 60.0, +, (, -3.0, )]",
					"[-1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0]",
					"[-1.0, +, -1.0, -, 1.0, +, -1.0, -, 1.0, -, -1.0]",
					"[1.0, +, 3.0, +, 5.09, -, 2.0, +, (, -4.165, )]",
					"[]"
			);

			Iterator<String> inputGen = inputExpressionList.iterator();
			Function<String, String> displayNameGen = (input) -> "Input: " + input;

			ThrowingConsumer<String> testExecutor = (input) ->
			{
				int i = inputExpressionList.indexOf(input);
				assertEquals(expectedList.get(i), invokeExpr(input));
			};

			return DynamicTest.stream(inputGen, displayNameGen, testExecutor);
		}

		@TestFactory
		@DisplayName("When given an expression String with uneven spacing")
		public Stream<DynamicTest> additiveUnevenSpaceParseTest()
		{
			List<String> inputExpressionList = Arrays.asList(
					"a    +  b  + 3- 2",
					"(      -   3 ) + 1 -    4  - 5",
					"     169      - 5 + 3+ 4 + 52-60 + (-3 )",
					"-1 - 1 - 1   - 1 -1-1",
					"-1 + -1- 1   +     -1 - 1 --1",
					"1.00 + 3 + 5.09- 2 +(-4.165 )",
					"          "
			);

			List<String> expectedList = Arrays.asList(
					"[a, +, b, +, 3.0, -, 2.0]",
					"[(, -3.0, ), +, 1.0, -, 4.0, -, 5.0]",
					"[169.0, -, 5.0, +, 3.0, +, 4.0, +, 52.0, -, 60.0, +, (, -3.0, )]",
					"[-1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0]",
					"[-1.0, +, -1.0, -, 1.0, +, -1.0, -, 1.0, -, -1.0]",
					"[1.0, +, 3.0, +, 5.09, -, 2.0, +, (, -4.165, )]",
					"[]"
			);

			Iterator<String> inputGen = inputExpressionList.iterator();
			Function<String, String> displayNameGen = (input) -> "Input: " + input;

			ThrowingConsumer<String> testExecutor = (input) ->
			{
				int i = inputExpressionList.indexOf(input);
				assertEquals(expectedList.get(i), invokeExpr(input));
			};

			return DynamicTest.stream(inputGen, displayNameGen, testExecutor);
		}

		@TestFactory
		@DisplayName("When given an expression String with extreme whitespacing")
		public Stream<DynamicTest> additiveBizarreSpaceParseTest()
		{
			List<String> inputExpressionList = Arrays.asList(
					"a   \n +  b \t + 3- 2",
					"(      -   3 ) \n+ 1 - \t\t   4  - 5",
					"    \n 169 \t\t\t     - 5 + 3+ 4 + \t\t52\t-\t60 + (-3 )",
					"-1 - 1 - 1   - 1 -1\t-1",
					"-1 +\n -1- 1   +     -1 - 1 --\t1",
					"1.00 + \n3 + \n5.09- 2 +(-4.165 \t)",
					"  \t\t\t\n\n\t        "
			);

			List<String> expectedList = Arrays.asList(
					"[a, +, b, +, 3.0, -, 2.0]",
					"[(, -3.0, ), +, 1.0, -, 4.0, -, 5.0]",
					"[169.0, -, 5.0, +, 3.0, +, 4.0, +, 52.0, -, 60.0, +, (, -3.0, )]",
					"[-1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0, -, 1.0]",
					"[-1.0, +, -1.0, -, 1.0, +, -1.0, -, 1.0, -, -1.0]",
					"[1.0, +, 3.0, +, 5.09, -, 2.0, +, (, -4.165, )]",
					"[]"
			);

			Iterator<String> inputGen = inputExpressionList.iterator();
			Function<String, String> displayNameGen = (input) -> "Input: " + input;

			ThrowingConsumer<String> testExecutor = (input) ->
			{
				int i = inputExpressionList.indexOf(input);
				assertEquals(expectedList.get(i), invokeExpr(input));
			};

			return DynamicTest.stream(inputGen, displayNameGen, testExecutor);
		}

	}

	@Nested
	@DisplayName("The infixToPostFix method should return a postfix expression")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class infixToPostFixTest
	{
		//LinkedList<Node> tokens;
		Method method;

		@BeforeEach
		public void setUp() throws NoSuchMethodException
		{
			method = ExpressionParser.class.getDeclaredMethod("infixToPostfix", List.class);
			method.setAccessible(true);
		}

		/**
		 * Since the invoke method is very long for lambdas, invokeExpr simply shortens the method call.
		 * @return String returned from invoked method
		 */
		private String invokeExpr(String expression) throws InvocationTargetException, IllegalAccessException
		{
			return method.invoke(parser, expression).toString();
		}
	}



}