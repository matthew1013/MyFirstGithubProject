<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />  
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/reward-invite.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="container">
    <%@ include file="/include/leftsider.jsp"%>
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
									class="caption-subject uppercase">邀请好友</span>
							</div>
						</div>
						<div class="portlet-body">
							<div class="row invited">
								<div class="row recommender-link">
									<div class="left">
										<span>我的推广链接:</span>
									</div>
	                               	<div class="middle">
	                               		<div class="copy-link" data-clipboard-action="copy" data-clipboard-target=".copy-link .link">
	                               			<a href="javascript:;" class="link" title="点击复制推广链接">https://www.anyonehelps.com/login.jsp?view=register&recommender=${user_id_session_key}</a> 
	                               		</div>
	                             	</div>
	                             	<div class="right">
	                             		<button type="button" class="btn copy-btn" data-clipboard-action="copy" data-clipboard-target=".copy-link .link">复制链接</button>
	                             	</div>
								</div>
								<div class="row qr-code">
									<div class="left">
										<span>我的推广二维码:</span>
									</div>
	                               	<div class="right">
	                               		<div></div>
	                             	</div>
								</div>
								
								<div class="row recommend-me email hide">
									<div class="left">
										<span class="desc">补充邀请我的用户:</span>
									</div>
	                               	<div class="right">
	                               		<input type="text" class="form-control  account" placeholder="用户名/邮箱">
	                             		<button type="button" class="btn invited-submit">提交</button>
	                             	</div>
                             	</div>
                             	<div class="row recommend-me margin-top-20 phone hide">
									<div class="left">
										<span class="desc">&nbsp;</span>
									</div>
	                               	<div class="right">
	                               		<select class="form-control area-code">
											<option value="+1">(+1)美国/加拿大</option>
											<option value="+86">(+86)中国</option>
											<option value="+44">(+44)英国</option>
											<option value="+61">(+61)澳洲</option>
										</select>
	                               		<input type="text" class="form-control phone" placeholder="手机">
	                             		<button type="button" class="btn invited-phone-submit" style="margin-left:10px;">提交</button>
	                             	</div>
                             	</div>
							</div>
						
							<div class="tabbable-custom">
								<ul class="nav nav-tabs" id="invite-tabs">
									<li class="active"><a href="#tab_1_1" data-toggle="tab">邀请注册</a></li>
									<li><a href="#tab_1_3" data-toggle="tab">请求关联 </a></li>
									<li><a href="#tab_1_2" data-toggle="tab">邀请记录 </a></li>

								</ul>
								<div class="tab-content">
									<div class="tab-pane fade active in" id="tab_1_1">
										<div class="form">
                                        	<!-- BEGIN FORM-->
                                          	<div action="#" class="form-horizontal">
                                            	<div class="form-body">
                                                 	<div class="row">
                                                   		<div class="left">
															<span>邮箱:</span>
														</div>
                                                     	<div class="right">
                                                       		<input type="email" class="form-control" placeholder="请输入邮箱" id="invite-users">
                                                        	<button type="button" class="btn invite-btn" id="invite-btn">邀请注册</button>
                                                      	</div>
                                                    </div>
                                                        
                                               		<div class="row margin-top-20">
                                                    	<div class="left">
															<span>手机:</span>
														</div>
                                                    	<div class="right">
                                                       		<select class="form-control area-code">
																<option value="+1">(+1)美国/加拿大</option>
																<option value="+86">(+86)中国</option>
																<option value="+44">(+44)英国</option>
																<option value="+61">(+61)澳洲</option>
															</select>
                                                            <input type="text" class="form-control invite-phone" placeholder="请输入手机">
                                                            <button type="button" class="btn" id="invite-phone-btn">邀请注册</button>
                                                     	</div>
                                                    </div>
                                            	</div>
                                          	</div>
                                        	<!-- END FORM-->
                                     	</div>
									</div>
									<div class="tab-pane fade" id="tab_1_2">
										<table class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th>邮箱</th>
													<th>手机号码</th>
													<th width="200">邀请时间</th>
													<th width="120">状态</th>
													<th width="120">操作</th>
												</tr>
											</thead>
											<tbody id="invite-list">

											</tbody>
										</table>
										<div class="search-pagination">
											<ul class="pagination" id="pageSplit">

											</ul>
										</div>
									</div>
									
									<div class="tab-pane fade" id="tab_1_3">
										<div class="form">
                                        	<!-- BEGIN FORM-->
                                          	<div action="#" class="form-horizontal">
                                            	<div class="form-body">
                                                 	<div class="row">
                                                   		<div class="left">
															<span>邮箱:</span>
														</div>
                                                     	<div class="right">
                                                       		<input type="email" class="form-control user" placeholder="请输入邮箱/用户名">
                                                        	<button type="button" class="btn invite-btn">请求关联</button>
                                                      	</div>
                                                    </div>
                                                        
                                               		<div class="row margin-top-20">
                                                    	<div class="left">
															<span>手机:</span>
														</div>
                                                    	<div class="right">
                                                       		<select class="form-control area-code">
																<option value="+1">(+1)美国/加拿大</option>
																<option value="+86">(+86)中国</option>
																<option value="+44">(+44)英国</option>
																<option value="+61">(+61)澳洲</option>
															</select>
                                                            <input type="text" class="form-control invite-phone" placeholder="请输入手机">
                                                            <button type="button" class="btn invite-phone-btn">请求关联</button>
                                                     	</div>
                                                    </div>
                                            	</div>
                                          	</div>
                                        	<!-- END FORM-->
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

<script id="invite-list-tmpl" type="html/x-jsrender">
{{for datas}}
<tr class="invite-item">
	<td>
		<span>{{:email}}</span>
	</td>
	<td>
		<span>{{:areaCode}} {{:telphone}}</span>
	</td>
	<td>
		<span>{{:modifyDate || createDate}}</span></td>
	<td>
		{{if state =="0"}} 
			<span class='font-red'>邀请未接受</span>
		{{else state =="1"}} 
			<span class='font-green'>邀请关联成功</span>
		{{else state =="2"}} 
			<span class='font-yellow'>邀请失败</span>
		{{else state =="3"}} 
			<span class='font-blue'>邀请过期</span>
		{{else state =="4"}} 
			<span class='font-dark'>等待关联确认</span>
		{{else state =="5"}} 
			<span class='' style="color:#53d89d;">关联成功</span>
		{{else state =="6"}} 
			<span class='font-purple'>关联失败</span>
		{{/if}}
	</td>
	<td>	
		<span>{{if state =="0"}} <a href="javascript:;" class="reinvite-btn" data-id="{{:id}}">再次邀请</a></span>{{/if}}
	</td>
</tr>
{{/for}}
</script>


<%@ include file="/include/footer.jsp"%>
<!-- BEGIN GLOBAL LEVEL PLUGINS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script src="/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script  src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-qrcode/jquery.qrcode.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/clipboard/clipboard.min.js" type="text/javascript"></script>
<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- <script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script> -->
<script src="/assets/pages/scripts/reward-invite.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
