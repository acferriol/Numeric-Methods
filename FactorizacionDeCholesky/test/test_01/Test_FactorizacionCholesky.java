package test_01;

import factorizacioncholesky.FactorizacionDeCholesky;
import factorizacioncholesky.TrabajoConMatrices;

public class Test_FactorizacionCholesky {

    public static void main(String[] args) {
        System.out.println("Prueba del metodo de Cholesky:\n");
        double[][] a = new double[4][4];

        a[0][0] = 4;
        a[0][1] = 1;
        a[0][2] = 2;
        a[0][3] = 1;
        a[1][0] = 1;
        a[1][1] = 3;
        a[1][2] = 0;
        a[1][3] = 1;
        a[2][0] = 2;
        a[2][1] = 0;
        a[2][2] = 5;
        a[2][3] = 3;
        a[3][0] = 1;
        a[3][1] = 1;
        a[3][2] = 3;
        a[3][3] = 4;

        double[][] b = new double[3][3];
        b[0][0] = 1;
        b[0][1] = -1;
        b[0][2] = 1;
        b[1][0] = -1;
        b[1][1] = 5;
        b[1][2] = -5;
        b[2][0] = 1;
        b[2][1] = -5;
        b[2][2] = 6;

        double[][] c = new double[5][5];
        c[0][0] = 2;
        c[0][1] = 18;
        c[0][2] = 9;
        c[0][3] = 34;
        c[0][4] = -6;
        c[1][0] = -1;
        c[1][1] = 0.8;
        c[1][2] = 43.25;
        c[1][3] = 82;
        c[1][4] = -9;
        c[2][0] = 1;
        c[2][1] = 75;           //esto es simplemete una matriz para probar determinante
        c[2][2] = 31.34;
        c[2][3] = 23;
        c[2][4] = 0;
        c[3][0] = 9;
        c[3][1] = 7;
        c[3][2] = 5.4;
        c[3][3] = 2;
        c[3][4] = -9;
        c[4][0] = 6.1;
        c[4][1] = -75;
        c[4][2] = 33.4;
        c[4][3] = -3.2;
        c[4][4] = 5;

        System.out.println(TrabajoConMatrices.definidaPositiva(b));
        double[][] res = FactorizacionDeCholesky.factorizacionCholesky(3, b);

        TrabajoConMatrices.mostrarMatriz(res);

        //Ejemplo para resolver el sistema m*x = m1, siendo m una matriz definida positiva
        double[][] m = {{4, -1, 1}, {-1, 4.25, 2.75}, {1, 2.75, 3.5}};
        double[] m1 = {6, 4, 9};

        double[] respuesta = FactorizacionDeCholesky.resolverSistemaUsandoCholesky(3, m, m1);
        for (int i = 0; i < respuesta.length; i++) {
            System.out.println(respuesta[i] + " ");
        }
    }

}

