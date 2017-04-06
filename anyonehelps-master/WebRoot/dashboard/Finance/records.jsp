<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css"/>    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/pages/css/finance-records.css" rel="stylesheet" type="text/css" />

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
                                <span class="caption-subject uppercase"> 财务记录</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                       	 	<!-- 当前查找条件 start -->
							<div class="row search">
							<!--haokun modified 2017/03/02-->
								<div class="row">
									<div class="typeFirst hidden-xs">
										<span>分类:</span>
										<select name="typeFirst" id="typeFirst">
											<option value="0">所有</option>
											<option value="1">收入</option>
											<option value="2">支出</option>
											<option value="3">冻结</option>
										</select>
									</div>
									<div class="type hidden-xs hide">
										<span>明细:</span>
										<select name="type" id="type">
										</select>
									</div>
									<div class="start-date hidden-xs">
										<span>开始时间:</span>
										<input type="text"  placeholder="yyyy-mm-dd">
									</div>
									<div class="end-date hidden-xs">
										<span>结束时间:</span>
										<input type="text"  placeholder="yyyy-mm-dd">
									</div>
								</div>
							    
								<div class="row">
									<div class="demandId">
										<span>任务ID:</span>
										<input type="text" id="demandId" value="${param.taskId }" placeholder="任务ID">
									</div>
									<div class="key">
										<input class="search-key" type="text" placeholder="搜索关键字">
									</div>
									<button class="search-start">搜索</button>
								</div>
								
								
							</div>
							<!-- 当前查找条件 end -->
                            <table class="table table-striped table-bordered table-hover table-checkable order-column" id="sample_1">
                                <thead>
                                    <tr>
                                        <!--haokun modified 2017/03/08 start 对顺序进行调整-->  
                                        <th> 时间 </th>
										<th> 单号 </th>
                                        <th> 明细 </th>                                                                
                                        <th> 描述 </th>
                                        <th> 状态 </th>
										<th> 金额($) </th> 
									  <!--haokun modified 2017/03/08 end 对顺序进行调整--> 
                                    </tr>
                                    
                                </thead>
                                <tbody>
                                    
                                </tbody>
                                
                            </table>
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
<input type="hidden" value="${param.taskId }" name="taskId"/>

<%@ include file="/include/footer.jsp"%>
<!-- BEGIN GLOBAL LEVEL PLUGINS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script src="/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- <script src="${pageContext.request.contextPath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script> -->
<script src="/assets/pages/scripts/finance-records.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
