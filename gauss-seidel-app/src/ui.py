import numpy as np
from tkinter import *
from tkinter import ttk
from gauss_seidel import gauss_seidel

class GaussSeidelApp:
    def __init__(self, master):
        self.master = master
        master.title("Método de Gauss-Seidel")
        master.configure(bg='#f0f0f0')

        # Configurar el diseño de la cuadrícula
        master.columnconfigure(0, weight=1)
        master.columnconfigure(1, weight=3)

        style = ttk.Style()
        style.configure("TLabel", background='#f0f0f0', font=('Arial', 12))
        style.configure("TButton", font=('Arial', 12))

        # Entrada de la matriz
        self.matrix_label = ttk.Label(master, text="Matriz (filas separadas por comas):")
        self.matrix_label.grid(row=0, column=0, padx=10, pady=5, sticky=E)
        self.matrix_entry = Text(master, height=10, width=50, font=('Arial', 12))
        self.matrix_entry.grid(row=0, column=1, padx=10, pady=5)

        # Entrada del vector
        self.vector_label = ttk.Label(master, text="Vector b (separado por comas):")
        self.vector_label.grid(row=1, column=0, padx=10, pady=5, sticky=E)
        self.vector_entry = ttk.Entry(master, width=50, font=('Arial', 12))
        self.vector_entry.grid(row=1, column=1, padx=10, pady=5)

        # Entrada de la tolerancia
        self.tolerance_label = ttk.Label(master, text="Tolerancia:")
        self.tolerance_label.grid(row=2, column=0, padx=10, pady=5, sticky=E)
        self.tolerance_entry = ttk.Entry(master, width=50, font=('Arial', 12))
        self.tolerance_entry.grid(row=2, column=1, padx=10, pady=5)

        # Entrada del máximo de iteraciones
        self.iterations_label = ttk.Label(master, text="Máximo de Iteraciones:")
        self.iterations_label.grid(row=3, column=0, padx=10, pady=5, sticky=E)
        self.iterations_entry = ttk.Entry(master, width=50, font=('Arial', 12))
        self.iterations_entry.grid(row=3, column=1, padx=10, pady=5)

        # Botón de calcular
        self.calculate_button = ttk.Button(master, text="Calcular", command=self.calculate)
        self.calculate_button.grid(row=4, column=0, columnspan=2, pady=10)

        # Mostrar resultado
        self.result_label = ttk.Label(master, text="Resultados:")
        self.result_label.grid(row=5, column=0, padx=10, pady=5, sticky=E)
        self.result_text = Text(master, height=10, width=50, font=('Arial', 12))
        self.result_text.grid(row=5, column=1, padx=10, pady=5)

        self.scrollbar = ttk.Scrollbar(master, command=self.result_text.yview)
        self.scrollbar.grid(row=5, column=2, sticky='ns')
        self.result_text.config(yscrollcommand=self.scrollbar.set)

    def calculate(self):
        try:
            matrix_input = self.matrix_entry.get("1.0", END).strip()
            rows = [list(map(float, row.split(','))) for row in matrix_input.splitlines() if row]
            matrix = np.array(rows)

            vector_input = self.vector_entry.get().strip()
            vector = np.array(list(map(float, vector_input.split(','))))

            tolerance = float(self.tolerance_entry.get().strip())
            max_iterations = int(self.iterations_entry.get().strip())

            def callback(iteration, x):
                self.result_text.insert(END, f"Iteración {iteration}: {x}\n")
                self.result_text.see(END)

            solution = gauss_seidel(matrix, vector, tolerance=tolerance, max_iterations=max_iterations, callback=callback)
            self.result_text.insert(END, f"Resultado final: {solution}\n")
            self.result_text.see(END)
        except Exception as e:
            self.result_text.insert(END, f"Error: {str(e)}")

if __name__ == "__main__":
    root = Tk()
    app = GaussSeidelApp(root)
    root.mainloop()