package joboonja.dataLayer;

import joboonja.dataLayer.dbConnectionPool.BasicDBConnectionPool;
import joboonja.dataLayer.dbConnectionPool.imp.SQLiteBasicDBConnectionPool;

public class ConnectionPool {
    private final static String dbURL = "jdbc:sqlite:/Users/tahabagheri/Desktop/Term 6/IE/IE-Project/joboonjaRDB.db";
    private static BasicDBConnectionPool instance;

    public static BasicDBConnectionPool getInstance() {
        if (instance == null) {
            instance = new SQLiteBasicDBConnectionPool(2, 4 , dbURL);
        }
        return instance;
    }
}
