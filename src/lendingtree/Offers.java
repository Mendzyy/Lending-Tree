package lendingtree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Chirag
 */
class MethodsForNormalFilteredOffers {

    Connection c;
    SQLConnection connection = new SQLConnection();
    int pageNumb = 'A';
    int IdOfOffer;
    int amount;
    float int_rate;
    int time_period;

    public void ShowOffers(String SQL) throws ClassNotFoundException, SQLException {
        Offers o = new Offers();
        c = connection.GetConnection();
        PreparedStatement ps2 = c.prepareStatement(SQL);
        if (SQL.contains("between")) {
            FirstCommonLineForPageNumber(o.forFirstCommonPageLine);
            ps2.setInt(1, 1);
            ps2.setInt(2, 10);
        } else if (SQL.contains("Amount")) {
            FirstCommonLineForPageNumber(o.forFisrtCommonLineFilter);
            ps2.setInt(1, amount);
            ps2.setFloat(2, int_rate);
            ps2.setInt(3, time_period);
        }
        ResultSet rs2 = ps2.executeQuery();
        if (!rs2.isBeforeFirst()) {
            System.out.println("There are no New or Matching Loans. Try again");
        } else {
            while (rs2.next()) {
                char temp = (char) pageNumb++;
                System.out.println("--------------------------------------------------");
                System.out.println("" + temp + "");
                int notforanypurpose = rs2.getInt(1);
                System.out.printf("%4s", "Bank_Name:---" + rs2.getString(3) + "");
                System.out.printf("%3s", "Offer_Name:---" + rs2.getString(4) + "");
                System.out.printf("%3s", "Amount_Name:---" + rs2.getString(5) + "");
                System.out.printf("%4s", "Interest_Rate:---" + rs2.getFloat(6) + "");
                System.out.printf("%3s\n", "Time_Period:---" + rs2.getString(7) + "");
            }
        }
        pageNumb = 'A';
        if (SQL.contains("between")) {
            InputOption(SQL);
        } else if (SQL.contains("Amount")) {
            InputOption(o.forFilterPaging);
        }
    }

    public void FirstCommonLineForPageNumber(String SQL) throws SQLException, ClassNotFoundException {
        int pages = 0;
        int count = 0;
        System.out.println("Enter 0 to Go Back Or Else Enter Appropriate Alphabet of Offer in which you have Interest");
        System.out.println("---------------New Offers---------------");
        c = connection.GetConnection();
        PreparedStatement ps = c.prepareStatement(SQL);
        if (SQL.contains("Amount")) {
            ps.setInt(1, amount);
            ps.setFloat(2, int_rate);
            ps.setInt(3, time_period);
        }
        ResultSet rs1 = ps.executeQuery();
        while (rs1.next()) {
            count++;
        }
        //System.out.println(count);
        for (int i = 0; i <= count; i++) {
            if (i % 10 == 0) {
                pages++;
            }

        }
        int remainder = count % 10;
        if (remainder != 0) {
            pages += 1;
        }
        System.out.print("Available Pages:-");
        System.out.print("");
        for (int i = 1; i < pages; i++) {
            System.out.printf("%3s", i);
        }
        System.out.println();
    }

