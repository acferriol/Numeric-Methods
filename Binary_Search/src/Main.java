import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;

import java.util.Scanner;

class Ecuacion{
    Jep ecuacion;
    public Ecuacion(String ecuacionR) throws JepException{
        ecuacion = new Jep();
        ecuacion.parse(ecuacionR);
    }
    public boolean semiAmpl(double i , double d , double tolerancia){
        return Math.abs(i - d)/2 < tolerancia;
    }
    public boolean valor_eval_pequenio(double eval , double tolerancia){
        return Math.abs(eval) < tolerancia;
    }
    public boolean error_absoluto(double eval , double evalLS , double tolerancia){
        return Math.abs(eval - evalLS) < tolerancia;
    }
    public boolean error_relativo(double eval , double evalLS , double tolerancia) {
        return Math.abs(eval - evalLS)/Math.abs(eval) < tolerancia;
    }
    public boolean criterios(double i , double d , double tolerancia , double eval , double evalLI , double evalLS , int criterio){
        if(criterio == 1) return semiAmpl(i , d , tolerancia);
        if(criterio == 2) return valor_eval_pequenio(eval , tolerancia);
        if(criterio == 3) return error_absoluto(eval , evalLS , tolerancia);
        return error_relativo(eval , evalLS , tolerancia);
    }
    public double BiseccionMethodR(double i , double d, double tolerancia , double evalLI , double evalLS  , int iteraciones , int contador , int criterio) throws JepException{
        double medio = (i + d)/2;
        ecuacion.setVariable("x", medio);
        double eval = ecuacion.evaluateD();
        if (eval == 0 || iteraciones == contador) return medio;
        if(criterios(i , d , tolerancia , eval , evalLI , evalLS , criterio)) return medio;
        System.out.println(eval);
        if (eval * evalLI > 0) return BiseccionMethodR(medio , d , tolerancia , eval , evalLS , iteraciones , contador + 1 , criterio);
        return BiseccionMethodR(i , medio , tolerancia , evalLI , eval , iteraciones , contador + 1, criterio);
    }
    public double BiseccionMethod(double i , double d , double tolerancia , int criterio , int iteraciones) throws JepException{
        ecuacion.addVariable("x" , i);
        double evalLI = ecuacion.evaluateD();
        ecuacion.addVariable("x" , d);
        double evalLS = ecuacion.evaluateD();
        if(evalLI == 0) return i;
        if(evalLS == 0) return d;
        return BiseccionMethodR(i , d , tolerancia , evalLI , evalLS , iteraciones , 0 , criterio );
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ecuacion obj;
        try{
            System.out.println("Función:");
            obj = new Ecuacion(sc.nextLine());
            System.out.println("Aproximacion inicial:");
            double aproxi = sc.nextDouble();
            System.out.println("Aproximacion final");
            double aproxf = sc.nextDouble();
            System.out.println("Tolerancia:");
            double tolerancia = sc.nextDouble();
            System.out.println("Criterio:");
            int criterio = sc.nextInt();
            System.out.println("Iteraciones Maximas");
            int iteraciones = sc.nextInt();
            System.out.println(obj.BiseccionMethod(aproxi , aproxf , tolerancia , criterio, iteraciones));
            System.out.println(obj.BiseccionMethod(1 , 2 , 0.000000001 , 1 , 50));
            System.out.println(obj.BiseccionMethod(1 , 2 , 0.000000001 , 2 , 50));
            System.out.println(obj.BiseccionMethod(1 , 2 , 0.000000001 , 3 , 50));
            System.out.println(obj.BiseccionMethod(1 , 2 , 0.000000001 , 4 , 50));
        }catch (JepException e){
            System.out.println("Ha ocurrido un error");
        }
    }
}