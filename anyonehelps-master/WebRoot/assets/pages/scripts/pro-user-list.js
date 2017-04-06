$.views.tags({
  	"countryFormat":function(country){
     	if(country == "us"){return "美国"}
     	else if(country == "cn"){return "中国"}
     	else if(country == "au"){return "澳洲"}
     	else if(country == "ca"){return "加拿大"}
     	else if(country == "uk"){return "英国"}
     	else if(country == "other"){return "其他"}
     	else {return "未知"}
     	
     },
});

var proUserList = function() {
	var init = function(){
		$("title").html($(":hidden[name='seoTitle']").val());
		$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
		$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
		
		$(".nav5").addClass("active open");
		$(".nav52").addClass("active open"); 
		
		$('select[name="proType"]').change(function(){
			$('select[name="pro"]').html($('#pro-tmpl').render({type: $(this).val()}));
		});
	
	}
	var searchCondition = {
		key : "",
		proTypeId : "",
		proId : "",
		pageIndex : 1,
		pageSize : 10
	};
	
	$(".search-start").click(function(){
		searchCondition.key =  $(this).prev().val();
		searchCondition.proTypeId = $("select[name='proType']").val();
		searchCondition.proId = $("select[name='pro']").val();
		searchCondition.pageIndex = 1;
		loadProUserList();
	})
	
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
	
	
	var loadProUserList = function(){
		$.ajax({
			type : "get",
			url : "/pro_user/search",
			data: {
				"key" : searchCondition.key,
     	    	"proTypeId" : searchCondition.proTypeId,
     	    	"proId" : searchCondition.proId,
     	    	"pageIndex" : searchCondition.pageIndex,
     	    	"pageSize" : searchCondition.pageSize,
     	    },  
			success : function(response) {
				searchCondition.pageIndex++;
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showProUserList(response.data.datas);
						showProUserLink(response.data);
					} else {
						showProUserList(null);
						showProUserLink(null);
					}
				} else if ("607" == code) {
					$.errorMsg("您尚未登录或登录已失效！");
					logout();
				} else{
					$.errorMsg(response.message);
				}
			}
		});
	}
	
	function showProUserList(list) {
		console.log(list)
		if(list==null){
			$('.pro-user-list').html("");
		}else{
			var obj = $('.pro-user-list').html($('#pro-user-item-tmpl').render({dataList:list}));
			//obj.find('.user-follow').unbind("click");
			obj.find('.user-follow').click(userFollow);
			
			obj.find('.item .right .open-evalute>i').click(function(){
				if($(this).hasClass('icon-minus')){
					$(this).removeClass("icon-minus");
					$(this).parent().parent().parent().find(".user-evalute").addClass("hide");
				}else{
					$(this).addClass("icon-minus");
					$(this).parent().parent().parent().find(".user-evalute").removeClass("hide");
				}
			})
		}
	}
	//$('.pro-user-list').html($('#pro-user-item-tmpl').render());
	function showProUserLink(pageSplit) {
		if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$(".search-pagination").html('<ul class="pagination"></ul>');
    		
    		$(".pagination").twbsPagination({
                totalPages: pageCount,
                first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
                onPageClick: function (event, page) {
                	if(page!=pageNow){
                		searchCondition.pageIndex = page;
                		loadProUserList(searchCondition);
                	}
                }
            });
    	}
	}
	
    return {
        //main function to initiate the module
        init: function() {
        	init();
        	loadProUserList(searchCondition);
        }

    };

}();

jQuery(document).ready(function() {
	proUserList.init();
});