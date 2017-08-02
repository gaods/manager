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

     function  deleteuser(id) {
         $.ajax({
             url:"/admin/deleteuser",
             contentType:"application/json",
             type:"POST",

             data:+""+id+"",
             success:function(data){
                 var temp1= JSON.parse(data);
                 if(temp1){
                     if(temp1.status==1){
                         alert("删除成功，请刷新");
                     }else{
                         alert("删除失败，请检查");
                     }


                 }
             }

         });
     }

     function showuser(id) {
        //alert(id);
         $("#phoneusers").html("");

         $.ajax({
             url:"/admin/showuser",
             type:"POST",
             contentType:"application/json",
             data:+""+id+"",
             success:function(data) {
                 var temp1 = JSON.parse(data);
                 if (temp1) {
                     if (temp1.status == 1) {
                        var data1= temp1.data;
                         var html="";
                         for(var i in data1){
                             var temp2=data1[i];
                             if(!temp2.cuPhone)continue;
                             html+="<tr>";
                             html+="<td>"+temp2.cuPhone+"<td>";
                             html+="<td>"+temp2.cuPassword+"<td>";
                             html+="<td>"+temp2.userPin+"<td>";
                             html+="</tr>";
                         }
                         $("#phoneusers").html(html);
                     } else {

                     }
                 }
             }

         });
     }

    </script>
</head>
<body>

<div  style="margin-bottom:10px;"><button type="button" class="btn btn-primary" onclick="javascript:adduser()">新增客户</button>
    <button type="button" class="btn btn-primary" onclick="javascript:importexcel()">导入</button>
</div>
<form  class="bs-example bs-example-form" role="form" id="userform">

<table class="table table-bordered" style="width: 1000px;">

    <thead>
    <tr>
        <th>工号</th>
        <th>数量</th>
        <th>已使用数量</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="userdata">
    <#if customers?exists>
        <#list customers as user>

        <tr> <input type="hidden"  name="id" value="${user.id!""}">
            <td>${user.pnCode!""}</td>
            <td>${user.zcCount!"0"}</td>
            <td>${user.ssCount!"0"}</td>
            <td>${user.pastate!"0"}</td>

            <td>  <input class="btn btn-default" type="button" data-toggle="modal" data-target="#myModal" onclick="showuser(${user.id!""})"

                         value="查看">
                <input class="btn btn-default" type="button" onclick="saveuser(${user.id!""})" value="保存">
                <input class="btn btn-default" type="button" onclick="deleteuser(${user.id!""})" value="删除"></td>
        </tr>
        </#list>
    </#if>

    </tbody>
</table>
</form>




</body>
</html>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">客户号码列表</h4>
            </div>
            <div class="modal-body">

                <table class="table table-hover">

                    <thead>
                    <tr>
                        <th>账号</th>
                        <th>密码</th>
                        <th>绑定业务员编号</th>
                    </tr>
                    </thead>
                    <tbody id="phoneusers">

                    </tbody>
                </table>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <#--<button type="button" class="btn btn-primary">提交更改</button>-->
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>