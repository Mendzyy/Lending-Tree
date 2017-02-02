package lendingtree;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Chirag
 */
public class Profiles {

    Connection c;
    SQLConnection connection = new SQLConnection();
    String utype = null;
   
    public void Profile(int a) throws SQLException, ClassNotFoundException {
        c = connection.GetConnection();
        PreparedStatement pstmt2 = c.prepareStatement("SELECT U_Type from LoginMST Where Id=?");
        pstmt2.setInt(1, a);
        ResultSet rsId = pstmt2.executeQuery();
        if (!rsId.isBeforeFirst()) {
        } else {
            while (rsId.next()) {
                utype = rsId.getString(1).trim();
                System.out.println(utype);
            }
        }

        System.out.println();
        System.out.println("Select one of them");

        if ("C".equalsIgnoreCase(utype)) {
            Clients cl = new Clients();
            System.out.println("1. Go Back 2. See Your Profile 3.Edit Profile 4.Bank Details 5.Logout");
            Scanner inp = new Scanner(System.in);
            String aa = inp.nextLine();
            if (aa.matches("")) {
                System.out.println("please Do not leave it blank");
                Profile(a);
            }
            if (aa.matches("[1-5]{1}")) {
                if ("1".equals(aa)) {
                    cl.MainPageClient();
                } else if ("2".equals(aa)) {
                    MainDetails(a);
                    Profile(a);
                } else if ("3".equals(aa)) {
                    ProfileUpdate(a);
                } else if ("4".equals(aa)) {
                    BankDetails(a);
                } else if ("5".equals(aa)) {
                    Login.Id = 0;
                    System.out.println("You are succesfully logged out of the system");
                    LendingTree lt = new LendingTree();
                    lt.MainWindow();
                }
            } else {
                System.out.println("Wrong Input");
                cl.MainPageClient();
            }
        } else if ("L".equalsIgnoreCase(utype)) {
            Lenders l = new Lenders();
            System.out.println("1. Go Back 2.See Your Profile 3.Edit Profile 4.Logout");
            Scanner inp = new Scanner(System.in);
            String aa = inp.nextLine();
            if (aa.matches("")) {
                System.out.println("please Do not leave it blank");
                Profile(a);
            }

            if (aa.matches("[1-4]{1}")) {
                if ("1".equals(aa)) {
                    l.MainPageLender();
                } else if ("2".equals(aa)) {
                    MainDetails(a);
                    Profile(a);
                } else if ("3".equals(aa)) {
                    ProfileUpdate(a);
                } else if ("4".equals(aa)) {
                    Login.Id = 0;
                    System.out.println("You are succesfully logged out of the system");
                    LendingTree lt = new LendingTree();
                    lt.MainWindow();
                }
            } else {
                System.out.println("Wrong Input");

                l.MainPageLender();
            }
        }

    }

