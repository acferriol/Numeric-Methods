import tkinter as tk
import numpy as np
from fractions import Fraction as fr
import Eliminacion_Gaussiana_Pivoteo_Parcial as eg
from PIL import Image,ImageTk
import Error as e
ventana=tk.Tk()
ventana.withdraw()
ventana.minsize(130,100)
ventana.maxsize(130,100)
ventana.title("Eliminacion Gaussiana")
cajas=[]
cajas_gauss=[]
cajas_solucion=[]
imagen_fondo=Image.open("LaboratorioNumerica/color.jpg")
imagen_fondo=imagen_fondo.resize((900,450))
imagen_canvas=Image.open("LaboratorioNumerica/colorsolo.jpg")
imagen_canvas=imagen_canvas.resize((800,800))
foto_canvas=ImageTk.PhotoImage(imagen_canvas)
foto=ImageTk.PhotoImage(imagen_fondo)
foto_ventana=tk.Label(ventana,image=foto)
foto_ventana.place(x=-20,y=-80)

def botonTamano():
    try:
        tamano=int(tamano_texto.get())
    except ValueError:
        ventana_aviso.deiconify()
        etiqueta2.configure(text="Debe introducir un numero",font=("Arial",12,"bold"),bg="DeepSkyBlue3")
        etiqueta2.place(relx=0.1,rely=0.25)
        ventana_aviso.protocol("WM_DELETE_WINDOW", lambda:cerrar_aviso(tamano_matriz))

    else:
        if tamano<=1 :
            ventana_aviso.deiconify()
            etiqueta2.configure(text="El tamaño debe ser 2 o mayor",font=("Arial",12,"bold"),bg="DeepSkyBlue3")
            etiqueta2.place(relx=0.1,rely=0.25)
            ventana_aviso.protocol("WM_DELETE_WINDOW", lambda:cerrar_aviso(tamano_matriz))
        else:
            ventana.deiconify()
            tamano_matriz.withdraw()
            ordenar_ventana(tamano)
            crear_entrys(tamano)
            boton_gauss.configure(command= lambda:abrir_ventana_gauss(tamano),bg="DeepSkyBlue3")
            boton_solucion.configure(command=lambda: abrir_ventana_solucion(tamano),bg="DeepSkyBlue3")


def abrir_ventana_solucion(tamano):
    try:
        lista_solucion=eg.sustitucion_regresiva(crear_matriz(tamano))
    except:
            ventana_aviso.deiconify()
            ventana_aviso.maxsize(650,80)
            ventana_aviso.minsize(650,80)
            etiqueta2.configure(text="Solo puede introducir numeros y no dejar ningun espacio en blanco",font=("Arial",12,"bold"),bg="DeepSkyBlue3")
            etiqueta2.place(relx=0.1,rely=0.25)
            ventana_aviso.protocol("WM_DELETE_WINDOW", lambda:cerrar_aviso(ventana))
    else:
        ventana.withdraw()
        ventana_solucion.deiconify()
        pintar_ventana_solucion(lista_solucion)

        

def abrir_ventana_gauss(tamano):
    try:
        matriz_gauss=eg.eliminacion_gaussiana(crear_matriz(tamano))
    except:
            ventana_aviso.deiconify()
            ventana_aviso.maxsize(650,80)
            ventana_aviso.minsize(650,80)
            etiqueta2.configure(text="Solo puede introducir numeros y no dejar ningun espacio en blanco",font=("Arial",12,"bold"),bg="DeepSkyBlue3")
            etiqueta2.place(relx=0.1,rely=0.25)
            ventana_aviso.protocol("WM_DELETE_WINDOW", lambda:cerrar_aviso(ventana))
    else:
        ventana.withdraw()
        ventana_gauss.deiconify()
        colocar_matriz_gauss(tamano)
        pintar_matriz(matriz_gauss)
        
        

