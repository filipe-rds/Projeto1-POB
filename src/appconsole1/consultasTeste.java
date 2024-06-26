package appconsole1;

import regras_negocio.Fachada;

public class consultasTeste {

    public static void main(String[] args) {
        

        Fachada.inicializar();
        try{

            System.out.println(Fachada.veiculosAcimaDoRegistro(1));
            System.out.println("---------------------------------------------------");
            System.out.println(Fachada.veiculosNaData("25/06/2024"));
        }
        catch(Exception e){
            System.out.println(e.getMessage());

        }

    Fachada.finalizar();
    
}

}
