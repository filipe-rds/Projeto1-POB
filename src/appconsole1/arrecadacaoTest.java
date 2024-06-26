package appconsole1;

import regras_negocio.Fachada;

public class arrecadacaoTest {

    public static void main(String[] args) {
        
        Fachada.inicializar();

        try{
            Fachada.cadastrarArrecadacao("25/06/2024");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Fachada.excluirArrecadacao("23/06/2024");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Fachada.cadastrarArrecadacao("24/06/2024");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Fachada.buscarArrecadacao("24/06/2024");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Fachada.buscarArrecadacao("22/06/2024");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Fachada.listarArrecadacoes();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Fachada.alterarArrecadacao("24/06/2024",10.0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        

        Fachada.finalizar();

        
    }
    
}