def ordenar_ventana(tamano):
    ventana.minsize(ventana.winfo_width()+99*tamano,ventana.winfo_height()+50*tamano)
    ventana.maxsize(ventana.winfo_width()+99*tamano,ventana.winfo_height()+50*tamano)
    if tamano==2:
        canva1.create_line(20,20,20,74,fill="black",width=3)
        canva1.create_line(20,20,30,5,fill="black",width=3)
        canva1.create_line(20,74,30,89,fill="black",width=3)

        canva2.create_line(53,20,53,74,fill="black",width=3)
        canva2.create_line(53,20,43,5,fill="black",width=3)
        canva2.create_line(53,74,43,89,fill="black",width=3)

        canva2.create_line(5,14,5,83,fill="black",width=3,dash=(1,1))        
    else:
        boton_solucion.place(relx=0.68+tamano*0.03,rely=0.8)
        canva2.configure(height=90+30*tamano)
        canva1.configure(height=90+30*tamano)
        canva1.place(x=100+5*tamano,y=32)
        canva2.place(x=185+20*tamano,y=32)
        if tamano==4:
            
            canva1.place(x=100+5*tamano,y=32)
            canva2.place(x=185+24*tamano,y=32)
            canva1.create_line(20,20,20,74+17*tamano,fill="black",width=3)
            canva1.create_line(20,20,30,5,fill="black",width=3)
            canva1.create_line(20,74+17*tamano,30,89+17*tamano,fill="black",width=3)

            canva2.create_line(53,20,53,74+17*tamano,fill="black",width=3)
            canva2.create_line(53,20,43,5,fill="black",width=3)
            canva2.create_line(53,74+17*tamano,43,89+17*tamano,fill="black",width=3)

            canva2.create_line(5,14,5,83+17*tamano,fill="black",width=3,dash=(1,1))
        
        elif tamano==5:
            
            canva1.place(x=100+5*tamano,y=32)
            canva2.place(x=185+28*tamano,y=32)
            canva1.create_line(20,20,20,74+18*tamano,fill="black",width=3)
            canva1.create_line(20,20,30,5,fill="black",width=3)
            canva1.create_line(20,74+18*tamano,30,89+18*tamano,fill="black",width=3)

            canva2.create_line(53,20,53,74+18*tamano,fill="black",width=3)
            canva2.create_line(53,20,43,5,fill="black",width=3)
            canva2.create_line(53,74+18*tamano,43,89+18*tamano,fill="black",width=3)

            canva2.create_line(5,14,5,83+18*tamano,fill="black",width=3,dash=(1,1))

        elif tamano>=6:
            
            canva1.place(x=100+5*tamano,y=32)
            canva2.place(x=185+30*tamano,y=32)
            canva1.create_line(20,20,20,74+20*tamano,fill="black",width=3)
            canva1.create_line(20,20,30,5,fill="black",width=3)
            canva1.create_line(20,74+20*tamano,30,89+20*tamano,fill="black",width=3)

            canva2.create_line(53,20,53,74+20*tamano,fill="black",width=3)
            canva2.create_line(53,20,43,5,fill="black",width=3)
            canva2.create_line(53,74+20*tamano,43,89+20*tamano,fill="black",width=3)

            canva2.create_line(5,14,5,83+20*tamano,fill="black",width=3,dash=(1,1))
        
        else:
            canva1.create_line(20,20,20,74+8*tamano,fill="black",width=3)
            canva1.create_line(20,20,30,5,fill="black",width=3)
            canva1.create_line(20,74+8*tamano,30,89+8*tamano,fill="black",width=3)

            canva2.create_line(53,20,53,74+8*tamano,fill="black",width=3)
            canva2.create_line(53,20,43,5,fill="black",width=3)
            canva2.create_line(53,74+8*tamano,43,89+8*tamano,fill="black",width=3)

            canva2.create_line(5,14,5,83+8*tamano,fill="black",width=3,dash=(1,1))
        
