package water.ustc.dao;

import sc.ustc.dao.BaseDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDAO extends BaseDAO {

    /**
     * connection pstmt rs
     * **/
    public UserDAO(String dbun, String dbpwd, String url, String driver)
    {
        // 在构造方法中初始化父类域(利用Setter方法)
        // this.setUserName(un);
        // this.setUserPassword(pwd);
        this.setDb_userName(dbun);
        this.setDb_userPassword(dbpwd);
        this.setUrl(url);
        this.setDriver(driver);
    }

    // 用户的账号和密码
    private String userName;
    private String userPassword;

    // 重写query
    @Override
    public Object query(String sql) {
        // "SELECT *   FROM USER   WHERE  sname=? and pwd=?"
        System.out.println("对语句'"+sql+"'执行query():");
        // 打开连接
        Connection connection = this.openDBConnection();
        // 返回结果
        ArrayList<HashMap<String,String>> resultHashMapList = new ArrayList<HashMap<String,String>>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // 预编译
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,userPassword);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("        返回的resultSet结果为:" + resultSet.toString());

            while (resultSet.next())
            {
                // 获取表单中所有结果
                HashMap<String, String> queryResult = new HashMap<>();

                String result1 = resultSet.getString("USERNAME");
                String result2 = resultSet.getString("USERPASS");
                String result3 = resultSet.getString("USERID");
                System.out.println("        返回结果中的用户名:"+result1+"密码为:"+result2+"ID为:"+result3);
                queryResult.put("username",result1);
                queryResult.put("userpass",result2);
                queryResult.put("userID",result3);

                resultHashMapList.add(queryResult);
            }

            // 关闭连接
            boolean closeResult = this.closeDBConnection(connection, preparedStatement, resultSet);
            if (closeResult) System.out.println("   query()关闭成功");
        } catch (SQLException e) {
            System.out.println("    query()方法出错:" + e);
        }

        return resultHashMapList;
    }

    @Override
    public boolean delete(String sql) {
        return false;
    }

    @Override
    public boolean insert(String sql,String tableName) {
        // 插入,在验证(登陆)时使用
        System.out.println("对语句'"+sql+"'执行insert():");

        // 判断表格是否存在,不存在则建表,存在则直接插入
        if (isTableExist(tableName))
        {
            System.out.println(tableName+"表格存在");
        }else
        {
            System.out.println(tableName+"表格不存在,创建表格");
            // 写死了,回来再改
            createTable();
        }
        Connection connection = this.openDBConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,userPassword);
            int rowNum = preparedStatement.executeUpdate();
            System.out.println("        插入成功,插入行数为:"+rowNum);

            this.closeDBConnection(connection,preparedStatement,null);
            return true;
        } catch (SQLException e) {
            System.out.println("        insert()方法出错:"+e);
            // e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(String sql) {
        return false;
    }

    private boolean isTableExist(String tableName)
    {
        // 判断表是否存在
        System.out.println("开始执行表格监测方法");
        boolean flag = false;
        Connection connection = this.openDBConnection();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String type [] = {"TABLE"};
            ResultSet rs = metaData.getTables(null, null, tableName, type);
            flag = rs.next();

            this.closeDBConnection(connection,null,rs);
        } catch (SQLException e) {
            System.out.println("    *表格检测方法失败:"+e);
            // e.printStackTrace();
        }

        System.out.println("表格监测方法执行结束:"+flag);
        return flag;
    }

    private void createTable()
    {
        System.out.println("开始创建表格");
        Connection connection = this.openDBConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE USER " +
                    "(USERID VARCHAR(255) not NULL, " +
                    " USERNAME VARCHAR(255), " +
                    " USERPASS VARCHAR(255), " +
                    " PRIMARY KEY ( USERID ))";
            statement.executeUpdate(sql);
            statement.close();
            this.closeDBConnection(connection,null,null);
        } catch (SQLException e) {
            System.out.println("创建表格失败"+e);
        }

    }

    public  String getUserName() {
        return userName;
    }public void   setUserName(String userName) {
        this.userName = userName;
    }public String getUserPassword() {
        return userPassword;
    }public void   setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}