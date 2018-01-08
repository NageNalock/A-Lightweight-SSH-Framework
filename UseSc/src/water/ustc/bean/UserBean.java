package water.ustc.bean;

import sc.ustc.dao.Conversation;
import sc.ustc.dao.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;

public class UserBean  {

    private String userName;
    private String userPass;

    public UserBean(String userName,String userPass)
    {
        this.userName = userName;
        this.userPass = userPass;
    }

    public boolean signIn()
    {
        Conversation conversation = new Conversation(this.getClass().getName());
        String userPassFromDB = null; // 查询出的密码
        // 1.执行查询
        QueryResult queryResult = conversation.query(this, "userName");
        // 2.验证结果是否为懒加载
        if (queryResult.isLazy())
        {
            // 是懒加载
            System.out.println("signIn得到的查询结果是懒加载");
            // 2.1 执行懒加载方法获得结果
            ArrayList<HashMap<String, String>> hashMapsList = queryResult.notLazy();
            for(HashMap<String,String> hashMap:hashMapsList)
            {
                userPassFromDB = hashMap.get("userPass"); // 反正这个情况只会有一个值
                System.out.println("数据库中读出的密码为:"+userPassFromDB);
            }
        }else {
            // 不是懒加载
            System.out.println("signIn得到的查询结果不是懒加载");
            // 2.1
            ArrayList<HashMap<String, String>> resultList = queryResult.getResultList();
            for(HashMap<String,String> hashMap:resultList)
            {
                userPassFromDB = hashMap.get("userPass"); // 反正这个情况只会有一个值
                System.out.println("数据库中读出的密码为:"+userPassFromDB);
            }
        }
        // 3.匹配密码
        if (userPass.equals(userPassFromDB))
            return true;  // 相等
        else return false;
    }

    public boolean signUp()
    {
        // 1. Conversation
        Conversation conversation = new Conversation(this.getClass().getName());
        // 2. 调用insert()
        boolean insertResult = conversation.insert(this);
        // 3. 得到结果()
        return insertResult;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }
}