def crear_entrys(tamano):
    exp,total,espacios=0,1,32
    x,y=127,53
    if tamano>2:
        exp=tamano
    while total<=tamano**2:
        caja=tk.Entry(ventana,width=3)
        caja.place(x=x+exp*8,y=y)
        cajas.append(caja)
        x+=espacios
        if total%tamano ==0:
            x+=12
            caja=tk.Entry(ventana,width=3)
            caja.place(x=x+exp*8,y=y)
            cajas.append(caja)
            x=127
            y+=espacios
        total+=1




tamano_matriz=tk.Toplevel(ventana)
tamano_matriz.minsize(300,200)
tamano_matriz.maxsize(300,200)
tamano_matriz.title("Eliminacion Gaussiana")
foto_ventana=tk.Label(tamano_matriz,image=foto_canvas)
foto_ventana.place(x=-20,y=-80)

etiqueta1=tk.Label(tamano_matriz,text="Introduzca el tamaño de la matiz :",font=("Arial",12,"bold"),bg="DeepSkyBlue3")
etiqueta1.place(relx=0.05,rely=0.2)

tamano_texto=tk.Entry(tamano_matriz,font=("Arial",12,"bold"))
tamano_texto.place(relx=0.19,rely=0.40)

boton_tamano=tk.Button(tamano_matriz,text="Aceptar",command=botonTamano,font=("Arial",12,"bold"),bg="DeepSkyBlue3")
boton_tamano.place(relx=0.36,rely=0.55)

def cerrar_aviso(ventana_actual):
    ventana_aviso.withdraw()
    ventana_actual.deiconify()

ventana_aviso=tk.Toplevel(ventana)
ventana_aviso.minsize(300,80)
ventana_aviso.maxsize(300,80)
ventana_aviso.withdraw()
ventana_aviso.title("AVISO!")
foto_ventana=tk.Label(ventana_aviso,image=foto_canvas)
foto_ventana.place(x=-20,y=-80)
etiqueta2=tk.Label(ventana_aviso)


boton_gauss=tk.Button(ventana,text="Gauss",font=("Arial",12,"bold"))
boton_solucion=tk.Button(ventana,text="Solucion",font=("Arial",12,"bold"))
boton_gauss.place(relx=0.05,rely=0.8)
boton_solucion.place(relx=0.68,rely=0.8)

canva1=tk.Canvas(ventana,width=30,height=90,highlightthickness=0)
canva2=tk.Canvas(ventana,width=60,height=90,highlightthickness=0)
canva1.place(x=100,y=32)
canva2.place(x=185,y=32)
canva1.create_image(-10,-10,image=foto_canvas)
canva2.create_image(-10,-10,image=foto_canvas)


def crear_matriz(tamano):
    x=1
    c=[]
    resultado=np.array([])
    while x<=len(cajas):
        c.append(fr(cajas[x-1].get()))
        if x%(tamano+1)==0:
            if x==((tamano+1)*2):
                q=np.array(c)
                resultado=np.array([w,q])
            else:
                w=np.array(c)
            if (x>((tamano+1)*2)):
                resultado=np.vstack((resultado,w))
            c.clear()
        x+=1
    return resultado


