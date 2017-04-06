<%@ include file="/admin/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/admin/css/admin-user-list.css" rel="stylesheet" type="text/css" />
	
	
<input type="hidden" value="${param.taskId }" name="taskId"/>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="page-container" style="margin-top:70px;">
    <%@ include file="/admin/include/leftsider.jsp"%>
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
                                <span class="caption-subject uppercase"> 推荐任务给用户</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <!-- 当前查找条件 start -->
							<div class="row search">
								<div class="type">
									<span>分类:</span>
									<select name="type">
										<option value="">所有</option>
										<option value="1">学习类</option>
										<option value="2">生活类</option>
										<option value="3">工作类</option>
									</select>
								</div>
								<div class="location">
									<span>位置:</span>
									<select name="location">
										<option value="">所有</option>
										<option value="us">美国</option>
										<option value="cn">中国</option>
										<option value="uk">英国</option>
										<option value="au">澳洲</option>
										<option value="jp">日本</option>
									</select>
								</div>
								<div class="sort">
									<span>排序:</span>
									<select name="sort">
										<option value="0">默认推荐</option>
										<option value="1">发任务金额高</option>
										<option value="2">接任务金额高</option>
										<option value="3">评分高</option>
										<option value="4">人气高</option>
										<option value="4">等级高</option>
									</select>
								</div>
								
								<div class="key">
									<i class="glyphicon glyphicon-search"></i>
									<input class="search-key" type="text" placeholder="搜索关键字" value="">
									<button class="search-start">搜索</button>
								</div>
								
							</div>
							<!-- 当前查找条件 end -->
                            <div class="user-list row"></div> 
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


<script type="html/x-jsrender" id="user-item-tmpl">
{{for dataList}}
	<div class="user-content row">
		<div class="user-person">
			<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}"><img class="img-circle" src="{{:picUrl}}" style="height: 80px;width: 80px;"></a>
		</div>
		<div>
			<span class="user-grade">LV{{calculateGrade grade/}}</span>
		</div>
		<div>
			<div class = "row" style="height: 34px;overflow: hidden;"> 
				<div class="user-name col-xs-2 col-sm-2 col-dm-2 col-lg-2">
					<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">{{:nickName}}</a>
				</div>

				<div class="user-location col-xs-2 col-sm-2 col-dm-2 col-lg-2">
					<i class="iconfont icon-map"></i>
					{{countryFormat country/}}
				</div>
				<div class = "user-specialty col-xs-6 col-sm-6 col-dm-6 col-lg-6">
					<ul>
						<li><a href="javascript:void(0)" data-tag="实习"><div class="user-tag">#租车</div></a></li>
						<li><a href="javascript:void(0)" data-tag="买车"><div class="user-tag">#买车</div></a></li>
						<li><a href="javascript:void(0)" data-tag="租房"><div class="user-tag">#租房</div></a></li>
						<li><a href="javascript:void(0)" data-tag="导游"><div class="user-tag">#导游</div></a></li>
						<li><a href="javascript:void(0)" data-tag="其他"><div class="user-tag">#其他</div></a></li>
					</ul>
				</div>
				<div class = "col-xs-2 col-sm-2 col-dm-2 col-lg-2">
					<button type="button" class="btn recommend-btn" data-userid="{{:id}}">{{if adminRecommend==1}}再次推荐{{else}}推荐{{/if}}</button>
				</div>
			</div>		

			<div class = "row" style="overflow: auto;"> 
				<div class="user-brief">{{:brief}}</div>
		
			</div>

			<div class = "row user-desc"> 
				<div class="user-pub-task">
					<a href="/profile.jsp?userId={{:id}}#tab_1_3" target="_blank">
						已发布任务数：{{:countPubDemand}}
					</a>
				</div>
				<div class="user-acc-task">
					<a href="/profile.jsp?userId={{:id}}#tab_1_2" target="_blank">
						已解决任务数：{{:countFinishRD}}
					</a>
				</div>
				<div class="user-level show-evaluate" data-userid="{{:id}}" data-evaluatecount="{{:evaluateCount}}" data-evaluatepublishcount="{{:evaluatePublishCount}}" data-honest="{{:honest}}" data-quality="{{:quality}}" data-punctual="{{:punctual}}" data-honestpublish="{{:honestPublish}}" data-align="bottom left">
					综合评级： {{evaluateFormat evaluate evaluateCount evaluatePublish evaluatePublishCount/}}
					<a href="/profile.jsp?userId={{:id}}#tab_1_2" target="_blank"><span class="evaluate-count">（{{evaluateCountFormat evaluateCount evaluatePublishCount/}}）</span></a>
				</div>
				<div class="leave-message">
					<a href="/profile.jsp?userId={{:id}}#tab_1_4" target="_blank">
						<i class="iconfont icon-leave-message"></i>留言（{{:commentCount}}）
					</a>
					
				</div>
		
			</div>

		</div>
		<div class="divider"></div>
	</div>
{{/for}}								
</script>

<%@ include file="/admin/include/footer.jsp"%>
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
<script src="/assets/pages/admin/scripts/admin-user-list.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
