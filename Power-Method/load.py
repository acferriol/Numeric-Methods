import json

class Load:
    @staticmethod
    def latex_conversion(matrix):
        latex_matrix = r'\begin{pmatrix}' + \
                       r'\\'.join([' & '.join(map(str, row)) for row in matrix]) + \
                       r'\end{pmatrix}'
        return latex_matrix

    @staticmethod
    def latex_vector(vector, name):
        v = (str(vector.tolist()).replace('[', '').replace(']', '').replace('array', ''))
        return f"{name} = {v}^T"

    @staticmethod
    def load_lottiefile(filepath: str):
        with open(filepath, "r") as f:
            return json.load(f)