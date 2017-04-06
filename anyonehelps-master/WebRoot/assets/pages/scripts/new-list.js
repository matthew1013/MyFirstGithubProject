$.views.tags({
     "datetimeFormat":function(datetime){
    	 if(datetime == null){
    		 return "";
    	 }
    	 return datetime.slice(0,datetime.indexOf(" "));
     }
});

$(function() {
	$("title").html($(":hidden[name='seoTitle']").val());
	$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
	$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
	
	var loadNewsList = function(pageIndex,showRightList) {
		showRightList = showRightList?showRightList:false;  //默认不显示右边新闻列表
		$.ajax({
			type : "get",
			url : "/news/search",
			data : {
				"pageIndex" : pageIndex,
	     	   	"pageSize" : 6
			},
			success : function(response) {
				var code = response.code;
				console.log(response);
				if ("200" == code) {
					if (response.data != null) {
						showNewsList(showRightList,response.data.datas);
					}
					showNewsLink(response.data);
				}
			},
			error : function() {
			}
		});
	}
		
	var showNewsList = function(showRightList,list) {
		if(showRightList){
			$(".new-list").html($("#new-item-tmpl").render({dataList: list}));
		}
		$(".list").html($("#list-item-tmpl").render({dataList: list}));
	}
	
	var showNewsLink = function(pageSplit) {
		if (pageSplit != null) {
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		if(pageNow<=1){
    			$(".page-prev").attr("disabled", true); 
    			$(".page-prev").data("page", 1); 
    		}else{ 
    			$(".page-prev").attr("disabled", false); 
    			$(".page-prev").data("page", pageNow); 
    		}
    		if(pageNow>=pageCount){
    			$(".page-next").attr("disabled", true); 
    			$(".page-next").data("page", pageCount); 
    		}else{
    			$(".page-next").attr("disabled", false); 
    			$(".page-next").data("page", pageNow); 
    		}
		}
	}
	
	
	var loadDemandSumAmount = function() {
		$.ajax({
			type : "get",
			url : "/demand/get_sum_amount",
			success : function(response) {
				var code = response.code;
				console.log(response)
				if ("200" == code) {
					$(".money-amount-2").html(response.data.sum);
					$(".task-amount-2").html(response.data.count);
				}
			}
		});
	}
	$(".page-prev").click(function(){
		var pageNow = $(this).data("page");
		loadNewsList(pageNow-1);
		
	})
	$(".page-next").click(function(){
		var pageNow = $(this).data("page");
		loadNewsList(pageNow+1);
	})

	loadNewsList(1,true);
	loadDemandSumAmount();
});
