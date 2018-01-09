package sc.ustc.dao;

import java.sql.*;

public abstract class BaseDAO {


    protected String db_userName; // 数据库的用户和密码,别与实体类搞混了
    protected String db_userPassword;
    protected String driver; // 驱动
    protected String url; // 数据库地址

    // 数据库连接对象,要全局使用
    // static Connection connection; // 数据库命令执行对象以及数据库返回结果都依赖于该对象
    /* Connection : 此接口与接触数据库的所有方法。连接对象表示通信上下文，即，与数据库中的所有的通信是通过此唯一的连接对象 */
    // static PreparedStatement pstmt;
    /* Statement(包括pstmt) : 可以使用这个接口创建的对象的SQL语句提交到数据库。一些派生的接口接受除执行存储过程的参数 */
    // static ResultSet rs;
    /* ResultSet: 这些对象保存从数据库后，执行使用Statement对象的SQL查询中检索数据。它作为一个迭代器，可以通过移动它来检索下一个数据 */

    // 打开数据库连接
    protected Connection openDBConnection()
    {
        Connection conn =null;
        System.out.println("创建数据库连接,加载驱动:");
        try {
            // 加载驱动
            Class.forName(driver);
            if(conn==null)
            {
                try {
                    // System.out.println("000000"+url+";"+userName+";"+userPassword+"000000");
                    conn = DriverManager.getConnection(url,db_userName,db_userPassword);
                } catch (SQLException e) {
                    System.out.println("    创建链接失败:"+e);
                    // e.printStackTrace();
                }
            }else
            {
                System.out.println("    BaseDAO逻辑错误:数据链接对象非空");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("    加载驱动失败:" + e);
            // e.printStackTrace();
        }
        return conn;
    }

    // 关闭数据库连接
    protected boolean closeDBConnection(Connection connection, PreparedStatement pstmt, ResultSet rs)
    {
        // conn数据库链接对象
        // rs数据返回结果,有可能为空
        // pstmt数据库命令执行对象
        System.out.println("关闭数据库连接");
        try {
            if (rs!=null)
            {
                rs.close();
                // rs = null;
            }
            if (pstmt!=null)
            {
                pstmt.close();
                // pstmt = null;
            }
            if (connection!=null)
            {
                connection.close();
                // connection = null;
            }
            System.out.println("    数据库连接关闭成功");
        }catch (SQLException e)
        {
            System.out.println("    数据库关闭失败,错误为:"+e);
            return false;
        }
        return true;
    }



    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPassword() {
        return db_userPassword;
    }

    public void setDb_userPassword(String db_userPassword) {
        this.db_userPassword = db_userPassword;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 增删改查,四个抽象方法
    public abstract Object query(String sql);
    public abstract boolean insert(String sql,String tableName);
    public abstract boolean update(String sql);
    public abstract boolean delete(String sql);
}