package regras_negocio;


import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import daodb4o.DAO;
import daodb4o.DAOVeiculo;
import daodb4o.DAORegistro;
import daodb4o.DAOArrecadacao;
import modelo.Veiculo;
import modelo.Registro;
import modelo.Arrecadacao;


public class Fachada {

    private Fachada() {}

	private static DAOVeiculo daoveiculo = new DAOVeiculo();  
	private static DAORegistro daoaregistro = new DAORegistro(); 
	private static DAOArrecadacao daoarrecadacao = new DAOArrecadacao();

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

    // CRUD Veiculo

    /*  
        --> Método para cadastrar um novo veículo no sistema de estacionamento.
        Fluxo: ele cria um novo veículo com a placa informada e o adiciona ao banco de dados.
        Exceções: se o veículo já estiver cadastrado, lança uma exceção.
        Retorno: retorna o veículo cadastrado.
    */
    public static Veiculo cadastrarVeiculo(String placa) throws Exception {
        DAO.begin();
        Veiculo veiculo = daoveiculo.read(placa);
        if (veiculo != null)
            throw new Exception("Veiculo ja cadastrado:" + placa);
        veiculo = new Veiculo(placa);
        daoveiculo.create(veiculo);
        DAO.commit();
        return veiculo;
    }
    
    /*
     * --> Método para adicionar um registro a um veículo cadastrado.
     * Fluxo: ele busca o veículo com a placa informada e adiciona o registro a ele.
     * Exceções: se o veículo não existir, lança uma exceção.
     * Retorno: não há retorno.
     */
    public static void adicionarRegistroVeiculo(String placa, Registro registro) throws Exception {

        DAO.begin();
        Veiculo veiculo = buscarVeiculo(placa);
        if (veiculo == null) {
            throw new Exception("Veiculo nao existe: " + placa);
        }
        veiculo.getRegistros().add(registro);
        daoveiculo.update(veiculo);
        DAO.commit();

    }

    /*  
        --> Método para remover um registro de um veículo cadastrado.
        Fluxo: ele busca o veículo com a placa informada e remove o registro dele.
        Exceções: se o veículo não existir, lança uma exceção.
        Retorno: não há retorno.
    */
    public static void removerRegistroVeiculo(String placa, Registro registro) throws Exception {

        DAO.begin();
        Veiculo veiculo = buscarVeiculo(placa);
        if (veiculo == null) {
            throw new Exception("Veiculo nao existe: " + placa);
        }
        veiculo.getRegistros().remove(registro);
        daoveiculo.update(veiculo);
        DAO.commit();
    }
    
    /*  
        --> Método para excluir um veículo do sistema de estacionamento.
        Fluxo: ele busca o veículo com a placa informada e o exclui do banco de dados.
        Exceções: se o veículo não existir, lança uma exceção.
        Retorno: não há retorno.
    */
    public static void excluirVeiculo(String placa) throws Exception {
        DAO.begin();
        Veiculo veiculo = buscarVeiculo(placa);
        if (veiculo == null) {
            throw new Exception("Veiculo nao existe: " + placa);
        }

        List<Registro> registros = veiculo.getRegistros();

        // Verifica se registros é null ou não
        if (registros == null || registros.isEmpty()) {
            daoveiculo.delete(veiculo);
        } else {
            Registro ultimoRegistro = registros.get(registros.size() - 1);

            if (ultimoRegistro.getTipo().equals("entrada")) {
                throw new Exception("Veiculo nao pode ser excluido, pois possui registro de entrada");
            }

            daoveiculo.delete(veiculo);
        }
        DAO.commit();
    }
    
    /*  
        --> Método para listar todos os veículos cadastrados no sistema de estacionamento.
        Fluxo: ele busca todos os veículos cadastrados no banco de dados.
        Exceções: se não houver veículos cadastrados, lança uma exceção.
        Retorno: retorna uma lista com todos os veículos cadastrados.
    */	
    public static List<Veiculo> listarVeiculos() throws Exception {
        List<Veiculo> veiculos = daoveiculo.readAll();
        if (veiculos.size() == 0)
            throw new Exception("Nenhum veiculo cadastrado");
        return veiculos;
    }

    /*  
        --> Método para buscar um veículo no sistema de estacionamento.
        Fluxo: ele busca o veículo com a placa informada no banco de dados.
        Exceções: se o veículo não existir, lança uma exceção.
        Retorno: retorna o veículo buscado.
    */
    public static Veiculo buscarVeiculo(String placa) throws Exception{
        Veiculo veiculo = daoveiculo.read(placa);
        if(veiculo==null)
            throw new Exception("Veiculo nao existe:" + placa);
        return veiculo;

    }

