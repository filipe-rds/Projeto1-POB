package appconsole;

import modelo.*;
import regras_negocio.Fachada;
import java.util.List;


public class Consultar {

  public Consultar() {
    try {
      System.out.println("consultas... \n");
      System.out.println("Todos os registros na data: 2021-06-01");
      List<Registro> registros = Fachada.RegistrosNaData("2021-06-01");
      for (Registro r : registros) {
        System.out.println(r);
      }

      System.out.println("Veículos que tiveram registro na data: 2021-06-01");
      List<Veiculo> veiculos = Fachada.VeiculosNaData("2021-06-01");
      for (Veiculo v : veiculos) {
        System.out.println(v);
      }

      System.out.println("Veículos com mais de 2 registros");
      List<Veiculo> veiculos2 = Fachada.VeiculosAcimaDoRegistro(2);
      for (Veiculo v : veiculos2) {
        System.out.println(v);
      }

    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Fachada.inicializar();
    System.out.println("Fim de consultas");
  }

  public static void main(String[] args) {
    new Consultar();
  }
}
