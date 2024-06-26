package appconsole;

import modelo.*;
import regras_negocio.Fachada;
import java.util.List;


public class Consultar {

  public Consultar() {

    Fachada.inicializar();
    try {
      System.out.println("consultas... \n");
      System.out.println("Todos os registros na data: 2024/06/25");
      List<Registro> registros = Fachada.registrosNaData("25/06/2024");
      for (Registro r : registros) {
        System.out.println(r);
      }

      System.out.println("Veículos que tiveram registro na data: 2021/06/01");
      List<Veiculo> veiculos = Fachada.veiculosNaData("25/06/2024");
      for (Veiculo v : veiculos) {
        System.out.println(v);
      }

      System.out.println("Veículos com mais de 2 registros");
      List<Veiculo> veiculos2 = Fachada.veiculosAcimaDoRegistro(0);
      for (Veiculo v : veiculos2) {
        System.out.println(v);
      }

    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Fachada.finalizar();
    System.out.println("Fim de consultas");
  }

  public static void main(String[] args) {
    new Consultar();
  }
}