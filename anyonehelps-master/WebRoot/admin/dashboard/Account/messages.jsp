<%@ include file="../inc/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="${pageContext.request.contextPath}/assets/global/plugins/datatables/datatables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<%@ include file="../inc/frontmenu.jsp"%>
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<%@ include file="../inc/leftsider.jsp"%>
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<!-- BEGIN CONTENT BODY -->
		<div class="page-content">
			<!-- BEGIN PAGE BASE CONTENT -->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet light bordered">
						<div class="portlet-title">
							<div class="caption font-dark">
								<i class=" icon-list"></i> <span
									class="caption-subject bold uppercase"> 消息中心</span>
							</div>
						</div>
						<div class="portlet-body">
							<div class="tabbable-custom">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#tab_1_1" data-toggle="tab">系统消息</a></li>
									<li><a href="#tab_1_2" data-toggle="tab">留言消息 </a></li>

								</ul>
								<div class="tab-content">
									<div class="tab-pane fade active in" id="tab_1_1">
										<div class="blog-comments">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th width="60">状态</th>
														<th>内容</th>
														<th width="120">来源</th>
														<th width="120">创建时间</th>
													</tr>
												</thead>
												<tbody id="messages-system-list">

												</tbody>
											</table>
										</div>
										<div class="search-pagination">
											<ul class="pagination" id="pageSplit-system">

											</ul>
										</div>
									</div>
									<div class="tab-pane fade" id="tab_1_2">
										<div class="blog-comments">
										<table class="table table-bordered">
												<thead>
													<tr>
														<th width="60">状态</th>
														<th>内容</th>
														<th width="200">来源</th>
														<th width="120">创建时间</th>
													</tr>
												</thead>
												<tbody id="messages-user-list">

												</tbody>
											</table>
										</div>
										<div class="search-pagination">
											<ul class="pagination" id="pageSplit-user">

											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE BASE CONTENT -->

		</div>
		<!-- END CONTENT BODY -->
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<script id="items-tmpl" type="html/x-jsrender">
{{for dataList}}
<tr>
	<td>{{:state == "0" ? "<em class='font-red-flamingo'>new</em>": "已读"}}</td>
	<td>
		<a href="{{:#parent.parent.data.baseUrl}}{{:link}}" target="_blank" name="message-system-content" data-id="{{:id}}">{{:content}}</a>
	</td>
	<td>系统</td>
	<td>{{:createDate}}</td>
</tr>
{{/for}}
</script>


<script id="messages-user-items-tmpl" type="html/x-jsrender">
{{for dataList}}
<tr>
	<td>{{:state == "0" ? "<em class='font-red-flamingo'>new</em>": "已读"}}</td>
	<td><a href="{{:#parent.parent.data.baseUrl}}{{:link}}" target="_blank" name="message-user-content" data-id="{{:id}}">{{:content}}</a></td>
	<td>{{for sendUser}}

	    	<img data-id="{{:id}}" style="margin: 4px;height: 39px;" class="media-object img-circle pull-left user-pic" alt="" src="{{:#parent.parent.parent.data.baseUrl}}{{:picUrl}}"> 
	    		<a class="user-pic" data-id="{{:id}}">{{:nickName}}</a>
		{{/for}}</td>
	<td>{{:createDate}}</td>
</tr>
{{/for}}
</script>



<%@include file="../inc/footer.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script
	src="${pageContext.request.contextPath}/assets/global/scripts/datatable.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script>
<script 
	src="${pageContext.request.contextPath}/assets/global/plugins/jquery.twbsPagination.min.js"
	type="text/javascript"></script>
	
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- <script src="${pageContext.request.contextPath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script> -->
<script
	src="${pageContext.request.contextPath}/assets/pages/scripts/account-messages.js"
	type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
