package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection1 {
	//driver
	static String driver="com.mysql.jdbc.Driver";
	//url
	static String url = "jdbc:mysql://localhost:3306/supermarket?characterEncoding=utf-8";	
	//user
	static String user="root";
	//pwd
	static String pwd="123456";
	//获取数据库的连接对象
	public static Connection getCon(){
		//声明返回变量
		Connection con=null;
		//注册
		try {
			Class.forName(driver);
			//获取con
			con=DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//返回
		return con;
	}
	//关闭st
	public static void closeSt(Statement st){
		try {
			//判断
			if (st!=null&&!st.isClosed()) {
				st.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//关闭con
	public static void closeCon(Connection c){
		try {
			//判断
			if (c!=null&&!c.isClosed()) {
				c.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//关闭rs
	public static void closeRS(ResultSet rs){
		//判断
		try {
			if (rs!=null&&!rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
