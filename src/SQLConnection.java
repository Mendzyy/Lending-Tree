import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLConnection{
    public static void main(String[] args){
    	try {
            Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver" );
            System.out.println("Driver Loaded");
            
            String server = "localhost";
            int port = 1433;
            String user = "Mendzyy";
            String password = "9879699640";            
            String database = "LendingTree2";
            
            String jdbcUrl = "jdbc:sqlserver://"+server+":"+port+";user="+user+";password="+password+";databaseName="+database+"";

            Connection con = DriverManager.getConnection(jdbcUrl);
            System.out.println("Connection created");
            
            Statement stmt = con.createStatement();
            System.out.println("Statement created");    
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM Offers");
            System.out.println("Query created");    
            
            while (rs.next()) {
            	System.out.println("------------------------");
            	System.out.println("Id :" +rs.getInt("Loan_Id"));
            	System.out.println("Name:" +rs.getString("BanK_Name"));
            	System.out.println("User Type:" +rs.getInt("Amount"));
            	
            }

        
            rs.close();
            stmt.close();
            con.close();
            System.out.println("Resourcen released");
        } catch (Exception ex) {
        	System.out.println("Error :" +ex);
        }
}
};