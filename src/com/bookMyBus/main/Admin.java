package com.bookMyBus.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Admin {
	static String user_name;
	static String upassword;
	static Connection con = MainApp.con;
	static Scanner sc = new Scanner(System.in);
	
	private static PreparedStatement pstmt;
	private static ResultSet res;
	
	public Admin(String user_name, String upassword) {
		super();
		this.user_name = user_name;
		this.upassword = upassword;
	}
	
	public static String getUser_name() {
		return user_name;
	}
	public static void setUser_name(String user_name) {
		Admin.user_name = user_name;
	}
	public static String getUpassword() {
		return upassword;
	}
	public static void setUpassword(String upassword) {
		Admin.upassword = upassword;
	}
	
	static void adminLogin() {
		try {
			System.out.println(con);
			System.out.println("Enter the User name:");
			user_name = sc.next();
			System.out.println("Enter the Password:");
			upassword = sc.next();
			String sql = "select * from admin where user_name=? and upassword=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);
			pstmt.setString(2, upassword);
			res = pstmt.executeQuery();
			if (res.next()==true) {
				System.out.println("Login Successfull.\n"
						+"******Welcome to your BusBooking Application "+res.getString(1).toUpperCase()+"******");
				BusRoute.buses(res.getString(1));
			} else {
				System.out.println("User Login Failed. Refresh the page to re-Login again");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