def colocar_matriz_gauss (tamano):
    ventana_gauss.deiconify()
    ventana_gauss.minsize(ventana_gauss.winfo_width()+99*tamano,ventana_gauss.winfo_height()+50*tamano)
    ventana_gauss.maxsize(ventana_gauss.winfo_width()+99*tamano,ventana_gauss.winfo_height()+50*tamano)
    if tamano==2:
        canva3.create_line(20,20,20,74,fill="black",width=3)
        canva3.create_line(20,20,30,5,fill="black",width=3)
        canva3.create_line(20,74,30,89,fill="black",width=3)

        canva4.create_line(53,20,53,74,fill="black",width=3)
        canva4.create_line(53,20,43,5,fill="black",width=3)
        canva4.create_line(53,74,43,89,fill="black",width=3)

        canva4.create_line(5,14,5,83,fill="black",width=3,dash=(1,1))        
    else:
        canva4.configure(height=90+30*tamano)
        canva3.configure(height=90+30*tamano)
        canva3.place(x=100+5*tamano,y=32)
        canva4.place(x=185+20*tamano,y=32)
        if tamano==4:
            
            canva3.place(x=100+5*tamano,y=32)
            canva4.place(x=185+24*tamano,y=32)
            canva3.create_line(20,20,20,74+17*tamano,fill="black",width=3)
            canva3.create_line(20,20,30,5,fill="black",width=3)
            canva3.create_line(20,74+17*tamano,30,89+17*tamano,fill="black",width=3)

            canva4.create_line(53,20,53,74+17*tamano,fill="black",width=3)
            canva4.create_line(53,20,43,5,fill="black",width=3)
            canva4.create_line(53,74+17*tamano,43,89+17*tamano,fill="black",width=3)

            canva4.create_line(5,14,5,83+17*tamano,fill="black",width=3,dash=(1,1))
        
        elif tamano==5:
            
            canva3.place(x=100+5*tamano,y=32)
            canva4.place(x=185+28*tamano,y=32)
            canva3.create_line(20,20,20,74+18*tamano,fill="black",width=3)
            canva3.create_line(20,20,30,5,fill="black",width=3)
            canva3.create_line(20,74+18*tamano,30,89+18*tamano,fill="black",width=3)

            canva4.create_line(53,20,53,74+18*tamano,fill="black",width=3)
            canva4.create_line(53,20,43,5,fill="black",width=3)
            canva4.create_line(53,74+18*tamano,43,89+18*tamano,fill="black",width=3)

            canva4.create_line(5,14,5,83+18*tamano,fill="black",width=3,dash=(1,1))

        elif tamano>=6:
            
            canva3.place(x=100+5*tamano,y=32)
            canva4.place(x=185+30*tamano,y=32)
            canva3.create_line(20,20,20,74+20*tamano,fill="black",width=3)
            canva3.create_line(20,20,30,5,fill="black",width=3)
            canva3.create_line(20,74+20*tamano,30,89+20*tamano,fill="black",width=3)

            canva4.create_line(53,20,53,74+20*tamano,fill="black",width=3)
            canva4.create_line(53,20,43,5,fill="black",width=3)
            canva4.create_line(53,74+20*tamano,43,89+20*tamano,fill="black",width=3)

            canva4.create_line(5,14,5,83+20*tamano,fill="black",width=3,dash=(1,1))
        
        else:
            canva3.create_line(20,20,20,74+8*tamano,fill="black",width=3)
            canva3.create_line(20,20,30,5,fill="black",width=3)
            canva3.create_line(20,74+8*tamano,30,89+8*tamano,fill="black",width=3)

            canva4.create_line(53,20,53,74+8*tamano,fill="black",width=3)
            canva4.create_line(53,20,43,5,fill="black",width=3)
            canva4.create_line(53,74+8*tamano,43,89+8*tamano,fill="black",width=3)

            canva4.create_line(5,14,5,83+8*tamano,fill="black",width=3,dash=(1,1))
    crear_entrys_gauss(tamano)
    boton_gauss=tk.Button(ventana_gauss,text="Aceptar",font=("Arial",12,"bold"),bg="DeepSkyBlue3")
    boton_gauss.place(relx=0.68+tamano*0.03,rely=0.8)
    boton_gauss.configure(command= cerrar)

def cerrar():
    ventana.deiconify()
    ventana_gauss.withdraw()


def crear_entrys_gauss(tamano):
    exp,total,espacios=0,1,32
    x,y=127,53
    if tamano>2:
        exp=tamano
    while total<=tamano**2:
        caja=tk.Entry(ventana_gauss,width=3)
        caja.place(x=x+exp*8,y=y)
        cajas_gauss.append(caja)
        x+=espacios
        if total%tamano ==0:
            x+=12
            caja=tk.Entry(ventana_gauss,width=3)
            caja.place(x=x+exp*8,y=y)
            cajas_gauss.append(caja)
            x=127
            y+=espacios
        total+=1


