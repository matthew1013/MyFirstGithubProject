<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>    
<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
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
                	<div class="portlet light bordered">
	                	<div class="portlet-title">
	                    	<div class="caption font-dark">
	                         	<i class=" icon-list"></i>
	                            <span class="caption-subject uppercase">微信扫码充值</span>
	                        </div>
	                	</div>
	                	
	               		<div class="portlet-body row">
	               			<div class="col-md-4"></div>
	               			<div class="col-md-4" style="text-align:center;">
	               				<div class="row">
	               					<h3>
										<p>扫一扫充值</p><p><span class="font-red-flamingo">￥</span> <span id="rmb-amount" class="font-red-flamingo"></span></p>
									</h3>
								</div>
								<div class="row">
									<img id="qrCodeImg" src="" style="width: 300px; height: 300px;">
								</div>
								<hr/>
								<div class="row">
									<input type="text" style="display: none;" id="isQRCodePageText" value="1" readonly>
									<input type="text" style="display: none;" id="amount" value="1" readonly>
								</div>
								<div class="row">
									<p>打开微信,扫一扫充值</p>
								</div>
	               			</div>
	               			<div class="col-md-4"></div>
	               			
						</div>
					</div>
					
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jsp"%>
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<script>
	var qrCodeInterval;
	$(function(){
		$("title").html("微信充值|账号充值 |财务中心|AnyoneHelps");
    	$(".nav3").addClass("active open");
		$(".nav31").addClass("active open");
		
		var sessionStorage = window.sessionStorage;
		var amount = sessionStorage.getItem("amount");
		document.getElementById("amount").value = amount;
		$("#rmb-amount").html(amount);
		$.ajax({
			type : "get",
			url : "/weixin/recharge",
			data : {
				amount : amount
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					document.getElementById("qrCodeImg").src = "/qr_code.img?codeUrl="+response.data;
					qrCodeInterval = setInterval(checkIfPay, 5000);//测试先关闭
				} else if (code == "607") {
					alert("您尚未登录或登录已失效！");
					logout();
				} else {
					alert("二维码生成失败，原因是:" + response.message+".请刷新重试！");
				}
				return false;
			}
		});
	});
	
	function checkIfPay(){
		if($("#isQRCodePageText").val() == "1"){
			$.ajax({
				type : "get",
				url : "/user/weixin/checkIfScanPay",
				data : {
					amount : $("#amount").val()
				},
				success : function(response) {
					var code = response.code;
					if (code == "200") {
						alert("支付成功");
						clearInterval(qrCodeInterval);
						window.location.href = "/dashboard/Finance/records.jsp";
					} else if(code == "15004"){//session中找不到待充值数据
						alert("没有待充值数据");
						clearInterval(qrCodeInterval);
						window.location.href = "/dashboard/Finance/records.jsp";
					} else if(code == "15003"){//未付款
					} else if (code == "607") {
						alert("您尚未登录或登录已失效！");
						logout();
					} 
					return false;
				}
			});
		}else{
			clearInterval(qrCodeInterval);
		}
	}
</script>
</html>
