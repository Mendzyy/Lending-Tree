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
import java.util.InputMismatchException;
import java.util.Scanner;

public class NewRequest {

    Connection c;
    SQLConnection connection = new SQLConnection();
    String Name = null;
    int mainIdofLender = 0;

    public void GetNewRequest(int a) throws SQLException, ClassNotFoundException {
        c = connection.GetConnection();
        String Client_name = null;
        mainIdofLender = a;
        PreparedStatement ps = c.prepareStatement("Select Name from LoginMST where Id=?");
        ps.setInt(1, a);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Name = rs.getString(1);
        }

        PreparedStatement ps3 = c.prepareStatement("Select Name, Offer_Name,Amount,Interest_Rate,Time_Period from Loan_Request where Lender_Name=?");
        ps3.setString(1, Name);
        ResultSet rs3 = ps3.executeQuery();
        int number = 0;
        if (!rs3.isBeforeFirst()) {
            System.out.println("There is No New Request to you from any client");
        } else {
            while (rs3.next()) {
                Client_name = rs3.getString(1);
                PreparedStatement ps4 = c.prepareStatement("Select * from ClientDetails where Name=?");
                ps4.setString(1, Client_name);
                ResultSet rs4 = ps4.executeQuery();
                while (rs4.next()) {
                    number++;
                    System.out.println("--------------------" + number + "--------------------------");
                    System.out.println(" Full Name:-" + Client_name + "" + rs4.getString(3) + "");
                    System.out.println("Birth Of Date:-" + rs4.getDate(4) + "");
                    System.out.println("Email Id:-" + rs4.getString(6) + "");
                    System.out.println("Gender:-" + rs4.getString(7) + "");
                    System.out.println("Mobile:-" + rs4.getString(8) + "");
                    System.out.println("Addres:-" + rs4.getString(9) + "");
                    System.out.println("City:-" + rs4.getString(10) + "");
                    System.out.println("State:-" + rs4.getString(11) + "");
                    System.out.println("Pincode:-" + rs4.getString(12) + "");
                }

            }
        }
        System.out.println("Which One Do You Want to Accept?");
        Scanner s = new Scanner(System.in);
        int NumberofRequest = s.nextInt();
        try {
            AcceptigNewRequest(NumberofRequest);
        } catch (InputMismatchException ioe) {
            System.out.println("Wrong Selected");
            GetNewRequest(a);
        }
    }
    int loanId = 0;

    public void AcceptigNewRequest(int NumberofRequest) throws SQLException, ClassNotFoundException {

        String nameClient = null;
        String nameOffer = null;
        int amnt = 0;
        float intrst = 0;
        int time = 0;
        PreparedStatement ps = c.prepareStatement("Select * from(Select ROW_NUMBER() OVER(ORDER BY Loan_Id) AS a, Loan_Id,Name, Offer_Name,Amount,Interest_Rate,Time_Period From Loan_Request where Lender_Name=?) AS Temp1 where a=?");
        ps.setString(1, Name);
        ps.setInt(2, NumberofRequest);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            System.out.println("You have selected Wrong Numb");

        } else {
            while (rs.next()) {
                loanId = rs.getInt(2);
               // System.out.println(loanId);
                nameClient = rs.getString(3);
                nameOffer = rs.getString(4);
                amnt = rs.getInt(5);
                intrst = rs.getFloat(6);
                time = rs.getInt(7);

                System.out.println("Name:-" + nameClient + "");
                System.out.println("Offer_Name:-" + nameOffer + "");
                System.out.println("Amount:-" + amnt + "");
                System.out.println("Interest Rate:-" + intrst + "");
                System.out.println("Time Period(In Months):-" + time + "");
            }
        }
        System.out.println("");
        System.out.println("Are You Sure You want to Accept this request? 1.Yes 2. No");
        Scanner s = new Scanner(System.in);
        int confirmation = s.nextInt();
       //System.out.println(loanId);
        try {
            if (1 == confirmation) {
                PreparedStatement ps2 = c.prepareStatement("Update Loan_Request SET Status =? where Loan_Id=?");
                ps2.setString(1, "ABL");
                ps2.setInt(2, loanId);
                ps2.executeUpdate();
                //System.out.println(loanId);
            } else {
                GetNewRequest(mainIdofLender);
            }

        } catch (InputMismatchException ioe) {
            System.out.println("Wrong Selected");
            GetNewRequest(mainIdofLender);
        }

    }

}
