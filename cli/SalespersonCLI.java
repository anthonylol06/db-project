package cli;
import cli.CLIInterface;

import java.sql.SQLException;
import java.util.Scanner;
import models.Database;

public class SalespersonCLI implements CLIInterface{
    private Database db;
    private Scanner scanner;

    public SalespersonCLI (Database db, Scanner scanner){
        this.db = db;
        this.scanner = scanner;
    }

    public void startCLI(){
        while (true){
            printSalespersonMenu();
            int option = scanner.nextInt();
            System.out.printf("\n");
            switch (option){
                case 1: 
                    searchParts();
                    return;
                case 2:
                    sellParts();
                    return;
                case 3:
                    return;
                default:
                    System.out.println("[Error] Invalid Operation. Try Again");
            }
        }
    }

    private void printSalespersonMenu(){
        System.out.println("-----Operations for manager menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Search for parts");
        System.out.println("2. Sell a part");
        System.out.println("3. Return to the main menu");
        System.out.printf("Enter your choice: ");
    }

    private void searchParts(){
        int mode, sort_order;
        String name;
        System.out.println("Choose the search criterion:");
        System.out.println("1. Part name");
        System.out.println("2. Manufacturer name");
        System.out.printf("Choose the search criterion: ");
        mode = scanner.nextInt();
        System.out.printf("Type in the search keyword: ");
        scanner.nextLine();
        name = scanner.nextLine();
        System.out.println("Choose Odering:");
        System.out.println("1. By price, in ascending order");
        System.out.println("2. By price, in descending order");
        System.out.printf("Choose the search criterion: ");
        sort_order = scanner.nextInt();

        if (mode == 1 || mode == 2){
            if (sort_order == 1 || sort_order == 2){
                try {
                    db.searchParts(mode, name, sort_order);
                    System.out.println("End of Query");
                } catch (SQLException e){}
            } else {
                System.out.println("[Error] Invalid Input.");
                return;
            }
        } else {
            System.out.println("[Error] Invalid Input.");
            return;
        }
    }

    private void sellParts() {
        int pID, sID;
        System.out.printf("Enter The Part ID: ");
        pID = scanner.nextInt();
        System.out.printf("Enter The Salesperson ID: ");
        sID = scanner.nextInt();

        try{
            db.makeTransactions(pID, sID);
        } catch (SQLException e){}
    }
}