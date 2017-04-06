 $.views.tags({
	"setOptionSelected": function(optionValue,result){
		if(result){
        	if(optionValue==result){
        		return "selected";
        	}
     	}
    	return "";
   	},
 	"setOptionChecked": function(optionValue,result){
 		if(result){
 			if(optionValue==result){
 				return "checked";
 			}
     	}else if(!optionValue||optionValue==""){
        	return "checked";
       	}
 		return "";
 	}, 
 	"questionFormat": function(index){
 		var result = ""
    	if(index == "1"){
    		result = '您母亲的姓名是？';
    	}else if(index == "2"){
    		result = '您父亲的姓名是？';
    	}else if(index == "3"){
    		result = '您配偶的姓名是？';
    	}else if(index == "4"){
    		result = '您的出生地是？';
    	}else if(index == "5"){
    		result = '您高中班主任的名字是？';
    	}else if(index == "6"){
    		result = '您初中班主任的名字是？';
    	}else if(index == "7"){
    		result = '您小学班主任的名字是？';
    	}else if(index == "8"){
    		result = '您的小学校名是？';
    	}else if(index == "9"){
    		result = '您父亲的生日是？';
    	}else if(index == "10"){
    		result = '您母亲的生日是？';
    	}else if(index == "11"){
    		result = '您配偶的生日是？';
    	}else if(index == "12"){
    		result = '您的学号（或工号）是？';
    	}
 		return result;
    }
 });
 