    public void ProfileUpdate(int a) throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("What do you wanna change? Please Select Number Accordingly.");
        System.out.println("1. Residence Detail");
        System.out.println("2. Password");
        System.out.println("3. Go Back");
        System.out.println("4. Log out");
        String change = in.nextLine();
        if (change.matches("")) {
            System.out.println("please Do not leave it blank");
            ProfileUpdate(a);
        }
        if (change.matches("[1-4]{1}")) {
            if ("1".equals(change)) {
                System.out.println("Change Your Residence Detail Here");
                ChangeAdd(a);
            } else if ("2".equals(change)) {
                System.out.println("Change Your Password here!");
                ChangePassword(a);
            } else if ("3".equals(change)) {
                Profile(a);
            } else if ("4".equals(change)) {
                Login.Id = 0;
                System.out.println("You are succesfully logged out of the system");
                LendingTree lt = new LendingTree();
                lt.MainWindow();
            }
        } else {
            System.out.println("Wrong Input");
            ProfileUpdate(a);
        }

    }

    public void ChangeAdd(int a) throws SQLException, ClassNotFoundException {
        Scanner scn = new Scanner(System.in);
        PreparedStatement pstmt = null;
        c = connection.GetConnection();
        String SQL1 = null;
        if ("C".equalsIgnoreCase(utype)) {
            SQL1 = "SELECT Address, City, State, Pincode FROM ClientDetails WHERE Id=?";
        } else if ("L".equalsIgnoreCase(utype)) {
            SQL1 = "SELECT Address, City, State, Pincode FROM LenderDetails WHERE Id=?";
        }
        pstmt = c.prepareStatement(SQL1);
        pstmt.setInt(1, a);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("Your Old Details are like:-");
            System.out.println("Address:-" + rs.getString(1) + "");
            System.out.println("City:-" + rs.getString(2) + "");
            System.out.println("State:-" + rs.getString(3) + "");
            System.out.println("Pin Code:-" + rs.getString(4) + "");
        }

        System.out.println();
        System.out.println("Enter You New Details.");
        System.out.println();
        System.out.println("Enter Your New Address");
        String new1 = scn.nextLine();
        System.out.println("Enter Your New City");
        String new2 = scn.nextLine();
        System.out.println("Enter Your New State");
        String new3 = scn.nextLine();
        System.out.println("Enter Your New Pincode");
        String new4 = scn.nextLine();
        System.out.println();
        System.out.println("Are You Sure You want to Update above given Details.? 1. Confirm 2.Cancel");
        String confirm = scn.nextLine();
        if ("".equals(confirm)) {
            System.out.println("Please Do not Leave it Blank");
        }
        if (confirm.matches("[1-2]{1}")) {
            if ("1".equals(confirm)) {
                PreparedStatement pstmt2 = null;
                String SQL = null;
                if ("C".equalsIgnoreCase(utype)) {
                    SQL = "UPDATE ClientDetails SET Address=?, City=?, State=?, Pincode=? WHERE Id=?";
                } else if ("L".equalsIgnoreCase(utype)) {
                    SQL = "UPDATE LenderDetails SET Address=?, City=?, State=?, Pincode=? WHERE Id=?";
                }
                pstmt2 = c.prepareStatement(SQL);
                pstmt2.setString(1, new1);
                pstmt2.setString(2, new2);
                pstmt2.setString(3, new3);
                pstmt2.setString(4, new4);
                pstmt2.setInt(5, a);
                pstmt2.executeUpdate();
                System.out.println("You have successfully changed it");
                Profile(a);
            }
            if ("2".equals(confirm)) {
                Profile(a);
            }
        } else {
            System.out.println("Wrong Input. Sorry.");
            Profile(a);
        }
    }

    public void ChangePassword(int a) throws SQLException, ClassNotFoundException {
        String compare = null;
        c = connection.GetConnection();
        PreparedStatement pstmt = null;
        String SQL = null;
        if ("C".equalsIgnoreCase(utype)) {
            SQL = "SELECT Password FROM ClientDetails WHERE Id=?";
        } else if ("L".equalsIgnoreCase(utype)) {
            SQL = "SELECT Password FROM LenderDetails WHERE Id=?";
        }
        pstmt = c.prepareStatement(SQL);
        pstmt.setInt(1, a);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            compare = rs.getString(1);
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Old Password!");
        String op = in.nextLine();
        if (op.equals(compare.trim())) {
            SignUp s = new SignUp();
            String newpass = s.Password();
            System.out.println(newpass);
        } else {
            System.out.println("Your Old Password is Wrong. Please Correct it.");
            ChangePassword(a);
        }
    }

    public void BankDetails(int a) throws SQLException, ClassNotFoundException {
        Scanner bnkdtl = new Scanner(System.in);
        c = connection.GetConnection();
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Client_bankDetails WHERE Id=?");
        pstmt.setInt(1, a);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.isBeforeFirst()) {
            System.out.println("Do You want to add your Bank Details?  1. Yes 2.No");
            String numb = bnkdtl.nextLine();
            if ("".equals(numb)) {
                System.out.println("Do Not Leave it Blank");
                BankDetails(a);
            }
            if (numb.matches("[1-2]{1}")) {
                if ("1".equals(numb)) {
                    System.out.print("Give The Name of Bank:---");
                    String bnk1 = bnkdtl.nextLine();
                    System.out.print("Give The IBAN:---");
                    String bnk2 = bnkdtl.nextLine();
                    System.out.print("Give The BIC:---");
                    String bnk3 = bnkdtl.nextLine();
                    PreparedStatement ps = c.prepareStatement("Insert into Client_bankDetails(Id, Bank_Name, IBAN, BIC) values(?,?,?,?)");
                    ps.setInt(1, a);
                    ps.setString(2, bnk1);
                    ps.setString(3, bnk2);
                    ps.setString(4, bnk3);
                    ps.executeUpdate();
                }
                if ("2".equals(numb)) {
                    Profile(a);
                }
            } else {
                Profile(a);
            }

        } else {
            while (rs.next()) {
                System.out.println("Bank_Name:--" + rs.getString(2) + "");
                System.out.println("IBAN:--" + rs.getString(3) + "");
                System.out.println("BIC:--" + rs.getString(4) + "");
            }
        }
    }

    public void MainDetails(int a) throws SQLException, ClassNotFoundException {
        c = connection.GetConnection();
        PreparedStatement pstmt = null;
        String sql = null;
        if ("C".equalsIgnoreCase(utype)) {
            sql = "SELECT * FROM ClientDetails WHERE Id=?";
        } else if ("L".equalsIgnoreCase(utype)) {
            sql = "SELECT * FROM LenderDetails WHERE Id=?";

        } else {
            System.out.println("Kai nathi");
        }
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, a);
        pstmt.executeQuery();
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            System.out.println(id);
            System.out.println("a is" + a + "");
            String Name = rs.getString(2);
            System.out.println("Your Name" + Name + "");
            String LName = rs.getString(3);
            System.out.println("Your LName:---" + LName + "");
            Date DOB = rs.getDate(4);
            System.out.println("Your DOB:---" + DOB + "");
            String Mail = rs.getString(5);
            System.out.println("Your Mail:---" + Mail + "");
            String Sex = rs.getString(7);
            System.out.println("Your Sex:---" + Sex + "");
            String Mobile = rs.getString(8);
            System.out.println("Your Mobile:---" + Mobile + "");
            String Address = rs.getString(9);
            System.out.println("Your Address:---" + Address + "");
            String City = rs.getString(10);
            System.out.println("Your City:---" + City + "");
            String State = rs.getString(11);
            System.out.println("Your State:---" + State + "");
            String Pincode = rs.getString(12);
            System.out.println("Your Pincode:---" + Pincode + "");

        }
    }
}
