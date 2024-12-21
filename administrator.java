import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class administrator {

    public static void mainManuDisplay(){
        System.out.println("Welcome to the sales system!");
        String mainManu = "-----Main menu-----\n" +
                "What kinds of operation would you like to perform?" +
                "1. Operations for administrator\n" +
                "2. Operations for salesperson\n" +
                "3. Operations for manager\n" +
                "4. Exit this program\n";
        System.out.println(mainManu);
        System.out.println("Enter Your Choice: ");
    }

    public static void administratorMenuDisplay(){
        String operationAdministrator = "-----Operations for administrator menu-----\n" +
                "What kinds of operation would you like to perform?\n" +
                "1. Create all tables\n" +
                "2. Delete all tables\n" +
                "3. Load from datafile\n" +
                "4. Show content of a table\n" +
                "5. Return to the main menu\n";
        System.out.println(operationAdministrator);
        System.out.println("Enter Your Choice: ");
    }

    public static void mainMenu(int choice){
        if(choice == 4){
            System.exit(1);
        }
        do {
            if (choice == 1) {
                Scanner choiceOfAdministrator = new Scanner(System.in);
                int choiceAdministratorMenu = choiceOfAdministrator.nextInt();
                do{
                    administratorMenu(choiceAdministratorMenu);
                }while(choiceAdministratorMenu != 5);
            }
        }while(choice != 5);
    }

    public static void administratorMenu(int choiceAdministratorMenu){
        administratorMenuDisplay();
        if (choiceAdministratorMenu == 1) {
            System.out.print("Processing... ");
            //try sql query
            try {
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk",
                        "h079", "IgZadNes");
                //System.out.println("Connected to the database.");

                //read .sql file
                BufferedReader reader = new BufferedReader(new FileReader("create_table.sql"));
                String line;
                StringBuilder sql = new StringBuilder();
                while((line = reader.readLine()) != null) {
                    sql.append(line);
                }
                reader.close();

                //split the sql queries
                String[] queries = sql.toString().split(";");
                Statement stmt = conn.createStatement();
                for (String query : queries) {
                    stmt.executeUpdate(query);
                }
                stmt.close();
                conn.close();

                //System.out.println("Table created successfully.");
            } catch (Exception e) {
                System.err.println("Something went wrong connection!");
            }

            System.out.println("Done! Database is initialized!");
        } else if (choiceAdministratorMenu == 2) {
            //try sql query
            //try sql query
            try {
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk",
                        "h079", "IgZadNes");
                //System.out.println("Connected to the database.");

                //read .sql file
                BufferedReader reader = new BufferedReader(new FileReader("drop_table.sql"));
                String line;
                StringBuilder sql = new StringBuilder();
                while((line = reader.readLine()) != null) {
                    sql.append(line);
                }
                reader.close();

                //split the sql queries
                String[] queries = sql.toString().split(";");
                Statement stmt = conn.createStatement();
                for (String query : queries) {
                    stmt.executeUpdate(query);
                }
                stmt.close();
                conn.close();

                //System.out.println("Table deleted successfully.");
            } catch (Exception e) {
                System.err.println("Something went wrong connection!");
            }

            System.out.println("Processing... Done! Database is removed!");
        } else if (choiceAdministratorMenu == 3) {
            System.out.println("Type in the Source Data Folder Path: ");
            Scanner path = new Scanner(System.in);
            String sourcePath = path.nextLine();

            //try sql query
            System.out.println("Processing... Done! Data is inputted to the database!");
        } else if (choiceAdministratorMenu == 4) {
            System.out.println("Which table would you like to show: ");
            //scan
            Scanner tableScan = new Scanner(System.in);
            String tableName = tableScan.nextLine();

            System.out.println("Content of table : \n");
            //try sql query
        } else if (choiceAdministratorMenu == 5) {
            System.out.println("Returning to the main menu");
            //go back to main menu
        }
    }

    public static void main(String[] args) {
        mainManuDisplay();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        while(choice < 4) {
            mainMenu(choice);
        }
    }
}
