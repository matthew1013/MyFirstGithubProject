<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/finance-checkout-records.css" rel="stylesheet" type="text/css" />
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
                                <span class="caption-subject uppercase"> 取现记录</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <table class="table table-striped table-bordered table-hover table-checkable order-column" id="sample_1">
                                <thead>
                                    <tr>
                                    	<th> 发票号</th>
                                        <th> 帐号类型</th>
                                        <th> 名字</th>
                                        <th> 帐号类型</th>
                                        <th> 帐号</th>
                                        <th> 收款人类别 </th>
                                        <th> 汇款路线号码</th>
                                        <th> 金额 </th>
                                        <th> 申请日期 </th>
                                        <th> 处理日期 </th>
                                        <th> 状态 </th>
                                        <th> 备注 </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
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

	<script id="items-tmpl" type="html/x-jsrender">
{{for dataList}}
<tr class='odd gradeX item'>
	<td>{{:invoiceNo/}}</td>
	<td>{{typeFormat type/}}</td>
	<td>{{:name}}</td>
	<td>
		{{if bnkType==0}}
			储蓄账号
		{{else bnkType==1}}
			支票账号
		{{/if}}
	</td>
	<td>{{:account}}</td>
	<td>
		{{if accountType==0}}
			个人
		{{else accountType==1}}
			商业
		{{/if}}
	</td>
	<td>{{:routingNumber}}</td>
    <td>{{:amount}}</td>
    <td class='center'>{{:createDate}}</td>
	<td class='center'>{{:modifyDate}}</td>
    <td> {{status state /}} </td>
	<td>{{:remark}}</td>
</tr>
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
<script src="/assets/pages/scripts/finance-checkout-records.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
