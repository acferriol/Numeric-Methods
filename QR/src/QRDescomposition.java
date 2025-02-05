
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;

public class QRDescomposition {

    

    /*
     * Recibe una matriz A(no singular) y la modifica (A') como el producto de R*Q
     * donde R es es triangular superior y Q ortogonal , A es semejante a A'.
     * Sera el metodo usado en el Front-End para imprimir todas las A'.
     */
    public static double[][] QR_Fact(double[][] A) {
        /*
         * double r = A[A.length - 1][A.length - 1];
         * for (int i = 0; i < A.length; i++) {
         * A[i][i] -= r;
         * }
         */
        double[][] Q = Gram_Schmidt(A);
        double[][] R = Multi_Tras(Q, A);
        A = Multi(R, Q);
        /*
         * for (int i = 0; i < R.length; i++) {
         * A[i][i] += r;
         * }
         */
        return A;
    }

    // Retorna true si la matriz a es Singular. Usando la biblioteca:
    // "org.apache.commons.math3.linear".
    public static boolean singular(double[][] A) {
        return new LUDecomposition(new Array2DRowRealMatrix(A)).getDeterminant() == 0;
    }

    /*
     * Funcion que aplica iterativamente la factorizacion QR para hallar los valores
     * propios.No sera la usada en el "Front-end" , para poder imprimir las
     * iteraciones.
     */
    public static double[] QRDescomposition_VP(double[][] A, int num_it, double tol) throws FailedProcedureException {
        if (singular(A)) {
            throw new FailedProcedureException("La matriz es singular");
        }
        double[][] Q = new double[A.length][A.length];
        double[][] R = new double[A.length][A.length];
        double[] X0 = new double[A.length];
        double diferencia = Double.MAX_VALUE;
        for (int i = 0; i < X0.length; i++) {
            X0[i] = A[i][i];
        }
        double norma_anterior = norma(X0);
        int j = 0;

        while (j < num_it && diferencia > tol) {
            Q = Gram_Schmidt(A);
            R = Multi_Tras(Q, A);
            A = Multi(R, Q);

            for (int i = 0; i < X0.length; i++) {
                X0[i] = A[i][i];
            }

            double norma_actual = norma(X0);
            diferencia = Math.abs((norma_actual - norma_anterior) / norma_actual); // Error relativo
            norma_anterior = norma_actual;
            j++;
        }

        if (diferencia < tol) {
            return X0; // Procedimiento exitoso , converge a X0
        } else {
            throw new FailedProcedureException("Procedimiento fallido");
        }
    }

    // Proceso de Ortogonalizacion de Gram Schmidt
    public static double[][] Gram_Schmidt(double[][] A) {
        double[][] Q = new double[A.length][A.length];
        for (int i = 0; i < Q.length; i++) {
            for (int j = 0; j < Q.length; j++) {
                Q[i][j] = A[i][j];
            }
        }
        for (int i = 1; i < Q.length; i++) {
            for (int j = 0; j < i; j++) {
                double a = 0;
                double b = 0;
                for (int k = 0; k < Q.length; k++) {
                    a += Q[k][i] * Q[k][j];
                    b += Q[k][j] * Q[k][j];
                }
                for (int l = 0; l < Q.length; l++) {
                    Q[l][i] -= Q[l][j] * (a / b);
                }

            }
        }
        Normalizar(Q);
        return Q;
    }

    // Normalizar una matriz (norma II)
    private static void Normalizar(double[][] Q) {
        for (int i = 0; i < Q.length; i++) {
            double norma = 0;
            for (int j = 0; j < Q.length; j++) {
                norma += Q[j][i] * Q[j][i];
            }
            for (int j = 0; j < Q.length; j++) {
                Q[j][i] /= Math.sqrt(norma);
            }
        }
    }

    // Multiplicar Matrices (tradicional)
    private static double[][] Multi(double[][] A, double[][] B) {
        double result[][] = new double[A.length][A.length];
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B.length; j++) {
                for (int k = 0; k < B.length; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    // Multiplicar Q traspuesta * A
    private static double[][] Multi_Tras(double[][] A, double[][] B) {
        double result[][] = new double[A.length][A.length];
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B.length; j++) {
                for (int k = 0; k < B.length; k++) {
                    result[i][j] += A[k][i] * B[k][j];
                }
            }
        }
        return result;
    }

    // devuelve la norma de un vector (no nulo)(norma II)
    public static double norma(double[] v) {
        double s = 0;
        for (int i = 0; i < v.length; i++) {
            s += v[i] * v[i];
        }
        return Math.sqrt(s);

    }
}