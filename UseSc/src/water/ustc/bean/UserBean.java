package water.ustc.bean;

import water.ustc.dao.UserDAO;

import java.util.HashMap;

public class UserBean {
    private String userID; // 觉得需求有问题,改了下逻辑
    private String userName;
    private String userPass;

    private String url; // 需要在Action中单独设置
    private String driver; // 需要Action中单独设置


    public UserBean(String uID, String name, String pass)
    {
        this.userID = uID;
        this.userName = name;
        this.userPass = pass;
    }

    public boolean signIn()
    {
        UserDAO userDAO = new UserDAO(userName, userPass, url, driver);
        String sql = "SELECT *   FROM USER   WHERE  USERNAME=? and USERPASS=? and USERID="+userID;
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("执行signIn()");
        // 判断userDAO的query方法,并取出ID(原需求为PASS
        HashMap queryResult = (HashMap) userDAO.query(sql);
        if (queryResult == null)
        {
            System.out.println("    signIn()中query()的返回值为空,signIn失败");
            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
            return false;
        }else {
            // 取出userID
            String queryUserID = (String) queryResult.get("userID");
            String queryUserPass = (String) queryResult.get("userpass");

            if (userID.equals(queryUserID) && userPass.equals(queryUserPass))
            {
                System.out.println("            ID匹配成功,signIn成功");
                System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
                return true;
            }else {
                System.out.println("            ID匹配失败~页面ID为:"+userID+";query返回ID为:"+queryUserID+"signIn失败");
                System.out.println("            ID匹配失败~页面pass为:"+userPass+";query返回pass为:"+queryUserPass+"signIn失败");
                System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
                return false;
            }
        }
    }

    public boolean signUp()
    {
        // 注册
        // 具体逻辑:在UserDAO中完成插入
        // 插入的内容为name,pass,id
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("执行signUp()");
        String tableName = "USER";
        String sql="INSERT INTO "+tableName+"(USERNAME,USERPASS,USERID)  VALUES(?,?,"+userID+")";
        UserDAO userDAO = new UserDAO(userName, userPass, url, driver);
        if(userDAO.insert(sql,tableName))
        {
            System.out.println("    signUp()执行成功");
            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
            return true;
        }
        else {
            System.out.println("   signUp()执行失败");
            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
            return false;
        }
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}