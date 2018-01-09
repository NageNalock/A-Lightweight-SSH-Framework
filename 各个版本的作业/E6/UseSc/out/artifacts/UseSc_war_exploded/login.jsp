<%--
  Created by IntelliJ IDEA.
  User: TaoYun
  Date: 6/12/17
  Time: 上午11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="wrong.jsp" %>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
<div align="center">
    <form action="loginAction.sc" method="post">
        <table>
            <caption>用户登录</caption>
            <tr><td>登录名：</td>
                <td><input type="text" name="username" size="20"/></td>
            </tr><tr><td>密码：</td>
            <td><input type="password" name="password" size = "21"/></td></tr>
        </table>
        <input type="submit" value="登录"/>
        <input type="reset" value="重置"/>

    </form>
    如果您还未注册请点击<a href="register.jsp">此处</a> 注册！
</div>
</body>
</html>
