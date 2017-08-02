<!DOCTYPE html>
<html>

<head>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<!-- 避免IE使用兼容模式 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<!-- 启用360浏览器的极速模式(webkit) -->
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="keywords" content="">
	<meta http-equiv="Cache-Control" CONTENT="no-cache">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title></title>

    <link href="${cdn}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${cdn}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${cdn}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${cdn}/css/animate.css" rel="stylesheet">
    <link href="${cdn}/css/style.css" rel="stylesheet">

 <style type="text/css">
html,body {
	height: 100%;
}
.box {
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#6699FF', endColorstr='#6699FF'); /*  IE */
	background-image:linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-o-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-moz-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-webkit-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-ms-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	
	margin: 0 auto;
	position: relative;
	width: 100%;
	height: 100%;
}
.login-box {
	width: 100%;
	max-width:500px;
	height: 400px;
	position: absolute;
	top: 50%;

	margin-top: -200px;
	/*设置负值，为要定位子盒子的一半高度*/
	
}
@media screen and (min-width:500px){
	.login-box {
		left: 50%;
		/*设置负值，为要定位子盒子的一半宽度*/
		margin-left: -250px;
	}
}	

.form {
	width: 100%;
	max-width:500px;
	height: 275px;
	margin: 25px auto 0px auto;
	padding-top: 25px;
}	
.login-content {
	height: 300px;
	width: 100%;
	max-width:500px;
	background-color: rgba(255, 250, 2550, .6);
	float: left;
}		
	
	
.input-group {
	margin: 0px 0px 30px 0px !important;
}
.form-control,
.input-group {
	height: 40px;
}

.form-group {
	margin-bottom: 0px !important;
}
.login-title {
	padding: 20px 10px;
	background-color: rgba(0, 0, 0, .6);
}
.login-title h1 {
	margin-top: 10px !important;
}
.login-title small {
	color: #fff;
}

.link p {
	line-height: 20px;
	margin-top: 30px;
}
.btn-sm {
	padding: 8px 24px !important;
	font-size: 16px !important;
}
</style>


</head>

<div class="box">
		<div class="login-box">
			<div class="login-title text-center">
				<h1><small>管理员登录</small></h1>
			</div>
			<div class="login-content ">
			<div class="form">
			<form id="loginform"   method="post" >
				<div class="form-group">
					<div class="col-xs-12  ">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
							<input type="text" id="username" data-bind="value:username" name="username" class="form-control" placeholder="用户名">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12  ">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
							<input type="text" id="password" data-bind="value:password" name="password" class="form-control" placeholder="密码">
						</div>
					</div>
				</div>
				<div class="form-group form-actions">
					<div class="col-xs-4 col-xs-offset-4 ">
						<button type="button"  onclick="submitForm()" id="logon" class="btn btn-sm btn-info"><span class="glyphicon glyphicon-off"></span> 登录</button>
					</div>
				</div>

                <p class="text-muted text-center pull-left m-t">
                    <small>还没有账号?</small><i class="fa fa-right"></i><a href="/admin/register">注册账号</a>
                </p>
                <div class="clearfix"></div>
			</form>
			<div id="msg"></div>

			</div>
		</div>
	</div>
</div>
    <script>
		window.ctx = {};
		window.ctx.cdn = "${cdn}";
		window.ctx.portal ="${portal}";
		


		function submitForm() {

            $('#loginform').ajaxSubmit({
                url: "/login",
                dataType: "text/json",
				type:"POST",
				data:"",
                success: function (data) {
					alert();
                },
				complete:function (data) {

					if(data){
						var resp= JSON.parse(data.responseText);

						if(resp.issuccess=="true"){
                            window.location.href="home";
						}else{
							$("#msg").html("<span>"+resp.msg+"</span>");
						}

					}


                }
            });
        }
		
	</script>
    <script src="${cdn}/js/requirejs/require.js"></script>
	<script src="/js/admin/jquery.min.js"></script>
	<script src="/js/jquery.form.js"></script>
</body>

</html>
