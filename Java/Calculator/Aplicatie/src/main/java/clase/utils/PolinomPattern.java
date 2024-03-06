package clase.utils;

import clase.model.Polinom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolinomPattern {

    //"([+-]?\\d*)?(x(\\^\\d+)?)?"
    private Pattern patternVerificare = Pattern.compile("([+-]?\\d*(\\.\\d+)?(x(\\^[0-9]+)?)?)+");
    private Pattern pattern = Pattern.compile("([+-]?\\d*(\\.\\d+)?(x(\\^[0-9]+)?)?)");

    public void crearePolinom(String text, Polinom polinom1) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            if(matcher.group().length()>0){
                String[] grupuri;
                double coeficient=1.0;
                int exponent=0;
                boolean existaPutere = false;
                boolean adaugare = false;
                grupuri = matcher.group().split("[^0-9.]");
                if((matcher.group().length()==1)||(grupuri.length==0))
                    adaugare=true;
                if(matcher.group().contains("x"))
                    exponent=1;
                if((matcher.group().charAt(0)=='x')||((matcher.group().length()>=2)&&((matcher.group().charAt(0)=='+'||matcher.group().charAt(0)=='-')&&(matcher.group().charAt(1)=='x'))))
                    existaPutere=true;
                int semnCoeficient=1;
                if(matcher.group().charAt(0)=='-')
                    semnCoeficient=-1;
                for (String g:grupuri) {
                    if(!g.equals("")){
                        if(existaPutere==false){
                            coeficient=Double.parseDouble(g);
                            existaPutere=true;
                        }
                        else{
                            exponent=Integer.parseInt(g);
                        }
                        adaugare=true;
                    }
                }
                if(adaugare==true){
                    polinom1.adaugaMonom(exponent, semnCoeficient * coeficient);
                }
            }
        }
    }
}
