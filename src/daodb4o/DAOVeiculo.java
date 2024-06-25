package daodb4o;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import modelo.Veiculo;
import modelo.Registro;

import java.util.List;

import com.db4o.query.Query;

public class DAOVeiculo extends DAO<Veiculo>{

    // Leitura de um veículo pela placa

    public Veiculo read (Object chave){
        String placa = (String) chave; //casting para o tipo da chave

        Query q = manager.query();
		q.constrain(Veiculo.class);
		q.descend("placa").constrain(placa);
		List<Veiculo> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

    // Consulta 1 - Veículos que possuem registros em uma determinada data

    public List<Veiculo> getVeiculosNaData(String data){

        Query q = manager.query();
        q.constrain(Registro.class);
        List<Registro> resultados = q.execute();

        if (resultados.isEmpty())
            return null;

        List<Veiculo> listaCarros = new ArrayList<>();

        for (Registro r: resultados){

            LocalDateTime dataObjeto = r.getDatahora();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataObjeto.format(formatter);

            if (dataFormatada.equals(data)){
                listaCarros.add(r.getVeiculo());
            }
        }

        if (listaCarros.isEmpty())
            return null;

        return listaCarros;

    }

    // Consulta 2 - Veículos que possuem registros acima de uma determinada quantidade

    public List<Veiculo> getVeiculosAcimaDoRegistro(int quantidade){

        List<Veiculo> listaVeiculos = new ArrayList<>();

        Query q = manager.query();
        q.constrain(Veiculo.class);
        List<Veiculo> todosVeiculos = q.execute();

        if (todosVeiculos.isEmpty())
            return null;

        for (Veiculo v: todosVeiculos){
            if (v.getRegistros().size() > quantidade){
                listaVeiculos.add(v);
            }
        }

        if(listaVeiculos.isEmpty())
            return null;

        return listaVeiculos;
        
        

    }


}




