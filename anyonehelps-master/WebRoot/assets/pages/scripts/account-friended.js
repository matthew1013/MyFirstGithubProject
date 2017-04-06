$.views.tags({
	"substrOfContent": function(content){
    	var c = $("<p>"+content+"</p>").text();
    	if(c.length>200){
    		return c.substr(0,200)+ '...';
    	}else {
    		return c;
    	}
    	
    },
    "countryFormat":function(country){
     	if(country == "us"){return "美国"}
     	else if(country == "cn"){return "中国"}
     	else if(country == "au"){return "澳洲"}
     	else if(country == "ca"){return "加拿大"}
     	else if(country == "uk"){return "英国"}
     	else if(country == "other"){return "其他"}
     	else {return "未知"}
     	
     },
    "expireDateFormat":function(expireDate){
     	var expireDateDate = new Date(Date.parse(expireDate.replace(/-/g, "/")));
     	var currentDate = new Date();
     	var time=expireDateDate.getTime()-currentDate.getTime();
     	var month=Math.floor(time/(30*24*60*60*1000));
     	var day=Math.floor(time/(24*60*60*1000));
     	var hours=Math.floor((time%(24*3600*1000)) /(60*60*1000));
     	var hours=Math.floor((time%(24*3600*1000)) /(60*60*1000));
     	if(month!="0"){
     		return month+"个月";
     	}else if(day!="0"&&day!="1"){
     		return day+"天";
     	}else if(day=="1"){
     		return (24+hours)+"小时";
     	}else{
     		return hours+"小时";
     	}
     	
     },
      "typeFormat": function(type){
    	  var strType = "其他";
    	  if(type=="1"){
    		  strType = "<div class='content-label'>学习</div>";
      	  }else if(type=="2"){
      		  strType = "<div class='content-label-2'>生活</div>";
      	  }else if(type=="3"){
      		strType = "<div class='content-label-3'>工作</div>";
      	  }
      	  return strType;
      },
      "tagFormat": function(tag){
    	  var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    	  var count = 0;  //读取多少个字了
    	  var strTag = "<div>";
    	  
    	  var tagArray = tag.split("#");		
    	  for(var k=0;k<tagArray.length;k++){		
    		  if(tagArray[k]!=""&&tagArray[k]!=null){
    			  	var bExist = false; //是否含有中文
    				if(reg.test(tagArray[k])){ //含有中文
    					count += 2*tagArray[k].length+2; //加2是井号长度
    					bExist = true;
    		     	}else {  //没有中文
    		     		count += tagArray[k].length+2;
    		     	}
    				
    				if(count<30){
    					strTag += "<a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'>#"+tagArray[k]+"</a>";
    				}else{
    					if(bExist){ //含有中文
    						if(count-(2*tagArray[k].length+2)==0){  //第一个标签就超出范围
    							strTag += "<a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'>#"+tagArray[k].substr(0,13)+"...</a>";
        					}
        		     	}else {  //没有中文
        		     		if(count-(tagArray[k].length+2)==0){    //第一个标签就超出范围
        		     			strTag += "<a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'>#"+tagArray[k].substr(0,26)+"...</a>";
        					}
        		     	}
    					
    				}
    		  }
    	  }
    	  strTag += "</div>"
    	  return strTag;
  	 }, 
     "MessageCount":function(Messages){
    	var messageCount = 0;
		if (Messages != null) {
			$.each(Messages, function(e) {
				messageCount +=this.replyMessages.length;
			})
			messageCount += Messages.length;
		}
		return messageCount;
     },
     /*"evaluateCountFormat": function(evaluateAcceptedCount, evaluatePublishCount){
 		 return parseFloat(evaluateAcceptedCount) + parseFloat(evaluatePublishCount);
 	 }*/
});

