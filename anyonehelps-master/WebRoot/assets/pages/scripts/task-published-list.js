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
				 if(item.state==1){
					 var amount = Number( item.amount );
					 if(!isNaN(amount))
						 totalAmount += amount;
				 }
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
	     "creditPayAmountFormat":function(amount){
	    	 //return (parseFloat(amount).toFixed(2)/0.971+0.3).toFixed(2);   haokun deleted 2017/03/03
			 return ((parseFloat(amount).toFixed(2)/1+0.3)/0.971).toFixed(2);    //haokun added 2017/03/09 这里必须要/1，否则结果出现NaN
	     }
		 
 });
 
 $.views.helpers({
	 "noEvaluate":function(receiveDemands){
		 var noEvaluate = true;
    	 if(receiveDemands == null){
    		 noEvaluate = true;
    	 }else{
    		 $.each(receiveDemands, function(i, item) {  
    			if (item.state==1 && item.evaluateState == 1) { 
    				noEvaluate = false;
 		        }; 
    		});  
    	 }
    	 return noEvaluate;
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
    		url:"/demand/search",
    		data:$.param({
    			state : states,
    			key : searchCondition.key,
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
    	
    	if(list!=null){
    		console.log(list);
    		$('.task-list').html($('#task-item-tmpl').render({dataList: list}));
    		
    		$('.task-list').find(".zoom").click(function(){
    			if($(this).hasClass("up")){
    				$(this).removeClass("up");
    				$(this).html("收起任务");
    				//$(this).children(":first").removeClass("glyphicon-chevron-left");
    				//$(this).children(":first").addClass("glyphicon-chevron-down");
    				$(this).parent().parent().parent().find(">.content").removeClass("hide");
    				$(this).parent().parent().parent().find(">.achieve").removeClass("hide");
    				$(this).parent().parent().parent().find(">.secret-achieve").removeClass("hide");//haokun added 2017/03/06 私密成果要求
    				$(this).parent().parent().parent().find(">.enclosure").removeClass("hide");
    				$(this).parent().parent().parent().find(">.specialty").removeClass("hide");
    				$(this).parent().parent().parent().find(">.specialty").next().removeClass("hide");
    				$(this).parent().parent().parent().find(">.achieve-receive").removeClass("hide");
    				$(this).parent().parent().parent().find(">.achieve-receive").next().removeClass("hide");
    				$(this).parent().parent().parent().find(">.evaluate").removeClass("hide"); 
    				$(this).parent().parent().parent().find(">.evaluate").next().removeClass("hide"); 
    				$(this).parent().parent().parent().find(">.arbitration").removeClass("hide"); 
    				$(this).parent().parent().parent().find(">.arbitration").next().removeClass("hide"); 
    				
    			}else{
    				$(this).addClass("up");
    				$(this).html("展开任务");
    				//$(this).children(":first").addClass("glyphicon-chevron-left");
    				//$(this).children(":first").removeClass("glyphicon-chevron-down");
    				$(this).parent().parent().parent().find(">.content").addClass("hide");
    				$(this).parent().parent().parent().find(">.achieve").addClass("hide");
    				$(this).parent().parent().parent().find(">.secret-achieve").addClass("hide");//haokun added 2017/03/06 私密成果要求
    				$(this).parent().parent().parent().find(">.enclosure").addClass("hide");
    				$(this).parent().parent().parent().find(">.specialty").addClass("hide");
    				$(this).parent().parent().parent().find(">.specialty").next().addClass("hide");
    				$(this).parent().parent().parent().find(">.achieve-receive").addClass("hide");
    				$(this).parent().parent().parent().find(">.achieve-receive").next().addClass("hide");
    				$(this).parent().parent().parent().find(">.evaluate").addClass("hide");
    				$(this).parent().parent().parent().find(">.evaluate").next().addClass("hide");
    				$(this).parent().parent().parent().find(">.arbitration").addClass("hide");
    				$(this).parent().parent().parent().find(">.arbitration").next().addClass("hide");
    				
    			}
    			
    		});
    		function sendFile(file) {  
    		    data = new FormData();  
    		    data.append("file", file);  
    		    $.ajax({  
    		        data: data,  
    		        type: "POST",  
    		        url: "/summernote/upload",  
    		        cache: false,  
    		        contentType: false,  
    		        processData: false,  
    		        success: function(response) {  
    		        	$('.attach-add-content').summernote('insertImage', response.data.saveFileName, response.data.fileName); 
    		        }  
    		    });  
    		} 
    		$('.task-attach-add').click(function(){
    			var open = $(this).parent().parent().data("open");
    			$(this).parent().parent().parent().find(".attach-add").append($('#attach-add-item').render({open: open}))
    			$('.attach-add-content').summernote({
        			height: 300,
        			onImageUpload: function(files) {  
        				sendFile(files[0]);  
        	        }
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
    		$(".rating").rating({
				starCaptions: {"0.5": 'Very Poor',1: 'Very Poor', "1.5": 'Poor', 2: 'Poor', "2.5": 'Ok', 3: 'Ok', "3.5": 'Good', 4: 'Good', "4.5": 'Very Good', 5: 'Very Good'},
				starCaptionClasses: {"0.5": 'text-danger',1: 'text-danger',"1.5": 'text-warning', 2: 'text-warning',"2.5": 'text-info', 3: 'text-info',"3.5": 'text-primary', 4: 'text-primary',"4.5": 'text-success', 5: 'text-success'}
			});
    		
    	}else{
    		$('.task-list').html("<div class='no-data'>暂无发布的任务，快去发布发任务吧~~</div>");
    	}
    }
    function afterLoadDemandList() {
    	
    }
    function showDemandLink(pageSplit) {
    	console.log(pageSplit);
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

var g_rdig = null;

jQuery(document).ready(function() {
	taskPublishedList.init();
	doc = $(window.document);
	doc.on('click', '.task-edit', function(){
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
					me.parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:4}));
					me.parent().remove();
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
					me.parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:4}));
				
					var dlg = dialog({
						align:'top left',
						skin:'task-pay-dlg',
						/*width : 720,*/
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
					    		    						$(me).parent().find(".task-evaluate").click();
					    		    						if(payPercent=="100"||payPercent=="100.00"){
					    		    							$(meThis).parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:8}));
					    		    						}else{
					    		    							$(meThis).parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:6}));
					    		    						}
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
			/*width : 720,*/
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
		    		    						if(payPercent==100||payPercent==100.00){
		    		    							$(meThis).html("评价");
		    		    							$(meThis).addClass("task-evaluate");
		    		    							$(meThis).removeClass("task-pay");
		    		    						}
		    		    						$(meThis).parent().find(".task-evaluate").click();
		    		    						
		    		    						if(payPercent=="100"||payPercent=="100.00"){
		    		    							$(meThis).parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:8}));
		    		    						}else{
		    		    							$(meThis).parent().parent().parent().find(".task-state-desc").html($('#task-state-tmpl').render({state:6}));
		    		    						}
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
	})
	//haokun added 2017/03/14 start 功能js---增加了一个拒绝支付的按钮；避免某些情况下用户不敢去点击“支付”这个按钮当他不想支付的时候
	.on('click', '.task-refusepay', function(){
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
		    					me.content($('#task-refusepay-dlg').render({task:response.data}));
		    				}
		    				
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	
							/*
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
							*/
		    				//$(".refusepercentage").on('ifChecked', function(event){
		    				//	if($(this).val()!="100"){
		    				//		$(this).parent().parent().next().html($('#task-pay-reason').render());
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
		    				//	}else{
		    				//		$(this).parent().parent().next().html("");
		    				//	}
		    				//});
		    		    	$(".task-refusepay-cancel").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	});
		    		    	$(".task-refusepay-ok").click(function(){
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
		    		    				payPercent = "0";
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
		    		    						$.successMsg("拒绝支付成功！");
		    		    						dlg.close().remove();
		    		    						if(payPercent==100||payPercent==100.00){
		    		    							$(meThis).html("评价");
		    		    							$(meThis).addClass("task-evaluate");
		    		    							$(meThis).removeClass("task-pay");
		    		    						}
		    		    						$(meThis).parent().find(".task-evaluate").click();
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
	})
	//haokun added 2017/03/14 end 功能js---增加了一个拒绝支付的按钮；避免某些情况下用户不敢去点击“支付”这个按钮当他不想支付的时候
	.on('click', '.task-arbitration', function(){
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
		    			    	            	if( obj.find('.upload-file-list').children().length > 4){
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
		var meThis = this;
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
		    		    		var evaluateUserId = $(this).parent().parent().find(".evaluate-item").data("userid");
		    		    		var evaluate = $( this ).parent().parent().find(".evaluate-item .evaluate-star").val();
		    		    		var description = $( this ).parent().parent().find(".evaluate-item .evaluate").val();
		    		    		var quality = $( this ).parent().parent().find(".evaluate-item .quality-star").val();
		    		    		var punctual = $( this ).parent().parent().find(".evaluate-item .punctual-star").val();
		    		    		
		    		    		$.ajax({
		    		    			url: '/demand/evaluate',
		    		    			type:'post',
		    		    			dataType: 'json',
		    		    			data:$.param({
			    		    			evaluateUserId : evaluateUserId,
			    		    			evaluate : evaluate,
			    		    			description : description,
			    		    			demandId : taskId,
			    		    			quality:quality,
			    		    			punctual:punctual
			    		    		}, true),
		    		    			success: function(result){
		    		    				if(result.code == 200){
		    		    					$.successMsg("评价成功！");
		    		    					var display = "";
			    		    				if($(meThis).parent().find(".zoom").hasClass("up")){
			    		    					display = "hide";
			    		    				}
			    		    				$(meThis).parent().parent().before($('#evaluate-desc').render({display:display,description:description,evaluate:evaluate,quality:quality,punctual:punctual}))
			    		    				$(".rating").rating({
			    		    					starCaptions: {"0.5": 'Very Poor',1: 'Very Poor', "1.5": 'Poor', 2: 'Poor', "2.5": 'Ok', 3: 'Ok', "3.5": 'Good', 4: 'Good', "4.5": 'Very Good', 5: 'Very Good'},
			    		    					starCaptionClasses: {"0.5": 'text-danger',1: 'text-danger',"1.5": 'text-warning', 2: 'text-warning',"2.5": 'text-info', 3: 'text-info',"3.5": 'text-primary', 4: 'text-primary',"4.5": 'text-success', 5: 'text-success'}
			    		    				});
		    		    					dlg.close().remove();
		    		    					$(meThis).remove();
		    		    						
		    		    				}else{
		    		    					$.errorMsg(result.message);
		    		    				}
		    		    			},
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
		var obj = $(this);
		var taskId = $(this).data("id"); 
		var dlg = dialog({
			align:'top left',
			skin:'task-select-dlg',
			/*width : 720,*/ 
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
		    			var payState = "1";
		    			if(response.data!=null){
		    				payState = response.data.payState;
	    				}
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
		    		    		var amount = $(this).parent().parent().find(":radio[name='userCheck']").data("amount");
		    		    		var id = "";
		    		    		//var str = "";
		    		    		if(userCheck.length){
		    		    			userCheck.each(function(i, item){
		    		    				if($(this).is(":checked")){
		    		    					id = $(item).data().rdid;
		    		    					
		    		    					//str +="<li>";
		    		    					//str +='<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="'+$(item).data().id+'" data-userid="'+$(item).data().id+'" data-rdid="'+$(item).data().rdid+'"><img class="img-circle img-50 opacity08" src="'+$(item).data().pic+'"></a>';
		    		    					//str +="</li>";
		    		    					return false;
		    		    				}
		    		    				
		    		    			});
		    		    		}
		    		    		
		    		    		if(id==""){
		    		    			$.successMsg("您没有选中接单人！！");
		    		    			return false;
		    		    		}
		    		    		
		    		    		if(payState=="0"){
		    		    			var d = dialog({
		    		    				align:'top left',
		    		    				skin:'tip-dlg',
		    		    				/*width : 720,*/
		    		    				quickClose : true,
		    						    onshow : function() {
		    						    	var contentObj = this.content($('#tip-dlg').render({taskId:taskId, rdId:id, amount:amount}));
		    						    	$(".tip-dlg .wclose").click(function(){
		    			    		    		d.close().remove();
		    			    		    		return false;
		    			    		    	})
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
		    		    		    						dlg.close().remove();
		    		    		    						$("[href='#tab_1_2']").parent().siblings().removeClass("active");
		    		    		    						$("[href='#tab_1_2']").parent().click();
		    		    		    						$("[href='#tab_1_2']").parent().addClass("active");
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
					    		    		    		    						dlg.close().remove();
					    		    		    		    						$("[href='#tab_1_2']").parent().siblings().removeClass("active");
					    		    		    		    						$("[href='#tab_1_2']").parent().click();
					    		    		    		    						$("[href='#tab_1_2']").parent().addClass("active");
					    		    		    		    						//obj.parent().parent().find(".task-end").removeClass("hide");
					    		    		    		    						//obj.parent().parent().find(".task-edit").addClass("hide");
					    		    		    		    						//obj.parent().parent().find(".task-select").addClass("hide");
			    		    			    				    				}else{
			    		    			    				    					$.errorMsg("anyonehelps尚未收到你的付款，稍后收到后，会执行你的任务匹配，请稍等查看");
			    		    			    				    					paypalDlg.close().remove();
					    		    		    		    						d.close().remove();
					    		    		    		    						dlg.close().remove();
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
		    		    			    		    		var payAmount = ((parseFloat(amount).toFixed(2)/1+0.3)/0.971).toFixed(2);    //haokun added 2017/03/09 这里必须要/1，否则结果出现NaN
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
		    		    		    		    						dlg.close().remove();
		    		    		    		    						$("[href='#tab_1_2']").parent().siblings().removeClass("active");
		    		    		    		    						$("[href='#tab_1_2']").parent().click();
		    		    		    		    						$("[href='#tab_1_2']").parent().addClass("active");
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
		    						    }
		    						});
		    						d.showModal();
		    		    		}else{
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
		    		    						dlg.close().remove();
		    		    						$("[href='#tab_1_2']").parent().siblings().removeClass("active");
		    		    						$("[href='#tab_1_2']").parent().click();
		    		    						$("[href='#tab_1_2']").parent().addClass("active");
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
				this.content('<div><textarea type="text" rows="4" placeholder="请输入您的备忘录" class="form-control radius0 remark-desc">'+remark+'</textarea></div>');
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
							$.successMsg("提交成功");
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
    	var content = encodeURI($(this).parent().parent().parent().find('.attach-add-content').code());
    	var achieve = encodeURI($(this).parent().parent().parent().find('.achieve').val());
		var me = $(this).parent().parent().parent();
    	$.ajax({
			type : "post",
			url : "/demand_attach/add",
			data : $.param({
				demandId:demandId,
				content:content,
				achieve:achieve,
				amount:amount,
				expireDate:expireDate
			},true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					$.successMsg("提交成功");
					if(response.data!=null){
						me.parent().parent().find(".attach-list").append($('#attach-item').render({attach:response.data}));
					}
					me.remove();
				}else if ("15005"==code){
					$.errorMsg(response.message);
					//window.open("/dashboard/Finance/recharge.jsp");
				} else {
					$.errorMsg(response.message);
				}
			}
		});
	}).on('click', '.attach-add-cancel', function(){
		$(this).parent().parent().parent().remove();
	}).on('click', '.attach-delete', function(){
		var id = $(this).data().id;
		var me = $(this);
		
		var d = dialog({
		    title: '提示',
		    /*width:'400px',*/
		    skin: 'tip-dlg',
		    content: '确认要删除附加任务吗？',
		    okValue: '确定',
		    ok: function () {
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
							me.parent().parent().parent().next().remove();
							me.parent().parent().parent().remove();
						} else {
							$.errorMsg(response.message);
						}
					},
				});
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		});
		d.showModal();
	}).on("click",".invite-user",function(){
		var demandId = $(this).data("id");
		var dlg = dialog({
			title : '邀请关注用户来接任务',
			skin:"dlg-radius0 dlg-invite-user",
			/*width : 720,*/
			padding:"0px",
			quickClose : true,
			onshow : function() {
				var me = this;
				
				$.ajax({
					type : "get",
					url : "/friend/search",
					data : {
						"key" : "",
						"demandId" : demandId,
						"pageSize" : 5,
						"pageIndex" : 1
					},
					success : function(response) {
						
						var code = response.code;
						if (code == "200") {
							var data = response.data;
							if(data!=null){
								me.content($('#friend-tmpl').render({dataList:data.datas,demandId:demandId}));
								showFriendLink(data);
							}else{
								me.content($('#friend-tmpl').render({dataList:null,demandId:demandId}));
								
								$(".invite-user-list").html('<div class="col-md-2 col-sm-2 col-xs-2"></div> <div class="col-md-8 col-sm-8 col-xs-8" style="margin-top: 50px;"><span style="margin-top: 100px;border-top-width: 10px;">没有关注人物,现在去<a target="_blank" href="/user/famousMore.jsp">关注</a></span></div>');
							}
							$(".wclose").click(function(){
		    					dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	$(".search-user-a").click(function(){
		    		    		var key = $(".search-user").val();
		                        loadFriend(key,demandId,"5","1");
		    		    		return false;
		    		    	})

							
						} else if ("607" == code) {
						}
					}
				});
			}
		}).show();
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
	}).on('click', '.invite-btn', function() {
		var me = $(this);
		me.attr("disabled","disabled");
		var id = $(this).data("id");
		var demandId = $(".search-user").data("demandid");
		
		$.ajax({
			type : "post",
			url : "/demand/invite_add",
			data : {
				"friendId" : id,
				"demandId" : demandId
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					$.successMsg("邀请成功，已将任务信息发给对方");
					me.html("已邀请");
					me.attr("disabled","disabled");
					me.css('background-color','rgb(128, 128, 128)');
				} else{
					$.errorMsg(response.message);
					me.attr("disabled", false);
				}
			},
			error : function() {
				me.attr("disabled", false);
			}
		});
	});
	
	function loadFriend(key,demandId,size,pn) {
		$.ajax({
			type : "get",
			url : "/friend/search",
			data : {
				"key" : key,
				"demandId" : demandId,
				"pageSize" : size,
				"pageIndex" : pn
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						console.log(response.data.datas);
						showFriendlist(response.data.datas);
						showFriendLink(response.data);
					}else {
						showFriendlist(null);
						showFriendLink(null);
					}
				} else if ("607" == code) {
				}
			}
		});
	}
	    
	function showFriendlist(list) {
	   	if(list!=null){
	   		$(".invite-user-list").html($('#friend-item-tmpl').render({dataList:list}));
	   	}else{
	   		$(".invite-user-list").html("");
	   	}
	}
	   
	function showFriendLink(pageSplit) {
    	if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$("#friendPageSplit").twbsPagination({
                totalPages: pageCount,
                first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
                onPageClick: function (event, page) {
                	var key = $(".search-user").val();
                	var demandId = $(".search-user").data("demandid");
                    loadFriend(key, demandId, "5", page);
                }
            });
    	}
    }
});