package org.example;

import clase.interfata.Interfata;

public class App
{
    public static void main(String[] args) {

        /*
        Clase.Polinom p1=new Clase.Polinom();
        p1.adaugaMonom(2,3.0);
        p1.adaugaMonom(0,1.0);
        p1.adaugaMonom(1,-1.0);

        Clase.Polinom p2=new Clase.Polinom();
        p2.adaugaMonom(0,-2.0);
        //p2.adaugaMonom(3,3.0);
        p2.adaugaMonom(1,1.0);

        Clase.Polinom p3=new Clase.Polinom();
        p3.adaugaMonom(2,-2.0);
        p3.adaugaMonom(3,1.0);
        p3.adaugaMonom(1,6.0);
        p3.adaugaMonom(0,-5.0);

        Clase.Polinom p4=new Clase.Polinom();
        p4.adaugaMonom(2,1.0);
        p4.adaugaMonom(0,-1.0);

        Clase.Operatii op=new Clase.Operatii();
        Clase.Polinom suma=op.adunarePolinoame(p1,p2);
        Clase.Polinom diferenta=op.scaderePolinoame(p1,p2);
        Clase.Polinom produs=op.inmultirePolinoame(p1,p2);
        Clase.Polinom derv=op.derivarePolinom(p1);
        Clase.Polinom integ=op.integrarePolinom(p2);
        //Clase.Polinom div=op.impartirePolinoame(p1,p2);
        Clase.Polinom catulImpartirii = new Clase.Polinom();
        Clase.Polinom restulImpartirii = new Clase.Polinom();
        Clase.Operatii operatii = new Clase.Operatii();


        System.out.println("Polinoame:");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println();
        System.out.print("Adunare:");System.out.println(suma);
        System.out.print("Scadere:");System.out.println(diferenta);
        System.out.print("Inmultire:");System.out.println(produs);
        System.out.print("Impartire:");

        try {
            Clase.Polinom[] rezultat = op.impartirePolinoame(p1, p2);
            System.out.println("Catul impartirii este: " + rezultat[0].toString());
            System.out.println("Restul impartirii este: " + rezultat[1].toString());
        } catch (ArithmeticException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.print("Derivare:");System.out.println(derv);
        System.out.print("Integrare:");System.out.println(integ);
        System.out.print("Evaluare:");System.out.println( p1.evaluare(2));
         */

        Interfata calc = new Interfata("Calculator");

    }
}
