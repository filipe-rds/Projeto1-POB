package daodb4o;
import modelo.Registro;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.db4o.query.Query;

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
    
    public List<Registro> getRegistrosNaData(String data){
        Query q = manager.query();
        q.constrain(Registro.class);
        List<Registro> resultados = q.execute();

        if (resultados.isEmpty())
            return null;

        List<Registro> listaRegistros = new ArrayList<>();

        for (Registro r: resultados){

            LocalDateTime dataObjeto = r.getDatahora();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataObjeto.format(formatter);

            if (dataFormatada.equals(data)){
                listaRegistros.add(r);
            }
        }

        if (listaRegistros.isEmpty())
            return null;

        return listaRegistros;

    }


    
}
