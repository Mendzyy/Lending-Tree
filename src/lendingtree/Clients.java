/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtree;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Chirag
 */
public class Clients {

    public void MainPageClient() throws SQLException, ClassNotFoundException {
        Login n = new Login();
        int a = n.userid();
        LendingTree lt = new LendingTree();
        Scanner in = new Scanner(System.in);
        System.out.println("Select one of them");
        System.out.println("1. View Offers");
        System.out.println("2. Applied Loan Detail");
        System.out.println("3.Profile");
        System.out.println("4.Logout");
        String input = in.nextLine();
        if (input.matches("")) {
            System.out.println("please Do not leave it blank");
            MainPageClient();
        }
        Offers o = new Offers();
        if (input.matches("[1-4]{1}")) {
            if ("1".equals(input)) {
                
                System.out.println("Please Select One");
                System.out.println("1. View All Offers");
                System.out.println("2.Filter Offers");
                System.out.println("3. Go Back");
                System.out.println("4. Logout");
                Scanner s = new Scanner(System.in);
                String offer = s.nextLine();
                if ("".equals(offer)) {
                    MainPageClient();
                }
                if (offer.matches("[1-4]")) {
                    if ("1".equals(offer)) {
                        o.ShowNormalOffers();
                    } else if ("2".equals(offer)) {
                        o.ShowFilteredOffers();
                    } else if ("3".equals(offer)) {
                        MainPageClient();
                    } else if ("4".equals(offer)) {
                        System.out.println("You are succesfully logged out of the system");
                        lt.MainWindow();
                    }
                }
            } else if ("2".equals(input)) {
                System.out.println("There are the details of Applied Loan");
                o.appliedLoan(a);

            } else if ("3".equals(input)) {
                System.out.println("Welcome to Your Profile");
                Profiles p = new Profiles();
                p.Profile(a);

            } else if ("4".equals(input)) {
                Login.Id = 0;
                System.out.println("You are succesfully logged out of the system");
                lt.MainWindow();

            }
        } else {
            System.out.println("Wrong Input");
            MainPageClient();
        }
    }
    
   

}
