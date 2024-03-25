package cli;
import cli.CLIInterface;

import java.sql.SQLException;
import java.util.Scanner;
import models.Database;

public class ManagerCLI implements CLIInterface{
    private Database db;
    private Scanner scanner;

    public ManagerCLI (Database db, Scanner scanner){
        this.db = db;
        this.scanner = scanner;
    }

    public void startCLI(){
        while (true){
            printManagerMenu();
            int option = scanner.nextInt();
            System.out.printf("\n");
            switch (option){
                case 1: 
                    listSalesperson();
                    System.out.printf("\n");
                    return;
                case 2:
                    rangeOfExp();
                    System.out.printf("\n");
                    return;
                case 3:
                    totalSalesValue();
                    System.out.printf("\n");
                    return;
                case 4: 
                    nMostPopular();
                    System.out.printf("\n");
                    return;
                case 5:
                    return;
                default:
                    System.out.println("[Error] Invalid Operation. Try Again\n");
            }
        }
    }

    private void printManagerMenu(){
        System.out.println("-----Operations for manager menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. List all salespersons");
        System.out.println("2. Count the no. of sales record of each salesperson under a specific range of years of experience");
        System.out.println("3. Show tht total sales value of each manufacturer");
        System.out.println("4. Show the N most popular part");
        System.out.println("5. Return to the main menu");
        System.out.printf("Enter your choice: ");
    }

    private void rangeOfExp(){
        int lower_bound = 0, upper_bound = 0;
        System.out.printf("Type in the lower bound for years of experience: ");
        lower_bound = scanner.nextInt();
        System.out.printf("Type in the upper bound for years of experience: ");
        upper_bound = scanner.nextInt();
        if (upper_bound > lower_bound) {
            try{
                db.transactionWithinNumOfExp(lower_bound, upper_bound);
                System.out.println("End of Query");
            } catch (SQLException e){}
        } else {
            System.out.println("[Error] Invalid input");
            return;
        }
    }

    private void listSalesperson(){
        int option = 0;
        System.out.println("Choose odering:");
        System.out.println("1. By ascending order");
        System.out.println("2. By descending order");
        System.out.printf("Choose the list ordering: ");
        option = scanner.nextInt();
        switch (option){
            case 1:
                try{
                    db.listAllSalesperson(option);
                } catch (SQLException e){} 
                break;
            case 2:
                try{
                    db.listAllSalesperson(option);
                } catch (SQLException e){}
                break;
            default:
                System.out.println("[Error] Invalid input");
                return;
        }
    }
    
    private void totalSalesValue(){
        try {
            db.showTotalSalesValueManufacturer();
            System.out.println("End of Query");
        } catch (SQLException e){}
    }

    private void nMostPopular(){
        int n = 0;
        System.out.print("Type in the number of parts: ");
        n = scanner.nextInt();
        if (n > 0){
            try {
                db.nMostPopularParts(n);
                System.out.println("End of Query");
            } catch (SQLException e){}
        } else {
            System.out.println("[Error] Invalid input");
            return;
        }
    }
}
