<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<%@ include file="/include/header.jsp"%>
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/assets/pages/css/new-detail.css" />
<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/header-video.css" />
	
<div class="container">  
	<div class="row">
		<!-- 新闻 start -->
		<div class="col-md-8 col-lg-8 margin-left-right-10">
			<div class="new-detail row" data-id="${param.id}">
				
			</div>
			
			<div class="new-comment">
				<span class="title">发表您的评论</span>
				<form action="#" method="post" class="comment-form" data-newid="${param.id }">
					<div class="form-group">
						<textarea rows="4" name="comment" placeholder="登录,输入您的评论,2-1000个字 ..." class="form-control c-square radius0 content"></textarea>
					</div>
					<div class="form-group">
						<button type="submit" class="btn radius0 btn-submit-comment">评论</button>
					</div>
				</form>
				<div class="comment-list">
				</div>
				<div class="search-pagination">
					<ul class="pagination">

					</ul>
				</div>

			</div>
		</div>
		<!-- 新闻 end -->

		<!-- 新闻右边 start -->
		<div class="col-md-4 col-lg-4 margin-left-right-10">
			<div>
				<!-- 活动 start -->
				<div class="activity row">
				</div>
				<!-- 活动 end -->

				<!-- 新闻 start -->
				<div class="news row">
					<div class="news-header">
						<div class="right-title">咨询分享</div>
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
		<!-- 新闻右边 end -->
	</div>
</div>

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


<script type="html/x-jsrender" id="activity-tmpl">
<div id="myCarousel" class="carousel slide">
	<!-- 轮播（Carousel）指标 -->
	<ol class="carousel-indicators">
		{{for dataList}}
			<li data-target="#myCarousel" data-slide-to="{{:#index}}" class="{{if #index==0}}active{{/if}}"></li>
		{{/for}}
	</ol>   
	<!-- 轮播（Carousel）项目 -->
	<div class="carousel-inner">
		{{for dataList}}
		<div class="item {{if #index==0}}active{{/if}}" data-title="{{:title}}" data-time="{{datetimeFormat createDate/}}">
			<a href="/activity-detail.jsp?id={{:id}}" target= "_blank"><img src="{{:coverImg}}" alt="First slide"></a>
		</div>
		{{/for}}

	</div>
	<!-- 轮播（Carousel）导航 -->
	<a class="carousel-control left" href="#myCarousel" 
	   data-slide="prev">&lsaquo;</a>
	<a class="carousel-control right" href="#myCarousel" 
	   data-slide="next">&rsaquo;</a>
</div> 
{{if dataList}}
<div class="row title">
	<a href="/activity-detail.jsp?id={{:dataList[0].id}}" target= "_blank"><span>{{:dataList[0].title}}</span></a>
</div>
<div class="row time">
	<span>{{datetimeFormat dataList[0].createDate/}}</span>
</div>
{{/if}}
</script>


<script type="html/x-jsrender" id="comment-reply-tmpl">
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
		<span class="content">{{:content}}</span> 
	</div>
</div>
</script>

<script type="html/x-jsrender" id="comment-item-tmpl">
<div class="comment-item">
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
			<span class="content">{{:content}}</span>
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
	<form action="#" method="post" class="comment-reply-form">
		<div class="form-group">
			<textarea rows="4" name="message" placeholder="输入您的评论,2-1000个字 ..."
				class="form-control c-square radius0 content"></textarea>
		</div>
		<div class="form-group">
			<button type="submit" class="btn radius0 btn-reply">评论</button>
		</div>
	</form>
</script>

<script type="html/x-jsrender" id="comment-tmpl">
{{for dataList}}
<div class="comment-item">
	<div class="div-left">
		<a class="user-pic" data-id='{{:userId}}'>
			<img class="img-circle" src="{{:userPicUrl}}" >
		</a>
    </div>
	<div class="div-right">
		<div class="row">
			<a class="user-pic username" data-id='{{:userId}}'>{{:userNickName}}</a>
			<span class="reply-create-date">{{:createDate}}</span>
		</div>
		<div class="row">
			<span class="content">{{:content}}</span>
		</div>
		<div class="reply-list">
			{{for replyComments}}
			<div class="reply-item">
				<div class="div-left">
					<img class="img-circle user-pic" alt="" src="{{:userPicUrl}}" data-id='{{:userId}}'>
				</div>
				<div class="div-right">
	  				<div class="row">
						<a class="user-pic username" data-id='{{:userId}}'>{{:userNickName}}</a>
						<span class="reply-create-date">{{:createDate}}</span>
					</div>
					<span class="content">{{:content}}</span> 
	  			</div>
			</div>
			{{/for}}
		</div>
	</div>

	<div class="row reply-row">
		<a href="javascript:void(0);" class="reply" >回复</a>
		<div class="reply-content" data-parentid="{{:id}}">
			
		</div>
	</div>
</div>

{{/for}}
</script>

<%@ include file="/include/footer.jsp"%>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>

<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
	
<script src="/assets/layouts/global/scripts/header-video.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/new-detail.js" type="text/javascript"></script>
	