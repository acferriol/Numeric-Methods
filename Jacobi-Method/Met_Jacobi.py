import tkinter as tk 
import re
from tkinter import messagebox
import numpy as np
from tkinter import ttk

respuestas=[]


def jacobi(A, b, x0, max_iterations=500, tolerance=1e-3):
    global respuestas

    #x^(k)=D_inv(L+U)*X^(k-1)+D_inv* b
    #D matriz diagonal de A
    #L matriz triangular inferor de A
    #U matriz triangular inferor de A
    D = np.diag(np.diag(A))
    LU = A - D #Si le resto a la matriz la matriz diagonal me daria la matriz triangular inferior y superior juntas que es lo mismo que L+U
    # x_new = np.zeros_like(x) porque x ya es el vector nulo  
    x=x0
    iteracion=0
    #Combinar A y b en la nueva matriz
    Ab=np.column_stack((A,b))
    rango_A=np.linalg.matrix_rank(A)
    rango_Ab=np.linalg.matrix_rank(Ab)
    if np.linalg.det(A) != 0 or (np.linalg.det(A) == 0 and rango_A==rango_Ab):
    
        if np.linalg.det(A) == 0:
            messagebox.showwarning("Advertencia","El sistema tiene infinitas soluciones.") 
        
        for i in range(max_iterations):
            D_inv=np.linalg.inv(D)
            x_temp=x
            x=np.dot(D_inv,np.dot(-LU,x))+np.dot(D_inv,b)
            iteracion=iteracion+1
        # print("iteracion",i,":x = ",x)
        # f"La solución es: {solucion}\nNúmero de iteraciones: {iteraciones}
            respuestas.append(f'Iteracion {iteracion} :{x}')
        if entry_parada.get()=='1':
            if np.linalg.norm(x-x_temp)<tolerance:
                return x,iteracion
        elif entry_parada.get()=='2':
            if np.linalg.norm(x-x_temp)/np.linalg.norm(x)<tolerance:
                return x,iteracion
        else:
            messagebox.showwarning("Advertencia","El criterio de parada introducido no es válido.")
            return entry_parada
        
        return x,iteracion
    

    else:
        messagebox.showwarning("Advertencia","El sistema no tiene solución.")
    
#-----------------------------------------------------------------------------------------------------------------------------------
def agregar_ecuacion():
    # Crear una nueva caja de texto para ingresar una ecuación
    nueva_ecuacion = tk.Entry(Frame1, bg='white', fg='black', font=("Perpetua", 18), width=25)
    nueva_ecuacion.pack(pady=5)  # Empaquetar la caja de texto
    ecuaciones.append(nueva_ecuacion)  # Agregar la nueva caja de texto a la lista de ecuaciones
#------------------------------------------------------------------------------------------------------------------------------------   
def eliminar_ultima_ecuacion():
    if ecuaciones:
        ultima_ecuacion=ecuaciones.pop()#Elimina la última caja de texto 
        ultima_ecuacion.destroy() #Elimina la caja de texto de la interfaz.
    else:
        messagebox.showwarning("Advertencia","No hay ecuaciones para eliminar.")
#-----------------------------------------------------------------------------------------------------------------------------------          
#Método para tomar los coeficientes de las ecuaciones introducidas en los Entry y guardar en la matriz
def guardar_datos():
    coeficientes = []
    constantes = []
    errores = 0
    est_set = set()

    for elem in ecuaciones:  # Iterar sobre cada Entry en la lista
        ecuacion = elem.get()
        for x in ecuacion:
            if x.isalpha():
                est_set.add(x)

    print(est_set)
    conList = list(est_set)
    print(conList)
    conList.sort()
    print(conList)
    

    for entry in ecuaciones:  # Iterar sobre cada Entry en la lista
        ecuac = entry.get().strip()
        print(ecuac)

        partes = ecuac.split('=')

        if not validar_ecuacion(ecuac):
            errores += 1
            continue

        if len(partes) != 2:  # caso en el que el usuario no introduzca una ecuacion válida
            errores += 1
            continue

        parte_izq = partes[0].strip()

        parte_der =float(partes[1].strip())
        
        

        # Obtener las variables y sus coeficientes
        temp = re.split(r'(?=\+|-)', parte_izq.replace(' ', ''))
        
        coeff_dict = {}
        lista_tempCoef = []
        
        
        for variable in conList:
        
            for term in temp:
                
                if variable in term:
                    coef_str = term.replace(variable, '')  # Procesa directamente aquí

                    # Validar coef_str antes de convertirlo a float
                    if coef_str == '' or coef_str == '+':
                        coef_str = 1.0
                    elif coef_str == '-':
                        coef_str = -1.0
                    else:
                        try:
                            coef_str = float(coef_str)
                        except ValueError:
                            errores += 1
                            coef_str = 0  # O manejar de otra manera
                            continue
                        
                    
                    coeff_dict[variable]=coef_str
        
        for variab in conList:
            lista_tempCoef.append(coeff_dict.get(variab, 0))

        coeficientes.append(lista_tempCoef)
        constantes.append(parte_der)
    
    if not(len(conList)==len(constantes)):
        messagebox.showwarning("Advertencia", "La cantidad de ecuaciones suministradas no le es correspondiente a la cantidad de incógnitas")
    elif errores == 0:
        print(coeficientes)
        print(constantes)
        A = np.array(coeficientes)
        b = np.array(constantes)
        return A, b
    elif errores == 1:
        messagebox.showwarning("Advertencia", "Una de las ecuaciones no es válida.")
    else:
        messagebox.showwarning("Advertencia", "Varias de las ecuaciones no son válidas.")
        
