package factorizacioncholesky;

public class TrabajoConMatrices {

    public static void mostrarMatriz(double[][] matriz) {  // Muestra los elementos de la matriz
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("\t" + matriz[i][j]);
            }
            System.out.println("");
        }
    }

    public static double[][] traspuesta(double[][] matriz) {   //  Halla la traspuesta de una matriz
        double[][] traspuesta = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                traspuesta[j][i] = matriz[i][j];
            }
        }
        return traspuesta;
    }

    public static boolean esSimetrica(double[][] matriz) { //  Retorna true si la matriz es simétrica y false si no lo es
        int filas = matriz.length;
        int columnas = matriz[0].length;
        if (filas != columnas) {
            return false;
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < i; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double[][] subMatriz(double[][] matriz, int tamanio) { //Método privado que extrae una submatriz de un determinado tamaño. Se usa para extraer los menores 
        double[][] subMatriz = new double[tamanio][tamanio];              //principales a los cuales se le va a calcular el determinante
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                subMatriz[i][j] = matriz[i][j];
            }
        }
        return subMatriz;
    }

    private static void cofactor(double[][] matriz, double[][] temp, int p, int q, int n) { // Método privado que va dando los cofactores para poder calcular el determinante
        int i = 0, j = 0;
        for (int fila = 0; fila < n; fila++) {
            for (int columna = 0; columna < n; columna++) {
                if (fila != p && columna != q) {
                    temp[i][j++] = matriz[fila][columna];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public static double determinante(double matriz[][], int n) { // Método recursivo que calcula el determinante de cualquier matriz, se le pasa la matriz y su tamaño
        double determinante = 0;
        if (n == 1) {
            return matriz[0][0];
        }
        double temp[][] = new double[n][n];
        int multiplicador = 1;
        for (int f = 0; f < n; f++) {
            cofactor(matriz, temp, 0, f, n);
            determinante += multiplicador * matriz[0][f] * determinante(temp, n - 1);
            multiplicador = -multiplicador;
        }
        return determinante;
    }

    public static boolean definidaPositiva(double matriz[][]) { // A partir de los métodos que determinan el determinante y si la matriz es simétrica devuelve si la matriz es definida positiva o no
        if (!esSimetrica(matriz)) {
            return false;
        }
        for (int i = 1; i <= matriz.length; i++) {
            double[][] submatriz = subMatriz(matriz, i);
            if (determinante(submatriz, i) <= 0) {
                return false;
            }
        }
        return true;
    }

}
