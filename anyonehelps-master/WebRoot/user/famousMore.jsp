<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

	<%@ include file="/include/header.jsp"%>
	<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/user-famous-more.css" />
	<% 
		String title = "";
		String keywords = "";
		String description = "";
		Seo s = SeoUtil.getSeoByWebFlag("6");
		if(s != null){
			title = s.getTitle();
			keywords = s.getKeywords();
			description = s.getDescription();
		}
	%>
	<input type="hidden" name="seoTitle" value="<%=title %>">
	<input type="hidden" name="seoKeywords" value="<%=keywords %>">
	<input type="hidden" name="seoDescription" value="<%=description %>">
		
	
	<!--content-->
	<div class="container"> 
	
		<div class="row" style="height:90px"></div>
		
		<div class="row">
			<!-- 人物列表页面 start -->
			<div class="famous col-md-12 col-lg-12">
				<div class="row"><span class="title">名人堂</span></div>
				<div class="type row">
					<ul>
						<li><a href="javascript:void(0)" class="search-type" data-type=""><div class="type-item active">全部</div></a></li>
						<li><a href="javascript:void(0)" class="search-type" data-type="程序员"><div class="type-item">程序员</div></a></li>
						<li><a href="javascript:void(0)" class="search-type" data-type="设计师"><div class="type-item">设计师</div></a></li>
						<li><a href="javascript:void(0)" class="search-type" data-type="教师"><div class="type-item">教师</div></a></li>
						<li><a href="javascript:void(0)" class="search-type" data-type="IT"><div class="type-item">IT</div></a></li>
						<li><a href="javascript:void(0)" class="search-type" data-type="Stage Designer"><div class="type-item">Stage Designer</div></a></li>
						<li><a href="javascript:void(0)" class="search-type" data-type="Designer"><div class="type-item">Designer</div></a></li>
						<li><a href="javascript:void(0)" class="search-type" data-type="导游"><div class="type-item">导游</div></a></li>
						
					</ul>
				</div>
				<div class="divider"></div>
				<div class="famous-list row">
				</div>
				<div class="row more">
					<a href="javasrcipt:void(0);"><div class="load-more">点击加载更多</div></a>
				</div>
			</div>
			<!-- 人物列表页面 end -->
		</div>
	</div>
	
<script type="html/x-jsrender" id="famous-people-tmpl">
{{for dataList}}
	<div class="item col-md-6 col-lg-6">
		<div class="famous-content">
			<div class="left">
				<div class="row ">
					<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">
						<img class="img-circle" src="{{:picUrl}}" style="height: 60px;width:60px;border: 2px solid #ffffff;box-shadow: 0 0 2px 0 rgba(0,0,0,0.30);">
					</a>
				</div>
				<div class="row {{if id === "${user_id_session_key}"}}hide{{/if}}">
					<button type="button" class="user-follow {{if follow=="1"}}{{else}}hide{{/if}}" data-follow="{{:follow}}" data-userid="{{:id}}">取消关注</button>
					<button type="button" class="user-follow {{if follow=="1"}}hide{{else}}{{/if}}"  data-follow="{{:follow}}" data-userid="{{:id}}">关注</button>
				</div>
				
			</div>
			<div class="right">
				<div class="user-name">
					<a href="javascript:void(0)" class="user-pic" data-id="{{:id}}">{{:nickName}}</a>
				</div>
				{{if pro>0}}
				<div class="user-pro">
					<a href="javascript:;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">PRO</a>
				</div>
				{{/if}}
				{{if occupation}}
				<div class="user-major">
					<a href="javascript:;">{{:occupation}}</a>
				</div>
				{{/if}}
				<div class="user-brief">{{:brief}}</div>
			</div>
				<!-- <div style="float:left">
					<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">
						<img class="img-circle" src="{{:picUrl}}" style="height: 60px;width:60px;border: 2px solid #ffffff;box-shadow: 0 0 2px 0 rgba(0,0,0,0.30);">
					</a>
					<span class="show-grade famous-people-grade" data-grade="{{:grade}}" data-align="bottom left" >LV{{calculateGrade grade/}}</span>
				</div> -->
		</div>
	</div>
{{/for}}								
</script>

	<%@ include file="/include/footer.jsp"%>
	<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
	<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="/assets/pages/scripts/user-famous-more.js" type="text/javascript"></script>
	
	