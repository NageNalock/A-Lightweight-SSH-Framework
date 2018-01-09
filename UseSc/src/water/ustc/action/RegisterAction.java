package water.ustc.action;

import water.ustc.bean.UserBean;

public class RegisterAction {

    // private UserBean user;


    public String handleRegister(String name,String pwd) {

        UserBean user = new UserBean();
        user.setUserName(name);
        user.setUserPass(pwd);

        if (user.signUp())
        {
            return "success";
        }else
        {
            return "handleRegister()出错";
        }
    }

//    public UserBean getUser() {
//        return user;
//    }
//
//    public void setUser(UserBean user) {
//        this.user = user;
//    }
}
