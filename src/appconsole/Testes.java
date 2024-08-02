package appconsole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Teste da Fachada
 */

import regras_negocio.Fachada;
import modelo.*;

public class Testes {

    public static void main(String[] args) {
        // Inicializar a Fachada
        Fachada.inicializar();

        // Dados para teste
        String placa1 = "ABC1234";
        String placa2 = "DEF5678";
        String placa3 = "GHI9012";

        LocalDateTime dataAtual = LocalDateTime.now();
        String dataString = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            // Cadastro de veículos
            Fachada.cadastrarVeiculo(placa1);
            Fachada.cadastrarVeiculo(placa2);
            Fachada.cadastrarVeiculo(placa3);
            // Cadastro de arrecadação
            Fachada.cadastrarArrecadacao(dataString);
        } catch (Exception e) {
            System.out.println("Dados de teste--->" + e.getMessage());
        }

        System.out.println("\n-------TESTE DE EXCEÇÕES LANÇADAS PELOS MÉTODOS DA FACHADA--------\n");

        // Testes de exceções
        try {
            Fachada.cadastrarVeiculo(placa1);
            System.out.println("*************1--->Não lançou exceção para: cadastrar veículo duplicado");
        } catch (Exception e) {
            System.out.println("1. ok---> " + e.getMessage());
        }

        try {
            Fachada.excluirVeiculo(placa1);
            Fachada.excluirVeiculo(placa1); // tentativa de excluir novamente
            System.out.println("*************2--->Não lançou exceção para: excluir veículo inexistente");
        } catch (Exception e) {
            System.out.println("2. ok---> " + e.getMessage());
        }

        // Testar cadastrar registro para veículo já dentro
        try {
            Fachada.cadastrarRegistro(placa2, "entrada");
            Fachada.cadastrarRegistro(placa2, "entrada");
            System.out.println(
                    "*************3--->Não lançou exceção para: cadastrar registro quando veículo já está dentro");
        } catch (Exception e) {
            System.out.println("3. ok---> " + e.getMessage());
        }

        // Testar cadastrar registro de saída sem registro de entrada
        try {
            Fachada.cadastrarRegistro(placa3, "saida");
            System.out.println(
                    "*************4--->Não lançou exceção para: cadastrar registro de saída sem registro de entrada");
        } catch (Exception e) {
            System.out.println("4. ok---> " + e.getMessage());
        }

        // Testar exclusão de veículo com registro de entrada
        try {
            Fachada.cadastrarRegistro(placa3, "entrada");
            Fachada.excluirVeiculo(placa3);
            System.out.println("*************5--->Não lançou exceção para: excluir veículo com registro de entrada");
        } catch (Exception e) {
            System.out.println("5. ok---> " + e.getMessage());
        }

        // Testes de arrecadação
    
        try {
            Fachada.cadastrarArrecadacao(dataString); // Tentar cadastrar arrecadação duplicada
            System.out.println("*************6--->Não lançou exceção para: cadastrar arrecadação duplicada.");
        } catch (Exception e) {
            System.out.println("6. ok---> " + e.getMessage());
        }

        try {
            Fachada.excluirArrecadacao("01/01/1977"); // Tentar excluir arrecadação que já foi excluída
            System.out.println("*************7--->Não lançou exceção para: excluir arrecadação inexistente.");
        } catch (Exception e) {
            System.out.println("7. ok---> " + e.getMessage());
        }

        // Excluir veículos restantes após os testes
        try {
            ArrayList <Registro> vazio = new ArrayList<>();
            Veiculo v3 = Fachada.buscarVeiculo(placa3);
            v3.setRegistros(vazio);
            
            Veiculo v2 = Fachada.buscarVeiculo(placa2);
            v2.setRegistros(null);

            Fachada.excluirVeiculo(placa3);
            Fachada.excluirVeiculo(placa2);

            Fachada.excluirArrecadacao(dataString); 
            System.out.println("Dados gerados excluídos com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir dados gerados--->" + e.getMessage());
        }

        // Finalizar a Fachada
        Fachada.finalizar();
    }
}
