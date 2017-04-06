	<link href="/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/layouts/leftsider/css/layout.css" rel="stylesheet" type="text/css" />
	<link href="/assets/layouts/leftsider/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color" />
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
					<ul class="sub-menu" style="display: block;">
						<li class="nav-item start nav11" >
							<a href="#"class="nav-link ">
							<i class="iconfont icon-user-profile"></i> 
							<span class="title">我的资料</span>
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
					<ul class="sub-menu" style="display: block;">
						<li class="nav-item start nav21">
							<a href="/admin/dashboard/Task/list.jsp" class="nav-link "> 
								<i class="iconfont icon-task-add"></i> 
								<span class="title">任务列表</span> 
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
