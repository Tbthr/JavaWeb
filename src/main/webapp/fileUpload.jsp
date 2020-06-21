<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form action="/fileUpload" enctype="multipart/form-data" method="POST">
    上传文件：<input type="file" name="file"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
