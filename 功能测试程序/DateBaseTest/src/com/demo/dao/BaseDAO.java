package com.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseDAO {
	protected static String url = null;// "jdbc:mysql://localhost:3306/mydata";
	protected static String user = null;// "root";
	protected static String password = null;// "root";
	protected static String driver = null;// ����������"com.mysql.jdbc.Driver";

	public static String getDriver() {
		return driver;
	}

	public static void setDriver(String driver) {
		BaseDAO.driver = driver;
	}

	public BaseDAO(String url, String user, String password, String driver) {
		this.setPassword(password);
		this.setUrl(url);
		this.setUser(user);
		this.setDriver(driver);
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		BaseDAO.url = url;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		BaseDAO.user = user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		BaseDAO.password = password;
	}

	static Connection conn = null;

	public Connection openDBConnection() {// Connection, ��������ݿ�����

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("�������ݿ����" + e);
		}

		return conn;

	}

	public boolean closeDBConnection(PreparedStatement stmt, Connection conn) {// boolean,
																				// ����ر����ݿ�����

		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);

			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		return true;
	}

	public abstract Object query(String sql);// , ����ִ�� sql ��䣬�����ؽ������

	public abstract boolean insert(String sql);// ����ִ�� sql ��䣬������ִ�н��

	public abstract boolean update(String sql);// boolean, ����ִ�� sql ��䣬������ִ�н��

	public abstract boolean delete(String sql);// : boolean, ����ִ�� sql ��䣬������ִ�н��
}
