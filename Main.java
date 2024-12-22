import java.sql.*;
import java.util.Scanner;
public class Main {

    public static void mainManuDisplay(){
        System.out.println("Welcome to the sales system!");
        String mainManu = "-----Main menu-----\n" +
                "What kinds of operation would you like to perform?\n" +
                "1. Operations for administrator\n" +
                "2. Operations for salesperson\n" +
                "3. Operations for manager\n" +
                "4. Exit this program\n";
        System.out.print(mainManu);
        System.out.print("Enter Your Choice: ");
    }

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (Exception x) {
            System.err.println("Unable to load the driver class!");
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            mainManuDisplay();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk",
                                "h128", "TwuOllEr");
                        Administrator administrator = new Administrator(conn);
                        administrator.jumpToAdministratorMenu();
                        System.out.println();
                    } catch (Exception e){
                        System.err.println("Something went wrong connection!");
                    }
                    break;
                case "2":
                    break;
                case "3":
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk",
                                "h128", "TwuOllEr");
                        Manager manager = new Manager(conn);
                        manager.showMenu();
                        System.out.println();
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
