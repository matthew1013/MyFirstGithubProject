<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <title>管理员登录 | AnyoneHelps</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta name="keywords" content="留学生（International Student）,美国留学生活,US International Student Life,自由职业,Free-lancer,兼职,part-time,求助,help,美国威客,US witkey, 作业,assignment,论文,paper,essay,dissertation,兼职导游,tour guide,文书和申请,PS/RL/CV,RL,Recommendation Letter, PS,Personal Statement, CV,Curriculum Vitae,School Application,文章润色,proofreading,paraphrase,essay expansion,项目兼职,project part-time">
		<meta name="description" content="Anyonehelps.com 致力于建立一个依托网络而成的C2C互助生态平台，我们提供最专业的服务，连接供需双方，一方面为在任何方面需要帮助的客户提供最优匹配的帮助者为他们解决实际问题，以最合理的成本获得最大的效用；另一方面为身怀绝技的人才们提供平台去帮助他人，以自身才能赚取最多的金钱。">
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
        <link href="/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="/assets/pages/admin/css/login.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="/assets/global/img/favicon.ico" /> 
    </head>
        
    <body class="login">
        <div class="menu-toggler sidebar-toggler"></div>
        <!-- END SIDEBAR TOGGLER BUTTON -->
        <!-- BEGIN LOGO -->
        <div class="logo">
            <h1 class="font-white">Anyonehelps | 管理员登录</h1>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content" style="padding:0; border: 0;">
            <!-- BEGIN LOGIN FORM -->

            <div class="tabbable-custom nav-justified" style="margin-bottom: 0;">
               
                <div class="tab-content" style="padding: 10px 30px 30px;">
                    <div class="tab-pane active">
                        <!-- BEGIN LOGIN FORM -->
						<form class="login-form" action="/admin/login" method="post">
                            <h3 class="form-title font-red-flamingo">用户登录</h3>
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                <span> 请输入正确的用户名称及密码. </span>
                            </div>
                            <div class="form-group">
			                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			                    <label class="control-label visible-ie8 visible-ie9">用户名</label>
			                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入邮箱/手机号/用户名" name="username" /> </div>
			                <div class="form-group">
			                    <label class="control-label visible-ie8 visible-ie9">密码</label>
			                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="请输入密码" name="password" /> </div>
			                <div class="form-actions">
			                    <button type="submit" class="btn red-flamingo uppercase">登 录</button>
			                    <label class="rememberme check">
			                        <input type="checkbox" name="remember" value="1" />记住我密码 </label>
			                    <a href="javascript:;" class="forget-password">忘记密码?</a>
			                </div>
                           
                            
                        </form>
                        <!-- END LOGIN FORM -->
                        <!-- BEGIN FORGOT PASSWORD FORM -->
                        <form class="forget-form" action="/user/reset_email_pwd" method="post">
                            <h3 class="font-red-flamingo">忘记密码 ?</h3>
                            <p> 输入下面的电子邮件地址重置您的密码. </p>
                            <div class="form-group">
                                <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入您的邮箱" name="email" /> </div>
                            <div class="form-actions">
                                <button type="button" class="btn red-flamingo back-btn">返回</button>
                                <button type="submit" class="btn red-flamingo uppercase pull-right">提交</button>
                            </div>
                        </form>
                        <!-- END FORGOT PASSWORD FORM -->
                    </div>
                    
                </div>
            </div>
        </div>
        <div class="copyright"> 2014 © Anyonehelps.com. </div>
        <!--[if lt IE 9]>
			<script src="/assets/global/plugins/respond.min.js"></script>
			<script src="/assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        
        <script src="/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="/assets/global/plugins/jquery.cookie.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="/assets/pages/admin/scripts/admin-login.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
    </body>

</html>