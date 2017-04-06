$.views.helpers({
    "isTimeout":function(modifyDate){
   	 }
});



$(function() {
	var inviteInit = function(){
		$("title").html("邀请好友 | 奖励中心 | AnyoneHelps");
		$(".nav4").addClass("active open");
		$(".nav41").addClass("active open");
	}
	inviteInit();
	
	//二维码
	$('.invited .qr-code .right>div').qrcode({
		text:utf16to8($(".invited .recommender-link .middle .copy-link .link").html()),
		width : 120,     //设置宽度  
		height : 120,     //设置高度
	});
	function utf16to8(str) {  
	    var out, i, len, c;  
	    out = "";  
	    len = str.length;  
	    for(i = 0; i < len; i++) {  
	    c = str.charCodeAt(i);  
	    if ((c >= 0x0001) && (c <= 0x007F)) {  
	        out += str.charAt(i);  
	    } else if (c > 0x07FF) {  
	        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));  
	        out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));  
	        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));  
	    } else {  
	        out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));  
	        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));  
	    }  
	    }  
	    return out;  
	}  
	
	var clipboard = new Clipboard('.invited .recommender-link .middle .copy-link, .invited .recommender-link .right .copy-btn');

    clipboard.on('success', function(e) {
    	$.successMsg("已经复制框中链接");
        console.log(e);
    });

    clipboard.on('error', function(e) {
        console.log(e);
    });
	
	loadRecommendedInfo();
	function loadRecommendedInfo(){
    	// 获取用户数据
    	$.ajax({
    		type:"get",
    		url:"/user/get_recommended",
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") {
    				var data = response.data;
    				if(data!=null){
    					$(".invited .email .account").val(data.nickName);
    					$(".invited .email .account").attr("readonly","readonly");
    					$(".invited .email .account").addClass("user-pic");
    					$(".invited .email .account").data("id",data.id);
    					$(".invited .email .desc").html("邀请我的用户:");
    					$(".invited .email .invited-submit").addClass("hide");
    					$(".invited .phone").addClass("hide");
    				}
    			} else if ("607" == code) {
    				$.errorMsg("您尚未登录或登录已失效！");
    				logout();
    			} 
    		}
    	});
    }	
	
	$('.invited-submit').click(function(){
		var account = $(this).parent().parent().find(".account").val();
		$.ajax( {
			type : "post",
			url : "/user/modify_recommender",
			data : $.param({
				"account" : account
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("提交成功！");
					var data = response.data;
    				if(data!=null){
    					$(".invited .email .account").val(data.nickName);
    					$(".invited .email .account").attr("readonly","readonly");
    					$(".invited .email .account").addClass("user-pic");
    					$(".invited .email .account").data("id",data.id);
    					$(".invited .email .desc").html("邀请我的用户:");
    					$(".invited .email .invited-submit").addClass("hide");
    					$(".invited .phone").addClass("hide");
    				}
				} else if ("607" == code) {
		           	$.errorMsg("您尚未登录或登录已失效！");
		           	logout();
		        } else {
		           	$.errorMsg(response.message);
		        }
				return false;
			}
		});
	});
	
	$('.invited-phone-submit').click(function(){
		var areaCode = $(this).parent().parent().find(".area-code").val();
		var phone = $(this).parent().parent().find(".phone").val();
		$.ajax( {
			type : "post",
			url : "/user/modify_recommender_phone",
			data : $.param({
				"areaCode" : areaCode,
				"phone" : phone
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("提交成功！");
					var data = response.data;
    				if(data!=null){
    					$(".invited .email .account").val(data.nickName);
    					$(".invited .email .account").attr("readonly","readonly");
    					$(".invited .email .account").addClass("user-pic");
    					$(".invited .email .account").data("id",data.id);
    					$(".invited .email .desc").html("邀请我的用户:");
    					$(".invited .email .invited-submit").addClass("hide");
    					$(".invited .phone").addClass("hide");
    				}
				} else if ("607" == code) {
		           	$.errorMsg("您尚未登录或登录已失效！");
		           	logout();
		        } else {
		           	$.errorMsg(response.message);
		        }
				return false;
			}
		});
	});
	
	
	$('#invite-phone-btn').click(function(){
		var obj = $(this);
		var areaCode = $(this).parent().parent().find(".area-code").val();
		var telphone = $.trim($(this).parent().parent().find(".invite-phone").val());
		if(areaCode ==''|| telphone ==''){
			$.errorMsg('请输入被邀请人');
			return false;
		}
		$.ajax( {
			type : "post",
			url : "/recommend/add_phone",
			data : $.param({
				"areaCode" : areaCode,
				"phone" : telphone
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("发送邀请成功！");
					obj.parent().parent().find('.invite-phone').val("");
		           	recommendSearch("","",10,1);
				} else if ("607" == code) {
		           	$.errorMsg("您尚未登录或登录已失效！");
		           	logout();
		        } else {
		           	$.errorMsg(response.message);
		        }
				return false;
			}
		});
	});
	
	$('#invite-btn').click(function(){
		var users = $.trim($('#invite-users').val());
		if(users ==''){
			$.errorMsg('请输入被邀请人');
			return false;
		}
		var chk_value = users.split(',');
		$.ajax( {
			type : "post",
			url : "/recommend/add",
			data : $.param({
				"recommenders" : chk_value
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					if(response.data==null||response.data==""){
						$.successMsg("发送邀请成功！");
		           	}else{
		               	var str = "";
		               	$.each(response.data, function() {
		                   	str += this+",";
		               	});
		               	$.errorMsg(str+"已经注册，或者在其他人邀请期内");
		           	}
		            $('#invite-users').val("");
		           	recommendSearch("","",10,1);
				} else if ("607" == code) {
		           	$.errorMsg("您尚未登录或登录已失效！");
		           	logout();
		        } else {
		           	$.errorMsg(response.message);
		        }
				return false;
			}
		});
	});
	
	function recommendSearch(key,state,size,pn) {
		$.ajax( {
	     	type : "get",
	      	url : "/recommend/search",
	      	data : $.param({
	      		"key" : key,
	           	"state" : state,
	           	"pageIndex" : pn,
	          	"pageSize" : size
	    	}, true),
	      	success : function(response) {
	      		var code = response.code;
	          	var html = '';
	          	if ("200" == code) {
	            	if(response.data){
	                 	html = $('#invite-list-tmpl').render(response.data);
	     				showPagelink(response.data);
	              	}else{
	              		html = '<tr><td colspan="99">' + response.message + '</td></tr>';
	     				showPagelink(null);
	              	}
	            	$('#invite-list').html(html)
	            } else if ("607" == code) {
	            	alert("您尚未登录或登录已失效！");
	            	logout();
	            } else {
	            	$.errorMsg(response.message);
	            }
	          	return false;
	      	}
		});
		return false;
	}
	
	recommendSearch("","",10,1);
	
	$(window.document).on('click', '.reinvite-btn', function(){
		var data = $(this).data();
		$.ajax( {
            type : "get",
            url : "/recommend/refresh",
            data : $.param({
            	"id" : data.id
            }, true),
            success : function(response) {
            	var code = response.code;
            	if ("200" == code) {
                	$.successMsg('再次邀请成功。')
               	} else if ("607" == code) {
                	$.errorMsg("您尚未登录或登录已失效！");
                	logout();
            	} else {
                	$.errorMsg(response.message);
               	}
               	return false;
            }
		});
	});
	

	function showPagelink(pageSplit) {
    
		if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$("#pageSplit").twbsPagination({
                totalPages: pageCount,
                first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
                //href: '?a=&page={{number}}&c=d' ,
                onPageClick: function (event, page) {
                	recommendSearch("","",pageSize,page);
                }
            });
    	}
    }
	
	
	
	
	$('#tab_1_3 .invite-phone-btn').click(function(){
		var obj = $(this);
		var areaCode = obj.parent().parent().find(".area-code").val();
		var telphone = $.trim(obj.parent().parent().find(".invite-phone").val());
		if(areaCode ==''|| telphone ==''){
			$.errorMsg('请输入被邀请人');
			return false;
		}
		$.ajax( {
			type : "post",
			url : "/recommend/user_add_phone",
			data : $.param({
				"areaCode" : areaCode,
				"phone" : telphone
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("请求关联发送成功，请等待对方确认！");
					obj.parent().parent().find('.invite-phone').val("");
		           	recommendSearch("","",10,1);
				} else if ("607" == code) {
		           	$.errorMsg("您尚未登录或登录已失效！");
		           	logout();
		        } else {
		           	$.errorMsg(response.message);
		        }
				return false;
			}
		});
	});
	
	$('#tab_1_3 .invite-btn').click(function(){
		var obj = $(this);
		var user = $.trim(obj.parent().parent().find('.user').val());
		if(user ==''){
			$.errorMsg('请输入关联用户的邮箱或者用户名');
			return false;
		}
		$.ajax( {
			type : "post",
			url : "/recommend/user_add",
			data : $.param({
				"recommender" : user
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("请求关联发送成功，请等待对方确认！");
					obj.parent().parent().find('.user').val("");
		           	recommendSearch("","",10,1);
				} else if ("607" == code) {
		           	$.errorMsg("您尚未登录或登录已失效！");
		           	logout();
		        } else {
		           	$.errorMsg(response.message);
		        }
				return false;
			}
		});
	});
	
});