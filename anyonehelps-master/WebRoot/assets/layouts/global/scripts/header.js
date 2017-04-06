$.views.tags({
	"substrOfTitle": function(title){
    	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    	if(reg.test(title)){ //含有中文 
    		if(title.length>28) return title.substr(0,28)+ '...';
         	else  return title;
    	}else {  //没有中文
    		if(title.length>56) return title.substr(0,56)+ '...';
         	else  return title;
    	}
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
      		return minute+"分钟前";
      	}else {
      		return "刚刚";
      	}
      	
      },
      "substrOfMessageContent" : function(content){
    	if(content.length>60) return content.substr(0,60)+ '...';
    	else  return content;
      },
      "showFileFormat" : function (filename){
    	  	if(filename==null||filename==""){
    	  		return "/assets/global/img/file-format/unknow.png";
    	  	} 
    	  	extend = filename.substr(filename.lastIndexOf(".")).toLowerCase();//获得文件后缀名

    	  	if(extend == '.bmp'){
    	        return "/assets/global/img/file-format/bmp.png";
    	    }else if(extend == '.doc'){
    	        return "/assets/global/img/file-format/doc.png";
    	    }else if(extend == '.docx'){
    	        return "/assets/global/img/file-format/docx.png";
    	    }else if(extend == '.gif'){
    	        return "/assets/global/img/file-format/gif.png";
    	    }else if(extend == '.jpeg'){
    	        return "/assets/global/img/file-format/jpeg.png";
    	    }else if(extend == '.jpg'){
    	        return "/assets/global/img/file-format/jpg.png";
    	    }else if(extend == '.pdf'){
    	        return "/assets/global/img/file-format/pdf.png";
    	    }else if(extend == '.png'){
    	        return "/assets/global/img/file-format/png.png";
    	    }else if(extend == '.ppt'){
    	        return "/assets/global/img/file-format/ppt.png";
    	    }else if(extend == '.pptx'){
    	        return "/assets/global/img/file-format/pptx.png";
    	    }else if(extend == '.psd'){
    	        return "/assets/global/img/file-format/psd.png";
    	    }else if(extend == '.xls'){
    	        return "/assets/global/img/file-format/xls.png";
    	    }else if(extend == '.xlsx'){
    	        return "/assets/global/img/file-format/xlsx.png";
    	    }else if(extend == '.zip'){
    	        return "/assets/global/img/file-format/zip.png";
    	    }else {
    	        return "/assets/global/img/file-format/unknow.png";
    	    }
    	   
    	},
    	"calculateGrade":function (grade){
    		return calculateGrade(grade);
    	},
    	"ratioGrade":function (distance, interval){
    		return (1-distance/interval)*100; 
    	},
    	"userEvaluateFormat": function(evaluate,evaluateCount){
     		 var e = (evaluate/evaluateCount).toFixed(1);
     		 if(e=="NaN"){
     			 e = 0;
     		 }
     		 ret = "<a title='"+e+" out of 5 stars.' href='javascript:void(0);'>"
     		 if(evaluateCount == "0")
     			 ret += '<img src="/assets/pages/img/index/star.png">' +
          	 			'<img src="/assets/pages/img/index/star.png">' +
          	 			'<img src="/assets/pages/img/index/star.png">' +
          	 			'<img src="/assets/pages/img/index/star.png">' +
          	 			'<img src="/assets/pages/img/index/star.png">';
     		 else{
     			 if(e>=5){
     				ret += '<img src="/assets/pages/img/index/star-padding.png">' +
      	 				   '<img src="/assets/pages/img/index/star-padding.png">' +
      	 				   '<img src="/assets/pages/img/index/star-padding.png">' +
      	 				   '<img src="/assets/pages/img/index/star-padding.png">' +
      	 				   '<img src="/assets/pages/img/index/star-padding.png">';
     			 }else if(e>=4){
     				ret += '<img src="/assets/pages/img/index/star-padding.png">' +
     		 			   '<img src="/assets/pages/img/index/star-padding.png">' +
     		 			   '<img src="/assets/pages/img/index/star-padding.png">' +
     		 			   '<img src="/assets/pages/img/index/star-padding.png">' +  
     		 			   '<img src="/assets/pages/img/index/star.png">';
             	}else if(e>=3){
             		ret += '<img src="/assets/pages/img/index/star-padding.png">' +
      		 			   '<img src="/assets/pages/img/index/star-padding.png">' +
      		 			   '<img src="/assets/pages/img/index/star-padding.png">' +
      		 			   '<img src="/assets/pages/img/index/star.png">' +
      		 			   '<img src="/assets/pages/img/index/star.png">';
             	}else if(e>=2){
             		ret += '<img src="/assets/pages/img/index/star-padding.png">' +
      	 				   '<img src="/assets/pages/img/index/star-padding.png">' +
      	 				   '<img src="/assets/pages/img/index/star.png">' +
      	 				   '<img src="/assets/pages/img/index/star.png">' +
      	 				   '<img src="/assets/pages/img/index/star.png">';
             	}else if(e>0){
             		ret += '<img src="/assets/pages/img/index/star-padding.png">' +
             		 	   '<img src="/assets/pages/img/index/star.png">' +
             		 	   '<img src="/assets/pages/img/index/star.png">' +
             		 	   '<img src="/assets/pages/img/index/star.png">' +
             		 	   '<img src="/assets/pages/img/index/star.png">';
             	}else if(e==0){
             		ret += '<img src="/assets/pages/img/index/star.png">' +
       				   '<img src="/assets/pages/img/index/star.png">' +
       				   '<img src="/assets/pages/img/index/star.png">' +
       				   '<img src="/assets/pages/img/index/star.png">' +
       				   '<img src="/assets/pages/img/index/star.png">';
             	}
     		 }
     		 ret += "</a>";
     		 return ret;
     	 },
     	"userAvgEvaluateFormat": function(evaluate,evaluateCount){
    		 var e = (evaluate/evaluateCount).toFixed(1);
    		 if(e=="NaN"){
    			 e = 0;
    		 }
    		 return e;
    	 },
    	 "evaluateFormat": function(evaluateAccepted,evaluateAcceptedCount,evaluatePublish,evaluatePublishCount){
        	 var evaluate = parseFloat(evaluateAccepted) + parseFloat(evaluatePublish);
        	 var count = parseFloat(evaluateAcceptedCount) + parseFloat(evaluatePublishCount);
        	 ret = "<a title='点击打开综合评级说明' href='javascript:void(0);'>"
        	 if(count == "0")
        		 ret += '<img src="/assets/pages/img/index/star.png">' +
            	 	'<img src="/assets/pages/img/index/star.png">' +
            	 	'<img src="/assets/pages/img/index/star.png">' +
            	 	'<img src="/assets/pages/img/index/star.png">' +
            	 	'<img src="/assets/pages/img/index/star.png">';
        	 else{
            	 var e = evaluate/count;
            	 if(e>=5){
            		 ret += '<img src="/assets/pages/img/index/star-padding.png">' +
        	 			'<img src="/assets/pages/img/index/star-padding.png">' +
        	 			'<img src="/assets/pages/img/index/star-padding.png">' +
       		 			'<img src="/assets/pages/img/index/star-padding.png">' +
        	 			'<img src="/assets/pages/img/index/star-padding.png">';
               	 }else if(e>=4){
               		 ret += '<img src="/assets/pages/img/index/star-padding.png">' +
       		 			'<img src="/assets/pages/img/index/star-padding.png">' +
       		 			'<img src="/assets/pages/img/index/star-padding.png">' +    		 			
       		 			'<img src="/assets/pages/img/index/star-padding.png">' +
        		 		'<img src="/assets/pages/img/index/star.png">';
               	 }else if(e>=3){
               		 ret += '<img src="/assets/pages/img/index/star-padding.png">' +
        		 		'<img src="/assets/pages/img/index/star-padding.png">' +
        		 		'<img src="/assets/pages/img/index/star-padding.png">' +
        		 		'<img src="/assets/pages/img/index/star.png">' +
        		 		'<img src="/assets/pages/img/index/star.png">';
               	 }else if(e>=2){
                	 ret += '<img src="/assets/pages/img/index/star-padding.png">' +
        	 			'<img src="/assets/pages/img/index/star-padding.png">' +
        	 			'<img src="/assets/pages/img/index/star.png">' +
        	 			'<img src="/assets/pages/img/index/star.png">' +
        	 			'<img src="/assets/pages/img/index/star.png">';
               	 }else if(e>0){
               		 ret += '<img src="/assets/pages/img/index/star-padding.png">' +
               		 	'<img src="/assets/pages/img/index/star.png">' +
               		 	'<img src="/assets/pages/img/index/star.png">' +
               		 	'<img src="/assets/pages/img/index/star.png">' +
               		 	'<img src="/assets/pages/img/index/star.png">';
               	 }else if(e==0){
               		 ret += '<img src="/assets/pages/img/index/star.png">' +
         				'<img src="/assets/pages/img/index/star.png">' +
         	 			'<img src="/assets/pages/img/index/star.png">' +
         	 			'<img src="/assets/pages/img/index/star.png">' +
         	 			'<img src="/assets/pages/img/index/star.png">';
               	 }
           	 }
           	 ret += "</a>"
        	 return ret;
         },
         "evaluateCountFormat": function(evaluateAcceptedCount, evaluatePublishCount){
     		 return parseFloat(evaluateAcceptedCount) + parseFloat(evaluatePublishCount);
     	 },
     	"base64Encode": function(data){
      		return base64Encode(data)
     	 },
      	 "base64Decode": function(data){
       		return base64Decode(data)
      	 }
});

