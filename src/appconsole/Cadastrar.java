package appconsole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import regras_negocio.*;

public class Cadastrar {
  public Cadastrar() {
    Fachada.inicializar();

    LocalDateTime dataAtual = LocalDateTime.now();
    String dataString = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    try {
      Fachada.cadastrarArrecadacao(dataString);

      Fachada.cadastrarVeiculo("AAA-1111");
      Fachada.cadastrarVeiculo("BBB-2222");
      Fachada.cadastrarVeiculo("CCC-3333");
      Fachada.cadastrarVeiculo("DDD-4444");

      Fachada.cadastrarRegistro("AAA-1111", "entrada");
      Fachada.cadastrarRegistro("AAA-1111", "saida");
      Fachada.cadastrarRegistro("AAA-1111", "entrada");

      Fachada.cadastrarRegistro("BBB-2222", "entrada");
      Fachada.cadastrarRegistro("BBB-2222", "saida");

      Fachada.cadastrarRegistro("CCC-3333", "entrada");
      Fachada.cadastrarRegistro("CCC-3333", "saida");
      Fachada.cadastrarRegistro("CCC-3333", "entrada");

      Fachada.cadastrarRegistro("DDD-4444", "entrada");

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Fachada.finalizar();
  }
  
  public static void main(String[] args) {
    new Cadastrar();
  }
}
