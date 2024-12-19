import java.util.Scanner;

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

    public static void main(String[] args) {
        int choice;
        System.out.println("-----Operations for saleperson menu-----");
        System.out.println("Which kinds of operation would you like to perform?");
        System.out.println("1. Search for parts");
        System.out.println("2. Sell a part");
        System.out.println("3. Return to the main menu");
        System.out.print("Enter Your Choice: ");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextInt();
        if(choice == 1){}
        if(choice == 2){}
        if(choice == 3){}

    }

    public void search_for_parts(){
        int choice;
        System.out.println("Choose the Search criterion");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");
        System.out.print("Choose the search criterion: ");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextInt();
        if(choice == 1){      //part
        }
        if(choice == 2){      //manufacturer
        }
    }

    public void sell_a_part(){
        int part_id;
        int salesperson_id;
        System.out.println("Enter the Part ID: ");
        Scanner scan = new Scanner(System.in);
        part_id = scan.nextInt();
        System.out.println("Enter the Salesperson ID: ");
        salesperson_id = scan.nextInt();
        //update from database
    }

    public static void search_by_part(){
        String keyword;
        int order;
        System.out.print("Type in the Search Keyword: ");
        Scanner scan = new Scanner(System.in);
        keyword = scan.nextLine();
        System.out.println("Choose ordering:");
        System.out.println("1. By price, ascending order");
        System.out.println("2. By price, descending order");
        System.out.print("Choose the search criterion: ");
        order = scan.nextInt();
        if(order == 1){       //ascending
        }
        if(order == 2){       //descending
        }
    }

    public void search_by_manufacturer(){
        String keyword;
        int order;
        System.out.print("Type in the Search Keyword: ");
        Scanner scan = new Scanner(System.in);
        keyword = scan.nextLine();
        System.out.println("Choose ordering:");
        System.out.println("1. By price, ascending order");
        System.out.println("2. By price, descending order");
        System.out.print("Choose the search criterion: ");
        order = scan.nextInt();
        if(order == 1){       //ascending
        }
        if(order == 2){       //descending
        }
    }

}
