var Login = function() {
	
	var handleInit = function() {
        jQuery('.forget-form').hide();
        
		jQuery('.forget-password').click(function() {
            jQuery('.login-form').hide();
            jQuery('.forget-form').show();
        });

        jQuery('.back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
        });
        

        //读取密码
        if ($.cookie("rmbAdmin") == "true") {
    		$("[name='remember']:checkbox").attr("checked", true);
    		$("[name='remember']:checkbox").parent().addClass("checked");
    		$(":text[name=username]").val($.cookie("adminAccount"));
   		 	$(":password[name=password]").val($.cookie("adminPass"));
    	}
        var saveRecommender = function() {
        	var recommender = $("#recommender").val();
    		if (recommender!=null&&recommender) {
    			$.cookie("adminRecommender", recommender, {expires : 1});
    		} 
    	};
    	saveRecommender();
        
	}

    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "Username is required."
                },
                password: {
                    required: "Password is required."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
            	//记住用户名密码
            	var saveAccount = function() {
            		if ($(":checkbox[name='remember']").is(":checked")) {
            			var account = $(form).find(':text[name=username]').val();
            			var password = $(form).find(':password[name=password]').val();
            			$.cookie("rmbAdmin", "true", {expires : 30}); //存储一个带30天期限的cookie
            			$.cookie("adminAccount", account, {expires : 30});
            			$.cookie("adminPass", password, {expires : 30});
            		} else {
            			$.cookie("rmbAdmin", "false", { expire : -1});
            			$.cookie("adminAccount", "", {expires : -1});
            			$.cookie("adminPass", "", {expires : -1});
            		}
            	};
            	$.ajax({  
            	       type: "POST",  
            	       url: $(form).attr("action"),
            	       dataType: "json", 
            	       data: {
            	    	   "account" : $(form).find(':text[name=username]').val(),
           				   "password" : $(form).find(':password[name=password]').val(),
           				   "vcode" : "1234"
            	       },  
            	       success : function(response) {
           				var code = response.code;
           				if (code == "200") {
           					saveAccount();
           					window.location.href = "dashboard/Task/arbitrationList.jsp";
           				} else {
           					alert("登录失败，失败原因是:" + response.message);
           				}
           			}
            	});  
            	
            }
        });

        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('.login-form').submit();       //form validation success, call ajax form submit
                }
                return false;
            }
        });
    }
    
    var handleForgetPassword = function() {
        $('.forget-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                email: {
                    required: true,
                    email: true
                }
            },

            messages: {
                email: {
                    required: "Email is required."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   

            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
                //form.submit();
            	$.ajax({
					type:"get",
					url: $(form).attr("action"),  
					data:{
						"email":$(form).find(':text[name=email]').val(),
						"vcode":"1234"
					},
					success:function(response) {
						var code = response.code;
						if (code == "200") {
							alert("亲爱的用户您好，您的验证信息已经发送到您的邮箱请登陆邮箱查看！");
						} else {
							alert("发送邮件，失败原因是:" + response.message+"请重试！");
						}
					}
				});
            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $('.forget-form').submit();
                }
                return false;
            }
        });
    }

    return {
        //main function to initiate the module
        init: function() {
        	handleInit();
            handleLogin();
            handleForgetPassword();
        }
    };

}();

jQuery(document).ready(function() {
    Login.init();
});