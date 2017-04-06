<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />  
<link href="/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/reward-bonus-point.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"> </div>
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
                                <span class="caption-subject uppercase">奖励中心</span>
                            </div>
                        </div>
                        
                        <div class="portlet-body">
                        	<!-- BEGIN FORM-->
                            <form action="#" class="form-horizontal" id="bonus-point-search">
                                <div class="form-body">
                                	<div class="row"><span class="search-desc hidden-xs">搜索</span></div>
                                    <div class="row search-item">
                                       	<!-- 
                                       	<div class="col-md-3">
                                            <button type="button" class="btn btn-0088ff" id="nowWeek">本周</button>
                                            <button type="button" class="btn btn-0088ff" id="nowMonth">本月</button>
                                        </div>
                                        <div class="col-md-2">
                                           <input type="text" class="form-control radius0" id="key" placeholder="搜索关键字">
                                        </div>
                                        <div class="col-md-2">
                                           <input type="text" class="form-control radius0" id="sd" placeholder="开始时间格式">
                                        </div>
                                        <div class="col-md-2">
                                            <input type="text" class="form-control radius0" id="ed" placeholder="结束时间格式">
                                        </div>
										
                                        <div class="col-md-2">
                                            <button type="submit" class="btn btn-0088ff">开始搜索</button>
                                        </div>
                                         -->
                                         
                                        <button type="button" class="btn now-week hidden-xs" id="nowWeek">本周</button>
                                        <button type="button" class="btn now-month hidden-xs" id="nowMonth">本月</button>
                                        <input type="text" class="form-control key" id="key" placeholder="搜索关键字">
                                        <input type="text" class="form-control sd hidden-xs" id="sd" placeholder="开始时间格式">
                                        <input type="text" class="form-control ed hidden-xs" id="ed" placeholder="结束时间格式">
                                        <button type="submit" class="btn search-btn">开始搜索</button>
                                    </div>
                            	</div>
                            </form>
                            <div class="bonus-point">
	                            <table class="table table-striped table-bordered table-hover" id="sample_1">
	                                <thead>
	                                    <tr>
	                                        <th>我的邀请人</th>
	                                        <!-- <th>与推荐人关系</th> -->
	                                        <th>奖励金额</th>
	                                        <th>说明 </th>
	                                        <th>获得时间</th>
	                                        <th>操作</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                    
	                                </tbody>
	                                
	                            </table>
	                            
	                            <div class="search-pagination">
									<ul class="pagination" id="pageSplit">
		
									</ul>
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

<script id="items-tmpl" type="html/x-jsrender">
{{for dataList}}
<tr class='odd gradeX item'>
	<td><a class="user-pic" data-id="{{:consumer.id}}">{{:consumer.nickName/}}</td>
    <td>{{:point}}</td>
    <td>{{:remark}}</td>
	<td>{{:createDate}}</td>
	<td><a href="/task/detail.jsp?id={{base64Encode demandId/}}" target="_blank">查看任务</a></td>
</tr>
{{/for}}
</script>

<%@ include file="/include/footer.jsp"%>
<!-- BEGIN GLOBAL LEVEL PLUGINS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- <script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script> -->
<script src="/assets/pages/scripts/reward-bonus-point.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
