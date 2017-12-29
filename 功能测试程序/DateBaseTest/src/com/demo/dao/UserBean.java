package com.demo.dao;

public class UserBean {
	// private String userId=null;
	private String userName = null;
	private String userPass = null;

	public UserBean(String userName, String userPass) {
		this.userName = userName;
		this.userPass = userPass;
	}

	public boolean signIn() {
		String sql = "SELECT *   FROM USER   WHERE  sname=? and pwd=?";
		UserDAO ado = new UserDAO();
		ado.setUserName(userName);
		ado.setUserPass(userPass);
		String str = (String) ado.query(sql);
		
		if (str != null) {
			return true;
		}
		return false;
	}
	public boolean register(){
		String sql="INSERT INTO USER(sname,pwd)  VALUES(?,?)";
		UserDAO ado = new UserDAO();
		ado.setUserName(userName);
		ado.setUserPass(userPass);
		if((Boolean) ado.insert(sql)){
			return true;
		}
		return false;
		
	}
}
