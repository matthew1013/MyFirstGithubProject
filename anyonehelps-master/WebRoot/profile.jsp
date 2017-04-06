<%@ page language="java" import="java.util.*,com.anyonehelps.common.util.Base64Util" pageEncoding="UTF-8"%>
	<%@ include file="/include/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/assets/global/css/components-rounded.css" />
	<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/user-profile.css" />
	<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/><!--haokun added 2017/02/23-->
	<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/><!--haokun added 2017/02/23-->
	<input type="hidden" value="${param.userId }" id="userId"/>
	<input type="hidden" value="${user_id_session_key}" id="myId"/>

<!--content-->
<div class="container" style="margin-top:90px;">
	
	<!--haokun modified 2017/02/24-->
	<div class="row bg">
		<div class="upload-bg hide">
			<a href="javascript:void(0);"> 
				<i class="glyphicon glyphicon-upload"></i> 
				<input type="file" name="user_cover_img"  id="user_cover_img" class="inputfile_cover" />  <!--haokun modified 2017/02/24-->
				<label for="user_cover_img">修改封面图</label>  <!--haokun modified 2017/02/24-->
			</a>				
		</div>
	</div>

	<div class="row pic-info">
		<div class="left">
			<img class="img-circle user-pic" src="">
		</div>
		<div class="right">
			<div class="username">
				<span></span>
			</div>
			<div class="pro hide">
				<a href="javascript:;" class="tooltip-test" data-toggle="tooltip"
					title="PRO：大牛级别人物">Pro</a>
			</div>
			<div class="brief">
				<span></span>
			</div>
		</div>
		<div class="follow" data-follow="0">
			<i class="glyphicon glyphicon-plus"></i> <span>关注</span>
		</div>
	</div>
	<div class="row">
		<!-- BEGIN PROFILE SIDEBAR -->
		<div class="profile-sidebar">
			<div class="level-info">
				<div class="left">
					<div class="level">
						<span>Lv.0</span>
					</div>
					<div class="desc">
						<span>等级</span>
					</div>
				</div>
				<div class="vertical-line"></div>
				<div class="middle">
					<div class="finish-sum-amount">
						<span>0</span>
					</div>
					<div class="desc">
						<span>已赚金额</span>
					</div>
				</div>
				<div class="vertical-line"></div>
				<div class="right">
					<div class="finish-count">
						<span>0</span>
					</div>
					<div class="desc">
						<span>完成任务数</span>
					</div>
				</div>
			</div>

			<div class="base-info">
				<div class="title">
					<span>基本信息</span>
				</div>
				<div class="line"></div>
				<div class="row margin-top-20">
					<div class="left">
						<span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
					</div>
					<div class="right">
						<span class="sex"></span>
					</div>
				</div>
				<div class="row margin-top-20">
					<div class="left">
						<span>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</span>
					</div>
					<div class="right">
						<span class="occupation"></span>
					</div>
				</div>
				<div class="row margin-top-20">
					<div class="left">
						<span>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</span>
					</div>
					<div class="right">
						<span class="degree"></span>
					</div>
				</div>
				<div class="row margin-top-20">
					<div class="left">
						<span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区：</span>
					</div>
					<div class="right">
						<span class="country"></span>
					</div>
				</div>

				<div class="row margin-top-20">
					<div class="left">
						<span>综合评级：</span>
					</div>
					<div class="right">
						<span class="grade"></span>
					</div>
				</div>
			</div>

			<% 
				String profileUserId = request.getParameter("userId");
				String prpfileMyId = (String) session.getAttribute("user_id_session_key");
				if (prpfileMyId != null && !prpfileMyId.equals("")) {
					prpfileMyId = Base64Util.encode(prpfileMyId);
				}
				if(prpfileMyId==null||!prpfileMyId.equals(profileUserId)){
				
			 %>
			<div class="message-private">
				<div class="title">
					<span>私信</span>
				</div>
				<div class="line"></div>
				<div class="row margin-top-20">
					<textarea class="form-control message-content" rows="6"
						placeholder="私信内容..."></textarea>
				</div>
				<div class="row margin-top-20">
					<a href="/dashboard/Account/messages.jsp?name=&userId=${param.userId }#tab_1_3" class="to-dialg hide"><i class="iconfont icon-liuyan"></i><span>发起对话</span></a>
					<button class="submit-btn" data-friendid="${param.userId }">发送</button>
				</div>
			</div>
			<% 
				}
			 %>
			
			<div class="row user-specialty">
			 	<div class="title">
					<span>技能</span>
				</div>
				<div class="line"></div>
                <div class="row specialty-list">
                </div>
           	</div>
		</div>
		<!-- END BEGIN PROFILE SIDEBAR -->
		<!-- BEGIN PROFILE CONTENT -->
		<div class="profile-content">
			<div class="row">
				<div class="col-md-12">
					<div class="profile-menu">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1_1" data-toggle="tab">用户信息</a>
							</li>
							<li><a href="#tab_1_5" data-toggle="tab">作品展示</a></li>
							<li><a href="#tab_1_2" data-toggle="tab">解决的任务</a></li>
							<li><a href="#tab_1_3" data-toggle="tab">发布的任务</a></li>
							<li><a href="#tab_1_4" data-toggle="tab">留言板</a></li>
						</ul>
					</div>

					<div class="portlet light bordered">
						<div class="portlet-body">
							<div class="tab-content">
								<!-- 基本信息 TAB start -->
								<div class="tab-pane active" id="tab_1_1">
									<!-- 教育经历 start -->
									<div class="form-group">
										<label class="control-label">教育经历</label>
										<div id="education-list"></div>
									</div>
									<!-- 教育经历 end -->
									<!-- 工作经验 start -->
									<div class="form-group">
										<label class="control-label">工作经验</label>
										<div id="work-experience-list"></div>
									</div>
									<!-- 工作经验 end -->
									
									<!-- 技能 start -->
									<div class="special-list row hide">
										<label class="control-label">已认证的技能</label>
										<table class="table table-striped table-bordered table-hover table-checkable order-column">
			                                <thead>
			                                    <tr>
			                                        <th width="100">技能 </th>
			                                        <th width="100">分类 </th>
			                                        <th>认证说明</th>
			                                    </tr>
			                                </thead>
			                                <tbody>
			                                </tbody>
			                                
			                            </table>
									</div>
								</div>
								<!-- 技能 end -->
								
								<!-- 基本信息 TAB end -->
								<!-- 已解决任务 TAB start -->
								<div class="tab-pane" id="tab_1_2">
									<div class="task-content row"></div>
									<div class="search-pagination">
										<ul class="pagination" id="pageSplit-task">

										</ul>
									</div>
								</div>
								<!-- 已解决任务 TAB end -->
								<!-- 已发布任务 TAB start -->
								<div class="tab-pane" id="tab_1_3">
									<div class="task-pub-content row"></div>
									<div class="search-pagination">
										<ul class="pagination" id="pageSplit-task-pub">

										</ul>
									</div>
								</div>
								<!-- 已发布任务 TAB end-->

								<!-- 留言板 TAB start -->
								<div class="tab-pane" id="tab_1_4">
									<div class="blog-comments" style="display:block;">
										<span class="title">发表您的留言</span>
										<form action="#" method="post" class="user-comment-form"
											data-userid="${param.userId }">
											<div class="form-group">
												<textarea rows="4" name="message"
													placeholder="输入您的留言,2-1000个字 ..."
													class="form-control c-square radius0 content"></textarea>
											</div>
											<div class="form-group">
												<button type="submit" class="btn radius0 btn-submit-comment">留言</button>
											</div>
										</form>
										<div class="c-comment-list" id="user-comment-list"></div>
										<div class="search-pagination">
											<ul class="pagination" id="pageSplit-user-comment">

											</ul>
										</div>

									</div>
								</div>
								<!-- 留言板 TAB end -->

								<!-- 作品展示 TAB start -->
								<div class="tab-pane" id="tab_1_5">
									<div class="works row"></div>
								</div>
								<!-- 作品展示 TAB end-->

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END PROFILE CONTENT -->
	</div>
