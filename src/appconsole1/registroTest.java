package appconsole1;

import regras_negocio.Fachada;

public class registroTest {
    
    public static void main(String[] args){
        // TODO code application logic here 

        Fachada.inicializar(); //ok

        try{

            Fachada.cadastrarRegistro("ABC-9992","entrada");
            System.out.println("registro1");
            Fachada.cadastrarRegistro("ABC-9992","saida");
            Fachada.cadastrarRegistro("ABC-9992","entrada");
            Fachada.cadastrarRegistro("ABC-9992","saida");
            Fachada.cadastrarRegistro("ABC-9992","entrada");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println(Fachada.listarRegistros());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Fachada.excluirRegistro(1);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println(Fachada.buscarRegistro(2));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        Fachada.finalizar();


       
    }

}