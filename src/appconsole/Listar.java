package appconsole;

import regras_negocio.Fachada;
import modelo.*;

public class Listar {
  public Listar() {
    Fachada.inicializar();
    try {
      System.out.println("Listando veículos...");
      for (Veiculo v : Fachada.listarVeiculos()) {
        System.out.println(v);
      }
      System.out.println("Listando registros...");
      for (Registro r : Fachada.listarRegistros()) {
        System.out.println(r);
      }
      System.out.println("Listando arrecadações...");
      for (Arrecadacao a : Fachada.listarArrecadacoes()) {
        System.out.println(a);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Fachada.finalizar();
  }

  public static void main(String[] args) {
   new Listar();
  }
  
}