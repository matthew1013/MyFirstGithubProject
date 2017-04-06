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
    "tagFormat": function(tag){
    	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    	var count = 0;  //读取多少个字了
    	var strTag = "";
    	  
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
    				
    			if(count<130){
    				strTag += "<li><a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'><div class='tag-item'>#"+tagArray[k]+"</div></a></li>";
    			}else{
    				if(bExist){ //含有中文
    					if(count-(2*tagArray[k].length+2)==0){  //第一个标签就超出范围
    						strTag += "<li><a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'><div class='tag-item'>#"+tagArray[k].substr(0,113)+"...</div></a></li>";
        				}
        		     }else {  //没有中文
        		     	if(count-(tagArray[k].length+2)==0){    //第一个标签就超出范围
        		     		strTag += "<li><a name='tag-a' href='javasrcipt:void(0);' data-tag='"+tagArray[k]+"'><div class='tag-item'>#"+tagArray[k].substr(0,126)+"...</div></a></li>";
        				}
        		     }
    					
    			}
    		}
    	}
    	if(tagArray.length<2){
    		strTag += "<li><div class='tag-item'>无标签</div></li>";
    	}
    	strTag += "";
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
    "substrOfMessageContent": function(content){
    	if(content.length>60) return content.substr(0,60)+ '...';
    	else  return content;
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
  	 "taskEvaluateFormat": function(evaluate, punctual, quality){
 		 var e = (evaluate*0.25+punctual*0.25+quality*0.5).toFixed(1);
 		 if(e=="NaN"){
 			 e = 0;
 		 }
 		 ret = "<a title='"+e+" out of 5 stars. 点击打开评级说明' href='javascript:void(0);'>"
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
 		ret += "</a>"
 		return ret;
  	},
  	"taskPubEvaluateFormat": function(evaluate){
		 var e = evaluate;
		 if(e=="NaN"){
			 e = 0;
		 }
		 ret = "<a title='"+e+" out of 5 stars. 点击打开评级说明' href='javascript:void(0);'>"
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
		ret += "</a>"
		return ret;
 	},
 	"evaluateStarFormat": function(evaluate){
		 var e = evaluate;
		 if(e=="NaN"){
			 e = 0;
		 }
		 ret = ""
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
		return ret;
 	},
 	"creditPayAmountFormat":function(amount){     //haokun added 2017/03/03
	   	 return ((parseFloat(amount).toFixed(2)/1+0.3)/0.971).toFixed(2);    //haokun added 2017/03/09 这里必须要/1，否则结果出现NaN
	}
});

$.views.helpers({
	"isShowBtn": function(receiveDemand){
		var result = false;
		var userId = $(":hidden[name='userId']").val()
		if(userId==null||receiveDemand==null){
			result = false;
		}else{
			$(receiveDemand).each(function(index,e){
				if(e.userId == userId){
					result = true;
				}
			})
		}
		return result;
	}
});