    public void Paging(int a, String SQL) throws SQLException, ClassNotFoundException {
        Offers o = new Offers();
        int temp_a = 0;
        int temp_b = 1;
        PreparedStatement ps = c.prepareStatement(SQL);
        if (SQL.contains("between")) {
            FirstCommonLineForPageNumber(o.forFirstCommonPageLine);
            if (a == 1) {
                ShowOffers(SQL);
            } else if (a > 1) {
                ps.setInt(1, (10 * (a - 1)) + temp_a);
                ps.setInt(2, (10 * (a - 1)) + temp_b);
            } else {
                ShowOffers(SQL);
            }
        } else if (SQL.contains("Amount")) {
            FirstCommonLineForPageNumber(o.forFisrtCommonLineFilter);
            if (a == 1) {
                ShowOffers(o.SQLforFilterPagingOffers);
            } else if (a > 1) {
                ps.setInt(1, amount);
                ps.setFloat(2, int_rate);
                ps.setInt(3, time_period);
                ps.setInt(4, (10 * (a - 1)) + temp_a);
                ps.setInt(5, (10 * (a - 1)) + temp_b);
            } else {
                ShowOffers(o.SQLforFilterPagingOffers);
            }

        }

        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            ShowOffers(SQL);
        } else {
            while (rs.next()) {
                char temp = (char) pageNumb++;
                System.out.println("--------------------------------------------------");
                System.out.println("" + temp + "");
                int IdOfOffers = rs.getInt(1);
                System.out.print("Bank_Name:---" + rs.getString(3) + "");
                System.out.println("Offer_Name:---" + rs.getString(4) + "");
                System.out.print("Amount_Name:---" + rs.getString(5) + "");
                System.out.println("Interest_Rate:---" + rs.getFloat(6) + "");
                System.out.print("Time_Period:---" + rs.getString(7) + "");
            }
            pageNumb = 'A';
            InputOption(SQL);

        }
    }

    public void ChoosingOffer(int page, char OfferFromList, String SQL) throws SQLException, ClassNotFoundException {
        char small = Character.toLowerCase(OfferFromList);
        int numerical = Character.getNumericValue(small) - 9;

        PreparedStatement ps = c.prepareStatement(SQL);
        if (page == 0) {
            ps.setInt(1, (page * 10) + numerical);
        } else if (page > 0) {
            page--;
            ps.setInt(1, (page * 10) + numerical);
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            IdOfOffer = rs.getInt(1);
            System.out.println("Bank Name:-" + rs.getString(3) + "");
            System.out.println("Offer_Name:-" + rs.getString(4) + "");
            System.out.println("Amount:-" + rs.getInt(5) + "");
            System.out.println("Interest Rate:-" + rs.getFloat(6) + "");
            System.out.println("Time Period:-" + rs.getString(7) + "");
        }
        System.out.println("If you want to apply for this Loan Please Select 1 otherwise 2. Please Enter O to Go back");
        Scanner s = new Scanner(System.in);
        String yesno = s.nextLine();

        {
            if (yesno.matches("")) {
                System.out.println("Please do not leave it blank");
            }
            if (yesno.matches("[0-2]{1}")) {
                if (yesno.matches("1")) {
                    Login id = new Login();
                    int a = id.userid();
                    AddingLoanToLoanRequest(a);
                } else if (yesno.matches("2")) {
                    ShowOffers(SQL);
                } else if (yesno.matches("0")) {
                    ShowOffers(SQL);
                } else {
                    ChoosingOffer(page, OfferFromList, SQL);
                }
            }

        }
    }

    public void AddingLoanToLoanRequest(int a) throws SQLException, ClassNotFoundException {
        String offr_name = null;
        int amount_loanReq = 0;
        float intrst_rate_loanReq = 0;
        String time_period_loanReq = null;
        String Client_name = null;
        String Name = GettingNameofLender(a);
        PreparedStatement ps1 = c.prepareStatement("Select Offer_Name,Amount,Interest_Rate,Time_Period from Offers where Loan_Id=? ");
        PreparedStatement ps2 = c.prepareStatement("Select Name from LoginMST where Id=? ");
        PreparedStatement ps3 = c.prepareStatement("Insert into Loan_Request(Id,Loan_Id,Name,Offer_Name,Amount,Interest_Rate,Time_Period,Lender_Name,Status) values(?,?,?,?,?,?,?,?)");
        ps1.setInt(1, IdOfOffer);
        ps2.setInt(1, a);
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        while (rs1.next()) {
            offr_name = rs1.getString(1);
            //System.out.println(offr_name);
            amount_loanReq = rs1.getInt(2);
            intrst_rate_loanReq = rs1.getFloat(3);
            time_period_loanReq = rs1.getString(4);
        }
        while (rs2.next()) {
            Client_name = rs2.getString(1);
        }

        ps3.setInt(1, a);
        ps3.setInt(2, IdOfOffer);
        ps3.setString(3, Client_name);
        ps3.setString(4, offr_name);
        ps3.setInt(5, amount_loanReq);
        ps3.setFloat(6, intrst_rate_loanReq);
        ps3.setString(7, time_period_loanReq);
        ps3.setString(8, Name);
        ps3.setString(9, "PND");
        ps3.executeUpdate();
        System.out.println("You have Successfully requested for the loan. You can check the status of Your applied Loan in 2nd option.");
        Clients clnt = new Clients();
        clnt.MainPageClient();

    }

    public String GettingNameofLender(int a) throws SQLException {
        String Name = null;
        PreparedStatement ps = c.prepareStatement("Select Bank_Name from Offers where Loan_Id=?");
        ps.setInt(1, IdOfOffer);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Name = rs.getString(1);
        }
        return Name;
    }

    public void InputOption(String SQL) throws SQLException, ClassNotFoundException {
        Offers o = new Offers();
        int intforchoosingoffer = 0;
        int ab = 0;
        Scanner s = new Scanner(System.in);
        String ChooseOption = s.nextLine();
        if (SQL.contains("between")) {
            if (ChooseOption.matches("[1-9]{1}")) {
                ab = Integer.parseInt(ChooseOption);
                intforchoosingoffer = ab;
                Paging(ab, SQL);
                ab = 0;
            } else if (ChooseOption.matches("[A-Ja-j]{1}")) {

                char charr = ChooseOption.charAt(0);
                SQL = o.forChoosingOffer;
                ChoosingOffer(intforchoosingoffer, charr, SQL);
            }
        } else if (SQL.contains("Amount")) {
            SQL = o.forFilterPaging;
            if (ChooseOption.matches("[1-9]{1}")) {
                ab = Integer.parseInt(ChooseOption);
                intforchoosingoffer = ab;
                Paging(ab, SQL);
                ab = 0;
            } else if (ChooseOption.matches("[A-Ja-j]{1}")) {

                char charr = ChooseOption.charAt(0);

                ChoosingOffer(intforchoosingoffer, charr, SQL);
            }
        }

    }
}

