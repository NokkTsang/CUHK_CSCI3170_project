import java.util.Scanner;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Salesperson {

    private int Salesperson_ID;
    private String Salesperson_Name;
    private String Salesperson_Address;
    private int Salesperson_Phone_Number;
    private int Salesperson_Experience;

    public Salesperson (int Salesperson_ID, String Salesperson_Name, String Salesperson_Address, int Salesperson_Phone_Number, int Salesperson_Experience){
        this.Salesperson_ID = Salesperson_ID;
        this.Salesperson_Name = Salesperson_Name;
        this.Salesperson_Address = Salesperson_Address;
        this.Salesperson_Phone_Number = Salesperson_Phone_Number;
        this.Salesperson_Experience = Salesperson_Experience;
    }

    public static void run() {
        int choice;
        System.out.println("-----Operations for saleperson menu-----");
        System.out.println("Which kinds of operation would you like to perform?");
        System.out.println("1. Search for parts");
        System.out.println("2. Sell a part");
        System.out.println("3. Return to the main menu");
        System.out.print("Enter Your Choice: ");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextInt();
        if(choice == 1){
            search_for_parts();
        }
        if(choice == 2){
            sell_a_part();
        }
        if(choice == 3){
            System.exit(0);
        }
    }

    public static void search_for_parts(){
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

    public static void sell_a_part(){
        int part_id = 0;
        int salesperson_id = 0;
        String part_name = "";
        System.out.println("Enter the Part ID: ");
        Scanner scan = new Scanner(System.in);
        part_id = scan.nextInt();
        System.out.println("Enter the Salesperson ID: ");
        salesperson_id = scan.nextInt();
        
        Statement stmt = conn.createStatement();                              // conn for connection in main file
        ResultSet rs;
        rs = stmt.executeQuery("SELECT pAvailableQuantity FROM Part
                                WHERE pID = " + part_id);
        int quantity = 0;
        while (rs.next()){
            quantity = rs.getInt("pAvailableQuantity");
        }
        if(quantity > 0){
            // update [Part] table
            rs = stmt.executeQuery("UPDATE Part
                                    SET pAvailableQuantity = " + (quantity - 1) +
                                    "WHERE pID = " + part_id);
            rs = stmt.executeQuery("SELECT pName, pAvailableQuantity
                                    FROM Part
                                    WHERE pID = " + part_id);
            while (rs.next()){
                part_name = rs.getString("pName");
                quantity = rs.getInt("pAvailableQuantity");
            }
            System.out.println("Product: " + part_name + "(id: " + part_id + ") Remaining Quantity: " + quantity);

            // update [Transaction Records] table
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyy");
            rs = stmt.executeQuery("INSERT INTO transaction(pID, sID, tDate)
                                    VALUES(" + part_id + ", " + salesperson_id + ", " + ft.format(date) + ")");
        }
        else{
            System.out.println("The part is sold out!!");
        }
        stmt.close();
        rs.close();
    }

    public static void search_by_part(){
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
        Statement stmt = conn.createStatement();                                       // conn for connection in main file
        ResultSet rs;
        if(order == 1){       //ascending
            rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
                                    FROM Part, Manufacturer, Category
                                    WHERE pName = '" + keyword + "'
                                    AND Part.mID = Manufacturer.mID
                                    AND Part.cID = Category.cID
                                    ORDER BY pPrice ASC");
            int id; String pname; String mname; String cname; int q; int w; int p;
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
            System.out.print("End of Query");
        }
        if(order == 2){       //descending
            rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
                                    FROM Part, Manufacturer, Category
                                    WHERE pName = '" + keyword + "'
                                    AND Part.mID = Manufacturer.mID
                                    AND Part.cID = Category.cID
                                    ORDER BY pPrice DESC");
            int id; String pname; String mname; String cname; int q; int w; int p;
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
            System.out.print("End of Query");
        }
        stmt.close();
        rs.close();
    }

    public static void search_by_manufacturer(){
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
        Statement stmt = conn.createStatement();                                           // conn for connection in main file
        ResultSet rs;
        if(order == 1){       //ascending
            rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
                                    FROM Part, Manufacturer, Category
                                    WHERE mName = '" + keyword + "'
                                    AND Part.mID = Manufacturer.mID
                                    AND Part.cID = Category.cID
                                    ORDER BY pPrice ASC");
            int id; String pname; String mname; String cname; int q; int w; int p;
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
            System.out.print("End of Query");
        }
        if(order == 2){       //descending
            rs = stmt.executeQuery("SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
                                    FROM Part, Manufacturer, Category
                                    WHERE mName = '" + keyword + "'
                                    AND Part.mID = Manufacturer.mID
                                    AND Part.cID = Category.cID
                                    ORDER BY pPrice DESC");
            int id; String pname; String mname; String cname; int q; int w; int p;
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
            System.out.print("End of Query");
        }
        stmt.close();
        rs.close();
    }

}
