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


        function  getLastIndex() {
            var indextext= $("#userdata").find("[name=index]");
            if(indextext){

                return indextext.length;
            }
            return -1;
        }

        function adduser() {
            //window.location.href="adduser";
            var i=getLastIndex();

            i=i+1;
            var index="<input type=\"hidden\"  name=\"index\" value=\""+i+"\">"

            var id="<input type=\"hidden\"  name=\"id\" value=\"\">";

            var userno="<input type=\"text\" class=\"form-control\" name=\"pnCode\">";

            var usercount="<input type=\"text\" class=\"form-control\" name=\"zcCount\">";

            $("#userdata").append("<tr>"+index+id+"<td>"+userno+"</td><td>"+usercount+"</td><td>删除</td></tr>");

        }

        function  saveuser() {


//
//         $.ajax({
//             url:"/admin/saveuser",
//             type:"POST",
//             data: $("#userform").serialize(),
//             dataType:"text/json",
//             success:function () {
//                 alert();
//             }
//         });

            var data=new Array();

            $("#userdata tr").each(function () {

                var temp=new Object();
                temp.index=$(this).find("[name=index]").val();
                temp.id=$(this).find("[name=id]").val();
                temp.pnCode=$(this).find("[name='pnCode']").val();
                temp.zcCount=$(this).find("[name='zcCount']").val();

                data.push(temp);
            });



            $.ajax({
                url: "/admin/saveuser",
                dataType: "text/json",
                contentType:"application/json",
                type:"POST",
                data: JSON.stringify(data),
                success: function (data) {
                    alert();
                },
                complete:function (data) {


                }
            });

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