var taskDetail = function() {
	$("title").html("任务详情 | AnyoneHelps");
	/*右侧标签点击事件*/
	$('.type-second li').click(function() {
		return false;
	})
	
	var loadMap = function(latitude,longitude,locationName) {
		$('#map').locationpicker({
		    location: {latitude: latitude, longitude: longitude},
		    locationName: locationName,
		    radius: 0,
		    zoom: 9,
		    inputBinding: {
		    	latitudeInput: $('#map-latitude'),
		    	longitudeInput: $('#map-longitude'),
		    	locationNameInput: $('#map-region')        
		    },
		    enableAutocomplete: true,
		    onchanged: function (currentLocation, radius, isMarkerDropped) {
		        var addressComponents = $(this).locationpicker('map').location.addressComponents;
		        if(addressComponents){
		        	$('#map-district').val(addressComponents.district);
		        	$('#map-province').val(addressComponents.stateOrProvince);
		        	$('#map-country').val(addressComponents.country);
					console.log(addressComponents);
		        }
		    },
		    oninitialized: function(component) {
		        var addressComponents = $(component).locationpicker('map').location.addressComponents;
		        if(addressComponents){
		        	$('#map-district').val(addressComponents.district);
		        	$('#map-province').val(addressComponents.stateOrProvince);
		        	$('#map-country').val(addressComponents.country);
		        	console.log(addressComponents);
		        }
		        
		    }
		});
    }
    var loadIPInfo = function(){
    	var lat = 34.0522342;
    	var lon = -118.2436849;
    	var regionName = "102 W 1st St, Los Angeles, CA 90012, USA";
    	try {
        	$.ajaxSetup({
                error: function (x, e) {
                	loadMap(lat,lon,regionName);
                }
            });
        	var ip = $(":hidden[name='ip']").val();
            $.getJSON("//pro.ip-api.com/json/"+ip+"?key=evuD3jbO1gaBBR1&callback=?", function(data) {
        		if(data!=null){
        			if(data.lat !=null ){
        				lat = data.lat;
        			}
        			if(data.lon !=null ){
        				lon = data.lon;
        			}
        			if(data.regionName !=null ){
        				regionName = data.regionName;
        			}
        			loadMap(lat,lon,regionName);
        		}else{
        			loadMap(lat,lon,regionName);
        		}
            });
        }
        catch (ex) {
        	loadMap(lat,lon,regionName);
        }
    	
    };
    
    var demandMessageAdd = function(demandId,receiverId,content,parentId) {
 		$.ajax({
			type:"post",
			url:"/demand/demand_msg_add",
			data:$.param({
				demandId:demandId,
				receiverId:receiverId,
				content:encodeURI(content),
				parentId:parentId
			},true),
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					window.location.reload();
				} else if ("607" == code) {
					$.errorMsg("您还没登录，或者登录已失效,请登录后再留言！");
				} else {
					$.errorMsg(response.message);
				}
				return false;
			}
		});
	}
    
    var demandFollowAdd = function(demandId) {
 		$.ajax({
			type:"post",
			url:"/demand_follow/add",
			data:$.param({
				id:demandId
			},true),
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					$(".follow").addClass("hide");
					$(".follow-cancel").removeClass("hide");
				} else if ("607" == code) {
					$.errorMsg("您还没登录，或者登录已失效,请登录后再留言！");
				} else {
					$.errorMsg(response.message);
				}
				return false;
			}
		});
	}
	
	var demandFollowDelete = function(id) {
		var ids = [];
		ids.push(id);
		
		$.ajax({
			type:"post",
			url:"/demand_follow/delete",
			data:$.param({
				"ids":ids
			},true),
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					//loadFriend("",10,pn);
					$(".follow").removeClass("hide");
					$(".follow-cancel").addClass("hide");
				} else if ("607" == code) {
					//跳至登录处
					$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
				} else {
					$.errorMsg(response.message);
				}
				return false;
			}
		});
	}
    
	var handleDemandFollow = function() {
        $('.follow').click( function() {
            var id = base64Decode($(":hidden[name='taskId']").val());
            demandFollowAdd(id);
        });
        $('.follow-cancel').click( function() {
            var id = base64Decode($(":hidden[name='taskId']").val());
            demandFollowDelete(id);
        });
    }
	
    var handleReceiveDemand = function() {
        $('.receive-demand form').validate({
        
            submitHandler: function(form) {
            	var remark = $("textarea[name='receive_remark']").val();
            	var finishDay = $("input[name='finishDay']").val();
     			var id = base64Decode($(":hidden[name='taskId']").val());
     			var amount = $("input[name='amount']").val();
			    if(parseFloat(amount)<10){
			    	$.errorMsg("报价不能低于10美元！");
			    	return false;
			    }
			    
     			var locationOpen = 0;
     			var locationName = "";
     			var locationCountry = "";
     			var locationProvince = "";
     			var locationDistrict = "";
     			var latitude = 0;
     			var longitude = 0;
     			if($(":checkbox[name='location-open']").is(":checked")){
     				locationOpen = 1;
     				locationName = $("#map-region").val();
     				locationCountry = $("#map-country").val();
     				locationProvince = $("#map-province").val();
     				locationDistrict = $("#map-district").val();
     				latitude = $("#map-latitude").val();
     				longitude = $("#map-latitude").val();
     			}else{
     				locationOpen = 0;
     			}
			    
     			
    	 		$.ajax({
    				type:"post",
    				url:"/demand/receive_add",
    				data:$.param({
    					demandId:id,
    					readme:remark,
    					finishDay:finishDay,
    					amount:amount,
    					locationOpen:locationOpen,
    					locationName:locationName,
    					locationCountry:locationCountry,
    					locationProvince:locationProvince,
    					locationDistrict:locationDistrict,
    					latitude:latitude,
    					longitude:longitude
    				},true),
    				success:function(response){
    					var code = response.code;
    					if ("200" == code) {
    						//$.errorMsg("接任务成功");
    						var d = dialog({
    						    width:'480px',
    						    skin: 'tip-dlg1',     //haokun modified   2017/02/16
    						    onshow : function() {
    						    	this.content($('#tip-dlg1').render());     //haokun modified  2017/02/16
    						    	$(".tip-dlg1 .tip-dlg-content1 .operate button").click(function(){    //haokun modified  2017/02/16
    						    		d.close();
    						    		window.location.href = "/dashboard/Task/acceptedList.jsp?view=tab_1_1";
    						    	})
    						    	
    						    }
    						});
    						d.showModal();
    					} else if ("607" == code) {
    						$.errorMsg("您还没登录，或者登录已失效,请登录后再竞标");
    					} else {
    						$.errorMsg(response.message);
    					}
    					return false;
    				}
    			});
            }
        });

        $('.receive-demand form input').keypress(function(e) {
            if (e.which == 13) {
                /*if ($('#receive-demand').validate().form()) {
                    $('#receive-demand').submit();
                }*/
                return false;
            }
        });
    }
    
    var handleDemandMessage = function() {
        $('#submit-task-message').validate({
            submitHandler: function(form) {
            	var content = $("textarea[name='message']").val();
            	var id = base64Decode($(":hidden[name='taskId']").val());
            	var receiverId = $(form).data("userid");
            	var parentId = "-1";
            	demandMessageAdd(id,receiverId,content,parentId);
            }
        });

        $('#submit-task-message input').keypress(function(e) {
            if (e.which == 13) {
                if ($('#submit-task-message').validate().form()) {
                    $('#submit-task-message').submit();
                }
                return false;
            }
        });
    }
    /*私信*/
    var handleMessage = function() {
        $('#submit-message').validate({
            submitHandler: function(form) {
            	var content = encodeURI($("textarea[name='message-private']").val());
            	var friendId = $('.message-submit').data("userid");
            	if(content==null||content==""){
    				$.errorMsg("发送内容不能为空！");
    				return false;
    			}
    			if(friendId==null||friendId==""){
    				$.errorMsg("没有发送对象，请刷新页面再操作！");
    				return false;
    			}
    			
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
    						$("textarea[name='message-private']").val("");
    					} else if ("607" == code) {
    						$.errorMsg("您还没登录，或者登录已失效！");
    						window.location.href = "/login.jsp";
    					} else {
    						$.errorMsg(response.message);
    					}
    				}
    			});
    			return false;
            }
        });

        $('#submit-message input').keypress(function(e) {
            if (e.which == 13) {
                if ($('#submit-message').validate().form()) {
                    $('#submit-message').submit();
                }
                return false;
            }
        });
    }
    
    var taskLoad = function() {
    	var loadDemand = function(id) {
    		$.ajax({
    			type:"get",
    			url:"/demand/get_one_by_writer",
    			data:{
    				id:id
    			},
    			success:function(response){
    				var code = response.code;
    				if ("200" == code) {
    					if(response.data!=null) {
    						var task = response.data;
    						$("title").html(task.title+"| "+$("title").html());
    						$(".task").html($('#task-content-tmpl').render({task:task}));
    						
    						$(".task-right").html($('#task-content-right-tmpl').render({task:task}));
    						$(".task-state").html($('#task-state-tmpl').render({state:task.state}));
    						
    						$('input[name="location-open"]').iCheck({
    							checkboxClass: 'icheckbox_minimal-blue',
    						    radioClass: 'iradio_minimal-blue',
    						    increaseArea: '20%' // optional
    						});
    						
    						$(".show-task-receive").click(function(){
    							$.ajax({
    				        		type:"get",
    				        		url:"/user/get_self",
    				        		success:function(response) {
    				        			var code = response.code;
    				        			if (code == "200") {
    				        				$(".receive-demand").removeClass("hide");
    	        							$(this).parent().addClass("hide");
    				        			} else if ("607" == code) {
    				        				$.errorMsg("您尚未登录或登录已失效！");
    				        			} 
    				        		}
    				        	});
    							var container = $('body'),
    						    scrollTo = $(this);
    							// Or you can animate the scrolling:
    							container.animate({
    							    scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop() - $(window).scrollTop() - 170
    							});
    							//alert($(window).scrollTop()+$(this).height())
    							//alert(document.body.clientHeight)
    							//alert(document.body.scrollHeight)
    						})
    						$(".close-task-receive").click(function(){
    							$(".show-task-receive").parent().removeClass("hide");
    							$(this).parent().parent().parent().parent().addClass("hide");
    						})
    						$('input[name="location-open"]').on('ifChecked', function(event){
    							$('#map-region').removeClass("hide");
    							$('#map').parent().removeClass("hide");
    							loadIPInfo();
    						});
    						$('input[name="location-open"]').on('ifUnchecked', function(event){
    							$('#map-region').addClass("hide");
    							$('#map').parent().addClass("hide");
    						});
    						
    						
    					    $(".receive-demand input[name='amount']").blur(function(){
    					    	var amount = parseFloat($(this).val());
    					    	if(amount<10){
    					    		$.errorMsg("报价不能低于10美元！");
    					    	}
    						})
    						
    						handleReceiveDemand();
    						handleDemandMessage();
    						handleMessage();
    						handleDemandFollow();
    						
    						
    						
    						$("a[name='message-reply']").click(function(){
    							$("#reply-content-div").remove();
        						$("#reply-submit-div").remove();
        						$(this).parent().next().after($('#reply-message-items-tmpl').render());
        						$("textarea[name='reply-content']").focus();
        						
        						var parentId = $(this).attr("href");
        						var receiverId = $(this).data("userid");
        						$("#reply-submit").click(function(){
        							var content = $("textarea[name='reply-content']").val();
        			            	var id = base64Decode($(":hidden[name='taskId']").val());
        			            	//还没检验参数
        			         
        			            	demandMessageAdd(id,receiverId,content,parentId);
        						});
    							return false;
    						});
    						
    						console.log(task);
    					
    						var view = $(":hidden[name='view']").val();
    						if(view=="tab_15_1"){
    							$("#tab_15_1").siblings().removeClass("active");
    							$("#tab_15_1").addClass("active");
    							$("#task-receive-a").parent().siblings().removeClass("active");
    							$("#task-receive-a").parent().removeClass("active");
    							$("#task-message-count").parent().addClass("active");
    							$("html,body").animate({scrollTop:$("#tab_15_1").offset().top-170},1000);
    							
    						}else if(view=="tab_15_2"){
    							$("html,body").animate({scrollTop:$("#tab_15_2").offset().top-170},1000);
    						}
    						$(".task-end").click(function(){
    							$.ajax({
    								url: '/demand/close',
    								type:'post',
    								dataType: 'json',
    								data:$.param({
    									"demandId" : id
    								}, true),
    								success: function(result){
    									if(result.code == 200){
    										$.successMsg("关闭任务成功");
    										window.location.reload();
    									}else{
    										$.errorMsg(result.message);
    									}
    								},
    								error: function(result){
    									$.errorMsg(result.message);
    								}
    							});
    						})
    						
    						$(".task-complete").click(function(){
    							$.ajax({
    								url: '/demand/finish',
    								type:'post',
    								dataType: 'json',
    								data:$.param({
    									"demandId" : id
    								}, true),
    								success: function(result){
    									if(result.code == 200){
    										$.successMsg("任务终止成功，请去任务中心支付任务报酬！");
    										window.location.reload();
    									}else{
    										$.errorMsg(result.message);
    									}
    								},
    								error: function(result){
    									$.errorMsg(result.message);
    								}
    							});
    						})
    						
    						$(".task-pay").click(function(){
    							$.ajax({
	    		    				url: "/demand/pay",
	    		    				type:'post',
	    		    				dataType: 'json',
	    		    				data:$.param({
	    		    					demandId : id,
	    		    					payPercent : 100,
				    		    		payReason : "",
				    		    		payReasonUrl1 : "",
				    		    		payReasonUrl2 : "",
				    		    		payReasonUrl3 : "",
				    		    		payReasonUrl4 : "",
				    		    		payReasonUrl5 : "",
	    		    				}, true),
	    		    				success: function(result){
	    		    					if(result.code == 200){
	    		    						$.successMsg("支付成功！");
	    		    						window.location.reload();
	    		    					}else{
	    		    						$.errorMsg(result.message);
	    		    					}
	    		    				},
	    		    				error: function(result){
	    		    					$.errorMsg(result.message);
	    		    				}
	    		    			});
    							
    							var keywords = "";
        						if(task.tag!=null){
        							var tagArray = task.tag.split("#");	
        					    	for(var k=0;k<tagArray.length;k++){		
        					    		if(tagArray[k]!=""&&tagArray[k]!=null){
        					    			keywords += tagArray[k]+" "
        					    		}
        					    	}
        						}
        						//$("title").html(task.title+"| "+$("title").html());
                 				$("meta[name='keywords']").attr("content",keywords);
                 				$("meta[name='description']").attr("content",task.content);
    						})
    					} 
    				} else if ("607" == code) {
    					//跳至登录处
    					//logout();
    				} 
    				return false;
    			}
    		});
    	}
    	
    	var loadRelvantDemand = function(id) {
    		$.ajax({
    			type:"get",
    			url:"/demand/relevant",
    			data:{
    				id:id
    			},
    			success:function(response){
    				var code = response.code;
    				if ("200" == code) {
    					if(response.data!=null) {
    						$(".task-relvant-list").html($('#task-relvant-item').render({dataList:response.data}));
    					} 
    				} else if ("607" == code) {
    					//跳至登录处
    					//logout();
    				} 
    				return false;
    			}
    		});
    	}
    	
    	var id = base64Decode($(":hidden[name='taskId']").val());
    	
    	loadRelvantDemand(id);
    	loadDemand(id);
    }
	
    return {
        //main function
        init: function() {
        	taskLoad();
        }
    };

}();
jQuery(document).ready(function() {
	taskDetail.init();
	$(window.document).on('click', '.show-task-evaluate', function(e){
		var evaluate = $(this).data("evaluate");
    	var punctual = $(this).data("punctual");
    	var quality = $(this).data("quality");
    	var d = dialog({
    		title: '任务成果具体评级',
		    width:'300px',
		    skin: 'task-evaluate-dlg',
		    quickClose : true,
		    onshow : function() {
				this.content($("#show-task-evaluate-tmpl").render({evaluate:evaluate, punctual:punctual, quality:quality}));
			}
		}).show(this);
    	return false;
	}).on('click', '.show-task-pub-evaluate', function(e){
		var evaluate = $(this).data("evaluate");
    	var d = dialog({
    		title: '接单人对发单人评级',
		    width:'300px',
		    skin: 'task-evaluate-dlg',
		    quickClose : true,
		    onshow : function() {
				this.content($("#show-task-pub-evaluate-tmpl").render({evaluate:evaluate}));
			}
		}).show(this);
    	return false;
	}).on('click', '.task-select', function(){
		var obj = $(this);
		var taskId = base64Decode($(":hidden[name='taskId']").val());
        var id = $(this).data("id");
		var amount = $(this).data("amount");
		//alert("amount"+amount);
		//alert("id="+id);
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/get_one",
		    		data:{
		    			id:taskId
		    		},
		    		success:function(response){
						//alert("success");
		    			var code = response.code;
		    			var payState = "1";
		    			if(response.data!=null){
							//alert("success not null");
		    				payState = response.data.payState;
	    				}
		    			if ("200" == code) {
							//alert("success 200");
		    		    		if(payState=="0"){
		    		    			var d = dialog({
		    		    				align:'top left',
		    		    				skin:'tip-dlg',
		    		    				/*width : 720,*/
		    		    				quickClose : true,
		    						    onshow : function() {
											//alert("tip dialog 1"); //comment
											//alert("amount="+amount);
											//alert("taskid="+taskId);
											//alert("rdId="+id);
		    						    	var contentObj = this.content($('#tip-dlg').render({taskId:taskId, rdId:id, amount:amount}));
		    						    	$(".tip-dlg .wclose").click(function(){
		    			    		    		d.close().remove();
		    			    		    		return false;
		    			    		    	})
											//alert("tip dialog 2"); //comment
		    			    		    	$(".tip-dlg .operate .balance").click(function(){
		    			    		    		$.ajax({
		    		    		    				url: '/demand/pay_balance',
		    		    		    				type:'post',
		    		    		    				dataType: 'json',
		    		    		    				data:$.param({
		    		    		    					"id" : id,
		    		    		    					"demandId" : taskId
		    		    		    				}, true),
		    		    		    				success: function(result){
		    		    		    					if(result.code == 200){
		    		    		    						$.successMsg("余额支付成功");
		    		    		    						d.close().remove();
		    		    		    						//dlg.close().remove();
		    		    		    						//$("[href='#tab_1_2']").parent().siblings().removeClass("active");
		    		    		    						//$("[href='#tab_1_2']").parent().click();
		    		    		    						//$("[href='#tab_1_2']").parent().addClass("active");
		    		    		    						//obj.parent().parent().find(".task-end").removeClass("hide");
		    		    		    						//obj.parent().parent().find(".task-edit").addClass("hide");
		    		    		    						//obj.parent().parent().find(".task-select").addClass("hide");
		    		    		    					}else{
		    		    		    						$.errorMsg(result.message);
		    		    		    					}
		    		    		    				},
		    		    		    				error: function(result){
		    		    		    					$.errorMsg(result.message);
		    		    		    				}
		    		    		    			});
		    			    		    	})
											//alert("tip dialog 3"); //comment
		    			    		    	$(".tip-dlg .operate .paypal").click(function(){
		    			    		    		$("#paypal-form").unbind("submit");
		    			    		    		$("#paypal-form").submit(function(e){
		    			    		    			var paypalDlg = dialog({
			    		    		    				align:'top left',
			    		    		    				skin:'paypal-pay-dlg',
			    		    		    				/*width : 720,*/
			    		    						    onshow : function() {
			    		    						    	this.content($('#paypal-pay-dlg').render());
			    		    						    	$(".paypal-pay-dlg .wclose").click(function(){
			    		    						    		paypalDlg.close().remove();
			    		    			    		    		return false;
			    		    			    		    	})
			    		    			    		    	$(".paypal-pay-dlg .operate .pay-succ").click(function(){
			    		    			    		    		$.ajax({
			    		    			    		        		type:"get",
			    		    			    		        		url:"/demand/get_one",
			    		    			    		        		data:$.param({
			    		    			    		        			id : taskId
			    		    			    		        		},true),
			    		    			    		        		success:function(response){
			    		    			    		        			var code = response.code;
			    		    			    		        			if ("200" == code) {
			    		    			    		        				if(response.data!=null){
			    		    			    				    				payState = response.data.payState;
			    		    			    				    				if(payState == "1"){
			    		    			    				    					paypalDlg.close().remove();
					    		    		    		    						d.close().remove();
					    		    		    		    						//dlg.close().remove();
					    		    		    		    						//$("[href='#tab_1_2']").parent().siblings().removeClass("active");
					    		    		    		    						//$("[href='#tab_1_2']").parent().click();
					    		    		    		    						//$("[href='#tab_1_2']").parent().addClass("active");
					    		    		    		    						//obj.parent().parent().find(".task-end").removeClass("hide");
					    		    		    		    						//obj.parent().parent().find(".task-edit").addClass("hide");
					    		    		    		    						//obj.parent().parent().find(".task-select").addClass("hide");
			    		    			    				    				}else{
			    		    			    				    					$.errorMsg("anyonehelps尚未收到你的付款，稍后收到后，会执行你的任务匹配，请稍等查看");
			    		    			    				    					paypalDlg.close().remove();
					    		    		    		    						d.close().remove();
					    		    		    		    						//dlg.close().remove();
			    		    			    				    				}
			    		    			    			    				}
			    		    			    		        			} else if ("607" == code) {
			    		    			    		        				alert("您尚未登录或登录已失效！");
			    		    			    		        				logout();
			    		    			    		        			} 
			    		    			    		        			return false;
			    		    			    		        		}
			    		    			    		        	});
			    		    			    		    	});
			    		    						    	$(".paypal-pay-dlg .operate .pay-fail").click(function(){
			    		    			    		    		alert("请联系客服！！");
			    		    			    		    	});

			    		    						    }
			    		    						});
			    			    		    		paypalDlg.showModal();
		    			    		    		});
		    			    		    	})
											//alert("tip dialog 4"); //comment
		    			    		    	$(".tip-dlg .operate .credit").click(function(){
		    			    		    		var creditDlg = dialog({
		    		    		    				align:'top left',
		    		    		    				skin:'credit-pay-dlg',
		    		    		    				/*width : 720,*/
		    		    		    				quickClose : true,
		    		    						    onshow : function() {
		    		    						    	var contentObj = this.content($('#credit-pay-dlg').render({amount:amount}));
		    		    						    	$(".credit-pay-dlg .wclose").click(function(){
		    		    						    		creditDlg.close().remove();
		    		    			    		    		return false;
		    		    			    		    	})
		    		    			    		    	$(".credit-pay-dlg .credit-pay-btn").click(function(){
		    		    			    		    		var name = $(".credit-pay-dlg :text[name='name']").val();
		    		    			    		    		var creditNo = $(".credit-pay-dlg :text[name='creditNo']").val();
		    		    			    		    		var creditSecurityCode = $(".credit-pay-dlg :text[name='creditSecurityCode']").val();
		    		    			    		    		var expireYear = $(".credit-pay-dlg select[name='expireYear']").val();
		    		    			    		    		var expireMonth = $(".credit-pay-dlg select[name='expireMonth']").val();
		    		    			    		    		var brand = $(".credit-pay-dlg select[name='brand']").val();
		    		    			    		    		//var payAmount = (parseFloat(amount).toFixed(2)/0.971+0.3).toFixed(2);
															var payAmount = ((parseFloat(amount).toFixed(2)/1+0.3)/0.971).toFixed(2);   //haokun added 2017/03/09 这里必须要/1，否则结果出现NaN
		    		    			    		    		$.ajax({
		    		    		    		    				url: '/demand/credit_balance',
		    		    		    		    				type:'post',
		    		    		    		    				dataType: 'json',
		    		    		    		    				data:$.param({
		    		    		    		    					"brand":brand,
		    		    			    		    				"name":name,
		    		    			    		    				"creditNo":creditNo,
		    		    			    		    				"currency":"usd",
		    		    			    		    				"securityCode":creditSecurityCode,
		    		    			    		    				"expireYear":expireYear,
		    		    			    		    				"expireMonth":expireMonth,
		    		    			    		    				"amount":payAmount,
		    		    			    		    				"id" : id,
		    		    		    		    					"demandId" : taskId
		    		    		    		    				}, true),
		    		    		    		    				success: function(result){
		    		    		    		    					if(result.code == 200){
		    		    		    		    						$.successMsg("信用卡支付成功");
		    		    		    		    						creditDlg.close().remove();
		    		    		    		    						d.close().remove();
		    		    		    		    						//dlg.close().remove();
		    		    		    		    						//$("[href='#tab_1_2']").parent().siblings().removeClass("active");
		    		    		    		    						//$("[href='#tab_1_2']").parent().click();
		    		    		    		    						//$("[href='#tab_1_2']").parent().addClass("active");
		    		    		    		    						//obj.parent().parent().find(".task-end").removeClass("hide");
		    		    		    		    						//obj.parent().parent().find(".task-edit").addClass("hide");
		    		    		    		    						//obj.parent().parent().find(".task-select").addClass("hide");
		    		    		    		    					}else{
		    		    		    		    						$.errorMsg(result.message);
		    		    		    		    					}
		    		    		    		    				},
		    		    		    		    				error: function(result){
		    		    		    		    					$.errorMsg(result.message);
		    		    		    		    				}
		    		    		    		    			});
		    		    			    		    		return false;
		    		    			    		    	})
		    		    			    		    	
		    		    						    }
		    		    						});
		    			    		    		creditDlg.showModal();
		    			    		    	})
											//alert("tip dialog 5"); //comment
		    						    }
		    						});
		    						d.showModal();
		    		    		}else{
									//alert("else id="+ id);
		    		    			$.ajax({
		    		    				url: '/demand/select',
		    		    				type:'post',
		    		    				dataType: 'json',
		    		    				data:$.param({
		    		    					"id" : id,
		    		    					"demandId" : taskId
		    		    				}, true),
		    		    				success: function(result){
		    		    					if(result.code == 200){
		    		    						$.successMsg("匹配成功,等待接单人开始任务…");
		    		    						//obj.parent().parent().find(".bid-user ul").html(str);
		    		    						//dlg.close().remove();
		    		    						//$("[href='#tab_1_2']").parent().siblings().removeClass("active");
		    		    						//$("[href='#tab_1_2']").parent().click();
		    		    						//$("[href='#tab_1_2']").parent().addClass("active");
		    		    						//obj.parent().parent().find(".task-end").removeClass("hide");
		    		    						//obj.parent().parent().find(".task-edit").addClass("hide");
		    		    						//obj.parent().parent().find(".task-select").addClass("hide");
		    		    					}else{ 
		    		    						$.errorMsg(result.message);
		    		    					}
		    		    				},
		    		    				error: function(result){
		    		    					$.errorMsg(result.message);
		    		    				}
		    		    			});
		    		    		}
		    		    		
		    		    	
		    				
		    			} else if ("607" == code) {
		    				alert("您尚未登录或登录已失效！");
		    				logout();
		    			} 
		    			return false;
		    		}
		    	});
				
			
		    		    		
	}).on('click', '.private-message-btn', function(e){   //haokun add this funtion 2017/02/27
		var userIdEncoded = $(this).data("userid");
        var userId = base64Decode(userIdEncoded);
		var userName = $(this).data("username")
    	var d = dialog({
    		title: '与'+userName+'私信中',
		    width:'300px',
		    skin: 'private-message-dlg',
		    quickClose : true,
		    onshow : function() {
				this.content($("#private-message-tmpl").render({userId:userId,userIdEncoded:userIdEncoded}));
			}
		}).show(this);
		//d.showModal();
		
    	return false;
	}).on('click','.private-message .submit-btn',function(e){  //haokun add this funtion 2017/02/27
		var obj = $(this);
		var content = $(".private-message .message-content").val();
		var friendId = $('.private-message .submit-btn').data("friendid")
		//alert("friendid="+friendId);
		if(content==null||content==""){
    	    $.errorMsg("发送内容不能为空！");
    	    return false;
        }
    	if(friendId==null||friendId==""){
    		$.errorMsg("没有发送对象，请刷新页面再操作！");
    		return false;
        }
		$.ajax({
			type: "post",
			url: "/message/add",
			data: $.param({
				content:content,
				friendId:friendId
			},true),
			success:function(response){
    			var code = response.code;
    			if ("200" == code) {
    				$.successMsg("发送成功");
    				$(".private-message .message-content").val("");  /*haokun 2017/03/02 发送成功后删除记录*/
					obj.prev().removeClass("hide");
    			} else if ("607" == code) {
    				$.errorMsg("您还没登录，或者登录已失效！");
    				window.location.href = "/login.jsp";
    			} else {
    				$.errorMsg(response.message);
    			}
    		}
			
		});
	}).on('click','a[name=tag-a]',function(){
		var val = encodeURIComponent($(this).data("tag"));
		window.location.href = "/index.jsp?tag="+val+"&tagType="+0;
	}).on('click','.amount-intervals li',function(){
		var val = encodeURIComponent($(this).children().data("tag"));
		var minAmount = $(this).find("a").first().data("minamount");
		var maxAmount = $(this).find("a").first().data("maxamount");
		window.location.href = "/index.jsp?tag="+val+"&tagType="+2+"&minAmount="+minAmount+"&maxAmount="+maxAmount;
	})
	/*
    $(window).scroll(function() {
		if ($(window).scrollTop()+70>($('.show-task-receive').offset().top)) {
			$(".show-task-receive").css("position","absolute");
			$(".show-task-receive").css("top",$(window).scrollTop()-46);
			
		}else{
			console.log("++");
		}

	});*/
});