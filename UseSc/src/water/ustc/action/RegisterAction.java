package water.ustc.action;

import water.ustc.bean.UserBean;

public class RegisterAction {

    public String handleRegister(String userName, String pwd) {

        // 向数据库中插入一条信息
//        DB db = new DB();
//        db.insertUserInfo(userName,pwd);
//        System.out.println("用户名"+userName+"密码"+pwd);
        String id = "112"; // ID写死
        UserBean userBean = new UserBean(id, userName, pwd);
        userBean.setUrl("jdbc:mysql://127.0.0.1:3306/USTC");
        userBean.setDriver("com.mysql.jdbc.Driver");
        userBean.setDb_userName("USTC");
        userBean.setDb_userPass("123");
        if (userBean.signUp())
        {
            return "success";
        }
        else {
            return "handleRegister()出错";
        }
    }
}
