<%@ page language="java" import="java.util.*,com.anyonehelps.common.util.Base64Util,com.anyonehelps.common.util.SeoUtil,com.anyonehelps.model.Seo" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html> 
<head>

<meta charset="UTF-8">
<meta name="keywords" content="留学生（International Student）,美国留学生活,US International Student Life,自由职业,Free-lancer,兼职,part-time,求助,help,美国威客,US witkey, 作业,assignment,论文,paper,essay,dissertation,兼职导游,tour guide,文书和申请,PS/RL/CV,RL,Recommendation Letter, PS,Personal Statement, CV,Curriculum Vitae,School Application,文章润色,proofreading,paraphrase,essay expansion,项目兼职,project part-time">
<meta name="description" content="Anyonehelps.com 致力于建立一个依托网络而成的C2C互助生态平台，我们提供最专业的服务，连接供需双方，一方面为在任何方面需要帮助的客户提供最优匹配的帮助者为他们解决实际问题，以最合理的成本获得最大的效用；另一方面为身怀绝技的人才们提供平台去帮助他人，以自身才能赚取最多的金钱。">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/header.css" />
<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/footer.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/artDialog/css/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/jquery-ui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/icheck-1.x/skins/minimal/blue.css"/>
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/icheck-1.x/skins/square/blue.css"/>
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/font-awesome/css/font-awesome.min.css">   <!--haokun added 2017/02/27 增加cdnjs一些icon库-->
<link rel="stylesheet" type="text/css" href="/assets/global/iconfont/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="/assets/global/css/components-rounded.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/simple-line-icons/simple-line-icons.css" />

<link rel="shortcut icon" href="/assets/global/img/favicon.ico" type="image/x-icon"/> 

<!--haokun added 2017/03/09 start 增加zendesk聊天---->
<!-- Start of anyonehelps Zendesk Widget script haokun added 2017/03/09-->
<script> 
	/*<![CDATA[*/window.zEmbed
			|| function(e, t) {
				var n, o, d, i, s, a = [], r = document.createElement("iframe");
				window.zEmbed = function() {
					a.push(arguments)
				}, window.zE = window.zE || window.zEmbed,
						r.src = "javascript:false", r.title = "",
						r.role = "presentation",
						(r.frameElement || r).style.cssText = "display: none",
						d = document.getElementsByTagName("script"),
						d = d[d.length - 1], d.parentNode.insertBefore(r, d),
						i = r.contentWindow, s = i.document;
				try {
					o = s
				} catch (e) {
							n = document.domain,
							r.src = 'javascript:var d=document.open();d.domain="'
									+ n + '";void(0);', o = s
				}
				o.open()._l = function() {
					var o = this.createElement("script");
					n && (this.domain = n), o.id = "js-iframe-async",
							o.src = e, this.t = +new Date,
							this.zendeskHost = t, this.zEQueue = a, this.body
									.appendChild(o)
				}, o.write('<body onload="document._l();">'), o.close()
			}("https://assets.zendesk.com/embeddable_framework/main.js",
					"anyonehelps.zendesk.com");
	/*]]>*/
</script>
<!-- End of anyonehelps Zendesk Widget script -->
<!--haokun added 2017/03/09 end 增加zendesk聊天---->


