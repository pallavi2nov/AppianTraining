package DataBase;

import com.novayre.jidoka.client.api.execution.IUsernamePassword;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class
 * Establace la coneccion a la base de datos DB2
 * @author jidoka.io
 * @version 0.0.1
 *
 */
public class DatabaseSura {

    private static String connectionUrl ="";
    private static volatile Connection conn = null;

    private DatabaseSura() {};

    /**
     * @param credentials
     * @param //SQLSERVER_ADDRESS
     * @param //SQLSERVER_BD_NAME
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(IUsernamePassword credentials, String db2address, String db2lib) throws ClassNotFoundException, SQLException{
        if (conn == null || (conn != null && conn.isClosed())){
            synchronized(Connection.class){

                connectionUrl = "jdbc:as400://"+db2address+"/"+db2lib+";prompt=false";
                Class.forName("com.ibm.as400.access.AS400JDBCDriver");
                conn = DriverManager.getConnection(connectionUrl,credentials.getUsername(), credentials.getPassword());

            }
        }
        return conn;
    }

    /**
     * to create or develop new url for connection.
     * @return
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }
}
