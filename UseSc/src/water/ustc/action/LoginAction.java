package water.ustc.action;

import water.ustc.bean.UserBean;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/14
 * \* Time: 20:19
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class LoginAction {


    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String handleLogin()
    {
        // UserBean userBean = new UserBean(name, pwd);
        if(user.signIn())
        {
            return "success";
        }else {
            return "failure";
        }
    }
}