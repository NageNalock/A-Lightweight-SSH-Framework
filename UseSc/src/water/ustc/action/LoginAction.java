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
    public String handleLogin(String name,String pwd) {

        UserBean userBean = new UserBean(name, pwd);
        if(userBean.signIn())
        {
            return "success";
        }else {
            return "failure";
        }
    }
}