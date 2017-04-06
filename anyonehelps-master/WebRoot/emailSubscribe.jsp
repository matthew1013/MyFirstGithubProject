<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/assets/pages/css/user-profile.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/css/components-rounded.css" />
<style type="text/css">
.tip-dlg{
	position: fixed;
    left: calc(50% - 200px);
    top: 140px;
    border-radius: 0px;
}
</style>

<% 
	String id = (String)session.getAttribute("user_id_session_key");
	String isLogin="0";
	if(id!=null&&!id.equals("")){
		isLogin = "1";
	}
%>
<input type="hidden" name="is_login" value="<%=isLogin%>"/>

<div class="container"  style="margin-top:90px;">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light bordered" style="min-height:300px;">
                <div class="portlet-title">
                   	<div class="caption">
                       	<i class="icon-equalizer font-red-sunglo"></i>
                       	<span class="caption-subject font-red-sunglo bold uppercase">退订广告邮件</span>
                	</div>
              	</div>
              	<div class="portlet-body form">
                    <div class="row">
                        <div class="col-md-offset-3 col-md-6">
                        	<button type="button" class="btn green subscribe-btn" data-subscribe="1">订阅</button>
                           	<button type="button" class="btn default subscribe-btn" data-subscribe="0">退订</button>
                   		</div>
                  	</div>
            	</div>
            </div>
      	</div>
    </div>

</div>
<!-- END CONTAINER -->

<%@ include file="/include/footer.jsp"%>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/email-subscribe.js" type="text/javascript"></script>
