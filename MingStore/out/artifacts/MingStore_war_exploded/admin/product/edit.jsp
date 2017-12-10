<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>

	</HEAD>
	
	<body>
		<!--修改表单 -->
		<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/admin/product?action=edit" method="post" enctype="multipart/form-data">
			
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">

				<input type="hidden" name = "pid" value="${pageContext.request.getParameter("pid")}">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>编辑商品</STRONG>
						</strong>
					</td>
				</tr>

				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品名称：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="pname" value="${pageContext.request.getParameter("pname")}" id="pname" class="bg"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						是否热门：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						
						<select name="is_hot">
							<option value="1"
									<c:if test="${pageContext.request.getParameter('is_hot') ==1 }">selected="selected"</c:if> >是 </option>
							<option value="0" <c:if test="${pageContext.request.getParameter('is_hot') ==0 }">selected="selected"</c:if>  >否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						市场价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="number" name="market_price" value="${pageContext.request.getParameter("market_price")}" id="market_price" class="bg"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商城价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="number" name="shop_price" value="${pageContext.request.getParameter("shop_price")}" id="shop_price" class="bg"/>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品图片：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input type="file" name="pimage" value="${pageContext.request.getParameter("pimage")}" />
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						所属分类：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<select name="cid" id = "cid">
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品描述：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<textarea   name="pdesc" rows="5" cols="30" >${pageContext.request.getParameter("pdesc")}</textarea>
					</td>
				</tr>
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok">
							&#30830;&#23450;
						</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
		</form>
	</body>

<script>
	$(function () {
        //异步显示分类
        $(function () {
            $.post(
                "${pageContext.request.contextPath}/category?action=showCategory",
                function (data) {
                    var html = "";
                    for(var i = 0;i<data.length;i++){
                        html+="<option value='"+data[i].cid+"'> "+ data[i].cname+"</option>";
                    }
                    $("#cid").html(html);
                },
                "json"
            );
        });
    });
</script>


</HTML>