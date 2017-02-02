package lendingtree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection{
    
    public Connection GetConnection() throws SQLException, ClassNotFoundException{
            Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver" );
            
            String server = "localhost";
            int port = 1433;
            String user = "Mendzyy";
            String password = "9879699640";            
            String database = "LendingTree2";
            
            String jdbcUrl = "jdbc:sqlserver://"+server+":"+port+";user="+user+";password="+password+";databaseName="+database+"";

            Connection con = DriverManager.getConnection(jdbcUrl);
   
        return con;
        
    }
};