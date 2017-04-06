<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="/include/header.jsp"%>

        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"  data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-463" data-genuitec-path="/anyonehelps-master/WebRoot/login.jsp"/>
        <link href="/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="/assets/pages/css/login.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL STYLES -->
		<% 
			String title = "";
			String keywords = "";
			String description = "";
			Seo s = SeoUtil.getSeoByWebFlag("8");
			if(s != null){
				title = s.getTitle();
				keywords = s.getKeywords();
				description = s.getDescription();
			}
		%>
		<input type="hidden" name="seoTitle" value="<%=title %>">
		<input type="hidden" name="seoKeywords" value="<%=keywords %>">
		<input type="hidden" name="seoDescription" value="<%=description %>">
			
    	<!-- 判断是否已经登录 -->
		<%
			if (sessionValue != null && !sessionValue.equals("")) {
				response.sendRedirect("/");
			}
		%>
        <!-- BEGIN LOGIN -->
        <div class="content">
        	
        	<!-- BEGIN LOGIN FORM -->
           <!-- <div class="tabbable-custom nav-justified">  haokun delete 2017/03/15-->
		  <!-- <div class="tabbable-custom-bg">  <!--haokun delete 2017/03/22-->
			<div class="tabbable-custom">  <!--  haokun add 2017/03/15-->
               <!-- <ul class="nav nav-tabs nav-justified">     <!--  haokun deleted 2017/03/15-->
			   <ul class="nav">     <!--  haokun added 2017/03/15-->
                    <li class="active" id="li-tab-first-1">
                        <a href="#" data-toggle="tab">Sign-Up</a>   
                    </li>
                    <li id="li-tab-first-2"> 
                        <a href="#" data-toggle="tab" >Sign-In</a>  
                    </li>
                </ul>
			   <ul class="nav">     <!--  haokun added 2017/03/15-->
                    <li id="li-tab-1">
                        <a href="#tab_1 " data-toggle="tab">Email</a>   
                    </li>
                    <li id="li-tab-2"> 
                        <a href="#tab_2" data-toggle="tab" >Phone</a>  
                    </li>
                    <li id="li-tab-3">
                        <a href="#tab_3" data-toggle="tab" >Forget?</a>
                    </li>
                </ul>
                <div class="tab-content" style="padding: 10px 30px 30px;">
                    <div class="tab-pane active" id="tab_1">
                        <!-- BEGIN LOGIN FORM -->
						<form class="email-login-form" action="user/login" method="post">
                           <!-- <h3 class="form-title">用户登录</h3> haokun delete 201/03/16   -->
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                <span> 请输入正确的邮箱/用户名及密码. </span>
                            </div>
                            <div class="form-group">
			                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			                    <label class="control-label ">Username/Email</label>    <!--haokun modified 2017/03/15 去掉visible-ie8 visible-ie9-->
			                    <input class="form-control form-control-solid " type="text" autocomplete="off" placeholder="Enter email or username" name="username" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label ">Password</label>  <!--haokun modified 2017/03/15 去掉visible-ie8 visible-ie9-->
			                    <input class="form-control form-control-solid " type="password" autocomplete="off" placeholder="Enter password" name="password" /> 
			                </div>
			                <div class="form-group show-login-code hide">
			                    <label class="control-label ">Verification Code</label> <!--haokun modified 2017/03/15 去掉visible-ie8 visible-ie9-->
			                    <input class="form-control form-control-solid " type="text" autocomplete="off" placeholder="Enter code" name="login-code" style="width:calc(100% - 290px);display: inline;" /> 
			                    <img src="/code/generate" style="width:100px;height:32px;margin-left: 10px;margin-right: 10px;">
								<span class="refresh-code" style="color:#f86436;cursor:pointer;line-height:32px;">Refresh</span>
			                </div>
			                <div class="form-actions">
							    <label class="rememberme check">
			                        <input type="checkbox" name="remember" value="1" id="email-login-form-checkbox"><span id="email-login-form-checkbox-span">Keep me Signed in</span>  <!--haokun added 2017/03/23 增加了span 和id，用来准确定位-->
								</label><br>
			                    <button type="submit" class="btn green uppercase">Login In</button>

			                    <a href="javascript:;" class="forget-password">Forget?</a>
			                </div>
                           
                            <div class="login-options">
                                <h4>第三方账号登录</h4>
                                <ul class="login-three">
                                    <li style="list-style: none;float: left;">
                                        <a class="facebook" scope="public_profile,email" href="javascript:;">
                                        	<img src="/assets/pages/img/login/facebook.png"/>
                                        </a>
                                    </li>
                                    <li style="list-style: none;float: left;">
                                        <a class="weixin" href="javascript:;">
                                        	<img src="/assets/pages/img/login/wechat.png"/>
                                       	</a>
                                    </li>
                                </ul>
                            </div>
                           <!-- <div class="create-account">  <!--haokun modified 2017/03/16 删除class
                                <p>
                                    <a href="javascript:;" class="register-btn" class="uppercase">返回注册</a> 
                                </p>
                            </div>-->
                        </form>
                        <!-- END LOGIN FORM -->
                        <!-- BEGIN FORGOT PASSWORD FORM -->
                        <form class="email-forget-form" action="user/reset_email_pwd" method="post">
                            <!--<h3 class="font-title">忘记密码 ?</h3>  haokun deleted 2017/03/16-->
                            <p> Reset passowrd by Email. </p>
                            <div class="form-group">
                                <input class="form-control " type="text" autocomplete="off" placeholder="Enter email" name="email" /> </div>
                            <div class="form-actions">
							    <button type="submit" class="btn green uppercase pull-right">Submit</button>
								<!--<div></div>   haokun deleted 2017/03/17
                                <button type="button" class="btn green back-btn">返回登录</button>-->

                            </div>
                            
                        </form>
                        <!-- END FORGOT PASSWORD FORM -->
                        <!-- BEGIN REGISTRATION FORM -->
			            <form class="email-register-form" action="user/email_register" method="post">
			                <!-- <h3>邮箱注册</h3>     haokun delete 2017/03/15
			               <!-- <p class="hint"> 输入您的注册详细信息: </p>   haokun deleted 2017/03/15-->
			                <div class="form-group">
			                    <label class="control-label">Username</label>     <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter username" name="reg-email-username" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label">Email</label>     <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter email" name="reg-email" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label">Password</label>      <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="password" autocomplete="off" id="register_password" placeholder="Enter password" name="reg-email-password" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label">Reenter Password</label>    <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="password" autocomplete="off" placeholder="Reenter password" name="reg-email-confirm-password" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label">Referrer</label>    <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
								<input class="form-control " type="text" autocomplete="off" placeholder="Enter username or email of referrer" name="reg-email-recommend" /> 
								<span class="tooltip-login"><span class="glyphicon glyphicon-question-sign"></span>Referrer <span class="tooltiptext-login">推荐人就是邀请您注册的用户</span></span> <!--haokun add 2017/02/09 -->							
			                    </div>
			                <div class="form-group show-login-code hide">
			                    <label class="control-label">Verification Code</label>  <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control form-control-solid " type="text" autocomplete="off" placeholder="Enter code" name="login-code" style="width:calc(100% - 290px);display: inline;" /> 
			                    <img src="/code/generate" style="width:100px;height:32px;margin-left: 10px;margin-right: 10px;">
								<span class="refresh-code" style="color:#f86436;cursor:pointer;line-height:32px;">Refresh</span>
			                </div>
			                <div class="form-group margin-top-20 margin-bottom-20">
			                    <label class="check">
			                        <input type="checkbox" name="tnc" checked/> 阅读并接受
			                        <a href="/agreement/user.jsp" target="_blank">用户协议 </a>、
			                        <a href="/agreement/credit.jsp" target="_blank">诚信规范服务承诺 </a>、
			                        <a href="/agreement/pro.jsp" target="_blank">大牛社区服务协议 </a>
			                    </label>
			                    <div id="register_tnc_error"> </div>
			                </div>
			                <div class="form-actions">
			                    <!--<button type="button" class="btn green register-back-btn">返回登录</button> haokun deleted 2017/03/17-->
			                    <button type="submit" id="register-submit-btn" class="btn green uppercase pull-right">Sign Up</button>
			                </div>
			            </form>
                        <!-- END REGISTRATION FORM -->
                    </div>
                    <div class="tab-pane" id="tab_2">
                    	<!-- BEGIN LOGIN FORM -->
                        <form class="login-form" action="/user/phone_login" method="post">
                            <!--<h3 class="form-title ">用户登录</h3>   haokun 2017/03/16-->
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                <span> 请输入正确的手机号码及密码. </span>
                            </div>
                            <div class="form-group">
			                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			                    <label class="control-label">Area-code/Phone</label><br>    <!--haokun modified 2017/03/15 去掉hide-ie8 ie9并加入电话-->
			                    <select class="form-control area-code"  style="position: absolute;z-index: 3;width: 150px;padding:10px;">   <!--haokun modified 2017/03/15 修改-->
									<option value="+1">(+1)美国/加拿大</option>
                        			<option value="+86">(+86)中国</option>
                        			<option value="+44">(+44)英国</option> 
                        			<option value="+61">(+61)澳洲</option>
								</select> 
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter phone number" name="username" style="margin-left:36%"/>  
			                </div>
			                <div class="form-group">
			                    <label class="control-label">Password</label>     <!--haokun modified 2017/03/23 去掉hide-ie8 ie9-->
			                    <input class="form-control form-control-solid " type="password" autocomplete="off" placeholder="Enter password" name="password"/> 
			               	</div>
			               	<div class="form-group show-login-code hide">
			                    <label class="control-label">Verification Code</label>    <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control form-control-solid " type="text" autocomplete="off" placeholder="Enter code" name="login-code" style="width:calc(100% - 290px);display: inline;" /> 
			                    <img src="/code/generate" style="width:100px;height:32px;margin-left: 10px;margin-right: 10px;">
								<span class="refresh-code" style="color:#f86436;cursor:pointer;line-height:32px;">Refresh</span>
			                </div>
			                <div class="form-actions">
							    <label class="rememberme check">
			                        <input type="checkbox" class="remember" value="1" id="login-form-checkbox"/><span id="login-form-checkbox-span">Keep me Signed in</span>    <!--haokun added 2017/03/23 增加了span-->
			                  	</label><br>
								<button type="submit" class="btn green uppercase">Sign In</button>		                    
			                    <a href="javascript:;" class="forget-password">Forget?</a>								
			                </div>
                           
                            <div class="login-options">
                                <h4>第三方账号登录</h4>
                                <ul class="login-three">
                                    <li style="list-style: none;float: left;">
                                        <a class="facebook" scope="public_profile,email" href="javascript:;"><img src="/assets/pages/img/login/facebook.png"/></a>
                                    </li>
                                    <li style="list-style: none;float: left;">
                                        <a class="weixin" href="javascript:;"><img src="/assets/pages/img/login/wechat.png"/></a>
                                    </li>
                                </ul>
                            </div>
                            <!--<div class="create-account">   haokun modified 2017/03/17 删除
                                <p>
                                    <a href="javascript:;" class="register-btn" class="uppercase">返回注册</a> 
                                </p>
                            </div>-->
                        </form>
                        <!-- END LOGIN FORM -->
                        <!-- BEGIN FORGOT PASSWORD FORM -->
                        <form class="phone-forget-form" action="user/reset_pwd" method="post">
                           <!-- <h3 class="font-green">忘记密码 ?</h3> haokun deleted 2017/03/16-->
                            <p> Reset your password by telphone. </p>
                            <div class="form-group">
                            	<select class="form-control area-code" style="position: absolute;z-index: 3;width: 150px;padding:10px;">
									<option value="+1">(+1)美国/加拿大</option>
                        			<option value="+86">(+86)中国</option>
                        			<option value="+44">(+44)英国</option>
                        			<option value="+61">(+61)澳洲</option>
								</select> 
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter phone number" name="forget-pwd-phone" style="width:calc(100% - 110px);display: inline;padding-left: 160px;"/> 
			                    <a id="forget-pwd-get-verifycode-btn" class="btn green uppercase pull-right"  style="line-height: 28px;border-radius: 0;width:100px" data-enable="1">Get Code</a>
			                </div>
			                <div class="form-group">
			                    <label class="control-label visible-ie8 visible-ie9">Verification Code</label>
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter verification code" name="forget-pwd-phone-verifycode" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label visible-ie8 visible-ie9">New Password</label>
			                    <input class="form-control " type="password" autocomplete="off" id="forget-pwd-password" placeholder="Enter new password" name="forget-pwd-phone-password" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label visible-ie8 visible-ie9">Reenter New Password</label>
			                    <input class="form-control " type="password" autocomplete="off" id="forget-pwd-confirm-password" placeholder="Reenter new password" name="forget-pwd-phone-confirm-password" /> 
			                </div>
			                <div class="form-actions">                               
                                <button type="submit" class="btn green uppercase pull-right">Submit</button>   <!--haokun modified 2017/03/15 交换位置-->
								<!--<div></div>   haokun deleted 2017/03/17
								<!--<button type="button" class="btn green back-btn">返回登录</button>               <!--haokun modified 2017/03/15 交换位置-->
                            </div>
                        </form>
                        <!-- END FORGOT PASSWORD FORM -->
                        <!-- BEGIN REGISTRATION FORM -->
                        <form class="register-form" action="user/phone_register" method="post">
                            <!-- <h3 class="font-title">手机注册</h3>   haokun deleted 2017/03/16
			               <p class="hint"> 输入您的注册详细信息: </p>
							-->
			                <div class="form-group">
			                    <label class="control-label">Username</label>      <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="text" placeholder="Enter username" name="reg-phone-username" /> 
			                </div>
			                <div class="form-group">
							    <label class="control-label">Telphone</label> <br>    <!--haokun modified 2017/03/15 增加电话-->
			                	<select class="form-control area-code" id="area-code" style="">
									<option value="+1">(+1)美国/加拿大</option>
                        			<option value="+86">(+86)中国</option>
                        			<option value="+44">(+44)英国</option>
                        			<option value="+61">(+61)澳洲</option>
								</select>  
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter telphone number" name="reg-phone" style="width:calc(100% - 110px);display: inline;"/> 
			                    <a id="register-get-verifycode-btn" class="btn green uppercase pull-right"  style="line-height: 28px;border-radius: 0;;width:100px" data-enable="1">Get Code</a>
			                </div>
			                <div class="form-group">     
			                    <label class="control-label">Verification Code</label>   <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter verification code" name="reg-phone-verifycode" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label">Password</label>     <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="password" autocomplete="off" placeholder="Enter password" name="reg-phone-password" /> 
			                </div>
			                <div class="form-group">
			                    <label class="control-label">Reenter Password</label>    <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="password" autocomplete="off" placeholder="Reenter password" name="reg-phone-confirm-password" /> 
			                </div>
			                
			                <div class="form-group">
			                    <label class="control-label">Referrer</label>     <!--haokun modified 2017/03/15 去掉hide-ie8 ie9-->
			                    <input class="form-control " type="text" autocomplete="off" placeholder="Enter username or email of Referrer" name="reg-phone-recommend" /> 
								 <span class="tooltip-login"><span class="glyphicon glyphicon-question-sign"></span>Referrer <span class="tooltiptext-login">推荐人就是邀请您注册的用户</span></span><!--haokun add 2017/02/09 -->
			                
							</div>
			                
			                <div class="form-group margin-top-20 margin-bottom-20">
			                    <label class="check">
			                        <input type="checkbox" name="tnc" checked/> 阅读并接受
			                        <a href="/agreement/user.jsp" target="_blank">用户协议 </a>、
			                        <a href="/agreement/credit.jsp" target="_blank">诚信规范服务承诺 </a>、
			                        <a href="/agreement/pro.jsp" target="_blank">大牛社区服务协议 </a>
			                    </label>
			                    <div id="register_tnc_error"> </div>
			                </div>
			                <div class="form-actions">
			                    <!--<button type="button" class="btn green register-back-btn">返回登录</button>  haokun deleted 2017/03/17 -->
			                    <button type="submit" id="register-submit-btn" class="btn green uppercase pull-right">Sign Up</button>
			                </div>
			            </form>
                        <!-- END REGISTRATION FORM -->
                    </div>
                    
                    
                    <div class="tab-pane" id="tab_3">
                    	
                        <!-- BEGIN FORGOT PASSWORD FORM -->
                        <form class="question-forget-form" action="/user/reset_pwd3" method="post">
                            <!--<h3 class="font-green">忘记密码 ?</h3>--> <!--haokun delete 2017/03/16-->
                            <p>Reset passowrd by telphone or username/email</p>
                            <div class="form-group">
                            	<select class="form-control question-area-code" style="position: absolute;z-index: 3;width: 150px;padding:10px;"> 
									<option value="+1">(+1)美国/加拿大</option>
                        			<option value="+86">(+86)中国</option>
                        			<option value="+44">(+44)英国</option>
                        			<option value="+61">(+61)澳洲</option>
								</select> 
			                    <input class="form-control question-phone" type="text" placeholder="Enter telphone number" style="padding-left: 160px;"/> 
			                </div>
			                <div class="form-group">
			                    <input class="form-control question-email" type="text" placeholder="Enter username or email"/> 
			                </div>
			                <div class="form-actions"> 
							    <button type="button" class="btn green uppercase pull-right question-next" data-state="0">Next</button>
								<!--<div></div>   <!--haokun add 2017/03/15 这个div用来创建一个横线-->
			                   <!-- <button type="button" class="btn green question-back-btn" data-state="0">返回登录</button>  -->
			                    
			                </div>
                        </form>
                        <!-- END FORGOT PASSWORD FORM -->
                    </div>
                    
                    
                    
                </div>
            
			</div>  <!--haokun added 2017/03/22 end-->
        </div>
        <input type="hidden" id="recommender" value="${param.recommender}"/>
        <input type="hidden" id="view" value="${param.view}"/>
        
