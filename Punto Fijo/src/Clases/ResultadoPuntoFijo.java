
package Clases;
import java.util.List;

public class ResultadoPuntoFijo {
    private double raiz;
    private double error;
    private int iteraciones;
    private List<Double> valores;

    public double getRaiz() {
        return raiz;
    }

    public void setRaiz(double raiz) {
        this.raiz = raiz;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public List<Double> getValores() {
        return valores;
    }

    public void setValores(List<Double> valores) {
        this.valores = valores;
    }

    public ResultadoPuntoFijo(double raiz, double error, int iteraciones, List<Double> valores) {
        this.raiz = raiz;
        this.error = error;
        this.iteraciones = iteraciones;
        this.valores = valores;
    }

    @Override
    public String toString() {
        return "Raíz: " + raiz + "\nError: " + error + "\nIteraciones: " + iteraciones + "\nValores: " + valores;
    }
}
