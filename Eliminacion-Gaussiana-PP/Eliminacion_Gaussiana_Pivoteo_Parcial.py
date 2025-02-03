import numpy as np
from fractions import Fraction as fr

def pivoteo_parcial (x,matriz):
    filas,columnas=matriz.shape
    q,z,a=0,0,0
    for i in range(x,filas):
        if matriz[i,x]<0:
            z=-matriz[i,x]
        else:
            z=matriz[i,x]        
        if a<z:
            a=z
            q=i
    if q==0:
        pass
    else:
        temp=np.array(matriz[q],dtype=object)
        matriz[q]=matriz[x]
        matriz[x]=temp

def eliminacion_gaussiana(matriz):
    filas,columnas=matriz.shape
    for i in range(0,columnas-2):
        x=i+1
        pivoteo_parcial(i,matriz)
        fila_pivote=np.array(matriz[i],dtype=object)
        while x<filas :
            if matriz[i,i]==0:
                pass
            else:
                y=matriz[x,i]/matriz[i,i]
                fila_actual=np.array(matriz[x],dtype=object)
                matriz[x]=fila_actual-(y*fila_pivote)
            x=1+x
    return matriz


def sustitucion_regresiva(matriz):
    eliminacion_gaussiana(matriz)
    filas,columnas=matriz.shape
    solucion=np.zeros(filas,dtype=object)
    if matriz[filas-1][columnas-2] ==0 and matriz[filas-1][columnas-1]==0:
        x=0
    elif matriz[filas-1][columnas-2] ==0:
        solucion=list(solucion)
        solucion.append("ns")
        return solucion
    else:
        x=matriz[filas-1][columnas-1]/matriz[filas-1][columnas-2]
    z=filas-1
    solucion[z]+=x
    z-=1
    while z>=0:
        if matriz[z,z] == 0:
            if (matriz[z,columnas-1]-matriz[z,:columnas-1]@solucion) ==0:
                solucion=list(solucion)
                solucion.append("ms")
            else:
                solucion=list(solucion)
                solucion.append("ns")
            return solucion
        else:
            x=(matriz[z,columnas-1]-matriz[z,:columnas-1]@solucion)/matriz[z,z]
            solucion[z]+=x
            z-=1
    return list(solucion)
        