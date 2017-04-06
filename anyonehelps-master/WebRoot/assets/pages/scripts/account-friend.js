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
     }/*,
     "evaluateCountFormat": function(evaluateAcceptedCount, evaluatePublishCount){
 		 return parseFloat(evaluateAcceptedCount) + parseFloat(evaluatePublishCount);
 	 }*/
});

$(function() {
	var friendInit = function(){
		$("title").html("我关注的人 | 用户中心 | AnyoneHelps");
		$(".nav1").addClass("active open");
		$(".nav14").addClass("active open");
	}
    
	
	function loadFriend(key,size,pn) {
		
		$.ajax({
			type : "get",
			url : "/friend/search",
			data : {
				"key" : key,
				"pageSize" : size,
				"pageIndex" : pn
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showFriendlist(response.data.datas);
						showFriendlink(response.data);
					} else {
						showFriendlist(null);
						showFriendlink(null);
					}
					//afterloadcwjllist();
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
	}
	
	var cancelFriend = function(id) {
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
					//loadFriend("",10,pn);
				} else if ("607" == code) {
					//跳至登录处
					alert("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	
	function showFriendlist(list) {
		console.log(list)
		if(list!=null){
    		var friendlist = $('.friend-list').html($('#user-item-tmpl').render({dataList: list}));
    		friendlist.find('.follow-btn').click(function(){
    			var obj = $(this);
    			var d = dialog({
    			    title: '提示',
    			    width:'300px',
    			    skin: 'tip-dlg',
    			    content: '您确定取消关注吗?',
    			    okValue: '确定',
    			    ok: function () {
    			        this.title('提交中…');
    			        var userId = obj.data().userid;
    	    			cancelFriend(userId);
    	    			obj.parent().parent().parent().parent().remove();
    			    },
    			    cancelValue: '取消',
    			    cancel: function () {}
    			});
    			d.showModal();
    		});
    		friendlist.find('.follow.dropdown>.dropdown-menu li a').click(function(){
    			var obj = $(this);
    			var d = dialog({
    			    title: '提示',
    			    width:'300px',
    			    skin: 'tip-dlg',
    			    content: '您确定取消关注吗?',
    			    okValue: '确定',
    			    ok: function () {
    			        this.title('提交中…');
    			        var userId = obj.data().userid;
    	    			cancelFriend(userId);
    	    			obj.parent().parent().parent().parent().parent().parent().remove();
    			    },
    			    cancelValue: '取消',
    			    cancel: function () {}
    			});
    			d.showModal();
    		});
    	}else{
    		$('.friend-list').html('<div class="no-data">暂无关注用户~~</div>');
    	}
	}

	function showFriendlink(pageSplit) {
    	
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
        		    loadFriend("", pageSize, page);
                }
            });
    	}
    }
	
	friendInit();
	loadFriend("", 10, 1);
});