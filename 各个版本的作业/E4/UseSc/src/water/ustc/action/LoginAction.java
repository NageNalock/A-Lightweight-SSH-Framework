package water.ustc.action;

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
//        DB db = new DB();
//        //去数据库中查找匹配
//        User user = db.checkUser(name,pwd);
        //	Login中根据传入的name与pwd进行判断,跳转,判断成功则返回success,失败则返回failure
        if (name.equals("tom") && pwd.equals("123")) {
            System.out.println("*****************************************");
            System.out.println("LoginAciton结果是success");
            System.out.println("******************************************");
            return "success";
        } else {
            System.out.println("*****************************************");
            System.out.println("LoginAciton结果是failure");
            System.out.println("******************************************");
            return "failure";
        }
    }
}