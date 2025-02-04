import numpy as np

def gauss_seidel(A, b, x=None, tolerance=1e-10, max_iterations=500, callback=None):
    if x is None:
        x = np.zeros_like(b)

    n = len(b)
    for it_count in range(max_iterations):
        x_new = np.copy(x)
        for i in range(n):
            sum_ax = np.dot(A[i, :], x_new) - A[i, i] * x_new[i]
            x_new[i] = (b[i] - sum_ax) / A[i, i]

        if callback:
           callback(k + 1, x)
        if np.linalg.norm(x_new - x, ord=np.inf) < tolerance:
            return x_new
        
        x = x_new

    raise ValueError("El método de Gauss-Seidel no convergió dentro del número máximo de iteraciones.")
