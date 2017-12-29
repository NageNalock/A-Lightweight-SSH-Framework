package com.demo.entity;

public class User {
    private int id;
    private String account;
    private String pwd;
    private String name;
    private String dept;
    private String role;
    private String phone;
    private String qq;
    private String mark;
    private String email;



    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getMark() {
        return mark;
    }
    public void setMark(String mark) {
        this.mark = mark;
    }
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(int id, String account, String pwd, String name, String dept,
                String role, String phone, String qq, String mark, String email) {
        super();
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.dept = dept;
        this.role = role;
        this.phone = phone;
        this.qq = qq;
        this.mark = mark;
        this.email = email;
    }
    public User(String account, String pwd, String name,int id) {
        super();
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.id = id;
    }
}