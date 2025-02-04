package factorizacioncholesky;

public class FactorizacionDeCholesky {

    public static double[][] factorizacionCholesky(int n, double[][] matriz) {
        double[][] matrizTriangularInferior = inicializarMatrizNula(n);
        //Paso 1
        matrizTriangularInferior[0][0] = Math.sqrt(matriz[0][0]);
        //Paso 2
        for (int j = 1; j <= n - 1; j++) {
            matrizTriangularInferior[j][0] = matriz[j][0] / matrizTriangularInferior[0][0];
        }
        //Paso 3
        for (int i = 1; i <= n - 2; i++) {
            //Paso 4
            matrizTriangularInferior[i][i] = Math.sqrt(matriz[i][i] - calcularSumatoria1(0, i - 1, i, matrizTriangularInferior));
            //Paso 5
            for (int j = i + 1; j <= n - 1; j++) {
                matrizTriangularInferior[j][i] = (matriz[j][i] - calcularSumatoria2(0, i - 1, j, i, matrizTriangularInferior)) / matrizTriangularInferior[i][i];
            }
        }
        //Paso 6
        matrizTriangularInferior[n - 1][n - 1] = Math.sqrt(matriz[n - 1][n - 1] - calcularSumatoria1(0, n - 2, n - 1, matrizTriangularInferior));
        //Paso 7
        return matrizTriangularInferior;
    }

    public static double[] resolverSistemaUsandoCholesky(int n, double[][] matriz, double[] b) {
        double[][] matrizFactorizada = factorizacionCholesky(n, matriz);
        //Paso 8
        double[] y = new double[n];
        y[0] = b[0] / matrizFactorizada[0][0];
        //Paso 9 
        for (int i = 1; i < matrizFactorizada.length; i++) {
            y[i] = (b[i] - calcularSumatoria3(0, i - 1, i, matrizFactorizada, y)) / matrizFactorizada[i][i];
        }
        //Paso 10
        double[] x = new double[n];
        //Paso 11
        x[n - 1] = y[n - 1] / matrizFactorizada[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            x[i] = (y[i] - calcularSumatoria4(i + 1, n - 1, i, matrizFactorizada, x)) / matrizFactorizada[i][i];
        }
        //Paso 12
        return x;
    }

    private static double[][] inicializarMatrizNula(int n) {
        double[][] matriz = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = 0;
            }
        }
        return matriz;
    }

    private static double calcularSumatoria1(int limInicial, int limFinal, int index1, double[][] argumento) {
        double suma = 0;
        for (int k = limInicial; k <= limFinal; k++) {
            suma += Math.pow(argumento[index1][k], 2);
        }
        return suma;
    }

    private static double calcularSumatoria2(int limInicial, int limFinal, int index1, int index2, double[][] argumento) {
        double suma = 0;
        for (int k = limInicial; k <= limFinal; k++) {
            suma += (argumento[index1][k]) * (argumento[index2][k]);
        }
        return suma;
    }

    private static double calcularSumatoria3(int limInicial, int limFinal, int index, double[][] argumento, double[] argumento1) {
        double suma = 0;
        for (int k = limInicial; k <= limFinal; k++) {
            suma += (argumento[index][k]) * (argumento1[k]);
        }
        return suma;
    }

    private static double calcularSumatoria4(int limInicial, int limFinal, int index, double[][] argumento, double[] argumento1) {
        double suma = 0;
        for (int k = limInicial; k <= limFinal; k++) {
            suma += (argumento[k][index]) * (argumento1[k]);
        }
        return suma;
    }
}
