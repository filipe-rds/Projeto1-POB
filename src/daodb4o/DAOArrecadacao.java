package daodb4o;

import modelo.Arrecadacao;
import java.util.List;
import com.db4o.query.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DAOArrecadacao extends DAO<Arrecadacao>{

    // Leitura de uma arrecadação pelo id

    public Arrecadacao read (Object chave){

        String data = (String) chave;
        Query q = manager.query();
        q.constrain(Arrecadacao.class);
        List<Arrecadacao> resultados = q.execute();

        for(Arrecadacao a: resultados){

            LocalDate dataObjeto = a.getData();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataObjeto.format(formatter);

            if (dataFormatada.equals(data)){
                return a;
            }

        }

        return null;
    }
 
}
