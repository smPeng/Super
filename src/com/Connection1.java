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
	//��ȡ���ݿ�����Ӷ���
	public static Connection getCon(){
		//�������ر���
		Connection con=null;
		//ע��
		try {
			Class.forName(driver);
			//��ȡcon
			con=DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//����
		return con;
	}
	//�ر�st
	public static void closeSt(Statement st){
		try {
			//�ж�
			if (st!=null&&!st.isClosed()) {
				st.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�ر�con
	public static void closeCon(Connection c){
		try {
			//�ж�
			if (c!=null&&!c.isClosed()) {
				c.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�ر�rs
	public static void closeRS(ResultSet rs){
		//�ж�
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
