<html>
<head>
    <title>后台管理系统</title>

    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/bootstrap-responsive.css">
    <link rel="stylesheet" href="/css/admin.css">
    <!-- JS -->
    <script src="/js/admin/jquery.min.js"></script> <!-- jQuery -->
    <script src="/js/admin/bootstrap.js"></script> <!-- Bootstrap -->
    <script src="/js/admin/jquery.easyui.min.js"></script> <!-- Bootstrap -->
    <script src="/js/admin/jquery.tabs.js"></script> <!-- Bootstrap -->
    <script src="/js/admin/sidebar.js"></script> <!-- Bootstrap -->

</head>
<body>


<div class="navbar navbar-duomi navbar-static-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/Admin/index.html" id="logo">后台管理系统
            </a>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2" id="sidebar">
            <div class="row">
                <div>
                    <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                        <li class="active">
                            <a href="#">
                                <i class="glyphicon glyphicon-th-large"></i>
                                首页
                            </a>
                        </li>

                        <li>
                            <a href="#admininfo" class="nav-header collapsed" data-toggle="collapse">
                                <i class="glyphicon glyphicon-fire"></i>
                                用户管理
                            </a>
                            <ul id="admininfo" class="nav nav-list collapse secondmenu" style="height: 0px;">

                                <li>
                                    <a href="javascript:void(0);" onclick="OpenTab(this);" titile=""
                                       rel="/admin/userlist">用户管理</a>
                                </li>

                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="span9" style="float: right; width: 82.9%; clear: right;">
            <!-- content start -->
            <div region="center" style="margin-top: 40px;">
                <iframe id="ifm" name="ifm" style="width:100%; height: 100%;" scrolling="auto"
                        frameborder="0" src=""></iframe>
            </div>
        </div>

        <!-- content end -->
    </div>
</div>


</body>
</html>
