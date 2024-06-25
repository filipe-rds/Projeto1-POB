package regras_negocio;

import java.util.List;
import com.db4o.foundation.List4;
import com.db4o.query.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import daodb4o.*;
import modelo.*;

public class Fachada {

    private Fachada() {}

	private static DAOVeiculo daoveiculo = new DAOVeiculo();  
	private static DAORegistro daoaregistro = new DAORegistro(); 
	private static DAOArrecadacao daoarrecadacao = new DAOArrecadacao();
	//public static Usuario logado;	//contem o objeto Usuario logado em TelaLogin.java

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

    //CRUD Veiculo

    public static Veiculo cadastrarVeiculo(String placa) throws Exception{
        DAO.begin(); 
        Veiculo veiculo = daoveiculo.read(placa);
        if(veiculo!=null)
            throw new Exception("Veiculo ja cadastrado:" + placa);
        veiculo = new Veiculo(placa);
        daoveiculo.create(veiculo);
        DAO.commit();
		return veiculo;
    } 

    // add novo Registro no array de Veículo 
    
    public static void adicionarRegistroVeiculo(String placa,Registro registro) throws Exception{

        DAO.begin();
        Veiculo veiculo = buscarVeiculo(placa);
        veiculo.getRegistros().add(registro);
        daoveiculo.update(veiculo);
        DAO.commit();

    }

    public static void excluirVeiculo(String placa) throws Exception{

        // Só poderá excluir um veículo se o último Registro for do tipo "Saida".
        // Se o último Registro for do tipo "Entrada", o veículo não poderá ser excluído.
        // Se o veículo não existir, lançar uma exceção.
        
        DAO.begin();
        Veiculo veiculo = buscarVeiculo(placa);
        
        List<Registro> registros = veiculo.getRegistros();
        
        if(registros.size() == 0) {
        	
        	daoveiculo.delete(veiculo);
            DAO.commit();
            return;
        	
        }
        
        Registro ultimoRegistro = registros.get(registros.size()-1);
        
        if(ultimoRegistro.getTipo().equals("Entrada"))
            throw new Exception("Veiculo nao pode ser excluido, pois possui registro de entrada");
        
        daoveiculo.delete(veiculo);
        DAO.commit();
    }

    public static List<Veiculo> listarVeiculos() throws Exception{
        List<Veiculo> veiculos = daoveiculo.readAll();
        if(veiculos.size()==0)
            throw new Exception("Nenhum veiculo cadastrado");
        return veiculos;
    }

    public static Veiculo buscarVeiculo(String placa) throws Exception{
        Veiculo veiculo = daoveiculo.read(placa);
        if(veiculo==null)
            throw new Exception("Veiculo nao existe:" + placa);
        return veiculo;

    }

    //CRUD Registro

