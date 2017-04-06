<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<%@ include file="/include/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/assets/pages/css/new-list.css" />
<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/header-video.css" />
<% 
	String title = "";
	String keywords = "";
	String description = "";
	Seo s = SeoUtil.getSeoByWebFlag("5");
	if(s != null){
		title = s.getTitle();
		keywords = s.getKeywords();
		description = s.getDescription();
	}
%>
<input type="hidden" name="seoTitle" value="<%=title %>">
<input type="hidden" name="seoKeywords" value="<%=keywords %>">
<input type="hidden" name="seoDescription" value="<%=description %>">
		
<div class="container"> 
	<div class="row" style="height:90px"></div>
	
	<div class="row">
		<!-- 新闻列表 start -->
		<div class="col-md-8 col-lg-8 margin-left-right-10">
			<div class="list row">
				
			</div>
			
			<div class="pagination"> 
				<button class="btn page-prev" disabled="disabled" data-page="1">上一页</button>
				<button class="btn pull-right page-next" disabled="disabled" data-page="1">下一页</button>
			</div>
			
		</div>
		<!-- 新闻列表 end -->

		<!-- 新闻列表右边 start -->
		<div class="col-md-4 col-lg-4 margin-left-right-10">
			<div>
				<!-- 当前悬赏金额和任务量 start -->
				<div class="money-task row hidden-xs">
					<div class="money-amount">
						<div class="money-amount-1">
							<i class="iconfont icon-money"></i>
							<span>悬赏金额</span>
						</div>
						<div class="money-amount-2"></div>
					</div>
					<div class="vertical-line"></div>
					<div class="task-amount">
						<div class="task-amount-1">
							<i class="iconfont icon-task-count"></i>
							<span>任务量</span>
						</div>
						<div class="task-amount-2"></div>
					</div>
				</div>
				<!-- 当前悬赏金额和任务量 end -->

				<!-- 新闻 start -->
				<div class="news row">

					<div class="news-header">
						<div class="right-title">资讯分享</div>
						<div class="more">
							<a href="/new/list.jsp" target="_blank">更多</a> 
						</div>
					</div>
					<div class="new-list">
						
					</div>
				</div>
				<!-- 新闻 end -->
			</div>
		</div>
		<!-- 新闻列表右边 end -->
	</div>
</div>


<script id="list-item-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="col-md-6 col-lg-6">
	<div class="item">
		<div class="detail">
			<div class="row">
				<img src="{{:coverImg}}">
				{{if priority>99}}
				<span class="top-flag">置顶</span>
				{{else hotFlag==1}}
				<span class="hot-flag">热门</span>
				{{/if}}
			</div>
			<div class="row title">
				<span>{{:title}}</span>
			</div>
			<div class="row desc">
				<span>{{datetimeFormat createDate/}}</span>
				<span>来源: {{:source}}</span>
				<span>浏览量：{{:visitNum}}</span>
			</div>
			
			<div class="row read-more">
				<a href="/new/detail.jsp?id={{:id}}" class="btn"> 阅读更多 </a>
			</div>
		
		</div>
	</div>
</div>
{{/for}}
</script>

<script id="new-detail-tmpl" type="html/x-jsrender">
<div class="row">
	<span class="title">{{:title}}</span>
</div>
<div class="row desc">
	<span>{{datetimeFormat createDate/}}</span>
	<span>来源: {{:source}}</span>
	<span>浏览量：{{:visitNum}}</span>
</div>
<div class="line row"></div>
<div class="content row">{{:content}}</div>
</script>
<script id="new-item-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="new-item row">
	<div style="">
		<a href="/new/detail.jsp?id={{:id}}"><img class="new-img" src="{{:coverImg}}"></a>
	</div>
	<div class="new-title">
		<a href="/new/detail.jsp?id={{:id}}">{{:title}}</a>
		<div class="new-time row">
			<span>{{datetimeFormat createDate/}}</span>
			{{if hotFlag==1}}
			<span class="hot">HOT</span>
			{{/if}}
		</div>
	</div>
</div>
{{/for}}
</script>
<%@ include file="/include/footer.jsp"%>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
	
<script src="/assets/layouts/global/scripts/header-video.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/new-list.js" type="text/javascript"></script>
	