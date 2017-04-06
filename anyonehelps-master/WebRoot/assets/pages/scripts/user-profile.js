$.views.tags({
	"status": function(status){
    	switch (true){
    	case status == 0: 
    		return '<span class="font-green">竞标</span>';
    	case status == 1:
    		return '<span class="font-red">竞标</span>';
    	case status == 2:
    		return '<span class="font-red">竞标</span>';
    	case status == 3:
    		return '<span style="color:#4586d0">开始</span>';
    	case status == 4:
    		return '<span style="color:#4586d0">关闭</span>';
    	case status == 5:
    		return '<span style="color:#4586d0">完成</span>';
    	case status == 6:
    		return '<span style="color:#4586d0">支付</span>';
    	case status == 7:
    		return '<span style="color:#4586d0">支付</span>';
    	case status == 8:
        		return '<span style="color:#4586d0">结束</span>';
    	default:
    		return '<span class="font-red">未知</span>';
    	}
    	
    },
    "tonedFormat": function(rds){
    	var tonedCount = 0;
		if (rds != null) {
			$.each(rds, function(e) {
				if(this.state=="1"&&tonedCount<3){
					tonedCount++;
				}
			})
		}
		return tonedCount;
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
     "substrOfContent": function(content){
     	var c = $("<p>"+content+"</p>").text();
     	if(c.length>200){
     		return c.substr(0,200)+ '...';
     	}else {
     		return c;
     	}
     	
     },
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
       "typeFormat": function(type){
     	  var strType = "<div class='type-other'>其他</div>";
     	  if(type=="1"){
     		  strType = "<div class='type-learn'>学习</div>";
       	  }else if(type=="2"){
       		  strType = "<div class='type-life'>生活</div>";
       	  }else if(type=="3"){
       		strType = "<div class='type-work'>工作</div>";
       	  }
       	  return strType;
       },
       "tagFormat": function(tag){
     		var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
     		var count = 0;  //读取多少个字了
     		var strTag = "<div style='margin-left: 20px;'>";
     	  
     		var tagArray = tag.split("#");		
     		for(var k=0;k<tagArray.length;k++){		
     			if(tagArray[k]!=""&&tagArray[k]!=null){
     			  		var bExist = false; //是否含有中文
     			  		if(reg.test(tagArray[k])){ //含有中文
     			  			count += 2*tagArray[k].length+2; //加2是井号长度
     			  			bExist = true;
     			  		}else {  //没有中文
     			  			count += tagArray[k].length+2;
     			  		}
     				
     			  		if(count<30){
     			  			strTag += "<a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'>#"+tagArray[k]+"</a>";
     			  		}else{
     			  			if(bExist){ //含有中文
     			  				if(count-(2*tagArray[k].length+2)==0){  //第一个标签就超出范围
     			  					strTag += "<a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'>#"+tagArray[k].substr(0,13)+"...</a>";
     			  				}
     			  			}else {  //没有中文
     			  				if(count-(tagArray[k].length+2)==0){    //第一个标签就超出范围
     			  					strTag += "<a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'>#"+tagArray[k].substr(0,26)+"...</a>";
     			  				}
     			  			}
     					
     			  		}
     			}
     		}
     		strTag += "</div>"
     			return strTag;
   	 	},
       "education": function(education){
    	   switch (true){
       			case education == 1:
       				return '高中毕业或其他同等学历';
       			case education == 2:
       				return '大专学历';
       			case education == 3:
       				return '大学本科学历';
       			case education == 4:
       				return '硕士研究生学历';
       			case education == 5:
       				return '博士学位';
       			default:
       				return '未知';
       	}
    },
	 "evaluateScoreFormat": function(evaluate, quality, punctual){
		 if(quality!=null&&quality!=""&&punctual!=null&&punctual!=""){
			 evaluate =  (parseInt(evaluate)+parseInt(quality)+parseInt(punctual))/3;
		 }
		 var ret = "";
		 if(evaluate>=5){
  			ret += '<img src="/assets/pages/img/index/star-padding.png">' +
   	 			'<img src="/assets/pages/img/index/star-padding.png">' +
   	 			'<img src="/assets/pages/img/index/star-padding.png">' +
   	 			'<img src="/assets/pages/img/index/star-padding.png">' +
   	 			'<img src="/assets/pages/img/index/star-padding.png">';
		 }else if(evaluate>=4){
  			ret += '<img src="/assets/pages/img/index/star-padding.png">' +
  				'<img src="/assets/pages/img/index/star-padding.png">' +
  		 		'<img src="/assets/pages/img/index/star-padding.png">' +  
  		 		'<img src="/assets/pages/img/index/star-padding.png">' +  
  		 		'<img src="/assets/pages/img/index/star.png">';
     	}else if(evaluate>=3){
         	ret += '<img src="/assets/pages/img/index/star-padding.png">' +
   		 		'<img src="/assets/pages/img/index/star-padding.png">' +
   		 		'<img src="/assets/pages/img/index/star-padding.png">' +
   		 		'<img src="/assets/pages/img/index/star.png">' +
   		 		'<img src="/assets/pages/img/index/star.png">';
    	}else if(evaluate>=2){
         	ret += '<img src="/assets/pages/img/index/star-padding.png">' +
   	 			'<img src="/assets/pages/img/index/star-padding.png">' +
   	 			'<img src="/assets/pages/img/index/star.png">' +
   	 			'<img src="/assets/pages/img/index/star.png">' +
   	 			'<img src="/assets/pages/img/index/star.png">';
    	}else if(evaluate>0){
         	ret += '<img src="/assets/pages/img/index/star-padding.png">' +
          		'<img src="/assets/pages/img/index/star.png">' +
          		'<img src="/assets/pages/img/index/star.png">' +
          		'<img src="/assets/pages/img/index/star.png">' +
          		'<img src="/assets/pages/img/index/star.png">';
    	}else if(evaluate==0){
         	ret += '<img src="/assets/pages/img/index/star.png">' +
    			'<img src="/assets/pages/img/index/star.png">' +
    			'<img src="/assets/pages/img/index/star.png">' +
    			'<img src="/assets/pages/img/index/star.png">' +
    			'<img src="/assets/pages/img/index/star.png">';
      	}
      	return ret;
  	 },
  	 "fileFormat":function(file){
  		var format=/\.[^\.]+$/.exec(file); 
  		var result = "/assets/global/img/file-extension/unknow.png";
  		if(format==".ppt"||format==".pptx"||format==".PPT"||format==".PPTX"){
  			result = "/assets/global/img/file-extension/ppt.png";
  		}else if(format==".png"||format==".bmp"||format==".jpg"||format==".gif"||format==".jpeg"
  			||format==".PNG"||format==".BMP"||format==".JPG"||format==".GIF"||format==".JPEG"){
  			result = "/assets/global/img/file-extension/text.png";
  		}else if(format==".xls"||format==".xlsx"||format==".XLS"||format==".XLSX"){
  			result = "/assets/global/img/file-extension/xls.png";
  		}else if(format==".pdf"||format==".PDF"){
  			result = "/assets/global/img/file-extension/pdf.png";
  		}else if(format==".doc"||format==".docx"||format==".DOC"||format==".DOCX"){
  			result = "/assets/global/img/file-extension/doc.png";
  		}
  		return result;
  	 },
  	 "specialtyTypeFormat": function(stid){
         switch (true){
         case stid == '1':
         	return '学术';
         case stid == '2':
         	return '学习与工作';
         case stid == '3':
         	return '生活娱乐';
         default:
         	return '自定义';
         }
     },
});

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

var userProfile = function() {
	$("title").html("用户资料 | AnyoneHelps");
	setTimeout(function(){
		scrollTo(0,0);
	}, 500); 
	
	//头像 鼠标放在上面效果
	$('.bg').mouseover(function() { 
		if(base64Decode($("#userId").val()) == $("#myId").val()){
			$(".bg .upload-bg").removeClass('hide'); 
		}
	    
	}).mouseout(function(){
		if(base64Decode($("#userId").val()) == $("#myId").val()){
			$(".bg .upload-bg").addClass('hide'); 
		}
	});
	
	$(".message-private-down").click(function(){
   	 	$(".message-private-div").toggle();
		/*var obj = $(this).children();
		if(obj.hasClass("glyphicon-arrow-down")){
			obj.removeClass("glyphicon-arrow-down");
			obj.addClass("glyphicon-arrow-up");
			$(".message-private-div").removeClass("hide");
		}else{
			obj.addClass("glyphicon-arrow-down");
			obj.removeClass("glyphicon-arrow-up");
			$(".message-private-div").addClass("hide");
		}*/
    })
    
    /*发私信*/
	$('.message-private .submit-btn').click(function(){
		var obj = $(this);
		var content = $('.message-private .message-content').val();
		
		var friendId = base64Decode($(this).data("friendid"));
		$.ajax({
			type:"post",
			url:"/message/add",
			data:$.param({
				content:content,
				friendId:friendId
			},true),
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					$.successMsg("发送成功");
					$('.message-private .message-content').val("");
					obj.prev().removeClass("hide");
				} else if ("607" == code) {
					$.errorMsg("您还没登录，或者登录已失效,请登录后再发私信！");
					window.location.href = "/login.jsp";
				} else {
					$.errorMsg(response.message);
				}
				return false;
			}
		});
	});
	 $('.follow').click(function(){
		 var elem = this;
    	 var follow = $(this).data("follow");
    	 var userId = base64Decode($("#userId").val());
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
    	 				$('.follow>span').html("关注");
    	 				$('.follow>i').addClass("glyphicon-plus");
    	 				$('.follow>i').removeClass("glyphicon-remove");
    	 				
    	 				$(elem).data("follow","0");
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
    	 				$('.follow>span').html("取消关注");
    	 				$('.follow>i').removeClass("glyphicon-plus");
    	 				$('.follow>i').addClass("glyphicon-remove");
    	 				$(elem).data("follow","1");
    	 			} else if ("607" == code) {
    	 				$.errorMsg("您尚未登录或登录已失效！请登录后再关注！");
    	 				logout();
    	 			} else {
    	 				$.errorMsg(response.message);
    	 			}

    	 		},
    	 	});
    	 }
     })
     
     var evaluateFormat = function(evaluateAccepted,evaluateAcceptedCount,evaluatePublish,evaluatePublishCount){
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
     }
	 
	 function deleteWorks(){
			var obj = $(this).parent();
			var id = $(this).data("id");
			
			var d = dialog({
			    title: '提示',
			    width:'400px',
			    skin: 'delete-tip-dlg',
			    content: '您确定在删除该项吗?',
			    okValue: '确定',
			    ok: function () {
			        this.title('提交中…');
			        $.ajax({
		        		type:"post",
		        		url:"/works/delete",
		        		data:$.param({
		    				"id":id
		    			},true),
		        		success:function(response) {
		        			var code = response.code;
		        			if (code == "200") {
		        				$.successMsg("删除成功");
		        				obj.remove();
		        				if($('.works').children().length==0){
		        					$('.works').html('<div class="work-item">暂无上传作品 ,现在<a href="/dashboard/Account/setting.jsp#works">去上传</a></div>');
		        				}
		        				
		        			} else if ("607" == code) {
		        				$.errorMsg("您尚未登录或登录已失效！");
		        				logout();
		        			} else {
		        				// 失败
		        				$.errorMsg(response.message);
		        			}
		        		}
		        	});
			    },
			    cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
			return false;
	 };
	 function loadUserInfo(){
		var userId = base64Decode($("#userId").val());
     	// 获取用户数据
     	$.ajax({
     		type:"get",
     		url:"/user/open_info",
     		data : {
				id : userId
			},
     		success:function(response) {
     			var code = response.code;
     			if (code == "200") {
     				var data = response.data;
     				console.log(response.data);
     				$(".finish-sum-amount span").html(balanceFormat(data.countAmount));
     				$(".finish-count span").html(data.finishCount);
     				
     				if(data.user!=null){
     					$(".level-info .level span").html("Lv."+calculateGrade(data.user.grade));
     					$(".pic-info .left .user-pic").attr("src",data.user.picUrl);
     					$(".bg").css("background","url("+data.user.coverUrl+")");  //haokun added 2017/02/24 增加封面头像
     					$(".pic-info .right .username span").html(data.user.nickName);
     					$(".pic-info .right .brief span").html(data.user.brief);
     					$(".pic-info .right .brief").attr("title",data.user.brief);
         				$(".base-info .occupation").html(data.user.occupation);
         				var sex = "保密";
         				if(data.user.sex=="1"){
         					sex = "男";
         				}else if(data.user.sex=="2"){
         					sex = "女";
         				}else if(data.user.sex=="3"){
         					sex = "其他";
         				}
         				$(".base-info .sex").html(sex);
         				if(data.user.pro>0){
         					$(".pic-info .right .pro").removeClass("hide");
         				}
         				var keywords = "";
         				if (data.user.su != null) {
         		     		$.each(data.user.su, function(index) {
         		     			if(this.type==0){
         		     				if(this.specialty!=null){
         		     					keywords += this.specialty.name +' ';
         		         			} 
         		     			}else if(this.type==1){
         		     				keywords += this.name +' ';
         		     			}
         		     			
         		     		});
         		     	} 
         				
         				$("title").html(data.user.nickName+"| "+$("title").html());
         				$("meta[name='keywords']").attr("content",keywords);
         				$("meta[name='description']").attr("content",data.user.brief);
         				
         				if(data.user.degree=="0"){
         					$(".base-info .degree").html("保密");
         				}else if(data.user.degree=="1"){
         					$(".base-info .degree").html("高中毕业或其他同等学历");
         				}else if(data.user.degree=="2"){
         					$(".base-info .degree").html("大专学历");
         				}else if(data.user.degree=="3"){
         					$(".base-info .degree").html("大学本科学历");
         				}else if(data.user.degree=="4"){
         					$(".base-info .degree").html("硕士研究生学历");
         				}else if(data.user.degree=="5"){
         					$(".base-info .degree").html("博士学位");
         				}else{
         					$(".base-info .degree").html("未知");
         				}
         				
         				if(data.user.country=="us"){
         					$(".base-info .country").html("美国");
         				}else if(data.user.country=="cn"){
         					$(".base-info .country").html("中国");
         				}else if(data.user.country=="au"){
         					$(".base-info .country").html("澳洲");
         				}else if(data.user.country=="ca"){
         					$(".base-info .country").html("加拿大");
         				}else if(data.user.country=="uk"){
         					$(".base-info .country").html("英国");
         				}else if(data.user.country=="other"){
         					$(".base-info .country").html("其他");
         				}else{
         					$(".base-info .country").html("保密");
         				}
         				
         				if(data.user.id==$("#myId").val()){
         					$(".follow-and-message-private").addClass("hide");
         				}
         				
         				if(data.user.pro=="1"){
         					$(".user-pro").removeClass("hide");
         					$("[data-toggle='tooltip']").tooltip();
         				}
         				$(".base-info .grade").html(evaluateFormat(data.user.evaluate, data.user.evaluateCount, data.user.evaluatePublish, data.user.evaluatePublishCount));
         				$(".base-info .grade").data("userid", data.user.id);
         				
         				$(".base-info .grade").addClass("show-evaluate");
         				$(".base-info .grade").data("userid",data.user.id);
         				$(".base-info .grade").data("evaluatecount",data.user.evaluateCount);
         				$(".base-info .grade").data("evaluatepublishcount",data.user.evaluatePublishCount);
         				$(".base-info .grade").data("honest",data.user.honest);
         				$(".base-info .grade").data("quality",data.user.quality);
         				$(".base-info .grade").data("punctual",data.user.punctual);
         				$(".base-info .grade").data("honestpublish",data.user.honestPublish);
         				$(".base-info .grade").data("align","bottom left");
         				
         				if(data.user.id==$("#myId").val()){
         					$(".follow").addClass("hide");
         				}
         				$(".follow").data("follow",data.user.follow);
         				if(data.user.follow=="1"){
         					$(".follow>span").html("取消关注");
         					$('.follow>i').removeClass("glyphicon-plus");
        	 				$('.follow>i').addClass("glyphicon-remove");
        	 				
         				}
         				
         				showSpecialty(data.user.su);
         				if(data.user.works!=null){
         					$('.works').html($('#works-items-tmpl').render({dataList: data.user.works,userId: data.user.id}));
         					$('.work-item .delete').unbind("click"); //移除click
            				$('.work-item .delete').click(deleteWorks);
         				}
         				if(data.user.education!=null){
         					$('#education-list').html($('#education-items-tmpl').render({dataList: data.user.education,userId: data.user.id}));
         				}
         				if(data.user.we!=null){
         					$('#work-experience-list').html($('#work-experience-items-tmpl').render({dataList: data.user.we,userId: data.user.id}));
            			}
         				
         				
         				//console.log(data.user.pubDemand);
         				//$(".task-content").html($("#task-item-tmpl").render({dataList: data.user.finishRD,userId:data.user.id}));
         				//$(".task-pub-content").html($("#task-pub-item-tmpl").render({dataList: data.user.pubDemand,userId:data.user.id}));
         				
         			}
     				
     				
     				
     			}  else {
     				// 失败
     				alert("获取人物资料信息失败，原因是:" + response.message);
     			}
     		}
     	});
     }	
	 
	 //已发布任务
	 function loadPubDemand(page){
		var userId = base64Decode($("#userId").val());
	     $.ajax({
	     	type:"get",
	     	url:"/demand/pub_search",
	     	data : {
				id : userId,
				pageIndex:page
			},
	   		success:function(response) {
	   			var code = response.code;
	   			if (code == "200") {
	   				if (response.data != null) {
	   					showPubDemandList(response.data.datas, userId);
	   					showPubDemandLink(response.data);
					} else {
						showPubDemand(null, userId);
						showPubDemandLink(null);
					}
	     			//$(".task-content").html($("#task-item-tmpl").render({dataList: data.user.finishRD,userId:data.user.id}));
	     			//$(".task-pub-content").html($("#task-pub-item-tmpl").render({dataList: data.user.pubDemand,userId:data.user.id}));
	     		}  else {
	     			// 失败
	     			$.errorMsg(response.message);
	     		}
	     	}
	     });
	 }	
	 
	 var showPubDemandList = function(list,userId) {
		console.log(list);
		$(".task-pub-content").html($("#task-pub-item-tmpl").render({dataList: list,userId:userId}));
	 }
	 
	 function showPubDemandLink(pageSplit) {
		if (pageSplit != null) {
	    	var rowCount = pageSplit.rowCount;
	    	var pageSize = pageSplit.pageSize;
	    	var pageNow = pageSplit.pageNow;
	    	var pageCount = pageSplit.pageCount;
	    	$("#pageSplit-task-pub").parent().html('<ul class="pagination" id="pageSplit-task-pub"></ul>');
	    	$("#pageSplit-task-pub").twbsPagination({
	    		totalPages: pageCount,
	            startPage: pageNow,  
	            first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
	            //href: '?a=&page={{number}}&c=d' ,
	            onPageClick: function (event, page) {
	            	if(page!=pageNow){
	                	loadPubDemand(page);
	               	}
	          	}
	      	});
	   	}
	 }
	 
	 
	 //已解决任务
	 function loadAccDemand(page){
		var userId = base64Decode($("#userId").val());
	     $.ajax({
	     	type:"get",
	     	url:"/demand/acc_search",
	     	data : {
				id : userId,
				pageIndex:page
			},
	   		success:function(response) {
	   			var code = response.code;
	   			if (code == "200") {
	   				if (response.data != null) {
	   					showAccDemandList(response.data.datas, userId);
	   					showAccDemandLink(response.data);
					} else {
						showAccDemandList(null, userId);
						showAccDemandLink(null);
					}
	     		}  else {
	     			// 失败
	     			$.errorMsg(response.message);
	     		}
	     	}
	     });
	 }	
	 
	 var showAccDemandList = function(list,userId) {
		console.log(list);
		$(".task-content").html($("#task-item-tmpl").render({dataList: list,userId:userId}));
	 }
	 
	 function showAccDemandLink(pageSplit) {
		if (pageSplit != null) {
	    	var rowCount = pageSplit.rowCount;
	    	var pageSize = pageSplit.pageSize;
	    	var pageNow = pageSplit.pageNow;
	    	var pageCount = pageSplit.pageCount;
	    	$("#pageSplit-task").parent().html('<ul class="pagination" id="pageSplit-task"></ul>');
	    	$("#pageSplit-task").twbsPagination({
	    		totalPages: pageCount,
	            startPage: pageNow,  
	            first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
	            //href: '?a=&page={{number}}&c=d' ,
	            onPageClick: function (event, page) {
	            	if(page!=pageNow){
	                	loadAccDemand(page);
	               	}
	          	}
	      	});
	   	}
	}
	 //显示自定义技能
	 /*function showSpecialtyCustom(list) {
	     	var str = '';
	     	var i = 0;
	     	if (list != null) {
	     		$.each(list, function(index) {
	     			var labelClass = "label-primary";
	     			if(this.state == 0){
	     				labelClass = "label-info";
	     			}else if(this.state == 1){
	     				labelClass = "label-warning";
	     			}else if(this.state == 2){
	     				labelClass = "label-success";
	     			}
	     			if(this.specialty!=null){
	     				str += '<span class="'+labelClass+'"> '+this.specialty.name +' </span>';
	     			} 
	     			
	     			
	     		});
	     	} 
	     	if(str==''){
	     		str += '<span class="label-info"> 暂无技能...  </span>';
	     	}
	     	$(".user-specialty").html(str); 
	 }*/
	 
	 var showRemark = function(){
	    var remark = $(this).data("remark");
	    dialog({
	    	align:'bottom right',
			width:'400px',
			quickClose : true,
			skin: 'special-remark-dlg',
			content: remark
		}).show(this);
	 }
	 
	 //显示用户拥有技能
     function showSpecialty(list) {
    	 
    	 if(list!=null){
    		 var isShow = false;
    		 $.each(list, function(index) {
    			 if(this.state==2){
    				 isShow = true;
    				 return false;
    			 }
    		 })
    		 if(isShow){
    			 $(".special-list").removeClass("hide");
    			 $(".special-list table tbody").html($("#special-item-tmpl").render({dataList: list}));
    			 $(".special-list table tbody .show-remark").click(showRemark);
    		 }
    		 
    		 //所有技能
    		 $(".specialty-list").html($("#special-all-item-tmpl").render({dataList: list}));
    	 }
    		
    	
     }
    
     
 	
 	var userComment = function(){
 		
 		var loadReply = function(){
 			$(".reply").unbind("click");
 			$(".reply").bind("click",function(){
 				$(".reply-content").html("");
 				$(this).next().html($('#reply-tmpl').render());
 				loadReplyBtn();
 			})
 		} 
 		
 		var loadReplyBtn = function(){
 			$('.user-comment-reply-form').validate({
 	            submitHandler: function(form) {
 	            	var content = $(form).find("textarea").val();
 	     			var userId = $(form).parent().data("userid");
 	     			var parentId = $(form).parent().data("parentid");
 	     			$.ajax({
 	     				type:"post",
 	     				url:"/user_comment/add",
 	     				data:$.param({
 	     					content:content,
 	     					userId:userId,
 	     					parentId : parentId
 	     				},true),
 	     				success:function(response){
 	     					var code = response.code;
 	     					if ("200" == code) {
 	     						$.successMsg("发表留言成功");
 	     						$(form).parent().parent().parent().find(".reply-list").append($('#user-comment-reply-tmpl').render(response.data));
 	     						$(form).remove();
 	     						loadReply();
 	     					} else if ("607" == code) {
 	     						$.errorMsg("您还没登录，或者登录已失效,请登录后再发表您的留言！");
 	     						$(".user-login").removeClass("hide");
 	     						$(".mbblack").removeClass("hide");
 	     					} else {
 	     						$.errorMsg(response.message);
 	     					}
 	     					return false;
 	     				}
 	     			});
 	            	
 	            }
 	        });

 	        $('.user-comment-reply-form textarea').keypress(function(e) {
 	            if (e.which == 13) {
 	                if ($('.user-comment-reply-form').validate().form()) {
 	                    $('.user-comment-reply-form').submit();       //form validation success, call ajax form submit
 	                }
 	                return false;
 	            }
 	        });
 		} 
 		
 		function showUserCommentlist(list) {
 	 		if(list!=null){
 	 			console.log(list);
 	     		$('#user-comment-list').html($('#user-comment-tmpl').render({dataList: list}));
 	     		loadReply();
 	     	}
 	 	}

 	 	function showUserCommentlink(pageSplit) {
 	     
 	 		if (pageSplit != null) {
 	     		var rowCount = pageSplit.rowCount;
 	     		var pageSize = pageSplit.pageSize;
 	     		var pageNow = pageSplit.pageNow;
 	     		var pageCount = pageSplit.pageCount;
 	     		$("#pageSplit-user-comment").twbsPagination({
 	                 totalPages: pageCount,
 	                first: g_first,
 	               	prev: g_prev,
 	              	next: g_next,
 	               	last: g_last,
 	                 //href: '?a=&page={{number}}&c=d' ,
 	                 onPageClick: function (event, page) {
 	                	var userId = base64Decode($("#userId").val());
 	         		    loadUserComment(userId, pageSize, page);
 	                 }
 	             });
 	     	}
 	     }
 		
 		$('.user-comment-form').validate({
            submitHandler: function(form) {
            	var content = $(form).find("textarea").val();
     			var userId = base64Decode($(form).data("userid"));
     			$.ajax({
     				type:"post",
     				url:"/user_comment/add",
     				data:$.param({
     					content:content,
     					userId:userId
     				},true),
     				success:function(response){
     					var code = response.code;
     					if ("200" == code) {
     						$.successMsg("发表留言成功");
     						$(form).find("textarea").val("");
     						/*$('#user-comment-list').prepend($('#user-comment-item-tmpl').render(response.data));
     						loadReply();*/
     						loadUserComment(userId,10,1);
     						//setTimeout(function(){},3*1000);
     					} else if ("607" == code) {
     						$.errorMsg("您还没登录，或者登录已失效,请登录后再发表您的留言！");
     						$(".user-login").removeClass("hide");
     						$(".mbblack").removeClass("hide");
     					} else {
     						$.errorMsg(response.message);
     					}
     					return false;
     				}
     			});
            	
            }
        });

        $('.user-comment-form textarea').keypress(function(e) {
            if (e.which == 13) {
                if ($('.user-comment-form').validate().form()) {
                    $('.user-comment-form').submit();       //form validation success, call ajax form submit
                }
                return false;
            }
        });
 		
 		var loadUserComment = function(userId, size, index) {
  			$.ajax({
  				type:"get",
  				url:"/user_comment/search",
  				data:{
  					id : userId,
  					pageIndex : index,
  					pageSize : size
  				},
  				success:function(response){
  					var code = response.code;
  					if ("200" == code) {
  						if (response.data != null) {
  							showUserCommentlist(response.data.datas);
  							showUserCommentlink(response.data);
  						} else {
  							showUserCommentlist(null);
  							showUserCommentlink(null);
  						}
  					} else if ("607" == code) {
  						//跳至登录处
  						//logout();
  					} 
  					return false;
  				}
  			});
  		}
 		var userId = base64Decode($("#userId").val());
     	loadUserComment(userId, 10, 1)
    }
 	

	//haokun added 2017/02/24 头像上传
	var handleUserCoverPic = function() {
		//alert("start cover upload");
		$(':file[name="user_cover_img"]').fileupload({
			url : '/user/cover_upload',
			dataType : 'json',
			acceptFileTypes : /(\.|\/)(gif|jpe?g|png|bmp)$/i,
			maxNumberOfFiles : 1,
			maxFileSize : 5 * 1024 * 1024,
			messages : {
				maxFileSize : '文件大小不能超过20M!',
				acceptFileTypes : '文件类型不支持！'
			},
			processfail : function(e, data) {
				var currentFile = data.files[data.index];
				if (data.files.error && currentFile.error) {
					console.log(currentFile.error);
					$.errorMsg(currentFile.error);
				}
			},
			done : function(e, data) {
				var code = data.result.code;
				if (code == "200") {
					$.successMsg("上传成功");
					//alert("返回数据= " + data);
					location.reload(); //刷新页面
				} else {
					$.errorMsg(data.result.message);
				}
			},

			progressall : function(e, data) {
				/*
				var progress = parseInt(data.loaded / data.total * 100, 10);
				if(progress=100){
					$('#works-progress').addClass("hide");
				}else{
					$('#works-progress').removeClass("hide");
				}
				$('#progress .bar').css(
				    'width',
				    progress + '%'
				);
				 */
			},
		});
	}
	
    return {
        //main function
        init: function() {
        	loadUserInfo();
        	userComment();
        	loadPubDemand(1);
        	loadAccDemand(2);
        	handleUserCoverPic();//haokun added 2017/2/24
        }
    };

}();
jQuery(document).ready(function() {
    userProfile.init();
});