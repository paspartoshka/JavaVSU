package org.example;

import java.util.*;

/**
 * Класс представляет собой калькулятор, который может вычислять
 * арифметические выражения включая сложение вычитание умножение и деление с поддержкой скобок
 *  и переменных
 */
public class Calculator {

    public static final Map<String, Double> variables = new HashMap<>();


    /**
     * Основной метод программы. Запрашивает у пользователя выражение и выводит результат
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String expression = scanner.nextLine();

        try {
            double result = evaluateExpression(expression);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Подготовка выржаения для вычисления, удаление проблеов
     * @param expression строка, содержащая выржаение
     * @return результат вычисления
     * @throws Exception если содержит ошибки
     */
    public static double evaluateExpression(String expression) throws Exception {
        expression = expression.replaceAll("\\s+", "");
        return evaluate(expression);
    }

    /**
     * Разбивает выражение на токены (числа, операторы, переменные)
     * @param expression
     * @return
     * @throws Exception
     */
    private static double evaluate(String expression) throws Exception {
        return parseExpression(new Tokenizer(expression));
    }


    /**
     * Парсит выражение с + -
     * @param tokenizer токены выражения
     * @return результат вычислений
     * @throws Exception если содержит ошибки
     */
    private static double parseExpression(Tokenizer tokenizer) throws Exception {
        double result = parseVar(tokenizer);
        while (true) {
            if (tokenizer.peek() != null && (tokenizer.peek().equals("+") || tokenizer.peek().equals("-"))) {
                String operator = tokenizer.next();
                double var = parseVar(tokenizer);
                if (operator.equals("+")) {
                    result += var;
                } else {
                    result -= var;
                }
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Парсит выражение с * /
     * @param tokenizer токены выражения
     * @return результат вычисления
     * @throws Exception ошибка
     */
    private static double parseVar(Tokenizer tokenizer) throws Exception {
        double result = parseBracket(tokenizer);
        while (true) {
            if (tokenizer.peek() != null && (tokenizer.peek().equals("*") || tokenizer.peek().equals("/"))) {
                String operator = tokenizer.next();
                double bracket = parseBracket(tokenizer);
                if (operator.equals("*")) {
                    result *= bracket;
                } else {
                    if (bracket == 0) {
                        throw new Exception("Деление на ноль");
                    }
                    result /= bracket;
                }
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Парсит выражение со скобками
     * @param tokenizer токены выражения
     * @return результат вычисления
     * @throws Exception ошибка
     */
    private static double parseBracket(Tokenizer tokenizer) throws Exception {
        if (tokenizer.peek() == null) {
            throw new Exception("Неожиданный конец выражения");
        }

        if (tokenizer.peek().equals("(")) {
            tokenizer.next(); // Пропускаем "("
            double result = parseExpression(tokenizer);
            if (!tokenizer.peek().equals(")")) {
                throw new Exception("Ожидается закрывающая скобка");
            }
            tokenizer.next(); // Пропускаем ")"
            return result;
        } else if (isNumber(tokenizer.peek())) {
            return Double.parseDouble(tokenizer.next());
        } else if (isVariable(tokenizer.peek())) {
            String variable = tokenizer.next();
            if (!variables.containsKey(variable)) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите значение переменной " + variable + ":");
                double value = scanner.nextDouble();
                variables.put(variable, value);
            }
            return variables.get(variable);
        } else {
            throw new Exception("Неожиданный токен: " + tokenizer.peek());
        }
    }

    /**
     *  Проверка является ли токен числом
     * @param token токен для проверки
     * @return результат проверки
     */
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверка является ли токен переменной
     * @param token токен для проверки
     * @return результат проверки
     */
    private static boolean isVariable(String token) {
        return token.matches("[a-zA-Z]+");
    }

    /**
     * Класс для разбиения на токены
     */
    static class Tokenizer {
        private final String expression;
        private int pos = 0;

        /**
         * Создает новый токенизатор для выражения
         * @param expression выражение
         */
        public Tokenizer(String expression) {
            this.expression = expression;
        }

        /**
         * Получает следующий токен из выражения
         * @return следующий токен или null
         */
        public String next() {
            if (pos >= expression.length()) {
                return null;
            }
            char ch = expression.charAt(pos);
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (pos < expression.length() && (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos) == '.')) {
                    sb.append(expression.charAt(pos++));
                }
                return sb.toString();
            } else if (Character.isLetter(ch)) {
                StringBuilder sb = new StringBuilder();
                while (pos < expression.length() && Character.isLetter(expression.charAt(pos))) {
                    sb.append(expression.charAt(pos++));
                }
                return sb.toString();
            } else {
                return String.valueOf(expression.charAt(pos++));
            }
        }

        /**
         * Возвращает следующий токен без перемещения позиции в выражении
         * @return следующий токен или null
         */
        public String peek() {
            if (pos >= expression.length()) {
                return null;
            }
            char ch = expression.charAt(pos);
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                int tempPos = pos;
                while (tempPos < expression.length() && (Character.isDigit(expression.charAt(tempPos)) || expression.charAt(tempPos) == '.')) {
                    sb.append(expression.charAt(tempPos++));
                }
                return sb.toString();
            } else if (Character.isLetter(ch)) {
                StringBuilder sb = new StringBuilder();
                int tempPos = pos;
                while (tempPos < expression.length() && Character.isLetter(expression.charAt(tempPos))) {
                    sb.append(expression.charAt(tempPos++));
                }
                return sb.toString();
            } else {
                return String.valueOf(expression.charAt(pos));
            }
        }
    }
}