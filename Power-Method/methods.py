import json

import numpy as np
import pandas as pd

class Methods:
    @staticmethod
    def __augmented(coefficients, independents):
        temp = np.zeros((coefficients.shape[0], coefficients.shape[0] + 1), float)

        for row in range(coefficients.shape[0]):
            temp[row][coefficients.shape[0]] = independents[row]

        return temp

    @staticmethod
    def __power_method(A, x, n_iter, tol):
        p = np.linalg.norm(x, ord=np.inf)
        x = x / p

        values = []

        for k in range(n_iter):
            y = A.dot(x)
            u = p
            p = np.linalg.norm(y, ord=np.inf)
            err = np.linalg.norm(x - (y / p), ord=np.inf)
            x = y / p

            values.append(u)

            if err < tol:
                return values, x

        raise ValueError("Failure after max number of iterations was reached (Power)")

    @staticmethod
    def __inverse_power_method(A, x, n_iter, tol):
        n = A.shape[0]
        q = x.T.dot(A).dot(x) / x.T.dot(x)

        p = np.argmax(np.abs(x))
        x = x / x[p]

        values = []

        for k in range(n_iter):

            try:
                y = np.linalg.solve(A - q * np.identity(n), x)
            except np.linalg.LinAlgError:
                return q, x

            mu = y[p]
            p = np.argmax(np.abs(y))

            err = np.linalg.norm(x - (y / y[p]), ord=np.inf)
            x = y / y[p]


            if err < tol:
                mu = (1 / mu) + q
                values.append(mu)
                return values, x

            q = (1 / mu) + q

            values.append(q)

        raise ValueError("Failure after max number of iterations was reached (Inverse)")

    @staticmethod
    def analyze_data(dataset, tol, n_iter):
        """
        Loads the dataset from 'dataset.csv' and computes the eigenvalues using the inverse power method
        :return: None
        """
        df = dataset

        results = []

        for index, row in df.iterrows():
            matrix = np.array(json.loads(row['matrix']))
            x = np.array(json.loads(row['vector']))

            values_inverse, x_inv = Methods.__inverse_power_method(matrix, x, n_iter, tol)
            values_power, x_pow = Methods.__power_method(matrix, x, n_iter, tol)
            results.append({
                'matrix': row['matrix'],
                'power-method-values': values_power,
                'power-vector': x_pow,
                'inverse-power-method-values': values_inverse,
                'inverse-vector': x_inv
            })
        results_df = pd.DataFrame(results)

        return results_df