var Login = function() {
	$("title").html($(":hidden[name='seoTitle']").val());
	$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
	$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
	
	var email_regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	var password_regex = /^\S{6,20}$/;
	var phone_regex = /^.{9,20}$/;
	var nick_name_regex= /^([a-zA-Z][a-zA-Z0-9]{2,19})$/;
	
	$(".login-box,.mbblack,.log-in,.sign-in").remove();
	
	/*function isPC(){
    	var userAgentInfo = navigator.userAgent;
    	console.log(userAgentInfo)
    	var Agents = ["Android","iPhone","Windows Phone","iPad","iPod"];
    	var flag = true;
    	for(var v = 0;v<Agents.length;v++){
    		if(userAgentInfo.indexOf(Agents[v])>0){
    			flag = false;
    			break;
    		}
    	}
    	return flag;
    }*/
    /*if(!isPC()){
		if ($.cookie("enable") != "true") {
			var d = dialog({
	    		width:480,
	    		title:"温馨提醒",
	    		skin:"mobile-tip", 
			    onshow : function() {
			    	this.content("为了您更好的使用Anyonehelps的功能，建议电脑登录。");     
			    }
			});
	    	d.showModal();
    		$.cookie("enable", true, {expires : 100000000});
    	}
    }*/
	
	//倒计时countdown 秒
    function settime(obj,countdown) { 
    	if (countdown == 0) {   
    		 obj.data('enable','1');
    		 obj.html("获取验证码"); 
    	} else { 
    		obj.html("重新发送(" + countdown + ")"); 
    		setTimeout(function() { settime(obj,countdown-1) },1000) 
    	}
    } 
	
	var handleInit = function() {
		/*haokun added start 2017/03/17*/
		    $('.nav #li-tab-1').children().addClass("highlighted-white");	
			$('.nav #li-tab-first-1').children().addClass("highlighted-white");
		/*haokun added end 2017/03/17*/
		
		
		$('#li-tab-3').hide();
		$('.question-forget-form').hide();
		
		if($("#view").val()=="register"){
			//$("title").html("用户注册 | AnyoneHelps");
			$('.login-form').hide();
            $('.email-login-form').hide();
            $('.phone-forget-form').hide();
            $('.email-forget-form').hide();
            $('.register-form').show();
            $('.email-register-form').show();
           
            $.ajax({  
        		type: "POST",  
        		url: "user/check_code",  
        		data: {
        			"codeType" : 2
        		},  
        		dataType: "json",  
        		success : function(response) {
        			var code = response.code;
       				if (code == "200") {
       					if(response.data=="1"){
       						$(".email-register-form").find(".show-login-code").removeClass("hide");
       			            resetValidateCode($(".email-register-form").find(".refresh-code").prev());
       					}
       				}
       			}
        	});  
            
		}else if($("#view").val()=="resetPwd"){
			//$("title").html("找回密码 | AnyoneHelps");
			$('.login-form').hide();
            $('.email-login-form').hide();
            $('.register-form').hide();
            $('.email-register-form').hide();
            $('.phone-forget-form').show();
            $('.email-forget-form').show();
            $('#li-tab-3').show();
            $('.question-forget-form').show();
		}else{
			//$("title").html("用户登录 | AnyoneHelps");
			$('.register-form').hide();
	        $('.email-register-form').hide();
	        $('.phone-forget-form').hide();
	        $('.email-forget-form').hide();
	        
	        $.ajax({  
        		type: "POST",  
        		url: "user/check_code",  
        		data: {
        			"codeType" : 1
        		},  
        		dataType: "json",  
        		success : function(response) {
        			var code = response.code;
       				if (code == "200") {
       					if(response.data=="1"){
       						$(".email-login-form").find(".show-login-code").removeClass("hide");
       			            resetValidateCode($(".email-login-form").find(".refresh-code").prev());
       					}
       				}
       			}
        	});  
		}
		
		/*
		$(".content .tabbable-custom .tab-content .tab-pane .form-actions .rememberme").click(function(){
           if ( $(".content .tabbable-custom .tab-content .tab-pane .form-actions .rememberme").is( ":checked" ) ){
              $(".content .tabbable-custom .tab-content .tab-pane .form-actions .rememberme span").css("color","#ffffff !important");
           }else{
              $(".content .tabbable-custom .tab-content .tab-pane .form-actions .rememberme span").css("color","#808080 !important");
           }
        });
		*/
		
		/*haokun added start 2017/03/23 点击保存密码颜色变亮*/
		$("#email-login-form-checkbox").click(function(){
           if ( $("#email-login-form-checkbox").is( ":checked" ) ){
              $("#email-login-form-checkbox-span").css("color","#ffffff !important");
           }else{
              $("#email-login-form-checkbox-span").css("color","#808080 !important");
           }
        });
		
		$("#login-form-checkbox").click(function(){
           if ( $("#login-form-checkbox").is( ":checked" ) ){
              $("#login-form-checkbox-span").css("color","#ffffff !important");
           }else{
              $("#login-form-checkbox-span").css("color","#808080 !important");
           }
        });
		/*haokun added end 2017/03/23 点击保存密码颜色变亮*/
		
		
		/*haokun added 2017/03/17  start 一级目录---------------------*/
		/*进入注册*/
		$('.nav #li-tab-first-1').click(function(){
            $('.login-form').hide();  
            $('.email-login-form').hide();
            $('.register-form').show();
            $('.email-register-form').show();           
            $('#li-tab-3').hide();
            $('.question-forget-form').hide();		
			$('.phone-forget-form').hide();
			$('.email-forget-form').hide();
			
			/*$('#li-tab-1').addClass("active");*/
            $('.email-register-form').addClass("active");
			$('.register-form').addClass("active");
            $(this).children().addClass("highlighted-white");    //该按钮高亮
			$('.nav #li-tab-first-2').children().removeClass("highlighted-white"); //另一个按钮取消高亮		
			/*$('.nav #li-tab-1').click(); //模拟点击*/
		}).click(function(){
			   $('.nav #li-tab-1').click(); //模拟点击		
		});
		
		/*进入登录*/
		$('.nav #li-tab-first-2').click(function(){
			$('.login-form').show();
            $('.email-login-form').show();
            $('.register-form').hide();
            $('.email-register-form').hide();           
            $('#li-tab-3').hide();
            $('.question-forget-form').hide();
			$('.phone-forget-form').hide();
			$('.email-forget-form').hide();
			
			/*$('#li-tab-1').addClass("active"); 
            /*$('#tab_1').addClass("active");*/
			$('.login-form').addClass("active");
			$('.emial-login-form').addClass("active");
            $(this).children().addClass("highlighted-white");    //该按钮高亮
			$('.nav #li-tab-first-1').children().removeClass("highlighted-white"); //另一个按钮取消高亮		
		}).click(function(){
			$('.nav #li-tab-1').click(); //模拟点击		
		});
		
		
		/*haokun added 2017/03/17   end  一级目录-----------------*/
		
		/*haokun added 2017/03/17  start 二级目录---------------------*/
		/*进入邮箱*/
		$('.nav #li-tab-1').click(function(){
            $(this).children().addClass("highlighted-white");    //该按钮高亮
			$('.nav #li-tab-2').children().removeClass("highlighted-white"); //另一个按钮取消高亮
			$('.nav #li-tab-3').children().removeClass("highlighted-white"); //另一个按钮取消高亮			
		});
		
		/*进入电话*/
		$('.nav #li-tab-2').click(function(){
            $(this).children().addClass("highlighted-white");    //该按钮高亮
			$('.nav #li-tab-1').children().removeClass("highlighted-white"); //另一个按钮取消高亮
			$('.nav #li-tab-3').children().removeClass("highlighted-white"); //另一个按钮取消高亮		
		})

		/*进入忘记密码*/
		$('.nav #li-tab-3').click(function(){
            $(this).children().addClass("highlighted-white");    //该按钮高亮
			$('.nav #li-tab-1').children().removeClass("highlighted-white"); //另一个按钮取消高亮
			$('.nav #li-tab-2').children().removeClass("highlighted-white"); //另一个按钮取消高亮				
		})		
		
		/*haokun added 2017/03/17   end  二级目录-----------------*/
        
		
		 /*haokun modified 2017/03/17*/
		$('.forget-password').click(function() {
			//$("title").html("找回密码 | AnyoneHelps");
            $('.login-form').hide();
            $('.email-login-form').hide();
            $('.phone-forget-form').show();
            $('.email-forget-form').show();
            $('#li-tab-3').show();
            $('.question-forget-form').show();
			$('.nav li').children().removeClass("highlighted-white"); //另一个按钮取消高亮	
            $('.nav #li-tab-1').children().addClass("highlighted-white");			
        });
		/*haokun modified 2017/03/17*/
		

        $('.back-btn').click(function() {
			//$("title").html("用户登录 | AnyoneHelps");
            $('.login-form').show();
            $('.email-login-form').show();
            $('.phone-forget-form').hide();
            $('.email-forget-form').hide();
            
            $('#li-tab-3').hide();
            $('.question-forget-form').hide();
        });
        $('.question-back-btn').click(function() {
        	//第一步
        	var state = $(this).data("state");
        	if(state == "0"){
        		//$("title").html("用户登录 | AnyoneHelps");
                $('.login-form').show();
                $('.email-login-form').show();
                $('.phone-forget-form').hide();
                $('.email-forget-form').hide();
                
                $('#li-tab-3').hide();
                $('.question-forget-form').hide();
                
                $('#li-tab-1').addClass("active");
                $('#tab_1').addClass("active");
                
        	}else if(state == "1"){
        		$('.question-forget-form .form-group').remove();
				$('.question-forget-form p').remove();
				$(this).html("返回登录");
				$(this).data("state","0");
				$('.question-forget-form .question-next').data("state","0");
				$('.question-forget-form .form-actions').before($('#question-first-tmpl').render());
			
        	}
			
            
        });
        
        $('.register-btn').click(function() {
			//$("title").html("用户注册 | AnyoneHelps");
            $('.login-form').hide();
            $('.email-login-form').hide();
            $('.register-form').show();
            $('.email-register-form').show();
            
            $('#li-tab-3').hide();
            $('.question-forget-form').hide();
        });

        $('.register-back-btn').click(function() {
			//$("title").html("用户登录 | AnyoneHelps");
            $('.login-form').show();
            $('.email-login-form').show();
            $('.register-form').hide();
            $('.email-register-form').hide();
            
            $('#li-tab-3').hide();
            $('.question-forget-form').hide();
        });
        //读取密码
        if ($.cookie("rmbUser") == "true") {
        	$(".email-login-form").find("[name='remember']:checkbox").attr("checked", true);
        	$(".email-login-form").find("[name='remember']:checkbox").parent().addClass("checked");
        	$(".email-login-form").find(":text[name=username]").val($.cookie("account"));
        	$(".email-login-form").find(":password[name=password]").val($.cookie("password"));
    	}
        
        //读取密码
        if ($.cookie("rmbUserByPhone") == "true") {
    		$(".login-form").find(".remember").attr("checked", true);
    		$(".login-form").find(".remember").parent().addClass("checked");
    		
    		$(".login-form").find(":text[name=username]").val($.cookie("phone"));
    		$(".login-form").find(":password[name=password]").val($.cookie("passwordByPhone"));
    		$(".login-form").find(".area-code").val($.cookie("areaCode"));
    	}
        var saveRecommender = function() {
        	var recommender = $("#recommender").val();
    		if (recommender!=null&&recommender) {
    			$.cookie("userRecommender", recommender, {expires : 1});
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
                //form.submit(function()); // form validation success, call ajax form submit
            	//记住用户名密码
            	var saveAccountByPhone = function() {
            		if ($(form).find(".remember").is(":checked")) {
            			var phone = $(form).find(':text[name=username]').val();
            			var areaCode = $(form).find(".area-code").val();
            			var password = $(form).find(':password[name=password]').val();
            			$.cookie("rmbUserByPhone", "true", {expires : 30}); //存储一个带30天期限的cookie
            			$.cookie("phone", phone, {expires : 30});
            			$.cookie("areaCode", areaCode, {expires : 30});
            			$.cookie("passwordByPhone", password, {expires : 30});
            		} else {
            			$.cookie("rmbUserByPhone", "false", { expire : -1});
            			$.cookie("phone", "", {expires : -1});
            			$.cookie("areaCode", "", {expires : -1});
            			$.cookie("passwordByPhone", "", {expires : -1});
            		}
            	};
            	$.ajax({  
            		type: "POST",  
            		url: $(form).attr("action"),  
            		data: {
            			"areaCode" : $(form).find(".area-code").val(),
            			"phone" : $(form).find(':text[name=username]').val(),
           				"password" : $(form).find(':password[name=password]').val(),
           				"vcode" : $(form).find(':text[name="login-code"]').val()
            		},  
            		dataType: "json",  
            		success : function(response) {
            			var code = response.code;
           				if (code == "200") {
           					saveAccountByPhone();
           					$.successMsg("登录成功");
           					window.location.href = "/index.jsp";
           				}else if(code == "12026"){
		    				//验证码
           					$(form).find(".show-login-code").removeClass("hide");
		    				resetValidateCode($(form).find(".refresh-code").prev());
		    				$.errorMsg(response.message);
		    			}else if(code == "12024"){
							window.location.href = "/forbidden/userForbidden.jsp";
						}else {
           					$.errorMsg(response.message);
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
        
        
        
        $('.email-login-form').validate({
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
                $('.alert-danger', $('.email-login-form')).show();
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
                //form.submit(function()); // form validation success, call ajax form submit
            	//记住用户名密码
            	var saveAccount = function() {
            		if ($(":checkbox[name='remember']").is(":checked")) {
            			var account = $(form).find(':text[name=username]').val();
            			var password = $(form).find(':password[name=password]').val();
            			$.cookie("rmbUser", "true", {expires : 30}); //存储一个带30天期限的cookie
            			$.cookie("account", account, {expires : 30});
            			$.cookie("password", password, {expires : 30});
            		} else {
            			$.cookie("rmbUser", "false", { expire : -1});
            			$.cookie("account", "", {expires : -1});
            			$.cookie("password", "", {expires : -1});
            		}
            	};
            	$.ajax({  
            	       type: "POST",  
            	       url: $(form).attr("action"),  
            	       data: {
            	    	   "account" : $(form).find(':text[name=username]').val(),
           				   "password" : $(form).find(':password[name=password]').val(),
           				   "vcode" : $(form).find(':text[name="login-code"]').val()
            	       },  
            	       dataType: "json",  
            	       success : function(response) {
           				var code = response.code;
           				if (code == "200") {
           					saveAccount();
           					$.successMsg("登录成功");
           					window.location.href = "/index.jsp";
           				}else if(code == "12026"){
		    				//验证码
           					$(form).find(".show-login-code").removeClass("hide");
		    				resetValidateCode($(form).find(".refresh-code").prev());
		    				$.errorMsg(response.message);
		    			}else if(code == "12024"){
							window.location.href = "/forbidden/userForbidden.jsp";
						}else {
           					$.errorMsg(response.message);
           				}
           			}
            	});  
            	
            }
        });

        $('.email-login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.email-login-form').validate().form()) {
                    $('.email-login-form').submit();       //form validation success, call ajax form submit
                }
                return false;
            }
        });
    }
    
    

    var handleForgetPassword = function() {
        $('.email-forget-form').validate({
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
            	var email = $(form).find(':text[name=email]').val();
            	if(!email_regex.test(email)){
    				$.errorMsg("邮箱格式错误，请输入正确的邮箱！");
    				return false;
    			};
            	$.ajax({
					type:"get",
					url: $(form).attr("action"),  
					data:{
						"email" : email,
						"vcode" : "1234"
					},
					success:function(response) {
						var code = response.code;
						if (code == "200") {
							$.successMsg("找回密码邮件已发送,请登录邮箱查看！");
						} else {
							$.errorMsg(response.message);
						}
					}
				});
            }
        });

        $('.email-forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.email-forget-form').validate().form()) {
                    $('.email-forget-form').submit();
                }
                return false;
            }
        });
        
        $('.phone-forget-form').validate({
            submitHandler: function(form) {
            	
                //form.submit();
            	var vcode = $(form).find(':text[name=forget-pwd-phone-verifycode]').val();
            	var areaCode = $(".phone-forget-form").find(".area-code").val();
            	var telphone = $(form).find(':text[name=forget-pwd-phone]').val();
    			var password = $(form).find(':password[name=forget-pwd-phone-password]').val();
    			var confirmPassword = $(form).find(':password[name=forget-pwd-phone-confirm-password]').val();
    			if(password!=confirmPassword){
    				$.errorMsg("新密码和确认密码不一致！");
    				return false;
    			}
    			var validaVerifycode = function (verifycode){
    				if(verifycode.length!=6){
    					$.errorMsg("验证码长度不对,请输入正确的验证码.");
    					return false;
    				}
    				return true;
    			}
    			
    			var validaTelphone =function (telphone){
    	    		if(!phone_regex.test(telphone)){
    	    			$.errorMsg("手机号码不正确，请输入正确手机号码！");
    	    			return false;
    	    		}
    	    		return true;
    			}
    			var validatePwd = function (password){
    				if(!password_regex.test(password)){
    					$.errorMsg("密码长度不对.长度必须是6~20位");
    					return false;
    				}
    				return true;
    			}
    			
    			if(!validaTelphone(telphone)||!validatePwd(password)||!validaVerifycode(vcode)){
    				return false;
    			}

    			$.ajax({
    				type:"get",
    				url: $(form).attr("action"),
    				data:{
    					"password":password,
    					"areaCode":areaCode,
    					"phone":telphone,
    					"vcode":vcode
    				},
    				success:function(response) {
    					var code = response.code;
    					if (code == "200") {
    						$.successMsg("密码已重置，正在登录中...");
    						//window.location.reload();
    						setTimeout(function(){
    							window.location.href="/"; 
    						},2000);
    					} else {
    						$.errorMsg(response.message);
    					}
    				}
    			});
            }
        });

        $('.phone-forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.phone-forget-form').validate().form()) {
                    $('.phone-forget-form').submit();
                }
                return false;
            }
        });
        
       
        
        $("#forget-pwd-get-verifycode-btn").click(function(){
        	var enable = $(this).data().enable;
        	if(enable=="0"){
        		return false;
        	}
        	var value = $(":text[name='forget-pwd-phone']").val();
    		if(!phone_regex.test(value)){
    			$.errorMsg("手机号码不正确，请输入正确手机号码！");
    			return false;
    		}
    		var me = $(this);
    		
    		$.ajax({
    			type:"post",
    			url:"code/phone",
    			data:{
    				"areaCode":$(".phone-forget-form").find(".area-code").val(),
    				"phone":value
    			},
    			success:function(response) {
    				var code = response.code;
    				if (code == "200") {
    					//按钮不可用
    		    		me.data('enable','0');
    		    		settime(me,60);
    					$(":text[name='forget-pwd-phone']").attr("readonly","readonly");
    					$.successMsg("验证码已发送，请查看手机,并填入验证码！");
    				} 
    				else
    				{
    					$.errorMsg(response.message);
    				}
    			}
    		});
        })
        
        /*密保找回密码 start*/
        //$('.question-forget-form').find('select[name=index]').html($('#question-tmpl').render({index1:"",index2:"",value:""}));

        /*$('select[name="index"]').click(function() {
        	var me = $(this);
        	var index1;
	    	var index2;
	    	var index3;
        	$('.question-forget-form').find('select[name=index]').each(function(index,item){
        		if(index==0){
	    			index1 = $(item);
	    		}
	    		if(index==1){
	    			index2 = $(item);
	    		}
	    		if(index==2){
	    			index3 = $(item);
	    		}
	    	});
        	if(index1.is(me)){
        		$(index2).html($('#question-tmpl').render({index1:index1.val(),index2:index3.val(),value:index2.val()}));
        		$(index3).html($('#question-tmpl').render({index1:index1.val(),index2:index2.val(),value:index3.val()}));
        	}else if(index2.is(me)){
        		$(index1).html($('#question-tmpl').render({index1:index2.val(),index2:index3.val(),value:index1.val()}));
        		$(index3).html($('#question-tmpl').render({index1:index1.val(),index1:index2.val(),value:index3.val()}));
        	}else if(index3.is(me)){
        		$(index1).html($('#question-tmpl').render({index1:index2.val(),index2:index3.val(),value:index1.val()}));
        		$(index2).html($('#question-tmpl').render({index1:index1.val(),index2:index3.val(),value:index2.val()}));
        	}
        });
        $('.question-forget-form').validate({
            submitHandler: function(form) {
            	var areaCode = $(form).find(".question-area-code").val();
            	var telphone = $(form).find('.question-phone').val();
    			var email = $(form).find('.question-email').val();
    			var index1 = "";
		    	var index2 = "";
		    	var index3 = "";
		    	var answer1 = "";
		    	var answer2 = "";
		    	var answer3 = "";
		    	$(form).find('select[name=index]').each(function(index,item){
		    		if(index==0){
		    			index1 = $(item).val();
		    		}
		    		if(index==1){
		    			index2 = $(item).val();
		    		}
		    		if(index==2){
		    			index3 = $(item).val();
		    		}
		    	});
		    	$(form).find('input[name=answer]').each(function(index,item){
		    		if(index==0){
		    			answer1 = $(item).val();
		    		}
		    		if(index==1){
		    			answer2 = $(item).val();
		    		}
		    		if(index==2){
		    			answer3 = $(item).val();
		    		}
		    	});
    			
    			
    			if(telphone!=null&&telphone!=""){
    				if(!phone_regex.test(telphone)){
        				$.errorMsg("手机号码输入不正确，请重新输入");
        				return false;
        			}
    			}
    			
    			if((email==null||email=="")&&(!phone_regex.test(telphone))){
    				$.errorMsg("手机或者用户名/邮箱必须输入一项");
    				return false;
    			}
    			
    			if(index1==null||index1==""){
    				$.errorMsg("请选择问题一,并进行回答");
    				return false;
    			}
    			if(answer1==null||answer1==""){
    				$.errorMsg("请回答问题一");
    				return false;
    			}
    			if(index2==null||index2==""){
    				$.errorMsg("请选择问题二,并进行回答");
    				return false;
    			}
    			if(answer2==null||answer2==""){
    				$.errorMsg("请回答问题二");
    				return false;
    			}
    			if(index3==null||index3==""){
    				$.errorMsg("请选择问题三,并进行回答");
    				return false;
    			}
    			if(answer3==null||answer3==""){
    				$.errorMsg("请回答问题三");
    				return false;
    			}

    			$.ajax({
    				type:"post",
    				url: $(form).attr("action"),
    				data:{
    					"email":email,
    					"areaCode":areaCode,
    					"phone":telphone,
    					"index1":index1,
    					"index2":index2,
    					"index3":index3,
    					"answer1":answer1,
    					"answer2":answer2,
    					"answer3":answer3
    				},
    				success:function(response) {
    					var code = response.code;
    					if (code == "200") {
    						var d = dialog({
    						    title: '设置新密码',
    						    width:'500px',
    						    skin: 'reset-pwd-dlg',
    						    content: $('#reset-pwd-tmpl').render(),
    						    okValue: '提交',
    						    ok: function () {
    						    	var password = "";
    				    			var confirmPassword = "";
    				    			$(".reset-pwd-dlg input.password").each(function(index,item){
    						    		if(index==0){
    						    			password = $(item).val();
    						    		}
    						    		if(index==1){
    						    			confirmPassword = $(item).val();
    						    		}
    						    	});
    				    			if(password!=confirmPassword){
    				    				$.errorMsg("新密码和确认密码不一致！");
    				    				return false;
    				    			}
    				    			if(!password_regex.test(password)){
    				    				$.errorMsg("密码长度不对.长度必须是6~20位");
    				    				return false;
    				    			}
    						    	$.ajax({
    					    			type : "post",
    					    			url : "/user/reset_pwd4",
    					    			data : {
    					    				"password" : password
    					    			},
    					    			success : function(response) {
    					    				var code = response.code;
    					    				if ("200" == code) {
    					    					$.successMsg("提交成功,请使用新密码登录！");
    					    				} else {
    					        				$.errorMsg(response.message);
    					    				}
    					    			}
    					    		});
    						    },
    						    cancelValue: '取消',
    						    cancel: function () {}
    						});
    						d.showModal();
    					} else {
    						$.errorMsg(response.message);
    					}
    				}
    			});
            }
        });*/

        /*$('.question-forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.question-forget-form').validate().form()) {
                    $('.question-forget-form').submit();
                }
                return false;
            }
        });*/
        
        $(".question-next").click(function(){
        	var state = $(this).data("state");
        	if(state == "0"){
        		var areaCode = $(this).parent().parent().find(".question-area-code").val();
            	var telphone = $(this).parent().parent().find('.question-phone').val();
    			var email = $(this).parent().parent().find('.question-email').val();
    			
            	$.ajax({
        			type:"post",
        			url: "/asq/get_asq2",
        			data:{
        				"email":email,
        				"areaCode":areaCode,
        				"phone":telphone
        			},
        			success:function(response) {
        				var code = response.code;
        				if (code == "200") {
        					if(response.data==null){
        						$.errorMsg("您没有设置密保问题，请使用邮箱或者手机找回密码！");
        					}else{
        						$('.question-forget-form .form-group').remove();
        						$('.question-forget-form p').remove();
        						$('.question-forget-form .question-back-btn').html("上一步");
        						$('.question-forget-form .question-back-btn').data("state","1");
        						$('.question-forget-form .question-next').data("state","1");
        						$('.question-forget-form .question-next').data("userid",response.data.userId);
        						$('.question-forget-form .form-actions').before($('#question-two-tmpl').render({asq:response.data}));
        					}
        				} else {
        					$.errorMsg(response.message);
        				}
        			}
        		});
        	}else if(state == "1"){
        		var userId = $(this).data("userid");
        		var answer1 = "";
		    	var answer2 = "";
		    	var answer3 = ""; 
		    	$(".question-forget-form input[name='answer']").each(function(index,item){
		    		if(index==0){
		    			answer1 = $(item).val();
		    		}
		    		if(index==1){
		    			answer2 = $(item).val();
		    		}
		    		if(index==2){
		    			answer3 = $(item).val();
		    		}
		    	});
        		$.ajax({
	    			type : "post",
	    			url : "/asq/validate",
	    			data : {
	    				"answer1" : answer1,
	    				"answer2" : answer2,
	    				"answer3" : answer3,
	    				"userId" : userId,
	    			},
	    			success : function(response) {
	    				var code = response.code;
	    				if ("200" == code) {
	    					//输入新密码
	    					$('.question-forget-form .form-group').remove();
    						$('.question-forget-form p').remove();
    						$('.question-forget-form .question-back-btn').html("上一步");
    						$('.question-forget-form .question-back-btn').data("state","2");
    						$('.question-forget-form .question-next').data("state","2");
    						$('.question-forget-form .question-next').html("提交");
    						$('.question-forget-form .form-actions').before($('#question-third-tmpl').render());
	    				} else { 
	        				$.errorMsg(response.message);
	    				}
	    			}
	    		});
        	}else if(state=="2"){
        		var pwd = $('.question-forget-form input[name="pwd"]').val()
        		var nwePwd = $('.question-forget-form input[name="newPwd"]').val()
        		if(pwd!=nwePwd){
    				$.errorMsg("密码和确认密码不一致！");
    				return false;
    			}
        		
    			if(!password_regex.test(pwd)){
					$.errorMsg("密码长度不对.长度必须是6~20位");
					return false;
				}
    			
        		$.ajax({
	    			type : "post",
	    			url : "/user/reset_pwd3",
	    			data : {
	    				"password" : pwd,
	    			},
	    			success : function(response) {
	    				var code = response.code;
	    				if ("200" == code) {
	    					alert("密码重置成功，请使用新密码登录");
	    					window.location.href = "/login.jsp";
	    				} else { 
	        				$.errorMsg(response.message);
	    				}
	    			}
	    		});
        	}
        	
        })
        /*密保找回密码 end*/
    }

    var handleRegister = function() {

        $('.register-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                tnc: {
                    required: true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
                tnc: {
                    required: "您还未接受用户协议 、 诚信规范服务承诺 、 大牛社区服务协议"
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
                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },

            submitHandler: function(form) {
                //form.submit();
            	var name = $(":text[name='reg-phone-username']").val();
    			var vcode = $(":text[name='reg-phone-verifycode']").val();
    			var areaCode = $(".register-form").find(".area-code").val();
    			var telphone = $(":text[name='reg-phone']").val();
    			var password = $(":password[name='reg-phone-password']").val();
    			var confirmPassword = $(":password[name='reg-phone-confirm-password']").val();
    			var recommend = $(":text[name = 'reg-phone-recommend']").val();
    			var recommender = $.cookie("userRecommender");
    			if (recommender == null || recommender == "") {
    				recommender = "-1";
    			}
    			if(password!=confirmPassword){
    				$.errorMsg("密码和确认密码不一致！");
    				return false;
    			}
    			var validatePwd = function (password){
    				if(!password_regex.test(password)){
    					$.errorMsg("密码长度不对.长度必须是6~20位");
    					return false;
    				}
    				return true;
    			}
    			
    			var validaVerifycode = function (verifycode){
    				if(verifycode.length!=6){
    					$.errorMsg("验证码长度不对,请输入正确的验证码.");
    					return false;
    				}
    				return true;
    			}
    			
    			var validaTelphone =function (telphone){
    	    		if(!phone_regex.test(telphone)){
    	    			$.errorMsg("手机号码不正确，请输入正确手机号码！");
    	    			return false;
    	    		}
    	    		return true;
    			}
    			
    			
    			if(!validaTelphone(telphone)||!validatePwd(password)||!validaVerifycode(vcode)){
    				return false;
    			}
    			
    			$.ajax({
    				type:"post",
    				url:$(form).attr("action"),
    				data:{
    					"name" : name,
    					"password" : password,
    					"areaCode" : areaCode,
    					"phone" : telphone,
    					"vcode" : vcode,
    					"recommender" : recommender,
    					"recommend" : recommend,
    					"isloging" : "1"
    				},
    				success:function(response) {
    					var code = response.code;
    					if (code == "200") {
    						$.successMsg("注册成功");
    						window.location.href = "/dashboard/Account/setting.jsp";
    					} else {
    						$.errorMsg(response.message);
    					}
    				}
    			});
            }
        });

        $('.register-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.register-form').validate().form()) {
                    $('.register-form').submit();
                }
                return false;
            }
        });
        
       
        $('#register-get-verifycode-btn').click(function() {
        	var telphone = $(":text[name='reg-phone']").val();
        	var validaTelphone =function (telphone){
	    		if(!phone_regex.test(telphone)){
	    			$.errorMsg("手机号码不正确，请输入正确手机号码！");
	    			return false;
	    		}
	    		return true;
			}
        	if(!validaTelphone(telphone)){
				return false;
			}
        	
        	var enable = $(this).data().enable;
        	if(enable=="0"){
        		return false;
        	} 
        	var me = $(this);
        	$.ajax({
				type:"post",
				url:"code/phone",
				data:{
					"areaCode":$(".register-form").find(".area-code").val(),
					"phone":telphone
				},
				success:function(response) {
					var code = response.code;
					if (code == "200") {
						//按钮不可用
						me.data('enable','0');
			    		settime(me,60); 
						$.successMsg("验证码已发送，请查看手机");
					}else{
						$.errorMsg(response.message);
					}
				}
			});
        });
        
        
        $('.email-register-form').validate({
        	errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                tnc: {
                    required: true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
                tnc: {
                    required: "您还未接受用户协议 、 诚信规范服务承诺 、 大牛社区服务协议"
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
                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },

            submitHandler: function(form) {
                //form.submit();
            	var name = $(":text[name='reg-email-username']").val();
    			var email = $(":text[name='reg-email']").val();
    			var password = $(":password[name='reg-email-password']").val();
    			var confirmPassword = $(":password[name='reg-email-confirm-password']").val();
    			var recommend = $(":text[name = 'reg-email-recommend']").val();
    			var vcode = $(form).find(':text[name="login-code"]').val()
    			var recommender = $.cookie("userRecommender");
    			if (recommender == null || recommender == "") {
    				recommender = "-1";
    			}
    			if(password!=confirmPassword){
    				$.errorMsg("密码和确认密码不一致！");
    				return false;
    			}
    			if(!password_regex.test(password)){
    				$.errorMsg("密码长度不对.长度必须是6~20位");
    				return false;
    			}
    			if(!email_regex.test(email)){
    				$.errorMsg("邮箱格式错误，请输入正确的邮箱！");
    				return false;
    			}; 
    			
    			
    			$.ajax({
    				type:"post",
    				url:$(form).attr("action"),
    				data:{
    					"password":password,
    					"name":name,
    					"email":email,
    					"recommender":recommender,
    					"recommend":recommend,
    					"isloging":"1",
    					"vcode" : vcode
    				},
    				success:function(response) {
    					var code = response.code;
    					if (code == "200") {
    						$.successMsg("注册成功");
    						window.location.href = "/dashboard/Account/setting.jsp";
    					}else if(code == "12026"){
		    				//验证码
           					$(form).find(".show-login-code").removeClass("hide");
		    				resetValidateCode($(form).find(".refresh-code").prev());
		    				$.errorMsg(response.message);
		    			}else if(code == "12024"){
							window.location.href = "/forbidden/userForbidden.jsp";
						}else {
    						$.errorMsg(response.message);
    					}
    				}
    			});
            }
        });

        $('.email-register-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.email-register-form').validate().form()) {
                    $('.email-register-form').submit();
                }
                return false;
            }
        });
        
        $(":text[name='reg-email-username'], :text[name='reg-phone-username']").blur(function() {
        	if(!nick_name_regex.test($(this).val())){
				$.errorMsg("用户名仅支持3-20个字母、数字、下划线，以字母开头");
				return false;
			};
		});
    }

    var handleThirdLogin = function(){
    	$(".facebook").click(function(){
    		  FB.login(function(response) {
    			if (response.authResponse) {
    				console.log(response);
    				console.log('Welcome!  Fetching your information.... ');
    				var token = response.authResponse.accessToken;
    				var id = response.authResponse.userID;
    				var name ="";
    				var email = "";
    			    FB.api('/me?fields=name,first_name,last_name,email,picture', function(response) {
    			    	$.ajax({
        					type : "post",
        					url : "user/fb_login",
        					data : {
        						"fbToken" : token,
        						"fbId" : id,
        						"fbName" : response.name,
        						"fbEmail": response.email,
        						"isloging": "1",
        						"picture" : response.picture.data.url
        					},
        					success : function(response) {
        						var code = response.code;
        						if (code == "200") {
        							window.location.href = "/index.jsp";
        						}else if(code == "12024"){
        							window.location.href = "/forbidden/userForbidden.jsp";
        						}else  {
        							$.errorMsg(response.message);
        						}
        					}
        				});
    			    });
    			    
    			} else {
    			 	console.log('User cancelled login or did not fully authorize.');
    			}
    		}, {scope: 'email,public_profile'});
    		return false;
    	  })
    	  $(".weixin").click(function(){
    		  var dlg = dialog({
    			  title : '微信登录',
    			  skin: 'dlg-wechat',
    			  content: '<div id="winxin-content"></div>'
    		  })
    		  dlg.showModal();
    		  var obj = new WxLogin({
                  id:"winxin-content", 
                  appid: "wxffe1847ad9cb0c56", 
                  scope: "snsapi_login", 
                  redirect_uri: encodeURIComponent("http://www.anyonehelps.com/user/wx_login"), 
                  state: "",
                  style: "",
                  href: ""
    		  });
    	  });
    }
    
    return {
        //main function to initiate the module
        init: function() {
        	handleInit();
            handleLogin();
            handleForgetPassword();
            handleRegister();
            handleThirdLogin();
        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
});