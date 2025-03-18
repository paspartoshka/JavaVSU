import org.example.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Юнит тесты для калькулятора
 */
public class CalculatorTest {


    @Test
    void testEvaluateExpression_SimpleAddition() throws Exception {
        double result = Calculator.evaluateExpression("2 + 3");
        assertEquals(5.0, result, 0.0001);
    }

    @Test
    void testEvaluateExpression_SimpleSubtraction() throws Exception {
        double result = Calculator.evaluateExpression("5 - 3");
        assertEquals(2.0, result, 0.0001);
    }

    @Test
    void testEvaluateExpression_SimpleMultiplication() throws Exception {
        double result = Calculator.evaluateExpression("2 * 3");
        assertEquals(6.0, result, 0.0001);
    }

    @Test
    void testEvaluateExpression_SimpleDivision() throws Exception {
        double result = Calculator.evaluateExpression("6 / 3");
        assertEquals(2.0, result, 0.0001);
    }

    @Test
    void testEvaluateExpression_ComplexExpression() throws Exception {
        double result = Calculator.evaluateExpression("2 + 3 * 4 - 6 / 2");
        assertEquals(11.0, result, 0.0001);
    }

    @Test
    void testEvaluateExpression_WithParentheses() throws Exception {
        double result = Calculator.evaluateExpression("(2 + 3) * 4");
        assertEquals(20.0, result, 0.0001);
    }

    @Test
    void testEvaluateExpression_WithVariables() throws Exception {
        // Переменная x будет запрошена у пользователя, поэтому для теста зададим её значение
        Calculator.variables.put("x", 5.0);
        double result = Calculator.evaluateExpression("x * 2 + 3");
        assertEquals(13.0, result, 0.0001);
    }

    @Test
    void testEvaluateExpression_DivisionByZero() {
        Exception exception = assertThrows(Exception.class, () -> {
            Calculator.evaluateExpression("5 / 0");
        });
        assertEquals("Деление на ноль", exception.getMessage());
    }

    @Test
    void testEvaluateExpression_InvalidExpression() {
        Exception exception = assertThrows(Exception.class, () -> {
            Calculator.evaluateExpression("2 + ");
        });
        assertEquals("Неожиданный конец выражения", exception.getMessage());
    }



    @Test
    void testEvaluateExpression_EmptyExpression() {
        Exception exception = assertThrows(Exception.class, () -> {
            Calculator.evaluateExpression("");
        });
        assertEquals("Неожиданный конец выражения", exception.getMessage());
    }
}
