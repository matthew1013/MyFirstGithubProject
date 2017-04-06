<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/assets/pages/css/user-profile.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/css/components-rounded.css" />
	
<div class="container"  style="margin-top:90px;">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light bordered" style="min-height:300px;">
                <div class="portlet-title">
                   	<div class="caption">
                       	<i class="icon-equalizer font-red-sunglo"></i>
                       	<span class="caption-subject font-red-sunglo bold uppercase">邮箱验证</span>
                	</div>
                        
              	</div>
                <div class="portlet-body form">
                   	<!-- BEGIN FORM-->
                   	<input type="hidden" name="email" value="${param.email }"/>
					<input type="hidden" name="token" value="${param.token }"/>
					<span id="verify-result"></span>
                                                                      点击这里返回<a href="/index.jsp">首页</a>
            	</div>
            </div>
      	</div>
    </div>

</div>
<!-- END CONTAINER -->

<%@ include file="/include/footer.jsp"%>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/email-verify.js" type="text/javascript"></script>
