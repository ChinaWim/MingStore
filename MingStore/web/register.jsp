<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- JQuery插件-->
<script src="js/jquery.validate.min.js"></script>
<script src="js/messages_zh.js"></script>
<script src="js/gVerify.js"></script>

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
	.error{
		color: red;
	}
</style>
<script>
    //自定义验证规则 判断用户名是否重复
    $.validator.addMethod(
        "checkUsername",
        function (data,element,prams) { // data 就是input 的value element就是input的对象 prams 就是下面的参数(true..)
            var isExist = false;
            $.ajax({
                async:false,
                data:{"username":data},
                url:"${pageContext.request.contextPath}/user?action=checkUsername",
                dataType:"json",
                type:"post",
                success:function (data) {
                    isExist = data.isExist;
                }
            });
            //当 不存在时候返回true
            return !isExist;
        }
    );

    $(function () {
        $("#registerForm").validate({
            rules:{
                "username":{
                    required:true,
                    "checkUsername":true
                },
                "password":{
                    required:true,
                    rangelength:[6,12]
                },
                "repassword":{
                    equalTo:"#password"
                },
                "name":{
                    required:true
                },
                "sex": {
                    required: true
                },
                "email":{
                    required:true,
                    email:true
                }
            },
            messages:{
                "username":{
                    required:"姓名不能为空",
                    "checkUsername":"用户名已存在"
                },
                "password":{
                    required:"密码不能为空",
                    rangelength:"密码长度必须在6-12位"
                },
                "repassword":{
                    equalTo:"两次密码不一样"
                },
                "name":{
                    required:"姓名不能为空"
                },
                "email":{
                    required:"邮箱不能为空",
                    email:"邮箱格式不对"
                },
                "sex": {
                    required: "性别不能为空"
                }
            }
        });
    });
</script>

</head>
<body>

<!-- 引入header.jsp -->
<jsp:include page="/header.jsp"></jsp:include>

<div class="container"
	 style="width: 100%; background: url('image/regist_bg.jpg');">
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8"
			 style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
			<font>会员注册</font>USER REGISTER
			<form id = "registerForm" class="form-horizontal" style="margin-top: 5px;" action="${pageContext.request.contextPath}/user?action=register" method="post">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="username" name = "username"
							   placeholder="请输入用户名">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="password" name = "password"
							   placeholder="请输入密码">
					</div>
				</div>
				<div class="form-group">
					<label for="repassword" class="col-sm-2 control-label">确认密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="repassword" name="repassword"
							   placeholder="请输入确认密码">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="name" name = "name"
							   placeholder="请输入姓名">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">Email</label>
					<div class="col-sm-6">
						<input type="email" class="form-control" id="email" name = "email"
							   placeholder="Email">
					</div>
				</div>
				<div class="form-group">
					<label for="telephone" class="col-sm-2 control-label">电话号码</label>
					<div class="col-sm-6">
						<input type="number" class="form-control" id="telephone" name = "telephone"
							   placeholder="电话号码">
					</div>
				</div>
				<div class="form-group opt">
					<label for="sex1" class="col-sm-2 control-label">性别</label>
					<div class="col-sm-6">
						<label class="radio-inline"> <input type="radio" name="sex" id="sex1" value="male">男</label>
						<label class="radio-inline"> <input type="radio" name="sex" id="sex2" value="female">女</label>
						<label class="error" for="sex" style="margin-left:40px;  display: none"></label>
					</div>
				</div>
				<div class="form-group">
					<label for="birthday" class="col-sm-2 control-label">出生日期</label>
					<div class="col-sm-6">
						<input type="date" class="form-control" name ="birthday" id = "birthday">
					</div>
				</div>
				<div class="form-group">
					<label for="checkCode" class="col-sm-2 control-label">验证码</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id = "checkCode" name = "checkCode">
					</div>
					<div class="col-sm-2">
						<div id = "code"></div>
					</div>
					<label  id = "errorCode" style="color: red"></label>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" width="100" value="注册" onclick="return validateCode()" name="submit" style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
					</div>
				</div>
			</form>
		</div>

		<div class="col-md-2"></div>

	</div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="/footer.jsp"></jsp:include>
<!-- 验证码 -->
<script>
    var codeItem = new GVerify("code");
    function validateCode() {
        var res = codeItem.validate($("#checkCode").val());
		 if(!res){
             $("#errorCode").html("验证码错误");
         }else {
             $("#errorCode").html("");
		 }
        codeItem.refresh();
		return res;
    }
</script>
</body>
</html>




