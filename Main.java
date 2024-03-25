import java.sql.SQLException;
import java.util.Scanner;

import models.*;
import cli.MainCLI;

public class Main{
	public static void main(String args[]){
		Database db = new Database();
		try {
			db.connect();
		} 
		catch (ClassNotFoundException e){
			System.out.println("[Error] Java MySQL DB Driver not found!");
            System.exit(0);
		} 
		catch (SQLException e){
			System.out.println(e);
		}

		System.out.println("Welcome to sales system!\n");

		MainCLI cli = new MainCLI(db);
        cli.startCLI();
	}
}