</div>

<script type="html/x-jsrender" id="user-comment-reply-tmpl">
<div class="reply-item">
	<div class="div-left">
		<a class="user-pic" data-id='${user_id_session_key}'>
			<img class="img-circle" src="${user_pic_url_session_key}">
		</a>
	</div>
	<div class="div-right">
		<div class="row">
			<a class="user-pic username" data-id='${user_id_session_key}'>${user_username_session_key}</a>
			<span class="reply-create-date">{{:createDate}}</span>
		</div>
		<span class="content-text">{{:content}}</span> 
	</div>
</div>
</script>
	
<script type="html/x-jsrender" id="user-comment-item-tmpl">
<div class="user-comment-item">
	<div class="div-left">
		<a class="user-pic" data-id='${user_id_session_key}'>
			<img class="img-circle" src="${user_pic_url_session_key}">
		</a>
    </div>
	<div class="div-right">
		<div class="row">
			<a class="user-pic username" data-id='${user_id_session_key}'>${user_username_session_key}</a>
			<span class="reply-create-date">{{:createDate}}</span>
		</div>
		<div class="row">
			<span class="content-text">{{:content}}</span>
		</div>
		<div class="reply-list">
		</div> 
	</div>
	
	<div class="row reply-row">
		<a href="javascript:void(0);" class="reply">回复</a>
		<div class="reply-content" data-userid="{{:userId}}" data-parentid="{{:id}}">
			
		</div>
	</div> 
	