    public static Registro cadastrarRegistro(String placa,String tipo) throws Exception{

        DAO.begin();

        int id = daoaregistro.gerarId();

        Veiculo veiculo = buscarVeiculo(placa);

        List<Registro> registros = veiculo.getRegistros();


        LocalDateTime dataAtual = LocalDateTime.now(); 
        String dataString = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));


        Arrecadacao arrecadacao = buscarArrecadacao(dataString);

        if (arrecadacao == null){
            cadastrarArrecadacao(dataString);
        }

        
        if(tipo.equals("entrada")){

            if(registros.size()>0){
                 Registro ultimoRegistro = registros.get(registros.size()-1); 

                if(ultimoRegistro.getTipo().equals("Entrada"))
                    throw new Exception("Veiculo já está no estacionamento");
            }

        }
        else if(tipo.equals("saida")){

            if(registros.size()==0)
                throw new Exception("Veiculo não está no estacionamento");
                
            Registro ultimoRegistro = registros.get(registros.size()-1);

            LocalDateTime dataUltimoRegistro = ultimoRegistro.getDatahora();  
            
            if(ultimoRegistro.getTipo().equals("Saida") )
                throw new Exception("Veiculo já saiu do estacionamento");
            
            LocalDate dataInicio = dataUltimoRegistro.toLocalDate();
            LocalDate dataTermino = dataAtual.toLocalDate();

            Long diferencaEmDias = dataInicio.until(dataTermino, ChronoUnit.DAYS);

            if(diferencaEmDias>=0){ 
                alterarArrecadacao(dataString,10.0*(diferencaEmDias+1));
            }
            else{
                throw new Exception("Data de saída menor que data de entrada");
            }  

        }else{
            throw new Exception("Tipo de registro inválido");
        }

        Registro registro = new Registro(id,veiculo,tipo);
        adicionarRegistroVeiculo(placa,registro);
        daoaregistro.create(registro);
        DAO.commit();
        return registro;
    }

    public static void excluirRegistro(int id) throws Exception{

        DAO.begin();
        Registro registro = daoaregistro.read(id);
        if(registro==null)
            throw new Exception("Registro nao existe:" + id);
        daoaregistro.delete(registro);
        DAO.commit();
        

    }

    public static List<Registro> listarRegistros() throws Exception{
        List<Registro> registros = daoaregistro.readAll();
        if(registros.size()==0)
            throw new Exception("Nenhum registro cadastrado");
        return registros;
    }

    public static Registro buscarRegistro(Integer id) throws Exception{

        Registro registro = daoaregistro.read(id);

        if(registro==null)
            throw new Exception("Registro nao existe:" + id);

        return registro;

    }

    //CRUD Arrecadacao

    public static Arrecadacao cadastrarArrecadacao(String data) throws Exception{

        DAO.begin();
        Arrecadacao arrecadacao = daoarrecadacao.read(data);
       
        if(arrecadacao!=null)
            throw new Exception("Arrecadação já cadastrada:" + data);

        
        arrecadacao = new Arrecadacao(data); 
        
        daoarrecadacao.create(arrecadacao);

        DAO.commit();
        return arrecadacao;

    }

    public static void alterarArrecadacao(String data,double valor) throws Exception{

        DAO.begin();

        Arrecadacao arrecadacao = buscarArrecadacao(data);
        
        double total = arrecadacao.getTotal();

        arrecadacao.setTotal(total+valor);
        daoarrecadacao.update(arrecadacao);
        DAO.commit();
        
    }

    public static void excluirArrecadacao(String data) throws Exception{
        DAO.begin();
        Arrecadacao arrecadacao = buscarArrecadacao(data);
        daoarrecadacao.delete(arrecadacao);
        DAO.commit();
    }

    public static List<Arrecadacao> listarArrecadacoes() throws Exception{
        List<Arrecadacao> arrecadacoes = daoarrecadacao.readAll();
        if(arrecadacoes.size()==0)
            throw new Exception("Nenhuma arrecadação cadastrada");
        return arrecadacoes;
    }

    public static Arrecadacao buscarArrecadacao(String dataString) throws Exception {
        DAO.begin();
        Arrecadacao arrecadacao = daoarrecadacao.read(dataString);
        if (arrecadacao == null)
            throw new Exception("Arrecadação não existe nesta data: " + dataString);
        return arrecadacao;
    }
    
    // Consultas

    public static List<Registro> RegistrosNaData(String data) throws Exception {
        List<Registro> registros = daoaregistro.getRegistrosNaData(data);
        if (registros.size() == 0)
            throw new Exception("Nenhum registro cadastrado nesta data");
        return registros;
    }
    
    public static List<Veiculo> VeiculosNaData(String data) throws Exception {
        List<Veiculo> veiculos = daoveiculo.getVeiculosNaData(data);
        if (veiculos.size() == 0)
            throw new Exception("Nenhum veículo cadastrado nesta data");
        return veiculos;
    }

    public static List<Veiculo> VeiculosAcimaDoRegistro(int n) throws Exception {
        List<Veiculo> veiculos = daoveiculo.getVeiculosAcimaDoRegistro(n);
        if (veiculos.size() == 0)
            throw new Exception("Nenhum veículo cadastrado com " + n + " registros");
        return veiculos;
    }
    
}
