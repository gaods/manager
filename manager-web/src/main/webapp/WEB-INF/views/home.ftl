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

        <div id="bs-example-navbar-collapse-9" class="collapse navbar-collapse navbar-right">
            <ul class="nav navbar-nav">
                <li class="wb-sellang">
                    <select name="i18nopts" id="i18nopts" style="display:none"><option selected="" value="zh_CN">简体中文</option><option value="en_US">English</option></select>
                </li>
                <li class="wb-msgtip hide">
                    <a href="#/messageCenter" id="messageCount">
                        <div class="u-badge u-badge-info w-20 m" data-badge="0">
                            <i class="iconfont icon-tongzhi"></i>
                        </div>
                    </a>
                </li>

                <li class="yc-dropdown" id="li_msg">
                    <a href="#" class="dropdown-toggle count-info" title="消息中心">
                        <i class="fa fa-envelope"></i>
                        <span id="msgcount" class="label label-warning"></span>
                    </a>
                    <ul id="msgcontent" class="dropdown-menu dropdown-messages">
                        <li>
                            <div class="text-center link-block">
                                <a id="allmessage" href="#/ifr/%252Fgwmanage%252Fmygwapp%252Fmessagecenter%252Findex">
                                    <i class="fa fa-envelope"></i> <strong>查看所有消息</strong>
                                </a>
                            </div>
                        </li>
                    </ul>
                </li>

                <li class="dropdown wb-child open">
                    <a role="button" id="username" aria-expanded="true" href="javascript:void (0);" data-toggle="dropdown" class="navbar-avatar dropdown-toggle">

                        <span class="avatar-name">11</span>
                        <span class="iconfont icon-arrowdown"></span>
                    </a>
                    <ul role="menu" class="dropdown-menu" id="moreMenu">

                            <li role="presentation">
                        <a role="menuitem" href="/caslogout">
                            <i aria-hidden="true" id="logout" class="iconfont  icon-logout"></i> 注销</a></li></ul>
                </li>
            </ul>
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
                                客户经理管理
                            </a>
                            <ul id="admininfo" class="nav nav-list collapse secondmenu" style="height: 0px;">

                                <li>
                                    <a href="javascript:void(0);" onclick="OpenTab(this);" titile=""
                                       rel="/admin/userlist">客户管理</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0);" onclick="OpenTab(this);" titile=""
                                       rel="/admin/usershow">客户查看</a>
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