#---------------------------------------------------------------------------------------------------------------------------        
#para tener el x0 para el método de jacobi
def ponerEcuacionesCero():
    x0= [0] * len(ecuaciones)
    return x0
#---------------------------------------------------------------------------------------------------------------------------
#LLamada a la función jacobi
def funcion_resultado():
    try:
        resultado = guardar_datos()
        if isinstance(resultado, tuple):  # Verifica si es un par (A, b)
            resultado_matriz, resultado_terminos = resultado
            Aproximación_inicial = ponerEcuacionesCero()
        
            if iteraciones.get().strip()=="":
                messagebox.showinfo("Verifique","No ha introducido el número de iteraciones.")
        
            elif tolerancia.get().strip()=="":
                messagebox.showinfo("Verifique","No ha introducido la tolerancia.")
            else:
                max_iter =int(iteraciones.get())
                toleranc=float(tolerancia.get())
                
                solucion, iteracion = jacobi(resultado_matriz, resultado_terminos, Aproximación_inicial,max_iter,toleranc)
                print("La solución es:", solucion)
                print("Número de iteraciones:", iteracion)
                print(respuestas)
                if iteracion==max_iter:
                    messagebox.showinfo("Resultado",f"El sistema no convergió luego de {iteracion} iteraciones\n El resultado final es: {solucion}")
                else:
                    messagebox.showinfo("Resultado", f"La solución es: {solucion}\nNúmero de iteraciones: {iteracion}")
                n=70
                i=0
                for x in respuestas:
                    etiq=tk.Label(Frame2)
                    etiq.place(x=100,y=250+30*i)
                    etiq.configure(text=x)
                    i+=1
            
            
        
        

    except ValueError as e:
        print("Error:", e)
#---------------------------------------------------------------------------------------------------------------------------
def validar_ecuacion(ecuacion):
    # Permitir variables, coeficientes, operadores y constantes
    patron = r'^[\s\+\-\d\w\.\=]+$'
    if re.match(patron, ecuacion):
        return True
    return False
#--------------------------------------------------------------------------------------------------------------------------------
ecuacion =""
patron=[]
def abrir_frame():
    global ecuacion
    global patron
    if entry_parada.get().strip()=="":
        messagebox.showinfo("Verifique","No ha elegido el criterio de parada.")
        return entry_parada
    if entry_parada.get().strip()!="1" and entry_parada.get().strip()!="2":
        messagebox.showinfo("Verifique","No ha elegido correctamente el criterio de parada.")
        return entry_parada
    for entry in ecuaciones:  # Iterar sobre cada Entry en la lista
        ecuacion = entry.get().strip()
        patron= re.findall(r'[a-zA-Z]{2}', ecuacion)
        if not bool(re.search(r'=',ecuacion)):
            messagebox.showinfo("Verifique","La ecuación debe tener el signo =")
            return entry
        if len(patron)!=0:
            messagebox.showinfo("Verifique","Los terminos colocados en una ecuacion no son válidos.")
            return entry
        if ecuacion=="":
            messagebox.showinfo("Verifique","Hay campos vacíos.")
            return entry
    Frame1.pack_forget()
    Frame2.pack(fill=tk.BOTH,expand=True)

    
#Crear la ventana principal
# Función para cambiar el color del botón al pasar el cursor
def on_enter(e):
    e.widget['background'] = '#5A5A5A'
    e.widget['foreground'] = 'white'

def on_leave(e):
    e.widget['background'] = '#333333'
    e.widget['foreground'] = 'white'

# Función para cambiar el borde del Entry al enfocarse
def on_focus_in(e):
    e.widget['highlightbackground'] = '#5A5A5A'
    e.widget['highlightthickness'] = 2

def on_focus_out(e):
    e.widget['highlightbackground'] = '#333333'
    e.widget['highlightthickness'] = 1
    
root = tk.Tk()
root.title("Método de Jacobi")
# Establecer un tamaño predeterminado para la ventana
root.geometry("1000x800")  # Ancho x Alto
#Para que el fondo sea negro
root.configure(bg='black')

Frame1= tk.Frame(root)
Frame1.configure(bg='black')
Frame2= tk.Frame(root)
Frame2.configure(bg='black')
Frame1.pack(fill=tk.BOTH,expand=True)

