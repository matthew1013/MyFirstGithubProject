var userfamous = function() {
	$("title").html($(":hidden[name='seoTitle']").val());
	$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
	$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
	
	var searchCondition = {
		key : "",
		pageIndex : 1,
		pageSize : 20
	};
	var penddingData = false;
	var hasMorePage = true;
	
	
	var userFollow = function(){
		var elem = this;
		var follow = $(this).data("follow");
   	 	var userId = $(this).data("userid");
   	 	var chk_value = [];
   	 	chk_value.push(userId);
   	 	if(follow=="1"){
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
   	 					$(elem).html("关注");
   	 					$(elem).data("follow","0");
   	 					$.successMsg("取消关注成功");
   	 				} else if ("607" == code) {
   	 					$.errorMsg("您尚未登录或登录已失效！请登录后再取消关注！");
   	 					logout();
   	 				} else {
   	 					$.errorMsg(response.message);
   	 				}

   	 			},
   	 		});
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
   	 					$(elem).html("取消关注");
   	 					$(elem).data("follow","1");
   	 					$.successMsg("关注成功");
   	 				} else if ("607" == code) {
   	 					$.errorMsg("您尚未登录或登录已失效！请登录后再关注！");
   	 					logout();
   	 				} else {
   	 					$.errorMsg(response.message);
   	 				}
   	 				
   	 			},
   	 		});
   	 	}
	}
	
	
	var loadUserBySumAmount = function(searchCondition) {
		$(".load-more").html("加载中...");
		$.ajax({
			type : "get",
			url : "/user/by_sum_amount",
			data: {
				"key" : searchCondition.key,
     	    	"pageIndex" : searchCondition.pageIndex,
     	    	"pageSize" : searchCondition.pageSize,
     	    },  
			success : function(response) {
				searchCondition.pageIndex++;
				console.log(response);
				var code = response.code;
				if ("200" == code) {
					var data = response.data;
					if (data != null) {
						if (response.data.datas.length < searchCondition.pageSize) {
							hasMorePage = false;
							$(".load-more").html("已经加载全部");
						} else {
							hasMorePage = true;
							$(".load-more").html("点击加载更多");
						}
						obj = $(".famous-list").append($('#famous-people-tmpl').render({dataList:data.datas}));
						obj.find('.user-follow').click(userFollow);
					
					}else{
						hasMorePage = false;
						$(".load-more").html("已经加载全部");
					}
					$("[data-toggle='tooltip']").tooltip();
				}else{
					$(".load-more").html("点击加载更多");
				}
				penddingData = false;
				return false;
			},
			error : function() {
				//请求有问题
				penddingData = false;
				$(".load-more").html("点击加载更多");
			}
     	    
		});
	}
	
	$(".type .search-type").click(function(){
		$(".type .search-type .type-item").removeClass("active");
		$(this).children().addClass("active");
		hasMorePage = true;
		penddingData = false;
		var key = $(this).data("type");
		searchCondition.key = key;
		searchCondition.pageIndex = 1;
		searchCondition.pageSize = 20;
		$(".famous-list").html("");
		loadUserBySumAmount(searchCondition);
	})
	
	/*加载更多点击事件*/
	$(".load-more").click(function(){
		if(!hasMorePage||penddingData) return false;  //正在加载数据或者已加载全部数据
		penddingData = true;
		loadUserBySumAmount(searchCondition);
	})

	
    return {
        //main function
        init: function() {
        	loadUserBySumAmount(searchCondition);
        }
    };

}();
jQuery(document).ready(function() {
	userfamous.init();
});