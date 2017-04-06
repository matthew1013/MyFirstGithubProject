 $.views.tags({
        "status": function(status){
        	switch (true){
        	case status == 0:
        		return '<span class="font-green">等待确认</span>';
        	case status == 1:
        		return '<span class="font-green">已有接单</span>';
        	case status == 2:
        		return '<span class="font-red">进行中</span>';
        	case status == 3:
        		return '<span class="font-grey">任务关闭</span>';
        	case status == 4:
        		return '<span class="font-grey">任务完成</span>';
        	default:
        		return '<span class="font-red">未知</span>';
        	}
        	
        },
		 "totalAmount": function(taskAmount,da){
			 var totalAmount = Number(taskAmount);
			 $.each(da, function(i,item){ 
				 var amount = Number( item.amount);
				 if(!isNaN(amount))
					 totalAmount += amount;
	         });  
			 return totalAmount;
		 },
		 "setOptionChecked": function(optionValue,result){
			 if(result){
	        	if(optionValue==result){
	        		return "checked";
	        	}
	        }
	        return "";
		 },
 });
 /*
 $.views.helpers({
     "isShowNewDemand": function(index){
    	 if(index<5)return true;
    	 else return false;
     },
     "hasArbitration": function(rd){
    	 var has = false;
    	 if(rd!=null){
    		 $.each(rd, function(i,item){ 
    			 if(item.arbitration!=null){
    				 has = true;
    			 }
             });  
    	 }
    	 return has;
     },
});
*/
 


var taskPublishedList = function() {
    var tasklistInit = function() {
    	$("title").html("已发布任务 | 任务中心 | AnyoneHelps");
    	$(".nav2").addClass("active open");
		$(".nav22").addClass("active open");
		var state = $(".task-state .active").data("state");
		loadDemandList("",1,state)
		$(".task-state").click(function(){
			g_flag = true;
			loadDemandList("", 1, $(this).data("state"));
		});
    }
    
    function loadDemandList(key,pn,state) {
    	$(":hidden[name='key']").val(key);
    	$(":hidden[name='pn']").val(pn);
    	var states = [];
    	if(state==0){
    		states.push(0);
    		states.push(1);
    		states.push(2);
    		states.push(3);
    		states.push(4);
    		states.push(5);
    		states.push(6);
    		states.push(7);
    		states.push(8);
    	}else if(state==1){
    		states.push(0);
    		states.push(1);
    	}else if(state==2){
    		states.push(2);
    	}else if(state==3){
    		states.push(3);
    		states.push(4);
    		states.push(5);
    		states.push(6);
    		states.push(7);
    		states.push(8);
    	}
    	$.ajax({
    		type:"get",
    		url:"/demand/search",
    		data:$.param({
    			state : states,
    			key : key,
    			pageIndex : pn
    		},true),
    		success:function(response){
    			var code = response.code;
    			if ("200" == code) {
    				if(response.data==null) {
    					showDemandList(null);
    					showDemandLink(null);
    				} else {
    					showDemandList(response.data.datas);
    					showDemandLink(response.data);
    				}
    				afterLoadDemandList();
    			} else if ("607" == code) {
    				alert("您尚未登录或登录已失效！");
    				logout();
    			} 
    			return false;
    		}
    	});
    }
    
    function showDemandList(list) {
    	
    	if(list!=null){
    		console.log(list);
    		$('.task-list').html($('#task-item-tmpl').render({dataList: list}));
    		
    		$('.task-list').find(".zoom").click(function(){
    			if($(this).hasClass("up")){
    				$(this).removeClass("up");
    				$(this).children().removeClass("glyphicon-chevron-left");
    				$(this).children().addClass("glyphicon-chevron-down");
    				$(this).parent().parent().parent().parent().parent().find(".content").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".user").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".attach-list").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".achieve-receive").removeClass("hide");
    				
    			}else{
    				$(this).addClass("up");
    				$(this).children().addClass("glyphicon-chevron-left");
    				$(this).children().removeClass("glyphicon-chevron-down");
    				$(this).parent().parent().parent().parent().parent().find(".content").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".user").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".attach-list").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".achieve-receive").addClass("hide");
    				
    			}
    			
    		});
    		$('.attach-add-content').summernote({
    			height: 300
    		});
    		$('.task-attach-add').click(function(){
    			var open = $(this).parent().parent().data("open");
    			$(this).parent().parent().find(".attach-add").append($('#attach-add-item').render({open: open}))
    			$('.attach-add-content').summernote({
        			height: 300
        		});
    			$('.expire-time').datetimepicker({
                    language: 'zh-CN',
                    autoclose: 1,
                    todayBtn: 1,
                    pickerPosition: "bottom-left",
                    weekStart: 1,  
                    startView: 2,  
                    minView: 1,  
                    format: 'yyyy-mm-dd hh:ii:ss'
                   
                });
    		})
    		/*$('.receive-user ul li a').click(function(){
    			var taskId = $(this).data().taskid;
    			var userId = $(this).data().userid;
    			var rdId = $(this).data().rdid;
    			var userPic = $(this).data().userpic;
    			var state = $(this).data().state;
    			var bidBtn = "";
    			if(state==0||state==1){
    				bidBtn = '<li><a href="javascript:;" data-taskid="'+taskId+'" data-id="'+rdId+'" data-userid="'+userId+'" data-userpic="'+userPic+'" class="btn bid-btn">中标</a></li>';
    			}
    			dialog({
    				skin:"user-menu",
    			    content: '<div style="min-width:100px;"><ul>'+bidBtn+'<li><a href="javascript:;" data-id="'+userId+'" class="btn user-pic">查看个人资料</a></li></ul></div>',
    			    quickClose: true// 点击空白处快速关闭
    			}).show(this);
    		});*/
    		/*var task = $('#task-list').html($('#items-tmpl').render({dataList: list, baseUrl: baseUrl}));
    		task.find('.user-name').click(function(){
    			var id = $(this).data().id;
    			$.showUserDesc(id, this);
    		});
    		task.find('.demand-start-btn').click(function(){
    			var data = $(this).data();
    			var checked = $(this).parent().parent().find(":checked");
    			var ids = [];
    			checked.each(function(i, item){
    				ids.push($(item).data().id);
    			});
    			
    			$.demandStart(data.taskid, ids);
    			
    		});
    		task.find('.tooltips').tooltip();*/
    	}else{
    		$('.task-list').html("暂无发布的任务，快去发布接任吧~~");
    	}
    }
    function afterLoadDemandList() {
    	
    }
    var g_page_size=0;
    var g_flag=false;
    function showDemandLink(pageSplit) {
    	console.log(pageSplit);
    	if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		if(g_page_size != pageCount && g_flag){  
    		    $(".search-pagination").html('<ul class="pagination" id="pageSplit"></ul>');  
    		    g_flag = false;
    		}  
    		$("#pageSplit").twbsPagination({
                totalPages: pageCount,
                first: '首页',
                prev: '上一页',
                next: '下一页',
                last: '最后一页',
                onPageClick: function (event, page) {
                	var key = $(":hidden[name='key']").val();
                	var state = $(".task-state.active").data("state");
        		    loadDemandList(key,page,state);
                }
            });
    	}

    }
    
    return {
        //main function to initiate the module
        init: function() {
        	tasklistInit();
        }

    };

}();

