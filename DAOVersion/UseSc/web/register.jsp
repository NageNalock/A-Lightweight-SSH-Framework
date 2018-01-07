<%--
  Created by IntelliJ IDEA.
  User: TaoYun
  Date: 6/12/17
  Time: 上午11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
</head>
<body>
<div align="center">
    <form action="registerAction.sc" method="post">
        <table>
            <caption>用户注册</caption>
            <tr><td>登录名：</td>
                <td><input type="text" name="username"/></td>
            </tr><tr><td>密码：</td>
            <td><input type="password" name="password"/></td></tr>
        </table>
        <input type="submit" value="注册">
        <input type="reset" value="重置">
    </form>
</div>
</body>
</html>
