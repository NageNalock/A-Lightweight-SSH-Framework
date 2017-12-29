package com.demo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends BaseDAO {
	protected static String url = "jdbc:mysql://localhost:3306/mydata";
	protected static String user = "root";
	protected static String password = "root";
	protected static String driver = "com.mysql.jdbc.Driver";
	static PreparedStatement stmt = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	private String userName = null;
	private String userPass = null;

	public UserDAO() {
		super(url, user, password, driver);

	}

	@Override
	public Object query(String sql) {
	
		this.openDBConnection();
		String re = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userName);
			stmt.setString(2, userPass);
			ResultSet rs = stmt.executeQuery();// ִ�в����ؽ��
			while (rs.next()) {

				String name = rs.getString("sname");
				String pwd1 = rs.getString("pwd");
				System.out.println("��ѯ��" + name + "," + pwd1);
				re = name;
				// System.out.println("��ѯ��"+re);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ִ�г���" + e);
		}finally{
			this.closeDBConnection(stmt, conn);
		}
		
		return re;
	}

	@Override
	public boolean insert(String sql) {
		int count=0;
		this.openDBConnection();
		try {
			stmt= conn.prepareStatement(sql);
			
			stmt.setString(1, userName);
			stmt.setString(2, userPass);

			
			count = stmt.executeUpdate();
			System.out.println("Ӱ����"+count+"��");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		System.out.println("ִ�г���"+e);	
		}finally{
			this.closeDBConnection(stmt, conn);
		}
		return true;
	}

	@Override
	public boolean update(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

}
