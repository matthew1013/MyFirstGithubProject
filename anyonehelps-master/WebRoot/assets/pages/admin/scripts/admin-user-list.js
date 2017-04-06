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
     "evaluateCountFormat": function(evaluateAcceptedCount, evaluatePublishCount){
 		 return parseFloat(evaluateAcceptedCount) + parseFloat(evaluatePublishCount);
 	 },
});

$(function() {
	var userInit = function(){
		$("title").html("任务推荐 | 管理员中心 | AnyoneHelps");
		$(".nav1").addClass("active open");
		$(".nav14").addClass("active open");
	}
    
	
	var searchCondition = {
		taskId : $(":hidden[name='taskId']").val(),
	    sortType:"0",
	    key : "",
	    country : "",
	    type : "",
	    pageIndex : 1,
	    pageSize : 20
	};
	userInit();
	if(searchCondition.taskId){
		loadUser();
	}else{
		alert("推荐任务错误！");
	}
	
	$(".search-start").click(function(){
    	g_flag = true;
    	searchCondition.sortType = $("select[name='sort']").val();
		searchCondition.key = $(".search-key").val();
		searchCondition.country = $("select[name='location']").val();
		searchCondition.type = $("select[name='type']").val();
		searchCondition.pageIndex = "1";
		loadUser();
    })
	
	function loadUser() {
		
		$.ajax({
			type : "get",
			url : "/admin/user_search",
			data : {
				"specialtyTypeId" : searchCondition.type,
			    "sortType" : searchCondition.sortType,
			    "country" : searchCondition.country,
				"key" : searchCondition.key,
				"demandId" : searchCondition.taskId,
				"pageSize" : searchCondition.pageSize,
				"pageIndex" : searchCondition.pageIndex
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
	
	var recommendUser = function(userId,obj) {
		$.ajax({
			type:"post",
			url:"/admin/user_recommend",
			data:$.param({
				"userId":userId,
				"demandId":searchCondition.taskId
			},true),
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					alert("推荐成功！");
					obj.html("再次推荐");
				} else if ("607" == code) {
					//跳至登录处
					alert("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} else {
					alert(response.message);
				}
			}
		});
		return false;
	}
	
	function showUserlist(list) {
		if(list!=null){
    		$('.user-list').html($('#user-item-tmpl').render({dataList: list}));
    		$('.recommend-btn').click(function(){
    			var userId = $(this).data().userid;
    			recommendUser(userId,$(this));
    		});
    	}else{
    		$('.user-list').html('暂无用户~~');
    		$(".search-pagination").html('<ul class="pagination"></ul>'); 
    	}
	}
	//g_page_size g_flag 决定重新分页
    var g_page_size=0;
    var g_flag=false; 
	function showUserlink(pageSplit) {
    	
		if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		if(g_page_size != pageCount && g_flag){  
    		    $(".search-pagination").html('<ul class="pagination"></ul>');  
    		    g_flag = false;
    		}  
    		g_page_size = pageCount;
    		$(".pagination").twbsPagination({
                totalPages: pageCount,
                first: '首页',
                prev: '上一页',
                next: '下一页',
                last: '最后一页',
                onPageClick: function (event, page) {
                	searchCondition.pageIndex = page;
        		    loadUser();
                }
            });
    	}
    }
});