public class Offers extends MethodsForNormalFilteredOffers {

    String forFirstCommonPageLine = "Select * from Offers";
    String forChoosingOffer = "Select * from(Select ROW_NUMBER() OVER(ORDER BY Loan_Id) AS a, * From Offers) AS Temp where a=?";
    String forFisrtCommonLineFilter = "Select * From Offers where Amount <= ? And Interest_Rate <= ? And Time_Period <= ?";
    String forFilterPaging = "Select * from(Select ROW_NUMBER() OVER(ORDER BY Loan_Id) AS a, * From Offers ) AS Temp1 where Amount<=? and Interest_Rate<=? and Time_Period<=? and a Between ? AND ?";
    String SQLforFilterPagingOffers = "Select * from(Select ROW_NUMBER() OVER(ORDER BY Loan_Id) AS a, * From Offers) AS Temp where Amount <= ? And Interest_Rate <= ? And Time_Period <= ?";

    public void ShowNormalOffers() throws ClassNotFoundException, SQLException {
        String SQLforPagingOffers = "Select * from(Select ROW_NUMBER() OVER(ORDER BY Loan_Id) AS a, * From Offers) AS Temp where a between ? and ?";
        ShowOffers(SQLforPagingOffers);
    }

    public void ShowFilteredOffers() throws ClassNotFoundException, SQLException {

        AddOffer ao = new AddOffer();
        MethodsForNormalFilteredOffers op = (MethodsForNormalFilteredOffers) ao;
        System.out.println("Enter Your Desired Amount");
        amount = ao.Amount();
        System.out.println("Enter Your Desired Interest Rate");
        int_rate = ao.Interest_rate();
        System.out.println("Enter Your Desired Time ");
        time_period = ao.Time_Period();
        ShowOffers(SQLforFilterPagingOffers);
    }

