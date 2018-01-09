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
        //	Login中根据传入的name与pwd进行判断,跳转,判断成功则返回success,失败则返回failure
//        if (name.equals("tom") && pwd.equals("123")) {
//            System.out.println("*****************************************");
//            System.out.println("LoginAciton结果是success");
//            System.out.println("******************************************");
//            return "success";
//        } else {
//            System.out.println("*****************************************");
//            System.out.println("LoginAciton结果是failure");
//            System.out.println("******************************************");
//            return "failure";
//        }
        String id = "113"; // ID写死
        UserBean userBean = new UserBean(id, name, pwd);
        userBean.setUrl("jdbc:mysql://127.0.0.1:3306/USTC");
        userBean.setDriver("com.mysql.jdbc.Driver");
        userBean.setDb_userName("USTC");
        userBean.setDb_userPass("123");
        if(userBean.signIn())
        {
            return "success";
        }else {
            return "failure";
        }

    }
}