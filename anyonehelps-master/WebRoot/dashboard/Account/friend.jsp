<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/account-friend.css" rel="stylesheet" type="text/css" />
	
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
                                <span class="caption-subject uppercase"> 我关注的人</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <!-- <table class="table table-striped table-bordered table-hover table-checkable order-column">
                                <thead>
                                    <tr>
                                        <th>用户名</th>
                                        <th>邮箱</th>
                                        <th>手机 </th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="friend-list">
                                    
                                </tbody>
                            </table>
                             -->
                            <div class="friend-list row"></div> 
                        </div>
                        <div class="search-pagination">
							<ul class="pagination" id="pageSplit">

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


<script type="html/x-jsrender" id="user-item-tmpl">
{{for dataList}}
{{for friendUser}}
	<div class="user-content row">
		<div class="user-person">
			<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">
				<img class="img-circle" src="{{:picUrl}}">
			</a>
		</div>
		<div class="content">
			<div class="base-info row">
				<div class = "left"> 
					<div class="user-name">
						<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}" title="{{:nickName}}">{{:nickName}}</a>
					</div>
					{{if pro>0}}
					<div class="pro">
						<a href="javascript:;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">Pro</a>
					</div>
					{{/if}}
					<div class="user-location">
						<i class="iconfont icon-map"></i>
						{{countryFormat country/}}
					</div>
					<div class = "user-specialty hidden-xs">
						<ul>
							{{for su}} 
								{{if type==0}}
									<li><a href="javascript:void(0)" data-tag="{{:specialty.name}}"><div class="user-tag">\#{{:specialty.name}}</div></a></li>
								{{else type==1}}
									<li><a href="javascript:void(0)" data-tag="{{:name}}"><div class="user-tag">\#{{:name}}</div></a></li>
								{{/if}}
							{{/for}}
						</ul>
					</div>
				</div>	
				{{if followed=="0"}}
				<div class="right follow dropdown">
					<button type="button" class="btn follow-btn" data-userid="{{:id}}" data-follow="{{:follow}}">取消关注</button>
				</div>
				{{else}}
				<div class="right follow dropdown">
					<button type="button" class="btn dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown">相互关注<span class="caret"></span></button>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
          				<li><a href="javascript:;"  data-userid="{{:id}}" data-follow="{{:follow}}">取消关注</a></li>
     				</ul>
				</div>
				{{/if}}
			</div>		

			<div class = "row" style="overflow: auto;"> 
				<div class="user-brief">{{:brief}}</div>
		
			</div>

			<div class = "row user-desc"> 
				<div class="user-pub-task">
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_3" target="_blank">
						已发布任务数：{{:countPubDemand}}
					</a>
				</div>
				<div class="user-acc-task">
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_2" target="_blank">
						已解决任务数：{{:countFinishRD}}
					</a>
				</div>
				<div class="user-level  show-evaluate" data-userid="{{:id}}" data-evaluatecount="{{:evaluateCount}}" data-evaluatepublishcount="{{:evaluatePublishCount}}" data-honest="{{:honest}}" data-quality="{{:quality}}" data-punctual="{{:punctual}}" data-honestpublish="{{:honestPublish}}" data-align="bottom left">
					综合评级： {{evaluateFormat evaluate evaluateCount evaluatePublish evaluatePublishCount/}}
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_2" target="_blank"><span class="evaluate-count">（{{evaluateCountFormat evaluateCount evaluatePublishCount/}}）</a>
				</div>
				<div class="leave-message">
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_4" target="_blank"><i class="iconfont icon-leave-message"></i>留言（{{:commentCount}}）</a>
					
				</div>
		
			</div>

		</div>
		<div class="divider"></div>
	</div>
{{/for}}	
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
<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- <script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script> -->
<script src="/assets/pages/scripts/account-friend.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
