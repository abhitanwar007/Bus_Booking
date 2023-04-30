package com.bookMyBus.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BookBus {
	static String user_name;
	static Scanner sc = new Scanner(System.in);
	static Connection con = MainApp.con;
	private static PreparedStatement pstmt;
	private static ResultSet res;

	static void ticket(String user) {
		user_name = user;
		System.out.println("Select an option\n" 
				+ "1. View buses\n" 
				+ "2. book ticket\n" 
				+ "3. update booking detail\n"
				+ "4. Delete booking\n" 
				+ "5. Logout");
		int choice = sc.nextInt();
		switch (choice) {
		case 1: {
			viewBuses();
		}
		case 2: {
			bookTicket();
		}
		case 3: {
			updateBooking();
		}
		case 4: {
			deleteBooking();
		}
		case 5: {
			MainApp.main(null);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}

	static void deleteBooking() {
		try {
			String sql = "delete from buses where id=?";
			pstmt = con.prepareStatement(sql);
			System.out.println("Enter the bus id");
			pstmt.setInt(1, sc.nextInt());

			int x = pstmt.executeUpdate();
			if (x > 0) {
				System.out.println("Bus removed");
				System.out.println("**********************");
				ticket(user_name);
			} else {
				System.out.println("Bus deletion Failed");
				System.out.println("**********************");
				ticket(user_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void updateBooking() {
		try {
			String sql = "update buses set arival_time=? , departure_time=? where user_name=? and id=?";
			pstmt = con.prepareStatement(sql);
			System.out.println("Enter the bus id");
			pstmt.setInt(4, sc.nextInt());
			pstmt.setString(3, user_name);
			System.out.println("Enter the Arival time");
			pstmt.setString(1, sc.next());
			System.out.println("Enter the Dreparture time");
			pstmt.setString(2, sc.next());

			int x = pstmt.executeUpdate();
			if (x > 0) {
				System.out.println("Bus details Updated");
				System.out.println("**********************");
				ticket(user_name);
			} else {
				System.out.println("Bus Updation Failed");
				System.out.println("**********************");
				ticket(user_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void bookTicket() {
		try {
			String route_start="";
			String route_end="";
			String arivalTime="";
			String departureTime="";
			
			System.out.println("Enter bus id to book the ticket: ");
			int bus_id=sc.nextInt();
			
			String sql1 = "Select * from buses where id=?";
			pstmt = con.prepareStatement(sql1);
			pstmt.setInt(1, bus_id);
			res = pstmt.executeQuery();
			while (res.next() == true) {
				System.out.println("Bus Id: " + res.getInt(1));
				System.out.println("Bus Name: " + res.getString(2));
				System.out.println("Bus Start: " + res.getString(3));
				System.out.println("Bus End: " + res.getString(4));
				System.out.println("Bus Arival: " + res.getString(5));
				System.out.println("Bus Departure: " + res.getString(6));
				System.out.println("-----------------------------------");
				route_start=res.getString(3);
				route_end =res.getString(4);
				arivalTime=res.getString(5);
				departureTime =res.getString(6);
			}
			
			String sql2 = "insert into ticket (uname,start_location,end_location,arival_time,destination_time) values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, user_name);
			pstmt.setString(2, route_start);
			pstmt.setString(3, route_end);
			pstmt.setString(4, arivalTime);
			pstmt.setString(5, departureTime);
			int x= pstmt.executeUpdate();
			if(x>0)
			{
				System.out.println("Ticket booked Successfully.\n");
				System.out.println("**********************");
				ticket(user_name);
			} else {
				System.out.println("Ticket booking Failed.");
				System.out.println("**********************");
				ticket(user_name);
			}
			System.out.println("**********************");
			ticket(user_name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void viewBuses() {
		try {
			System.out.println("Search bus from which location: ");
			String source=sc.next();
			String sql = "Select * from buses where route_start=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, source);
			res = pstmt.executeQuery();
			while (res.next() == true) {
				System.out.println("Bus Id: " + res.getInt(1));
				System.out.println("Bus Name: " + res.getString(2));
				System.out.println("Bus Start: " + res.getString(3));
				System.out.println("Bus End: " + res.getString(4));
				System.out.println("Bus Arival: " + res.getString(5));
				System.out.println("Bus Departure: " + res.getString(6));
				System.out.println("-----------------------------------");
			}
			System.out.println("**********************");
			ticket(user_name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
