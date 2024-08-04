package daodb4o;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import modelo.Veiculo;
import modelo.Registro;
import java.util.List;
import com.db4o.query.Query;
import com.db4o.query.Predicate;

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

    public List<Veiculo> getVeiculosNaData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataLocalDate = LocalDate.parse(data, formatter);

        List<Veiculo> listaVeiculos = manager.query(new Predicate<Veiculo>() {
            @Override
            public boolean match(Veiculo veiculo) {
                for (Registro r : veiculo.getRegistros()) {
                    if (r.getDatahora().toLocalDate().equals(dataLocalDate)) {
              
                        return true;
                    }
                }
                return false;
            }
        });

        if (listaVeiculos.isEmpty())
            return null;

        return listaVeiculos;
    }
    // Consulta 2 - Veículos que possuem registros acima de uma determinada quantidade

    public List<Veiculo> getVeiculosAcimaDoRegistro(int quantidade){

        List<Veiculo> listaVeiculos = manager.query(new Predicate<Veiculo>() {
            @Override
            public boolean match(Veiculo veiculo) {
                return veiculo.getRegistros().size() > quantidade;
            }
        });
        
        if (listaVeiculos.isEmpty())
            return null;

        return listaVeiculos;

    }


}




