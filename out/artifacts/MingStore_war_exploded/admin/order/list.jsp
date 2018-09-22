<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
	<meta http-equiv="Content-Language" content="zh-cn">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
	<!-- 弹出层插件 -->
	<link href="${pageContext.request.contextPath}/css/popup_layer.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/popup_layer.js"></script>
	<!-- 调用插件弹出层的方法 -->
	<script type="text/javascript">
        $(function(){
            //弹出层插件调用
            new PopupLayer({
                trigger:".clickedElement",
                popupBlk:"#showDiv",
                closeBtn:"#closeBtn",
                useOverlay:true
            });

            //  点击按钮查看订单详情


        });
        function findOrderInfoByOid(oid) {
            $.post(
                "${pageContext.request.contextPath}/admin/orders?action=showOrderitem",
                {"oid":oid},
                function (data) {
                    $("#shodDivOid").html(oid);
                    var html = "<tr id='showTableTitle'> <th width='20%'>图片</th> <th width='25%'>商品</th> <th width='20%'>价格</th> <th width='15%'>数量</th> <th width='20%'>小计</th> </tr>";
                    for(var i = 0 ;i < data.length;i++){
                        html+="<tr style='text-align: center;' >";
                        html+="<td> <img src='${pageContext.request.contextPath }/"+data[i].product.pimage+" ' width='70px' height='60px'/> </td>";
                        html+="<td><a target='_blank'>"+data[i].product.pname+"</a></td>";
                        html+="<td>￥"+data[i].product.shop_price+"</td>";
                        html+="<td>"+data[i].count+"</td>";
                        html+="<td><span class='subtotal'>￥"+data[i].subtotal+"</span></td>";
                        html+="</tr>";
                    }
                    $("#tbody_item").html(html);
                    $("#loading").css("display","none");
                },
                "json"
            )
        }
        function closeBtn() {
            $("#tbody_item").html("");
            $("#loading").css("display","block");
        }
	</script>

</HEAD>
<body>

<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
	<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
		<TBODY>
		<tr>
			<td class="ta_01" align="center" bgColor="#afd1f3">
				<strong>订单列表</strong>
			</TD>
		</tr>

		<tr>
			<td class="ta_01" align="center" bgColor="#f5fafe">
				<table cellspacing="0" cellpadding="1" rules="all"
					   bordercolor="gray" border="1" id="DataGrid1"
					   style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
					<tr style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

						<td align="center" width="10%">
							序号
						</td>
						<td align="center" width="10%">
							订单编号
						</td>
						<td align="center" width="10%">
							订单金额
						</td>
						<td align="center" width="10%">
							收货人
						</td>
						<td align="center" width="10%">
							订单状态
						</td>
						<td align="center" width="50%">
							订单详情
						</td>
					</tr>

					<c:forEach items="${ordersList}" var="order" varStatus="i">
						<tr onmouseover="this.style.backgroundColor = 'white'"
							onmouseout="this.style.backgroundColor = '#F5FAFE';">
							<td style="CURSOR: hand; HEIGHT: 22px" align="center"
								width="18%">
									${i.count}
							</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center"
								width="17%">
									${order.oid}
							</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center"
								width="17%">
									${order.total}
							</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center"
								width="17%">
									${order.name}
							</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center"
								width="17%">
									${order.state == 1 ? "已付款":"未付款"}
							</td>
							<td align="center" style="HEIGHT: 22px">
								<input type="button" value="订单详情" class="clickedElement" onclick="findOrderInfoByOid('${order.oid}')"/>
							</td>

						</tr>
					</c:forEach>

				</table>
			</td>
		</tr>

		</TBODY>
	</table>
</form>

<!-- 弹出层 HaoHao added -->
<div id="showDiv" class="blk" style="display:none;">
	<div class="main">
		<h2>订单编号：<span id="shodDivOid" style="font-size: 13px;color: #999"></span></h2>
		<a href="javascript:void(0);" id="closeBtn" class="closeBtn" onclick="closeBtn()">关闭</a>
		<div id="loading" style="padding:0px;text-align: center;display: block">
			<img  height="225px"  alt="" src="${pageContext.request.contextPath }/images/loading.gif">
		</div>
		<div style="padding:20px;">
			<table id="showDivTab" style="width:100%">
				<thead>

				</thead>
				<!--动态显示item项 -->
				<tbody id = "tbody_item">
				</tbody>

			</table>
		</div>
	</div>

</div>


</body>
</HTML>

