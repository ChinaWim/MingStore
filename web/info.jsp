<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>小明商城信息展示</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
<div class="container-fluid">

    <!-- 引入header.jsp -->
    <jsp:include page="/header.jsp"></jsp:include>

    <div class="container-fluid">
        <div class="main_con">
            <h2>项目简介</h2>
            <hr/>
            <div>
                这是我学习框架前的第一个Web项目，纯jsp+servlet 搭建。<br>
                实现了响应式前台页面，邮箱验证登录， 浏览商品，查看历史记录，购物车详情，订单查看,在线支付,后台商城管理等主要功能。<br>
                开发者：<a href="https://github.com/MingZzzz">潘伟明</a>
            </div>
        </div>
    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>