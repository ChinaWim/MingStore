<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-lg-4 col-md-4 col-sm-6">
		<img src="img/shop.png" />
	</div>
	<div class="col-lg-4 col-md-4 hidden-xs col-sm-6" >
		<img src="img/header.png" />
	</div>
	<div class="col-lg-4 col-md-4 col-sm-12" style="padding-top:20px">
		<ol class="list-inline">
			<c:choose>
				<c:when test="${!empty user}">
					<li><a href = "javascript:void(0)" >[${user.name}]</a></li>
					<li><a href = "${pageContext.request.contextPath}/user?action=logout">退出登录</a></li>
					<li><a href = "${pageContext.request.contextPath}/cart.jsp" >购物车</a></li>
					<li><a onclick="myOrder()" href = "javascript:void (0)">我的订单</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="login.jsp">登录</a></li>
					<li><a href="${pageContext.request.contextPath}/register.jsp">注册</a></li>
					<li><a href = "${pageContext.request.contextPath}/cart.jsp" >购物车</a></li>
					<li><a href = "javascript:void (0)" onclick="myOrder()">我的订单</a></li>
				</c:otherwise>
			</c:choose>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id = "categoryList">
					<%--<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">所有分类 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li>
								<a href="#">手机数码</a>
							</li>
						</ul>
					</li>--%>
				</ul>
				<form class="navbar-form navbar-right" role="search" method="post"
					  action="${pageContext.request.contextPath}/product?action=submitSearch">
					<div class="form-group">
						<input type="text" name = "pname"   id = "search" class="form-control" placeholder="Search" onkeyup="searchShop(this)">
					</div>
					<button type="submit" class="btn btn-default" >搜索</button>
					<div  id ="showDiv" style="position:absolute;z-index: 1000; display: none;" >
						<select   size="12" id = "showId"  multiple="multiple" style="width: 200px; cursor:pointer" >
						</select>
					</div>
				</form>
			</div>
		</div>
	</nav>
</div>
<script>
    //异步显示分类
    $(function () {
        $.post(
            "${pageContext.request.contextPath}/category?action=showCategory",
            function (data) {
                var html = "";
                for(var i = 0;i<data.length;i++){
                    data[i].cname
                    html+= "<li><a href = '${pageContext.request.contextPath}/product?action=productList&cid="+data[i].cid+" '>"+data[i].cname+"</a></li>";
                }
                $("#categoryList").html(html);
            },
            "json"
        );
    });

    //异步搜索商品 把搜索到商品显示到下拉框
    function searchShop(data) {
        var dataValue = data.value.trim();
        if (dataValue == ""){
            $("#showDiv").css("display","none");
        }else {
            $.ajax({
                async:true,
                url:"${pageContext.request.contextPath}/product?action=search",
                data: {"searchValue":$(data).val()},
                type:"GET",
                success:function (datas) {
                    if(datas.length > 0){
                        var str = "";
                        $.each( datas, function(i, n){
                            str += "<option style=' font-size: 11px;margin-top: 10px;' value='"+n+"' onclick='clickFn(this)' onmouseout='outFn(this)' onmouseover='overFn(this)'> "+n+" </option>";
                        });
                        $("#showId").html(str);
                        $("#showDiv").css("display","block");
                    }
                },
                dataType:"json"
            });
        }
    }
    function overFn(data) {
        $(data).css("background-color","#DBEAF9");
    }
    function outFn(data) {
        $(data).css("background-color","#fff");
    }
    function clickFn(data) {
        $("#search").val($(data).val());
        $("#showDiv").css("display","none");
    }
    function myOrder() {
		<c:if test="${empty user}">
		alert("请先登录！");
		</c:if>
        <c:if test="${!empty user}">
       	location.href="${pageContext.request.contextPath}/orders?action=myOrders";
        </c:if>
    }

</script>


