<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>小明商城购物车</title>
	<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
	<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 引入自定义css文件 style.css -->
	<link rel="stylesheet" href="css/style.css" type="text/css" />
	<style>
		body {
			margin-top: 20px;
			margin: 0 auto;
		}

		.carousel-inner .item img {
			width: 100%;
			height: 300px;
		}

		font {
			color: #3164af;
			font-size: 18px;
			font-weight: normal;
			padding: 0 10px;
		}
	</style>
</head>

<body>
<!-- 引入header.jsp -->
<jsp:include page="/header.jsp"></jsp:include>
<!--判断购物车是否有商品-->
<c:if test="${ (!empty reverseCartMap)}">

	<div class="container">
		<div class="row">
			<div style="margin:0 auto; margin-top:10px;width:950px;">
				<strong style="font-size:16px;margin:5px 0;">购物车详情</strong>
				<table class="table table-bordered">
					<tbody>
					<tr class="warning">
						<th>图片</th>
						<th>商品</th>
						<th>价格</th>
						<th>数量</th>
						<th>小计</th>
						<th>操作</th>
					</tr>
					<c:forEach var="cartItem" items="${reverseCartMap}" >
						<tr class="active">
							<td width="60" width="40%">
								<input type="hidden" name="id" value="22">
								<a href="${pageContext.request.contextPath}/product?action=productInfo&pid=${cartItem.value.product.pid}&currentPage=1">
									<img src="${pageContext.request.contextPath}/${cartItem.value.product.pimage}" width="70" height="60">
								</a>
							</td>
							<td width="30%">
								<a href="${pageContext.request.contextPath}/product?action=productInfo&pid=${cartItem.value.product.pid}&currentPage=1">
										${cartItem.value.product.pname}
								</a>
							</td>
							<td width="20%">
								￥${cartItem.value.product.shop_price}
							</td>
							<td width="10%">
								<input type="text"  name="quantity" value="${cartItem.value.quantity}" disabled="disabled" maxlength="4" size="10">
							</td>
							<td width="15%">
								<span class="subtotal">￥${cartItem.value.subtotal}</span>
							</td>
							<td>
								<a href="javascript:void(0)" onclick="deleteItem('${cartItem.value.product.pid}')" class="delete">删除</a>
							</td>
						</tr>
					</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
		<div style="margin-right:130px;">
			<div style="text-align:right;">
				<em style="color:#ff6600;">
					登录后确认是否享有优惠&nbsp;&nbsp;
				</em> 赠送积分: <em style="color:#ff6600;">596</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${totalPrice}元</strong>
			</div>
			<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
				<a href="javascript:void(0)" onclick="deleteCart()" id="clear" class="clear">清空购物车</a>
				<a href="javascript:void(0)" onclick=" return confirmOrders()">
					<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
				</a>
			</div>
		</div>
	</div>
</c:if>

<!--购物车为空-->
<c:if test="${ empty reverseCartMap}" >
	<div style="margin: 30px 0 60px 200px;" >
		<div style="height: 300px;" >
			<img  src="images/empty.png" width="400px" style=" float: left;margin-right: 60px;" >
			<div style="font-size: 40px;color: #cccccc; padding-top:40px;">您的购物车还是空的，您可以：<br>
					<button onclick="location.href='${pageContext.request.contextPath}/login.jsp' " type="button" class="btn btn-default navbar-btn" style="width: 100px;height: 60px; color: red;margin: 70px 100px 0px 100px">立即登录</button>
					<button  onclick="location.href='${pageContext.request.contextPath}' " type="button" class="btn btn-default navbar-btn" style="width: 100px;height: 60px; color: red;margin: 70px 0px 0px 0px;">马上购物</button>
			</div>
		</div>
	</div>
</c:if>

<!-- 引入footer.jsp -->
<jsp:include page="/footer.jsp"></jsp:include>

</body>
<script>
    function deleteItem(data){
        if(confirm("您是否要删除该项？")){
            location.href= "${pageContext.request.contextPath}/product?action=delCartItem&pid="+data;
        }
    }
    function deleteCart() {
        if(confirm("您是否要清空购物车？")){
            location.href= "${pageContext.request.contextPath}/product?action=delCart";
        }
    }
    function confirmOrders() {
        <c:if test="${ empty user}">
		alert("请先登录！");
		return false;
		</c:if>
        if(confirm("您是否要提交订单？")){
            location.href= "${pageContext.request.contextPath}/orders?action=submitOrder";
        }
    }

</script>

</html>