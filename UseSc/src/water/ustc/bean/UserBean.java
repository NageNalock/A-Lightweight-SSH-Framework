package water.ustc.bean;

import sc.ustc.dao.Conversation;
import sc.ustc.dao.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;

public class UserBean  {

    private String userName;
    private String userPass;


    public boolean signIn()
    {
        Conversation conversation = new Conversation(this.getClass().getName());
        String userPassFromDB = null; // 查询出的密码
        // 1.执行查询
        QueryResult queryResult = conversation.query(this, "userName");

        // 2.获取密码(不用考虑懒加载
        ArrayList<HashMap<String, String>> resultList = queryResult.getResult();
        for(HashMap<String,String> result:resultList)
        {
            userPassFromDB = result.get("userPass"); // 反正这个情况只会有一个值
            System.out.println("数据库中读出的密码为:"+userPassFromDB);
        }

        // 3.匹配密码
        return userPass.equals(userPassFromDB);
    }

    public boolean signUp()
    {
        // 1. Conversation
        Conversation conversation = new Conversation(this.getClass().getName());
//        // 2. 调用insert()
//        boolean insertResult = conversation.insert(this);
        // 3. 得到结果()
        return conversation.insert(this);
    }


    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}