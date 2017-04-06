	<%@ page language="java" import="java.util.*,com.anyonehelps.common.util.Base64Util" pageEncoding="UTF-8"%>
	
	<link href="/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/layouts/leftsider/css/layout.css" rel="stylesheet" type="text/css" />
	<link href="/assets/layouts/leftsider/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color" />
	<link href="/assets/layouts/leftsider/css/custom.css" rel="stylesheet" type="text/css" />
	<!-- BEGIN SIDEBAR --> 
	<div class="page-sidebar-wrapper">
		<!-- BEGIN SIDEBAR -->
		<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
		<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
			<!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
			<!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
			<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
			<!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
			<!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
			<ul class="page-sidebar-menu" data-keep-expanded="true" data-auto-scroll="true" data-slide-speed="200">
				
				<li class="nav-item start nav1">
					<a href="javascript:;" class="nav-link nav-toggle">
						<i class="iconfont icon-user"></i> 
						<span class="title">用户中心</span> 
						<span class="selected"></span> 
						<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
					
						<li class="nav-item start hidden-sm hidden-md hidden-lg" >
							<a href="/" class="nav-link ">
							<i class="iconfont icon-user-profile"></i> 
							<span class="title">首页</span>
							</a> 
						</li> 
						<%
							if (sessionValue != null && !sessionValue.equals("")) {
								String userId = (String) session
										.getAttribute("user_id_session_key");
									userId = Base64Util.encode(userId);
						%>
						<li class="nav-item start hidden-sm hidden-md hidden-lg">
							<a href="/profile.jsp?userId=<%=userId %>" class="nav-link ">
							<i class="iconfont icon-user-profile"></i> 
							<span class="title">个人主页</span>
							</a>
						</li>
						<%
							}
						%>
						
						<li class="nav-item start nav11" >
							<a href="/dashboard/Account/setting.jsp" class="nav-link ">
							<i class="iconfont icon-user-profile"></i> 
							<span class="title">我的资料</span>
							</a>
						</li>
						<li class="nav-item start nav12">
							<a href="/dashboard/Account/myskills.jsp" class="nav-link "> 
								<i class="iconfont icon-specialty"></i> 
								<span class="title">技能管理</span>
							</a>
						</li>

						<li class="nav-item start nav13">
							<a href="/dashboard/Account/messages.jsp" class="nav-link "> 
								<i class="iconfont icon-message"></i> 
								<span class="title">消息中心</span>
							</a>
						</li>
						<!-- 
						<li class="nav-item start nav14">
							<a href="/dashboard/Account/invite.jsp" class="nav-link "> 
								<i class="iconfont icon-level"></i> 
								<span class="title">我的邀请</span>
							</a>
						</li> -->
						<li class="nav-item start nav14">
							<a href="/dashboard/Account/friend.jsp" class="nav-link "> 
								<i class="iconfont icon-follow-user"></i> 
								<span class="title">我关注的人</span>
							</a>
						</li>
						<li class="nav-item start nav15">
							<a href="/dashboard/Account/friended.jsp" class="nav-link "> 
								<i class="iconfont icon-follow"></i> 
								<span class="title">关注我的人</span>
							</a>
						</li>
						
						<li class="nav-item start hidden-sm hidden-md hidden-lg" >
							<a href="javascript:logout()" class="nav-link ">
							<i class="iconfont icon-user-profile"></i> 
							<span class="title">退出</span>
							</a> 
						</li> 
					</ul>
				</li>
				<li class="nav-item start nav2">
					<a href="javascript:;" class="nav-link nav-toggle"> 
						<i class="iconfont icon-task"></i> 
						<span class="title">任务中心</span>
						<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
						<li class="nav-item start nav21">
							<a href="/dashboard/Task/add.jsp" class="nav-link "> 
								<i class="iconfont icon-task-add"></i> 
								<span class="title">新发任务</span> 
							</a>
						</li>
						<li class="nav-item start nav22">
							<a href="/dashboard/Task/publishedList.jsp?view=tab_1_0" class="nav-link "> 
								<i class="iconfont icon-task-pub"></i> 
								<span class="title">已发布任务</span>
							</a>
						</li>
						<li class="nav-item start nav23">
							<a href="/dashboard/Task/acceptedList.jsp?view=tab_1_0" class="nav-link "> 
							<i class="iconfont icon-task-acc"></i> 
							<span class="title">已接收任务</span>
							</a>
						</li>
						<li class="nav-item start nav24">
							<a href="/dashboard/Task/follow.jsp" class="nav-link "> 
								<i class="iconfont icon-task-follow"></i>
								<span class="title">关注的任务</span>
							</a>
						</li>
						<!-- <li class="nav-item start nav25">
							<a href="/dashboard/Task/arbitrationList.jsp" class="nav-link "> 
								<i class="iconfont icon-task-arbitration"></i> 
								<span class="title">任务仲裁记录</span>
							</a>
						</li> -->
					</ul>
				</li>
				<li class="nav-item start nav3">
					<a href="javascript:;" class="nav-link nav-toggle"> 
						<i class="iconfont icon-finance"></i> 
						<span class="title">财务中心</span> 
						<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
						<li class="nav-item start nav31">
							<a href="/dashboard/Finance/recharge.jsp" class="nav-link "> 
								<i class="iconfont icon-finance-recharge"></i> 
								<span class="title">帐号充值</span>
							</a>
						</li>
						<li class="nav-item start nav32">
							<a href="/dashboard/Finance/records.jsp" class="nav-link "> 
							<i class="iconfont icon-finance-records"></i> 
							<span class="title">财务明细</span>
							</a>
						</li>
						<li class="nav-item start nav33">
							<a href="/dashboard/Finance/checkout_request.jsp" class="nav-link ">
								<i class="iconfont icon-finance-checkout-request"></i> 
								<span class="title">取现申请</span>
							</a>
						</li>
						<li class="nav-item start nav34">
							<a href="/dashboard/Finance/checkout_records.jsp" class="nav-link ">
								<i class="iconfont icon-finance-checkout-records"></i> 
								<span class="title">取现记录</span>
							</a>
						</li>
						
					</ul>
				</li>
				
				<li class="nav-item start nav4">
					<a href="javascript:;" class="nav-link nav-toggle"> 
						<i class="iconfont icon-boint"></i> 
						<span class="title">奖励中心</span> 
						<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
						<li class="nav-item start nav41">
							<a href="/dashboard/Reward/invite.jsp" class="nav-link "> 
								<i class="iconfont icon-sms"></i> 
								<span class="title">邀请好友</span>
							</a>
						</li> 
						<li class="nav-item start nav42">
							<a href="/dashboard/Reward/bonus_point.jsp" class="nav-link "> 
							<i class="iconfont icon-boint-my"></i> 
							<span class="title">我的奖励</span>
							</a>
						</li>
					</ul>
				</li>
				<li class="nav-item start nav5">
					<a href="javascript:;" class="nav-link nav-toggle"> 
						<i class="iconfont icon-daniushequ"></i> 
						<span class="title">大牛社区</span> 
						<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
						<li class="nav-item start nav51">
							<a href="/dashboard/Pro/become.jsp" class="nav-link "> 
								<i class="iconfont icon-user-great-add"></i> 
								<span class="title">成为大牛</span>
							</a>
						</li> 
						<li class="nav-item start nav53">
							<a href="/dashboard/Pro/records.jsp" class="nav-link "> 
							<i class="iconfont icon-user-great-helps"></i> 
							<span class="title">申请大牛记录</span>
							</a>
						</li>
						<li class="nav-item start nav52">
							<a href="/dashboard/Pro/list.jsp" class="nav-link "> 
							<i class="iconfont icon-user-great-search"></i> 
							<span class="title">大牛查询</span>
							</a>
						</li>
						
					</ul>
				</li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->
	</div>
	<!-- END SIDEBAR -->
