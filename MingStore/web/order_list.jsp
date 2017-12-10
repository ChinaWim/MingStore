<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>会员登录</title>
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
	</style>
</head>

<body>

<!-- 引入header.jsp -->
<jsp:include page="/header.jsp"></jsp:include>
<c:if test="${!empty OrdersBean.data}">
	<div class="container">
		<div class="row">

			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>我的订单</strong>
				<table class="table table-bordered">

					<c:forEach   var="Orders" items="${OrdersBean.data}" varStatus="i" >
						<tbody>
						<tr class="success">
							<th colspan="5">订单编号:${Orders.oid}&nbsp;&nbsp;
								下定时间:${Orders.ordertime}&nbsp;&nbsp;
								订单总额:${Orders.total}元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<c:choose>
									<c:when test="${Orders.state == 1}">
										<font color="green">已付款</font>
									</c:when>
									<c:otherwise>
										<font color="red">未付款</font>&nbsp;&nbsp;&nbsp;&nbsp;
										<a href = "javascript:void(0)" onclick="reOrder('${Orders.oid}')" >[取消订单]</a>
									</c:otherwise>
								</c:choose>
							</th>
						</tr>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>

						<c:forEach items="${Orders.orderitems}" var="orderitem">
							<tr class="active">
								<td width="60" width="40%">
									<img src="${pageContext.request.contextPath}/${orderitem.product.pimage}" width="70"
										 height="60"></td>
								<td width="30%"><a target="_blank">${orderitem.product.pname} </a></td>
								<td width="20%">￥${orderitem.product.shop_price}</td>
								<td width="10%">${orderitem.count}</td>
								<td width="15%"><span class="subtotal">￥${orderitem.subtotal}</span></td>
							</tr>
						</c:forEach>

						</tbody>
					</c:forEach>

				</table>
			</div>
		</div>
		<div style="text-align: center;">
			<ul class="pagination">
				<!--上一页-->
				<c:if test="${OrdersBean.currentPage==1}">
					<li class="disabled">
						<a href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
					</li>
				</c:if>
				<c:if test="${OrdersBean.currentPage!=1}">
					<li >
						<a href="${pageContext.request.contextPath}/orders?action=myOrders&currentPage=${OrdersBean.currentPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
					</li>
				</c:if>

				<c:forEach begin="1" end="${OrdersBean.totalPage}" var="i">
					<c:if test="${i==OrdersBean.currentPage}">
						<li class="active"><a href="javascript:void(0)">${i}</a></li>
					</c:if>
					<c:if test="${i!=OrdersBean.currentPage}">
						<li ><a href="${pageContext.request.contextPath}/orders?action=myOrders&currentPage=${i}">${i}</a></li>
					</c:if>
				</c:forEach>

				<!--下一页-->
				<c:if test="${OrdersBean.currentPage==OrdersBean.totalPage}">
					<li class="disabled">
						<a href="javascript:void(0)" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
					</li>
				</c:if>
				<c:if test="${OrdersBean.currentPage!=OrdersBean.totalPage}">
					<li>
						<a href="${pageContext.request.contextPath}/orders?action=myOrders&currentPage=${OrdersBean.currentPage+1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>

</c:if>
<!--没有订单显示的页面-->
<c:if test="${empty OrdersBean.data}">
	<div style="margin: 30px 0 60px 200px;" >
		<div style="height: 300px;" >
			<img  src="images/empty.png" width="400px" style=" float: left;margin-right: 60px;" >
			<div style="font-size: 30px;color: #cccccc; padding-top:40px;"><div>您还没有订单，您可以:</div>
				<div style=" margin-top: 50px;font-size: 24px">回首页<a href = '${pageContext.request.contextPath}' style="color: red">逛逛去~~&nbsp;&nbsp;φ(>ω<*) </a></div>
			</div>
		</div>
	</div>
</c:if>
<!-- 引入footer.jsp -->
<jsp:include page="/footer.jsp"></jsp:include>

</body>
<script>
    function reOrder(oid) {
        if(confirm("确认退回订单吗？")){
            window.location.href = "${pageContext.request.contextPath}/orders?action=reOrder&oid="+oid;
        }
    }
</script>

</html>