<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String picMessage = request.getParameter("picMessage");
   if(picMessage!=null){
	   picMessage = new String(picMessage.getBytes("ISO-8859-1"),"UTF-8");
	}else{
		picMessage = "";
	}
%>
<%@ include file="../inc/header.jsp" %>

<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${pageContext.request.contextPath}/assets/pages/css/profile.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>

<link href="${pageContext.request.contextPath}/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL PLUGINS -->

	<%@ include file="../inc/frontmenu.jsp" %>
<!-- BEGIN HEADER & CONTENT DIVIDER -->

<div class="clearfix"> </div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <%@ include file="../inc/leftsider.jsp" %>
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <!-- BEGIN PAGE BASE CONTENT -->
            <div class="row">
                <div class="col-md-12">
                    <!-- BEGIN PROFILE SIDEBAR -->
                    <div class="profile-sidebar">

                        <div class="portlet light profile-sidebar-portlet bordered">
                            <!-- SIDEBAR USERPIC -->
                            <div class="profile-userpic">
                                <img id="profile-userpic" src="${pageContext.request.contextPath}${user_pic_url_session_key}" class="img-responsive" alt=""> </div>
                            <!-- END SIDEBAR USERPIC -->
                            <!-- SIDEBAR USER TITLE -->
                            <div class="profile-usertitle">
                                <div class="profile-usertitle-name"> ${user_username_session_key} </div>
                                <div class="profile-usertitle-job"> <span id="user-occupation"> </span> </div>
                            </div>
                            <!-- END SIDEBAR USER TITLE -->
                            <!-- SIDEBAR BUTTONS -->
                            <div class="profile-userbuttons">
                                <button type="button" class="btn btn-circle green btn-sm" id="friend-link">关注</button>
                                <button type="button" class="btn btn-circle red btn-sm" id="message-link">消息</button>
                            </div>
                            <!-- END SIDEBAR BUTTONS -->
                            <!-- SIDEBAR MENU -->
                            <div class="profile-usermenu">

                            </div>
                            <!-- END MENU -->
                        </div>

                        <div class="portlet light bordered">
                            <!-- STAT -->
                            <div class="row list-separated profile-stat">
                                <div class="col-md-4 col-sm-4 col-xs-6">
                                    <div class="uppercase profile-stat-title"> <span id="finish-sum-amount"> 0 </span> </div>
                                    <div class="uppercase profile-stat-text"> 已赚金额 </div>
                                </div>
                                <div class="col-md-4 col-sm-4 col-xs-6">
                                    <div class="uppercase profile-stat-title"><span id="finish-count"> 0 </span></div>
                                    <div class="uppercase profile-stat-text"> 完成任务 </div>
                                </div>
                                <div class="col-md-4 col-sm-4 col-xs-6">
                                    <div class="uppercase profile-stat-title"><span id="user-grade"> 1 </span></div>
                                    <div class="uppercase profile-stat-text"> 等级 </div>
                                </div>
                            </div>
                            <!-- END STAT -->
                            <div>
                                <h4 class="profile-desc-title">拥有的技能</h4>
                                <span class="profile-desc-text" id="specialty">
                                </span>
                                <!-- <div class="margin-top-20 profile-desc-link">
                                    <i class="fa fa-globe"></i>
                                    <a href="http://www.keenthemes.com">www.keenthemes.com</a>
                                </div>
                                <div class="margin-top-20 profile-desc-link">
                                    <i class="fa fa-twitter"></i>
                                    <a href="http://www.twitter.com/keenthemes/">@keenthemes</a>
                                </div>
                                <div class="margin-top-20 profile-desc-link">
                                    <i class="fa fa-facebook"></i>
                                    <a href="http://www.facebook.com/keenthemes/">keenthemes</a>
                                </div> -->
                            </div>
                            <div id="works">
                            	<!-- 
                                <h4 class="profile-desc-title">作品展示</h4>
                                <label class="control-label font-red-flamingo">
                                	最大能上传3个附件，支持附件类型：gif、jpeg、png、word、pdf，jpg，bmp，png
                                </label>
                                <div id="works-list">
                                	<div class="margin-top-20 profile-desc-link">
                                		<a href="#">删除</a>
                                    	<i class="fa fa-globe"></i>
                                    	<a href="#">www.keenthemes.com</a>
                                	</div>
                                	<div class="margin-top-20 profile-desc-link">
                                    	<i class="fa fa-twitter"></i>
                                    	<a href="#">@keenthemes</a>
                                	</div>
                                	<div class="margin-top-20 profile-desc-link">
                                    	<i class="fa fa-facebook"></i>
                                    	<a href="#">keenthemes</a>
                                	</div> 
                               	</div>
                               	<div class="margin-top-20">
                               		<span class="btn green fileinput-button">
                                    	<i class="fa fa-plus"></i>
                                        <span> Add files... </span>
                                        <input type="file" name="files[]" data-url="${pageContext.request.contextPath}/works/add">
                                    </span>
                             	</div>
                             	<div class="margin-top-20">
									<div id="progress" class="progress">
										<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
									</div> 
								</div>
                                         --> 
                            </div>
                        </div>

                    </div>
                    <!-- END BEGIN PROFILE SIDEBAR -->
                    <!-- BEGIN PROFILE CONTENT -->
                    <div class="profile-content">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet light bordered">
                                    <div class="portlet-title tabbable-line">
                                        <div class="caption caption-md">
                                            <i class="icon-globe theme-font hide"></i>
                                            <span class="caption-subject font-blue-madison bold uppercase">账户设置</span>
                                        </div>
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab">个人信息</a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab">修改头像</a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3" data-toggle="tab">修改密码</a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="tab-content">
                                            <!-- PERSONAL INFO TAB -->
                                            <div class="tab-pane active" id="tab_1_1">
                                                <form role="form" action="#">
                                                	<div class="form-group">
                                                        <label class="control-label">邮箱</label>
                                                        <input type="text" id="email" placeholder="username@anyonehelps.com" class="form-control" style="width: 50%;"/>
                                                        <a href="javascript:;" class="btn green" id="emailverify">验证</a>
                                                       	<label id="emailflag" style="display:none;">已验证</label>
														&nbsp;&nbsp;
														<label id="remarkcodeemail">请输入验证码:&nbsp;</label>
														<input type="text"name="emailcode"	maxlength="6"  />
														&nbsp;&nbsp;
														<a href="javascript:;" class="btn green" id="emailverifycode">确定验证</a>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">手机</label>
                                                        <input type="text" id="telphone" placeholder="请输入你的手机号码，中国手机如：13800138000 。美国手机如：5551234567" class="form-control" />
                                                        <a href="javascript:;" class="btn green" id="phoneverify">验证</a>
														<label id="phoneflag" style="display:none;">已验证</label>
														&nbsp;&nbsp;
														<label id="remarkcodephone">请输入验证码:&nbsp;</label>
														<input type="text" name="phonecode"	maxlength="6"  />
														&nbsp;&nbsp;
														<a href="javascript:;" class="btn green" id="phoneverifycode">确定验证</a>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">职业</label>
                                                        <input type="text" id="occupation" placeholder="请输入您的职业" class="form-control" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">最高学历</label>
                                                        <select name="degree" class="form-control">
															<option value="0">请选择学历</option>
															<option value="1">高中毕业或其他同等学力(High School diploma or equivalent)</option>
															<option value="2">大专学历(College degree)</option>
															<option value="3">大学本科学历(Undergraduate degree)</option>
															<option value="4">硕士研究生学历(Master's degree)</option>
															<option value="5">博士学位(Doctoral or professional degree)</option>
														</select>	
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">国家</label>
                                                        <select name="nationality" class="form-control">
															<option value="us">美国</option>
															<option value="cn">中国</option>
															<option value="au">澳洲</option>
															<option value="jp">日本</option>
															<option value="eu">欧洲</option>
															<option value="other">其他</option>
														</select>	
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">省份</label>
                                                        <input type="text" id="province" placeholder="请输入中国省市，或美国等国家的州、特区" class="form-control" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">城市</label>
                                                        <input type="text" id="city" placeholder="请输入你所在的城市" class="form-control" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">微信/WeChat</label>
                                                        <input type="text" id="wechat" class="form-control" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">实时联系方式</label>
                                                        <input type="text" id="otherContact" class="form-control" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">简介</label>
                                                        <textarea class="form-control" rows="3" id="brief"></textarea>
                                                    </div>
                                                    
                                                    <div class="form-group">
                                                        <label class="control-label">教育经历</label>
                                                        <div id="education-list">
                                                        	
                                                        </div>
                                                        <div id="education-add">
                                                        	
                                                        </div>
                                                        <div>
															<a href="javascript:;" class="btn green" id="education-link"> 添加教育经历 </a>
														</div>
                                                    </div>
                                                    
                                                    <div class="form-group">
                                                        <label class="control-label">工作经验</label>
                                                        <div id="work-experience-list">
                                                        	
                                                        </div>
                                                        <div id="work-experience-add">
                                                        	
                                                        </div>
                                                        <div>
															<a href="javascript:;" class="btn green" id="work-experience-link"> 添加工作经验 </a>
														</div>
                                                    </div>
                                                   
                                                    <div class="margiv-top-10">
                                                        <a href="javascript:;" class="btn green" id="saveInfo"> 保存 </a>
                                                        <a href="javascript:;" class="btn default"> 取消 </a>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- END PERSONAL INFO TAB -->
                                            <!-- CHANGE AVATAR TAB -->
                                            <div class="tab-pane" id="tab_1_2">
                                                <form action="${pageContext.request.contextPath}/user/pic_upload" id="user-pic-upload" role="form" method="POST" enctype="multipart/form-data">
													<div class="form-group">
														<span><%=picMessage%></span>
													</div>                                                   
                                                    <div class="form-group">
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    	<img style="height: 39px;margin-top: 8px;" src="${pageContext.request.contextPath}/assets/pages/img/avatars/team1.jpg" alt="" />
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="fileinput fileinput-new" data-provides="fileinput">
                                                            <div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
                                                                <img src="${pageContext.request.contextPath}/assets/pages/img/avatars/no-image.png" alt="" />
                                                            </div>
                                                            <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;"> </div>
                                                            <div>
                                                                <span class="btn default btn-file">
                                                                    <span class="fileinput-new"> 选择相片 </span>
                                                                    <span class="fileinput-exists"> 修改 </span>
                                                                    <input type="file" name="picFile">
                                                                </span>
                                                                <a href="javascript:;" class="btn default fileinput-exists" data-dismiss="fileinput"> 移除 </a>
                                                            </div>
														</div>
                                                    </div>
                                                    <div class="margin-top-10">
                                                    	<button type="submit" class="btn green">提交</button>
                                                        <a href="javascript:;" class="btn default"> 取消 </a>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- END CHANGE AVATAR TAB -->
                                            <!-- CHANGE PASSWORD TAB -->
                                            <div class="tab-pane" id="tab_1_3">
                                                <form id="modifyPass" action="#">
                                                    <div class="form-group">
                                                        <label class="control-label">当前密码</label>
                                                        <input type="password" id="old_pass" class="form-control" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">新密码</label>
                                                        <input type="password" id="new_pass_1" class="form-control" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">重复新密码</label>
                                                        <input type="password" id="new_pass_2" class="form-control" />
                                                    </div>
                                                    <div class="margin-top-10">
                                            			<button type="submit" class="btn green">提交</button>
                                            			<button type="reset" class="btn default">重置</button>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- END CHANGE PASSWORD TAB -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- END PROFILE CONTENT -->
                </div>
            </div>


            <!-- END PAGE BASE CONTENT -->

        </div>
        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<script id="works-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="margin-top-20 profile-desc-link">
	<a class="btn green" data-id="{{:worksId}}" name="delete-works">删除</a>
	<i class="fa fa-globe"></i>
	<a href="{{:#parent.parent.data.baseUrl}}/{{:saveFileName}}">{{:fileName}}</a>
</div>
{{/for}}
</script>                                	
<script id="works-items-tmpl" type="html/x-jsrender">
<h4 class="profile-desc-title">作品展示</h4>
<label class="control-label font-red-flamingo">
	最大能上传3个附件，支持附件类型：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
</label>
<div id="works-list">
	{{for dataList}}
	<div class="margin-top-20 profile-desc-link">
		<a class="btn green" data-id="{{:id}}" name="delete-works">删除</a>
		<i class="fa fa-globe"></i>
		<a href="{{:#parent.parent.data.baseUrl}}/{{:url}}">{{substrOfTitle title/}}</a>
	</div>
	{{/for}}
</div>

{{if dataList.length<3}}
<div class="margin-top-20" id="works-add">
	<span class="btn green fileinput-button">
		<i class="fa fa-plus"></i>
		<span> Add files... </span>
		<input type="file" name="files[]" data-url="{{:#parent.data.baseUrl}}/works/add">
	</span>
</div>
<div class="margin-top-20" id="works-progress">
	<div id="progress" class="progress">
		<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
	</div> 
</div>
{{/if}}
</script>
                                	
<script id="education-items-tmpl" type="html/x-jsrender">
{{for dataList}}
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
<p style="padding-left: 20px;">{{:majorDesc}}</p>
<hr/>
{{/for}}
</script>

<script id="education-items" type="html/x-jsrender">
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
<p style="padding-left: 20px;">{{:majorDesc}}</p>
<hr/>
</script>

<script id="education-add-item" type="html/x-jsrender">
<div>
	<div class="row" >
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">开始时间</th>
					<th width="100">结束时间（不填即至今）</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="startDate" placeholder="格式：yyyy-mm">
					</td>
					<td>
						<input type="text" class="form-control" name="endDate" placeholder="格式：yyyy-mm">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">学校</th>
					<th width="100">专业</th>
					<th width="100">学历</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="school">
					</td>
					<td>
						<input type="text" class="form-control" name="major">
					</td>
					<td>
						<select class="form-control" name="education">
							<option value="1">高中毕业或其他同等学历</option>
							<option value="2">大专学历</option>
							<option value="3">大学本科学历</option>
							<option value="4">硕士研究生学历</option>
							<option value="5">博士学位</option>
						</select>	
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
	 	<label class="control-label">专业描述</label>
	    	<textarea class="form-control" rows="3" name="majorDesc"></textarea>
	</div>
	<div class="row" style="width: auto;display: table;margin-left: auto;margin-top: 10px;margin-right: auto;">
		<a href="javascript:;" class="btn green" name="saveEducation"> 保存 </a>
	    <a href="javascript:;" class="btn default" name="cancelEducation"> 取消 </a>
	</div>
</div>
</script>


<!-- work experience -->
<script id="work-experience-items-tmpl" type="html/x-jsrender">
{{for dataList}}
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
<p style="padding-left: 20px;">{{:jobDesc}}</p>
<hr/>
{{/for}}
</script>

<script id="work-experience-items" type="html/x-jsrender">
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
<p style="padding-left: 20px;">{{:jobDesc}}</p>
<hr/>
</script>

<script id="work-experience-add-item" type="html/x-jsrender">
<div>
	<div class="row" >
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">开始时间</th>
					<th width="100">结束时间（不填即至今）</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="startDate" placeholder="格式：yyyy-mm">
					</td>
					<td>
						<input type="text" class="form-control" name="endDate" placeholder="格式：yyyy-mm">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">公司</th>
					<th width="100">职位名称</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="company">
					</td>
					<td>
						<input type="text" class="form-control" name="jobTitle">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
	 	<label class="control-label">工作描述</label>
	    	<textarea class="form-control" rows="3" name="jobDesc"></textarea>
	</div>
	<div class="row" style="width: auto;display: table;margin-left: auto;margin-top: 10px;margin-right: auto;">
		<a href="javascript:;" class="btn green" name="saveWorkExperience"> 保存 </a>
	    <a href="javascript:;" class="btn default" name="cancelWorkExperience"> 取消 </a>
	</div>
</div>
</script>

    <%@ include file="../inc/footer.jsp" %>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/assets/pages/scripts/account-setting.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->