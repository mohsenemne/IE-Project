package joboonja.dataLayer;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:sqlite:/Users/Mymac/Desktop/UNI/Semester6/Internet Engineering/Computer Assignments/CA7/Joboonja/joboonjaRDB.db";

    static {
        ds.setUrl(dbURL);
        ds.setUsername("user");
        ds.setPassword("password");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource(){ }
}
