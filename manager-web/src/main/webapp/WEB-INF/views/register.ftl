<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册</title>
    <link href="${cdn}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${cdn}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${cdn}/css/animate.css" rel="stylesheet">
    <link href="${cdn}/css/style.css" rel="stylesheet">

    <link href="/css/register.css" rel="stylesheet">

</head>

<body class="gray-bg">

<div class="gray-bg pace-running">
    <div class="ibox main-title text-center">
        <!-- <span id="isExist">已有账号？<a id="login"  class="btn btn-default">登录</a></span>-->
        <img src="" alt="" width="70px">
        <h2 class="i-form-title text-center" >信息注册</h2>

    </div>
    <div class="ibox-content" style="border-width:1px">


        <div class="main-content">

            <form >

                <div class="form-group row">
                    <label class="col-md-3 col-xs-4 control-label text-right"><span class="price-color">*</span><span>客户经理编号：&nbsp;</span></label>

                    <div class="col-md-5 col-xs-6 ivalidate padding4">
                        <input id="userCode" type="text"  oninput="this.value=this.value.replace(/([\u4E00-\u9FA5])/g,'');" class="form-control infon-input regloginname" placeholder="请输入登录用户名" ></input>



                        <label class="tips"></label>
                    </div>

                    <div class="message-box message-box-account">
                        <div class="send">
                            <div class="arrow">
                                ◄
                            </div>
                            <span id="checkUserCode"></span>
                        </div>
                    </div>
                </div>


                <div class="form-group row">
                    <label class="col-md-3 col-xs-4 control-label text-right"><span class="price-color">*</span>密码：&nbsp;</label>

                    <div class="col-md-5 col-xs-6 ivalidate padding4">
                        <input id="password" type="password" class="form-control infon-input regpassword1" placeholder="请设置密码（至少6位）" data-bind="{value:user.pwd}">

                        <label class="tips"></label>
                    </div>
                    <!--<div class="col-md-3 text-left div-error">-->
                    <!--<span id="checkPassword" style="line-height:36px"></span>-->
                    <!--</div>-->
                    <div class="message-box message-box-pwd">
                        <div class="send">
                            <div class="arrow">
                                ◄
                            </div>
                            <span id="checkPassword"></span>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-md-3 col-xs-4 control-label text-right"><span class="price-color">*</span>确认密码：&nbsp;</label>

                    <div class="col-md-5 col-xs-6 ivalidate padding4">
                        <input id="password" type="password" class="form-control infon-input regpassword1" placeholder="请设置密码（至少6位）" data-bind="{value:user.pwd}">

                        <label class="tips"></label>
                    </div>
                    <!--<div class="col-md-3 text-left div-error">-->
                    <!--<span id="checkAgainPassword" style="line-height:36px"></span>-->
                    <!--</div>-->
                    <div class="message-box message-box-repwd">
                        <div class="send">
                            <div class="arrow">
                                ◄
                            </div>
                            <span id="checkAgainPassword"></span>
                        </div>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-md-3 col-xs-4 control-label text-right"><span class="price-color">*</span><span>手机号&nbsp;/&nbsp;邮箱：&nbsp;</span></label>

                    <div class="col-md-5  col-xs-6 padding4">

                        <input id="telno" type="text" class="form-control infon-input" placeholder="可用于登录或找回密码" data-bind="{value:user.telno}">

                        <label class="tips"></label>
                    </div>
                    <div class="message-box message-box-phone">
                        <div class="send">
                            <div class="arrow">
                                ◄
                            </div>
                            <span id="checktelno"></span>
                        </div>
                    </div>
                    <!--<div class="col-md-3 text-left div-error">-->
                    <!--<span id="checktelno" style="line-height:36px"></span>-->
                    <!--</div>-->
                </div>

                <div class="form-group row">
                    <label class="col-md-3 col-xs-4 control-label text-right"><span class="price-color">*</span><span>验证码：&nbsp;</span></label>

                    <div class="col-md-3 col-xs-3 padding4" style="padding-right: 0">
                        <input id="sms" type="text" class="form-control infon-input" placeholder="请输入验证码" data-bind="{value:user.sms}">
                        <label class="tips"></label>
                    </div>
                    <div class="col-md-2 col-xs-3 text-right">
                        <button id="getvertsms" type="button" class="btn btn-primary" data-bind="click:getsms">获取验证码
                        </button>
                    </div>
                    <!--<div class="col-md-3 text-left div-error">-->
                    <!--<span id="checksms" style="line-height:36px"></span>-->
                    <!--</div>-->
                    <div class="message-box message-box-sms">
                        <div class="send">
                            <div class="arrow">
                                ◄
                            </div>
                            <span id="checksms"></span>
                        </div>
                    </div>
                </div>


                <div class="form-group row">
                    <label class="col-md-3 col-xs-4 control-label text-right"><span class="price-color">*</span><span>图形验证码：&nbsp;</span></label>

                    <div class="col-md-5 col-xs-6 padding4">
                        <input id="graph" type="text" class="form-control infon-input regloginname" placeholder="请输入图形验证码" data-bind="{value:user.graph}">
                        <label class="tips"></label>
                    </div>
                    <div id="box-graph" style="position: absolute;right: 33%;line-height: 39px">
                        <#--<span id="datelong" style="display:none">${datelong}</span>-->
                        <img style="cursor:pointer" id="imgvertificationcode" data-bind="attr:{ 'src':nowSrc}" width="100%">
                    </div>
                    <!--<div class="col-md-3 text-left div-error">-->
                    <!--<span id="checkgraph" style="line-height:36px"></span>-->
                    <!--</div>-->
                    <div class="message-box message-box-graph">
                        <div class="send">
                            <div class="arrow">
                                ◄
                            </div>
                            <span id="checkgraph"></span>
                        </div>
                    </div>
                </div>




                <div class="row">
                    <label class="col-md-3 col-xs-3"></label>
                    <div class="col-md-5 col-xs-6 padding4">
                        <el-button style="font-size:16px;background-color:#1baede;width: 100%" class="btn btn-primary  btn-sm" id="saveBtn" @click="submitForm('registerForm')">&nbsp;立即注册&nbsp;</el-button>
                    </div>
                </div>
                <div style="margin-top: 10px;">
                    <div class="text-center" style="color:red" ></div>
                </div>

            </form>
        </div>
    </div>


</div>
<script>
    window.ctx = {};
    window.ctx.cdn = "${cdn}";
    window.ctx.portal ="${portal}";

</script>

<script src="${cdn}/js/requirejs/2.2.0/require.min.js"></script>


</body>

</html>
