package appconsole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modelo.*;
import regras_negocio.Fachada;
import java.util.List;


public class Consultar {

  public Consultar() {
    LocalDateTime dataAtual = LocalDateTime.now();
    String dataString = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    Fachada.inicializar();
    try {
      System.out.println("Consultas... \n");
      System.out.println("\nTodos os registros na data: " + dataString + "\n");
      List<Registro> registros = Fachada.registrosNaData(dataString);
      for (Registro r : registros) {
        System.out.println(r);
      }

      System.out.println("\nVeículos que tiveram registro na data: " + dataString + "\n");
      List<Veiculo> veiculos = Fachada.veiculosNaData(dataString);
      for (Veiculo v : veiculos) {
        System.out.println(v.getPlaca());
      }

      System.out.println("\nVeículos com mais de 2 registros\n");
      List<Veiculo> veiculos2 = Fachada.veiculosAcimaDoRegistro(2);
      for (Veiculo v : veiculos2) {
        System.out.println(v.getPlaca());
      }

    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Fachada.finalizar();
    System.out.println("\nFim de consultas!");
  }

  public static void main(String[] args) {
    new Consultar();
  }
}