</div>
</script>

<script type="html/x-jsrender" id="reply-tmpl">
	<form action="#" method="post" class="user-comment-reply-form">
		<div class="form-group">
			<textarea rows="4" name="message" placeholder="输入您的留言,2-1000个字 ..."
				class="form-control c-square radius0 content"></textarea>
		</div>
		<div class="form-group">
			<button type="submit" class="btn radius0 btn-reply">留言</button>
		</div>
	</form>
</script>

<script type="html/x-jsrender" id="user-comment-tmpl">
{{for dataList}}
<div class="user-comment-item">
	<div class="div-left">
		<a class="user-pic" data-id='{{:senderId}}'>
			<img class="img-circle" src="{{:senderPicUrl}}" >
		</a>
    </div>
	<div class="div-right">
		<div class="row">
			<a class="user-pic username" data-id='{{:senderId}}'>{{:senderNickName}}</a>
			<span class="reply-create-date">{{:createDate}}</span>
		</div>
		<div class="row">
			<span class="content-text">{{:content}}</span>
		</div>
		<div class="reply-list">
			{{for replyMessages}}
			<div class="reply-item">
				<div class="div-left">
					<img class="img-circle user-pic" alt="" src="{{:senderPicUrl}}" data-id='{{:senderId}}'>
				</div>
				<div class="div-right">
	  				<div class="row">
						<a class="user-pic username" data-id='{{:senderId}}'>{{:senderNickName}}</a>
						<span class="reply-create-date">{{:createDate}}</span>
					</div>
					<span class="content-text">{{:content}}</span> 
	  			</div>
			</div>
			{{/for}}
		</div>
	</div>

	<div class="row reply-row">
		<a href="javascript:void(0);" class="reply" >回复</a>
		<div class="reply-content" data-userid="{{:userId}}" data-parentid="{{:id}}">
			
		</div>
	</div>
</div>

{{/for}}
</script>

<script type="html/x-jsrender" id="task-pub-item-tmpl">
{{if dataList}}
{{for dataList}}
<div class="content-1 row">
	<div class="row">
		<div class="col-xs-7 col-sm-7 col-dm-7 col-lg-7">
			<div class="content-title">
				<a href="/task/detail.jsp?id={{base64Encode id/}}" target="_blank">{{substrOfTitle title/}}</a>
			</div>
		</div>
		<div class="col-xs-1 col-sm-1 col-dm-1 col-lg-1">
			{{typeFormat type/}}
		</div>
		<div class="col-xs-4 col-sm-4 col-dm-4 col-lg-4">
			{{tagFormat tag/}}
		</div>
	</div>
	<div class="row">
		<div class="content-text col-xs-10 col-sm-10 col-dm-10 col-lg-10">
		{{substrOfContent content/}}
		</div>
		<div class="content-money col-xs-2 col-sm-2 col-dm-2 col-lg-2">$ {{:amount}}</div>
	</div>
	
	<div class="row">
		<div class="content-bidding">状态（{{status state/}}）</div>
			
			<!-- <div class="content-bidding">中标数 （{{tonedFormat  receiveDemands /}}）</div>
			<div>
				<img src="/assets/pages/img/index/bidding.png">
			</div> -->
			<div class="content-comment">投标人数 （{{:receiveDemands.length}}）</div>
			<div class="content-message"><a href="/task/detail.jsp?id={{base64Encode id/}}&view=tab_15_1#tab_15_1" target="_blank">留言数（{{:messageCount}}）</a></div>
												
	</div>
	{{for receiveDemands}}
		{{if state==1}}
			{{if evaluateReceiver}}
			<div class="row margin-top-10">
				<div class="evaluate-score">
					<span>评价:</span>{{evaluateScoreFormat evaluateReceiver.evaluate/}}
				</div>
				<div class="evaluate-desc">
					<a href="/task/detail.jsp?id={{base64Encode evaluateReceiver.demandId/}}">{{:evaluateReceiver.description}}</a>
				</div>
			</div>
			{{else}}
			<div class="row">
				<div class="evaluate-score">
					评价:暂未评价
				</div>
			</div>
			{{/if}}
		{{/if}}

		{{if rulePercent}}
			<div class="row margin-top-10">
				<div class="rule-percent">
					<span>裁决付款比例:{{:rulePercent}}%</span>
				</div>
				<div class="rule-reason">
					<a href="/task/detail.jsp?id={{base64Encode demandId/}}"><span>裁决理由:{{:ruleReason}}</span></a>
				</div>
			</div>
		{{/if}}

	{{/for}}
	<div class="divider"></div>
