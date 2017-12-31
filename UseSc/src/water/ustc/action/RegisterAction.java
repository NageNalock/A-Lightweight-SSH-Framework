package water.ustc.action;

import water.ustc.bean.UserBean;

public class RegisterAction {

    public String handleRegister(String userName, String pwd) {

        String id = "113"; // ID写死
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
