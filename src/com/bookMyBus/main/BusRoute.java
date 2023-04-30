package com.bookMyBus.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BusRoute {
	static String user_name;
	static Scanner sc = new Scanner(System.in);
	static Connection con = MainApp.con;
	private static PreparedStatement pstmt;
	private static ResultSet res;

	static void buses(String user) {
		user_name = user;
		System.out.println("Select an option\n" 
					+ "1. Add bus\n" 
					+ "2. view buses\n" 
					+ "3. update bus detail\n"
					+ "4. Delete bus\n" 
					+ "5. Logout");
		int choice = sc.nextInt();
		switch (choice) {
		case 1: {
			addBus();
		}
		case 2: {
			viewBuses();
		}
		case 3: {
			updateBus();
		}
		case 4: {
			deleteBus();
		}
		case 5: {
			MainApp.main(null);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}

	static void deleteBus() {
		try {
			String sql ="delete from buses where id=?";
			pstmt = con.prepareStatement(sql);
			System.out.println("Enter the bus id");
			pstmt.setInt(1, sc.nextInt());
			
			int x = pstmt.executeUpdate();
			if(x>0) {
				System.out.println("Bus removed");
				System.out.println("**********************");
				buses(user_name);
			}else {
				System.out.println("Bus deletion Failed");
				System.out.println("**********************");
				buses(user_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void updateBus() {
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
				buses(user_name);
			} else {
				System.out.println("Bus Updation Failed");
				System.out.println("**********************");
				buses(user_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void viewBuses() {
		try {
			String sql = "Select * from buses";
			pstmt = con.prepareStatement(sql);
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
			buses(user_name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void addBus() {
		try {
			System.out.println(con);
			System.out.println("Enter Bus Name:");
			String bus_name = sc.next();
			System.out.println("Enter bus starting location:");
			String route_start = sc.next();
			System.out.println("Enter bus Destination location:");
			String route_end = sc.next();
			System.out.println("Enter bus arival time:");
			String arivalTime = sc.next();
			System.out.println("Enter bus Departure time:");
			String departureTime = sc.next();

			String sql = "insert into buses (busname,route_start,route_end,arival_time,departure_time,user_name) value(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bus_name);
			pstmt.setString(2, route_start);
			pstmt.setString(3, route_end);
			pstmt.setString(4, arivalTime);
			pstmt.setString(5, departureTime);
			pstmt.setString(6, user_name);
			int x = pstmt.executeUpdate();
			if (x > 0) {
				System.out.println("bus Added Successfully.\n");
				System.out.println("**********************");
				buses(user_name);
			} else {
				System.out.println("bus Addition Failed.");
				System.out.println("**********************");
				buses(user_name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