    public void appliedLoan(int id) throws SQLException, ClassNotFoundException {
        c = connection.GetConnection();
        Clients clnt = new Clients();
        int LoanId = 0;
        String Offer_Name = null;
        String Name_of_Bank = null;
        int count = 0;
        String Status = null;
        Scanner s = new Scanner(System.in);
        System.out.println("Loan Details");
        PreparedStatement ps1 = c.prepareStatement("Select * from Client_Loan_Details where Id=?");
        ps1.setInt(1, id);
        ResultSet rs1 = ps1.executeQuery();
        if (!rs1.isBeforeFirst()) {
            PreparedStatement ps2 = c.prepareStatement("Select * from Loan_request where Id=?");
            ps2.setInt(1, id);
            ResultSet rs2 = ps2.executeQuery();
            if (!rs2.isBeforeFirst()) {
                System.out.println("You have not aplied for Any laon");
                System.out.println("If You Want to Apply Press 1. Or Press 2 to Go Back");
                try {
                    int optionAtAppliedLoan = s.nextInt();
                    if (optionAtAppliedLoan == 1) {
                        ShowNormalOffers();
                    } else if (optionAtAppliedLoan == 2) {
                        clnt.MainPageClient();
                    }
                } catch (InputMismatchException ioe) {
                    System.out.println("Must be 1 or 2");
                    appliedLoan(id);
                }
            } else {
                while (rs2.next()) {

                    
                    
//                    count++;
//                    Status = rs2.getString(9);
//                    if ("PND".equals(Status)) {
//                        Status = "Pending";
//                    } else if ( "ABL".equals(Status)) {
//                        Status = "Accepted By Lander.";
//                    }
//                    System.out.println("--------------" + count + "-----------------");
//                    System.out.println("Name Of the Bank:--" + rs2.getString(8) + "");
//                    System.out.println("Offer Name:--" + rs2.getString(4) + "");
//                    System.out.println("Loan Amount(In Euro):--" + rs2.getInt(5) + "");
//                    System.out.println("Interest Rate is(In %):--" + rs2.getFloat(6) + "");
//                    System.out.println("Total Time Duration(In Month):--" + rs2.getString(7) + "");
//                    System.out.println("Status" + Status + "");
//                    System.out.println("");
//                    System.out.println("Please Select the loan you want to accept from your side");
                                   }
            }

        } else {
            while (rs1.next()) {
                count++;
                LoanId = rs1.getInt(2);
                Name_of_Bank = GettingNameofLender(LoanId);
                System.out.println("--------------" + count + "-----------------");
                System.out.println("Name Of the Bank:--" + Name_of_Bank + "");
                System.out.println("Loan Amount(In Euro):--" + rs1.getInt(4) + "");
                System.out.println("Interest Rate is(In %):--" + rs1.getFloat(5) + "");
                System.out.println("Total Time Duration(In Month):--" + rs1.getString(6) + "");
                System.out.println("Remained Amount of Loan(In Euro):-" + rs1.getInt(7) + "");
                System.out.println("Remained Time to pay the rest of loan(In Month):--" + rs1.getString(8) + "");
                System.out.println("Penalty:" + rs1.getInt(9) + "");
                System.out.println();
                System.out.println();
                System.out.println("Press 1 to go back");
                String option = s.nextLine();
                if ("".equals(option)) {
                    appliedLoan(id);
                } else if ("1".equals(option)) {

                    clnt.MainPageClient();
                } else {
                    appliedLoan(id);
                }
            }

        }
    }
}

class AddOffer extends MethodsForNormalFilteredOffers {

    public void AddNewOffer(int a) throws SQLException {
        String Name = GettingNameofLender(a);
        String O_Name = offername();
        int amnt = Amount();
        int Time_Period = Time_Period();
        float Interest_rate = Interest_rate();
        PreparedStatement ps2 = c.prepareStatement("Insert into Offers(Bank_Name,Offer_Name,Amount,Interest_Rate,Time_Period) values(?,?,?,?,?)");
        ps2.setString(1, Name);
        ps2.setString(2, O_Name);
        ps2.setInt(3, amnt);
        ps2.setFloat(4, Interest_rate);
        ps2.setInt(5, Time_Period);
        ps2.executeUpdate();
    }

    public String offername() {
        String offer_name = null;
        Scanner s = new Scanner(System.in);
        offer_name = s.nextLine();
        if (!offer_name.matches("[^[A-Za-z.\\s_-]+$]")) {
            System.out.println("Must be Name(Alphabets)");
            return offername();
        } else {
            return offer_name;
        }

    }

    public int Amount() {
        Scanner s = new Scanner(System.in);
        try {
            int amnt = s.nextInt();
            return amnt;
        } catch (InputMismatchException ioe) {
            System.out.println("Must be Number");
            return Amount();
        }

    }

    public int Time_Period() {
        Scanner s = new Scanner(System.in);
        try {
            int time = s.nextInt();
            return time;
        } catch (InputMismatchException ioe) {
            System.out.println("Must be Number");
            return Time_Period();
        }

    }

    public float Interest_rate() {
        Scanner s = new Scanner(System.in);
        try {
            float i_rate = s.nextFloat();
            return i_rate;
        } catch (InputMismatchException ioe) {
            System.out.println("Must be Number");
            return Interest_rate();
        }

    }
}