    //CRUD Registro

    /*  
        --> Método para cadastrar um novo registro no sistema de estacionamento.
        Fluxo: ele cria um novo registro com a placa e o tipo informados, o adiciona ao banco de dados e atualiza a arrecadação.
        Exceções: se o veículo não existir ou se o tipo do registro for inválido em relação ao último registro do veículo, lança uma exceção.
        Retorno: retorna o registro cadastrado.
    */
    public static Registro cadastrarRegistro(String placa, String tipo) throws Exception {

        DAO.begin();

        int id = daoaregistro.gerarId();

        Veiculo veiculo = buscarVeiculo(placa);

        List<Registro> registros = veiculo.getRegistros();

        LocalDateTime dataAtual = LocalDateTime.now();
        String dataString = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Arrecadacao arrecadacao = buscarArrecadacao(dataString);

        if (arrecadacao == null) {
            cadastrarArrecadacao(dataString);
        }

        if (tipo.equals("entrada")) {

            if (registros.size() > 0) {
                Registro ultimoRegistro = registros.get(registros.size() - 1);

                if (ultimoRegistro.getTipo().equals("entrada"))
                    throw new Exception("Veiculo já está no estacionamento");
            }

        } else if (tipo.equals("saida")) {

            if (registros.size() == 0)
                throw new Exception("Veiculo não está no estacionamento");

            Registro ultimoRegistro = registros.get(registros.size() - 1);

            LocalDateTime dataUltimoRegistro = ultimoRegistro.getDatahora();

            if (ultimoRegistro.getTipo().equals("saida"))
                throw new Exception("Veiculo já saiu do estacionamento");

            LocalDate dataInicio = dataUltimoRegistro.toLocalDate();
            LocalDate dataTermino = dataAtual.toLocalDate();

            Long diferencaEmDias = dataInicio.until(dataTermino, ChronoUnit.DAYS);

            if (diferencaEmDias >= 0) {
                alterarArrecadacao(dataString, 10.0 * (diferencaEmDias + 1));
            } else {
                throw new Exception("Data de saída menor que data de entrada");
            }

        } else {
            throw new Exception("Tipo de registro inválido");
        }

        Registro registro = new Registro(id, veiculo, tipo);
        Fachada.adicionarRegistroVeiculo(placa, registro);
        daoaregistro.create(registro);
        DAO.commit();
        return registro;
    }

    /*  
        --> Método para excluir um registro do sistema de estacionamento.
        Fluxo: ele busca o registro com o id informado e o exclui do array de rsgistros do veículo e consequentemente no banco de dados.
        Exceções: se o registro não existir, lança uma exceção.
        Retorno: não há retorno.
    */
    public static void excluirRegistro(int id) throws Exception {
        DAO.begin();
        Registro registro = Fachada.buscarRegistro(id);
        if (registro == null) {
            throw new Exception("Registro não encontrado");
        }
        Veiculo veiculo = registro.getVeiculo();
        String placa = veiculo.getPlaca();
        Fachada.removerRegistroVeiculo(placa,registro);

        daoveiculo.update(veiculo);
        daoaregistro.delete(registro);
        DAO.commit();
    }

    /*  
        --> Método para listar todos os registros cadastrados no sistema de estacionamento.
        Fluxo: ele busca todos os registros cadastrados no banco de dados.
        Exceções: se não houver registros cadastrados, lança uma exceção.
        Retorno: retorna uma lista com todos os registros cadastrados.
    */
    public static List<Registro> listarRegistros() throws Exception {

        List<Registro> registros = daoaregistro.readAll();
        if (registros.size() == 0)
            throw new Exception("Nenhum registro cadastrado");
        return registros;
    }

    /*  
        --> Método para buscar um registro no sistema de estacionamento.
        Fluxo: ele busca o registro com o id informado no banco de dados.
        Exceções: se o registro não existir, lança uma exceção.
        Retorno: retorna o registro buscado.
    */
    public static Registro buscarRegistro(Integer id) throws Exception{

        Registro registro = daoaregistro.read(id);
        if(registro==null)
            throw new Exception("Registro nao existe:" + id);

        return registro;

    }

    //CRUD Arrecadacao

    /*  
        --> Método para cadastrar uma nova arrecadação no sistema de estacionamento.
        Fluxo: ele cria uma nova arrecadação com a data informada e a adiciona ao banco de dados.
        Exceções: se a arrecadação já estiver cadastrada, lança uma exceção.
        Retorno: retorna a arrecadação cadastrada.
    */
    public static Arrecadacao cadastrarArrecadacao(String data) throws Exception {

        DAO.begin();
        Arrecadacao arrecadacao = daoarrecadacao.read(data);

        if (arrecadacao != null)
            throw new Exception("Arrecadação já cadastrada:" + data);

        arrecadacao = new Arrecadacao(data);

        daoarrecadacao.create(arrecadacao);

        DAO.commit();
        return arrecadacao;

    }

