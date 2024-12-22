import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class Administrator {
    private final Connection conn;

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
        System.out.println();
        System.out.print(operationAdministrator);
        System.out.print("Enter Your Choice: ");
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

    private static String generateInsertSQL(String tableName, String columns){
        String[] columnsArray = columns.split(",");
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < columnsArray.length; i++) {
            placeholders.append("?");
            if (i < columnsArray.length - 1) {
                placeholders.append(",");
            }
        }
        return "INSERT INTO " + tableName + " (" + columns + ") values ('" + placeholders.toString() + "')";
    }

    public void importTxtToDatabase(String tableName, String columns, String filename, String delimiter){
        String sql = generateInsertSQL(tableName, columns);
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            PreparedStatement statement = conn.prepareStatement(sql);

            String lines;
            while ((lines = br.readLine()) != null) {
                String[] data = lines.split(delimiter);

                for (int i = 0; i < data.length; i++) {
                    statement.setString(i + 1, data[i]);
                }

                statement.executeUpdate();
            }
        } catch (Exception e){
            System.err.println("Something went wrong when importing txt to database!");
        }
    }

    public void administratorMenu(int choiceAdministratorMenu){
        if (choiceAdministratorMenu == 1) {
            System.out.print("Processing...");
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

            } catch (Exception e) {
                System.err.println("Something went wrong connection!");
                e.printStackTrace();
            }
            System.out.println("Done! Database is initialized!");
        } else if (choiceAdministratorMenu == 2) {
            System.out.print("Processing...");
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
                e.printStackTrace();
            }
            System.out.println("Done! Database is removed!");
        } else if (choiceAdministratorMenu == 3) {
            System.out.println();
            System.out.print("Type in the Source Data Folder Path: ");
            Scanner path = new Scanner(System.in);
            String sourcePath = path.nextLine();
            System.out.print("Processing...");
            //try sql query
            try{
                File dir = new File(sourcePath);
                File[] filesList = dir.listFiles();
                // fetch all file in the folder
                try{
                    String TableName1 = "category";
                    String TableName2 = "manufacturer";
                    String TableName3 = "part";
                    String TableName4 = "salesperson";
                    String TableName5 = "transaction";

                    String columns1 = "cID,cName";
                    String columns2 = "mID,mName,mAddress,mPhoneNumber";
                    String columns3 = "pID,pName,pPrice,mID,cID,pWarrantyPeriod,pAvailableQuantity";
                    String columns4 = "sID,sName,sAddress,sPhoneNumber,sExperience";
                    String columns5 = "tID,pID,sID,tDate";

                    importTxtToDatabase(TableName1,columns1,filesList[0].getAbsolutePath(),"\t");
                    importTxtToDatabase(TableName2,columns2,filesList[1].getAbsolutePath(),"\t");
                    importTxtToDatabase(TableName3,columns3,filesList[2].getAbsolutePath(),"\t");
                    importTxtToDatabase(TableName4,columns4,filesList[3].getAbsolutePath(),"\t");
                    importTxtToDatabase(TableName5,columns5,filesList[4].getAbsolutePath(),"\t");

                } catch (Exception e){
                    System.err.println("Something went wrong when inserting data files!");
                }
            }catch (Exception e){
                System.err.println("Something went wrong!");
            }
            System.out.println("Done! Data is inputted to the database!");
        } else if (choiceAdministratorMenu == 4) {
            System.out.print("Which table would you like to show: ");
            //scan
            Scanner tableScan = new Scanner(System.in);
            String tableName = tableScan.nextLine();
            System.out.printf("Content of table %s: \n",tableName);
            switch (tableName) {
                case "category" -> System.out.print("| c_id | c_name |\n");
                case "manufacturer" -> System.out.print("| m_id | m_name | m_address | m_phone_number |\n");
                case "part" -> System.out.print("| p_id | p_name | p_price | m_id | c_id | p_warranty | p_quantity |\n");
                case "salesperson" -> System.out.print("| s_id | s_name | s_address | s_phone_number | s_experience |\n");
                case "transaction" -> System.out.print("| t_id | p_id | s_id | t_date |\n");
            }
            //try sql query
            try {
                Statement stmt = conn.createStatement();
                String query = String.format("select * from %s", tableName);
                ResultSet rs = stmt.executeQuery(query);
                switch (tableName) {
                    case "category" -> {
                        while (rs.next()) {
                            int cID = rs.getInt("cID");
                            String cName = rs.getString("cName");
                            System.out.printf("| %d | %s |\n", cID, cName);
                        }
                        System.out.println("End of Query");
                    }
                    case "manufacturer" -> {
                        while (rs.next()) {
                            int mID = rs.getInt("mID");
                            String mName = rs.getString("mName");
                            String mAddress = rs.getString("mAddress");
                            int mPhoneNumber = rs.getInt("mPhoneNumber");
                            System.out.printf("| %d | %s | %s | %d |\n", mID, mName, mAddress, mPhoneNumber);
                        }
                        System.out.println("End of Query");
                    }
                    case "part" -> {
                        while (rs.next()) {
                            int pID = rs.getInt("pID");
                            String pName = rs.getString("pName");
                            int pPrice = rs.getInt("pPrice");
                            int mID = rs.getInt("mID");
                            int cID = rs.getInt("cID");
                            int pWarrantyPeriod = rs.getInt("pWarrantyPeriod");
                            int pAvailableQuantity = rs.getInt("pAvailableQuantity");
                            System.out.printf("| %d | %s | %d | %d | %d | %d | %d |\n", pID, pName, pPrice, mID, cID, pWarrantyPeriod, pAvailableQuantity);
                        }
                        System.out.println("End of Query");
                    }
                    case "salesperson" -> {
                        while (rs.next()) {
                            int sID = rs.getInt("sID");
                            String sName = rs.getString("sName");
                            String sAddress = rs.getString("sAddress");
                            int sPhoneNumber = rs.getInt("sPhoneNumber");
                            int sExperience = rs.getInt("sExperience");
                            System.out.printf("| %d | %s | %s | %d | %d |\n", sID, sName, sAddress, sPhoneNumber, sExperience);
                        }
                        System.out.println("End of Query");
                    }
                    case "transaction" -> {
                        while (rs.next()) {
                            int tID = rs.getInt("tID");
                            int pID = rs.getInt("pID");
                            int sID = rs.getInt("sID");
                            String tDate = rs.getString("tDate");
                            System.out.printf("| %d | %d | %d | %s |\n", tID, pID, sID, tDate);
                        }
                        System.out.println("End of Query");
                    }
                }
                //show the results


            } catch (Exception e) {
                System.err.println("Something went wrong connection!");
            }
        }
    }

}
