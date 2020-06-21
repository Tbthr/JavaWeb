<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
<h2 style="text-align: center">Hello World!</h2>

<%--这里提交的路径，需要寻找到项目的路径--%>
<%--${pageContext.request.contextPath}代表当前的项目--%>
<form action="/login" method="post" style="text-align: center">
    用户名：<input type="text" name="username" required> <br>
    密码：<input type="password" name="password" required> <br>
    爱好：<br>
    女孩：<input type="checkbox" name="hobbies" value="girl"><br>
    编程：<input type="checkbox" name="hobbies" value="programing"><br>
    唱歌：<input type="checkbox" name="hobbies" value="singing"><br>
    打篮球：<input type="checkbox" name="hobbies" value="play basketball"><br>
    <input type="submit">
</form>
</body>
</html>
