package com.lottery.test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;

public class connectionTest {

	public static void main(String[] args) {
		final String connectionUrl ="jdbc:sqlserver://localhost:1433;databaseName=TS;encrypt=true;trustServerCertificate=true";
		final String user = "tts";
		final String pwd = "1234";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String L_RecId = "" , L_Lottery = "";
		try {
			conn = DriverManager.getConnection(connectionUrl ,user ,pwd);
			if(conn != null) {
				DatabaseMetaData metaData = conn.getMetaData();
				System.out.println("1>" + metaData.getDatabaseProductName());
				System.out.println("2>" + metaData.getDatabaseProductVersion());
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select top 1 * from lo_main");
				if (rs.next()) {
					L_RecId   = rs.getString("L_RecId");
					L_Lottery = rs.getString("L_Lottery");
				}rs.close();rs=null;
				System.out.println("L_RecId> " + L_RecId);
				System.out.println("L_Lottery> " + L_Lottery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		}

	}

}
