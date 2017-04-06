/*IE9以下提示 start*/
if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE6.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0")
{
	alert("您的浏览器版本过低，建议在ie10或以上，chrome，firefox等主流浏览器使用");
}
/*IE9以下提示 end*/



$.views.tags({
	"substrOfTitle": function(title){
   	 var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
   	 if(reg.test(title)){ //含有中文
   		 if(title.length>24) return title.substr(0,24)+ '...';
        	 else  return title;
   	 }else {  //没有中文
   		 if(title.length>48) return title.substr(0,48)+ '...';
        	 else  return title;
   	 }
     },
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
     	/*
     	if(month!="0"){   haokun delete 2017/03/02
     		return month+"个月 后截止";
     	}else if(day!="0"&&day!="1"){
     		return day+"天 后截止";
     	}else if(day=="1"){
     		return (24+hours)+"小时 后截止";
     	}else{
     		return hours+"小时 后截止";
     	}
		*/
		/* haokun add start 修改返回值 2017/03/02*/
		if(month > 0){
     		return month+"个月 后截止";
     	}else if(month < 0){
			return "已截止";
		}else{
			if(day!="0"&&day!="1"){
     		    return day+"天 后截止";
     	    }else if(day=="1"){
     		    return (24+hours)+"小时 后截止";
     	    }else{
     		    return hours+"小时 后截止";
     	    }
		}
		/* haokun add start 修改返回值 2017/03/02*/
     	
     },
     "typeFormat": function(type){
   	  	var strType = "<div class='type-other'>其他</div>";
   	  	if(type=="1"){
   			strType = "<div class='type-learn'>学习</div>";   /*haokun added 2017/02/19*/
     	}else if(type=="2"){
     		strType = "<div class='type-life'>生活</div>";  /*haokun added 2017/02/19*/
     	}else if(type=="3"){
     		strType = "<div class='type-work'>工作</div>";  /*haokun added 2017/02/19*/
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
    	 
     },
     "datetimeFormat":function(datetime){
    	 if(datetime == null){
    		 return "";
    	 }
    	 return datetime.slice(0,datetime.indexOf(" "));
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
/*金额整数每三位加一个逗号，可以有小数点*/
function balanceFormat(balance){
	balance = balance.toString();
	if(/[^0-9\.]/.test(balance)) return "$ 0.00";
	balance=balance.replace(/^(\d*)$/,"$1.");
	balance=(balance+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	balance=balance.replace(".",",");
	var re=/(\d)(\d{3},)/;
	while(re.test(balance)) balance=balance.replace(re,"$1,$2");
	balance=balance.replace(/,(\d\d)$/,".$1");
	return balance.replace(/^\./,"0.")
}


var index = function() {
	if(!g_ispc){
		$("a").removeAttr("target");
	}
	$("title").html($(":hidden[name='seoTitle']").val());
	$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
	$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
	/*选择地区*/
	$('input[name="nationality"]').iCheck({
  	  	checkboxClass: 'icheckbox_minimal-blue',
    	radioClass: 'iradio_minimal-blue',
   	 	increaseArea: '20%', // optional
   	 	check:function(){
   	 	  alert('Well done, Sir');
   	 	}
  	});
	
	var paramKey = $(":hidden[name='param-key']").val();
	var paramLocation = $(":hidden[name='param-location']").val();
	var paramType = $(":hidden[name='param-type']").val();
	var paramTypeSecond = $(":hidden[name='param-type-second']").val();
	var paramMinAmount = $(":hidden[name='paramMinAmount']").val();
	var paramMaxAmount = $(":hidden[name='paramMaxAmount']").val();
	var paramTag = $(":hidden[name='paramTag']").val(); //标签
	var paramTagType = $(":hidden[name='paramTagType']").val(); //标签
	
	if(paramTag!=null&&paramTag!=""){
		$('.tag-div').next().removeClass("hide");
		$('.tag-div').removeClass("hide");
		if(paramTagType != "0"&&paramTagType != "1"&&paramTagType != "2"){
			paramTagType = 0;
		}
		$('.tag-div').html($('#tag-items-tmpl').render({tag: paramTag, type:paramTagType}));
		
	}
	var sortType = 0;
	var pageIndex = 1;
	var pageSize = 12;
	var searchType = "task";
	
	var userSumAmountIndex = 1; //名人堂页数
	var userSumAmountSize = 10;//名人堂每页大小
	
	var searchCondition = {
		sortType:sortType,
		key : paramKey,
		nationality : paramLocation,
		type : paramType,
		typeSecond : paramTypeSecond,
		minAmount : paramMinAmount,
		maxAmount : paramMaxAmount,
		tag:paramTag,
		pageIndex : pageIndex,
		pageSize : pageSize
	};
	
	
	/*查找人物或者任务*/
	$('.search-type ul li a').click(function(){
		$(this).parent().siblings().removeClass("active");
		$(this).parent().addClass("active");
		searchType =  $(this).data("type");
		searchCondition.pageIndex=1;
		//标签置空  隐藏 
		$('.tag-div').html("");
		$('.tag-div').addClass("hide");
		$('.tag-div').next().addClass("hide");
		//关键字置空  隐藏
		$('.search-key-div').addClass("hide");
		$('.search-key-div a b').html("");
		$('.search-key').val("");
		//地区重置为所有
		$(".location").val("");
		$('.curr-location').data('location',"");
		$('.curr-location-name').html("不限");
		
		//分类重置为所有
		$('.curr-type').data('type',"");
		$('.curr-type-name').html("所有");
		
		//第二分类重置为所有
		$(".dropdown-type-second-div").addClass("hide");
		$('.curr-type-second').data('typesecond',"");
		$('.curr-type-second-name').html("所有");
		
		if(searchType=="user"){
			$('.search-key').attr("placeholder","请输入人物关键字");
			$(".curr-sorting").data("sort","0");
			$(".curr-sorting-name").html("默认推荐");
		}else if(searchType=="task"){
			$('.search-key').attr("placeholder","请输入任务名称关键字或所在地");
			$(".curr-sorting").data("sort","1");
			$(".curr-sorting-name").html("最新任务");
		}
		
		$(".sorting ul").html($("#sort-tmpl").render({searchType: searchType}));
		$('.sorting ul li a').unbind("click");
		$('.sorting ul li a').bind("click",sortClick);
		$(".task-content").html("");
		loadDemandList(searchCondition,true);
		//return false;
	})
	
	$(".famous-people .nav.nav-tabs>li>a").click(function(){
		var href = $(this).data("more");
		$(this).parent().parent().find(".more a").attr("href",href);
	})
	
	/*查找人物或者任务*/
	/*$('a[name="search-type"]').click(function(){
		$(this).parent().siblings().removeClass("active");
		$(this).parent().addClass("active");
		searchType =  $(this).parent().data("type");
		searchCondition.pageIndex=1;
		//标签置空
		$('.tag-div').remove();
		//关键字置空
		$('.search-key-span').html("");
		$('.search-key').val("");
		//地区重置为所有
		$(".location").val("");
		$('.curr-location').data('location',"");
		$('.curr-location-name').html("不限");
		
		//分类重置为所有
		$('.curr-type').data('type',"");
		$('.curr-type-name').html("所有");
		
		//第二分类重置为所有
		$(".dropdown-type-second-div").addClass("hide");
		$('.curr-type-second').data('typesecond',"");
		$('.curr-type-second-name').html("所有");
		
		if(searchType=="user"){
			$('.search-key').attr("placeholder","请输入人物关键字");
			$(".sorting-active").data("sort","0");
			$(".sorting-active").html("默认推荐");
		}else if(searchType=="task"){
			$('.search-key').attr("placeholder","请输入任务名称关键字或所在地");
			$(".sorting-active").data("sort","1");
			$(".sorting-active").html("最新任务");
		}
		
		$(".sorting ul").html($("#sort-tmpl").render({searchType: searchType}));
		$('.sorting ul li a').unbind("click");
		$('.sorting ul li a').bind("click",sortClick);
		$(".task-content").html("");
		loadDemandList(searchCondition,true);
		return false;
	})*/
	
	/*排序*/
	var sortClick = function(){
		$(".curr-sorting").data("sort",$(this).attr("href"));
		$(".curr-sorting-name").html($(this).html());
		searchCondition.minAmount = "";
		searchCondition.maxAmount = "";
		searchCondition.pageIndex = 1;
		
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
	}
	$('.sorting ul li a').click(sortClick)
	
	/*右侧金额点击事件*/
	$('.amount-intervals li').click(function() {
		$('.tag-div').next().removeClass("hide");
		$('.tag-div').removeClass("hide");
		$('.tag-div').html($('#tag-items-tmpl').render({tag: $(this).children().data("tag"),type:2}));
		if ($(this).hasClass("active")) {
			$(".tag-active").removeClass("tag-active");
			$(this).removeClass("active");
			$(this).removeClass().addClass("tag-active");
			searchCondition.minAmount = "";
			searchCondition.maxAmount = "";
			searchCondition.typeSecond = "";
			searchCondition.pageIndex = 1;
			$(".task-content").html("")
			loadDemandList(searchCondition,false);
		}else{
			$(this).parent().find("li").removeClass("active");
			$(".tag-active").removeClass("tag-active");
			$(this).addClass("active");
			$(this).children().addClass("tag-active");
			searchCondition.minAmount = $(this).find("a").first().data("minamount");
			searchCondition.maxAmount = $(this).find("a").first().data("maxamount");
			searchCondition.typeSecond = "";
			searchCondition.pageIndex = 1;
			$(".task-content").html("")
			loadDemandList(searchCondition,false);
		}
	})
	  
	
	/*任务加载更多点击事件*/
	/*$(".content-click").click(function(){
		if(!hasMorePage||penddingData) return false;  //正在加载数据或者已加载全部数据
		penddingData = true;
		var minAmount = $('.amount-intervals li .active').find("a").data("minamount");
		var maxAmount = $('.amount-intervals li .active').find("a").data("maxamount");
		searchCondition.minAmount = minAmount;
		searchCondition.maxAmount = minAmount;
		
		loadDemandList(searchCondition,false);
		return false;
	})*/
	
	/*热门标签换一批*/
	var tagFlag=0;
	$("a[name='tag-refresh']").click(function(){
		if(tagFlag%2==0){   /*haokun modified 2017/02/21 变成2列*/
			var str = "";
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="辅导"><div class="tag-item margin-right-10">#辅导</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="纯手工"><div class="tag-item margin-right-10">#纯手工</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="导游"><div class="tag-item margin-right-10">#导游</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="兼职"><div class="tag-item margin-right-10">#兼职</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="买车"><div class="tag-item margin-right-10">#买车</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="辅导"><div class="tag-item margin-right-10">#辅导</div></a>';
			$(this).parent().prev().html(str);
		}else if(tagFlag%2==1){
			var str = "";
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="论文"><div class="tag-item margin-right-10">#论文</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="卖车"><div class="tag-item margin-right-10">#卖车</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="旅游"><div class="tag-item margin-right-10">#旅游</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="留学"><div class="tag-item margin-right-10">#留学</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="数学"><div class="tag-item margin-right-10">#数学</div></a>';
			str += '<a href="javascript:void(0)" name="tag-a" data-tag="摄影"><div class="tag-item margin-right-10">#摄影</div></a>';
			$(this).parent().prev().html(str);
        }
		tagFlag++;
		return false;
	})
	
	/*全部结果点击事件*/
	$(".task-all a").click(function(){
		//标签置空  隐藏 
		$('.tag-div').html("");
		$('.tag-div').addClass("hide");
		$('.tag-div').next().addClass("hide");
		//关键字置空  隐藏
		$('.search-key-div').addClass("hide");
		$('.search-key-div a b').html("");
		$('.search-key').val("");
		//地区重置为所有
		$(".location").val("");
		$('.curr-location').data('location',"");
		$('.curr-location-name').html("不限");
		
		//分类重置为所有
		$('.curr-type').data('type',"");
		$('.curr-type-name').html("所有");
		
		//第二分类重置为所有
		$(".dropdown-type-second-div").addClass("hide");
		$('.curr-type-second').data('typesecond',"");
		$('.curr-type-second-name').html("所有");
		
		searchCondition.minAmount = "";
		searchCondition.maxAmount = "";
		searchCondition.typeSecond = "";
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
		
	});
	
	/*分类点击*/
	$("a[name='type-a']").click(function(){
		var type = $(this).data("type");
		$('.curr-type').data('type',type);
		$('.curr-type-name').html($(this).find("p").html());
		
		$("#type-second-list").html($("#type-second-tmpl").render({type: type}));
		$("#type-second-ul").html($("#dropdown-type-second-tmpl").render({type: type}));
		
		if(type!=""){
			$(".type-second-div").removeClass("hide");
			$(".dropdown-type-second-div").removeClass("hide");
		}else{
			$(".type-second-div").addClass("hide");
			$(".dropdown-type-second-div").addClass("hide");
		}
		
		//第二分类重置为所有
		$('.curr-type-second').data('typesecond',"");
		$('.curr-type-second-name').html("所有");
		
		searchCondition.minAmount = "";
		searchCondition.maxAmount = "";
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
		
		$("a[name='type-second-a']").unbind("click");
		$("a[name='type-second-a']").bind("click",typeSecond);
		$("a[name='dropdown-type-second-a']").unbind("click");
		$("a[name='dropdown-type-second-a']").bind("click",dropdownTypeSecond);
		
	})
	
	/*第二分类*/
	var typeSecond = function(){
		var typesecond = $(this).data("typesecond");
		$('.curr-type-second').data('typesecond',typesecond);
		$('.curr-type-second-name').html($(this).find("p").html());
		
		searchCondition.minAmount = "";
		searchCondition.maxAmount = "";
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
	}
	
	/*分类点击*/
	/*$(".category table th").click(function(){
		$(".category table th").removeClass("type-active");
		$(this).addClass("type-active");
		var val = $(this).data("type");
		$('.curr-type').data('type',val);
		$('.curr-type-name').html($(this).html());
		
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
		return false;
	
	})*/
	
	/*地区按下改变*/
	$("input[name='nationality']").on('ifChecked', function(){
		var val = $(this).val();
		var text = $(this).parent().next().html();
		$(".location").val(val);
		$('.curr-location').data('location',val);
		$('.curr-location-name').html(text);
		
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
	});
	
	
	/*地区下拉框改变*/
	$(".location").change(function(){
		var val = $(this).val();
		var text = $(this).find("option:selected").text();
		$(".location").val(val);
		$('.curr-location').data('location',val);
		$('.curr-location-name').html(text);
		
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
	})
	
	
	/*下拉分类改变*/
	$(".dropdown-type").click(function(){
		var val = $(this).attr("href");
		$('.curr-type').data('type',val);
		$('.curr-type-name').html($(this).html());
		
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
		$('.curr-type').click();//模拟点击一次，关闭弹出框
		return false;
	})
	
	/*下拉第二分类改变*/
	var dropdownTypeSecond = function(){
		var val = $(this).attr("href");
		$('.curr-type-second').data('typesecond',val);
		$('.curr-type-second-name').html($(this).html());
		
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
		return false;
	}
	
	/*下拉地区改变*/
	$(".dropdown-location").click(function(){
		var val = $(this).attr("href");
		var text = $(this).html();
		$(".location").val(val);
		$('.curr-location').data('location',val);
		$('.curr-location-name').html(text);
		
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
		$('.curr-location').click();  //模拟点击一次，关闭弹出框
		return false;
	})
	/*搜索按钮按下*/
	$('.search-button').click(function(){
		var key = $(this).parent().parent().find('.search-key').val();
		$('.search-key-div').removeClass("hide");
		$('.search-key-div a b').html(key);
		$('.search-key').val(key);
			
		searchCondition.minAmount = "";
		searchCondition.maxAmount = "";
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
	})
	/*输入框回车查找*/
	$('.search-key').keypress(function(e) {
		if (e.which == 13) {
			var key = $(this).val();
			$('.search-key-div').removeClass("hide");
			$('.search-key-div a b').html(key);
			$('.search-key').val(key);
			
			searchCondition.minAmount = "";
			searchCondition.maxAmount = "";
			searchCondition.pageIndex = 1;
			$(".task-content").html("")
			loadDemandList(searchCondition,false);
        }
    });
	$('a[name="search-a"]').click(function(e) {
		var key = $("search-top-1").val();
		$('.search-key').val(key);
		searchCondition.minAmount = "";
		searchCondition.maxAmount = "";
		searchCondition.pageIndex = 1;
		$(".task-content").html("")
		loadDemandList(searchCondition,false);
    });
	$( ".search-key" ).autocomplete({
		source: function( request, response ) {
			$.ajax({
            	url: "/demand/all_region",
            	dataType: "json",
              	data:{
              		region: request.term
              	},
             	success: function( data ) {
             		var code = data.code;
             		if ("200" == code) {
        				if(data.data!=null) {
        					response( $.map( data.data, function( item ) {
                            	return {
                                	value: item
                              	}
                         	}));
        				}
                    }
                        
             	}
         	});
     	},
      	minLength: 3,
     	select: function( event, ui ) {
           	$(".search-key").val(ui.item.value);
       	}
	});
	
	var loadUserList = function(searchCondition,init) {
		searchCondition.key = $('.search-key-div a b').html();
		if(searchCondition.key==""){
			//$('.search-key-span').parent().parent().parent().addClass("hide");
			if($('.tag-div a').data("type")==0){
				searchCondition.key = $('.tag-div a b').html();
			};
			$('.search-key-div').addClass("hide");
		}else{
			$('.search-key-div').removeClass("hide");
		}
		searchCondition.nationality = $('.curr-location').data("location");
		searchCondition.type = $('.curr-type').data("type");
		searchCondition.sortType = $(".curr-sorting").data("sort");
		
		$.ajax({
			type : "get",
			url : "/user/search_by_key",
			data : $.param({
				"key" : searchCondition.key,
				"country" : searchCondition.nationality,
				"specialtyTypeId" : searchCondition.type,
				"specialtyId" : searchCondition.typeSecond,
				"sortType" : searchCondition.sortType,
				"pageIndex" : searchCondition.pageIndex,
     	    	"pageSize" : searchCondition.pageSize
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					if(response.data != null){
						showUserList(response.data.datas);
						showPageLink(response.data);
					}else{
						showPageLink(null);
					}
				}
			}
		});
		if(!init){
			location.href = "#region"; 
		}
		return false;
	}
	
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
   	 				} else {
   	 					$.errorMsg(response.message);
   	 				}
   	 				
   	 			},
   	 		});
   	 	}
	}
	
	var showUserList = function(list) {
		console.log(list);
		var obj = $(".task-content").append($("#user-item-tmpl").render({dataList: list}));
		obj.find('.user-follow').click(userFollow);
		if(!g_ispc){
			$("a").removeAttr("target");
		}
	}
	
	var loadDemandList = function(searchCondition,init) {
		if(searchType=="user"){
			loadUserList(searchCondition,init);
			return false;
		}
		searchCondition.tag = $('.tag-div a b').next().html();
		
		if(searchCondition.tag=="$100以下"
			||searchCondition.tag=="$100~$300"
			||searchCondition.tag=="$300~$500"
			||searchCondition.tag=="$500以上"){
			searchCondition.tag = "";
		}
		searchCondition.key = $('.search-key-div a b').html();
		if(searchCondition.key==""){
			$('.search-key-div').addClass("hide");
		}else{
			$('.search-key-div').removeClass("hide");
		}
		searchCondition.nationality = $('.curr-location').data("location");
		searchCondition.type = $('.curr-type').data("type");
		searchCondition.typeSecond = $('.curr-type-second').data('typesecond');
		searchCondition.sortType = $(".curr-sorting").data("sort");
		$.ajax({
			type : "get",
			url : "demand/search_by_writer",
			data : $.param({
				sortType:searchCondition.sortType,
				key:searchCondition.key,
				nationality:searchCondition.nationality,
				type:searchCondition.type,
				typeSecond:searchCondition.typeSecond,
				minAmount:searchCondition.minAmount,
				maxAmount:searchCondition.maxAmount,
				tag:searchCondition.tag,
				pageIndex:searchCondition.pageIndex,
				pageSize:searchCondition.pageSize
			}, true),
			success : function(response) {
				console.log(response.data);
				var code = response.code;
				if ("200" == code) {
					if (response.data != null) {
						showDemandList(response.data.datas);
						showPageLink(response.data);
					}else{
						showPageLink(null);
					}
				}
			}
		});
		if(!init){
			location.href = "#region"; 
		}
		return false;
		    
	}

	var showDemandList = function(list) {
		$(".task-content").append($("#task-item-tmpl").render({dataList: list}));
		if(!g_ispc){
			$("a").removeAttr("target");
		}
	}
	
    function showPageLink(pageSplit) {
    	console.log(pageSplit);
    	
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
                    	$(".task-content").html("");
                    	loadDemandList(searchCondition,false);
                 	}
                    	
              	}
         	});
    	}else{
    		$(".search-pagination").html('<div>没有搜索到相关的任务 </div>');
    	}
    }
	
	var loadSumAmount = function() {
		var loadUserBySumAmount = function(pageIndex,pageSize) {
			$.ajax({
				type : "get",
				url : "/user/by_sum_amount",
				data: {
	     	    	"pageIndex" : pageIndex,
	     	    	"pageSize" : pageSize,
	     	    },  
				success : function(response) {
					console.log("======");
					console.log(response);
					var code = response.code;
					if ("200" == code) {
						var data = response.data;
						if (data != null) {
							$(".famous-people-list").html($('#famous-people-tmpl').render({dataList:data.datas}));
							$("[data-toggle='tooltip']").tooltip();
							if(!g_ispc){
								$("a").removeAttr("target");
							}
						}
					}
				}
			});

		}

		var loadDemandSumAmount = function() {
			$.ajax({
				type : "get",
				url : "/demand/get_sum_amount",
				success : function(response) {
					var code = response.code;
					console.log(response)
					if ("200" == code) {
						$(".money-amount-2").html(balanceFormat(response.data.sum));
						$(".task-amount-2").html(response.data.count);
					}
				} 
			});

		}

		var loadNewsList = function() {
			$.ajax({
				type : "get",
				url : "/news/search",
				data : {
					"pageIndex" : 1,
	     	    	"pageSize" : 6
				},
				success : function(response) {
					var code = response.code;
					if ("200" == code) {
						if (response.data != null) {
							showNewsList(response.data.datas);
						}
					}
				},
				error : function() {
				}
			});
		}
		
		var showNewsList = function(list) {
			$(".new-list").html($("#new-item-tmpl").render({dataList: list}));
			if(!g_ispc){
				$("a").removeAttr("target");
			}
		}
		
		var loadProUserList = function(){
			$.ajax({
				type : "get",
				url : "/pro_user/search",
				data: {
					"key" : "",
	     	    	"proTypeId" : "",
	     	    	"proId" : "",
	     	    	"pageIndex" : 1,
	     	    	"pageSize" : 5,
	     	    },  
				success : function(response) {
					var code = response.code;
					if (code == "200") {
						if (response.data != null) {
							showProUserList(response.data.datas);
						} else {
							showProUserList(null);
						}
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
				$('.pro-user-list').html($('#pro-user-tmpl').render({dataList:list}));
				if(!g_ispc){
					$("a").removeAttr("target");
				}
				//$('.user-follow').unbind("click");
				//$('.user-follow').click(userFollow);
			}
		}
		
		var loadActivityList = function(){
			$.ajax({
				type : "get",
				url : "/activity/search",
				data: {
	     	    	"pageIndex" : 1,
	     	    	"pageSize" : 5
	     	    },  
				success : function(response) {
					var code = response.code;
					if (code == "200") {
						if (response.data != null) {
							showActivityList(response.data.datas);
						} else {
							showActivityList(null);
						}
					} else{
						$.errorMsg(response.message);
					}
				}
			});
		}
		
		function showActivityList(list) {
			console.log(list)
			if(list==null){
				$('.activity').html("");
			}else{
				$('.activity').html($('#activity-tmpl').render({dataList:list}));
				/*图片轮播*/
				$('#myCarousel').on('slid.bs.carousel', function (event) {
					var title = $(event.relatedTarget).data("title");
					var time = $(event.relatedTarget).data("time");
					$(event.relatedTarget).parent().parent().parent().find(".title span").html(title);
					$(event.relatedTarget).parent().parent().parent().find(".time span").html(time);
			    });
				if(!g_ispc){
					$("a").removeAttr("target");
				}
			}
		}
		
		loadDemandSumAmount();
		loadUserBySumAmount(userSumAmountIndex,userSumAmountSize);
		loadNewsList();
		loadProUserList();
		
		loadActivityList();
	}

    return {
        //main function
        init: function() {
        	loadDemandList(searchCondition,true);
        	loadSumAmount();
        	$(window.document).on('click', '.glyphicon-remove', function() {
            	$(this).parent().find("b").html("");
            	$(this).parent().parent().addClass("hide");
            	if($(this).parent().parent().hasClass("tag-div")){
                	$(this).parent().parent().next().addClass("hide");
                	searchCondition.typeSecond = "";
            	}
            	
        		searchCondition.pageIndex = 1;
        		$(".task-content").html("")
        		loadDemandList(searchCondition,false);
        	})/*.on('click', '.crumb-select-item', function() {
            	$(this).parent().remove();
        		searchCondition.pageIndex = 1;
        		$(".task-content").html("")
        		loadDemandList(searchCondition,false);
        	})*/.on('click','a[name=tag-a]',function(){
        		var val = $(this).data("tag");
        		/*$($(".crumb-select-item").find("em")).each(function(){
        		    if($(this).html()==val) 
        		    	$(this).parent().parent().remove();
        		});*/
        		$('.tag-div').next().removeClass("hide");
        		$('.tag-div').removeClass("hide");
        		$('.tag-div').html($('#tag-items-tmpl').render({tag: val, type:0}));
        		//$('.search-key-div').before($('#tag-items-tmpl').render({tag: val, type:0}));
        		searchCondition.pageIndex = 1;
        		searchCondition.typeSecond = "";
        		$(".task-content").html("")
        		loadDemandList(searchCondition,false);
        		return false;
        	}).on('click','.specialty-tag',function(){
        		var val = $(this).data("tag");
        		$('.tag-div').next().removeClass("hide");
        		$('.tag-div').removeClass("hide");
        		$('.tag-div').html($('#tag-items-tmpl').render({tag: val, type:1}));
        		//$('.tag-div').remove();
        		//$('.search-key-div').before($('#tag-items-tmpl').render({tag: val,type:1}));
        		searchCondition.pageIndex = 1;
        		if($(this).hasClass("custom")){
        			searchCondition.typeSecond = "";
        		}else{
        			searchCondition.typeSecond = $(this).data("id");
        		}
        		$(".task-content").html("")
        		loadDemandList(searchCondition,false);
        		
        		return false;
        	});
        }
    };

}();
jQuery(document).ready(function() {
    index.init();
    
    /*登录后顶部搜索框显示和隐藏*/
    $(window).scroll(function() {
		if (($(window).scrollTop()+70>($('.search-bar').offset().top+80))
				||(($(window).scrollTop()+$(window).height())<$('.search-bar').offset().top)) {
			$(".top-search").removeClass('hide');
		}else{
			$(".top-search").addClass('hide');
		}

	});
});