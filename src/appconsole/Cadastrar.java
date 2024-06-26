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

      // Cadastro de veículos
      Fachada.cadastrarVeiculo("ABC-1234");
      Fachada.cadastrarVeiculo("DEF-5678");
      Fachada.cadastrarVeiculo("GHI-9012");
      Fachada.cadastrarVeiculo("JKL-3456");
      Fachada.cadastrarVeiculo("MNO-7890");
      Fachada.cadastrarVeiculo("PQR-2345");
      Fachada.cadastrarVeiculo("STU-6789");
      Fachada.cadastrarVeiculo("VWX-0123");
      Fachada.cadastrarVeiculo("YZA-4567");
      Fachada.cadastrarVeiculo("BCD-8901");
      Fachada.cadastrarVeiculo("EFG-2345");
      Fachada.cadastrarVeiculo("HIJ-6789");
      Fachada.cadastrarVeiculo("KLM-0123");
      Fachada.cadastrarVeiculo("NOP-4567");
      Fachada.cadastrarVeiculo("QRS-8901");
      Fachada.cadastrarVeiculo("TUV-2345");
      Fachada.cadastrarVeiculo("WXY-6789");
      Fachada.cadastrarVeiculo("ZAB-0123");
      Fachada.cadastrarVeiculo("CDE-4567");
      Fachada.cadastrarVeiculo("FGH-8901");

      // Cadastro de registros de entrada e saída para cada veículo
      Fachada.cadastrarRegistro("ABC-1234", "entrada");
      Fachada.cadastrarRegistro("ABC-1234", "saida");
      Fachada.cadastrarRegistro("ABC-1234", "entrada");
      Fachada.cadastrarRegistro("ABC-1234", "saida");

      Fachada.cadastrarRegistro("DEF-5678", "entrada");
      Fachada.cadastrarRegistro("DEF-5678", "saida");
      Fachada.cadastrarRegistro("DEF-5678", "entrada");

      Fachada.cadastrarRegistro("GHI-9012", "entrada");
      Fachada.cadastrarRegistro("GHI-9012", "saida");

      Fachada.cadastrarRegistro("JKL-3456", "entrada");
      Fachada.cadastrarRegistro("JKL-3456", "saida");
      Fachada.cadastrarRegistro("JKL-3456", "entrada");

      Fachada.cadastrarRegistro("MNO-7890", "entrada");

      Fachada.cadastrarRegistro("PQR-2345", "entrada");
      Fachada.cadastrarRegistro("PQR-2345", "saida");

      Fachada.cadastrarRegistro("STU-6789", "entrada");
      Fachada.cadastrarRegistro("STU-6789", "saida");
      Fachada.cadastrarRegistro("STU-6789", "entrada");
      Fachada.cadastrarRegistro("STU-6789", "saida");

      Fachada.cadastrarRegistro("VWX-0123", "entrada");

      Fachada.cadastrarRegistro("YZA-4567", "entrada");
      Fachada.cadastrarRegistro("YZA-4567", "saida");
      Fachada.cadastrarRegistro("YZA-4567", "entrada");

      Fachada.cadastrarRegistro("BCD-8901", "entrada");
      Fachada.cadastrarRegistro("BCD-8901", "saida");

      Fachada.cadastrarRegistro("EFG-2345", "entrada");
      Fachada.cadastrarRegistro("EFG-2345", "saida");
      Fachada.cadastrarRegistro("EFG-2345", "entrada");

      Fachada.cadastrarRegistro("HIJ-6789", "entrada");
      Fachada.cadastrarRegistro("HIJ-6789", "saida");

      Fachada.cadastrarRegistro("KLM-0123", "entrada");

      Fachada.cadastrarRegistro("NOP-4567", "entrada");
      Fachada.cadastrarRegistro("NOP-4567", "saida");
      Fachada.cadastrarRegistro("NOP-4567", "entrada");

      Fachada.cadastrarRegistro("QRS-8901", "entrada");

      Fachada.cadastrarRegistro("TUV-2345", "entrada");
      Fachada.cadastrarRegistro("TUV-2345", "saida");

      Fachada.cadastrarRegistro("WXY-6789", "entrada");
      Fachada.cadastrarRegistro("WXY-6789", "saida");

      Fachada.cadastrarRegistro("ZAB-0123", "entrada");
      Fachada.cadastrarRegistro("ZAB-0123", "saida");

      Fachada.cadastrarRegistro("CDE-4567", "entrada");
      Fachada.cadastrarRegistro("CDE-4567", "saida");
      Fachada.cadastrarRegistro("CDE-4567", "entrada");
      Fachada.cadastrarRegistro("CDE-4567", "saida");

      Fachada.cadastrarRegistro("FGH-8901", "entrada");
      Fachada.cadastrarRegistro("FGH-8901", "saida");
      Fachada.cadastrarRegistro("FGH-8901", "entrada");
      Fachada.cadastrarRegistro("FGH-8901", "saida");

      System.out.println("Cadastros realizados com sucesso!");

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Fachada.finalizar();
  }
  
  public static void main(String[] args) {
    new Cadastrar();
  }
}