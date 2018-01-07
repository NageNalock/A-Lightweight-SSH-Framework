package water.ustc.action;

import water.ustc.bean.UserBean;

public class RegisterAction {

    public String handleRegister(String userName, String pwd) {

        // 向数据库中插入一条信息
//        DB db = new DB();
//        db.insertUserInfo(userName,pwd);
//        System.out.println("用户名"+userName+"密码"+pwd);
//        return "success";
        UserBean userBean = new UserBean(userName, pwd);
        if (userBean.signUp())
        {
            return "success";
        }else
        {
            return "handleRegister()出错";
        }
    }
}
