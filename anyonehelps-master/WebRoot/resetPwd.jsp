<%@ include file="/include/header.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />

<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->

<div class="page-container" style="margin-top:70px;">
	<!-- BEGIN CONTENT BODY -->
    <div class="page-content">
    	<!-- BEGIN PAGE BASE CONTENT -->
		<div class="row">
			<div class="col-md-12">
				<div class="portlet light bordered">
                	<div class="portlet-title">
                    	<div class="caption">
                        	<i class="icon-equalizer font-red-sunglo"></i>
                            <span class="caption-subject font-red-sunglo bold uppercase">重置密码</span>
                        </div>
                        
              		</div>
                	<div class="portlet-body form">
                   		<!-- BEGIN FORM-->
                     		<form action="#" class="form-horizontal reset-pwd-form">
                                <input type="hidden" name="email" value="${param.email }"/>
								<input type="hidden" name="token" value="${param.token }"/>
				
                                <div class="form-body">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">新密码</label>
                                        <div class="col-md-3">
                                            <input name="newpwd" type="password" class="form-control"/>
                                        </div>
                            		</div>
                            		
                            		<div class="form-group">
                                        <label class="col-md-3 control-label">确认密码</label>
                                        <div class="col-md-3">
                                            <input name="confirmpwd" type="password" class="form-control"/>
                                        </div>
                            		</div>
                                    
            					</div>
            					<div class="form-actions">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-6">
                                            <button type="submit" class="btn green">提交</button>
                                            <button type="reset" class="btn default">重置</button>
                                        </div>
                                    </div>
                                </div>
            					
            				</form>
            		</div>
            	</div>
            </div>
    	</div>
	</div>

</div>
<!-- END CONTAINER -->

<%@ include file="/include/footer.jsp"%>
<!-- BEGIN INDEX PAGE SCRIPTS -->
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/reset-pwd-email.js" type="text/javascript"></script>
<!-- END INDEX PAGE SCRIPTS -->
