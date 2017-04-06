$.views.tags({
    "substrOfContent": function(content){
    	if(content.length>30) return content.substr(0,30)+ '...';
    	else  return content;
    },
    "createDateFormat":function(createDate){
     	var createDateDate = new Date(Date.parse(createDate.replace(/-/g, "/")));
     	var currentDate = new Date();
     	var time=currentDate.getTime()-createDateDate.getTime();
     	if(time<0)return "刚刚";
     	var month=Math.floor(time/(20*24*60*60*1000));
     	var day=Math.floor(time/(24*60*60*1000));
     	var hours=Math.floor(time/(60*60*1000));
     	var minute=Math.floor(time/(60*1000));
     	if(month!="0"){
     		return month+"个月前";
     	}else if(day!="0"){
     		return day+"天前";
     	}else if(hours!="0"){
     		return hours+"小时前";
     	}else if(minute!="0"){
     		return minute!="分钟前";
     	}else {
     		return "刚刚";
     	}
     	
     },
     
     "levelFormat":function(level){
      	if(level=="1"){
      		return "label-default";
      	}else if(level=="2"){
      		return "label-primary";
      	}else if(level=="3"){
      		return "label-success";
      	}else if(level=="4"){
      		return "label-info";
      	}else if(level=="5"){
      		return "label-warning";
      	}else if(level=="6"){
      		return "label-danger";
      	}else {
      		return "label-info";
      	}
      },
      "levelFaFormat":function(level){
        	if(level=="1"){
        		return "fa-plus";
        	}else if(level=="2"){
        		return "fa-plus";
        	}else if(level=="3"){
        		return "fa-bell-o";
        	}else if(level=="4"){
        		return "fa-bell-o";
        	}else if(level=="5"){
        		return "fa-bullhorn";
        	}else if(level=="6"){
        		return "fa-bolt";
        	}else {
        		return "fa-bell-o";
        	}
        },
      "substrOfTitle": function(title){
      	return title.substr(0,30)+ '...';;
      }
});
var indexFrontmenu = function() {
	var modifyMessageUserState = function(id) {
		$.ajax({
			type:"get",
			url:message_user_read_url,
			data:{
				id:id
			},
			success:function(response){
				var code = response.code;
				if ("200" == code) {
				} else if ("607" == code) {
					//跳至登录处
					alert("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	var loadMessageUserList = function(key,state,pn,ps) {
		$.ajax({
			type:"get",
			url:message_user_search_url,
			data:{
				key:key,
				state:state,
				pageIndex:pn,
				pageSize:ps
			},
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					if(response.data==null) {
					} else {
						//JSON.stringify(response.data.datas);
						$("#message-user-count").html(response.data.rowCount);
						$("#message-user-count1").html(response.data.rowCount);
						$('#message-user-list').html($('#message-user-items-tmpl').render({dataList: response.data.datas, baseUrl: baseUrl}));
						
						$("a[name='message-user-link']").click(function(){
							var id = $(this).parent().attr("name");
							modifyMessageUserState(id);
						})
					}
				} else if ("607" == code) {
					//跳至登录处
					//logout();
				} 
				setTimeout(function(){loadMessageUserList("","0","1","5")},2*60*1000);
				return false;
			}
		});
		
	}
	
	var modifyMessageSystemState = function(id) {
		$.ajax({
			type:"get",
			url:message_system_read_url,
			data:{
				id:id
			},
			success:function(response){
				var code = response.code;
				if ("200" == code) {
				} else if ("607" == code) {
					//跳至登录处
					alert("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	var loadMessageSystemList = function(key,state,pn,ps) {
		$.ajax({
			type:"get",
			url:message_system_search_url,
			data:{
				key:key,
				state:state,
				pageIndex:pn,
				pageSize:ps
			},
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					if(response.data==null) {
					} else {
						//JSON.stringify(response.data.datas);
						$("#message-system-count").html(response.data.rowCount);
						$("#message-system-count1").html(response.data.rowCount);
						$('#message-system-list').html($('#message-system-items-tmpl').render({dataList: response.data.datas, baseUrl: baseUrl}));
						
						$("a[name='message-system-link']").click(function(){
							var id = $(this).parent().attr("name");
							modifyMessageSystemState(id);
						})
					}
				} else if ("607" == code) {
					//跳至登录处
					//logout();
				} 
				setTimeout(function(){loadMessageSystemList("","0","1","5")},2*60*1000);
				return false;
			}
		});
		
	}
	
	var indexFrontMenuLoad = function() {
		baseUrl = $("#project_name").val();
		firstSetUrl(baseUrl);
		$("button[name='go-home']").click(function(){
			window.location.href = project_base_name;
		})
		loadMessageUserList("","0","1","5"); //未读留言
		loadMessageSystemList("","0","1","5"); //未读系统消息
	}
	
	return {
	    init: function() {
	    	indexFrontMenuLoad();
	    }
	};

}();

jQuery(document).ready(function() {
	indexFrontmenu.init();
});