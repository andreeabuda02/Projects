import clase.interfata.Interfata;
import clase.model.Operatii;
import clase.model.Polinom;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/*
ADUNARE:
p1: 4x^5-3x^4+x^2-8x+1
p2: 3x^4-x^3+x^2+2x-1
rs: 4x^5-x^3+2x^2-6x

SCADERE:
p1: 4x^5-3x^4+x^2-8x+1
p2: 3x^4-x^3+x^2+2x-1
rs: 4x^5-6x^4+x^3-10x+2

INMULTIRE:
p1: 3x^2-x+1
p2: x-2
rs: 3x^3-7x^2+3x-2

IMPARTIRE:
p1: x^3-2x^2+6x-5
p2: x^2-1
rs: cat: x-2
    rest: 7x-7

DERIVARE:
p1: x^3-2x^2+6x-5
rs: 3x^2-4x+6

INTEGRARE:
p1: x^3+4x^2+5
rs: 0.25x^4+1.33x^3+5x

* */

public class CLasaTeste {
    Operatii op=new Operatii();
    @Test
    public void testareSuma(){
        Polinom p1=new Polinom();
        p1.adaugaMonom(2,3.0);
        p1.adaugaMonom(0,1.0);
        p1.adaugaMonom(1,-1.0);

        Polinom p2=new Polinom();
        p2.adaugaMonom(0,-2.0);
        p2.adaugaMonom(3,3.0);
        p2.adaugaMonom(1,1.0);

        Polinom p3=new Polinom();
        p3.adaugaMonom(3,3.0);
        p3.adaugaMonom(2,3.0);
        p3.adaugaMonom(1,0.0);
        p3.adaugaMonom(0,-1.0);

        Polinom suma = op.adunarePolinoame(p1,p2);

        assertEquals(p3.toString(), suma.toString());
    }


    @Test
    public void testareDiferenta(){
        Polinom p1=new Polinom();
        p1.adaugaMonom(2,3.0);
        p1.adaugaMonom(1,-1.0);
        p1.adaugaMonom(0,1.0);

        Polinom p2=new Polinom();
        p2.adaugaMonom(3,3.0);
        p2.adaugaMonom(1,1.0);
        p2.adaugaMonom(0,-2.0);

        Polinom p3=new Polinom();
        p3.adaugaMonom(3,-3.0);
        p3.adaugaMonom(2,3.0);
        p3.adaugaMonom(1,-2.0);
        p3.adaugaMonom(0,3.0);

        Polinom diferenta = op.scaderePolinoame(p1,p2);

        assertEquals(p3.toString(), diferenta.toString());
    }

    @Test
    public void testareInmultire(){
        Polinom p1=new Polinom();
        p1.adaugaMonom(2,3.0);
        p1.adaugaMonom(1,-1.0);
        p1.adaugaMonom(0,1.0);

        Polinom p2=new Polinom();
        p2.adaugaMonom(3,3.0);
        p2.adaugaMonom(1,1.0);
        p2.adaugaMonom(0,-2.0);

        Polinom p3=new Polinom();
        p3.adaugaMonom(5,9.0);
        p3.adaugaMonom(4,-3.0);
        p3.adaugaMonom(3,6.0);
        p3.adaugaMonom(2,-7.0);
        p3.adaugaMonom(1,3.0);
        p3.adaugaMonom(0,-2.0);

        Polinom produs = op.inmultirePolinoame(p1,p2);

        assertEquals(p3.toString(), produs.toString());
    }

    @Test
    public void testareImpartire(){
        Polinom p1=new Polinom();
        p1.adaugaMonom(3,1.0);
        p1.adaugaMonom(2,-2.0);
        p1.adaugaMonom(1,6.0);
        p1.adaugaMonom(0,4.0);

        Polinom p2=new Polinom();
        p2.adaugaMonom(2,1.0);
        p2.adaugaMonom(1,2.0);
        p2.adaugaMonom(0,3.0);

        Polinom p3=new Polinom();
        p3.adaugaMonom(1,1.0);
        p3.adaugaMonom(0,-4.0);

        Polinom p4=new Polinom();
        p4.adaugaMonom(1,11.0);
        p4.adaugaMonom(0,16.0);

        Polinom[] rezultat = op.impartirePolinoame(p1, p2);
        Polinom cat  = rezultat[0];
        Polinom rest = rezultat[1];

        assertEquals(p3.toString(), cat.toString());
        assertEquals(p4.toString(), rest.toString());
    }

    @Test
    public void testareDerivare(){
        Polinom p1=new Polinom();
        p1.adaugaMonom(2,3.0);
        p1.adaugaMonom(0,1.0);
        p1.adaugaMonom(1,-1.0);

        Polinom p2=new Polinom();
        p2.adaugaMonom(1,6.0);
        p2.adaugaMonom(0,-1.0);

        Polinom der = op.derivarePolinom(p1);

        assertEquals(der.toString(), p2.toString());
    }

    @Test
    public void testareIntegrare(){
        Polinom p1=new Polinom();
        p1.adaugaMonom(2,3.0);
        p1.adaugaMonom(0,1.0);
        p1.adaugaMonom(1,-1.0);

        Polinom p2=new Polinom();
        p2.adaugaMonom(3,1.0);
        p2.adaugaMonom(2,-0.5);
        p2.adaugaMonom(1,1.0);

        Polinom intr = op.integrarePolinom(p1);

        assertEquals(intr.toString(), p2.toString());
    }

    @Test
    public void testareIntefata(){

        Interfata calculator1 = new Interfata("Calculator");
    }
}
