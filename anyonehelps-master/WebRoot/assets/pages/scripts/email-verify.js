
$(function() {

	 function emailVerify() {
        var email = $(":hidden[name='email']").val();
    	var token = $(":hidden[name='token']").val();
    	$.ajax({
    		type:"post",
    		url:"user/email_verify2",
    		data:{
    			"email":email,
    			"token":token,
    			"vcode":"1234"
    		},
    		success:function(response) {
    			$("#verify-result").html(response.message);
    			return false;
    		}
    	});
        
     }
	 emailVerify()
});