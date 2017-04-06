 $.views.tags({
    "sublevelFormat": function(sublevel){
        switch (true){
        case sublevel == '1':
        	return '一级邀请人';
        case sublevel == '2':
        	return '二级邀请人';
        case sublevel == '3':
        	return '三级邀请人';
        default:
        	return '未知邀请人';
        }
    }
 });
 
$(function() {
	
	//格式化日期：yyyy-MM-dd     
	function formatDate(date) {      
	    var myyear = date.getFullYear();     
	    var mymonth = date.getMonth()+1;     
	    var myweekday = date.getDate();      
	         
	    if(mymonth < 10){     
	        mymonth = "0" + mymonth;     
	    }      
	    if(myweekday < 10){     
	        myweekday = "0" + myweekday;     
	    }     
	    return (myyear+"-"+mymonth + "-" + myweekday);      
	}      
	//获得某年某月的天数     
	function getMonthDays(nowYear,myMonth){    
	    var monthStartDate = new Date(nowYear, myMonth, 1);      
	    var monthEndDate = new Date(nowYear, myMonth + 1, 1);      
	    var days = (monthEndDate - monthStartDate)/(24*60*60*1000);      
	    return days;      
	}     
	//获得本周的开始日期     
	function getWeekStartDate(nowYear, nowMonth,nowDay,nowDayOfWeek) {      
	    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);      
	    return formatDate(weekStartDate);     
	}      
	    
	//获得本周的结束日期     
	function getWeekEndDate(nowYear, nowMonth,nowDay,nowDayOfWeek) {      
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));      
	    return formatDate(weekEndDate);     
	}      
	    
	//获得本月的开始日期     
	function getMonthStartDate(nowYear, nowMonth){     
	    var monthStartDate = new Date(nowYear, nowMonth, 1);      
	    return formatDate(monthStartDate);     
	}     
	    
	//获得本月的结束日期     
	function getMonthEndDate(nowYear, nowMonth){     
	    var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear,nowMonth));      
	    return formatDate(monthEndDate);     
	}     
   
	var bonusPointInit = function() {
		$("title").html("我的奖励 | 奖励中心  | AnyoneHelps");
    	$(".nav4").addClass("active open");
		$(".nav42").addClass("active open");
		$('#sd').datepicker({
            language: 'zh-CN',
            autoclose: 1,
            format: 'yyyy-mm-dd'
        });
		$('#ed').datepicker({
            language: 'zh-CN',
            autoclose: 1,
            format: 'yyyy-mm-dd'
        });
		$("#nowWeek").click(function(){
			var now = new Date();                    //当前日期     
			var nowDayOfWeek = now.getDay();         //今天本周的第几天     
			var nowDay = now.getDate();              //当前日     
			var nowMonth = now.getMonth();           //当前月     
			var nowYear = now.getYear();             //当前年     
			nowYear += (nowYear < 2000) ? 1900 : 0;  //    
			$("#sd").val(getWeekStartDate(nowYear, nowMonth,nowDay,nowDayOfWeek));
			$("#ed").val(getWeekEndDate(nowYear, nowMonth,nowDay,nowDayOfWeek));
		})
		$("#nowMonth").click(function(){
			var now = new Date();                    //当前日期     
			var nowDayOfWeek = now.getDay();         //今天本周的第几天     
			var nowDay = now.getDate();              //当前日     
			var nowMonth = now.getMonth();           //当前月     
			var nowYear = now.getYear();             //当前年     
			nowYear += (nowYear < 2000) ? 1900 : 0;  //    
			$("#sd").val(getMonthStartDate(nowYear, nowMonth));
			$("#ed").val(getMonthEndDate(nowYear, nowMonth));
		})
		
    }
    var bonusPointLoad = function() {

    	function loadBonusPoint(key,sd,ed,size,pn) {
    		$.ajax({
    			type : "get",
    			url : "/bonus_point/search",
    			data : {
    				"key" : key,
    				"sd" : sd,
    				"ed" : ed,
    				"pageSize" : size,
    				"pageIndex" : pn
    			},
    			success : function(response) {
    				var code = response.code;
    				if (code == "200") {
    					if (response.data != null) {
    						showBonusPointlist(response.data.datas);
    						showBonusPointlink(response.data);
    					} else {
    						showBonusPointlist(null);
    						showBonusPointlink(null);
    					}
    					//afterloadcwjllist();
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} 
    			}
    		});
    	}
    	
    	function showBonusPointlist(list) {
    		if(list!=null){
        		$('#sample_1 tbody').html($('#items-tmpl').render({dataList: list}));
        	}else{
        		$('#sample_1 tbody').html("<tr class='odd gradeX'><td colspan='99' class='center'>无数据.......</td></tr>");
        	}
    	}

    	function showBonusPointlink(pageSplit) {
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
                    	var key = $('#key').val();
                    	loadBonusPoint(key,"","",pageSize,page);
                    }
                });
        	}
    	}

    	$('#bonus-point-search').validate({
            submitHandler: function(form) {
            	var key = $('#key').val();
            	var sd = $('#sd').val();
            	var ed = $('#ed').val();
            	loadBonusPoint(key,sd,ed,10,1)
            }
        });

        $('#bonus-point-search').keypress(function(e) {
            if (e.which == 13) {
                if ($('#bonus-point-search').validate().form()) {
                    $('#bonus-point-search').submit();      
                }
                return false;
            }
        });
    	loadBonusPoint("","","",10,1)
    }
  
    
    bonusPointInit();
	bonusPointLoad();

});
