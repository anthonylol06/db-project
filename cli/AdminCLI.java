package cli;
import java.sql.SQLException;
import java.util.Scanner;
import cli.CLIInterface;
import models.Database;

public class AdminCLI implements CLIInterface {
    private Database db;
    private Scanner scanner;

    public AdminCLI (Database db, Scanner scanner){
        this.db = db;
        this.scanner = new Scanner(System.in);
    }

    public void startCLI(){
        while (true){
            printAdminMenu();
            int option = scanner.nextInt();
            System.out.printf("\n");
            switch (option){
                case 1: 
                    createTables();
                    System.out.printf("Done! Database is initialized!\n");
                    return;
                case 2:
                    deleteTables();
                    System.out.printf("Done! Database is removed!\n");
                    return;
                case 3:
                    loadData();
                    System.out.printf("Done! Data is inputted to the database!\n");
                    return;
                case 4: 
                    printTable();
                    return;
                case 5:
                    return;
                // Case 6 is for debugging: check if the tables exists
                // case 6: 
                //     special();
                //     return;
                default:
                    System.out.println("[Error] Invalid Operation. Try Again");
            }
        }
    }

    private void printAdminMenu(){
        System.out.println("-----Operations for administrator menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Create all tables");
        System.out.println("2. Delete all tables");
        System.out.println("3. Load from database");
        System.out.println("4. Show content of a table");
        System.out.println("5. Return to the main menu");
        System.out.printf("Enter your choice: ");
    }

    private void createTables(){
        System.out.printf("Processing...");
        try {
            this.db.createAllTables();
        } catch (SQLException e) {}
    }

    private void deleteTables(){
        System.out.printf("Processing...");
        try {
            this.db.deleteAllTables();
        } catch (SQLException e) {}
    }

    private void loadData(){
        Scanner scan = new Scanner(System.in);
        System.out.printf("Type in the Source Data Folder Path: ");
        String path = scan.nextLine();
        System.out.printf("Processing...");
        db.loadDataFromFiles(path);
    }

    // Case 6 is for debugging: check if the tables exists
    // private void special(){
    //     db.bruh();
    // }

    private void printTable(){
        String table;
        Scanner scan = new Scanner(System.in);
        System.out.printf("Which table would you like to show: ");
        table = scan.nextLine();
        try {
            db.showTableContent(table);
        } catch (SQLException e) {}
    }
}
