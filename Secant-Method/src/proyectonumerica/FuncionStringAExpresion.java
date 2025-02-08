
package proyectonumerica;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Osniel
 */


public class FuncionStringAExpresion {

    private String expresionYString;
    private List<Token> tokens;

    public FuncionStringAExpresion(String expresionYString) {
        this.expresionYString = expresionYString;
        try {
            this.tokens = tokenize(expresionYString);
        } catch (IllegalArgumentException e) {
            System.err.println("Error en la expresión: " + e.getMessage());
            this.tokens = null;
        }
    }

    private List<Token> tokenize(String expression) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                currentNumber.append(c);
            } else {
                if (currentNumber.length() > 0) {
                    tokens.add(new Token(TokenType.NUMBER, currentNumber.toString()));
                    currentNumber = new StringBuilder();
                }
                switch (c) {
                    case '+': tokens.add(new Token(TokenType.OPERATOR, "+")); break;
                    case '-': tokens.add(new Token(TokenType.OPERATOR, "-")); break;
                    case '*': tokens.add(new Token(TokenType.OPERATOR, "*")); break;
                    case '/': tokens.add(new Token(TokenType.OPERATOR, "/")); break;
                    case '^': tokens.add(new Token(TokenType.OPERATOR, "^")); break;
                    case 'x': tokens.add(new Token(TokenType.VARIABLE, "x")); break;
                    case '(': tokens.add(new Token(TokenType.LPAREN, "(")); break;
                    case ')': tokens.add(new Token(TokenType.RPAREN, ")")); break;
                    case ' ': break; // Ignorar espacios
                    default:
                        throw new IllegalArgumentException("Carácter no válido: " + c);
                }
            }
        }
        if (currentNumber.length() > 0) {
            tokens.add(new Token(TokenType.NUMBER, currentNumber.toString()));
        }
        return tokens;
    }

    public List<Token> getTokens() {
        return this.tokens;
    }

    public Double evaluar(double x) {
        if (this.tokens == null) {
            return null; // No se puede evaluar si la expresión no es válida
        }

        List<Token> rpn = infixToRPN(this.tokens);
        Stack<Double> stack = new Stack<>();

        for (Token token : rpn) {
            switch (token.type) {
                case NUMBER:
                    stack.push(Double.parseDouble(token.value));
                    break;
                case VARIABLE:
                    if (token.value.equals("x")) {
                        stack.push(x);
                    }
                    break;
                case OPERATOR:
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    double result = applyOperator(token.value, operand1, operand2);
                    stack.push(result);
                    break;
            }
        }
        return stack.pop();
    }

    private List<Token> infixToRPN(List<Token> tokens) {
        List<Token> output = new ArrayList<>();
        Stack<Token> operatorStack = new Stack<>();

        for (Token token : tokens) {
            switch (token.type) {
                case NUMBER:
                case VARIABLE:
                    output.add(token);
                    break;
                case OPERATOR:
                    while (!operatorStack.isEmpty() &&
                           !operatorStack.peek().value.equals("(") &&
                           precedence(token.value) <= precedence(operatorStack.peek().value)) {
                        output.add(operatorStack.pop());
                    }
                    operatorStack.push(token);
                    break;
                case LPAREN:
                    operatorStack.push(token);
                    break;
                case RPAREN:
                    while (!operatorStack.isEmpty() && !operatorStack.peek().value.equals("(")) {
                        output.add(operatorStack.pop());
                    }
                    operatorStack.pop(); // Eliminar el paréntesis izquierdo
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            output.add(operatorStack.pop());
        }

        return output;
    }
    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return 0;
        }
    }

    private double applyOperator(String operator, double operand1, double operand2) {
        switch (operator) {
            case "+": return operand1 + operand2;
            case "-": return operand1 - operand2;
            case "*": return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                return operand1 / operand2;
            case "^": return Math.pow(operand1, operand2);
            default: throw new IllegalArgumentException("Operador no válido: " + operator);
        }
    }

    public enum TokenType {
        NUMBER,
        OPERATOR,
        VARIABLE,
        LPAREN,
        RPAREN
    }

    public static class Token {
        TokenType type;
        String value;

        Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Token{" +
                   "type=" + type +
                   ", value='" + value + '\'' +
                   '}';
        }
    }

    // Método de la secante
   public String secantMethod(double x0, double x1, double tolerancia, int maxIteraciones) {
    StringBuilder resultados = new StringBuilder(); // Usamos StringBuilder para construir el String

    if (this.tokens == null) {
        resultados.append("No se puede aplicar el método de la secante debido a errores en la expresión.\n");
        return resultados.toString();
    }

    Double fx0 = evaluar(x0);
    Double fx1 = evaluar(x1);

    if (fx0 == null || fx1 == null) {
        resultados.append("Error al evaluar la función en los puntos iniciales.\n");
        return resultados.toString();
    }

    for (int i = 0; i < maxIteraciones; i++) {
        double x2 = x1 - fx1 * (x1 - x0) / (fx1 - fx0); // Fórmula de la secante
        Double fx2 = evaluar(x2);

        if (fx2 == null) {
            resultados.append("Error al evaluar la función en la iteración " + (i + 1) + "\n");
            return resultados.toString();
        }

        String resultadoIteracion = "Iteración " + (i + 1) + ": x = " + x2 + ", f(x) = " + fx2 + "\n";
        resultados.append(resultadoIteracion); // Añadir el resultado al StringBuilder

        if (Math.abs(x2 - x1) < tolerancia) {
            resultados.append("Raíz encontrada con la tolerancia especificada.\n");
            resultados.append("Aproximación de la raíz: " + x2 + "\n");
            return resultados.toString();
        }

        x0 = x1;
        fx0 = fx1;
        x1 = x2;
        fx1 = fx2;
    }

    resultados.append("El método de la secante no convergió después de " + maxIteraciones + " iteraciones.\n");
    return resultados.toString(); // Convertir el StringBuilder a String
}

}

