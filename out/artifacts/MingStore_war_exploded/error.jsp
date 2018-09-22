<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2017/8/27
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isErrorPage="true" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
        <h1>异常原因</h1>
        ${pageContext.exception.message}
</body>
</html>
