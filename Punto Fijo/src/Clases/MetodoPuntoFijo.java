
package Clases;

import java.util.ArrayList;
import java.util.List;
import org.mariuszgromada.math.mxparser.Function;

public class MetodoPuntoFijo {
      private double valorInicial;
    private double tolerancia;
    private int iteracionesMaximas;
    private Function funcion;
    

    public MetodoPuntoFijo(double valorInicial, double tolerancia, int iteracionesMaximas, Function funcion ) {
        this.valorInicial = valorInicial;
        this.tolerancia = tolerancia;
        this.iteracionesMaximas = iteracionesMaximas;
        this.funcion = funcion;
     
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(double tolerancia) {
        this.tolerancia = tolerancia;
    }

    public int getIteracionesMaximas() {
        return iteracionesMaximas;
    }

    public void setIteracionesMaximas(int iteracionesMaximas) {
        this.iteracionesMaximas = iteracionesMaximas;
    }

    public Function getFuncion() {
        return funcion;
    }

    public void setFuncion(Function funcion) {
        this.funcion = funcion;
    }

    

    // Método principal para encontrar la raíz
    public ResultadoPuntoFijo encontrarRaiz() {
        double x ;
        double xAnterior = valorInicial;
        int iteracion = 0;
        double error = Double.MAX_VALUE;
        
        List<Double> iteraciones = new ArrayList<>();

          while (error > tolerancia && iteracion < iteracionesMaximas) {
            x = funcion.calculate(xAnterior); 
            iteraciones.add(x);

            error = Math.abs(x - xAnterior);
            xAnterior = x;
            iteracion++;
          }
        return new ResultadoPuntoFijo(xAnterior, error, iteracion, iteraciones);
    }
    
    
}
