<%@ include file="/include/header.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/account-message.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->

<input type="hidden" value="${param.userId }" id="userId"/>

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
									class="caption-subject uppercase"> 消息中心</span>
							</div>
							<ul class="nav nav-tabs">
								<li class="active">
									<a href="#tab_1_1" data-toggle="tab">系统消息</a>
									<div class="divider"></div>
								</li>
								<li>
									<a href="#tab_1_2" data-toggle="tab">留言 </a>
									<div class="divider"></div>
								</li>
								<li>
									<a href="#tab_1_3" data-toggle="tab">私信 </a>
									<div class="divider"></div>
								</li>
							</ul>
							
						</div>
						<div class="portlet-body">
							<div class="tabbable-custom">
								
								<div class="tab-content">
									<div class="tab-pane fade active in" id="tab_1_1">
										<div class="row operate">
											<div class="left">
												<input type="checkbox" name="ms-select">
											</div>
											<div class="right">
												<a href="javascript:void(0);" title="选择系统消息进行删除" class="ms-select-delete">
													<span>选择</span>
												</a>
												<a href="javascript:void(0);" title="取消" class="ms-select-cancel hide">
													<span>取消</span>
												</a>
											</div>
										</div>
										<div class="ms-list">
										</div>
										<div class="search-pagination">
											<ul class="pagination" id="pageSplit-system">

											</ul>
										</div>
									</div>
									<div class="tab-pane fade" id="tab_1_2">
										<div class="row operate">
											<div class="left">
												<input type="checkbox" name="mu-select">
											</div>
											<div class="right">
												<a href="javascript:void(0);" title="选择留言进行删除" class="mu-select-delete">
													<span>选择</span>
												</a>
												<a href="javascript:void(0);" title="取消" class="mu-select-cancel hide">
													<span>取消</span>
												</a>
											</div>
										</div>
										<div class="mu-list">
										</div>
										
										<div class="search-pagination">
											<ul class="pagination" id="pageSplit-user">

											</ul>
										</div>
									</div>

									<div class="tab-pane fade" id="tab_1_3">
										<div class="widget-container scrollable chat chat-page">
											<div class="contact-list">
												
											</div>
											<div class="heading hide">
												<i class="iconfont icon-liuyan"></i>与 <a class="friend-nick-name" href="javascript:void(0);"></a> 的私信
												<a href="javascript:void(0);" title="取消选择"><span class="pull-right cancel-delete" style="margin-left: 10px;display:none;">取消</span></a>
												<a href="javascript:void(0);" title="选择私信记录进行删除"><img src="/assets/pages/img/message/show-delete.png" class="pull-right message-curr-delete" data-show="1" style="margin-left:15px;width: 18px;"></a>
												<a href="javascript:void(0);" title="删除所有私信记录"><img src="/assets/pages/img/message/chat-delete-all.png" class="pull-right message-all-delete" style="margin-left:15px;width: 18px;"></a>
												<a href="javascript:void(0);" title="加入黑名单"><img src="/assets/pages/img/message/blacklist-add.png" class="pull-right blacklist-add" style="margin-left:15px;width: 18px;"></a>
												<a href="javascript:void(0);" title="返回" class="return"><img src="/assets/pages/img/message/return.jpg" class="pull-right" style="width: 18px;"></a>
												
											</div>

											<div class="widget-content padded message-content-list phone-style">
												<div class="more-heading hide">
													<a href="javascript:void(0);">查看更多</a>
												</div>
												<ul>
												</ul>
											</div>
											<div class="post-message hide">
												<input placeholder="输入需要发送的信息…" type="text">
												<input class="submit-message" type="button" value="发送"/>
											</div>

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
<script id="message-content-item-tmpl" type="html/x-jsrender">
	<li class="current-user">
		<img class="user-pic" data-id="${user_id_session_key}" width="30" height="30" src="${user_pic_url_session_key}">
		<div class="bubble">
			<p class="message">
				{{:content}}
			</p>
			<p class="time">
				刚刚
			</p>
		</div>
	</li>
