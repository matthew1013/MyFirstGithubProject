<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/task-follow.css" rel="stylesheet" type="text/css" />    
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
                                <i class=" icon-list"></i>
                                <span class="caption-subject uppercase"> 关注的任务</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                        	<div class="task-list row"></div> 
                        </div>
                        <div class="search-pagination">
							<ul class="pagination">

							</ul>
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

<script type="html/x-jsrender" id="task-item-tmpl">
{{for dataList}}
{{for demand}}

<div class="task-item" data-id="{{:id}}"> 
	<div class="row">
			<div class="title {{if open==0}}private{{/if}}">
   				<a href="/task/detail.jsp?id={{base64Encode id/}}" target="_blank">{{:title}}</a>
			</div>
			<div class="amount">
   				<span>
					{{amountFormat amount/}}
				</span>
			</div>
 	</div>

	<div class="row info">
		<span>任务编号：</span><span>{{:id}}</span>
      	<span class="expire-date">截止时间：</span><span>{{:expireDate}}</span>
  	</div>
	<div class="row content">
		{{:content}}
	</div>

	{{if enclosure1}}
	<div class="enclosure row">
		<span>附件:</span>
		<ul>
		{{if enclosure1}}
			<li><a href="{{:enclosure1}}" target="_blank">{{:enclosure1Name}}</a></li>
		{{/if}}
		{{if enclosure2}}
			<li><a href="{{:enclosure2}}" target="_blank">{{:enclosure2Name}}</a></li>
		{{/if}}
		{{if enclosure3}}
			<li><a href="{{:enclosure3}}" target="_blank">{{:enclosure3Name}}</a></li>
		{{/if}}
		{{if enclosure4}}
			<li><a href="{{:enclosure4}}" target="_blank">{{:enclosure4Name}}</a></li>
		{{/if}}
		{{if enclosure5}}
			<li><a href="{{:enclosure5}}" target="_blank">{{:enclosure5Name}}</a></li>
		{{/if}}
		</ul>
	</div>
	{{/if}}

	<div class="line margin-top-10"></div>
	{{if da}}
	<div class="row attach-list">
		{{for da}}
		<div class="attach-item">
			<div class="row attach-info">
				<div class="open">
					<i class="iconfont icon-add"></i>
				</div>
				<div class="attach-title">
					<span>附加任务{{:#index+1}}</span>
				</div>
 				<div class="attach-expire-date">
					<span>截止时间：{{:expireDate}}</span>
				</div>
 				<div class="attach-amount">
					{{amountFormat amount/}}
				</div>
			</div>
			<div class="row content hide">
				{{:content}}
			</div> 
			<div class="row achieve hide margin-top-10">
				<div class="achieve-tip">
					<span>成果要求：</span>
				</div>
				<div>
					<pre>{{:achieve}}</pre>
				</div>
			</div>
		</div> 
		<div class="line margin-top-10"></div>
		{{/for}}
	</div> 
	{{/if}}

	<div class="row specialty">
		<div class="left">
			{{if ds.length > 0}}
			<ul>
				{{for ds}}
				<li><div class="specialty-item">{{:specialty.name}}</div></li>
				{{/for}}
			</ul>
			{{/if}}
		</div>
 	</div>

	<div class="bottom margin-top-20">
		<div class="user">
			{{if receiveDemands.length>0}}
				{{for receiveDemands}}
					{{if #index==0}}
					<span class="username"><a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}">{{:userNickName}}</a></span>
					{{/if}}
					{{if #index==1}}
					<span class="username">、<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}">{{:userNickName}}</a></span>
					{{/if}}
					{{if #index==2}}
					<span class="username">、<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}">{{:userNickName}}</a></span>
					{{/if}}
				{{/for}}
				<span>等{{:receiveDemands.length}}人竞标了此任务</span>
			{{else}}
				<span>暂无人竞标此任务</span>
			{{/if}}
		</div>
		<div class="row operate">
			<button class="btn cancel-follow-btn" data-demandId="{{:id}}">取消关注</button>
			{{if state==0||state==1}}
			<button class="btn show-task-receive" data-demandId="{{:id}}">接受任务</button>
			{{/if}}
		</div>
	</div>

	<div class="row hide receive-demand">
		<div class="line margin-top-10 margin-bottom-10"></div>
		<form action="#" method="post" data-demandid="{{:id}}">
			<div class="form-group">
				<div class="row">
					<div class="col-md-6">
						<span class="receive-amount-desc">我的报价($):</span>
						<input type="number" min="1" name="amount" placeholder="单位($),空则发单价" class="form-control receive-amount">
					</div>
					<div class="col-md-6">
						<span class="finish-day-desc">完成时长(天):</span>
						<input type="number" min="1" name="finishDay" placeholder="单位(天)" class="form-control finish-day">
					</div>
				</div>
			</div>
			<div class="form-group">
				<textarea rows="5" name="receive_remark"  placeholder="自我介绍一下吧！" class="form-control receive-remark"></textarea>
			</div>
			<div class="row">
				<div class="operate">
					<button type="button" class="btn close-task-receive">取消</button>
					<button type="submit" class="btn receive-submit">确认竞标</button>
				</div>
        	</div>
		</form>
	</div>

</div>
{{/for}}	
{{/for}}								
</script>


<script id="tip-dlg" type="html/x-jsrender">
<div class="tip-dlg-content">
	<div class="row title">
		<span>系统提示</span>
	</div>
	<div class="row content">
		<i class="iconfont icon-info-warning"></i>
		<span>温馨提示，您已成功接收任务，请耐心等待匹配结果。</span>
	</div>
	<div class="row operate">
		<button type="button" class="btn">我知道了</button>
	</div>
</div>
</script>

<%@ include file="/include/footer.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>


<script src="/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>

<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/task-follow.js" type="text/javascript"></script>
