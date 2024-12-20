import java.sql.*;
import java.util.Scanner;

public class Manager {
    private Connection conn;

    public Manager(Connection conn) {
        this.conn = conn;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("-----Operations for manager menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. List all salespersons");
            System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular part");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listSalespersons();
                    break;
                case 2:
                    countTransactionsByExperience();
                    break;
                case 3:
                    sortManufacturersByTotalSales();
                    break;
                case 4:
                    showNMostPopularParts();
                    break;
                case 5:
                    System.out.println("Return to the main menu");
                    return;
                default:
                    System.out.println("Invalid choice, please try again");
            }
        }
    }

    private void listSalespersons() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose ordering");
        System.out.println("1. By ascending order");
        System.out.println("2. By descending order");
        System.out.print("choose the list ordering: ");
        int choice = scanner.nextInt();

        String order = null;
        if (choice == 1) {
            order = "ASC";
        }else if (choice == 2) {
            order = "DESC";
        }

        try {
            Statement stmt = conn.createStatement();
            String selectSQL = "SELECT s.sID AS ID, \n" +
                    "       s.sName AS \"Name\", \n" +
                    "       s.sPhoneNumber AS \"Mobile Phone\", \n" +
                    "       s.sExperience AS \"Years of Experience\"\n" +
                    "FROM salesperson s\n" +
                    "ORDER BY s.sExperience " + order;
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.print("| ID | Name | Mobile Phone | Years of Experience |\n");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String phoneNumber = rs.getString("Mobile Phone");
                int experience = rs.getInt("Years of Experience");
                System.out.printf("| %d | %s | %s | %d |\n", id, name, phoneNumber, experience);
            }
            System.out.println("End of Query");
        } catch (Exception e) {
            System.err.println("Something went wrong connection!");
        }
    }

    private void countTransactionsByExperience() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1. Type in the lower bound of years of experience: ");
        int lower = scanner.nextInt();
        System.out.print("2. Type in the upper bound of years of experience: ");
        int upper = scanner.nextInt();
        try{
            Statement stmt = conn.createStatement();
            String selectSQL = "SELECT s.sID AS ID, " +
                    "s.sName AS \"Name\", " +
                    "s.sExperience AS \"Years of Experience\", " +
                    "t.\"Number of Transaction\" " +
                    "FROM salesperson s " +
                    "JOIN ( " +
                    "SELECT sID, COUNT(tID) AS \"Number of Transaction\" " +
                    "FROM transaction " +
                    "WHERE sID IN ( " +
                    "SELECT sID " +
                    "FROM salesperson " +
                    "WHERE sExperience >= " + lower + " AND sExperience <= " + upper + " " +
                    ") " +
                    "GROUP BY sID " +
                    ") t ON s.sID = t.sID " +
                    "ORDER BY t.sID DESC";
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.print("| ID | Name | Years of Experience | Number of Transaction |\n");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int experience = rs.getInt("Years of Experience");
                int transaction = rs.getInt("Number of Transaction");
                System.out.printf("| %d | %s | %d | %d |\n", id, name, experience, transaction);
            }
            System.out.println("End of Query");
        }catch (Exception e){
            System.out.println("Something went wrong connection!");
            e.printStackTrace();
        }

    }

    private void sortManufacturersByTotalSales() {
        try{
            Statement stmt = conn.createStatement();
            String selectSQL = "SELECT m.mID AS \"Manufacturer ID\", m.mName AS \"Manufacturer Name\", SUM(s.a) AS \"Total Sales Value\"\n" +
                    "FROM part p\n" +
                    "JOIN manufacturer m ON p.mID = m.mID\n" +
                    "JOIN (\n" +
                    "    SELECT t.pID, SUM(p.pPrice) AS a\n" +
                    "    FROM part p\n" +
                    "    JOIN transaction t ON p.pID = t.pID\n" +
                    "    GROUP BY t.pID\n" +
                    ") s ON p.pID = s.pID\n" +
                    "GROUP BY m.mID, m.mName\n" +
                    "ORDER BY \"Total Sales Value\" DESC";
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.print("| Manufacturer ID | Manufacturer Name | Total Sales Value |\n");
            while (rs.next()) {
                int mID = rs.getInt("Manufacturer ID");
                String mName = rs.getString("Manufacturer Name");
                int tValue = rs.getInt("Total Sales Value");
                System.out.printf("| %d | %s | %d |\n", mID, mName, tValue);
            }
            System.out.println("End of Query");
        }catch (Exception e){
            System.out.println("Something went wrong connection!");
            e.printStackTrace();
        }
    }

    private void showNMostPopularParts(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type in number of parts: ");
        int part = scanner.nextInt();
        try{
            Statement stmt = conn.createStatement();
            String selectSQL = "SELECT p.pID AS \"Part ID\", \n" +
                    "       p.pName AS \"Part Name\", \n" +
                    "       COUNT(t.tID) AS \"No. of Transaction\"\n" +
                    "FROM part p\n" +
                    "LEFT JOIN transaction t ON p.pID = t.pID\n" +
                    "GROUP BY p.pID, p.pName\n" +
                    "ORDER BY COUNT(t.tID) DESC\n" +
                    "FETCH FIRST " + part + " ROWS ONLY";
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.print("| Part ID | Part Name | No. of Transaction |\n");
            while (rs.next()) {
                int pID = rs.getInt("Part ID");
                String pName = rs.getString("Part Name");
                int tNumber = rs.getInt("No. of Transaction");
                System.out.printf("| %d | %s | %d |\n", pID, pName, tNumber);
            }
            System.out.println("End of Query");
        }catch (Exception e){
            System.out.println("Something went wrong connection!");
            e.printStackTrace();
        }
    }
}
