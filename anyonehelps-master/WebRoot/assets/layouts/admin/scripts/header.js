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
    	   
    	},calculateGrade:function (grade){
    		return calculateGrade(grade);
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
     	 "base64Encode": function(data){
      		return base64Encode(data)
     	 },
      	 "base64Decode": function(data){
       		return base64Decode(data)
      	 }
    
});

/*base64 start*/ 
$.base64.utf8encode = true;
function base64Encode(data){
	return $.base64.encode(data);
}
function base64Decode(data){
	return $.base64.decode(data);
}
/*base64 end*/ 

function logout() {
	$.get("/admin/logout", function(response) {
		window.location.href = "/admin/login.jsp";
	});
	//return false;
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

var header = function() {
	
	var initLogin = function() {

	}

	
    return {
        //main function
        init: function() {
        	initLogin();
        	
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
				this.content($("#show-evaluate-tmpl").render({userId:userId,honest:honest, quality:quality, punctual:punctual, evaluateCount:evaluateCount, honestPublish:honestPublish, evaluatePublishCount:evaluatePublishCount}));
			}
		}).show(this);
    	return false;
	})
});