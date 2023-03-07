package com.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import core.sql.LinkSQL;

import com.common.Crypt;
import com.common.Func;

public class UserDAOImpl implements UserDAO{

	private Statement stmt = null;
	private ResultSet rs = null;
	private Connection conn = null;
	
	LinkSQL sqlserver = new LinkSQL();
	Crypt crypt = new Crypt();
	Func func = new Func();
	
	public void sysout(String str) {
		System.out.println(str);
	}

	public void ReSetResult(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 釋放空間
	public void free() {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void LinkToMySQL() {
		try {
			Class.forName(sqlserver.DB_DRIVER);
			conn = DriverManager.getConnection(sqlserver.DB_URL ,sqlserver.USER ,sqlserver.PASSWORD);
//			sysout("in LinkToMySQL");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean userSignUp(UserVO vo) {
		LinkToMySQL();
		boolean boo = false;
		PreparedStatement pstmt = null;
		String SqlStr = "";
		String name = "" , email = "" , pwd = "";
		name  	  = vo.getOrgName();
		email 	  = vo.getEmail();
		pwd       = vo.getPassword();
		try {
			if(vo.isAdmin()) {
				System.out.println("a1");
				pstmt = (PreparedStatement) conn.prepareStatement("insert into Org (OrgName ,Email ,PWD ,isAdmin) values (?,?,?,?)");
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				pstmt.setString(3, pwd);
				pstmt.setString(4, "1");
				pstmt.executeUpdate();
				pstmt.close();
				boo = true;
			}else {
				System.out.println("a2");
				pstmt = (PreparedStatement) conn.prepareStatement("insert into Org (OrgName ,Email ,PWD) values (?,?,?)");
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				pstmt.setString(3, pwd);
				pstmt.executeUpdate();
				pstmt.close();
				boo = true;
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn ,rs ,stmt ,pstmt);
		}
		return boo;
	}

	@Override
	public UserVO userLogin(JSONObject jsonobject) {
		LinkToMySQL();
		UserVO vo = new UserVO();
		String SqlStr = "";
		String email = "" , pwd = "";
		email = jsonobject.getString("email");
		pwd   = jsonobject.getString("pwd");
		try {
			SqlStr = "select OrgId ,OrgName ,isAdmin from Org where Email='"+email +"' and PWD='"+pwd+"'";
			ReSetResult(SqlStr);
			sysout("dao SqlStr> " + SqlStr);
			if(rs.next()) {
				vo.setOrgId(rs.getInt("OrgId"));
				vo.setOrgName(rs.getString("OrgName"));
				vo.setAdmin(rs.getBoolean("isAdmin"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			free();
			jsonobject.clear();
		}
		return vo;
	}

	@Override
	public boolean selectUserName(String username) {
		LinkToMySQL();
		boolean boo = false;
		String SqlStr = "";
		try {
			SqlStr = "select * from Org where OrgName='"+username+"'";
			ReSetResult(SqlStr);
			if(rs.next()) {
				boo = true;
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			free();
		}
		return boo;
	}

	@Override
	public boolean selectUserEmail(String email) {
		LinkToMySQL();
		boolean boo = false;
		String SqlStr = "";
		try {
			SqlStr = "select * from Org where Email='"+email+"'";
			ReSetResult(SqlStr);
			if(rs.next()) {
				boo = true;
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			free();
		}
		return boo;
	}

}
