package ag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Random;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class AlgoritmoGenetico {
    
    ArrayList populacao = new ArrayList();
    ArrayList populacaoAux = new ArrayList();
    ArrayList populacaoAux2 = new ArrayList();
    Random aleatorio = new Random();
    FunctionCalcExpression ordena = new FunctionCalcExpression();
    int escolhido1, escolhido2;
    Cromossomo cromossomo = new Cromossomo();
    Cromossomo cromossomo2 = new Cromossomo();
    Cromossomo cromossomo3 = new Cromossomo();
            
    public void rodaScriptCaderno(int tamanho){
        iniciaPopulacao(tamanho);
        
        for(int i = 1; i <= TelaConsole.ger; i++){
        
            steadystate();
            
            for(int j = 1; j <= populacao.size()-populacaoAux.size(); j++){
            	
            	//Se num aleatorio for menor que crossover
                if(aleatorio.nextDouble() <= TelaConsole.crossover){
                    escolhido1 = selecao();
                    escolhido2 = selecao();
                    crossover(escolhido1, escolhido2); //Realiza o crossover
                }
            }
            repovoa(); //Reaprova para repetir com os pais
            if(populacao.size() < TelaConsole.tamanho){
                completa();
            }
            
            //... Ordena para avaliação roleta!!!
            Collections.sort(populacao, new FunctionCalcExpression());
            
            imprime(populacao, i);
        }
        System.out.println("\nMaior avaliacao: " + populacao.get(0));
    }
    
    public void iniciaPopulacao(int tamanho){
        double individuoX, individuoY, individuoZ;
        //Inicia a populacao com o tamanho e os seus valores
        for(int i = 0; i <= tamanho - 1; i++){
        	
        	//Avaliacao + aptidao 
        	// Aptidao = min + [max - min / n - 1] * i ...
            individuoX = ((aleatorio.nextDouble() * (TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo);
            individuoY = ((aleatorio.nextDouble() * (TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo);
            individuoZ = ((aleatorio.nextDouble() * (TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo);
            populacao.add(new Cromossomo(individuoX,individuoY,individuoZ));
        }
        //Avaliação + Biblioteca de funcoes
        Collections.sort(populacao, new FunctionCalcExpression());
    }
    
    public void steadystate(){
        ListIterator list = populacao.listIterator();
        
        for(int i = 0; i<= populacao.size()-TelaConsole.m-1; i++){
            cromossomo = (Cromossomo) list.next();
            populacaoAux.add(cromossomo);
            populacaoAux2.add(cromossomo);
        }
    }
    
    public double avaliacao(double x, double y, double z){
        Calculable equacao;
        double resultado = 0;
        
        try{ 
               equacao = new ExpressionBuilder(FunctionCalcExpression.funcao)
                    .withVariable("x",x)
                    .withVariable("y",y)
                    .withVariable("z",z)
                    .build();
            resultado = equacao.calculate();
            }catch(UnknownFunctionException e){
                    e.printStackTrace();
                }catch(UnparsableExpressionException e){
                    e.printStackTrace();
                }
        return resultado;
    }
    
    public int selecao(){
        double soma = somaCromossomo();
        double roleta = (aleatorio.nextDouble() * (soma+1));
        double acc=0;
        int retorno = 0;
        
        for(int i=0; i <= populacaoAux.size()-1; i++){
            cromossomo = (Cromossomo) populacaoAux.get(i);
            acc = (acc + avaliacao(cromossomo.getIndividuoX(), cromossomo.getIndividuoY(), cromossomo.getIndividuoZ()));
            if(roleta <= acc){
                retorno = i;
                i = populacaoAux.size()+1;
            }
        }
        return retorno;
    }
    
    public void crossover(int i, int j){
        double x, y, z;
        cromossomo2 = (Cromossomo) populacaoAux.get(i);
        cromossomo = (Cromossomo) populacaoAux.get(j);
                
        x = (cromossomo.getIndividuoX()+cromossomo2.getIndividuoX())/2;
        y = (cromossomo.getIndividuoY()+cromossomo2.getIndividuoY())/2;
        z = (cromossomo.getIndividuoZ()+cromossomo2.getIndividuoZ())/2;
        
        //Se o numero de taxa de mutacao
        if(aleatorio.nextDouble() <= TelaConsole.mutacao){
        	
        	//  MUTAÇÃO 
            x = x + (aleatorio.nextDouble()* ( TelaConsole.maiorIntervalo -  TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo;
            y = y + (aleatorio.nextDouble()* ( TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo;
            z = z + (aleatorio.nextDouble()* ( TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo;
        } else {
        	manterGene();
        }
        
        cromossomo3 = new Cromossomo(x, y, z);
        populacaoAux2.add(cromossomo3); 
    }
    
   

	public void repovoa(){
        ListIterator i = populacaoAux2.listIterator();
        populacao = new ArrayList();
        populacaoAux = new ArrayList();
        int j = 0;

        while (i.hasNext()) {
            Cromossomo a = (Cromossomo) i.next();
            populacao.add(a);
        }
        populacaoAux2 = new ArrayList();
    }
    
    public void imprime(ArrayList b, int index){
        ListIterator i = b.listIterator();

        System.out.println("\n A " + index + "º geração teve o resultado :");
        while (i.hasNext()) {
            Cromossomo a = (Cromossomo) i.next();
            System.out.println(a);
        }
    }
    
    public double somaCromossomo(){
        ListIterator i = populacaoAux.listIterator();
        double soma = 0;

        while (i.hasNext()) {
            Cromossomo a = (Cromossomo) i.next();
            soma = (soma + avaliacao(a.getIndividuoX(), a.getIndividuoY(), a.getIndividuoZ()));
        }
        return soma;
    }
    
    public void completa(){
        double individuoX, individuoY, individuoZ;
        Cromossomo cromo;
        
        for(int i = 0; i <= TelaConsole.tamanho - populacao.size(); i++){
            individuoX = ((aleatorio.nextDouble() * (TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo);
            individuoY = ((aleatorio.nextDouble() * (TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo);
            individuoZ = ((aleatorio.nextDouble() * (TelaConsole.maiorIntervalo - TelaConsole.menorIntervalo + 1)) + TelaConsole.menorIntervalo);
            cromo = new Cromossomo(individuoX,individuoY,individuoZ);
            populacao.add(cromo);
        }
    }
    
    private void manterGene() {
    	double individuoX, individuoY, individuoZ;
        Cromossomo cromo;		
   	}
}