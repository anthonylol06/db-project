package cli;
import cli.CLIInterface;
import java.util.Scanner;
import models.Database;

public class MainCLI implements CLIInterface {
    private Database db;
    private Scanner scanner;

    public MainCLI (Database db){
        this.db = db;
        this.scanner = new Scanner(System.in);
    }

    public void startCLI(){
        while (true){
            printMainMenu();
            int option = scanner.nextInt();
            System.out.printf("\n");
            CLIInterface c = null;
            switch (option){
                case 1: 
                    c = new AdminCLI(db, scanner);
                    break;
                case 2: 
                    c = new SalespersonCLI(db, scanner);
                    break;
                case 3:
                    c = new ManagerCLI(db, scanner);
                    break;
                case 4:
                    return ;
                default:
                    System.out.println("[Error]: Invalid Operation. Try again!");
            }
            if (c != null) c.startCLI();
        }
    }

    private void printMainMenu() {
        System.out.println("-----Main menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Operations for administrator");
        System.out.println("2. Operations for salesperson");
        System.out.println("3. Operations for manager");
        System.out.println("4. Exit this program");
        System.out.printf("Enter your choice: ");
    }
}
