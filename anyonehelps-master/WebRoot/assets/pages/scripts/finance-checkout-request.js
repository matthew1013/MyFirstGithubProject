
var financeCheckoutRequest = function() {
	var financeCheckoutRequserInit = function() {
		$("title").html("取现申请 | 财务中心 | AnyoneHelps");
    	$(".nav3").addClass("active open");
		$(".nav33").addClass("active open");
    }
	function loadUserInfo(){
    	// 获取用户数据 
    	$.ajax({
    		type:"get",
    		url:"/user/get_self",
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") {
    				var data = response.data;
    				console.log(response.data);
    				$(".usd").html("$"+data.usdBalance);
    				$(".usd").data("usd", data.usdBalance);
    				if(data.usdBalance<100){
    					$.errorMsg("您的可用余额小于$100,无法取现！");
    				}
    				if(data.telphoneState!=1||data.emailState!=1)//电话状态已经验证
    				{
    					$("#checkout-request").find("input").attr("disabled","disabled");
    					$("#checkout-request").find("select").attr("disabled","disabled");
    					$("#checkout-request").find("button").attr("disabled","disabled");
    				}else{
    					$(".desc").addClass("hide");
    				}
    			} else if ("607" == code) {
    				alert("您尚未登录或登录已失效！");
    				logout();
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
    }	
    var handleInit = function() {
    	
    	$("select[name='type']").change(function(){
    		$("input[name='account']").val("");
    		if($(this).val()=="1"){
    			$(".routing-number").removeClass("hide");
    			$(".account-type-div").removeClass("hide");
    			$(".bnk-type-div").removeClass("hide");
    		}else{
    			$(".routing-number").addClass("hide");
    			$(".account-type-div").addClass("hide");
    			$(".bnk-type-div").addClass("hide");
    		}
    	})
    	$('input[name="bnkType"],input[name="accountType"]').iCheck({
    		checkboxClass: 'icheckbox_square-blue',
    		radioClass: 'iradio_square-blue',
    		increaseArea: '20%' // optional
    	});
    	var accountFormat = function(){
    		if($("select[name='type']").val()==1){
    			$(this).val($(this).val().replace(/(\d{4})(?=[^\s])/,'$1 '));
    		}
    	}
    	$("input[name='account']").keydown(accountFormat);
    	$("input[name='account']").keyup(accountFormat);
    	$("input[name='account']").keypress(accountFormat);
    	
    	var request = function(type, name,account,accountType, bnkType, routingNumber, amount, code) {
    		if(type =="2"){
    			accountType = "";
    			bnkType = "";
    			routingNumber = "";
    		}
    		if(amount<100){
    			$.errorMsg("取现金额不能低于100美金！");
    			return false;
    		}
    		var usd = $(".usd").data("usd");
    		if(parseFloat(amount)>parseFloat(usd)){
    			$.errorMsg("您可用金额不足！");
    			return false;
    		}
    		$.ajax({
    			type : "post",
    			url : "/account/withdrawals/add",
    			data : {
    				"type" : type,
    				"name" : name,
    				"account" : account,
    				"accountType" : accountType,
    				"bnkType" : bnkType,
    				"account" : account,
    				"routingNumber" : routingNumber,
    				"amount" : amount,
    				"vcode" : code
    			},
    			success : function(response) {
    				var code = response.code;
    				if ("200" == code) {
    					$.successMsg("申请成功");
    					window.location.href = "/dashboard/Finance/checkout_records.jsp";
    				} else if ("607" == code) {
    					$.errorMsg(response.message);
        				logout();
        			} else {
        				$.errorMsg(response.message);
    				}
    			}
    		});
    	}
		
		$('#checkout-request').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
            	account: {
                    required: true
                },
                name: {
                    required: true
                },
                amount: {
                    required: true,
                    number:true
                },
                code:{
                	required: true,
                	rangelength:[6,6]
                }
            },

            messages: {
                account: {
                    required: "请输入收款帐号."
                },
                name: {
                    required: "请输入收款人名字."
                },
                amount: {
                	required: "请输入金额。",
                	number: "金额输入不正确。"
                },
                code: {
                	required: "请输入验证码。",
                	rangelength: "验证码长度不正确。"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
            	$('.alert-danger', $('#checkout-request')).show();
            },

            highlight: function(element) { // hightlight error inputs
                //$(element) .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                //$(label).closest('.form-group').removeClass('has-error');
               // label.remove();
            },

            errorPlacement: function(error, element) {
                //error.insertAfter(element.closest('.input-icon'));
                //error.insertAfter( $('.alert-danger span') );
            },

            submitHandler: function(form) {
            	var type = $("select[name='type']").val();
            	var name = $(":text[name='name']").val();
            	var account = $(":text[name='account']").val();
            	var accountType = $(":radio[name='accountType']").val();
            	var bnkType = $(":radio[name='bnkType']").val();
        		var routingNumber = $(":text[name='routingNumber']").val();
        		var amount = $("input[name='amount']").val();
        		var code = $("input[name='code']").val();
        		
        		request(type, name,account,accountType, bnkType, routingNumber, amount, code);
        		return false;
            }
        });

        $('#checkout-request input').keypress(function(e) {
            if (e.which == 13) {
                if ($('#checkout-request').validate().form()) {
                    $('#checkout-request').submit();       //form validation success, call ajax form submit
                }
                return false;
            }
        });
        
        
        //倒计时60s
        var countdown=60; 
        function settime() { 
        	var obj = $(".get-verify-code");
        	if (countdown == 0) {   
        		 obj.data('enable','1');
        		 obj.html("获取验证码"); 
        		countdown = 60; 
        	} else { 
        		obj.html("重新发送(" + countdown + ")"); 
        		countdown--; 
        		setTimeout(function() { settime() },1000) 
        	}
        } 
        
        $('.get-verify-code').click(function(){
        	var enable = $(this).data("enable");
        	if(enable=="0"){
        		return false;
        	}
    		me = this;
        	$.ajax({
    			type : "post",
    			url : "/account/withdrawals/get_phone_code",
    			success : function(response) {
    				var code = response.code;
    				if ("200" == code) {
    					$.successMsg("验证码已发送,请查看手机,并填入验证码！");
    					//按钮不可用
    		    		$(me).data('enable','0');
    		    		setTimeout(function() {settime() },1000);
    				} else if ("607" == code) {
        				$.errorMsg("您尚未登录或登录已失效！");
        				logout();
        			} else {
        				$.errorMsg(response.message);
    				}
    			}
    		});
        })
    }
    return {
        //main function to initiate the module
        init: function() {
        	financeCheckoutRequserInit();
        	handleInit();
        	loadUserInfo();
        }

    };

}();

jQuery(document).ready(function() {
	financeCheckoutRequest.init();
});