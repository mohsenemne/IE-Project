package joboonja.dataLayer;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:mysql://localhost:3306/joboonja";

    static {
        ds.setUrl(dbURL);
        ds.setUsername("root");
        ds.setPassword("taha1234");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }


    public static Connection getConnection() throws SQLException {

        return ds.getConnection();
    }

    private DBCPDataSource(){ }
}
