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
      System.out.println("consultas... \n");
      System.out.println("Todos os registros na data: " + dataString);
      List<Registro> registros = Fachada.registrosNaData(dataString);
      for (Registro r : registros) {
        System.out.println(r);
      }

      System.out.println("Veículos que tiveram registro na data: " + dataString);
      List<Veiculo> veiculos = Fachada.veiculosNaData(dataString);
      for (Veiculo v : veiculos) {
        System.out.println(v);
      }

      System.out.println("Veículos com mais de 2 registros");
      List<Veiculo> veiculos2 = Fachada.veiculosAcimaDoRegistro(2);
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