<script id="question-first-tmpl" type="html/x-jsrender">  
<p> Reset passowrd by telphone or username/email </p>
<div class="form-group">
	<select class="form-control question-area-code" style="position: absolute;z-index: 3;width: 150px;padding:10px;">
		<option value="+1">(+1)美国/加拿大</option>
		<option value="+86">(+86)中国</option>
		<option value="+44">(+44)英国</option>
		<option value="+61">(+61)澳洲</option>
	</select> 
    <input class="form-control question-phone" type="text" placeholder="Enter telphone number" style="padding-left: 160px;"/> 
</div>
<div class="form-group">
    <input class="form-control question-email" type="text" placeholder="Enter username or email"/> 
</div>
</script>
<script id="question-two-tmpl" type="html/x-jsrender">  
<div class="form-group">
	<label class="control-label">Question 1:</label>
	<span style="color:#bfbfbf;">{{questionFormat asq.question1/}}</span>   <!--haokun added 2017/03/23 增加颜色-->
</div>
<div class="form-group">
	<input class="form-control" type="text" placeholder="Enter the answer of Question 1" name="answer"/> 
</div>
<div class="form-group">
	<label class="control-label">Question 2:</label>
	<span style="color:#bfbfbf;;">{{questionFormat asq.question2/}}</span>   <!--haokun added 2017/03/23 增加颜色-->
</div>
<div class="form-group">
	<input class="form-control" type="text" placeholder="Enter the answer of Question 1" name="answer"/> 
</div>
<div class="form-group">
	<label class="control-label">Question 3:</label>
	<span style="color:#bfbfbf;">{{questionFormat asq.question3/}}</span>    <!--haokun added 2017/03/23 增加颜色-->
</div>
<div class="form-group">
	<input class="form-control" type="text" placeholder="Enter the answer of Question 1" name="answer"/> 
</div>
</script> 

<script id="question-third-tmpl" type="html/x-jsrender">  
<div class="form-group">
	<label class="control-label">New password:</label>
	<input class="form-control" type="password" placeholder="Enter passowrd" name="pwd"/> 
</div>
<div class="form-group">
	<label class="control-label">Reenter password:</label>
	<input class="form-control" type="password" placeholder="Reenter password" name="newPwd"/> 
</div>
</script>

		<%@ include file="/include/footer.jsp"%>
        <!-- BEGIN CORE PLUGINS -->
        <script src="/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        
        <script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="/assets/global/plugins/jquery.cookie.js" type="text/javascript"></script>
       	<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
		<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
		<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
		<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
		
        <!-- END CORE PLUGINS -->
       
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="/assets/pages/scripts/login.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
	