</div>
{{/for}}
{{else}}
<div class='no-data'>该人物还没发布过任务... </div>
{{/if}}
</script>

<script type="html/x-jsrender" id="task-item-tmpl">
{{if dataList}}
{{for dataList}}
<div class="content-1 row">
	<div class="row">
		<div class="col-xs-7 col-sm-7 col-dm-7 col-lg-7">
			<div class="content-title">
				<a href="/task/detail.jsp?id={{base64Encode id/}}" target="_blank">{{substrOfTitle title/}}</a>
			</div>
		</div>
		<div class="col-xs-1 col-sm-1 col-dm-1 col-lg-1">
			{{typeFormat type/}}
		</div>
		<div class="col-xs-4 col-sm-4 col-dm-4 col-lg-4">
			{{tagFormat tag/}}
		</div>
	</div>
	<div class="row">
		<div class="content-text col-xs-10 col-sm-10 col-dm-10 col-lg-10">
			{{substrOfContent content/}}
		</div>
		<div class="content-money col-xs-2 col-sm-2 col-dm-2 col-lg-2">$ {{:amount}}</div>
	</div>
	{{for receiveDemands}}
		{{if state==1}}
			{{if evaluate}}
			<div class="row">
				<div class="evaluate-score">
					<span>评价:</span>{{evaluateScoreFormat evaluate.evaluate evaluate.quality evaluate.punctual/}}
				</div>
				<div class="evaluate-desc">
					<a href="/task/detail.jsp?id={{base64Encode evaluate.demandId/}}">{{:evaluate.description}}</a>
				</div>
			</div>
			{{else}}
			<div class="row">
				<div class="evaluate-score">
					评价:暂未评价
				</div>
			</div>
			{{/if}}
		{{/if}}
		
		{{if rulePercent}}
			<div class="row margin-top-10">
				<div class="rule-percent">
					<span>裁决付款比例:{{:rulePercent}}%</span>
				</div>
				<div class="rule-reason">
					<a href="/task/detail.jsp?id={{base64Encode demandId/}}"><span>裁决理由:{{:ruleReason}}</span></a>
				</div>
			</div>
		{{/if}}
	{{/for}}
	<div class="divider"></div>
</div> 
{{/for}}
{{else}}
<div class='no-data'>该人物还没解决过任务...</div>
{{/if}}

</script>

<script type="html/x-jsrender" id="famous-people-tmpl">
{{for dataList}}
	<div class="famous-people-content">
		<div class="famous-people-img">
			<a href="#">
				<img src="{{:picUrl}}" style="height: 54px;">
			</a>
		</div>
		<div class="famous-people-name">
			<a href="#">{{:nickName}}</a>
		</div>
		<div class="famous-people-level">
			<a href="#" class="tooltip-test" data-toggle="tooltip" title="PRO：专家级别人物">PRO</a>
		</div>
		<div class="famous-people-major">
			<a href="#">{{:occupation}}</a>
		</div>
		<div style="clear: right;"></div>
			<div class="famous-people-brief">{{:brief}}</div>
		</div>
{{/for}}								
</script>


<script id="works-items-tmpl" type="html/x-jsrender">
{{if dataList.length>0}}
	<!--{{for dataList}}
	 <div class="work-item">
		<a target = "_blank" href="{{:url}}">{{:title}}</a>
		
		{{if userId === "${user_id_session_key}"}}
		<a href="javascript:;" class="delete" data-id="{{:id}}">
			<i class="glyphicon glyphicon-trash"></i>
		</a>
		{{/if}}
	</div>
	{{/for}}-->
	{{for dataList}}
	 <div class="work-item">
		<div class="file-extension row">  
			<a target="_blank" href="{{:url}}"><img src="{{fileFormat url/}}"></a>  <!--haokun modified 2017/02/21 增加了href-->
		</div>
		<div class="line"></div>
		<div class="title row">
			<a target = "_blank" href="{{:url}}"><span>{{:title}}</span></a> 
		</div>
		
	</div>
	{{/for}}
{{else}}
	<div class="no-data">
		暂无上传作品{{if userId === "${user_id_session_key}"}} ,现在<a href="/dashboard/Account/setting.jsp#works">去上传</a>{{/if}}
	</div>
{{/if}}
</script>

