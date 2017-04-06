
$(function() {
	var init = function() {
		$("title").html("申请大牛记录 | 大牛社区 | AnyoneHelps");
    	$(".nav5").addClass("active open");
		$(".nav53").addClass("active open");
    }
	
	var showData = function(){
	    var data = $(this).data("data");
	    dialog({
	    	align:'bottom left',
			width:'400px',
			quickClose : true,
			skin: 'show-data-dlg',
			content: data
		}).show(this);
	}
	
	function load(size,pn) {
		$.ajax({
			type : "get",
			url : "/pro_user/search_self",
			data : {
				"pageSize" : size,
				"pageIndex" : pn
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showList(response.data.datas);
						showLink(response.data);
					} else {
						showList(null);
						showLink(null);
					}
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
	}
	
	
	function showList(list) {
		if(list!=null){
    		$('#pro-user-list tbody').html($('#items-tmpl').render({dataList: list}));
    		$("#pro-user-list tbody .show-data").click(showData);
		}else{
    		//$('#pro-user-list').html('暂无关注用户~~');
    	}
	}

	function showLink(pageSplit) {
		if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$(".pagination").twbsPagination({
                totalPages: pageCount,
                first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
                //href: '?a=&page={{number}}&c=d' ,
                onPageClick: function (event, page) {
                	if(page!=pageNow){
                		load(pageSize, page);
                	}
                	
                }
            });
    	}
    }
    init();
    load(10,1)
});
