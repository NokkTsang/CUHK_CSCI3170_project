import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class Administrator {
    private Connection conn;

    public Administrator(Connection conn) {
        this.conn = conn;
    }

    public void administratorMenuDisplay(){
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

    public void jumpToAdministratorMenu(){
        while(true){
            administratorMenuDisplay();
            Scanner choiceOfAdministrator = new Scanner(System.in);
            int choiceAdministratorMenu = choiceOfAdministrator.nextInt();
            if (choiceAdministratorMenu == 5) {
                System.out.println("Returning to the main menu");
                return;
            }
            administratorMenu(choiceAdministratorMenu);
        }
    }

    public void administratorMenu(int choiceAdministratorMenu){
        if (choiceAdministratorMenu == 1) {
            System.out.print("Processing... ");
            //try sql query
            try {
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

                //System.out.println("Table created successfully.");
            } catch (Exception e) {
                System.err.println("Something went wrong connection!");
            }
            System.out.println("Done! Database is initialized!");
        } else if (choiceAdministratorMenu == 2) {
            System.out.println("Processing... Done!");
            //try sql query
            try {
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
        }
    }

}
