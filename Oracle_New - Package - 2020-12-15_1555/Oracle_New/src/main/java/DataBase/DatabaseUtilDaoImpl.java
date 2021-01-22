package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jidoka.io
 * @version 1.5.7
 */
public abstract class DatabaseUtilDaoImpl {


    protected abstract Connection getConn() throws ClassNotFoundException, SQLException ;

    /**
     *
     * @param rs
     * @param ps
     * @param //connection
     * @throws SQLException
     */
    public void close(ResultSet rs,  Statement st, PreparedStatement ps, Connection conn) throws SQLException {
        if (rs != null) {
            rs.close();
            rs = null;
        }

        if (st != null)  {
            st.close();
            st = null;
        }
        if (ps != null) {
            ps.close();
            ps = null;
        }

        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

}
