package daodb4o;
import modelo.Registro;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.db4o.query.Query;
import com.db4o.query.Predicate;

public class DAORegistro extends DAO<Registro>{

    // Leitura de um registro pelo id

    public Registro read (Object chave){

        int id = (int) chave;	//casting para o tipo da chave
        Query q = manager.query();
        q.constrain(Registro.class);
        q.descend("id").constrain(id);
        List<Registro> resultados = q.execute();
        if (resultados.size()>0)
            return resultados.get(0);
        else
            return null;
    }

    //metodo sobrescrito da classe DAO para criar "id" sequencial 
    public void create(Registro obj){
        int novoid = super.gerarId();  	//gerar novo id da classe
        obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
        manager.store( obj );
    }

    // Consulta 1 - Registros em uma determinada data
    
    public List<Registro> registrosNaData(String data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataLocalDate = LocalDate.parse(data, formatter);
        
        List<Registro> listaRegistros = manager.query(new Predicate<Registro>() {
            @Override
            public boolean match(Registro registro) {
                return registro.getDatahora().toLocalDate().equals(dataLocalDate);
            }
        });

        if (listaRegistros.isEmpty())
            return null;
        return listaRegistros;

    }


    
}
