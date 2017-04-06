<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/finance-checkout-request.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
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
								<i class=" icon-list"></i> <span
									class="caption-subject uppercase"> 取现申请</span>
							</div>
						</div>
						<div class="portlet-body form">
							<!-- BEGIN FORM-->
							<form action="#" class="form-horizontal" id="checkout-request">
								<div class="form-body">
									<div class="row margin-top-10">
	                                	<div class="col-md-3 col-sm-3 col-xs-12">
	                                  	</div>
	                                 	<div class="col-md-6 col-sm-6 col-xs-12 desc">
	                                 	<!--haokun add 2017/02/09 怎加了x号-->
                                			<span>您还没认证手机和邮箱,无法申请取现,请</span><span><b><a style="font-size: 16;margin-left: 5px;" href="/dashboard/Account/setting.jsp">认证</a></b></span>
	                                 	</div>
	                              	</div>
									<div class="row margin-top-10">
	                                	<div class="col-md-4 col-sm-4 col-xs-4">
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-5 alert alert-danger display-hide">
	                                    	<!-- <button class="close" data-close="alert"></button> -->
                                			<span> 请输入正确收款帐号、收款人名字、金额、验证码</span>
	                                 	</div>
	                              	</div>
	                              	
									
									 <div class="row margin-top-10">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">账号类型：</span>
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-12">
	                                    	<select name="type" class="form-control radius0">
												<option value="1">美国银行卡</option>
												<option value="2">Paypal</option>  
											</select>
	                                 	</div>
	                              	</div>
	                              	
	                              	<div class="row margin-top-30">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">Payee name：</span>
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-12">
	                                 		<input type="text" class="form-control radius0" name="name" placeholder="Payee name"/>
	                                 	</div>
	                              	</div>
	                              	
	                              	<div class="row margin-top-30 bnk-type-div">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">Payee bnk account type：</span>
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-12">
	                                 		<div class="bnk-type">
												<input type="radio" name="bnkType" value="0" checked>
												Saving 
											</div>
											<div class="bnk-type">
												<input type="radio" name="bnkType" value="1">
												Checking 
											</div>
	                                 	</div>
	                              	</div>
	                              	
	                              	<div class="row margin-top-30">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">Payee account number：</span>
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-12">
	                                 		<input type="text" class="form-control radius0" name="account" placeholder="account number"/>
	                                 	</div>
	                              	</div>
	                              	
	                              	<div class="row margin-top-30 routing-number">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">payee bnk routing number：</span>
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-12">
	                                 		<input type="text" class="form-control radius0" name="routingNumber" placeholder="routing number"/>
	                                 	</div>
	                              	</div>
	                              	
	                              <div class="row margin-top-30 account-type-div">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">Personal or business account：</span>
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-12">
	                                 		<div class="account-type">
												<input type="radio" name="accountType" value="0" checked>
												Personal
											</div>
											<div class="account-type">
												<input type="radio" name="accountType" value="1">
												Business 
											</div>
	                                 	</div>
	                              	</div>
	                              	
	                              	<div class="row margin-top-30">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">取现金额：</span>
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-6">
	                                 		<input type="number" class="form-control radius0" name="amount" maxlength="10" min="100" step="1.00" placeholder="请输入取现金额,最低100美金"/>
	                                 	</div>
	                               		<div class="col-md-3 col-sm-3 col-xs-6"> 
	                               			<span>可用余额：</span><span class="usd font-red-flamingo" data-usd="0">$0</span>
	                               		</div>
	                              	</div>
	                              	<div class="row margin-top-30">
	                                	<div class="col-md-4 col-sm-4 col-xs-12">
	                                  		<span class="left">手机验证码：</span>
	                                  	</div>
	                                 	<div class="col-md-2 col-sm-2 col-xs-6">
	                                 		<input type="text" class="form-control radius0" name="code" maxlength="6" placeholder="请输入验证码"/>
	                                 	</div>
	                                 	<div class="col-md-6 col-sm-6 col-xs-6">
	                                 		<a class="btn btn-success get-verify-code"  style="line-height: 20px;border-radius: 0;width:100px" data-enable="1">获取验证码</a>
	                                 	</div>
	                              	</div>
	                              	
	                              	<div class="row margin-top-30">
	                                	<div class="col-md-4 col-sm-4 col-xs-0">
	                                  	</div>
	                                 	<div class="col-md-5 col-sm-5 col-xs-5">
	                                 		<button type="submit" class="btn btn-0088ff">提交</button>
	                                 	</div>
	                              	</div>
								</div>
							</form>
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

<%@ include file="/include/footer.jsp"%>
<!-- BEGIN GLOBAL LEVEL PLUGINS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script src="/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- <script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script> -->
<script src="/assets/pages/scripts/finance-checkout-request.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
