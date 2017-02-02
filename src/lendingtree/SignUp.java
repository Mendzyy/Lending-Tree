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
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Chirag
 */
public class SignUp {
     Connection c;
     SQLConnection connection = new SQLConnection();

     
     public void Signup() throws SQLException, ClassNotFoundException {
        System.out.println("Welcome Signup page/n/n");
        System.out.println("--------------");
        String U_Type = Utype();
        System.out.println("Please Enter Your Name:-");
        Scanner name = new Scanner(System.in);
        String Name = name.nextLine();
        System.out.println("Please Enter Your Last Name:-");
        Scanner sname = new Scanner(System.in);
        String Sname = sname.nextLine();
        System.out.println("Please Enter Your Birth Date");
        Date tmpbod = DateofBirth();
        java.sql.Date bod = new java.sql.Date(tmpbod.getTime());
        String PSWD = Password();
        String ml = Mail();
        String gndr = SexOption();
        System.out.println("Please Enter Your Mobile No:-");
        Scanner Mobile = new Scanner(System.in);
        String mobile = Mobile.nextLine();
        System.out.println("Please Enter Your Address:-");
        Scanner Address = new Scanner(System.in);
        String address = Address.nextLine();
        System.out.println("Please Enter Your City:-");
        Scanner City = new Scanner(System.in);
        String city = City.nextLine();
        System.out.println("Please Enter Your State:-");
        Scanner State = new Scanner(System.in);
        String state = State.nextLine();
        System.out.println("Please Enter Your Pincode:-");
        Scanner Pincode = new Scanner(System.in);
        String pincode = Pincode.nextLine();
        c = connection.GetConnection();
        PreparedStatement stmt = c.prepareStatement("Insert into LoginMST(Name,Password,U_Type) values(?,?,?)");
        stmt.setString(1, Name);
        stmt.setString(2, PSWD);
        stmt.setString(3, U_Type);
        stmt.executeUpdate();
        Statement stmt2 = c.createStatement();
        ResultSet rslt = stmt2.executeQuery("Select MAX(id) from LoginMST where name='" + Name + "' AND Password='" + PSWD + "'");
        int tempid=0;
        if (rslt.next()) {
            tempid = rslt.getInt(1);
        }
        if ("C".equals(U_Type)) {
            PreparedStatement pstmt = c.prepareStatement("Insert Into ClientDetails(Id,Name,LName,DOB,Password,Mail,Sex,Mobile,Address,City,State,Pincode) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, tempid);
            pstmt.setString(2, Name.trim());
            pstmt.setString(3, Sname.trim());
            pstmt.setDate(4, bod);
            pstmt.setString(5, PSWD.trim());
            pstmt.setString(6, ml.trim());
            pstmt.setString(7, gndr.trim());
            pstmt.setString(8, mobile.trim());
            pstmt.setString(9, address.trim());
            pstmt.setString(10, city.trim());
            pstmt.setString(11, state.trim());
            pstmt.setString(12, pincode.trim());
            pstmt.executeUpdate();

        }
        if ("L".equals(U_Type)) {
            PreparedStatement pstmt = c.prepareStatement("Insert Into LenderDetails(Id,Name,LName,DOB,Password,Mail,Sex,Mobile,Address,City,State,Pincode) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, tempid);
            pstmt.setString(2, Name.trim());
            pstmt.setString(3, Sname.trim());
            pstmt.setDate(4, bod);
            pstmt.setString(5, PSWD.trim());
            pstmt.setString(6, ml.trim());
            pstmt.setString(7, gndr.trim());
            pstmt.setString(8, mobile.trim());
            pstmt.setString(9, address.trim());
            pstmt.setString(10, city.trim());
            pstmt.setString(11, state.trim());
            pstmt.setString(12, pincode.trim());
            pstmt.executeUpdate();

        }

    }

    public String SexOption() {
            String Gender = null;
            System.out.println("Please Select one");
            System.out.println("1. Female 2.Male");
            Scanner sex = new Scanner(System.in);
            Integer Sex = Integer.parseInt(sex.nextLine());
            if (Sex == 1) {
                Gender = "Female";
            }
            if (Sex == 2) {
                Gender = "Male";
            }
            if ((Sex != 2) && (Sex != 1)) {
                return SexOption();
            }

            return Gender;

        }

        
        // Aane sudharvani che. Undhu kar. Jya method call karave che tya actual return type karavano che
        // ane jya actual return type karave che tya method return karavani che
        //jethi actual recursion thase
        public Date DateofBirth() {
            Date bod = null;
            System.out.println("Enter Your Birht of Date");
            Scanner dt = new Scanner(System.in);
            try {
                String dateinput = dt.nextLine();
                boolean checkformat;
                if (dateinput.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
                    checkformat = true;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    formatter.setLenient(false);
                    try {
                        bod = formatter.parse(dateinput);
                    } catch (ParseException ex) {
                        System.out.println("Please Enter Your Date again");
                        return DateofBirth();
                    }
                 return bod;   
                } else {
                    checkformat = false;
                    System.out.println("Your date is not in proper format. Try this one dd-MM-yyyy");
                    
                }
            } catch (DateTimeException e) {
                e.printStackTrace();
               
            }
              return DateofBirth(); 
                    }
   

        public String Password() {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter a Password: (It Should be combination of Digits and small Alphabets)");
            String password = input.next();
            System.out.println(CheckLength(password) + "" + CheckLower(password) + "" + CheckDigit(password) + "" + CheckUpper(password));
            while (CheckLength(password) || CheckLower(password) || CheckDigit(password) || CheckUpper(password)) {
                System.out.print("Please enter a Password: ");
                String ps = input.next();
                password = ps;
            }
            return password;
        }

        public boolean CheckLength(String password) {
            if (password.length() > 8 || password.length() < 15) {
                return false;
            } else {
                return true;
            }
        }

        public boolean CheckLower(String password) {
            return password.equals(password.toUpperCase());
        }

        public boolean CheckUpper(String password) {
             return !password.equals(password.toLowerCase());
        }

        public boolean CheckDigit(String password) {
            for (int i = 0; i < password.length(); i++) { // Lets iterate over only once. Saving time
                if (Character.isDigit(password.charAt(i))) {
                    return false;
                } else {
                    return true;
                }

            }
            return true;
        }

        public String Mail() {
            String pswd = null;
            System.out.println("Enter Mail");
            System.out.println("------------------");
            Scanner ps1 = new Scanner(System.in);
            String pstmp1 = ps1.nextLine();
            System.out.println("Confirm Mail");
            System.out.println("----------------------");
            Scanner ps2 = new Scanner(System.in);
            String pstmp2 = ps2.nextLine();
            if (pstmp1.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                if (pstmp1.equals(pstmp2)) {
                    pswd = pstmp1;
                }
                if (!pstmp1.equals(pstmp2)) {
                    System.out.println("Mails do not match. Please Enter again.");
                    System.out.println("--------------");
                   return Mail();
                }
            } else {
                System.out.println("Not In Proper Format");
                return Mail();
            }

            return pswd;
        }

        public String Utype() {
            String utyp = null;
            System.out.println("Please Select Your User Type /n 1. Client 2. Lender");
            Scanner scn = new Scanner(System.in);
            String scn1 = scn.nextLine();
            if (scn1.matches("")) {
                System.out.println("please Do not leave it blank");
                 return Utype();
            }
            if (scn1.matches("[1-2]{1}")) {
                if ("1".equals(scn1)) {
                    utyp = "C";
                }
                if ("2".equals(scn1)) {
                    utyp = "L";
                }
            } else {
                System.out.println("this one is working");
                return Utype();
            }

            return utyp;
        }
        

    
}
