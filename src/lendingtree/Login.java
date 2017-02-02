/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Chirag
 */
public class Login {
    Connection c;
     SQLConnection connection = new SQLConnection();
     
     public static int Id = 0;
//     private static final int user;
//     public Login() 
//     {
//         // user = 25;
//     }
      
     public void Loginn() throws SQLException, ClassNotFoundException
     {
         String utype = null;
         String checkUser = null;
         String checkPass = null;
         Scanner in = new Scanner(System.in);
         System.out.println("Please Enter Your Login Details");
         System.out.print("Please Enter Your Username");
         String usrnm =in.nextLine();
         System.out.print("Please Enter Your Password");
         String pswd = in.nextLine();
         c = connection.GetConnection();
         PreparedStatement pstmt = c.prepareStatement("SELECT * FROM LoginMST WHERE Name = ? AND Password = ?");
         pstmt.setString(1, usrnm);
         //System.out.println("sdfsdfs:-"+usrnm+"");
         pstmt.setString(2, pswd);
         //System.out.println("paswd:-"+pswd+"");
         pstmt.executeQuery();
         ResultSet rs = pstmt.executeQuery();
         if(!rs.isBeforeFirst())
         {
             System.out.println("No Data");
             LendingTree l = new LendingTree();
             l.MainWindow();
         }
         else{
         while(rs.next()){
          Id = rs.getInt(1);
         
          System.out.println(Id);
          checkUser = rs.getString(2);
          System.out.println(checkUser);
          checkPass = rs.getString(3);
          System.out.println(checkPass);
          utype = rs.getString(4);
          System.out.println(utype);
         }
          if((checkUser.equals(usrnm)) && (checkPass.equals(pswd)) && ("C".equals(utype)))
        {
            Clients cl = new Clients();
            cl.MainPageClient();
        }
          else   if((checkUser.equals(usrnm)) && (checkPass.equals(pswd)) && ("L".equals(utype)))
        {
            
            Lenders l = new Lenders();
            l.MainPageLender();
        }
          else   if((checkUser.equals(usrnm)) && (checkPass.equals(pswd)) && ("A".equals(utype)))
        {
            //Method aavse Admin ni.
        }
         }
          

        
        
     }
     
     public int userid()
     {
         return Id;
     }
   
}