function isPC(){
	var userAgentInfo = navigator.userAgent;
	console.log(userAgentInfo)
	var Agents = ["Android","iPhone","Windows Phone","iPad","iPod"];
	var flag = true;
	for(var v = 0;v<Agents.length;v++){
		if(userAgentInfo.indexOf(Agents[v])>0){
			flag = false;
			break;
		}
	}
	return flag;
}


var g_first = '<<';
var g_prev = '<';
var g_next = '>';
var g_last = '>>';
var g_ispc = false;
if(isPC()){
	g_first = '首页';
	g_prev = '上一页';
	g_next = '下一页';
	g_last = '最后一页';
	g_ispc = true;
}

/*base64 start*/ 
$.base64.utf8encode = true;
function base64Encode(data){
	return $.base64.encode(data);
}
function base64Decode(data){
	return $.base64.decode(data);
}
/*base64 end*/ 

/*facebook api start*/
window.fbAsyncInit = function() {
	FB.init({
		appId      : '1596144240676397',
		xfbml      : true,
		version    : 'v2.6'
    });
};

(function(d, s, id){
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) {return;}
	js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    js.async="async";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
/* facebook api end*/

/* 百度统计 start*/
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?692d19f9aa708893439796cb10e9585a";
  hm.async="async";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
/* 百度统计 end*/

function logout() {
	$.ajax({ 
		type:"get",
		url:"/user/logout",
		success:function(response){
			var code = response.code;
			if ("200" == code) {
				window.location.href = "/";
			}
		}
	});
	/*$.get("/user/logout", function(response) {
		alert("******") 
		
	});*/
	//return false; NaN
}

function calculateGrade(grade) {
	var result = 1;
	if(grade!=null&&grade!=""){
		var temp = parseInt(grade);
		
    	if (!isNaN(temp)){
    		if(temp>=203000){
    			result=51;
    		}else if(temp>=193000){
    			result=50;
    		}else if(temp>=183000){
    			result=49;
    		}else if(temp>=173000){
    			result=48;
    		}else if(temp>=163000){
    			result=47;
    		}else if(temp>=153000){
    			result=46;
    		}else if(temp>=143000){
    			result=45;
    		}else if(temp>=133000){
    			result=44;
    		}else if(temp>=123000){
    			result=43;
    		}else if(temp>=113000){
    			result=42;
    		}else if(temp>=103000){
    			result=41;
    		}else if(temp>=203000){
    			result=40;
    		}else if(temp>=97000){
    			result=39;
    		}else if(temp>=91000){
    			result=38;
    		}else if(temp>=85000){
    			result=37;
    		}else if(temp>=79000){
    			result=36;
    		}else if(temp>=73000){
    			result=35;
    		}else if(temp>=67000){
    			result=34;
    		}else if(temp>=61000){
    			result=33;
    		}else if(temp>=55000){
    			result=32;
    		}else if(temp>=49000){
    			result=31;
    		}else if(temp>=43000){
    			result=30;
    		}else if(temp>=37000){
    			result=29;
    		}else if(temp>=34000){
    			result=28;
    		}else if(temp>=31000){
    			result=27;
    		}else if(temp>=28000){
    			result=26;
    		}else if(temp>=25000){
    			result=25;
    		}else if(temp>=22000){
    			result=24;
    		}else if(temp>=19000){
    			result=23;
    		}else if(temp>=16000){
    			result=22;
    		}else if(temp>=13000){
    			result=21;
    		}else if(temp>=12000){
    			result=20;
    		}else if(temp>=11000){
    			result=19;
    		}else if(temp>=10000){
    			result=18;
    		}else if(temp>=9000){
    			result=17;
    		}else if(temp>=8000){
    			result=16;
    		}else if(temp>=7000){
    			result=15;
    		}else if(temp>=6000){
    			result=14;
    		}else if(temp>=5000){
    			result=13;
    		}else if(temp>=4000){
    			result=12;
    		}else if(temp>=3000){
    			result=11;
    		}else if(temp>=2500){
    			result=10;
    		}else if(temp>=2000){
    			result=9;
    		}else if(temp>=1500){
    			result=8;
    		}else if(temp>=1000){
    			result=7;
    		}else if(temp>=500){
    			result=6;
    		}else if(temp>=400){
    			result=5;
    		}else if(temp>=300){
    			result=4;
    		}else if(temp>=200){
    			result=3;
    		}else if(temp>=100){
    			result=2;
    		}else {
    			result=1;
    		}
    	}
	}
	
	return result;
}

function showGrade(grade) {
	var result = 1;//等级
	var distance =  0; //距离下一等级金额
	var interval = 100;//距离下一等级金额区间
	if(grade!=null&&grade!=""){
		var temp = parseInt(grade);
		
    	if (!isNaN(temp)){
    		if(temp>=203000){
    			result=50;
    			distance = 0;
    			interval = 10000;
    		}else if(temp>=193000){
    			result=49;
    			distance = 203000 -temp;
    			interval = 10000;
    		}else if(temp>=183000){
    			result=48;
    			distance = 193000 -temp;
    			interval = 10000;
    		}else if(temp>=173000){
    			result=47;
    			distance = 183000 -temp;
    			interval = 10000;
    		}else if(temp>=163000){
    			result=46;
    			distance = 173000 -temp;
    			interval = 10000;
    		}else if(temp>=153000){
    			result=45;
    			distance = 163000 -temp;
    			interval = 10000;
    		}else if(temp>=143000){
    			result=44;
    			distance = 153000 -temp;
    			interval = 10000;
    		}else if(temp>=133000){
    			result=43;
    			distance = 143000 -temp;
    			interval = 10000;
    		}else if(temp>=123000){
    			result=42;
    			distance = 133000 -temp;
    			interval = 10000;
    		}else if(temp>=113000){
    			result=41;
    			distance = 123000 -temp;
    			interval = 10000;
    		}else if(temp>=103000){
    			result=40;
    			distance = 113000 -temp;
    			interval = 10000;
    		}else if(temp>=97000){
    			result=39;
    			distance = 103000 -temp;
    			interval = 6000;
    		}else if(temp>=91000){
    			result=38;
    			distance = 97000 -temp;
    			interval = 6000;
    		}else if(temp>=85000){
    			result=37;
    			distance = 91000 -temp;
    			interval = 6000;
    		}else if(temp>=79000){
    			result=36;
    			distance = 85000 -temp;
    			interval = 6000;
    		}else if(temp>=73000){
    			result=35;
    			distance = 79000 -temp;
    			interval = 6000;
    		}else if(temp>=67000){
    			result=34;
    			distance = 73000 -temp;
    			interval = 6000;
    		}else if(temp>=61000){
    			result=33;
    			distance = 67000 -temp;
    			interval = 6000;
    		}else if(temp>=55000){
    			result=32;
    			distance = 61000 -temp;
    			interval = 6000;
    		}else if(temp>=49000){
    			result=31;
    			distance = 55000 -temp;
    			interval = 6000;
    		}else if(temp>=43000){
    			result=30;
    			distance = 49000 -temp;
    			interval = 6000;
    		}else if(temp>=37000){
    			result=29;
    			distance = 43000 -temp;
    			interval = 6000;
    		}else if(temp>=34000){
    			result=28;
    			distance = 37000 -temp;
    			interval = 3000;
    		}else if(temp>=31000){
    			result=27;
    			distance = 34000 -temp;
    			interval = 3000;
    		}else if(temp>=28000){
    			result=26;
    			distance = 31000 -temp;
    			interval = 3000;
    		}else if(temp>=25000){
    			result=25;
    			distance = 28000 -temp;
    			interval = 3000;
    		}else if(temp>=22000){
    			result=24;
    			distance = 25000 -temp;
    			interval = 3000;
    		}else if(temp>=19000){
    			result=23;
    			distance = 22000 -temp;
    			interval = 3000;
    		}else if(temp>=16000){
    			result=22;
    			distance = 19000 -temp;
    			interval = 3000;
    		}else if(temp>=13000){
    			result=21;
    			distance = 16000 -temp;
    			interval = 3000;
    		}else if(temp>=12000){
    			result=20;
    			distance = 13000 -temp;
    			interval = 1000;
    		}else if(temp>=11000){
    			result=19;
    			distance = 12000 -temp;
    			interval = 1000;
    		}else if(temp>=10000){
    			result=18;
    			distance = 11000 -temp;
    			interval = 1000;
    		}else if(temp>=9000){
    			result=17;
    			distance = 10000 -temp;
    			interval = 1000;
    		}else if(temp>=8000){
    			result=16;
    			distance = 9000 -temp;
    			interval = 1000;
    		}else if(temp>=7000){
    			result=15;
    			distance = 8000 -temp;
    			interval = 1000;
    		}else if(temp>=6000){
    			result=14;
    			distance = 7000 -temp;
    			interval = 1000;
    		}else if(temp>=5000){
    			result=13;
    			distance = 6000 -temp;
    			interval = 1000;
    		}else if(temp>=4000){
    			result=12;
    			distance = 5000 -temp;
    			interval = 1000;
    		}else if(temp>=3000){
    			result=11;
    			distance = 4000 -temp;
    		}else if(temp>=2500){
    			result=10;
    			distance = 3000 -temp;
    			interval = 500;
    		}else if(temp>=2000){
    			result=9;
    			distance = 2500 -temp;
    			interval = 500;
    		}else if(temp>=1500){
    			result=8;
    			distance = 2000 -temp;
    			interval = 500;
    		}else if(temp>=1000){
    			result=7;
    			distance = 1500 -temp;
    			interval = 500;
    		}else if(temp>=500){
    			result=6;
    			distance = 1000 -temp;
    			interval = 500;
    		}else if(temp>=400){
    			result=5;
    			distance = 500 -temp;
    			interval = 100;
    		}else if(temp>=300){
    			result=4;
    			distance = 400 -temp;
    			interval = 100;
    		}else if(temp>=200){
    			result=3;
    			distance = 300 -temp;
    			interval = 100;
    		}else if(temp>=100){
    			result=2;
    			distance = 200 -temp;
    			interval = 100;
    		}else {
    			result=1;
    			distance = 100 -temp;
    			interval = 100;
    		}
    	}
	}
	return {"grade":grade,"distance":distance,"interval":interval};
}

function resetValidateCode(event) {
	$(event).attr('src','code/generate?timestamp=' + new Date().getTime());
}


var header = function() {
	$('.save-password input').iCheck({
		checkboxClass: 'icheckbox_square-blue',
		radioClass: 'iradio_square-blue',
		increaseArea: '20%' // optional
	});
	
	
	var temp1 = Math.ceil(Math.random()*999);
	var temp2 = Math.ceil(Math.random()*999);
	var temp3 = Math.ceil(Math.random()*999);
	$(".message-system .message-external a.more").attr("href","/dashboard/Account/messages.jsp?name="+temp1+"#tab_1_1")
	$(".message-user .message-external a.more").attr("href","/dashboard/Account/messages.jsp?name="+temp2+"#tab_1_2")
	$(".message-private .message-external a.more").attr("href","/dashboard/Account/messages.jsp?name="+temp3+"#tab_1_3")
	
	
	$(".message-system .message-external a.more").click(function(){
		$.ajax({
			type:"get",
			url:"/message_system/read_all",
			success:function(response){
				var code = response.code;
				if ("200" == code) {
				}
			}
		});
	})
	$(".message-user .message-external a.more").click(function(){
		$.ajax({ 
			type:"get",
			url:"/message_user/read_all",
			success:function(response){
				var code = response.code;
				
				if ("200" == code) {
				}
			}
		});
	})
	//头像 鼠标放在上面效果
	$('div.person').mouseover(function() {  
		$('div.message-system').removeClass("open");
		$('div.message-user').removeClass("open");
		$('div.message-private').removeClass("open");
	    $(this).addClass('open'); 
	});
	//头像 鼠标放在上面效果
	$('div.message-system').mouseover(function() {   
		$('div.person').removeClass("open");
		$('div.message-user').removeClass("open");
		$('div.message-private').removeClass("open");
	    $(this).addClass('open'); 
	});
	//头像 鼠标放在上面效果
	$('div.message-user').mouseover(function() {  
		$('div.message-system').removeClass("open");
		$('div.person').removeClass("open");
		$('div.message-private').removeClass("open");
	    $(this).addClass('open'); 
	});
	//头像 鼠标放在上面效果
	$('div.message-private').mouseover(function() {  
		$('div.message-user').removeClass("open");
		$('div.message-system').removeClass("open");
		$('div.person').removeClass("open");
	    $(this).addClass('open'); 
	});
	
	/*鼠标指针不在顶部消息按钮上和下拉的菜单上时，收起菜单*/
	var messageHover = false;
	var menuHover = false;
	$(".header-top .top-nav .info").mouseover(function(){
		messageHover = true;
	});
	$(".header-top .top-nav .info").mouseout(function(){
		messageHover = false;
	});
	$(".header-top .top-nav .info .dropdown-menu").mouseover(function(){
		menuHover = true;
	});
	$(".header-top .top-nav .info .dropdown-menu").mouseout(function(){
		menuHover = false;
	});
	function showMessageMenu(){
		if(messageHover||menuHover){  
		}else{  
			$(".header-top .top-nav .info").removeClass("open");
		}  
		setTimeout(showMessageMenu, 1000);
	}
	showMessageMenu();

	$(".header-top .top-nav .info").click(function(){
		var open = false;
		if(this.hasClass("open")){
			open = true;
		}
		$('div.message-user').removeClass("open");
		$('div.message-system').removeClass("open");
		$('div.message-private').removeClass("open");
		$('div.person').removeClass("open");
		if(open){
			$(this).removeClass('open'); 
		}else{
			$(this).addClass('open'); 
		}
	    
	});
	
	/*顶部发布任务按钮*/
	$(".header-top .top-nav .task-pub-btn >button").click(function(){
		window.location.href="/dashboard/Task/add.jsp";
	})
	
	function loadUserInfo(){
    	// 获取用户数据
    	$.ajax({
    		type:"get",
    		url:"/user/get_self",
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") {
    				var data = response.data;
    				$(".header-top .top-nav .person .grade").html("Lv"+calculateGrade(data.grade));
    				$(".header-top .top-nav .person .grade").addClass("show-grade");
    				$(".header-top .top-nav .person .grade").data("grade",data.grade);
    				$(".header-top .top-nav .person .grade").data("align","bottom right");
    				$(".header-top .top-nav .person .grade").data("left","353");
    				$(".header-top .top-nav .person .grade").data("top","40");
    			} else if ("607" == code) {
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
    }	
	
	var initLogin = function() {
		//读取密码
        if ($.cookie("rmbUser") == "true") {
        	$(".save-password input").attr("checked", true);
        	$(".save-password input").parent().addClass("checked");
        	$(".user-login").find(":text[name=username]").val($.cookie("account"));
        	$(".user-login").find(":password[name=password]").val($.cookie("password"));
    	}
        
        //读取密码
        if ($.cookie("rmbUserByPhone") == "true") {
        	$(".save-password input").attr("checked", true);
        	$(".save-password input").parent().addClass("checked");
        	
			$(".user-login").find(":text[name=telphone]").val($.cookie("phone"));
    		$(".user-login").find(":password[name=password]").val($.cookie("passwordByPhone"));
    		$(".user-login").find(".area-code").val($.cookie("areaCode"));
    	}
        
		$(".refresh-code").click(function(){
			resetValidateCode($(this).prev());
		})
		$(".refresh-code").prev().click(function(){
			resetValidateCode($(this));
		})
		$("a[name='login-a']").click(function(){
			$(".user-login").removeClass("hide");
			$(".mbblack").removeClass("hide");
			$.ajax({  
        		type: "POST",  
        		url: "user/check_code",  
        		data: {
        			"codeType" : 1
        		},  
        		dataType: "json",  
        		success : function(response) {
        			var code = response.code;
       				if (code == "200") {
       					if(response.data=="1"){
       						$(".show-login-code").removeClass("hide");
       			            resetValidateCode($(".refresh-code").prev());
       					}
       				}
       			}
        	});  
		})
		/*$("a[name='login-close-a']").click(function(){
			alert("===")
			$(".user-login").addClass("hide");
			$(".mbblack").addClass("hide");
		})*/
		$(".user-login .close").click(function(){
			$(".user-login").addClass("hide");
			$(".mbblack").addClass("hide");
		})
		

		
		var saveAccountByPhone = function(phone,areaCode,password) {
            if ($(".save-password input").is(":checked")) {
            	$.cookie("rmbUserByPhone", "true", {expires : 30}); //存储一个带30天期限的cookie
            	$.cookie("phone", phone, {expires : 30});
            	$.cookie("areaCode", areaCode, {expires : 30});
            	$.cookie("passwordByPhone", password, {expires : 30});
            } else {
            	$.cookie("rmbUserByPhone", "false", { expire : -1});
            	$.cookie("phone", "", {expires : -1});
            	$.cookie("areaCode", "", {expires : -1});
            	$.cookie("passwordByPhone", "", {expires : -1});
            }
        };
        
        var saveAccount = function(account, password) {
    		if ($(".save-password input").is(":checked")) {
    			$.cookie("rmbUser", "true", {expires : 30}); //存储一个带30天期限的cookie
    			$.cookie("account", account, {expires : 30});
    			$.cookie("password", password, {expires : 30});
    		} else {
    			$.cookie("rmbUser", "false", { expire : -1});
    			$.cookie("account", "", {expires : -1});
    			$.cookie("password", "", {expires : -1});
    		}
    	};
		
		
		/*haokun added start 2017/03/23 点击保存密码颜色变亮*/
        $('input[name="login-checkbox"]').on('ifChecked', function(event){
             $(".save-password span").css("color","#ffffff !important");
    	});
		
		$('input[name="login-checkbox"]').on('ifUnchecked', function(event){
             $(".save-password span").css("color","#808080 !important");
    	});
		

		/*haokun added end 2017/03/23 点击保存密码颜色变亮*/
		
		$("#box-btn").click(function(){
			var vcode = $("input[name='login-code']").val();
			var password = $(':password[name=password]').val();
			if($(".login-phone-a").data("logintype")=="phone"){
				var account = $(':text[name=username]').val()
				$.ajax({  
		     		type: "POST",  
		     	  	url: "/user/login",  
		     	  	data: {
		     	  		"account" : account,
		     	  		"password" : password,
		    	    	"vcode" : vcode
		    	  	}, 
		     		dataType: "json",  
		     		success : function(response) {
		    			var code = response.code;
		    			if (code == "200") {
		    				saveAccount(account, password);
		    				location.reload(); 
		    			}else if(code == "12026"){
		    				//验证码
		    				$(".show-login-code").removeClass("hide");
		    				resetValidateCode($(".refresh-code").prev());
		    				alert(response.message);
		    			}else if(code == "12024"){
							window.location.href = "/forbidden/userForbidden.jsp";
						}else {
		    				alert(response.message);
		    			}
		    		}
				});  
			}else if($(".login-phone-a").data("logintype")=="email"){
				var areaCode = $(this).parent().parent().find(".area-code").val();
    			var phone = $(this).parent().parent().find(':text[name=telphone]').val();
				$.ajax({  
		     		type: "POST",  
		     	  	url: "/user/phone_login",  
		     	  	data: {
		     	  		"areaCode" : areaCode,
            			"phone" : phone,
		     	  		"password" : password,
		     	  		"vcode" : vcode
		    	  	}, 
		     		dataType: "json",  
		     		success : function(response) {
		    			var code = response.code;
		    			if (code == "200") {
		    				saveAccountByPhone(phone,areaCode,password);
		    				location.reload(); 
		    			}else if(code == "12026"){
		    				//验证码
		    				$(".show-login-code").removeClass("hide");
		    				resetValidateCode($(".refresh-code").prev());
		    				alert(response.message);
		    			}else if(code == "12024"){
							window.location.href = "/forbidden/userForbidden.jsp";
						}else {
		    				alert("登录失败，失败原因是:" + response.message);
		    			}
		    		}
				});  
			}
			return false;
		})
		
		$(".login-phone-a").click(function(){
			if($(this).data("logintype")=="phone"){
				$(".login-form").find("center.email").addClass("hide");
				$(".login-form").find("center.phone").removeClass("hide");
				$(this).data("logintype","email");
				//$(this).html("邮箱登录");         //haokun deleted 2017/03/20
				$(this).html("Sign In By Email");   //haokun added 2017/03/20
			}else if($(this).data("logintype")=="email"){
				$(".login-form").find("center.email").removeClass("hide");
				$(".login-form").find("center.phone").addClass("hide");
				$(this).data("logintype","phone");
				//$(this).html("手机登录");   //haokun deleted 2017/03/20
				$(this).html("Sign In By Phone");    //haokun added 2017/03/20
			}
			
			return false;
		});
		
	    if(!isPC()){
			if ($.cookie("enable") != "true") {
				var d = dialog({
		    		width:'100%',
		    		title:"温馨提醒",
		    		skin:"mobile-tip",
				    onshow : function() {
				    	this.content("<span>为了您更好的使用Anyonehelps的功能，建议电脑登录。</span>");     
				    }
				});
		    	d.showModal();
	    		$.cookie("enable", true, {expires : 100000000});
	    		setTimeout(function () {
		    	    d.close().remove();
		    	}, 5000);
	    	}
	    }
		
	}
	

	var handleThirdLogin = function(){
    	$(".facebook").click(function(){
    		  FB.login(function(response) {
    			if (response.authResponse) {
    				console.log(response);
    				console.log('Welcome!  Fetching your information.... ');
    				var token = response.authResponse.accessToken;
    				var id = response.authResponse.userID;
    				var name ="";
    				var email = "";
    			    FB.api('/me?fields=name,first_name,last_name,email,picture', function(response) {
    			    	console.log(response);
    			    	console.log('Good to see you, ' + response.name + '.');
    			    	console.log('Good to see you,email: ' + response.email + '.');
        			    $.ajax({
        					type : "post",
        					url : "/user/fb_login",
        					data : {
        						"fbToken" : token,
        						"fbId" : id,
        						"fbName" : response.name,
        						"fbEmail": response.email,
        						"isloging": "1",
        						"picture" : response.picture.data.url
        					},
        					success : function(response) {
        						var code = response.code;
        						if(code == "200") {
        							window.location.href = "/";
        						}else if(code == "12024"){
        							window.location.href = "/forbidden/userForbidden.jsp";
        						}else  {
        							alert(response.message);
        						}
        					}
        				});
    			    });
    			    
    			} else {
    			 	console.log('User cancelled login or did not fully authorize.');
    			}
    		}, {scope: 'email,public_profile'});
    		return false;
    	  })
    	  $(".weixin").click(function(){
    		$(".user-login").addClass("hide");
  			//$(".mbblack").addClass("hide");
  			$(".weixin-login").removeClass("hide");


  			$(".weixin-login .close").click(function(){
  				$(".weixin-login").addClass("hide");
  				$(".user-login").removeClass("hide");
  			})
  			
    		var obj = new WxLogin({
    			id:"winxin-content", 
    			appid: "wxffe1847ad9cb0c56", 
             	scope: "snsapi_login", 
             	redirect_uri: encodeURIComponent("http://www.anyonehelps.com/user/wx_login"), 
              	state: "",
             	style: "",
              	href: ""
            });
    	  });
    	
    	
    }
	
	var loadMessage = function(){
		
		var modifyMessageUserState = function(id) {
			$.ajax({
				type:"get",
				url:"/message_user/read",
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
				}
			});
			return false;
		}
		var loadMessageUserList = function(key,state,pn,ps) {
			$.ajax({
				type:"get",
				url:"/message_user/search",
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
							console.log(response.data.datas);
							$(".message-user-count").html(response.data.rowCount);
							if(response.data.rowCount>0){
								$(".message-user-count").removeClass("hide");
							}
							$('.message-user-list').html($('#message-user-items-tmpl').render({dataList: response.data.datas}));
							
							$("a[name='message-user-link']").click(function(){
								var id = $(this).parent().data("id");
								modifyMessageUserState(id);
							})
						}
					} else if ("607" == code) {
						//跳至登录处
						//logout();
					} 
					setTimeout(function(){loadMessageUserList("","0","1","5")},2*60*1000);
				}
			});
			return false;
		}
		
		var loadMessageList = function(key,state,pn,ps) {
			$.ajax({
				type:"get",
				url:"/message/search",
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
							console.log(response.data.datas);
							$(".message-count").html(response.data.rowCount);
							if(response.data.rowCount>0){
								$(".message-count").removeClass("hide");
							}
							$('.message-list').html($('#message-items-tmpl').render({dataList: response.data.datas}));
							
							/*$("a[name='message-link']").click(function(){
								var id = $(this).parent().data("id");
								//modifyMessageState(id);
							})*/
						}
					} else if ("607" == code) {
						//跳至登录处
						//logout();
					} 
					setTimeout(function(){loadMessageList(key,state,pn,ps)},2*60*1000);
					
				}
			});
			return false;
		}
		
		
		var modifyMessageSystemState = function(id) {
			$.ajax({
				type:"get",
				url:"/message_system/read",
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
				}
			});
			return false;
		}
		
		var loadMessageSystemList = function(key,state,pn,ps) {
			$.ajax({
				type:"get",
				url:"/message_system/search",
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
							$(".message-system-count").html(response.data.rowCount);
							if(response.data.rowCount>0){
								$(".message-system-count").removeClass("hide");
							}
							$('.message-system-list').html($('#message-system-items-tmpl').render({dataList: response.data.datas}));
							$("a[name='message-system-link']").click(function(){
								var obj = $(this)
								var id = obj.parent().data("id");
								var recommender = obj.data("recommender");
								
								modifyMessageSystemState(id);
								if(recommender!=null&&recommender!=""&&recommender!="-1"){
									obj.parent().next().remove();
									obj.parent().remove();
									$(".message-system-count").html($(".message-system-count").html() - 1);
									
									var d = dialog({
		    						    width:'480px',
		    						    skin: 'recommend-dlg',
		    						    onshow : function() {
		    						    	var title = obj.data("title");
		    						    	this.content($('#recommend-dlg').render({title:title}));
		    						    	$(".recommend-dlg .recommend-dlg-content .operate button.accepted").click(function(){
		    						    		$.ajax({
		    										type:"get",
		    										url:"/recommend/accepted",
		    										data:{
		    											recommender:recommender
		    										},
		    										success:function(response){
		    											var code = response.code;
		    											if ("200" == code) {
		    												$.successMsg("接受成功");
		    												d.close();
		    											} else if ("607" == code) {
		    												//跳至登录处
		    												alert("您尚未登录，或者登录失败，请重新登录！");
		    												logout();
		    											} else{
		    												$.errorMsg(response.message);
		    											}
		    										}
		    									});
		    									return false;
		    						    	})
		    						    	$(".recommend-dlg .recommend-dlg-content .operate button.reject").click(function(){
		    						    		$.ajax({
		    										type:"get",
		    										url:"/recommend/reject",
		    										data:{
		    											recommender:recommender
		    										},
		    										success:function(response){
		    											var code = response.code;
		    											if ("200" == code) {
		    												$.successMsg("拒绝成功");
		    												d.close();
		    											} else if ("607" == code) {
		    												//跳至登录处
		    												alert("您尚未登录，或者登录失败，请重新登录！");
		    												logout();
		    											} else{
		    												$.errorMsg(response.message);
		    											}
		    										}
		    									});
		    									return false;
		    						    	})
		    						    	
		    						    }
		    						});
		    						d.showModal();
									
									
									return false;
								}
								
							})
						}
					} else if ("607" == code) {
						//跳至登录处
						//logout();
					} 
					setTimeout(function(){loadMessageSystemList("","0","1","5")},2*60*1000);
					
				}
			});
			return false;
		}
		
		loadMessageSystemList("","0","1","5"); //系统消息
		loadMessageUserList("","0","1","5"); //未读留言
		loadMessageList("","0","1","5"); //未读留言
	}
	
	// Handles the go to top button at the footer
    var handleGoTop = function() {
        var offset = 300;
        var duration = 500;

        if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) { // ios supported
            $(window).bind("touchend touchcancel touchleave", function(e) {
                if ($(this).scrollTop() > offset) {
                    $('.scroll-to-top').fadeIn(duration);
                } else {
                    $('.scroll-to-top').fadeOut(duration);
                }
            });
        } else { // general 
            $(window).scroll(function() {
                if ($(this).scrollTop() > offset) {
                    $('.scroll-to-top').fadeIn(duration);
                } else {
                    $('.scroll-to-top').fadeOut(duration);
                }
            });
        }

        $('.scroll-to-top').click(function(e) {
            e.preventDefault();
            $('html, body').animate({
                scrollTop: 0
            }, duration);
            return false;
        });
    };
	
    return {
        //main function
        init: function() {
        	initLogin();
        	handleThirdLogin();
        	loadMessage();
        	loadUserInfo();
        	handleGoTop();
        }
    };

}();
jQuery(document).ready(function() {
	header.init();
	
    $(window.document).on('click', '.user-pic', function() {
		var id = $(this).data().id;
		window.open( "/profile.jsp?userId="+base64Encode(id.toString()));
		return false;
	}).on('click', '.show-evaluate', function(e){
		var userId = $(this).data("userid");
    	var honest = $(this).data("honest");
    	var quality = $(this).data("quality");
    	var punctual = $(this).data("punctual");
    	var evaluateCount = $(this).data("evaluatecount");
    	var honestPublish = $(this).data("honestpublish");
    	var evaluatePublishCount = $(this).data("evaluatepublishcount");
    	var align = $(this).data("align");
    	var left = $(this).data("left");
    	var vclass = "";
    	if(left=="353"){
    		vclass = "left-353";
    	}
    	var d = dialog({
    		align:align,
    		title: '综合评级',
		    width:'300px',
		    skin: 'user-evaluate-dlg '+vclass+'',
		    quickClose : true,
		    onshow : function() {
				this.content($("#show-evaluate-tmpl").render({userId:userId.toString(), honest:honest, quality:quality, punctual:punctual, evaluateCount:evaluateCount, honestPublish:honestPublish, evaluatePublishCount:evaluatePublishCount}));
			}
		}).show(this);
    	return false;
	}).on('click', '.show-grade', function(e){
		
		var grade = $(this).data("grade");
		var gradeInfo = showGrade(grade);
		var distance =  gradeInfo["distance"]; //距离下一等级金额
		var interval = gradeInfo["interval"];//距离下一等级金额区间
		var currInterval = interval - distance;//距离区间最小值金额
    	
    	var align = $(this).data("align");
    	var left = $(this).data("left");
    	var top = $(this).data("top");
    	var vclass = "";
    	if(left=="353"){
    		vclass += "left-353";
    	}
    	if(top=="40"){
    		vclass += " top-40";
    	}
    	
    	var d = dialog({
    		align:align,
    		title: '等级',
		    width:'300px',
		    skin: 'grade-dlg '+vclass+'',
		    quickClose : true,
		    onshow : function() {
				this.content($("#show-grade-tmpl").render({grade:grade,distance:distance,interval:interval,currInterval:currInterval}));
			}
		}).show(this);
    	return false;
	}).on('click', 'body', function(e){
		//$('div.person').removeClass("open");
	})
});