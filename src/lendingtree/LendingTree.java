package lendingtree;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class LendingTree {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        LendingTree lt = new LendingTree();
        lt.MainWindow();

    }

//@SuppressWarnings("StringEquality")
    public void MainWindow() throws SQLException, ClassNotFoundException {
        System.out.println("Please Select one of them");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        Scanner scan1 = new Scanner(System.in);
        String a = scan1.nextLine();
        if (a.matches("")) {
            System.out.println("please Do not leave it blank");
            MainWindow();
        }
        if (a.matches("[1-2]{1}")) {
            if ("1".equals(a)) {
                System.out.println("Welcome to Sign up");
                SignUp s = new SignUp();
                s.Signup();
              
                
            }
            if ("2".equals(a)) {
                System.out.println("Welcome to Login Page");
                Login l = new Login();
                l.Loginn();
            }
        } else {
            System.out.println("Wrong Input");
            MainWindow();
        }

    }

}
