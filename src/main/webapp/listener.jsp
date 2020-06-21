<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>统计在线人数</title>
</head>
<body>
<h1>当前在线人数为：<%= request.getSession().getServletContext().getAttribute("OnlineCount") %></h1>
</body>
</html>
