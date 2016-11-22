package ag;

import java.util.Comparator;

public class FunctionCalcExpression implements Comparator {
    static AlgoritmoGenetico exec = new AlgoritmoGenetico();
    static double teste, teste2;
    static String funcao;
    
    @Override
    public int compare(Object o1, Object o2) {
        if (!(o1 instanceof Cromossomo) || !(o2 instanceof Cromossomo)) {
            return 0;
        }
        Cromossomo c1=(Cromossomo)o1;
        Cromossomo c2=(Cromossomo)o2;
        
        teste = exec.avaliacao(c1.getIndividuoX(), c1.getIndividuoY(), c1.getIndividuoZ());
        teste2 = exec.avaliacao(c2.getIndividuoX(), c2.getIndividuoY(), c2.getIndividuoZ());
                
        if(teste < teste2)
            return 1;
        else if(teste > teste2) 
            return -1;
        else
            return 0;
    }
}
