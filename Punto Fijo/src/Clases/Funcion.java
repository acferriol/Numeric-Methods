
package Clases;

public class Funcion {
    private String expresion;

    public Funcion(String expresion) {
        this.expresion = expresion;
    }

    public double evaluar(double x) {
        org.mariuszgromada.math.mxparser.Function funcionInterna = new org.mariuszgromada.math.mxparser.Function("f(x) = " + expresion);
        return funcionInterna.calculate(x);
    }
}

