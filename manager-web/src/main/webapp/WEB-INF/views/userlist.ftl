<html>
<head>
    <title>后台管理系统</title>

    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/bootstrap-responsive.css">

    <!-- JS -->
    <script src="/js/admin/jquery.min.js"></script> <!-- jQuery -->
    <script src="/js/admin/bootstrap.js"></script> <!-- Bootstrap -->
    <script src="/js/admin/jquery.easyui.min.js"></script> <!-- Bootstrap -->
    <script src="/js/jquery.form.js"></script>
    <script>


     function adduser() {
         window.location.href="adduser";
     }

    </script>
</head>
<body>

<div  style="margin-bottom:10px;"><button type="button" class="btn btn-primary" onclick="javascript:adduser()">新增</button></div>
<form  class="bs-example bs-example-form" role="form" id="userform">

<table class="table table-bordered" style="width: 800px;">

    <thead>
    <tr>
        <th>工号</th>
        <th>数量</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="userdata">

    </tbody>
</table>
</form>
<div><button type="button" class="btn btn-primary" onclick="javascript:saveuser()">保存</button></div>

</body>
</html>