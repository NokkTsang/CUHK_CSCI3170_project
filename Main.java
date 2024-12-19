import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        System.out.println("hello world");
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
        } catch(Exception x) {
            System.err.println("Unable to load the driver class!");
        }


        Scanner myObj = new Scanner(System.in);
        System.out.println("who are you?");
        String userName = myObj.nextLine();
        System.out.println("Username is: " + userName);

        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk", "h128", "TwuOllEr");
            Statement stmt = conn.createStatement();
            System.out.println("Connected to the database.");
            String createTableSQL = "CREATE TABLE TEST_TABLE ("
                    + "ID INT PRIMARY KEY, "
                    + "NAME VARCHAR(50))";
            stmt.execute(createTableSQL);
            System.out.println("Table TEST_TABLE created successfully.");

            String insertSQL = "INSERT INTO TEST_TABLE (ID, NAME) VALUES (1, 'Alice')";
            stmt.executeUpdate(insertSQL);
            System.out.println("Data inserted successfully.");

            String selectSQL = "SELECT * FROM TEST_TABLE";
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                System.out.println("ID: " + id + ", Name: " + name);
            }
            
        } catch (Exception e) {
            System.err.println("Something went wrong connection!");
        }
        
        System.out.println("Exit");
    }
}