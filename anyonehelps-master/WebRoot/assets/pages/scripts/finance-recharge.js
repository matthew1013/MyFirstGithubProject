
var financeRecharge = function() {
	var financeRechargeInit = function() {
		$("title").html("帐号充值 | 财务中心 | AnyoneHelps");
    	$(".nav3").addClass("active open");
		$(".nav31").addClass("active open");
		
		$("[data-toggle='tooltip']").tooltip();
		$("input[name='tempAmount'],input[name='amount']").keyup(function(){
			//do something
	    	var tempAmount = parseFloat($(this).val()).toFixed(2);
	    	if (!isNaN(tempAmount)&&tempAmount>0)
	    	{
	    		//var total = (tempAmount/0.971+0.3).toFixed(2);
				var total = ((parseFloat(tempAmount).toFixed(2)/1+0.3)/0.971).toFixed(2);    //haokun added 2017/03/09 这里必须要/1，否则结果出现NaN
				var poundage = (total - tempAmount).toFixed(2);
	    		$(this).parent().parent().find(".poundage").html(poundage);
	    		$(this).parent().parent().parent().find(".totalAmount").html("$ "+total);
	    		$(this).parent().parent().parent().find("input[name='amount_1']").val(total);
	    	}else{
	    		$(this).val("");
	    		$(this).parent().parent().find(".poundage").html("0");
	    		$(this).parent().parent().parent().find(".totalAmount").html("$ 0");
	    		$(this).parent().parent().parent().find("input[name='amount_1']").val("0");
	    	}
		});
    }
	function getUserInfo(){
		// 获取用户数据
		$.ajax({
			type:"get",
			url:"/user/get_self",
			success:function(response) {
				var code = response.code;
				if (code == "200") {
					var total = parseFloat(response.data.usdBalance)
						+parseFloat(response.data.freezeUsd)
						+parseFloat(response.data.withdrawalUsd)
					//$("#total").html("$"+parseFloat(total).toFixed(2));
					//$("#usd").html("$"+response.data.usdBalance);
					//$("#freeze").html("$"+response.data.freezeUsd);
					//$("#forwardusd").html("$"+response.data.forwardUsd);
					//$("#withdrawal").html("$"+response.data.withdrawalUsd);
						
					$(".balance .balance-total>span").html(balanceFormat(parseFloat(total).toFixed(2)));
					$(".balance .balance-usd>span").html(response.data.usdBalance); //haokun 2017/03/03
					$(".balance .balance-freeze>span").html(balanceFormat(response.data.freezeUsd));
					$(".balance .balance-withdrawal>span").html(balanceFormat(response.data.withdrawalUsd));
					$(":hidden[name='custom']").val(response.data.id);
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！");
				} else {
					// 失败
					alert("获取账户余额失败，原因是:" + response.message);
				}
			}
		});
		
		function balanceFormat(balance){
			balance = balance.toString();
			if(/[^0-9\.]/.test(balance)) return "$ 0.00";
			balance=balance.replace(/^(\d*)$/,"$1.");
			balance=(balance+"00").replace(/(\d*\.\d\d)\d*/,"$1");
			balance=balance.replace(".",",");
			var re=/(\d)(\d{3},)/;
			while(re.test(balance)) balance=balance.replace(re,"$1,$2");
			balance=balance.replace(/,(\d\d)$/,".$1");
			return "$ " + balance.replace(/^\./,"0.")
		}
	}
    var rechargeLoad = function() {
    	
		function weixinRecharge() {
			var amount = $("#weixinRmbText").val();
			if (/^\d+(.\d{0,2})?$/.test(amount)) {
				var sessionStorage = window.sessionStorage;
				sessionStorage.setItem("amount", amount);
				window.open("/dashboard/Finance/weixinQRCode.jsp");
			} else {
				alert("请输入要充值的人民币数量，值必须是数字，而且大于0！");
			}
			return false;
		};
			
    	/*function paypalRecharge() {
    		$.ajax({
    			type:"post",
    			url:"https://www.paypal.com/cgi-bin/webscr",
    			data:{
    				"name":name,
    				//"type":type,
    				"creditNo":creditNo,
    				"currency":"usd",
    				"securityCode":creditSecurityCode,
    				"expireYear":expireYear,
    				"expireMonth":expireMonth,
    				"amount":amount
    			},
    			success:function(response) {
    					var code = response.code;
    					if (code == "200") {
    						alert("充值成功");
    					} else if ("607" == code) {
    						alert("您尚未登录或登录已失效！");
    						logout();
    					} else {
    						// 失败
    						alert("充值失败，原因是:" + response.message);
    					}
    				}
    		});
    	};*/

    	function alipayRecharge() {
    		var amount = $("input[name='amount_rmb']").val();
    		if (/^\d+(.\d{0,2})?$/.test(amount)) {
    			alert("请选择及时到账服务");
    			 window.open("/alipay/recharge"+ "?amount=" + amount);  
    			//window.location.href = alipay_recharge_url+ "?amount=" + amount;				
    		} else {
    			alert("请输入要充值的人民币数量，值必须是数字，而且大于0！");
    		}
    		return false;
    	};
    	function creditCardRecharge() {
    		var name = $(":text[name='name']").val();
    		//var type = $(":text[name='type']").val();
    		var creditNo = $(":text[name='creditNo']").val();
    		var creditSecurityCode = $(":text[name='creditSecurityCode']").val();
    		var expireYear = $("select[name='expireYear']").val();
    		//var expireYear = $(":text[name='expireYear']").val();
    		var expireMonth = $("select[name='expireMonth']").val();
    		//var expireMonth = $(":text[name='expireMonth']").val();
    		var brand = $("select[name='brand']").val();

    		var amount = $(this).parent().parent().parent().find("input[name='amount_1']").val();
    		//var amount = $("input[name='amount']").val();
    		if (confirm("您是否确认要进行信用卡充值?")) {
    			$.ajax({
    				type:"post",
    				url:"/stripe/recharge",
    				data:{
    					"brand":brand,
    					"name":name,
    					"creditNo":creditNo,
    					"currency":"usd",
    					"securityCode":creditSecurityCode,
    					"expireYear":expireYear,
    					"expireMonth":expireMonth,
    					"amount":amount
    				},
    				success:function(response) {
    						var code = response.code;
    						if (code == "200") {
    							$.successMsg("充值成功");
    							window.location.href = "/dashboard/Finance/records.jsp";
    						} else if ("607" == code) {
    							$.errorMsg("您尚未登录或登录已失效！");
    							logout();
    						} else {
    							// 失败
    							$.errorMsg(response.message);
    						}
    					}
    			});
    		}
    	}
    	
    	function anetCreditCardRecharge() {
    		var creditNo = $(":text[name='anetCreditNo']").val();
    		var creditSecurityCode = $(":text[name='anetCreditSecurityCode']").val();
    		var expireYear = $(":text[name='anetExpireYear']").val();
    		var expireMonth = $(":text[name='anetExpireMonth']").val();
    		var amount = $("input[name='anetAmount']").val();
    		
    		if (confirm("您是否确认要进行信用卡充值?")) {
    			$.ajax({
    				type:"post",
    				url:"/anet/recharge",
    				data:{
    					"creditNo":creditNo,
    					"securityCode":creditSecurityCode,
    					"expireYear":expireYear,
    					"expireMonth":expireMonth,
    					"amount":amount
    				},
    				success:function(response) {
    						var code = response.code;
    						if (code == "200") {
    							//alert("充值成功");
    							$.successMsg("充值成功");
    							window.location.href = "/dashboard/Finance/records.jsp";
    						} else if ("607" == code) {
    							$.errorMsg("您尚未登录或登录已失效！");
    							logout();
    						} else {
    							// 失败
    							$.errorMsg(response.message);
    						}
    					}
    			});
    		}
    	}


		$("a[name='a_alipay_recharge']").click(alipayRecharge);
		$("a[name='a_creditcard_recharge']").click(creditCardRecharge);
		$("#a_rmb_weixin_charge_link").click(weixinRecharge);
		$("a[name='anet_creditcard_recharge']").click(anetCreditCardRecharge);
		$("input[name='Paypal']").click(function (){
			var amount = $(".paypal-form").find('input[name="amount_1"]').val()
			if(amount==null||amount==""||amount=="0"){
				$.errorMsg("请输入充值金额");
				return false;
			}else{
				this.form.submit();
			}
			
		})
		getUserInfo();
    }
    var accountTransferInit = function() {
    	function transferDlg() {
			var account = $("input[name='transfer-user']").val();
			var amount = $("input[name='transfer-amount']").val();
			if (/^\d+(.\d{0,2})?$/.test(amount)) {
    		} else {
    			alert("请输入要充值的人民币数量，值必须是数字，而且大于0！");
    			return false;
    		}
			if (account==""||account==null) {
    			alert("请输入收款帐号！");
    			return false;
    		}
			var dlg = dialog({
				title : '',
				fixed: true,
				width : 500,
				quickClose : true,
				onshow : function() {
					var me = this;
					$.ajax({
						url : '/user/open_info_by_account',
						data : {
							account : account
						},
						success : function(data) {
							console.log(data);
							if (data.code=="200") {
								if(data.data!=null){
									me.content($('#transfer-tmpl').render({amount:amount,user:data.data}));
									$('.transfer-cancle-btn').click(function(){
										dlg.close();
									});
									$('.transfer-submit-btn').click(function(){
										$.ajax({
											url : '/account/transfer',
											data : {
												account : account,
												amount : amount
											},
											success : function(data) {
												if (data.code=="200") {
													$.successMsg("付款成功");
													dlg.close();
													getUserInfo();
												} else {
													$.errorMsg("付款失败，原因是:" + data.message);
												}
											},
										});
									});
								}
							} else {
								$.errorMsg("付款失败，原因是:" + data.message);
								me.close();
							}
						},
					});

				}
			}).show(this);
			return false;
		};
    	$("a[name='transfer-a']").click(transferDlg);
    }
    return {
        //main function to initiate the module
        init: function() {
        	financeRechargeInit();
        	rechargeLoad();
        	accountTransferInit();
        }

    };

}();

jQuery(document).ready(function() {
	financeRecharge.init();
});