$(function() {
	var userInit = function(){
		$("title").html("关注我的人 | 用户中心 | AnyoneHelps");
		$(".nav1").addClass("active open");
		$(".nav15").addClass("active open");
	}
    
	
	function loadUser(key,size,pn) {
		
		$.ajax({
			type : "get",
			url : "/friend/search_ed",
			data : {
				"key" : key,
				"pageSize" : size,
				"pageIndex" : pn
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showUserlist(response.data.datas);
						showUserlink(response.data);
					} else {
						showUserlist(null);
						showUserlink(null);
					}
					//afterloadcwjllist();
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
	}
	
	var cancelUser = function(id) {
		var ids = [];
		ids.push(id);
		
		var pn = $("#pageSplit").find("[class='page active']").find("a").html()
		$.ajax({
			type:"post",
			url:"/friend/delete",
			data:$.param({
				"ids":ids
			},true),
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					//loadUser("",10,pn);
				} else if ("607" == code) {
					//跳至登录处
					alert("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	
	function showUserlist(list) {
		if(list!=null){
			console.log(list);
    		var friendlist = $('.friend-list').html($('#user-item-tmpl').render({dataList: list}));
    		friendlist.find('.follow-btn').click(function(){
    			var obj = $(this);
   	    	 	var userId = $(this).data("userid");
   	    	 	var chk_value = [];
   	    	 	chk_value.push(userId);
   	    	 	
   	    	 	$.ajax({
	    	 		type : 'post',
	    	 		url : '/friend/add',
	    	 		data :$.param({
	    	 			"ids" : chk_value
	    	 		}, true) ,
	    	 		success : function(response) {
	    	 			console.log(response);
	    	 			var code = response.code;
	    	 			if (code=="200") {
	    	 				obj.parent().addClass("hide");
	    	 				obj.parent().next().removeClass("hide");
	    	 			} else if ("607" == code) {
	    	 				alert("您尚未登录或登录已失效！请登录后再关注！");
	    	 				logout();
	    	 			} else {
	    	 				alert(response.message);
	    	 			}

	    	 		},
	    	 	});
    		})
    		friendlist.find('.follow.dropdown>.dropdown-menu>li>a').click(function(){
    			var obj = $(this);
   	    	 	var userId = $(this).data("userid");
   	    	 	var chk_value = [];
   	    	 	chk_value.push(userId);
   	    	 	
   	    	 	var d = dialog({
	    			title: '提示',
	    			width:'300px',
	    			skin: 'tip-dlg',
	    			content: '您确定取消关注吗?',
	    			okValue: '确定',
	    			ok: function () {
	    				this.title('提交中…');

	    				$.ajax({
	    	    	 		type : 'post',
	    	    	 		url : '/friend/delete',
	    	    	 		data :$.param({
	    	    	 			"ids" : chk_value
	    	    	 		}, true),
	    	    	 		success : function(response) {
	    	    	 			console.log(response);
	    	    	 			var code = response.code;
	    	    	 			if (code=="200") {
	    	    	 				obj.parent().parent().parent().addClass("hide");
	    	    	 				obj.parent().parent().parent().prev().removeClass("hide");
	    	    	 			} else if ("607" == code) {
	    	    	 				alert("您尚未登录或登录已失效！请登录后再取消关注！");
	    	    	 				logout();
	    	    	 			} else {
	    	    	 				alert(response.message);
	    	    	 			}

	    	    	 		},
	    	    	 	});
	    			},
	    			cancelValue: '取消',
	    			cancel: function () {}
	    		});
	    		d.showModal();
	    		return false;
    		})
    		/*friendlist.find('.follow-btn').click(function(){
    			 var obj = $(this);
    	    	 var follow = $(this).data("follow");
    	    	 var userId = $(this).data("userid");
    	    	 var chk_value = [];
    	 		 chk_value.push(userId);
    	    	 if(follow=="1"){
    	    		var d = dialog({
    	    			title: '提示',
    	    			width:'300px',
    	    			skin: 'tip-dlg',
    	    			content: '您确定取消关注吗?',
    	    			okValue: '确定',
    	    			ok: function () {
    	    				this.title('提交中…');

    	    				$.ajax({
    	    	    	 		type : 'post',
    	    	    	 		url : '/friend/delete',
    	    	    	 		data :$.param({
    	    	    	 			"ids" : chk_value
    	    	    	 		}, true),
    	    	    	 		success : function(response) {
    	    	    	 			console.log(response);
    	    	    	 			var code = response.code;
    	    	    	 			if (code=="200") {
    	    	    	 				obj.html("关注");
    	    	    	 				obj.data("follow","0");
    	    	    	 			} else if ("607" == code) {
    	    	    	 				alert("您尚未登录或登录已失效！请登录后再取消关注！");
    	    	    	 				logout();
    	    	    	 			} else {
    	    	    	 				alert(response.message);
    	    	    	 			}

    	    	    	 		},
    	    	    	 	});
    	    			},
    	    			cancelValue: '取消',
    	    			cancel: function () {}
    	    		});
    	    		d.showModal();
    	    	 }else{
    	    		 $.ajax({
    	    	 		type : 'post',
    	    	 		url : '/friend/add',
    	    	 		data :$.param({
    	    	 			"ids" : chk_value
    	    	 		}, true) ,
    	    	 		success : function(response) {
    	    	 			console.log(response);
    	    	 			var code = response.code;
    	    	 			if (code=="200") {
    	    	 				obj.html("取消关注");
    	    	 				obj.data("follow","1");
    	    	 			} else if ("607" == code) {
    	    	 				alert("您尚未登录或登录已失效！请登录后再关注！");
    	    	 				logout();
    	    	 			} else {
    	    	 				alert(response.message);
    	    	 			}

    	    	 		},
    	    	 	});
    	    	 }
    	     })*/
    	}else{
    		$('.friend-list').html('<div class="no-data">暂无关注我的人~~</div>');
    	}
	}

	function showUserlink(pageSplit) {
    	
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
                	loadUser("", pageSize, page);
                }
            });
    	}
    }
	
	userInit();
	loadUser("", 10, 1);
});