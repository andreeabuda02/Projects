package clase.model;

import java.text.DecimalFormat;
import java.util.*;

public class Polinom {
    private Map<Integer, Double> termeni = new HashMap<>();

    public Polinom() {
    }

    public Map<Integer, Double> getTermeni() {
        return termeni;
    }

    public void adaugaMonom(Integer exp, Double coef) {
        if (coef != 0) {
            if(!termeni.containsKey(exp))
                termeni.put(exp, coef);
            else {
                double nouCoef = termeni.get(exp)+coef;
                termeni.put(exp, nouCoef);
            }
        }
    }


    @Override

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String[] sir=new String[termeni.size()+1];
        DecimalFormat f=new DecimalFormat("0.##");
        int i=0;
        for (Map.Entry<Integer, Double> monom : termeni.entrySet()) {
            if (monom.getValue() == 0) {
                continue; // monomul cu un coeficient 0 nu va fi afisat
            }
            if (i < termeni.size()-1) {
                String s=(monom.getValue() < 0 ? " - " : " + ");
                sir[i]=s; // semnul "+" sau "-"
            }
            if (Math.abs(monom.getValue()) != 1 || monom.getKey() == 0) {
                if(sir[i]!=null)
                    sir[i]=sir[i]+f.format(Math.abs(monom.getValue())); // coeficientul, ignorand semnul
                else
                    sir[i]=f.format(Math.abs(monom.getValue())); // coeficientul, ignorand semnul
            }
            if (monom.getKey() > 0) {
                if(sir[i]!=null)
                    sir[i]=sir[i]+(monom.getKey() == 1 ? "x" : "x^" + monom.getKey()); // x sau x^n, n != 1
                else
                    sir[i]=(monom.getKey() == 1 ? "x" : "x^" + monom.getKey()); // x sau x^n, n != 1
            }
            i++;
        }
        if (i == 0) {
            return "0"; // polinomul cu toate monoamele cu coeficientul 0
        }
        i--;
        while(i>=0){
            if(!sir[i].equals("")) {
                sb.append(sir[i]);
            }
            i--;
        }
        return sb.toString();
    }

    /* public Clase.Polinom sorteaza() {
         List<Map.Entry<Integer, Double>> monomi = new ArrayList<>(this.getTermeni().entrySet());

         monomi.sort((m1, m2) -> m2.getKey().compareTo(m1.getKey()));

         Clase.Polinom rezultat = new Clase.Polinom("2x^3+4x^2+5x-42");
         for(Map.Entry<Integer, Double> m : monomi) {
             rezultat.adaugaMonom(m.getKey(), m.getValue());
         }

         return rezultat;
     }
 */

    public int getGrad() {
        int grad = 0;
        for (int exp : termeni.keySet()) {
            if (exp > grad && termeni.get(exp) != 0) {
                grad = exp;
            }
        }
        return grad;
    }

    public double evaluare(double x) {
        double rezultat = 0.0;
        for (Map.Entry<Integer, Double> intrare : termeni.entrySet()) {
            int degree = intrare.getKey();
            double coefficient = intrare.getValue();
            rezultat += coefficient * Math.pow(x, degree);
        }
        return rezultat;
    }
}