    /*  
        --> Método para alterar o valor total de uma arrecadação.
        Fluxo: ele busca a arrecadação com a data informada e altera o valor total dela.
        Exceções: se a arrecadação não existir, lança uma exceção.
        Retorno: não há retorno.
    */
    public static void alterarArrecadacao(String data, double valor) throws Exception {

        DAO.begin();

        Arrecadacao arrecadacao = buscarArrecadacao(data);

        double total = arrecadacao.getTotal();

        arrecadacao.setTotal(total + valor);
        daoarrecadacao.update(arrecadacao);
        DAO.commit();

    }

    /*  
        --> Método para excluir uma arrecadação do sistema de estacionamento.
        Fluxo: ele busca a arrecadação com a data informada e a exclui do banco de dados.
        Exceções: se a arrecadação não existir, lança uma exceção.
        Retorno: não há retorno.
    */
    public static void excluirArrecadacao(String data) throws Exception {
        DAO.begin();
        Arrecadacao arrecadacao = buscarArrecadacao(data);
        if (arrecadacao == null)
            throw new Exception("Arrecadação não existe nesta data: " + data);
        daoarrecadacao.delete(arrecadacao);
        DAO.commit();
    }

    /*  
        --> Método para listar todas as arrecadações cadastradas no sistema de estacionamento.
        Fluxo: ele busca todas as arrecadações cadastradas no banco de dados.
        Exceções: se não houver arrecadações cadastradas, lança uma exceção.
        Retorno: retorna uma lista com todas as arrecadações cadastradas.
    */
    public static List<Arrecadacao> listarArrecadacoes() throws Exception {
        List<Arrecadacao> arrecadacoes = daoarrecadacao.readAll();
        if (arrecadacoes.size() == 0)
            throw new Exception("Nenhuma arrecadação cadastrada");
        return arrecadacoes;
    }

    /*  
        --> Método para buscar uma arrecadação no sistema de estacionamento.
        Fluxo: ele busca a arrecadação com a data informada no banco de dados.
        Exceções: se a arrecadação não existir, lança uma exceção.
        Retorno: retorna a arrecadação buscada.
    */
    public static Arrecadacao buscarArrecadacao(String dataString) throws Exception{
        DAO.begin();
        Arrecadacao arrecadacao = daoarrecadacao.read(dataString);
        return arrecadacao;
    }


    //Consultas

    /*  
        --> Método para listar todos os veículos que tiveram registro na data informada.
        Fluxo: ele busca todos os veículos que tiveram registro na data informada no banco de dados.
        Exceções: se não houver veículos cadastrados nesta data, lança uma exceção.
        Retorno: retorna uma lista com todos os veículos que tiveram registro na data informada.
    */
    public static List<Veiculo> veiculosNaData(String data) throws Exception {
        List<Veiculo> veiculos = daoveiculo.getVeiculosNaData(data);

        if (veiculos == null)
            throw new Exception("Nenhum veículo cadastrado nesta data");

        return veiculos;
    }

    /*  
        --> Método para listar todos os veículos que tiveram mais de n registros.
        Fluxo: ele busca todos os veículos que tiveram mais de n registros no banco de dados.
        Exceções: se não houver veículos cadastrados com mais de n registros, lança uma exceção.
        Retorno: retorna uma lista com todos os veículos que tiveram mais de n registros.
    */
    public static List<Veiculo> veiculosAcimaDoRegistro(int n) throws Exception {
        List<Veiculo> veiculos = daoveiculo.getVeiculosAcimaDoRegistro(n);

        if (veiculos == null)
            throw new Exception("Nenhum veículo cadastrado com mais de " + n + " registros");

        return veiculos;
    }

    /*  
        --> Método para listar todos os registros que ocorreram na data informada.
        Fluxo: ele busca todos os registros que ocorreram na data informada no banco de dados.
        Exceções: se não houver registros cadastrados nesta data, lança uma exceção.
        Retorno: retorna uma lista com todos os registros que ocorreram na data informada.
    */
    public static List<Registro> registrosNaData(String data) throws Exception {
        List<Registro> registros = daoaregistro.registrosNaData(data);

        if (registros==null)
            throw new Exception("Nenhum registro cadastrado nesta data");

        return registros;
    }



    
}
