$.views.tags({
     "datetimeFormat":function(datetime){
    	 if(datetime == null||datetime==""){
    		 return "unknow";
    	 }
    	 return datetime.slice(0,datetime.indexOf(" "));
     }
});

$(function() {
	$("title").html("活动详情| 活动 | AnyoneHelps");

	$(".submit-btn").click(function(){
		/*var userId = $(".activity-detail").data("userid")
		var name = $(".activity-from").find("input[name='name']").val();
		var password = $(".activity-from").find("input[name='password']").val();
		var email = $(".activity-from").find("input[name='email']").val();
		var areaCode = $(".activity-from").find(".area-code").val();
		var telphone = $(".activity-from").find("input[name='telphone']").val();
		*/
		//var description = $(".activity-from").find("textarea[name='content']").val();
		
		var userId = $(".activity-detail").data("userid")
		var name = $(this).parent().parent().parent().parent().parent().parent().find("input[name='name']").val();
		var password = $(this).parent().parent().parent().parent().parent().parent().find("input[name='password']").val();
		var email = $(this).parent().parent().parent().parent().parent().parent().find("input[name='email']").val();
		var areaCode = $(this).parent().parent().parent().parent().parent().parent().find(".area-code").val();
		var telphone = $(this).parent().parent().parent().parent().parent().parent().find("input[name='telphone']").val();
		
		
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var password_regex = /^\S{6,20}$/;
		if(!reg.test(email)) {
			$.errorMsg("请输入联系邮箱！");
			return false;
		}
		
		if(userId==null||userId==""){
			//未登录校验
			if(name==null||name=="") {
				$.errorMsg("请输入有效的用户名！");
				return false;
			}
			if(!password_regex.test(password)){
				$.errorMsg("密码长度不对.长度必须是6~20位");
				return false;
			}
		}
		
		/*if(description==null||description=="") {
			$.errorMsg("请输入有效的内容！");
			return false;
		}*/
		$.ajax({  
			type: "POST",  
			url: "/activity_user/add",  
			data: $.param({
				"name" : name,
				"areaCode" : areaCode,
				"telphone" : telphone,
				"email" : email,
				//"description" : description,
				"activityId" : activityId,
				"password":password
			}, true),
			dataType: "json",  
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					var tipContent = "";
					if(userId==null||userId==""){
						tipContent = "注册并报名成功";
					}else{
						tipContent = "报名成功";
					}
					$("input[name='name']").val("");
					$("input[name='email']").val("");
					$("input[name='telphone']").val("");
					$("input[name='password']").val("");
					 
					var d = dialog({
					    /*width:'480px',*/
					    skin: 'tip-dlg',
					    onshow : function() {
					    	this.content($('#tip-dlg').render({content:tipContent}));
					    	$(".tip-dlg .tip-dlg-content .operate button").click(function(){
					    		d.close();
					    		window.location.href = $(".activity-detail").data("link");
					    	})
					    }
					});
					d.showModal();
				} else {
					$.errorMsg(response.message);
				}
			}
		})
	});  
	
	
	var loadActivityDetail = function(activityId) {
		$.ajax({
			type : "get",
			url : "/activity/get_one",
			data : {
				"id" : activityId,
			},
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					if(response.data!=null){
						$(".activity-detail").html($("#activity-detail-tmpl").render(response.data))
						$(".activity-detail").data("link",response.data.link);
         				
						$("title").html(response.data.title+"| "+$("title").html());
         				$("meta[name='keywords']").attr("content",response.data.title);
         				$("meta[name='description']").attr("content",response.data.title);
					}
				}
			}
		});
	}
	var activityId = $(".activity-detail").data("id")
	loadActivityDetail(activityId);
	
    $(window).scroll(function() {
    	//
    	if($(window).scrollTop()<=56){
    		$(".activity-from>div>.right").css("top",0);
    	}else{
    		$(".activity-from>div>.right").css("top",$(window).scrollTop()-56);
    	}

	});
    
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
    
   	if($(window).width()<975||!isPC()){
    	$(".activity-from>div>.right").css("position","initial");
    	$(".activity-from.activity-from-top").removeClass("hide");
    }else{
    	$(".activity-from>div>.right").css("position","absolute");
    }
	
    $(window).resize(function() {  
    	if($(window).width()<=975||!isPC()){
    		$(".activity-from>div>.right").css("position","initial");
        	$(".activity-from.activity-from-top").removeClass("hide");
    	}else{
    		$(".activity-from>div>.right").css("position","absolute");
        	$(".activity-from.activity-from-top").addClass("hide");
    	}
	});
});
