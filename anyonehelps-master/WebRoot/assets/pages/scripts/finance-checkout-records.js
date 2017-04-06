 $.views.tags({
    "status": function(status){
        switch (true){
        case status == '0':
        	return '未处理';
        case status == '1':
        	return '处理中';
        case status == '2':
        	return '取现成功';
        case status == '3':
        	return '取现异常';
        default:
        	return '未知';
        }
    },
    "typeFormat": function(type){
        switch (true){
        case type == '1':
        	return '银行卡';
        case type == '2':
        	return 'Paypal';
        default:
        	return '未知';
        }
    }
 });
 
var financeRecord = function() {
	var financeRecordInit = function() {
		$("title").html("取现记录 | 财务管理 | AnyoneHelps");
    	$(".nav3").addClass("active open");
		$(".nav34").addClass("active open");
    }
    var recordLoad = function() {
    	function loadCheckoutinfo(type,state,size,pn) {
    		$.ajax({
    			type : "get",
    			url : "/account/withdrawals/search",
    			data : {
    				"type" : type,
    				"state" : state,
    				"pageSize" : size,
    				"pageIndex" : pn
    			},
    			success : function(response) {
    				var code = response.code;
    				console.log(response);
    				if (code == "200") {
    					if (response.data != null) {
    						showCheckoutlist(response.data.datas);
    						showCheckoutlink(response.data);
    					} else {
    						showCheckoutlist(null);
    						showCheckoutlink(null);
    					}
    					//afterloadcwjllist();
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} 
    			}
    		});
    	}

    	function showCheckoutlist(list) {
    		if(list!=null){
        		$('#sample_1 tbody').html($('#items-tmpl').render({dataList: list}));
        	}else{
        		$('#sample_1 tbody').html("<tr><td colspan=99>暂无数据.......</td></tr>");
        	}
    	}

    	function showCheckoutlink(pageSplit) {
    		/*if (pageSplit != null) {
    			var rowCount = pageSplit.rowCount;
    			var pageSize = pageSplit.pageSize;
    			var pageNow = pageSplit.pageNow;
    			var pageCount = pageSplit.pageCount;
    			$("tfoot td") .html(createPaginationHtmlStr(rowCount, pageSize, pageNow, pageCount));

    			$("tfoot td a").click(function() {
    				
    				var pn = $(this).attr("href");
    				loadCheckoutinfo("","",10,pn);
    				return false;
    			});

    			$("tfoot td input").keydown(function(event) {
    				if (event.which == 13) {
    					var pn = $(this).val();
    					if (/^\d*$/.test(pn)) {
    						loadCheckoutinfo("","",10,pn);
    					} else {
    						alert("只能输入数字");
    					}
    					$(this).blur();
    				}
    			});
    		} else {
    			$("tfoot td").html("");
    		}*/
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
                    	loadCheckoutinfo("","",pageSize,page);
                    }
                });
        	}
    	}

    	
    	loadCheckoutinfo("","",10,1)
    }
    return {
        //main function to initiate the module
        init: function() {
        	financeRecordInit();
        	recordLoad();
        }

    };

}();

jQuery(document).ready(function() {
	financeRecord.init();
});