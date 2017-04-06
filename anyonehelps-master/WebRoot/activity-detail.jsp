<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<%@ include file="/include/header.jsp"%>
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/assets/pages/css/new-detail.css" />
<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/header-video.css" />
<link rel="stylesheet" type="text/css" href="/assets/pages/css/activity-detail.css" />

<div class="container"> 
	<div class="row activity-from activity-from-top hide">
		<div class="col-md-12 col-lg-12 margin-left-right-10">
			<div class="right">
				<div class="enroll">
					<div class="enroll-header row">
						<span>活动报名</span>
					</div>
					<div class="line row"></div>
					<div class="enroll-body row">
						<div class="email">
							<span class="red-star">*</span>
							<input type="text" name="email" class="form-control" placeholder="请输入您的联系邮箱 " value="${user_email_session_key}">
						</div>
						<%
							//已经登录
							if (sessionValue != null && !sessionValue.equals("")) {
						%>
						<div class="phone">
							<span class="red-star"></span>
							<select class="form-control area-code">
								<option value="+1">(+1)美国/加拿大</option>
			                    <option value="+86">(+86)中国</option>
			                    <option value="+44">(+44)英国</option>
			                    <option value="+61">(+61)澳洲</option>
							</select>
							<input type="text" name="telphone" class="form-control" placeholder="联系手机号码 " value="">
						</div>
						<div class="row">
							<span class="red-star"></span>
							<a href="javascript:;" class="btn submit-btn">报名</a>
						</div>
						<%
							}else{
							//未登录
						%>
						<div class="name">
							<span class="red-star">*</span>
							<input type="text" name="name" class="form-control" placeholder="请输入您的注册用户名">
						</div>
						
						<div class="password">
							<span class="red-star">*</span>
							<input type="password" name="password" class="form-control" placeholder="请输入您的注册密码">
						</div>
						<div class="phone">
							<span class="red-star"></span>
							<select class="form-control area-code">
								<option value="+1">(+1)美国/加拿大</option>
			                    <option value="+86">(+86)中国</option>
			                    <option value="+44">(+44)英国</option>
			                    <option value="+61">(+61)澳洲</option>
							</select>
							<input type="text" name="telphone" class="form-control" placeholder="联系手机号码 " value="">
						</div>
						<div class="row">
							<span class="red-star"></span>
							<a href="javascript:;" class="btn submit-btn">报名并注册</a>
						</div>
						<%
							}
						%>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row activity-from">
		
		<div class="col-md-8 col-lg-8 margin-left-right-10">
			<div class="left">
				<!-- 活动信息 start -->
				<div class="col-md-12 col-lg-12">
					<div class="activity-detail row" data-id="${param.id}" data-userid="${user_id_session_key}">
					
					</div>
				</div>
				<!-- 活动信息 end -->
			</div>
		</div>
		<div class="col-md-4 col-lg-4 margin-left-right-10">
		
			<div class="right">
				<div class="enroll">
					<div class="enroll-header row">
						<span>活动报名</span>
					</div>
					<div class="line row"></div>
					<div class="enroll-body row">
						<div class="email">
							<span class="red-star">*</span>
							<input type="text" name="email" class="form-control" placeholder="请输入您的联系邮箱 " value="${user_email_session_key}">
						</div>
						<%
							//已经登录
							if (sessionValue != null && !sessionValue.equals("")) {
						%>
						<div class="phone">
							<span class="red-star"></span>
							<select class="form-control area-code">
								<option value="+1">(+1)美国/加拿大</option>
			                    <option value="+86">(+86)中国</option>
			                    <option value="+44">(+44)英国</option>
			                    <option value="+61">(+61)澳洲</option>
							</select>
							<input type="text" name="telphone" class="form-control" placeholder="联系手机号码 " value="">
						</div>
						<div class="row">
							<span class="red-star"></span>
							<a href="javascript:;" class="btn submit-btn">报名</a>
						</div>
						<%
							}else{
							//未登录
						%>
						<div class="name">
							<span class="red-star">*</span>
							<input type="text" name="name" class="form-control" placeholder="请输入您的注册用户名">
						</div>
						
						<div class="password">
							<span class="red-star">*</span>
							<input type="password" name="password" class="form-control" placeholder="请输入您的注册密码">
						</div>
						<div class="phone">
							<span class="red-star"></span>
							<select class="form-control area-code">
								<option value="+1">(+1)美国/加拿大</option>
			                    <option value="+86">(+86)中国</option>
			                    <option value="+44">(+44)英国</option>
			                    <option value="+61">(+61)澳洲</option>
							</select>
							<input type="text" name="telphone" class="form-control" placeholder="联系手机号码 " value="">
						</div>
						<div class="row">
							<span class="red-star"></span>
							<a href="javascript:;" class="btn submit-btn">报名并注册</a>
						</div>
						<%
							}
						%>
						
					</div>
					
					<!-- 
					<div class="content">
						<span class="red-star">*</span>
						<textarea type="text" name="content" rows="4" placeholder="关于自己…" class="form-control radius0"></textarea>
					</div>
					 -->
				</div>
			</div>
		</div>
	</div>
</div>

<script id="activity-detail-tmpl" type="html/x-jsrender">
<div class="row">
	<span class="title">{{:title}}</span>
</div>
<div class="row desc">
	<span>创建时间:{{datetimeFormat createDate/}}</span>
</div>
<div class="line row"></div>
<div class="content row">{{:content}}</div>
</script>

<script id="tip-dlg" type="html/x-jsrender">
<div class="tip-dlg-content">
	<div class="row title">
		<span>系统提示</span>
	</div>
	<div class="row content">
		<i class="iconfont icon-info-warning"></i>
		<span>恭喜您，{{:content}}！现在带您进入主办方首页。</span>
	</div>
	<div class="row operate">
		<button type="button" class="btn">我知道了</button>
	</div>
</div>
</script>

<%@ include file="/include/footer.jsp"%>
<script src="/assets/pages/scripts/activity-detail.js" type="text/javascript"></script>

<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>

<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