<script>!function(e){var c={nonSecure:"8123",secure:"8124"},t={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=t[n]+r[n]+":"+c[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document);</script></head>
<body>
	<!-- wechat(weixin) login start -->
	<div class="wechat weixin-login hide" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-465" data-genuitec-path="/anyonehelps-master/WebRoot/include/header.jsp">
		<div class="close">
			<a href="javascript:void(0);" name="wexin-login-close-a">
				<img src="/assets/pages/img/index/close-2.png">
			</a>
		</div>
		<div class="qr-code" id="winxin-content">
		</div>
	</div>
	<!-- wechat(weixin) login end -->
	
	<!--login start-->
	<div class="login-box user-login hide">
       <div class="login-box-bg">   <!--haokun added 2017/03/23-->
		<div class="close">	
			<a href="javascript:void(0);" name="login-close-a"> 
				<img src="/assets/pages/img/index/close.png">
			</a>
		</div>
		<!--   haokun deleted 2017/03/20
		<div class="logo">
			<img src="/assets/pages/img/index/logo-bottom.png">
		</div>
		<div class="tip">
			<img src="/assets/pages/img/index/login-tip1.png">
		</div>
		-->
		
		<!--
		<div class="login-box-tip2">
			<img src="/assets/pages/img/index/login-tip2.png">
		</div>
		-->
		<form class="login-form">
		    <!--haokun added 2017/03/20 start 增加了Login Title-->
			<div class="title">
			  <!--<img src="/assets/pages/img/index/logo-white.png">-->  <!--haokun deleted 2017/03/23-->
			</div>
			 <!--haokun added 2017/03/20 end 增加了Login Title-->
			<center class="email">
				<div class="form-group">
					<div class="input-group">
					<!--   
						<span class="input-group-addon">  
							<img src="/assets/pages/img/index/user.png">
						</span> 
					-->
                    	<label class="control-label">Username/Email</label> <br>			
						<input type="text" class="form-control" placeholder="Enter username or email" name="username">
					</div>
				</div>
			</center>
			
			<center class="phone hide">
	            <div class="phone-title" id="phone-title">AreaCode/Telephone</div>	 <!--haokun added 2017/03/20-->  		
				<div class="form-group">				    
					<select class="form-control area-code" style="position: absolute;z-index: 3;width: 44px;padding:0px; 
					    border: 1px solid #ccc;border-radius: 5px;border-top-right-radius: 0;border-bottom-right-radius: 0;">  <!--haokun modified 2017/03/20 margin-left从123改为-->
						<option value="+1">+1</option>
                        <option value="+86">+86</option>
                        <option value="+44">+44</option>
                        <option value="+61">+61</option>
                        <!-- <option value="+81">(+81)日本</option> -->
					</select>
					<input type="text" class="form-control" placeholder="Enter telephone number" name="telphone" style="margin-left:100px;border-top-left-radius: 0;border-bottom-left-radius: 0;">  <!--haokun modified 2017/03/20 删除margin-left -->
				</div>
			</center>
	
			<center>
				<div class="form-group">
	
					<div class="input-group">
					<!--
						<span class="input-group-addon">  
							<img src="/assets/pages/img/index/password.png">
						</span> 
					-->	
					    <label class="control-label">Password</label> <br>	
						<input type="password" class="form-control" placeholder="Enter password" name="password">
					</div>
				</div>
			</center>
			<center class="row">
				<div class="form-group show-login-code hide">
					<input type="text" style="width:150px;float:left;margin-bottom: 15px;" maxlength="4" class="form-control" placeholder="请输入验证码" name="login-code" value="">
					<img src="/code/generate" style="width:50px;height:32px;float:left;margin-left: 10px;margin-right: 10px;">
					<span class="refresh-code" style="color:#f86436;cursor:pointer;float:left;line-height:32px;">Refresh</span>   <!--haokun modified 2017/03/20 把“点击刷新”变成“refresh”-->
				</div>
			</center>
			<div class="form-group save-password" >
                     <input type="checkbox" id="login-checkbox" name="login-checkbox" /><span id="login-checkbox-span">Keep me Signed In</span>   <!--haokun added 2017/03/23 增加了span-->
			</div>
			<div class="box-btn">
				<button id="box-btn">Sign In</button>
			</div>
		</form>
		<div class="box-bottom">
			<div class="forget-password">
				<a href="/login.jsp?view=resetPwd">Forget？</a>
			</div>
			<div class="show-login-phone">
				<a href="javascript:;" class="login-phone-a" data-logintype="phone">Sign In By Phone</a>
			</div>
			<div class="sign-in-bottom">
				<a href="/login.jsp?view=register">Sign Up</a>
			</div>
		</div>
		
		<div class="horizontal-line">
		</div>
		
		<div class="other-login">
		    <h4>第三方账号登录</h4>
			<a href="javascript:void(0)" class="weixin"> 
				<img src="/assets/pages/img/index/wechat.png">
			</a> 
			<a href="javascript:void(0)" class="facebook"> 
				<img src="/assets/pages/img/index/facebook-login.png">
			</a>
		</div>
	  </div>   <!--haokun added 2017/03/23-->	
	</div>
	<!-- login end-->
	<!-- 遮罩层 -->
	<div class="mbblack hide"></div>
	
	<!--登录后显示的nav header-->
	<%
		String sessionValue = (String) session
				.getAttribute("user_username_session_key");
		if (sessionValue != null && !sessionValue.equals("")) {
			String userId = (String) session
					.getAttribute("user_id_session_key");
				userId = Base64Util.encode(userId);
	%>
	<div class="header-top">
		<div class="top-nav row">
			<div class="logo-2 hidden-xs">
				<a href="/">
				<img src="/assets/pages/img/index/logo-bottom.png"></a>
			</div>

			<div class="info person">
				<!-- 首页显示 start -->
				<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
					<img src="${user_pic_url_session_key}" class="img-circle" />
				</a>
				<!-- 首页显示 end -->
				
				<!-- 用户中心显示 start -->
				<a href="javascript:;" class="responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
					<img src="${user_pic_url_session_key}" class="img-circle" />
				</a>
				<!-- 用户中心显示 end -->
				
				<span class="grade">Lv1</span>
			
				<div class="row">
					<span class="username" title="${user_username_session_key}">${user_username_session_key}</span>
				
				</div>
				<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">  
			    	<li role="presentation"><a href="/dashboard/Account/setting.jsp" role="menuitem" tabindex="-1">用户中心</a></li>  
			       	<li role="presentation"><a href="/profile.jsp?userId=<%=userId %>" role="menuitem" tabindex="-1">我的主页</a></li> 
			       	<li role="presentation"><a href="/dashboard/Task/publishedList.jsp?view=tab_1_0" role="menuitem" tabindex="-1">已发任务</a></li>    <!--haokun modified 2017/03/01--> 
					<li role="presentation"><a href="/dashboard/Task/acceptedList.jsp?view=tab_1_0" role="menuitem" tabindex="-1">已接任务</a></li>    <!--haokun modified 2017/03/01--> 
			       	<li role="presentation" class="divider"></li>  
			      	<li role="presentation"><a href="javascript:logout();" role="menuitem" tabindex="-1">退出</a></li>  
				</ul>  
				
			</div>
			
			<div class="task-pub-btn-xs hidden-sm hidden-md hidden-lg">
				<a href="/dashboard/Task/add.jsp">
					<i class="fa fa-plus-square-o"></i>
					<!-- <div class="desc"><span>发布</span></div> -->
				</a>
			</div>
			
			<div class="info message-private">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<i class="iconfont icon-sms"></i>
					<span class="badge info-span message-count hide"> 0 </span>
				</a>
				<ul class="dropdown-menu pull-right">
					<li class="message-external">
						<h3 class="message-user-h3">
							您有 <span class="bold message-count">0</span> 个未读私信
						</h3> 
						<a href="/dashboard/Account/messages.jsp?name=tab_1_3#tab_1_3" class="more">查看全部</a>
					</li>
					<li class="divider"></li>
					<li>
						<ul class="message-list">

						</ul>
					</li>
				</ul>
			</div>
			
			<div class="info message-user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<i class="iconfont icon-liuyan"></i>
					<span class="badge info-span message-user-count hide"> 0 </span>
				</a>
				<ul class="dropdown-menu pull-right">
					<li class="message-external">
						<h3 class="message-user-h3">
							您有 <span class="bold message-user-count">0</span> 未读留言
						</h3> 
						<a href="/dashboard/Account/messages.jsp?name=tab_1_2#tab_1_2" class="more">查看全部</a>
					</li>
					<li class="divider"></li>
					<li>
						<ul class="message-user-list">

						</ul>
					</li>
				</ul>
			</div>
			
			<div class="info message-system">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<i class="iconfont icon-message"></i>
					<span class="badge info-span message-system-count hide"> 0 </span>
				</a>
				<ul class="dropdown-menu pull-right">
					<li class="message-external">
						<h3 class="message-system-h3">
							您有 <span class="bold message-system-count">0</span> 未读系统消息
						</h3> 
						<a href="/dashboard/Account/messages.jsp?name=tab_1_1#tab_1_1" class="more">查看全部</a>
					</li>
					<li class="divider"></li>
					<li>
						<ul class="message-system-list">

						</ul>
					</li>
				</ul>
			</div>
			<div class="task-pub-btn hidden-sm hidden-xs">
				<button>发布任务</button>
			</div> 
			
			<!-- 
			<div class="page-actions hidden-sm hidden-md hidden-lg">
	            <div class="btn-group">  
	                <button type="button" style="background-color: #0088ff;border-color: #0088ff;" class="btn red-haze btn-sm dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
	                    <i class="glyphicon glyphicon-plus" style="color: #ffffff;"></i>
	                </button>
	                <ul class="dropdown-menu" role="menu">
	                    <li>
	                        <a href="/dashboard/Task/add.jsp">
	                        	<i class="glyphicon glyphicon-plus"></i> 发布任务
	                        </a>
	                    </li>
	                </ul>
	            </div>
	        </div>
	         -->
			<div style="width: calc(97% - 761px);float: left;padding-left: calc(48% - 580px);">
				<div class="top-search hidden-md hidden-sm hidden-xs hide">
					<div class="top-input-icon">
						<i class="glyphicon glyphicon-search"></i>
						<input type="text" class="form-control search-top search-key" placeholder="任务名称关键字或所在地,回车进行搜索！" />
					</div>
						  
				</div>
				<div class="top-search hidden-md hidden-sm hidden-xs hide">
					<div class="top-select-icon">	
						<i class="iconfont icon-map"></i>
						<select class="form-control top-location location">
							<option value="">不限</option>
							<option value="us">美国</option>
		                    <option value="cn">中国</option>
		                    <option value="uk">英国</option>
		                    <option value="au">澳洲</option>
		                    <option value="ca">加拿大</option>
						</select>
					</div>	
				</div>
			</div>
		</div>
	</div>
	<%
		}else{
	%>
	
	<div class="header-top-login">
		<div class="top-nav">
			<div class="logo-2">  
				<a href="/"><img src="/assets/pages/img/index/logo.png"></a>
			</div>
			<div class="sign-in">
				<a href="/login.jsp?view=register"><span>注册</span></a>
			</div>
			<div class="log-in">
				<a href="javascript:void(0);" name="login-a"><span>登录</span></a>
			</div>

		</div>
	</div>
	<%} %>
	
<script id="message-system-items-tmpl" type="html/x-jsrender">
	{{for dataList}}
	<li data-id="{{:id}}">
		<a href="{{:link}}" name="message-system-link" class="message-link" data-id="{{:id}}" data-recommender="{{:recommender}}" data-title="{{:title}}"> 
			<span class="time">{{createDateFormat createDate /}}</span>
			<span class="details"> 
				<span class="glyphicon glyphicon-bell" style="color: #428bca;"></span> 
				{{substrOfTitle title /}}
			</span>
		</a>
	</li>
	<li class="divider"></li>
	{{/for}}
</script>
	
	
<script id="message-user-items-tmpl" type="html/x-jsrender">
	{{for dataList}}
	<li data-id="{{:id}}">
		<a href="{{:link}}" name="message-user-link" class="message-link" data-id="{{:id}}"> 
			<span class="photo"> 
				<img src="{{:sendUser.picUrl}}" class="img-circle" alt="">
			</span> 
			<span class="subject"> 
				<span class="from"> {{:sendUser.nickName}} </span>
		 		<span class="time">{{createDateFormat createDate /}} </span>
			</span> 
			<span class="message">
				{{substrOfMessageContent content /}}
			</span>
		</a>
	</li>
	<li class="divider"></li>
	{{/for}}
</script>

	

<script id="message-items-tmpl" type="html/x-jsrender">
	{{for dataList}}
	<li data-id="{{:id}}"> 
		<a href="/dashboard/Account/messages.jsp#tab_1_3" name="message-user-link" class="message-link" data-id="{{:id}}"> 
			<span class="photo"> 
				<img src="{{:friendPicUrl}}" class="img-circle" alt="">
			</span> 
			<span class="subject"> 
				<span class="from"> {{:friendNickName}} </span>
		 		<span class="time">{{createDateFormat createDate /}} </span>
			</span> 
			<span class="message">
				{{substrOfMessageContent content /}}
			</span>
		</a>
	</li>
	<li class="divider"></li>
	{{/for}}
</script>
	
<script id="show-evaluate-tmpl" type="html/x-jsrender">
<div class="user-evaluate">
	<div class="row">
		<span>接单人评级  </span><a href="/profile.jsp?userId={{base64Encode userId/}}#tab_1_2" target="_blank"><span class="evaluate-count">Reviews （{{:evaluateCount}}）</span></a>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			诚信
		</div>
		<div class="middle">
			{{userEvaluateFormat honest evaluateCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat honest evaluateCount/}}
		</div>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			质量
		</div>
		<div class="middle">
			{{userEvaluateFormat quality evaluateCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat quality evaluateCount/}}
		</div>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			准时
		</div>
		<div class="middle">
			{{userEvaluateFormat punctual evaluateCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat punctual evaluateCount/}}
		</div>
	</div>
	<div class="row margin-top-10">
		<span>发单人评级  </span><a href="/profile.jsp?userId={{base64Encode userId/}}#tab_1_3" target="_blank"><span class="evaluate-count">Reviews （{{:evaluatePublishCount}}）</span></a>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			诚信
		</div>
		<div class="middle">
			{{userEvaluateFormat honestPublish evaluatePublishCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat honestPublish evaluatePublishCount/}}
		</div>
	</div>
</div>
</script>

<script id="show-grade-tmpl" type="html/x-jsrender">
<div class="grade">
	<div class="row">
		<span>等级：</span><span>{{calculateGrade grade/}}级</span>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			<div class="progress progress-striped">
    			<div class="progress-bar " role="progressbar"
        	 		aria-valuenow="{{:currInterval}}" aria-valuemin="0" aria-valuemax="{{:interval}}"
         			style="width: {{ratioGrade distance interval/}}%;">
       	 			<span class="sr-only">{{ratioGrade distance interval/}}%</span>
    			</div>
			</div>
		</div>
		<div class="right">
			<span>下一级</span>
		</div>
	</div>
	<div class="row">
		<span>还需完成任务金额：</span><span>\${{:distance}}</span>
	</div>
</div>	
</script>

<script id="recommend-dlg" type="html/x-jsrender">
<div class="recommend-dlg-content">
	<div class="row title">
		<span>系统提示</span>
	</div>
	<div class="row content">
		<i class="iconfont icon-info-warning"></i>
		<span>{{:title}}</span>
	</div>
	<div class="row operate">
		<button type="button" class="btn accepted">接受</button>
		<button type="button" class="btn reject">拒绝</button>
	</div>
</div>
</script>