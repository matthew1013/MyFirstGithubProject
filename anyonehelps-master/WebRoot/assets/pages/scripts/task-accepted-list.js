 $.views.tags({
        "status": function(status){
        	switch (true){
        	case status == 0: 
        		return '<span class="font-green">等待确认</span>';
        	case status == 1:
        		return '<span class="font-red">已有接单</span>';
        	case status == 2:
        		return '<span class="font-red">进行中</span>';
        	case status == 3:
        		return '<span class="font-grey">任务关闭</span>';
        	case status == 4:
        		return '<span class="font-grey">任务结束</span>';
        	default:
        		return '<span class="font-red">未知</span>';
        	}
        	
        },
        "reciveStatus": function(status){
        	switch (true){
        	case status == 1: 
        		return '<span class="font-green">选中</span>';
        	default:
        		return '<span class="font-red">未选中</span>';
        	}
        	
        },
		 "totalAmount": function(taskAmount,da){
			 var totalAmount = Number(taskAmount);
			 $.each(da, function(i,item){ 
				 if(item.state==1){
					 var amount = Number( item.amount);
					 if(!isNaN(amount))
						 totalAmount += amount;
				 }
	         });  
			 return totalAmount;
		 },"payAmount": function(taskAmount,da,percent){
			 var totalAmount = Number(taskAmount);
			 $.each(da, function(i,item){ 
				 if(item.state==1){
					 var amount = Number( item.amount);
					 if(!isNaN(amount))
						 totalAmount += amount;
				 }
	         });  
			 return totalAmount*Number(percent)/100;
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

var taskAcceptedList = function() {

    var tasklistInit = function() {
    	$("title").html("已接收的任务 | 任务中心 | AnyoneHelps");
    	$(".nav2").addClass("active open");
		$(".nav23").addClass("active open");

		$(".task-state").click(function(){
			searchCondition.state = $(this).data("state");
			searchCondition.pageIndex = 1;
			loadDemandList();
		});
		
		var view = $(":hidden[name='view']").val();
		if(view==null||view==""){
			loadDemandList();
		}else{
			$("[href='#"+view+"']").parent().click();
			$("[href='#"+view+"']").parent().siblings().removeClass("active");
			$("[href='#"+view+"']").parent().addClass("active");
		}
		
    }
    
    var searchCondition = {
    	key : "",
    	state : "2",
    	pageIndex : 1,
    };
    
    function loadDemandList() {
    	var states = [];
    	if(searchCondition.state == 0){
    		states.push(0);
    		states.push(1);
    		states.push(2);
    		states.push(3);
    		states.push(4);
    		states.push(5);
    		states.push(6);
    		states.push(7);
    		states.push(8);
    	}else if(searchCondition.state == 1){
    		states.push(0);
    		states.push(1);
    	}else if(searchCondition.state == 2){
    		states.push(2);
    		states.push(3);
    		states.push(5);
    		states.push(6);
    		states.push(7);
    	}else if(searchCondition.state == 3){
    		states.push(4);
    		states.push(8);
    	}
    	
    	$.ajax({
    		type:"get",
    		url:"/demand/receive_search",
    		data:$.param({
    			state : states,
    			pageIndex : searchCondition.pageIndex
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
    	console.log(list)
    	if(list!=null){
    		$('.task-list').html($('#task-item-tmpl').render({dataList: list}));
    		$('.task-list').find(".zoom").click(function(){
    			if($(this).hasClass("up")){
    				$(this).removeClass("up");
    				$(this).html("收起任务");
    				$(this).parent().parent().parent().parent().parent().find(">.content").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.achieve").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.secret-achieve").removeClass("hide");   //haokun added 2017/03/06 私密成果要求
    				$(this).parent().parent().parent().parent().parent().find(">.enclosure").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.specialty").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.specialty").next().removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.achieve-receive").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.achieve-receive").next().removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.evaluate").removeClass("hide"); 
    				$(this).parent().parent().parent().parent().parent().find(">.evaluate").next().removeClass("hide"); 
    				$(this).parent().parent().parent().parent().parent().find(">.arbitration").removeClass("hide"); 
    				$(this).parent().parent().parent().parent().parent().find(">.arbitration").next().removeClass("hide"); 
    				
    			}else{
    				$(this).addClass("up");
    				$(this).html("展开任务");
    				$(this).parent().parent().parent().parent().parent().find(">.content").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.achieve").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.secret-achieve").addClass("hide");   //haokun added 2017/03/06 私密成果要求
    				$(this).parent().parent().parent().parent().parent().find(">.enclosure").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.specialty").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.specialty").next().addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.achieve-receive").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.achieve-receive").next().addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.evaluate").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.evaluate").next().addClass("hide");
    				
    				$(this).parent().parent().parent().parent().parent().find(">.arbitration").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(">.arbitration").next().addClass("hide");
    			}
    		});
			$(".rating").rating({
				starCaptions: {"0.5": 'Very Poor',1: 'Very Poor', "1.5": 'Poor', 2: 'Poor', "2.5": 'Ok', 3: 'Ok', "3.5": 'Good', 4: 'Good', "4.5": 'Very Good', 5: 'Very Good'},
				starCaptionClasses: {"0.5": 'text-danger',1: 'text-danger',"1.5": 'text-warning', 2: 'text-warning',"2.5": 'text-info', 3: 'text-info',"3.5": 'text-primary', 4: 'text-primary',"4.5": 'text-success', 5: 'text-success'}
			});
    	}else{
    		$('.task-list').html("<div class='no-data'>无接收的任务数据，快去接收任务吧~~</div>");
    	}
    	
    }
    function afterLoadDemandList() {
    	
    }
    function showDemandLink(pageSplit) {
    	if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$(".search-pagination").html('<ul class="pagination" id="pageSplit"></ul>');  
    		$("#pageSplit").twbsPagination({
                totalPages: pageCount,
                startPage: pageNow,  
                first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
                onPageClick: function (event, page) {
                	if(page!=pageNow){
                		searchCondition.pageIndex = page;
                		loadDemandList();
        		    }
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

jQuery(document).ready(function() {
	taskAcceptedList.init();
	doc = $(window.document);
	
	doc.on('click','.task-cancel',function(){
		var me = $(this);
		var id = $(this).data().id;
		$.ajax({
			url: '/demand/receive_delete',
			type:'post',
			dataType: 'json',
			data:$.param({
				"demandId" : id
			}, true),
			success: function(data){
				if(data.code == 200){
					$.successMsg("取消成功");
					me.parent().parent().parent().parent().parent().remove();
				}else{
					$.errorMsg(data.message);
				}
			}
		});
	}).on('click', '.task-start', function(){
		var taskId = $(this).data("id"); 
		var me = $(this);
		$.ajax({
			url: '/demand/start',
			type:'post',
			dataType: 'json',
			data:$.param({
				"demandId" : taskId
			}, true),
			success: function(result){
				if(result.code == 200){
					$.successMsg("开始任务成功,请按要求完成任务");
					me.parent().parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:3}));
					me.parent().parent().find(".task-complete").parent().removeClass("hide");
					me.parent().parent().find(".task-refuse").parent().addClass("hide");
					me.parent().remove();
				}else{
					$.errorMsg(result.message);
				}
			},
			error: function(result){
				$.errorMsg("网络错误");
			}
		});
	})
	//haokun added 2017/03/10 start 增加接单人任务匹配后能够拒绝任务
	.on('click', '.task-refuse', function(){
		var taskId = $(this).data("id"); 
		var me = $(this);
		$.ajax({
			url: '/demand/refuse',
			type:'post',
			dataType: 'json',
			data:$.param({
				"demandId" : taskId
			}, true),
			success: function(result){
				if(result.code == 200){
					$.successMsg("拒绝任务成功");
					location.reload();  //haokun added 2017/03/14 点击后刷新该页面
				}else{
					$.errorMsg(result.message);
				}
			}
		});
	})
    //haokun added 2017/03/10 end 增加接单人任务匹配后能够拒绝任务
	.on('click', '.task-pay', function(){
		var meThis = this;
		var taskId = $(this).data("id");
		var dlg = dialog({
			align:'top left',
			skin:'task-pay-dlg',
			/*width : 720,*/
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/receive_get_one",
		    		data:{
		    			id:taskId
		    		},
		    		success:function(response){
		    			var code = response.code;
		    			var data = response.data;
		    			console.log(data);
		    			if ("200" == code) {
		    				if(data!=null){
		    					me.content($('#task-pay-dlg').render({task:data}));
		    				}
		    				
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".show-arbitration").click(function(){
		    		    		var arbitration = $(this).parent().parent().find(".arbitration").html($('#task-arbitration-item').render({task:data}));
		    		    		$(arbitration).find(':radio').on('ifChecked', function(event){
		    		    			var totalAmount = Number(data.receiveDemands[0].amount);
		    		   			 	$.each(data.da, function(i,item){ 
		    		   			 		if(item.state==1){
		    		   			 			var amount = Number( item.amount);
		    		   			 			if(!isNaN(amount))
		    		   			 				totalAmount += amount;
		    		   			 		}
		    		   			 	});  
		    		   			 	$(".my-pay-amount").html(totalAmount*Number($(this).val())/100);
		    		   			 	
			    		    	});
		    		    		$(arbitration).find(':radio').iCheck({
			    		    		checkboxClass: 'icheckbox_flat-blue',
			    		    		radioClass: 'iradio_flat-blue',
			    		    		increaseArea: '20%', // optional
			    		    		check:function(){
			    		    			alert('Well done, Sir');
			    		    		}
			    		    	});
		    		    		
		    		    		$(arbitration).find(':file[name="files[]"]').fileupload({
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
			    			    	            	if( obj.find('.upload-file-list').children().length > 4){
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
		    		    		$(this).parent().addClass("hide");
		    		    		$(".cancel-arbitration").click(function(){
		    		    			$(this).parent().parent().parent().find(".operate").removeClass("hide");
		    		    			$(this).parent().parent().html("");
		    		    		})
		    		    		$(".submit-arbitration").click(function(){
			    		    		var arbitration = $(this).parent().parent();
			    		    		var refutePercent = "";
			    		    		var refuteReason = "";
			    		    		var refuteReasonUrl1 = "";
			    		    		var refuteReasonUrl2 = "";
			    		    		var refuteReasonUrl3 = "";
			    		    		var refuteReasonUrl4 = "";
			    		    		var refuteReasonUrl5 = "";
			    		    		var refuteReasonUrl1Name = "";
			    		    		var refuteReasonUrl2Name = "";
			    		    		var refuteReasonUrl3Name = "";
			    		    		var refuteReasonUrl4Name = "";
			    		    		var refuteReasonUrl5Name = "";
			    		    		if(arbitration.length){
			    		    			arbitration.each(function(index, item){
			    		    				refutePercent = $( this ).find(":radio:checked").val();
			    		    				refuteReason = $( this ).find(".arbitration-reason").val();
			    		    				
			    		    				$( this ).find(".upload-file-item").each(function(i,item){
					    						if(i<5){
					    							if(i==0){
					    								refuteReasonUrl1 = $( item ).data("id");
					    								refuteReasonUrl1Name = $( item ).data("filename");
					    							}else if(i==1){
					    								refuteReasonUrl2 = $( item ).data("id");
					    								refuteReasonUrl2Name = $( item ).data("filename");
					    							}else if(i==2){
					    								refuteReasonUrl3 = $( item ).data("id");
					    								refuteReasonUrl3Name = $( item ).data("filename");
					    							}else if(i==3){
					    								refuteReasonUrl4 = $( item ).data("id");
					    								refuteReasonUrl4Name = $( item ).data("filename");
					    							}else if(i==4){
					    								refuteReasonUrl5 = $( item ).data("id");
					    								refuteReasonUrl5Name = $( item ).data("filename");
					    							}
					    						}
					    					});
			    		    			});
			    		    		
			    		    			$.ajax({
			    		    				url: "/demand/pay_oppose",
			    		    				type:'post',
			    		    				dataType: 'json',
			    		    				data:$.param({
			    		    					demandId : taskId,
			    		    					refutePercent : refutePercent,
						    		    		refuteReason : refuteReason,
						    		    		refuteReasonUrl1 : refuteReasonUrl1,
						    		    		refuteReasonUrl2 : refuteReasonUrl2,
						    		    		refuteReasonUrl3 : refuteReasonUrl3,
						    		    		refuteReasonUrl4 : refuteReasonUrl4,
						    		    		refuteReasonUrl5 : refuteReasonUrl5,
						    		    		refuteReasonUrl1Name : refuteReasonUrl1Name,
						    		    		refuteReasonUrl2Name : refuteReasonUrl2Name,
						    		    		refuteReasonUrl3Name : refuteReasonUrl3Name,
						    		    		refuteReasonUrl4Name : refuteReasonUrl4Name,
						    		    		refuteReasonUrl5Name : refuteReasonUrl5Name
			    		    				}, true),
			    		    				success: function(result){
			    		    					if(result.code == 200){
			    		    						$.successMsg("提交成功！");
			    		    						$(meThis).html("查看仲裁");
			    		    						dlg.close().remove();
			    		    						$(meThis).parent().parent().find(".task-evaluate").click();
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
		    		    	})
		    				$(".task-argee-pay").click(function(){
		    					taskId = $(this).data("id");
		    					$.ajax({
		    			    		type:"post",
		    			    		url:"/demand/pay_agree",
		    			    		data:$.param({
		    			    			demandId : taskId
		    			    		},true),
		    			    		success:function(response){
		    			    			var code = response.code;
		    			    			if ("200" == code) {
		    			    				$.successMsg("同意收款成功，任务结束！");
		    			    				dlg.close().remove();
		    			    				$( meThis ).removeClass("task-pay");
		    			    				$( meThis ).addClass("task-evaluate");
		    			    				$( meThis ).html("评价");
		    			    				$( meThis ).click(); //模拟点击
		    			    			} else if ("607" == code) {
		    			    				$.errorMsg("您尚未登录或登录已失效！");
		    			    				logout();
		    			    			} else{
		    			    				$.errorMsg(result.message);
		    			    			}
		    			    			
		    			    		}
		    			    	});
		    				})
		    			} else if ("607" == code) {
		    				$.errorMsg("您尚未登录或登录已失效！");
		    				logout();
		    			} else{
		    				$.errorMsg(response.message);
		    			}
		    			return false;
		    		}
		    	});
				
			},
			onclose: function () {
		    }
		}).show(meThis);
	}).on('click','.remark',function(){
		var me = $(this);
		var id = $(this).data().id;
		var remark = $(this).attr("remark");
		dialog({
			align:'bottom right',
			skin:'remark-dlg',
			/*width : 300,*/
			quickClose : true,
			onshow : function() {
				this.content('<div><textarea type="text" rows="4" placeholder="请输入您的备忘录" class="form-control radius0 remark-desc">'+remark+'</textarea></div>');
			},
			onclose: function () {
				var remarkDesc = $(".remark-dlg").find(".remark-desc").val();
				$.ajax({
					url: '/demand/receive_modify_remark',
					type:'post',
					dataType: 'json',
					data:$.param({
						"id" : id,
						"remark" : remarkDesc
					}, true),
					success: function(result){
						if(result.code == 200){
							$.successMsg("修改成功!");
							me.attr("remark",remarkDesc);
						}else{
							$.errorMsg(result.message);
						}
					},
					error: function(result){
						$.errorMsg("无网络连接！请重试");
					}
				});
		    }
		}).show(this);
	}).on('click','.modify-amount',function(){
		var me = $(this);
		var id = $(this).data().id;
		var amount = $(this).attr("amount");
		if(amount==null||amount==""){
			amount = $(this).attr("taskamount")
		}
		dialog({
			align:'bottom right',
			skin:'amount-dlg',
			/*width : 300,*/
			quickClose : true,
			onshow : function() {
				this.content('<div><input type="number" placeholder="请输入您的最新报价" class="form-control radius0 amount-desc" value="'+amount+'"/></div>');
			},
			onclose: function () {
				var amountDesc = $(".amount-dlg").find(".amount-desc").val();
				$.ajax({
					url: '/demand/receive_modify_amount',
					type:'post',
					dataType: 'json',
					data:$.param({
						"id" : id,
						"amount" : amountDesc
					}, true),
					success: function(result){
						if(result.code == 200){
							$.successMsg("修改成功!");
							me.attr("amount",amountDesc);
							me.parent().parent().parent().parent().parent().find(".my-amount").next().html("$"+amountDesc);
						}else{
							$.errorMsg(result.message);
						}
					},
					error: function(result){
						$.errorMsg("无网络连接！请重试");
					}
				});
		    }
		}).show(this);
	}).on('click', '.task-evaluate', function(){
		var meThis = $(this);
		var taskId = $(this).data("id"); 
		var dlg = dialog({
			align:'top left',
			skin:'task-evaluate-dlg',
			/*width : 720,*/
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/demand/get_one_by_writer",
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
		    		    		var evaluateUserId = $(this).parent().parent().find(".evaluate-item").data("userid");
		    		    		var description = $(this).parent().parent().find(".evaluate-item .evaluate").val();
		    		    		var evaluate = $(this).parent().parent().find(".evaluate-item .rating").val();
		    		    		
		    		    		$.ajax({
		    		    			url: '/demand/evaluate',
		    		    			type:'post',
		    		    			dataType: 'json',
		    		    			data:$.param({
		    		    				evaluateUserId : evaluateUserId,
		    		    				evaluate : evaluate,
		    		    				description : description,
		    		    				demandId : taskId
		    		    			}, true),
		    		    			success: function(result){
		    		    				if(result.code == 200){
		    		    					$.successMsg("评价成功！");
		    		    					var display = "";
			    		    				if(meThis.parent().parent().find(".zoom").hasClass("up")){
			    		    					display = "hide";
			    		    				}
			    		    				meThis.parent().parent().parent().parent().prev().before($('#evaluate-desc').render({display:display,description:description,evaluate:evaluate}));
		    		    					dlg.close().remove();
		    		    					meThis.parent().remove();
		    		    					$(".rating").rating({
			    		    					starCaptions: {"0.5": 'Very Poor',1: 'Very Poor', "1.5": 'Poor', 2: 'Poor', "2.5": 'Ok', 3: 'Ok', "3.5": 'Good', 4: 'Good', "4.5": 'Very Good', 5: 'Very Good'},
			    		    					starCaptionClasses: {"0.5": 'text-danger',1: 'text-danger',"1.5": 'text-warning', 2: 'text-warning',"2.5": 'text-info', 3: 'text-info',"3.5": 'text-primary', 4: 'text-primary',"4.5": 'text-success', 5: 'text-success'}
			    		    				});
		    		    				}else{
		    		    					$.errorMsg(result.message);
		    		    				}
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
	}).on('click', '.task-complete', function(){
		var meObj = $(this);
		var taskId = $(this).data("id"); 
		var dlg = dialog({
			align:'top left',
			skin:'task-complete-dlg',
			/*width : 720,*/
			quickClose : true,
			onshow : function() {
				var me = this;
				me.content($('#task-complete-dlg').render({taskId:taskId}));
				$(".wclose,.task-complete-cancel").click(function(){
		    		dlg.close().remove();
		    		return false;
		    	})
		    	$(".task-complete-list").find(':file[name="files[]"]').fileupload({
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
			    	            	if( obj.find('.result-file-list').children().length > 4){
			    	            		obj.find(".result-upload").hide();
			    	            		obj.find('.result-format').hide();
			    	            		return;
			    	            	}
			    	            	obj.find('.result-file-list').append($('#result-upload-file').render(file));
				        			if(obj.find('.result-file-list').children().length>=5){
				        				obj.find(".result-upload").hide();
				        				obj.find('.progress').hide();
				        				obj.find('.result-format').hide();
				        			}
				        			obj.find('.result-file-delete').unbind("click"); //移除click
				        			obj.find('.result-file-delete').click(function(){
			        					$(this).parent().parent().remove();
			        					obj.find(".result-upload").show();
			        					obj.find('.result-format').show();
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
		    	$(".task-complete-ok").click(function(){
		    		var resultDesc = $(".task-complete-list").find('.result-desc').val();
		    		
		    		/*  haokun delete 2017/03/01 不需要成果描述也能提交
		    		if(resultDesc==""||resultDesc==null){
		    			$.errorMsg("请输入成果描述");
		    			return false;
		    		}
					*/
		    		var taskId = $(this).data("taskid"); 
		    		
		    		var resultUrl1 = "";
		    		var resultUrl2 = "";
		    		var resultUrl3 = "";
		    		var resultUrl4 = "";
		    		var resultUrl5 = "";
		    		var resultUrl1Name = "";
		    		var resultUrl2Name = "";
		    		var resultUrl3Name = "";
		    		var resultUrl4Name = "";
		    		var resultUrl5Name = "";
		    		var resultFileObj = $(".task-complete-list .result-file-list .result-file-item");
		    		if(resultFileObj.length){
		    			resultFileObj.each(function(i, item){
	    					if(i<5){
	    						if(i==0){
	    							resultUrl1 = $( item ).data("id");
	    							resultUrl1Name = $( item ).data("filename");
	    						}else if(i==1){
	    							resultUrl2 = $( item ).data("id");
	    							resultUrl2Name = $( item ).data("filename");
	    						}else if(i==2){
	    							resultUrl3 = $( item ).data("id");
	    							resultUrl3Name = $( item ).data("filename");
	    						}else if(i==3){
	    							resultUrl4 = $( item ).data("id");
	    							resultUrl4Name = $( item ).data("filename");
	    						}else if(i==4){
	    							resultUrl5 = $( item ).data("id");
	    							resultUrl5Name = $( item ).data("filename");
	    						}
	    					}
	    				});
		    		}
		    		
		    		$.ajax({
		    			url: '/demand/finish_by_receiver',
		    			type:'post',
		    			dataType: 'json',
		    			data:$.param({
	    					demandId : taskId,
	    		    		resultDesc : resultDesc,
	    		    		resultUrl1 : resultUrl1,
	    		    		resultUrl2 : resultUrl2,
	    		    		resultUrl3 : resultUrl3,
	    		    		resultUrl4 : resultUrl4,
	    		    		resultUrl5 : resultUrl5,
	    		    		resultUrl1Name : resultUrl1Name,
	    		    		resultUrl2Name : resultUrl2Name,
	    		    		resultUrl3Name : resultUrl3Name,
	    		    		resultUrl4Name : resultUrl4Name,
	    		    		resultUrl5Name : resultUrl5Name
	    				}, true),
		    			success: function(result){
		    				if(result.code == 200){
		    					$.successMsg("任务提交成果成功,请等待对方付款");
		    					meObj.parent().parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:5}));
		    					meObj.parent().remove();
		    		    		dlg.close().remove();
		    		    		
		    		    		location.reload();  //haokun added 2017/03/14 点击后刷新该页面
		    				}else{
		    					$.errorMsg(result.message);
		    				}
		    			}
		    		});
		    	})
			},
			onclose: function () {
		    }
		}).show(this);
	})/*.on('click', '.show-attach', function(){
		if($(this).hasClass("attach-hide")){
			$(this).removeClass("attach-hide");
			$(this).html("显示附加任务");
			$(this).parent().parent().find(".attach-list").addClass("hide");
			
		}else{
			$(this).addClass("attach-hide");
			$(this).html("隐藏附加任务");
			$(this).parent().parent().find(".attach-list").removeClass("hide");
		}
		
	})*/.on('click', '.attach-accepte', function(){
		var obj = $(this); 
		var id = $(this).data("id");
		$.ajax({
			url: '/demand_attach/modify_state',
			type:'post',
			dataType: 'json',
			data:$.param({
				"id" : id,
				"state" : 1
			}, true),
			success: function(result){
				if(result.code == 200){
					$.successMsg("附加任务接受成功!");
					obj.next().remove();
					obj.after('<span class="attach-state">已接受<span>');
					obj.remove();
				}else{
					$.errorMsg(result.message);
				}
			}
		});
		
	}).on('click', '.attach-reject', function(){
		var obj = $(this); 
		var id = $(this).data("id");
		$.ajax({
			url: '/demand_attach/modify_state',
			type:'post',
			dataType: 'json',
			data:$.param({
				"id" : id,
				"state" : 2
			}, true),
			success: function(result){
				if(result.code == 200){
					$.successMsg("附加任务拒绝成功!");
					obj.prev().remove();
					obj.after('<span class="attach-state">已拒绝<span>');
					obj.remove();
				}else{
					$.errorMsg(result.message);
				}
			}
		});
		
	}).on('click', '.icon-add', function() {
		$(this).parent().parent().parent().find(".achieve").removeClass("hide");
		$(this).parent().parent().parent().find(".content").removeClass("hide");
		$(this).removeClass("icon-add");
		$(this).addClass("icon-minus");
	}).on('click', '.icon-minus', function() {
		$(this).parent().parent().parent().find(".achieve").addClass("hide");
		$(this).parent().parent().parent().find(".content").addClass("hide");
		$(this).removeClass("icon-minus");
		$(this).addClass("icon-add");
	})
	
});