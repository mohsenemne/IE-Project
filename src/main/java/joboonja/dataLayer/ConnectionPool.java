package joboonja.dataLayer;

import joboonja.dataLayer.dbConnectionPool.BasicDBConnectionPool;
import joboonja.dataLayer.dbConnectionPool.imp.SQLiteBasicDBConnectionPool;

public class ConnectionPool {
    private final static String dbURL = "jdbc:sqlite:/Users/Mymac/Desktop/UNI/Semester6/Internet Engineering/Computer Assignments/CA8/Joboonja/joboonjaRDB.db";
    private static BasicDBConnectionPool instance;

    public static BasicDBConnectionPool getInstance() {
        if (instance == null) {
            instance = new SQLiteBasicDBConnectionPool(2, 4 , dbURL);
        }
        return instance;
    }
}
