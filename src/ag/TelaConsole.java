package ag;

import java.util.Scanner;

public class TelaConsole {
    static int tamanho, m, ger, maiorIntervalo, menorIntervalo;
    static double mutacao, crossover;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        
        
        /***
         * DADOS DO USUARIO
         */
        System.out.print("Função: ");
        FunctionCalcExpression.funcao = scanner.nextLine();
                
        System.out.print("Tamanho da população: ");
        tamanho = scanner.nextInt();
        
        System.out.print("Taxa de Crossover: ");
        crossover = scanner.nextDouble();
        
        System.out.print("Taxa de Mutacao: ");
        mutacao = scanner.nextDouble();
        
        System.out.print("Numero de Geracoes: ");
        ger = scanner.nextInt();
        
        System.out.print("Steady-State: ");
        m = scanner.nextInt();
        
        System.out.print("Menor intervalo: ");
        menorIntervalo = scanner.nextInt();
        
        System.out.print("Maior intervalo: ");
        maiorIntervalo= scanner.nextInt();

        
        /***
         * CODIGO DO CADERNO
         */
        ag.rodaScriptCaderno(tamanho);
        
    }
    
}
