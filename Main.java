import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (Exception x) {
            System.err.println("Unable to load the driver class!");
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to sales system!\n");
            System.out.println("-----Main menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Operations for administrator");
                    break;
                case "2":
                    System.out.println("Operations for salesperson");
                    break;
                case "3":
                try {
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk", "h128", "TwuOllEr");
                    Manager manager = new Manager(conn);
                    manager.showMenu();
                } catch (Exception e) {
                    System.err.println("Something went wrong connection!");
                }
                    break;
                case "4":
                    System.out.println("Exit");
                    return;
            }
        }


    }



}
