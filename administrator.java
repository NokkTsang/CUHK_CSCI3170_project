import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class administrator {

    public static void mainManuDisplay(){
        System.out.println("Welcome to the sales system!");
        String mainManu = "-----Main menu-----\n" +
                "What kinds of operation would you like to perform?\n" +
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
        if(choice == 4) {
            System.exit(1);
        }
        if (choice == 1) {
            administratorMenuDisplay();
            Scanner choiceOfAdministrator = new Scanner(System.in);
            int choiceAdministratorMenu = choiceOfAdministrator.nextInt();
            do{
                administratorMenu(choiceAdministratorMenu);
            }while(choiceAdministratorMenu != 5);
        }
    }

    public static void administratorMenu(int choiceAdministratorMenu){
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
            System.out.println("Processing... Done!");
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
            } catch (Exception e) {
                System.err.println("Something went wrong connection!");
            }
            System.out.println("Database is removed!");
        } else if (choiceAdministratorMenu == 3) {
            System.out.println("Type in the Source Data Folder Path: ");
            Scanner path = new Scanner(System.in);
            String sourcePath = path.nextLine();
            System.out.print("Processing...");
            //try sql query
            try{
                File dir = new File(sourcePath);
                File[] files = dir.listFiles();
                // fetch all file in the folder
                try{
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk",
                            "h079", "IgZadNes");
                    Statement stmt = conn.createStatement();
                    String query1 = String.format("LOAD DATA INFILE %s INTO TABLE category", files[0]);
                    String query2 = String.format("LOAD DATA INFILE %s INTO TABLE manufacturer", files[1]);
                    String query3 = String.format("LOAD DATA INFILE %s INTO TABLE part", files[2]);
                    String query4 = String.format("LOAD DATA INFILE %s INTO TABLE salesperson", files[3]);
                    String query5 = String.format("LOAD DATA INFILE %s INTO TABLE transaction", files[4]);

                    stmt.executeUpdate(query1);
                    stmt.executeUpdate(query2);
                    stmt.executeUpdate(query3);
                    stmt.executeUpdate(query4);
                    stmt.executeUpdate(query5);

                    stmt.close();
                    conn.close();

                } catch (Exception e){
                    System.err.println("Something went wrong when inserting data files!");
                }
            }catch (Exception e){
                System.err.println("Something went wrong!");
            }

            System.out.println("Done! Data is inputted to the database!");
        } else if (choiceAdministratorMenu == 4) {
            System.out.println("Which table would you like to show: ");
            //scan
            Scanner tableScan = new Scanner(System.in);
            String tableName = tableScan.nextLine();
            System.out.println("Content of table : \n");
            //try sql query
            try {
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk",
                        "h079", "IgZadNes");
                Statement stmt = conn.createStatement();
                String query = String.format("select * from %s", tableName); // do not add a semi-colon here
                System.out.println(query);
                ResultSet rs = stmt.executeQuery(query);
                //show the results
                while (rs.next()) {
                    String result = rs.getString("table");
                    System.out.println(result);
                }

            } catch (Exception e) {
                System.err.println("Something went wrong connection!");
            }
        } else if (choiceAdministratorMenu == 5) {
            System.out.println("Returning to the main menu");
            //go back to main menu
        }
    }

    public static void main(String[] args) {
        mainManuDisplay();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        mainMenu(choice);
    }
}