var g_rdig = null;

jQuery(document).ready(function() {
	taskPublishedList.init();
	doc = $(window.document);
	/*var submitTask = function(demandId,amount,expireDate,content) {
		$.ajax({
			type : "post",
			url : "/demand_attach/add",
			data : $.param({
				demandId:demandId,
				content:content,
				amount:amount,
				expireDate:expireDate
			},true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("提交成功");
					window.location.href= "/dashboard/Task/publishedList.jsp";
				}else if ("15005"==code){
					$.errorMsg("操作失败:" + response.message);
					window.open("/dashboard/Finance/recharge.jsp");
				} else {
					$.errorMsg("操作失败:" + response.message);
				}
			}
		});
	}*/
	doc/*.on('click', '.demand-arbitration-btn', function(){
		var demandId = $(this).data().demandid;
		var userId = $(this).data().userid;
		var RDId = $(this).data().rdid;
		var title = $(this).data().title;
		var nickname = $(this).data().nickname;
		window.location.href= "/dashboard/Task/taskArbitration.jsp?id="+demandId+"&userid="+userId+"&rdid="+RDId+"&title="+title+"&nickname="+nickname;
	}).on('click', '.arbitration-result-btn', function(){
		var state = $(this).data().state;
		var reason = $(this).data().reason;
		var percentage = $(this).data().percentage;
		var title = $(this).data().title;
		var arbitrationid = $(this).data().arbitrationid;
		window.location.href= "/dashboard/Task/taskPublishedArbitration.jsp?arbitrationid="+arbitrationid+"&state="+state+"&reason="+reason+"&title="+title+"&percentage="+percentage;
	}).on('click', '.bid-btn', function(){
		var taskId = $(this).data().taskid;
		var $ul = $(".bid-user").find(".user_ul_"+taskId);
		var bidcount = $ul.data().bidcount;
		var length = $ul.children().length;
		if(bidcount>length){
			var userId = $(this).data().userid;
			var rdId = $(this).data().id;
			var userPic = $(this).data().userpic;
			$ul.append('<li><a class="bid-user-a" href="javascript:void(0);" target="_blank" data-userid="'+userId+'" data-rdid="'+rdId+'" ><img class="img-circle img-50" src="'+userPic+'"></a></li>')
		}else{
			alert("中标人数已经达到上限！！");
		}
	}).on('click', '.bid-user-a', function(){
		$(this).parent().remove();
	})*//*.on('click','.task-start',function(){
		var taskId = $(this).parent().parent().find(".bid-user ul").data("taskid");
		$.ajax({
			url: '/demand/start',
			type:'post',
			dataType: 'json',
			data:$.param({
				"demandId" : taskId
			}, true),
			success: function(result){
				if(result.code == 200){
					$.successMsg("任务开始成功");
					window.location.reload();
				}else{
					$.errorMsg(result.message);
				}
			},
			error: function(result){
				$.errorMsg(result.message); multiplex.jsp
			}
		});
		
	})*/.on('click', '.task-edit', function(){
		var id = $(this).data().id;
		window.open("/dashboard/Task/edit.jsp?id="+base64Encode(id.toString()));
	}).on('click', '.task-end', function(){
		var me = $(this);
		$.ajax({
			url: '/demand/close',
			type:'post',
			dataType: 'json',
			data:$.param({
				"demandId" : me.data().id
			}, true),
			success: function(result){
				if(result.code == 200){
					$.successMsg("关闭任务成功");
					me.parent().parent().remove();
				}else{
					$.errorMsg(result.message);
				}
			},
			error: function(result){
				$.errorMsg(result.message);
			}
		});
	}).on('click', '.task-complete', function(){
		var me = $(this);
		var meThis = this
		var taskId = me.data("id");
		$.ajax({
			url: '/demand/finish',
			type:'post',
			dataType: 'json',
			data:$.param({
				"demandId" : taskId
			}, true),
			success: function(result){
				if(result.code == 200){
					$.successMsg("任务结束成功，请确认支付报酬！");
					me.addClass("task-pay");
					me.removeClass("task-complete");
					me.html("确认支付");
					//window.location.reload();
					var dlg = dialog({
						align:'top left',
						skin:'task-pay-dlg',
						width : 720,
						quickClose : true,
						onshow : function() {
							var me = this;
							$.ajax({
					    		type:"get",
					    		url:"/demand/get_one",
					    		data:{
					    			id:taskId
					    		},
					    		success:function(response){
					    			var code = response.code;
					    			var data = response.data;
					    			console.log(data);
					    			if ("200" == code) {
					    				if(response.data!=null){
					    					me.content($('#task-pay-dlg').render({task:response.data}));
					    				}
					    				
					    				$(".wclose").click(function(){
					    		    		dlg.close().remove();
					    		    		return false;
					    		    	})
					    		    	
					    		    	$(':radio').iCheck({
					    		    		checkboxClass: 'icheckbox_flat-blue',
					    		    		radioClass: 'iradio_flat-blue',
					    		    		increaseArea: '20%', // optional
					    		    		check:function(){
					    		    			alert('Well done, Sir');
					    		    		}
					    		    	});
					    				$(":radio").on('ifChecked', function(event){
					    					if($(this).val()!="100"){
					    						$(this).parent().parent().next().html($('#task-pay-reason').render());
					    						$(':file[name="files[]"]').fileupload({
							    		    		dataType: 'json',
							    		    		acceptFileTypes:  /(\.|\/)(gif|jpe?g|png|bmp|docx?|pptx?|xlsx?|pdf|zip|rar)$/i,  
							    			        maxNumberOfFiles : 1,  
							    			        maxFileSize: 20*1024*1024, 
							    			        messages:{
							    			        	maxFileSize : '文件大小不能超过20M!',
							    			        	acceptFileTypes : '文件类型不支持！'
							    			        },
							    			        processfail : function(e,data){
							    			        	var currentFile = data.files[data.index];
							    			        	if(data.files.error && currentFile.error){
							    			        		console.log(currentFile.error);
							    			        		$.errorMsg(currentFile.error);
							    			        	}
							    			        },
							    		    		done: function (e, data) {
							    		    			var obj = $(this).parent().parent().parent();
							    		    			var code = data.result.code; 
							    		    			if(code=="200"){
							    		    				if(data.result.data!=null){
							    		    					$.each(data.result.data, function (index, file) {
							    		    						if( obj.find('.upload-file-list').children().length > 5){
							    		    							obj.find(".quote-upload").hide();
							    		    							obj.find('.quote-format').hide();
							    		    							return;
							    		    						}
							    		    						obj.find('.upload-file-list').append($('#arbitration-upload-file').render(file));
							    		    						if(obj.find('.upload-file-list').children().length>=5){
							    		    							obj.find(".quote-upload").hide();
							    		    							obj.find('.progress').hide();
							    		    							obj.find('.quote-format').hide();
							    		    						}
							    		    						obj.find('.upload-file-delete').unbind("click"); //移除click
							    		    						obj.find('.upload-file-delete').click(function(){
							    		    							$(this).parent().parent().remove();
							    		    							obj.find(".quote-upload").show();
							    		    							obj.find('.quote-format').show();
							    		    						});
							    		    					}); 
							    		    				}
							    		    			}else{
							    		    				alert(data.result.message);
							    		    			}
							    		    		},
				    			        
							    		    		progressall: function (e, data) {
							    		    			var obj = $(this).parent().parent().parent();
							    		    			var progress = parseInt(data.loaded / data.total * 100, 10);
							    		    			if(progress=100){
							    		    				obj.find('.progress').hide();
							    		    			}else{
							    		    				obj.find('.progress').show();
							    		    			}
							    		    			obj.find('.progress .bar').css(
							    		    					'width',
							    		    					progress + '%'
							    		    			);
							    		    		},
							    		    	});
					    					}else{
					    						$(this).parent().parent().next().html("");
					    					}
					    				});
					    		    	$(".task-pay-cancel").click(function(){
					    		    		dlg.close().remove();
					    		    		return false;
					    		    	});
					    		    	$(".task-pay-ok").click(function(){
					    		    		var title = $(this).parent().parent().data("title");
					    		    		var taskId = $(this).parent().parent().data("taskid");
					    		    		var payItem = $(this).parent().parent().find(".pay-item");
					    		    		var payPercent = "";
					    		    		var payReason = "";
					    		    		var payReasonUrl1 = "";
					    		    		var payReasonUrl2 = "";
					    		    		var payReasonUrl3 = "";
					    		    		var payReasonUrl4 = "";
					    		    		var payReasonUrl5 = "";
					    		    		var payReasonUrl1Name = "";
					    		    		var payReasonUrl2Name = "";
					    		    		var payReasonUrl3Name = "";
					    		    		var payReasonUrl4Name = "";
					    		    		var payReasonUrl5Name = "";
					    		    		
					    		    		if(payItem.length){
					    		    			payItem.each(function(index, item){
					    		    				payPercent = $( this ).find(":radio:checked").val();
					    		    				payReason = $( this ).find(".pay-reason").val();
					    		    				$( this ).find(".upload-file-item").each(function(i,item){
							    						if(i<5){
							    							if(i==0){
							    								payReasonUrl1 = $( item ).data("id");
							    								payReasonUrl1Name = $( item ).data("filename");
							    							}else if(i==1){
							    								payReasonUrl2 = $( item ).data("id");
							    								payReasonUrl2Name = $( item ).data("filename");
							    							}else if(i==2){
							    								payReasonUrl3 = $( item ).data("id");
							    								payReasonUrl3Name = $( item ).data("filename");
							    							}else if(i==3){
							    								payReasonUrl4 = $( item ).data("id");
							    								payReasonUrl4Name = $( item ).data("filename");
							    							}else if(i==4){
							    								payReasonUrl5 = $( item ).data("id");
							    								payReasonUrl5Name = $( item ).data("filename");
							    							}
							    						}
							    					});
					    		    			});
					    		    			$.ajax({
					    		    				url: "/demand/pay",
					    		    				type:'post',
					    		    				dataType: 'json',
					    		    				data:$.param({
					    		    					demandId : taskId,
					    		    					payPercent : payPercent,
								    		    		payReason : payReason,
								    		    		payReasonUrl1 : payReasonUrl1,
								    		    		payReasonUrl2 : payReasonUrl2,
								    		    		payReasonUrl3 : payReasonUrl3,
								    		    		payReasonUrl4 : payReasonUrl4,
								    		    		payReasonUrl5 : payReasonUrl5,
								    		    		payReasonUrl1Name : payReasonUrl1Name,
								    		    		payReasonUrl2Name : payReasonUrl2Name,
								    		    		payReasonUrl3Name : payReasonUrl3Name,
								    		    		payReasonUrl4Name : payReasonUrl4Name,
								    		    		payReasonUrl5Name : payReasonUrl5Name
					    		    				}, true),
					    		    				success: function(result){
					    		    					if(result.code == 200){
					    		    						$.successMsg("支付成功！");
					    		    						dlg.close().remove();
					    		    					}else{
					    		    						$.errorMsg(result.message);
					    		    					}
					    		    				},
					    		    				error: function(result){
					    		    					$.errorMsg(result.message);
					    		    				}
					    		    			});
					    		    		}else{
					    		    			$.errorMsg("参数错误！");
					    		    		}
					    		    	});
					    		    	
					    		    	
					    				
					    			} else if ("607" == code) {
					    				alert("您尚未登录或登录已失效！");
					    				logout();
					    			} 
					    			return false;
					    		}
					    	});
							
						},
						onclose: function () {
					    }
					}).show(meThis);
				}else{
					$.errorMsg(result.message);
				}
			},
			error: function(result){
				$.errorMsg(result.message);
			}
		});
	}).on('click', '.task-pay', function(){
		var meThis = this;
		var taskId = $(this).data("id");
		var dlg = dialog({
			align:'top left',
			skin:'task-pay-dlg',
			width : 720,
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/get_one",
		    		data:{
		    			id:taskId
		    		},
		    		success:function(response){
		    			var code = response.code;
		    			var data = response.data;
		    			console.log(data);
		    			if ("200" == code) {
		    				if(response.data!=null){
		    					me.content($('#task-pay-dlg').render({task:response.data}));
		    				}
		    				
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	
		    		    	$(':radio').iCheck({
		    		    		checkboxClass: 'icheckbox_flat-blue',
		    		    		radioClass: 'iradio_flat-blue',
		    		    		increaseArea: '20%', // optional
		    		    		check:function(){
		    		    			alert('Well done, Sir');
		    		    		}
		    		    	});
		    				$(".pay-item").each(function() { 
		    		    		if($(this).data("ispay")=="1"){
		    		    			$(".task-pay-list .operate").hide();
		    		    		}
		    		    	});
		    				$(":radio").on('ifChecked', function(event){
		    					if($(this).val()!="100"){
		    						$(this).parent().parent().next().html($('#task-pay-reason').render());
		    						$(':file[name="files[]"]').fileupload({
				    		    		dataType: 'json',
				    		    		acceptFileTypes:  /(\.|\/)(gif|jpe?g|png|bmp|docx?|pptx?|xlsx?|pdf|zip|rar)$/i,  
				    			        maxNumberOfFiles : 1,  
				    			        maxFileSize: 20*1024*1024, 
				    			        messages:{
				    			        	maxFileSize : '文件大小不能超过20M!',
				    			        	acceptFileTypes : '文件类型不支持！'
				    			        },
				    			        processfail : function(e,data){
				    			        	var currentFile = data.files[data.index];
				    			        	if(data.files.error && currentFile.error){
				    			        		console.log(currentFile.error);
				    			        		$.errorMsg(currentFile.error);
				    			        	}
				    			        },
				    		    		done: function (e, data) {
				    		    			var obj = $(this).parent().parent().parent();
				    		    			var code = data.result.code; 
				    		    			if(code=="200"){
				    		    				if(data.result.data!=null){
				    		    					$.each(data.result.data, function (index, file) {
				    		    						if( obj.find('.upload-file-list').children().length > 5){
				    		    							obj.find(".quote-upload").hide();
				    		    							obj.find('.quote-format').hide();
				    		    							return;
				    		    						}
				    		    						obj.find('.upload-file-list').append($('#arbitration-upload-file').render(file));
				    		    						if(obj.find('.upload-file-list').children().length>=5){
				    		    							obj.find(".quote-upload").hide();
				    		    							obj.find('.progress').hide();
				    		    							obj.find('.quote-format').hide();
				    		    						}
				    		    						obj.find('.upload-file-delete').unbind("click"); //移除click
				    		    						obj.find('.upload-file-delete').click(function(){
				    		    							$(this).parent().parent().remove();
				    		    							obj.find(".quote-upload").show();
				    		    							obj.find('.quote-format').show();
				    		    						});
				    		    					}); 
				    		    				}
				    		    			}else{
				    		    				alert(data.result.message);
				    		    			}
				    		    		},
	    			        
				    		    		progressall: function (e, data) {
				    		    			var obj = $(this).parent().parent().parent();
				    		    			var progress = parseInt(data.loaded / data.total * 100, 10);
				    		    			if(progress=100){
				    		    				obj.find('.progress').hide();
				    		    			}else{
				    		    				obj.find('.progress').show();
				    		    			}
				    		    			obj.find('.progress .bar').css(
				    		    					'width',
				    		    					progress + '%'
				    		    			);
				    		    		},
				    		    	});
		    					}else{
		    						$(this).parent().parent().next().html("");
		    					}
		    				});
		    		    	$(".task-pay-cancel").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	});
		    		    	$(".task-pay-ok").click(function(){
		    		    		var title = $(this).parent().parent().data("title");
		    		    		var taskId = $(this).parent().parent().data("taskid");
		    		    		var payItem = $(this).parent().parent().find(".pay-item");
		    		    		var payPercent = "";
		    		    		var payReason = "";
		    		    		var payReasonUrl1 = "";
		    		    		var payReasonUrl2 = "";
		    		    		var payReasonUrl3 = "";
		    		    		var payReasonUrl4 = "";
		    		    		var payReasonUrl5 = "";
		    		    		var payReasonUrl1Name = "";
		    		    		var payReasonUrl2Name = "";
		    		    		var payReasonUrl3Name = "";
		    		    		var payReasonUrl4Name = "";
		    		    		var payReasonUrl5Name = "";
		    		    		
		    		    		if(payItem.length){
		    		    			payItem.each(function(index, item){
		    		    				payPercent = $( this ).find(":radio:checked").val();
		    		    				payReason = $( this ).find(".pay-reason").val();
		    		    				
		    		    				$( this ).find(".upload-file-item").each(function(i,item){
				    						if(i<5){
				    							if(i==0){
				    								payReasonUrl1 = $( item ).data("id");
				    								payReasonUrl1Name = $( item ).data("filename");
				    							}else if(i==1){
				    								payReasonUrl2 = $( item ).data("id");
				    								payReasonUrl2Name = $( item ).data("filename");
				    							}else if(i==2){
				    								payReasonUrl3 = $( item ).data("id");
				    								payReasonUrl3Name = $( item ).data("filename");
				    							}else if(i==3){
				    								payReasonUrl4 = $( item ).data("id");
				    								payReasonUrl4Name = $( item ).data("filename");
				    							}else if(i==4){
				    								payReasonUrl5 = $( item ).data("id");
				    								payReasonUrl5Name = $( item ).data("filename");
				    							}
				    						}
				    					});
		    		    			});
		    		    			$.ajax({
		    		    				url: "/demand/pay",
		    		    				type:'post',
		    		    				dataType: 'json',
		    		    				data:$.param({
		    		    					demandId : taskId,
		    		    					payPercent : payPercent,
					    		    		payReason : payReason,
					    		    		payReasonUrl1 : payReasonUrl1,
					    		    		payReasonUrl2 : payReasonUrl2,
					    		    		payReasonUrl3 : payReasonUrl3,
					    		    		payReasonUrl4 : payReasonUrl4,
					    		    		payReasonUrl5 : payReasonUrl5,
					    		    		payReasonUrl1Name : payReasonUrl1Name,
					    		    		payReasonUrl2Name : payReasonUrl2Name,
					    		    		payReasonUrl3Name : payReasonUrl3Name,
					    		    		payReasonUrl4Name : payReasonUrl4Name,
					    		    		payReasonUrl5Name : payReasonUrl5Name
		    		    				}, true),
		    		    				success: function(result){
		    		    					if(result.code == 200){
		    		    						$.successMsg("支付成功！");
		    		    						dlg.close().remove();
		    		    					}else{
		    		    						$.errorMsg(result.message);
		    		    					}
		    		    				},
		    		    				error: function(result){
		    		    					$.errorMsg(result.message);
		    		    				}
		    		    			});
		    		    		}else{
		    		    			$.errorMsg("参数错误！");
		    		    		}
		    		    	});
		    		    	
		    		    	$(".task-pay-agree").click(function(){
		    		    		$.ajax({
	    		    				url: "/demand/agree_arbitration",
	    		    				type:'post',
	    		    				dataType: 'json',
	    		    				data:$.param({
	    		    					demandId : taskId
	    		    				}, true),
	    		    				success: function(result){
	    		    					if(result.code == 200){
	    		    						$.successMsg("同意仲裁成功，任务结束，请对接单人进行评价！");
	    		    						$( meThis ).removeClass("task-pay");
	    		    						$( meThis ).addClass("task-evaluate");
	    		    						$( meThis ).html("评价");
	    		    						dlg.close().remove();
	    		    						
	    		    					}else{
	    		    						$.errorMsg(result.message);
	    		    					}
	    		    				},
	    		    				error: function(result){
	    		    					$.errorMsg(result.message);
	    		    				}
		    		    		});
		    		    		return false;
		    		    	});
		    		    	
		    		    	
		    				
		    			} else if ("607" == code) {
		    				alert("您尚未登录或登录已失效！");
		    				logout();
		    			} 
		    			return false;
		    		}
		    	});
				
			},
			onclose: function () {
		    }
		}).show(meThis);
	}).on('click', '.task-arbitration', function(){
		var taskArbitrationBtn = $(this);
		var taskId = $(this).data("id"); 
		var dlg = dialog({
			align:'top left',
			skin:'task-arbitration-dlg',
			width : 720,
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/get_one",
		    		data:{
		    			id:taskId
		    		},
		    		success:function(response){
		    			var code = response.code;
		    			var data = response.data;
		    			console.log(data);
		    			if ("200" == code) {
		    				if(response.data!=null){
		    					me.content($('#task-arbitration-dlg').render({task:response.data}));
		    				}
		    				
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(':file[name="files[]"]').fileupload({
		    		    		dataType: 'json',
		    		    		acceptFileTypes:  /(\.|\/)(gif|jpe?g|png|bmp|docx?|pptx?|xlsx?|pdf|zip|rar)$/i,  
		    			        maxNumberOfFiles : 1,  
		    			        maxFileSize: 20*1024*1024, 
		    			        messages:{
		    			        	maxFileSize : '文件大小不能超过20M!',
		    			        	acceptFileTypes : '文件类型不支持！'
		    			        },
		    			        processfail : function(e,data){
		    			        	var currentFile = data.files[data.index];
		    			        	if(data.files.error && currentFile.error){
		    			        		console.log(currentFile.error);
		    			        		$.errorMsg(currentFile.error);
		    			        	}
		    			        },
		    			        done: function (e, data) {
		    			        	var obj = $(this).parent().parent().parent();
		    			        	var code = data.result.code;
		    			        	if(code=="200"){
		    			        		if(data.result.data!=null){
		    			    	            $.each(data.result.data, function (index, file) {
		    			    	            	if( obj.find('.upload-file-list').children().length > 5){
		    			    	            		obj.find(".quote-upload").hide();
		    			    	            		obj.find('.quote-format').hide();
		    			    	            		return;
		    			    	            	}
		    			    	            	obj.find('.upload-file-list').append($('#arbitration-upload-file').render(file));
		    				        			if(obj.find('.upload-file-list').children().length>=3){
		    				        				obj.find(".quote-upload").hide();
		    				        				obj.find('.progress').hide();
		    				        				obj.find('.quote-format').hide();
		    				        			}
		    				        			obj.find('.upload-file-delete').unbind("click"); //移除click
		    				        			obj.find('.upload-file-delete').click(function(){
		    			        					$(this).parent().parent().remove();
		    			        					obj.find(".quote-upload").show();
		    			        					obj.find('.quote-format').show();
		    			        				});
		    			    	            }); 
		    			        		}
		    			        	}else{
		    			        		alert(data.result.message);
		    			        	}
		    			        },
		    			        
		    			        progressall: function (e, data) {
		    			        	var obj = $(this).parent().parent().parent();
		    				        var progress = parseInt(data.loaded / data.total * 100, 10);
		    				        if(progress=100){
		    				        	obj.find('.progress').hide();
		    				        }else{
		    				        	obj.find('.progress').show();
		    				        }
		    				        obj.find('.progress .bar').css(
		    				            'width',
		    				            progress + '%'
		    				        );
		    			   		},
		    			    });
		    				
		    		    
		    		    	$(':radio').iCheck({
		    		    		checkboxClass: 'icheckbox_flat-blue',
		    		    		radioClass: 'iradio_flat-blue',
		    		    		increaseArea: '20%', // optional
		    		    		check:function(){
		    		    			alert('Well done, Sir');
		    		    		}
		    		    	});
		    		    	$(".task-arbitration-cancel").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".task-arbitration-ok").click(function(){
		    		    		
		    		    		var title = $(this).parent().parent().data("title");
		    		    		var taskId = $(this).parent().parent().data("taskid");
		    		    		var arbitrationItem = $(this).parent().parent().find(".arbitration-item");
		    		    		var arbitration = "";
		    		    		if(arbitrationItem.length){
		    		    			arbitrationItem.each(function(index, item){
		    		    				if(index!=0){
		    		    					arbitration += ",";
		    		    				}
		    		    				arbitration += '"arbitrations['+index+'].reason":"' + $( this ).find(".arbitration").val() + '"';
		    		    				arbitration += ',"arbitrations['+index+'].percentage":"' + $( this ).find(":radio:checked").val() + '"';
		    		    				arbitration += ',"arbitrations['+index+'].receiveDemandId":"' + $( this ).data("rdid") + '"';
		    		    				arbitration += ',"arbitrations['+index+'].refuteUserId":"' + $( this ).data("userid") + '"';
		    		    				arbitration += ',"arbitrations['+index+'].demandId":"' + taskId + '"';
		    		    				arbitration += ',"arbitrations['+index+'].title":"' + title + '"';
		    		    				
		    		    				$( this ).find(".upload-file-item").each(function(i,item){
				    						if(i<5){
				    							temp = i+1;
				    							arbitration += ',"arbitrations['+index+'].quoteUrl'+temp+'":"' + $( item ).data("id") + '"';
				    						}
				    					});
		    		    			});
		    		    			console.log(arbitration);
		    		    			var obj = $.parseJSON("{"+arbitration+"}");
		    		    			$.ajax({
		    		    				url: "/arbitration/add",
		    		    				type:'post',
		    		    				dataType: 'json',
		    		    				data:$.param(obj, true),
		    		    				success: function(result){
		    		    					if(result.code == 200){
		    		    						$.successMsg("提交仲裁成功！");
		    		    						dlg.close().remove();
		    		    						taskArbitrationBtn.html("查看任务仲裁")
		    		    					}else{
		    		    						$.errorMsg(result.message);
		    		    					}
		    		    				},
		    		    				error: function(result){
		    		    					$.errorMsg(result.message);
		    		    				}
		    		    			});
		    		    		}else{
		    		    			$.errorMsg("参数错误！");
		    		    		}
		    		    	})
		    		    	$(".arbitration-item").each(function() { 
		    		    		if($(this).data("isarbitration")=="1"){
		    		    			$(".task-arbitration-list .operate").hide();
		    		    		}
		    		    	});
		    				
		    			} else if ("607" == code) {
		    				alert("您尚未登录或登录已失效！");
		    				logout();
		    			} 
		    			return false;
		    		}
		    	});
				
			},
			onclose: function () {
		    }
		}).show(this);
		
		
	}).on('click', '.delete-arbitration', function(){
		var id = $(this).data("id"); 
		var obj = $(this);
		$.ajax({
			url: "/arbitration/delete",
			type:'post',
			dataType: 'json',
			data:{
    			id:id
    		},
			success: function(result){
				if(result.code == 200){
					$.successMsg("取消仲裁成功！");
					obj.parent().parent().parent().remove();
				}else{
					$.errorMsg(result.message);
				}
			},
			error: function(result){
				$.errorMsg(result.message);
			}
		});
	}).on('click', '.task-evaluate', function(){
		var taskId = $(this).data("id"); 
		var dlg = dialog({
			align:'top left',
			skin:'task-evaluate-dlg',
			width : 720,
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/get_one",
		    		data:{
		    			id:taskId
		    		},
		    		success:function(response){
		    			var code = response.code;
		    			var data = response.data;
		    			console.log(data);
		    			if ("200" == code) {
		    				if(response.data!=null){
		    					me.content($('#task-evaluate-dlg').render({task:response.data}));
		    				}
		    				$(".rating").rating({
		    					starCaptions: {"0.5": 'Very Poor',1: 'Very Poor', "1.5": 'Poor', 2: 'Poor', "2.5": 'Ok', 3: 'Ok', "3.5": 'Good', 4: 'Good', "4.5": 'Very Good', 5: 'Very Good'},
		    					starCaptionClasses: {"0.5": 'text-danger',1: 'text-danger',"1.5": 'text-warning', 2: 'text-warning',"2.5": 'text-info', 3: 'text-info',"3.5": 'text-primary', 4: 'text-primary',"4.5": 'text-success', 5: 'text-success'}
		    				});
		    				
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".task-evaluate-cancel").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".task-evaluate-ok").click(function(){
		    		    		var evaluateItem = $(this).parent().parent().find(".evaluate-item");
		    		    		var taskId = $(this).parent().parent().find(".user-info").data("id");
		    		    		var evaluate = "";
		    		    		if(evaluateItem.length){
		    		    			evaluateItem.each(function(index, item){
		    		    				if(index!=0){
		    		    					evaluate += ",";
		    		    				}
		    		    				evaluate += '"evaluates['+index+'].evaluateUserId":"' + $( this ).data("userid") + '"';
		    		    				evaluate += ',"evaluates['+index+'].description":"' + $( this ).find(".evaluate").val() + '"';
		    		    				evaluate += ',"evaluates['+index+'].evaluate":"' + $( this ).find(".evaluate-star").val() + '"';
		    		    				evaluate += ',"evaluates['+index+'].quality":"' + $( this ).find(".quality-star").val() + '"';
		    		    				evaluate += ',"evaluates['+index+'].punctual":"' + $( this ).find(".punctual-star").val() + '"';
		    		    				evaluate += ',"evaluates['+index+'].demandId":"' + taskId + '"';
		    		    				
		    		    			});
		    		    			var obj = $.parseJSON("{"+evaluate+"}");
		    		    			$.ajax({
		    		    				url: '/demand/evaluate',
		    		    				type:'post',
		    		    				dataType: 'json',
		    		    				data:$.param(obj, true),
		    		    				success: function(result){
		    		    					if(result.code == 200){
		    		    						$.successMsg("评价成功！");
		    		    						dlg.close().remove();
		    		    					}else{
		    		    						$.errorMsg(result.message);
		    		    					}
		    		    				},
		    		    				error: function(result){
		    		    					$.errorMsg(result.message);
		    		    				}
		    		    			});
		    		    		}else{
		    		    			$.errorMsg("参数错误！");
		    		    		}
		    		    		
		    		    	})
		    		    	$(".rating").each(function() { 
		    		    		if($(this).data("isevaluate")=="1"){
		    		    			
		    		    			$(".task-evaluate-list .operate").hide();
		    		    		}
		    		    	});
		    				
		    			} else if ("607" == code) {
		    				alert("您尚未登录或登录已失效！");
		    				logout();
		    			} 
		    			return false;
		    		}
		    	});
				
			},
			onclose: function () {
		    }
		}).show(this);
	})/*.on('click', '.task-start', function(){
		var taskId = $(this).data("id"); 
		var dlg = dialog({
			align:'top left',
			skin:'task-select-dlg',
			width : 720,
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/get_one",
		    		data:{
		    			id:taskId
		    		},
		    		success:function(response){
		    			var code = response.code;
		    			if ("200" == code) {
		    				if(response.data!=null){
		    					me.content($('#user-select-dlg').render({task:response.data}));
		    				}
		    				$('.task-select-dlg').find(":radio").iCheck({
		    			  	  	checkboxClass: 'icheckbox_minimal-blue',
		    			    	radioClass: 'iradio_minimal-blue',
		    			   	 	increaseArea: '20%', // optional
		    			   	 	check:function(){
		    			   	 	  alert('Well done, Sir');
		    			   	 	}
		    			  	})
		    			  	//选中的再次点击取消
		    			  	$('.task-select-dlg').find("ins").on('click', function(event){
		    			  		var obj = $(this).prev();
		    			  		if(obj.data("rdid")==g_rdig){
		    			  			
		    			  			obj.iCheck('uncheck'); 
		    			  			obj.removeAttr("checked");//不选中
		    			  			g_rdig = null;
		    			  		}else{
		    			  			g_rdig = obj.data("rdid");
		    			  		}
		    			  		
		    			  		
		    			  	})
		    			  	
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".user-select-cancel").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".user-select-ok").click(function(){
		    		    		var userCheck = $(this).parent().parent().find(":radio[name='userCheck']");
		    		    		var taskId = $(this).parent().parent().data().taskid;
		    		    		var id = "";
		    		    		var str = "";
		    		    		if(userCheck.length){
		    		    			userCheck.each(function(i, item){
		    		    				if($(this).is(":checked")){
		    		    					id = $(item).data().rdid;
		    		    					
		    		    					str +="<li>";
		    		    					str +='<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="'+$(item).data().id+'" data-userid="'+$(item).data().id+'" data-rdid="'+$(item).data().rdid+'"><img class="img-circle img-50 opacity08" src="'+$(item).data().pic+'"></a>';
		    		    					str +="</li>";
		    		    					return false;
		    		    				}
		    		    				
		    		    			});
		    		    		}
		    		    		
		    		    		if(id==""){
		    		    			$.successMsg("您没有选中接单人！！");
		    		    			return false;
		    		    		}
		    		    		$.ajax({
	    		    				url: '/demand/start',
	    		    				type:'post',
	    		    				dataType: 'json',
	    		    				data:$.param({
	    		    					"id" : id,
	    		    					"demandId" : taskId
	    		    				}, true),
	    		    				success: function(result){
	    		    					if(result.code == 200){
	    		    						$.successMsg("匹配成功,已经开始任务");
	    		    						//
	    		    						$(".bid-user ul").html(str);
	    		    						dlg.close().remove();
	    		    						
	    		    					}else{
	    		    						$.errorMsg(result.message);
	    		    					}
	    		    				},
	    		    				error: function(result){
	    		    					$.errorMsg(result.message);
	    		    				}
	    		    			});
		    		    	})
		    				
		    			} else if ("607" == code) {
		    				alert("您尚未登录或登录已失效！");
		    				logout();
		    			} 
		    			return false;
		    		}
		    	});
				
			},
			onclose: function () {
		    }
		}).show(this);
	})*/.on('click', '.task-select', function(){
		var taskId = $(this).data("id"); 
		var dlg = dialog({
			align:'top left',
			skin:'task-select-dlg',
			width : 720,
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/get_one",
		    		data:{
		    			id:taskId
		    		},
		    		success:function(response){
		    			var code = response.code;
		    			if ("200" == code) {
		    				if(response.data!=null){
		    					me.content($('#user-select-dlg').render({task:response.data}));
		    				}
		    				$('.task-select-dlg').find(":radio").iCheck({
		    			  	  	checkboxClass: 'icheckbox_flat-blue',
		    			    	radioClass: 'iradio_flat-blue',
		    			   	 	increaseArea: '20%', // optional
		    			   	 	check:function(){
		    			   	 	  alert('Well done, Sir');
		    			   	 	}
		    			  	})
		    			  	//选中的再次点击取消
		    			  	$('.task-select-dlg').find("ins").on('click', function(event){
		    			  		var obj = $(this).prev();
		    			  		if(obj.data("rdid")==g_rdig){
		    			  			
		    			  			obj.iCheck('uncheck'); 
		    			  			obj.removeAttr("checked");//不选中
		    			  			g_rdig = null;
		    			  		}else{
		    			  			g_rdig = obj.data("rdid");
		    			  		}
		    			  		
		    			  		
		    			  	})
		    			  	
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".user-select-cancel").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".user-select-ok").click(function(){
		    		    		var userCheck = $(this).parent().parent().find(":radio[name='userCheck']");
		    		    		var taskId = $(this).parent().parent().data().taskid;
		    		    		var id = "";
		    		    		var str = "";
		    		    		if(userCheck.length){
		    		    			userCheck.each(function(i, item){
		    		    				if($(this).is(":checked")){
		    		    					id = $(item).data().rdid;
		    		    					
		    		    					str +="<li>";
		    		    					str +='<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="'+$(item).data().id+'" data-userid="'+$(item).data().id+'" data-rdid="'+$(item).data().rdid+'"><img class="img-circle img-50 opacity08" src="'+$(item).data().pic+'"></a>';
		    		    					str +="</li>";
		    		    					return false;
		    		    				}
		    		    				
		    		    			});
		    		    		}
		    		    		
		    		    		if(id==""){
		    		    			$.successMsg("您没有选中接单人！！");
		    		    			return false;
		    		    		}
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
	    		    						$(".bid-user ul").html(str);
	    		    						dlg.close().remove();
	    		    						$(".task-end").removeClass("hide");
	    		    						$(".task-edit").addClass("hide");
	    		    						$(".task-select").addClass("hide");
	    		    					}else{
	    		    						$.errorMsg(result.message);
	    		    					}
	    		    				},
	    		    				error: function(result){
	    		    					$.errorMsg(result.message);
	    		    				}
	    		    			});
		    		    	})
		    				
		    			} else if ("607" == code) {
		    				alert("您尚未登录或登录已失效！");
		    				logout();
		    			} 
		    			return false;
		    		}
		    	});
				
			},
			onclose: function () {
		    }
		}).show(this);
	}).on('click','.remark',function(){
		var me = $(this);
		var id = $(this).data().id;
		var remark = $(this).attr("remark");
		dialog({
			align:'bottom right',
			skin:'remark-dlg',
			width : 300,
			quickClose : true,
			onshow : function() {
				this.content('<div><textarea type="text" rows="4" placeholder="请输入您的备注" class="form-control radius0 remark-desc">'+remark+'</textarea></div>');
			},
			onclose: function () {
				var remarkDesc = $(".remark-dlg").find(".remark-desc").val();
				$.ajax({
					url: '/demand/modify_remark',
					type:'post',
					dataType: 'json',
					data:$.param({
						"id" : id,
						"remark" : remarkDesc
					}, true),
					success: function(result){
						if(result.code == 200){
							$.successMsg(result.message);
							me.attr("remark",remarkDesc);
						}else{
							$.errorMsg(result.message);
						}
					},
					error: function(result){
						$.errorMsg(result.message);
					}
				});
		    }
		}).show(this);
	}).on('click', '.attach-add-submit', function(){
		var demandId = $(this).parent().parent().parent().parent().parent().data("id");
    	var amount = $(this).parent().parent().parent().find(".amount").val();
    	var expireDate = $(this).parent().parent().parent().find(".expire-time").val(); 
    	var content = $(this).parent().parent().parent().find('.attach-add-content').code().replace(/\"/g,"\\\"");
    	var me = $(this).parent().parent().parent();
    	$.ajax({
			type : "post",
			url : "/demand_attach/add",
			data : $.param({
				demandId:demandId,
				content:content,
				amount:amount,
				expireDate:expireDate
			},true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("提交成功");
					if(response.data!=null){
						$(".attach-list").append($('#attach-item').render({attach:response.data}));
					}
					me.remove();
				}else if ("15005"==code){
					$.errorMsg("操作失败:" + response.message);
					//window.open("/dashboard/Finance/recharge.jsp");
				} else {
					$.errorMsg("操作失败:" + response.message);
				}
			}
		});
	}).on('click', '.attach-add-cancel', function(){
		$(this).parent().parent().parent().remove();
	}).on('click', '.attach-delete', function(){
		var id = $(this).data().id;
		var me = $(this);
		$.ajax({
			url : '/demand_attach/delete',
			type:'post',
			dataType: 'json',
			data : {
				id : id
			},
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("删除成功");
					me.parent().parent().remove();
				} else {
					$.errorMsg("删除失败:" + response.message);
				}
			},
		});
	});
	
	
});