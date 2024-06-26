package appconsole1;

import regras_negocio.Fachada;

public class carroTest {

    public static void main(String[] args) {
        
        Fachada.inicializar();

        try{
            Fachada.cadastrarVeiculo("ABC-1001");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Fachada.excluirVeiculo("ABC-1001");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Fachada.cadastrarVeiculo("ABC-9992");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Fachada.buscarVeiculo("ABC-9992");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Fachada.buscarVeiculo("ABC-9891");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Fachada.listarVeiculos();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println(Fachada.buscarVeiculo("ABC-9992"));
        }
        catch(Exception e){
            System.out.println(e.getMessage()); }

        Fachada.finalizar();
    }
    
}