<script id="education-items-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="education-item">
	<p>
		<span style="margin-left: 20px;">{{:startDate}}~
		{{if endDate==""}}
			至今
		{{else}}
			{{:endDate}}
		{{/if}}
		</span>
		<span style="margin-left: 20px;">{{:school}}</span>
		<span style="margin-left: 20px;">{{:major}}</span>
		<span style="margin-left: 20px;">{{education education/}}</span>
	</p>
	<p><pre class="major-desc">{{:majorDesc}}</pre></p>
	{{if enclosure!=""&& enclosure!=null}}
	<p style="padding-left: 20px;"><a target="_blank" href="{{:enclosure}}">{{:enclosureName}}</a></p>
	{{/if}}
	<hr>
</div>
{{/for}}
{{if dataList.length==0}}
<div class="no-data">
	暂无填写教育经历{{if userId === "${user_id_session_key}"}} ,现在<a href="/dashboard/Account/setting.jsp#education-list">去添加</a>{{/if}}
</div>
{{/if}}
</script>

<!-- work experience -->
<script id="work-experience-items-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="work-experience-item">
	<p>
		<span style="margin-left: 20px;">{{:startDate}}~
		{{if endDate==""}}
			至今
		{{else}}
			{{:endDate}}
		{{/if}}
		</span>
		<span style="margin-left: 20px;">{{:company}}</span>
		<span style="margin-left: 20px;">{{:jobTitle}}</span>
	</p>
	<p><pre class="job-desc">{{:jobDesc}}</pre></p>
	{{if enclosure!=""&& enclosure!=null}}	
	<p style="padding-left: 20px;"><a target="_blank" href="{{:enclosure}}">{{:enclosureName}}</a></p>
	{{/if}}
	<hr>
</div>
{{/for}}
{{if dataList.length==0}}
<div class="no-data">
	暂无填写工作经验{{if userId === "${user_id_session_key}"}} ,现在<a href="/dashboard/Account/setting.jsp#work-experience-list">去添加</a>{{/if}}
<div>
{{/if}}
</script>


<!-- 已认证技能 -->
<script id="special-item-tmpl" type="html/x-jsrender">
{{for dataList}}
	{{if state==2}}
	<tr class='odd gradeX'>
		<td>
			{{if type==0}}
				{{:specialty.name/}}
			{{else type==1}}
				{{:name}}
			{{/if}}
		</td>
    	<td>
			{{if type==0}}
				{{specialtyTypeFormat specialtyTypeId/}}
			{{else type==1}}
				自定义
			{{/if}}
		</td>
		<td>
			<a data-remark="{{:remark}}" href="javascript:;" class="show-remark"><span>查看点评</span></a>
		</td>
	</tr>
	{{/if}}

{{/for}}
</script>

<!-- 所有技能 -->
<script id="special-all-item-tmpl" type="html/x-jsrender">
{{for dataList}}
	{{if type == 0}}
		{{if specialty}}
			<span class="special-label {{if #parent.parent.data.state == 2}} show-special-remark{{/if}}" title="{{:specialty.name}}" data-remark="{{:remark}}" data-state="{{:state}}">
    			<em></em>
     			<b>{{:specialty.name}}</b>
				{{if #parent.parent.data.state == 2}}
				<i class="glyphicon glyphicon-saved" title="认证通过"></i>
             	{{/if}}
             </span>
		{{/if}}
	{{else type == 1}}
		<span class="special-label {{if state == 2}} show-special-remark{{/if}}" title="{{:name}}" data-remark="{{:remark}}"  data-state="{{:state}}">
     		<em></em>
     		<b>{{:name}}</b>
     		{{if state == 2}}
			<i class="glyphicon glyphicon-saved" title="认证通过"></i>
            {{/if}}
     	</span>
	{{/if}}
	
{{/for}}
{{if dataList.length==0}}
	<span class="special-label"title="暂无技能...">
		<em></em>
		<b>暂无技能...</b>
	</span>
{{/if}}
</script>

	<%@ include file="/include/footer.jsp"%>
	<script src="/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script><!--haokun added 2017/02/23-->
    <script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script><!--haokun added 2017/02/23-->
    <script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js " type="text/javascript"></script><!--haokun added 2017/02/23-->
    <script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script><!--haokun added 2017/02/23-->
	<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
   	<script src="/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        
	<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
	<script src="/assets/pages/scripts/user-profile.js" type="text/javascript"></script>