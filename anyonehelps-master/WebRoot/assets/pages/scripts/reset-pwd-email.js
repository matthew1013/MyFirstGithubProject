
$(function() {

	$('.reset-pwd-form').validate({
        submitHandler: function(form) {
        	var email = $(":hidden[name='email']").val();
    		var token = $(":hidden[name='token']").val();
    		var newpwd = $(":password[name='newpwd']").val();
    		var confirmpwd = $(":password[name='confirmpwd']").val();
    		if(newpwd=="")
    		{
    			alert("必须输入新密码！");
    			return false;
    		}
    		if(newpwd!=confirmpwd)
    		{
    			alert("新密码输入不一致，请重新输入!");
    			$(":password[name='newpwd']").val("");
    			$(":password[name='confirmpwd']").val("");
    			return false;
    		}
    		$.ajax({
    			type:"post",
    			url:"user/reset_pwd2",
    			data:{
    				"email":email,
    				"token":token,
    				"password":newpwd,
    				"vcode":"1234"
    			},
    			success:function(response) {
    				var code = response.code;
    				if (code == "200") {
    					alert("重置密码成功，请登录！");
    					window.location.href = "login.jsp";
    				} else {
    					alert(response.message);
    					//window.location.href = "resetPwd.html";
    				}
    				return false;
    			}
    		});
        	
        }
    });

    $('.reset-pwd-form input').keypress(function(e) {
        if (e.which == 13) {
            if ($('.reset-pwd-form').validate().form()) {
                $('.reset-pwd-form').submit();       
            }
            return false;
        }
    });

});