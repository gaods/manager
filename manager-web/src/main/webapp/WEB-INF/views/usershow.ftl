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

     function edituser(id){
         $("#pnCode").val('');
         $("#zcCount").val('');
         $("#customerid").val('');
         $.ajax({
             url:"/admin/edituser",
             type:"POST",
             contentType:"application/json",
             data:+""+id+"",
             success:function(data) {
                 var temp1 = JSON.parse(data);
                 if (temp1) {
                     if (temp1.status == 1) {
                         var data1= temp1.data;
                         $("#pnCode").val(data1.pnCode);
                         $("#zcCount").val(data1.zcCount);
                         $("#customerid").val(data1.id);
                     } else {

                     }
                 }
             }

         });
     }

     function startuser(id){

         $.ajax({
             url:"/customer/exportExcel",
             type:"POST",
             contentType:"application/json",
             data:+""+id+"",
             success:function(data) {
                 var temp1 = JSON.parse(data);
                 if (temp1) {
                     if (temp1.status == 1) {
                         window.location.href="userlist";


                     } else {

                     }
                 }
             }

         });
     }
    function exportexcel() {

        $("#exportform").attr("action","/customer/exportExcel");
        $("#exportform").submit();
    }

    </script>
</head>
<body>

<div  style="margin-bottom:10px;">
    <#--<button type="button" class="btn btn-primary" onclick="javascript:adduser()">新增客户</button>-->

   <form id="exportform"></form>
    <button type="button" class="btn btn-primary" onclick="javascript:exportexcel()">导出</button>
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
            <td>
            <#if user.pastate=='0'>
            未开始
            <#elseif user.pastate=='1'>
            完成
            <#elseif user.pastate=='3'>
                未完成
            <#elseif user.pastate=='2'>
               删除
            </#if>
            </td>

            <td>  <input class="btn btn-default" type="button" data-toggle="modal" data-target="#myModal" onclick="showuser(${user.id!""})"

                         value="查看">


               </td>
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


<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">编辑客户信息</h4>
            </div>
            <div class="modal-body" >
                <form class="bs-example bs-example-form" id="customminfo" role="form">
                    <input hidden id="customerid">
                    <div class="form-group row">
                        <label class="col-md-3 col-xs-4 control-label text-right"><span>工号：&nbsp;</span></label>

                        <div class="col-md-5 col-xs-6 ivalidate padding4">
                            <input id="pnCode" type="text"   class="form-control infon-input regloginname" ></input>

                            <label class="tips"></label>
                        </div>

                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 col-xs-4 control-label text-right"> 数量：&nbsp;</label>

                        <div class="col-md-5 col-xs-6 ivalidate padding4">
                            <input id="zcCount" type="text" class="form-control infon-input " >

                            <label class="tips"></label>
                        </div>

                    </div>
                </form>
            </div>
            <div class="modal-footer">

                <button type="button" onclick="startuser()" class="btn btn-primary">启用</button>
                <#--<button type="button" class="btn btn-primary">提交更改</button>-->
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>