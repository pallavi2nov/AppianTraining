package DataBase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import org.apache.commons.lang3.StringUtils;

import com.novayre.jidoka.client.api.IJidokaServer;
//import com.novayre.jidoka.suracl.data.Factory;
import org.apache.commons.lang.StringUtils;
//import com.novayre.jidoka.suracl.util.DateUtil;



/**
 *
 * @author jidoka.io
 * @version 2.0.0
 */
public class Db2DAOImpl extends DatabaseUtilDaoImpl implements Db2DAO {

    private static final String DB2_CREDENTIALS = "xyz" ;
    protected IJidokaServer<?> server;

    /**
     *
     * @param server
     */
    public Db2DAOImpl (IJidokaServer<?> server){
        this.server = server;
    }

//    @Override
//    public boolean buscaTalleres(String rutEmisor) throws ClassNotFoundException, SQLException {
//
//        ResultSet rs = null;
//        Statement statement = null;
//        boolean allowPayment = false;
//        StringBuilder query = null;
//
//        try{
//
//            statement = getConn().createStatement();
//
//            query = new StringBuilder("SELECT CVALATR1 FROM RSASOLDB2.TBACSTGE WHERE DCODTABL= '2738' AND REPLACE(CVALATR2, '.', '') ='");
//            query.append(rutEmisor);
//            query.append("'");
//
//            //server.debug("salida ->" + query.toString());
//
//            rs = statement.executeQuery(query.toString());
//            String salida = "";
//
//            while(rs.next()){
//                salida = rs.getString(1).trim();
//
//                if (!StringUtils.isEmpty(salida)&& "S".equals(salida)){
//                    allowPayment = true;
//                }
//            }
//
//        }finally{
//            this.close(rs, statement, null, null);
//            query = null;
//        }
//        return allowPayment;
//    }
    /**
     *
     */
//    @Override
//    public int update(Factura factura, boolean actualizarFecha) throws ClassNotFoundException, SQLException, Exception{
//
//
//        return null;
//    }


//    @Override
//    public int upateBinnacle(Factura factura) throws ClassNotFoundException, SQLException {
//
//
//        return null;
//    }

    /**
     * CNURUGAR es con digito.
     * NNUFOLIO es sin digito.
     */
//    @Override
//    public List<Factura> buscaBitacora(String feDesde, String feHasta, boolean isRechazable) throws ClassNotFoundException, SQLException {
//
//
//
//        return null;
//    }

    @Override
    public Boolean facturaPagada(String folio, String rutfact) throws ClassNotFoundException, SQLException {


        return null;
    }

    @Override
    public Boolean existeSiniestro(String nnumsini) throws ClassNotFoundException, SQLException {



        return null;
    }

    /**
     * Obtiene el correo electronico del proveedor.
     */
    @Override
    public String getMail(String rutEmisor) throws ClassNotFoundException, SQLException {
        ResultSet rs = null;
        Statement statement = null;
        String eMail = "";

        try{

            statement = getConn().createStatement();

            StringBuilder query = new StringBuilder("SELECT CDESELEM FROM RSASOLDB2.TBACSTGE WHERE DCODTABL='2738' AND REPLACE(CVALATR2, '.', '') ='");
            query.append(rutEmisor);
            query.append("'");

            rs = statement.executeQuery(query.toString());

            //server.debug(query.toString());

            while(rs.next()){

                eMail = rs.getString(1);
            }

            return eMail;

        }finally{
            this.close(rs, statement, null, null);
        }
    }

    /**
     *
     * Identifies if given a date returns if it is a holiday   or not
     * need to change table name "holiday" to given one.
     */
    @Override
    public Boolean isHoliday(String date) throws ClassNotFoundException, SQLException {


        return null;
    }

    @Override
    public Connection getConn() throws ClassNotFoundException, SQLException {
        return DatabaseSura.getConnection(server.getCredentials(DB2_CREDENTIALS).get(0),
                server.getParameters().get("DB2_ADDRESS"),
                server.getParameters().get("DB2_LIB"));
    }
}

