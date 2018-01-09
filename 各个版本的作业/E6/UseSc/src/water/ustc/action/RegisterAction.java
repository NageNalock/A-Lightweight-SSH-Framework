package water.ustc.action;

import water.ustc.bean.UserBean;

public class RegisterAction {

    public String handleRegister(String userName, String pwd) {

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