</script>
<script id="message-content-tmpl" type="html/x-jsrender">
{{for dataList}}
	{{if #parent.parent.data.friendId==senderId}}
	<li data-id="{{:id}}">
		<img class="user-pic" data-id="{{:friendId}}" width="30" height="30" src="{{:friendPicUrl}}">
		<div class="bubble">
			<p class="message">
				{{:content}}
			</p>
			<p class="time">
				{{:createDate}}
			</p>
		</div>
	</li>
	{{else #parent.parent.data.friendId==receiverId}}
	<li class="current-user" data-id="{{:id}}">
		<img class="user-pic" data-id="{{:userId}}" width="30" height="30" src="${user_pic_url_session_key}">
		<div class="bubble">
			<p class="message">
				{{:content}}
			</p>
			<p class="time">
				{{:createDate}}
			</p>
		</div>
	</li>
	{{/if}}
{{/for}}
</script>

<script type="html/x-jsrender" id="blacklist-item-tmpl">
{{for dataList}}
<div class="row margin-top-20 ">
	<div class="col-md-1 col-sm-1 col-xs-12"></div>
	<div class="col-md-10 col-sm-10 col-xs-12">
		<div style="float:left;">
			<a href="javascript:void(0);" class="user-pic" data-id="{{:friendId}}">
				<img src="{{:friendPicUrl}}">
			</a>
		</div>
		<div style="float:left;">
			<div class="row">
				<a href="javascript:void(0);" class="user-pic" data-id="{{:friendId}}">
					<span class="username">{{:friendNickName}}</span>
				</a>
			</div>
			<div class="row">
				<span class="evaluate show-evaluate" data-userid="{{:friendId}}" data-evaluatecount="{{:friendEvaluateCount}}" data-evaluatepublishcount="{{:friendEvaluatePublishCount}}" data-honest="{{:friendHonest}}" data-quality="{{:friendQuality}}" data-punctual="{{:friendPunctual}}" data-honestpublish="{{:friendHonestPublish}}" data-align="bottom left">
					{{evaluateFormat friendEvaluate friendEvaluateCount friendEvaluatePublish friendEvaluatePublishCount/}}
				</span>
				<a href="/profile.jsp?userId={{base64Encode friendId/}}#tab_1_2" target="_blank">
					<span class="evaluate-count">（{{evaluateCountFormat friendEvaluateCount friendEvaluatePublishCount/}}）</span>
				</a>
				<span class="occupation hidden-xs"> {{:friendOccupation}} </span>
			</div>
		</div>
		<div style="float:right;">
			<button type="buttom" class="btn remove-btn" data-id="{{:friendId}}">移出黑名单</button>
		</div>
	</div>
	<div class="col-md-1 col-sm-1 col-xs-12"></div>
</div>
{{/for}}
</script>
<script type="html/x-jsrender" id="blacklist-tmpl">
<div class="blacklist-item">
	<div class="row">
		<div class="col-md-1 col-sm-1 col-xs-12"></div>
		<div class="col-md-10 col-sm-10 col-xs-12">
			<i class="glyphicon glyphicon-search search-i"></i>
			<input type="text" class="form-control search-key" placeholder="请输入邮箱/用户名/电话关键字…">
			<button class="search-start" style="float:left;">搜索</button>
		</div>
		<div class="col-md-1 col-sm-1 col-xs-1"></div>
	</div>
	<div class="blacklist-list">	
		{{if dataList}}
			{{for dataList}}
			<div class="row margin-top-20 ">
				<div class="col-md-1 col-sm-1 col-xs-12"></div>
				<div class="col-md-10 col-sm-10 col-xs-12">
					<div style="float:left;">
						<a href="javascript:void(0);" class="user-pic" data-id="{{:friendId}}">
							<img src="{{:friendPicUrl}}">
						</a>
					</div>
					<div style="float:left;">
						<div class="row">
							<span class="username">{{:friendNickName}}</span>
						</div>
						<div class="row">
							<span class="evaluate show-evaluate" data-userid="{{:friendUserId}}" data-evaluatecount="{{:friendEvaluateCount}}" data-evaluatepublishcount="{{:friendEvaluatePublishCount}}" data-honest="{{:friendHonest}}" data-quality="{{:friendQuality}}" data-punctual="{{:friendPunctual}}" data-honestpublish="{{:friendHonestPublish}}" data-align="bottom left">
								{{evaluateFormat friendEvaluate friendEvaluateCount friendEvaluatePublish friendEvaluatePublishCount/}}
							</span>
							<span class="occupation hidden-xs"> {{:friendOccupation}} </span>
						</div>
					</div>
					<div style="float:right;">
						<button type="buttom" class="btn remove-btn" data-id="{{:friendId}}">删除黑名单</button>
					</div>
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12"></div>
			</div>
			{{/for}}
		{{/if}}
	</div>
	<div class="row">
		<div class="col-md-1 col-sm-1 col-xs-1"></div>
		<div class="col-md-10 col-sm-10 col-xs-10">
			<div class="search-pagination">
				<ul class="pagination"> </ul>
			</div>
		</div>
		<div class="col-md-1 col-sm-1 col-xs-1"></div>
	</div>
</div>
    
</script>

<script id="contact-item-tmpl" type="html/x-jsrender">
<li>
	<a href="javascript:void(0);" class="message-detail" data-friendid="{{:friendId}}" data-friendnickname="{{:friendNickName}}">
		<img width="30" height="30" src="{{:friendPicUrl}}">{{:friendNickName}} 
		{{if state==0}}<i class="glyphicon glyphicon-bell text-danger"></i>{{/if}}
	</a>
</li>
</script>

<script id="contact-tmpl" type="html/x-jsrender">
<div class="blacklist-heading">
	<a href="javascript:void(0);" class="show-blacklist">
		<span class="blacklist-pic">黑</span>查看黑名单
	</a>
</div>
<div class="heading">
	私信联系人(<span class="contact-user-count" data-count="{{:dataList.length}}">{{:dataList.length}}</span>)
</div>
<ul>
	{{for dataList}}
	<li>
		<a href="javascript:void(0);" class="message-detail" data-friendid="{{:friendId}}" data-friendnickname="{{:friendNickName}}">
			<img width="30" height="30" src="{{:friendPicUrl}}">{{:friendNickName}} 
			{{if state==0}}<i class="glyphicon glyphicon-bell text-danger"></i>{{/if}}
		</a>
	</li>
	{{/for}}

</ul>

</script>

<script id="ms-items-tmpl" type="html/x-jsrender">
{{if dataList}}
	{{for dataList}}
	<div class="ms-item">
		<div class="left {{if #parent.parent.data.display=="1"}}hide{{/if}}">
			<input type="checkbox" name="ms-delete" value="{{:id}}">
		</div>
		<div class="right {{if state == "0"}}unread{{/if}} {{if #parent.parent.data.display=="1"}}full{{/if}}">
			<div class="div-left">
				<span class="system-pic">系</span>
    		</div>
			<div class="div-right">
				<div class="row">
					<span class="username">system</span>
					<span class="create-date">{{:createDate}}</span>
				</div>
				<div class="row">
					{{if recommender !== ""&&recommender !== "-1"&&recommendState === "0"}}
					<a class="ms-read" data-id="{{:id}}">
						<span class="content">{{:content}}</span>
					</a>
					{{else}}
					<a href="{{:link}}" target="_blank" class="ms-read" data-id="{{:id}}">
						<span class="content">{{:content}}</span>
					</a>
					{{/if}}
					
				</div>
				
			</div>

			<div class="row">
				<a href="javascript:;" class="delete" data-id="{{:id}}">
					<span>删除</span>
				</a>
				{{if recommender&&recommender !== "-1"&&recommendState === "0"}}
				<a href="javascript:;" class="recommend-operation accepted" data-id="{{:id}}" data-recommender="{{:recommender}}">
					<span>接受</span>
				</a>
				<a href="javascript:;" class="recommend-operation reject" data-id="{{:id}}" data-recommender="{{:recommender}}">
					<span>拒绝</span>
				</a>
				{{else recommendState === "1"}}
				<span class="recommend-operation">已接受</span>
				{{else recommendState === "2"}}
				<span class="recommend-operation">已拒绝</span>
				{{/if}}

			</div>
		</div> 
	</div> 
	{{/for}}
{{else}}
	<div class="ms-item">
		<div class="row" style="text-align: center;">暂未收到系统消息~~</div>
	</div> 
{{/if}}
</script>

<script id="mu-items-tmpl" type="html/x-jsrender">
{{if dataList}}
	{{for dataList}}
	<div class="mu-item">
		<div class="left {{if #parent.parent.data.display=="1"}}hide{{/if}}">
			<input type="checkbox" name="mu-delete" value="{{:id}}">
		</div>
		<div class="right {{if state == "0"}}unread{{/if}} {{if #parent.parent.data.display=="1"}}full{{/if}}">
			<div class="div-left">
				<a href="javascript:void(0);" class="user-pic" data-id="{{:sendUser.id}}">
					<img class="img-circle" src="{{:sendUser.picUrl}}" data-id="{{:sendUser.id}}">
				</a>
    		</div>
			<div class="div-right">
				<div class="row">
					<a href="javascript:void(0);" class="user-pic" data-id="{{:sendUser.id}}" style="float: left;">
						<span class="username">{{:sendUser.nickName}}</span>
					</a>
					{{if sendUser.pro>0}}
					<div class="pro" style="border-radius: 10px;width: 30px;height: 16px;margin-left: 20px;float: left;text-align: center;">
						<a href="javascript:;" style="font-size: 12px;color: #ffffff;letter-spacing: 0;text-align: left;width: 16px;background: #ed75b8;border-radius: 10px;padding: 1px 8px;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">Pro</a>
					</div>
					{{/if}}
					<span class="create-date">{{:createDate}}</span>
				</div>
				<div class="row">
					<a href="{{:link}}" target="_blank" class="mu-read" data-id="{{:id}}">
						<span class="content">{{:content}}</span>
					</a>
				</div>
			</div>

			<div class="row">
				<a href="javascript:;" class="delete" data-id="{{:id}}">
					<span>删除</span>
				</a>
				<a href="{{:link}}" target="_blank" class="mu-read read">
					<span>查看</span>
				</a>
			</div>
		</div>
	</div> 
	{{/for}}
{{else}}
	<div class="mu-item">
		<div class="row" style="text-align: center;">暂未收到留言~~</div>
	</div> 
{{/if}}
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
<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/pages/scripts/account-messages.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
