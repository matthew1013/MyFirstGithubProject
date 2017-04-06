 $.views.tags({
        "formatState": function(state){
        	switch (true){
        	case state == 0:
        		return '等待对方确认';
        	case state == 1:
        		return '对方同意,仲裁结束';
        	case state == 2:
        		return '对方反对,等待平台裁决';
        	case state == 3:
        		return '平台裁决中';
        	case state == 4:
        		return '平台裁决结束';
        	default:
        		return 'unkown';
        	}
        	
        }
 });

var arbitrationList = function() {
	var arbitrationInit = function() {
		$("title").html("任务仲裁记录 | 任务中心 | AnyoneHelps");
    	$(".nav2").addClass("active open");
		$(".nav25").addClass("active open");
    }
    var arbitrationLoad = function() {
    	function loadArbitration(pn) {
    		$.ajax({
    			type : "get",
    			url : "/arbitration/search",
    			data : {
    				"pageIndex" : pn
    			},
    			success : function(response) {
    				var code = response.code;
    				if (code == "200") {
    					if (response.data != null) {
    						showArbitrationList(response.data.datas);
    						showArbitrationLink(response.data);
    					} else {
    						showArbitrationList(null);
    						showArbitrationLink(null);
    					}
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} 
    			}
    		});
    	}

    	function showArbitrationList(list) {
    		if (list != null) {
    			var arbitration = $("#sample_1 tbody").html($('#arbitration-tmpl').render({dataList: list}));
        		arbitration.find(".arbitration-result-btn").click(function(){
        			var state = $(this).data().state;
        			var reason = $(this).data().reason;
        			var percentage = $(this).data().percentage;
        			var title = $(this).data().title;
        			var arbitrationid = $(this).data().arbitrationid;
        			var dir = $(this).data().dir;
        			if(dir=="1"){
        				window.location.href= "/dashboard/Task/taskAcceptedArbitration.jsp?arbitrationid="+arbitrationid+"&state="+state+"&reason="+reason+"&title="+title+"&percentage="+percentage;
        			}else if(dir=="0"){
        				window.location.href= "/dashboard/Task/taskPublishedArbitration.jsp?arbitrationid="+arbitrationid+"&state="+state+"&reason="+reason+"&title="+title+"&percentage="+percentage;
        			}
        				
        		})
    		}else{
    			$("#sample_1 tbody").html("<tr><td colspan=99>暂无数据.......</td></tr>");
    		}
    	}

    	function showArbitrationLink(pageSplit) {
    		
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
                    onPageClick: function (event, page) {
                    	loadArbitration(page);
                    }
                });
        	}
    	}

    	
    	loadArbitration(1)
    }
    return {
        //main function to initiate the module
        init: function() {
        	arbitrationInit();
        	arbitrationLoad();
        }

    };

}();

jQuery(document).ready(function() {
	arbitrationList.init();
});