ventana_gauss=tk.Toplevel(ventana)
ventana_gauss.minsize(130,100)
ventana_gauss.maxsize(130,100)
ventana_gauss.withdraw()
ventana_gauss.title("Pivoteo Parcial")
foto_ventana=tk.Label(ventana_gauss,image=foto)
foto_ventana.place(x=-20,y=-80)


ventana_solucion=tk.Toplevel(ventana)
ventana_solucion.withdraw()
ventana_solucion.minsize(300,80)
ventana_solucion.maxsize(300,80)
ventana_solucion.title("SOLUCION!")
foto_ventana=tk.Label(ventana_solucion,image=foto_canvas)
foto_ventana.place(x=-20,y=-80)


canva3=tk.Canvas(ventana_gauss,width=30,height=90,highlightthickness=0)
canva4=tk.Canvas(ventana_gauss,width=60,height=90,highlightthickness=0)
canva3.place(x=100,y=32)
canva4.place(x=185,y=32)
canva3.create_image(-10,-10,image=foto_canvas)
canva4.create_image(-10,-10,image=foto_canvas)

def pintar_matriz(matriz):
    i=0
    for filas in matriz:
        for valor in filas:
            cajas_gauss[i].insert(0,str(valor))
            i+=1

def cerrarV():
    ventana.deiconify()
    ventana_solucion.withdraw()

boton1=tk.Button(ventana_solucion,text="Aceptar",command=cerrarV,font=("Arial",12,"bold"),bg="DeepSkyBlue3")



def pintar_ventana_solucion(lista):
    
    etiqueta3=tk.Label(ventana_solucion,font=("Arial",12,"bold"))
    if lista[-1] == "ns":
        etiqueta3.place(relx=0.13,rely=0.25)
        etiqueta3.configure(text="El sistema no tiene solucion",bg="DeepSkyBlue3")
        boton1.place(relx=0.4,rely=0.55)
    elif lista[-1] == "ms":
        ventana_solucion.minsize(350,80)
        ventana_solucion.maxsize(350,80)
        etiqueta3.place(relx=0.1,rely=0.25)
        etiqueta3.configure(text="El sistema tiene infinitas soluciones",bg="DeepSkyBlue3")
        boton1.place(relx=0.41,rely=0.55)
    else:
        a=60
        x=len(lista)
        letras=[chr(i+97) for i in range(0,x)]
        if x==2:
            ventana_solucion.minsize(120*x,100)
            ventana_solucion.maxsize(120*x,100)
        else:
            ventana_solucion.minsize(105*x,100)
            ventana_solucion.maxsize(105*x,100)
        etiquet=tk.Label(ventana_solucion,text="{",font=("Arial",24,"bold"),bg="DeepSkyBlue3")
        etiquet.place(x=35,y=17)
        for i in range(0,x):
            etiqueta3=tk.Label(ventana_solucion,font=("Arial",12,"bold"),text=f"{letras[i]}=",bg="DeepSkyBlue3")
            etiqueta3.place(x=a,y=28)
            a+=30
            caja=tk.Entry(ventana_solucion,width=3)
            caja.place(x=a,y=30)
            a+=30
            cajas_solucion.append(caja)
            if i==(x-1):
                etiqueta3=tk.Label(ventana_solucion,font=("Arial",24,"bold"),text="}",bg="DeepSkyBlue3")
                etiqueta3.place(x=a,y=17)
            else:
                etiqueta3=tk.Label(ventana_solucion,font=("Arial",12,"bold"),text=",",bg="DeepSkyBlue3")
                etiqueta3.place(x=a,y=28)
                a+=10
        boton1.place(relx=0.34,rely=0.62)
        for i in range(0,x):
            cajas_solucion[i].insert(0,str(lista[i]))
            
ventana_gauss.protocol("WM_DELETE_WINDOW",lambda: ventana.destroy())
ventana_solucion.protocol("WM_DELETE_WINDOW", lambda:ventana.destroy())
tamano_matriz.protocol("WM_DELETE_WINDOW", lambda:ventana.destroy())

ventana.mainloop()