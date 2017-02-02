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
public class Lenders {

    public void MainPageLender() throws SQLException, ClassNotFoundException {
        Login n = new Login();
        int a = n.userid();
        Scanner in = new Scanner(System.in);
        System.out.println("Select one of them");
        System.out.println("1. View Clients");
        System.out.println("2. See New Requests");
        System.out.println("3. Publish New Offer");
        System.out.println("4.Profile");
        System.out.println("5.Logout");
        String input = in.nextLine();
        if (input.matches("")) {
            System.out.println("please Do not leave it blank");
            MainPageLender();
        }
        if (input.matches("[1-5]{1}")) {
            if ("1".equals(input)) {
                System.out.println("These are Your Clients");

            } else if ("2".equals(input)) {
                System.out.println("There are Your New Request");
                NewRequest nr = new NewRequest();
                nr.GetNewRequest(a);
            } else if ("3".equals(input)) {
                AddOffer o = new AddOffer();
                o.AddNewOffer(a);

            } else if ("4".equals(input)) {
                System.out.println("Welcome to Your Profile");
                Profiles p = new Profiles();
                p.Profile(a);

            } else if ("5".equals(input)) {
                Login.Id = 0;
                System.out.println("You are succesfully logged out of the system");
                LendingTree lt = new LendingTree();
                lt.MainWindow();

            }
        } else {
            System.out.println("Wrong Input");
            MainPageLender();
        }
    }

}