# Estilo para las etiquetas
label_style = {"bg": 'black', "fg": 'white', 'font': ("Perpetua", 18), 'width': 40, 'height': 4}
entry_style = {
    'font': ('Perpetua', 18),
    'background': 'white',
    'foreground': 'black',
    'relief': 'flat',
    'highlightbackground': '#333333',
    'highlightcolor': '#5A5A5A',
    'highlightthickness': 1,
    'insertbackground': 'black',
    'width': 25
}
button_style = {
    'font': ('Perpetua', 16),
    'background': '#333333',
    'foreground': 'white',
    'activebackground': '#5A5A5A',
    'activeforeground': 'white',
    'relief': 'flat',
    'bd': 0
}

#Elementos de la ventana2
label2 = tk.Label(Frame2, text="Introduzca la tolerancia:",**label_style)
label3 = tk.Label(Frame2, text="Introduzca el número de iteraciones:",**label_style)
label2.grid(row=0,column=0,columnspan=3)
label3.grid(row=1,column=0,columnspan=3)

label_iter = tk.Label(Frame2, text=" ",**label_style)
label_iter.grid(row=20,column=0,columnspan=3)

tolerancia = tk.Entry(Frame2, **entry_style)
iteraciones= tk.Entry(Frame2, **entry_style)
tolerancia.grid(row=0,column=4,columnspan=3)
iteraciones.grid(row=1,column=4,columnspan=3)

boton_resolver = tk.Button(Frame2, text="Resolver", command=funcion_resultado, **button_style)
boton_resolver.grid(column=4,pady=200)
#boton_resolver.bind("<Enter>", on_enter)
#boton_resolver.bind("<Leave>", on_leave)
#boton_resolver.pack(side=tk.BOTTOM, pady=30)





#label principal
label = tk.Label(Frame1, text="Introduzca las ecuaciones:",**label_style)

label.pack(pady=10)



# Lista para guardar las cajas de texto de las ecuaciones
ecuaciones = []

# Cajas de texto iniciales para las primeras dos ecuaciones

"""ec1 = tk.Entry(root, bg='white', fg='black', font=("Perpetua", 18), width=25)
ec1.pack(pady=5)
ec2 = tk.Entry(root, bg='white', fg='black', font=("Perpetua", 18), width=25)
ec2.pack(pady=5)"""
# Cajas de texto iniciales para las primeras dos ecuaciones
ec1 = tk.Entry(Frame1, **entry_style)
ec1.pack(pady=5)
ec1.bind("<FocusIn>", on_focus_in)
ec1.bind("<FocusOut>", on_focus_out)

ec2 = tk.Entry(Frame1, **entry_style)
ec2.pack(pady=5)
ec2.bind("<FocusIn>", on_focus_in)
ec2.bind("<FocusOut>", on_focus_out)


ecuaciones.append(ec1)
ecuaciones.append(ec2)

# Botón para agregar nuevas cajas de texto
"""boton_add = tk.Button(root, text="Añadir una nueva ecuación",font=("Perpetua", 16),command=agregar_ecuacion)
boton_add.pack(side=tk.BOTTOM, pady=20)"""
boton_add = tk.Button(Frame1, text="Añadir una nueva ecuación", command=agregar_ecuacion, **button_style)
boton_add.bind("<Enter>", on_enter)
boton_add.bind("<Leave>", on_leave)
boton_add.pack(side=tk.BOTTOM, pady=20)

# Botón para eliminar la ultima caja de texto 
"""boton_remove = tk.Button(root, text="Eliminar la ultima ecuación",font=("Perpetua", 16),command=eliminar_ultima_ecuacion)
boton_remove.pack(side=tk.BOTTOM, pady=20)"""
boton_remove = tk.Button(Frame1, text="Eliminar la última ecuación", command=eliminar_ultima_ecuacion, **button_style)
boton_remove.bind("<Enter>", on_enter)
boton_remove.bind("<Leave>", on_leave)
boton_remove.pack(side=tk.BOTTOM, pady=20)

#Botón para resolver el sistema de ecuaciones
"""boton_resolver = tk.Button(root, text="Resolver",font=("Perpetua", 16),command=funcion_resultado)
boton_resolver.pack(side=tk.BOTTOM, pady=30)"""
boton_aceptar = tk.Button(Frame1, text="Aceptar", command=abrir_frame, **button_style)
boton_aceptar.bind("<Enter>", on_enter)
boton_aceptar.bind("<Leave>", on_leave)
boton_aceptar.pack(side=tk.BOTTOM, pady=30)

#Estilo del entry de parada
entry_parada_style = {
    'font': ('Perpetua', 18),
    'background': 'white',
    'foreground': 'black',
    'relief': 'flat',
    'highlightbackground': '#333333',
    'highlightcolor': '#5A5A5A',
    'highlightthickness': 1,
    'insertbackground': 'black',
    'width': 25
}
#entry criterio de parada
entry_parada = tk.Entry(Frame1, **entry_parada_style)
entry_parada.pack(side=tk.BOTTOM, pady=25)

#label criterios de parada
label_parada = tk.Label(Frame1, text="Para seleccionar criterios de parada:\n 1-Error absoluto\n 2-Error relativo ",**label_style)
label_parada.pack(side=tk.BOTTOM, pady=20)



root.mainloop()
