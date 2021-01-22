package DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//import com.novayre.jidoka.suracl.data.Factory;
//import com.novayre.jidoka.oracle.data.Factory;

/**
 *
 * @author jidoka.io
 * @version 2.0.0
 */

public interface Db2DAO {

 //   boolean buscaTalleres(String rutEmisor) throws ClassNotFoundException, SQLException;

  //  int upateBitacora(Factura factura) throws ClassNotFoundException, SQLException;

  //  int upate(Factura factura, boolean actualizarFecha) throws ClassNotFoundException, SQLException, Exception;

 //   List<Factura> buscaBitacora(String feDesde,String feHasta, boolean isRechazable) throws ClassNotFoundException, SQLException;

    Boolean facturaPagada(String folio, String rutfact) throws ClassNotFoundException, SQLException;

    Boolean existeSiniestro(String nnumsini) throws ClassNotFoundException, SQLException;

    String getMail(String rutEmisor) throws ClassNotFoundException, SQLException;

    Boolean isHoliday(String date) throws ClassNotFoundException, SQLException;

    Connection getConn() throws ClassNotFoundException, SQLException;
}
