package clase.model;

import java.util.Map;
public class Operatii {
    public Operatii() {

    }

    public Polinom adunarePolinoame(Polinom pol1, Polinom pol2) {
        Polinom pol3 = new Polinom();
        for (Map.Entry<Integer, Double> p1 : pol1.getTermeni().entrySet()) {
            int gasit = 0;
            for (Map.Entry<Integer, Double> p2 : pol2.getTermeni().entrySet()) {
                if (p1.getKey().equals(p2.getKey())) {
                    pol3.adaugaMonom(p1.getKey(), p1.getValue() + p2.getValue());
                    gasit = 1;
                }
            }
            if(gasit==0){
                pol3.adaugaMonom(p1.getKey(),p1.getValue());
            }
        }

        for (Map.Entry<Integer, Double> p2 : pol2.getTermeni().entrySet()) {
            int gasit = 0;
            for (Map.Entry<Integer, Double> p1 : pol1.getTermeni().entrySet()) {
                if (p1.getKey().equals(p2.getKey())) {
                    gasit = 1;
                    break;
                }
            }
            if(gasit==0){
                pol3.adaugaMonom(p2.getKey(),p2.getValue());
            }
        }
        return pol3;
    }

    public Polinom scaderePolinoame(Polinom pol1, Polinom pol2) {
        Polinom pol3 = new Polinom();
        for (Map.Entry<Integer, Double> p1 : pol1.getTermeni().entrySet()) {
            int gasit = 0;
            for (Map.Entry<Integer, Double> p2 : pol2.getTermeni().entrySet()) {
                if (p1.getKey().equals(p2.getKey())) {
                    pol3.adaugaMonom(p1.getKey(), p1.getValue() - p2.getValue());
                    gasit = 1;
                }
            }
            if(gasit==0){
                pol3.adaugaMonom(p1.getKey(),p1.getValue());
            }
        }

        for (Map.Entry<Integer, Double> p2 : pol2.getTermeni().entrySet()) {
            int gasit = 0;
            for (Map.Entry<Integer, Double> p1 : pol1.getTermeni().entrySet()) {
                if (p1.getKey().equals(p2.getKey())) {
                    gasit = 1;
                    break;
                }
            }
            if(gasit==0){
                pol3.adaugaMonom(p2.getKey(),(-1)*p2.getValue());
            }
        }
        return pol3;
    }

    public Polinom inmultirePolinoame(Polinom pol1, Polinom pol2){
        Polinom rezultat = new Polinom();
        for(Map.Entry<Integer, Double> p1 : pol1.getTermeni().entrySet()) {
            for(Map.Entry<Integer, Double> p2 : pol2.getTermeni().entrySet()) {
                int exp = p1.getKey() + p2.getKey();
                double coef = p1.getValue() * p2.getValue();
                rezultat.adaugaMonom(exp, coef);
            }
        }
        return rezultat;
    }

    public Polinom[] impartirePolinoame(Polinom pol1, Polinom pol2) throws ArithmeticException {
        if (pol2.getTermeni().isEmpty()) {
            throw new ArithmeticException("Impartirea la zero nu este permisa!");
        }
        Polinom cat = new Polinom();
        Polinom rest = pol1;

        int grad1 = rest.getGrad();
        int grad2 = pol2.getGrad();

        while (grad1 >= grad2 && !rest.getTermeni().isEmpty()){
            int exp = grad1 - grad2;
            double coef = rest.getTermeni().get(grad1) / pol2.getTermeni().get(grad2);

            Polinom termen = new Polinom();
            termen.adaugaMonom(exp, coef);
            cat = adunarePolinoame(cat, termen);

            Polinom produs = inmultirePolinoame(termen, pol2);
            rest = scaderePolinoame(rest, produs);

            grad1 = rest.getGrad();
        }

        Polinom[] rezultat = new Polinom[2];
        rezultat[0] = cat;
        rezultat[1] = rest;
        return rezultat;
    }


    public Polinom derivarePolinom(Polinom pol) {

        Polinom polD=new Polinom();
        for (Map.Entry<Integer, Double> p1 : pol.getTermeni().entrySet()) {
            polD.adaugaMonom(p1.getKey() - 1, p1.getValue() * p1.getKey());
        }
        return polD;
    }

    public Polinom integrarePolinom(Polinom pol) {
        Polinom rezultat = new Polinom();
        for (Map.Entry<Integer, Double> termen : pol.getTermeni().entrySet()) {
            double coeficientIntegral = termen.getValue() / (termen.getKey() + 1);
            int putereIntegrala = termen.getKey() + 1;
            rezultat.adaugaMonom(putereIntegrala, coeficientIntegral);
        }
        return rezultat;
    }
}
