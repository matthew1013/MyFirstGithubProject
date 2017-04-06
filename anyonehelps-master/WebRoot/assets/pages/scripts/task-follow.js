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
     "locationFormat":function(district,province,country){
    	 var str = "";
    	 if(district){
    		str += district + ",";
    	 }
      	
      	if(province){
      		str += province + ",";
      	}
      	if(country){
      		str += country;
      	}
      	if(str==""){
      		str = "未分享";
      	}
      	return str;
      	
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
   	  	var strType = "<div class='type-other'>其他</div>";
   	  	if(type=="1"){
   	  		strType = "<div class='type-learn'>学习</div>";
   	  	}else if(type=="2"){
   	  		strType = "<div class='type-life'>生活</div>";
   	  	}else if(type=="3"){
   	  		strType = "<div class='type-work'>工作</div>";
   	  	}
   	  	return strType;
     },
     "tagFormat": function(tag){
   	  	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
   	  	var tagArray = tag.split("#");
   	  	var strTag = "";
   	 	for(var k=0;k<tagArray.length;k++){		
   	 		if(tagArray[k]!=""&&tagArray[k]!=null){
   	 			strTag += "<a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'><div class='tag-item margin-right-5'>#"+tagArray[k]+"</div></a>";
   	 		}
   	 	}
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
	 "amountFormat":function(amount){
    	 var x,y;
    	 if(amount == null){
    		 x = "0";
    		 y = null;
    	 }else{
    		 var tempArray = amount.split(".");
        	 x=formatNumber(tempArray[0]);
        	 y=tempArray[1];
    	 }
    	 var result = "<span class='amount-sm'>$</span><span>"+formatNumber(x)+"</span>";
    	 if(y!=null && y!="00"){
    		 result += "<span class='amount-sm'>"+y+"</span>";
    	 }
    	 return result;
     }
});


/**
 * 每三位数字加逗号
 * @param n
 * @returns
 */
function formatNumber(n){  
	var b = n.toString(); 
	return b.replace(/\B(?=(?:\d{3})+\b)/g, ',');
} 

$(function() {
	
	var searchCondition = {
		key : "",
		pageIndex : 1,
		pageSize : 10
	};
	var followInit = function(){
		$("title").html("关注的任务 | 任务中心| AnyoneHelps");
		$(".nav2").addClass("active open");
		$(".nav24").addClass("active open");
	}
	
	function loadFollow(key,size,pn) {
		
		$.ajax({
			type : "get",
			url : "/demand_follow/search",
			data : {
				"key" : searchCondition.key,
				"pageSize" : searchCondition.pageSize,
				"pageIndex" : searchCondition.pageIndex
			},
			success : function(response) {
				
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showFollowlist(response.data.datas);
						showFollowlink(response.data);
					} else {
						showFollowlist(null);
						showFollowlink(null);
					}
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
	}
	
	var cancelFollow = function(id) {
		var ids = [];
		ids.push(id);
		
		var pn = $("#pageSplit").find("[class='page active']").find("a").html()
		$.ajax({
			type:"post",
			url:"/demand_follow/delete",
			data:$.param({
				"ids":ids
			},true),
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					loadFollow();
				} else if ("607" == code) {
					//跳至登录处
					alert("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	
	function showFollowlist(list) {
		console.log(list);
		if(list!=null){
			var obj = $('.task-list').html($('#task-item-tmpl').render({dataList: list}));
			obj.find('.cancel-follow-btn').click(function(){
    			var demandId = $(this).data("demandid");
    			cancelFollow(demandId);
    			$(this).parent().parent().parent().remove();
    		});
			
			
			obj.find('.icon-add').click(function() {
				if($(this).hasClass("icon-minus")){  //展开
					$(this).parent().parent().parent().find(".achieve").addClass("hide");
					$(this).parent().parent().parent().find(".content").addClass("hide");
					$(this).removeClass("icon-minus");
				}else{                               //收起
					$(this).parent().parent().parent().find(".achieve").removeClass("hide");
					$(this).parent().parent().parent().find(".content").removeClass("hide");
					$(this).addClass("icon-minus");
				}
				
			})
			
			obj.find(".show-task-receive").click(function(){
				$(this).parent().parent().parent().find(".receive-demand").removeClass("hide");
    			$(this).addClass("hide");
    							
			})
			obj.find(".close-task-receive").click(function(){
				$(this).parent().parent().parent().parent().parent().find(".show-task-receive").removeClass("hide");
    			$(this).parent().parent().parent().parent().addClass("hide");
    		})
    		
    		obj.find(".receive-demand form").validate({
                submitHandler: function(form) {
                	var remark = $(form).find("textarea[name='receive_remark']").val();
                	var finishDay = $(form).find("input[name='finishDay']").val();
         			var id = $(form).data("demandid");
         			var amount = $(form).find("input[name='amount']").val();
         			var locationOpen = 0;
         			var locationName = "";
         			var locationCountry = "";
         			var locationProvince = "";
         			var locationDistrict = "";
         			var latitude = 0;
         			var longitude = 0;
         			
        	 		$.ajax({
        				type:"post",
        				url:"/demand/receive_add",
        				data:$.param({
        					demandId:id,
        					readme:remark,
        					finishDay:finishDay,
        					amount:amount,
        					locationOpen:locationOpen,
        					locationName:locationName,
        					locationCountry:locationCountry,
        					locationProvince:locationProvince,
        					locationDistrict:locationDistrict,
        					latitude:latitude,
        					longitude:longitude
        				},true),
        				success:function(response){
        					var code = response.code;
        					if ("200" == code) {
        						var d = dialog({
        						    width:'480px',
        						    skin: 'tip-dlg',
        						    onshow : function() {
        						    	this.content($('#tip-dlg').render());
        						    	$(".tip-dlg .tip-dlg-content .operate button").click(function(){
        						    		d.close();
        						    		window.location.href = "/dashboard/Task/acceptedList.jsp?view=tab_1_1";
        						    	})
        						    	
        						    }
        						});
        						d.showModal();
        					} else if ("607" == code) {
        						alert("您还没登录，或者登录已失效");
        						logout();
        					} else {
        						alert(response.message);
        					}
        					return false;
        				}
        			});
                }
            });

    	}else{
    		$('.task-list').html('<div class="no-data">无关注任务数据~~</div>');
    	}
	}

	function showFollowlink(pageSplit) {
    	
		if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$(".search-pagination").html('<ul class="pagination"></ul>');
    		$(".search-pagination .pagination").twbsPagination({
            	totalPages: pageCount,
              	startPage: pageNow,  
              	first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
               	onPageClick: function (event, page) {
                 	if(page!=pageNow){
                    	searchCondition.pageIndex = page;
                    	loadFollow();
                 	}
                    	
              	}
         	});
    	}
    }
	
	followInit();
	loadFollow();
});