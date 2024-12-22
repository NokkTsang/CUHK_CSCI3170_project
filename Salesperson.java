import java.util.Scanner;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Salesperson {

    private Connection conn;

    private int Salesperson_ID;
    private String Salesperson_Name;
    private String Salesperson_Address;
    private int Salesperson_Phone_Number;
    private int Salesperson_Experience;

    public Salesperson(Connection conn) {
        this.conn = conn;
    }

    public Salesperson (int Salesperson_ID, String Salesperson_Name, String Salesperson_Address, int Salesperson_Phone_Number, int Salesperson_Experience){
        this.Salesperson_ID = Salesperson_ID;
        this.Salesperson_Name = Salesperson_Name;
        this.Salesperson_Address = Salesperson_Address;
        this.Salesperson_Phone_Number = Salesperson_Phone_Number;
        this.Salesperson_Experience = Salesperson_Experience;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("-----Operations for saleperson menu-----");
            System.out.println("Which kinds of operation would you like to perform?");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");
            System.out.print("Enter Your Choice: ");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    search_for_parts();
                    break;
                case 2:
                    sell_a_part();
                    break;
                case 3:
                    System.out.println();
                    return;
                default:
                    System.out.println("Invalid choice, please try again");
            }
        }
    }

    private void search_for_parts(){
        int choice;
        System.out.println("Choose the Search criterion");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");
        System.out.print("Choose the search criterion: ");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextInt();
        if(choice == 1){
            search_by_part();
        }
        if(choice == 2){
            search_by_manufacturer();
        }
    }

    private void sell_a_part(){
        int part_id = 0;
        int salesperson_id = 0;
        String part_name = "";
        System.out.print("Enter the Part ID: ");
        Scanner scan = new Scanner(System.in);
        part_id = scan.nextInt();
        System.out.print("Enter the Salesperson ID: ");
        salesperson_id = scan.nextInt();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT pAvailableQuantity FROM part WHERE pID = " + part_id);
            int quantity = 0;
            while (rs.next()) {
                quantity = rs.getInt("pAvailableQuantity");
            }
            if (quantity > 0) {
                // update [Part] table
                rs = stmt.executeQuery("UPDATE part SET pAvailableQuantity = " + (quantity - 1) + " WHERE pID = " + part_id);
                rs = stmt.executeQuery("SELECT pName, pAvailableQuantity FROM part WHERE pID = " + part_id);
                while (rs.next()) {
                    part_name = rs.getString("pName");
                    quantity = rs.getInt("pAvailableQuantity");
                }
                System.out.println("Product: " + part_name + "(id: " + part_id + ") Remaining Quantity: " + quantity);

                // update [Transaction Records] table
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                stmt.executeUpdate("INSERT INTO transaction(pID, sID, tDate) VALUES(" + part_id + ", " + salesperson_id + ", '" + ft.format(date) + "')");
            } else {
                System.out.println("The part is sold out!!");
            }
        }catch (Exception e){
            System.out.println("Something went wrong connection!");
            e.printStackTrace();
        }
    }

    private void search_by_part(){
        int order = 0;
        System.out.print("Type in the Search Keyword: ");
        Scanner scan = new Scanner(System.in);
        String keyword = scan.nextLine();
        System.out.println("Choose ordering:");
        System.out.println("1. By price, ascending order");
        System.out.println("2. By price, descending order");
        System.out.print("Choose the search criterion: ");
        order = scan.nextInt();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            if (order == 1) {       //ascending
                rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, " +
                        "pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price " +
                        "FROM part, manufacturer, category " +
                        "WHERE pName LIKE '%" + keyword + "%' " +
                        "AND part.mID = manufacturer.mID " +
                        "AND part.cID = category.cID " +
                        "ORDER BY pPrice ASC");
                int id;
                String pname;
                String mname;
                String cname;
                int q;
                int w;
                int p;
                System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
                while (rs.next()) {
                    id = rs.getInt("ID");
                    pname = rs.getString("Name");
                    mname = rs.getString("Manufacturer");
                    cname = rs.getString("Category");
                    q = rs.getInt("Quantity");
                    w = rs.getInt("Warranty");
                    p = rs.getInt("Price");
                    System.out.println("| " + id + " | " + pname + " | " + mname + " | " + cname + " | " + q + " | " + w + " | " + p + " |");
                }
                System.out.println("End of Query");
            }
            if (order == 2) {       //descending
                rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, " +
                        "pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price " +
                        "FROM part, manufacturer, category " +
                        "WHERE pName LIKE '%" + keyword + "%' " +
                        "AND part.mID = manufacturer.mID " +
                        "AND part.cID = category.cID " +
                        "ORDER BY pPrice DESC");
                int id;
                String pname;
                String mname;
                String cname;
                int q;
                int w;
                int p;
                System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
                while (rs.next()) {
                    id = rs.getInt("ID");
                    pname = rs.getString("Name");
                    mname = rs.getString("Manufacturer");
                    cname = rs.getString("Category");
                    q = rs.getInt("Quantity");
                    w = rs.getInt("Warranty");
                    p = rs.getInt("Price");
                    System.out.println("| " + id + " | " + pname + " | " + mname + " | " + cname + " | " + q + " | " + w + " | " + p + " |");
                }
                System.out.println("End of Query");
            }
        }catch (Exception e){
            System.out.println("Something went wrong connection!");
            e.printStackTrace();
        }
    }

    private void search_by_manufacturer(){
        String keyword = "";
        int order = 0;
        System.out.print("Type in the Search Keyword: ");
        Scanner scan = new Scanner(System.in);
        keyword = scan.nextLine();
        System.out.println("Choose ordering:");
        System.out.println("1. By price, ascending order");
        System.out.println("2. By price, descending order");
        System.out.print("Choose the search criterion: ");
        order = scan.nextInt();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            if (order == 1) {       //ascending
                rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, " +
                        "pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price " +
                        "FROM part, manufacturer, category " +
                        "WHERE mName LIKE '%" + keyword + "%' " +
                        "AND part.mID = manufacturer.mID " +
                        "AND part.cID = category.cID " +
                        "ORDER BY pPrice ASC");
                int id;
                String pname;
                String mname;
                String cname;
                int q;
                int w;
                int p;
                System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
                while (rs.next()) {
                    id = rs.getInt("ID");
                    pname = rs.getString("Name");
                    mname = rs.getString("Manufacturer");
                    cname = rs.getString("Category");
                    q = rs.getInt("Quantity");
                    w = rs.getInt("Warranty");
                    p = rs.getInt("Price");
                    System.out.println("| " + id + " | " + pname + " | " + mname + " | " + cname + " | " + q + " | " + w + " | " + p + " |");
                }
                System.out.println("End of Query");
            }
            if (order == 2) {       //descending
                rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, " +
                        "pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price " +
                        "FROM part, manufacturer, category " +
                        "WHERE mName LIKE '%" + keyword + "%' " +
                        "AND part.mID = manufacturer.mID " +
                        "AND part.cID = category.cID " +
                        "ORDER BY pPrice DESC");
                int id;
                String pname;
                String mname;
                String cname;
                int q;
                int w;
                int p;
                System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
                while (rs.next()) {
                    id = rs.getInt("ID");
                    pname = rs.getString("Name");
                    mname = rs.getString("Manufacturer");
                    cname = rs.getString("Category");
                    q = rs.getInt("Quantity");
                    w = rs.getInt("Warranty");
                    p = rs.getInt("Price");
                    System.out.println("| " + id + " | " + pname + " | " + mname + " | " + cname + " | " + q + " | " + w + " | " + p + " |");
                }
                System.out.println("End of Query");
            }
        }catch (Exception e){
            System.out.println("Something went wrong connection!");
            e.printStackTrace();
        }
    }

}
