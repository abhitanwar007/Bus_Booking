package com.bookMyBus.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class MainApp {
	public static Connection con;

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busbooking","root","root");
			System.out.println("************connected to the database ************");
			System.out.println("Please select an option\n"
					+"1. Register as new User\n"
					+"2. admin Login\n"
					+"3. user Login\n"
					+"4. Exit");
			Scanner sc = new Scanner(System.in);
			int choice=sc.nextInt();
			if(choice==1) {
				Users.userRegister();
			}else if(choice==2) {
				Admin.adminLogin();
			}else if(choice==3) {
				Users.userLogin();
			}else if(choice==4) {
				System.exit(0);
			}else {
				System.out.println("Wrong Option selected Refresh the page");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
