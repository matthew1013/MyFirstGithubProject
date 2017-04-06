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
		 }
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
 


var taskList = function() {
    var tasklistInit = function() {
    	$("title").html("已发布任务 | 任务中心 | AnyoneHelps");
    	$(".nav2").addClass("active open");
		$(".nav22").addClass("active open");
		
		loadDemandList(0)
		$(".task-state").click(function(){
			g_flag = true;
			
			$("select[name='sort']").val("0");
			$(".search-key").val("");
			$("select[name='location']").val("");
			$("select[name='type']").val("");
			
			searchCondition.sortType = $("select[name='sort']").val();
			searchCondition.key = $(".search-key").val();
			searchCondition.nationality = $("select[name='location']").val();
			searchCondition.type = $("select[name='type']").val();
			searchCondition.pageIndex = "1";
			loadDemandList($(this).data("state"));
		});
    }
    
    var searchCondition = {
    	sortType:"0",
    	key : "",
    	nationality : "",
    	type : "",
    	pageIndex : 1,
    	pageSize : 20
    };
    
    $(".search-start").click(function(){
    	g_flag = true;
    	searchCondition.sortType = $("select[name='sort']").val();
		searchCondition.key = $(".search-key").val();
		searchCondition.nationality = $("select[name='location']").val();
		searchCondition.type = $("select[name='type']").val();
		searchCondition.pageIndex = "1";
		var state = $(".task-state.active").data("state");
		loadDemandList(state);
    })
    
    function loadDemandList(state) {
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
    		states.push(7);
    	}else if(state==3){
    		states.push(8);
    	}
    	$.ajax({
    		type:"get",
    		url:"/admin/demand/search",
    		data:$.param({
    			sortType : searchCondition.sortType,
    			nationality : searchCondition.nationality,
    			type : searchCondition.type,
    			state : states,
    			key : searchCondition.key,
    			pageIndex : searchCondition.pageIndex,
    			pageSize : searchCondition.pageSize
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
    				$(this).children(":first").removeClass("glyphicon-chevron-left");
    				$(this).children(":first").addClass("glyphicon-chevron-down");
    				$(this).parent().parent().parent().parent().parent().find(".content").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".user").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".attach-list").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".achieve-receive").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".pay-reason").removeClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".refute-reason").removeClass("hide");
    				
    				$(this).children(":first").next().html("收起");
    			}else{
    				$(this).addClass("up");
    				$(this).children(":first").addClass("glyphicon-chevron-left");
    				$(this).children(":first").removeClass("glyphicon-chevron-down");
    				$(this).parent().parent().parent().parent().parent().find(".content").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".user").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".attach-list").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".achieve-receive").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".pay-reason").addClass("hide");
    				$(this).parent().parent().parent().parent().parent().find(".refute-reason").addClass("hide");
    				
    				$(this).children(":first").next().html("展开");
    			}
    			
    		});
    		$('.attach-add-content').summernote({
    			height: 300
    		});
    		
    	}else{
    		$('.task-list').html("暂无任务~~");
    		$(".search-pagination").html('<ul class="pagination"></ul>'); 
    	}
    }
    function afterLoadDemandList() {
    	
    }
    //g_page_size g_flag 决定重新分页
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
    		    $(".search-pagination").html('<ul class="pagination"></ul>');  
    		    g_flag = false;
    		}  
    		g_page_size = pageCount;
    		$(".pagination").twbsPagination({
                totalPages: pageCount,
                first: '首页',
                prev: '上一页',
                next: '下一页',
                last: '最后一页',
                onPageClick: function (event, page) {
                	searchCondition.pageIndex = page;
                	var state = $(".task-state.active").data("state");
            		loadDemandList(state);
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
	taskList.init();
	doc = $(window.document);
	doc.on('click','.remark',function(){
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
					url: '/admin/demand/modify_remark',
					type:'post',
					dataType: 'json',
					data:$.param({
						"id" : id,
						"remark" : remarkDesc
					}, true),
					success: function(result){
						if(result.code == 200){
							$.successMsg("备注成功");
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
	}).on('click', '.task-arbitration', function(){
		var meThis = this;
		var taskId = $(this).data("id");
		var dlg = dialog({
			align:'top left',
			skin:'task-arbitration-dlg',
			width : 720,
			quickClose : true,
			onshow : function() {
				var me = this;
				var obj = me.content($('#task-arbitration-dlg').render(taskId));
				$(".wclose,.task-arbitration-cancel").click(function(){
		    		dlg.close().remove();
		    		return false;
		    	})
		    	//var arbitration = $(this).parent().parent().find(".arbitration").html($('#task-arbitration-item').render(taskId));
		    	$(".task-arbitration-list").find(":radio").iCheck({
			    	checkboxClass: 'icheckbox_flat-blue',
			    	radioClass: 'iradio_flat-blue',
			    	increaseArea: '20%', // optional
			    	check:function(){
			    		alert('Well done, Sir');
			    	}
		    	});
				
				$(".task-arbitration-list").find(':file[name="files[]"]').fileupload({
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
			    	            	if( obj.find('.arbitration-file-list').children().length > 5){
			    	            		obj.find(".arbitration-upload").hide();
			    	            		obj.find('.arbitration-format').hide();
			    	            		return;
			    	            	}
			    	            	obj.find('.arbitration-file-list').append($('#arbitration-upload-file').render(file));
				        			if(obj.find('.arbitration-file-list').children().length>=5){
				        				obj.find(".arbitration-upload").hide();
				        				obj.find('.progress').hide();
				        				obj.find('.arbitration-format').hide();
				        			}
				        			obj.find('.arbitration-file-delete').unbind("click"); //移除click
				        			obj.find('.arbitration-file-delete').click(function(){
			        					$(this).parent().parent().remove();
			        					obj.find(".arbitration-upload").show();
			        					obj.find('.arbitration-format').show();
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
				
				$(".task-arbitration-ok").click(function(){
		    		var arbitration = $(this).parent().parent();
		    		var rulePercent = "";
		    		var ruleReason = "";
		    		var ruleReasonUrl1 = "";
		    		var ruleReasonUrl2 = "";
		    		var ruleReasonUrl3 = "";
		    		var ruleReasonUrl4 = "";
		    		var ruleReasonUrl5 = "";
		    		var ruleReasonUrl1Name = "";
		    		var ruleReasonUrl2Name = "";
		    		var ruleReasonUrl3Name = "";
		    		var ruleReasonUrl4Name = "";
		    		var ruleReasonUrl5Name = "";
		    		if(arbitration.length){
		    			arbitration.each(function(index, item){
		    				rulePercent = $( this ).find(":radio:checked").val();
		    				ruleReason = $( this ).find(".arbitration-desc").val();
		    				
		    				$( this ).find(".arbitration-file-item").each(function(i,item){
	    						if(i<5){
	    							if(i==0){
	    								ruleReasonUrl1 = $( item ).data("id");
	    								ruleReasonUrl1Name = $( item ).data("filename");
	    							}else if(i==1){
	    								ruleReasonUrl2 = $( item ).data("id");
	    								ruleReasonUrl2Name = $( item ).data("filename");
	    							}else if(i==2){
	    								ruleReasonUrl3 = $( item ).data("id");
	    								ruleReasonUrl3Name = $( item ).data("filename");
	    							}else if(i==3){
	    								ruleReasonUrl4 = $( item ).data("id");
	    								ruleReasonUrl4Name = $( item ).data("filename");
	    							}else if(i==4){
	    								ruleReasonUrl5 = $( item ).data("id");
	    								ruleReasonUrl5Name = $( item ).data("filename");
	    							}
	    						}
	    					});
		    			});

		    			$.ajax({
		    				url: "/admin/demand/end",
		    				type:'post',
		    				dataType: 'json',
		    				data:$.param({
		    					demandId : taskId,
		    					rulePercent : rulePercent,
		    		    		ruleReason : ruleReason,
		    		    		ruleReasonUrl1 : ruleReasonUrl1,
		    		    		ruleReasonUrl2 : ruleReasonUrl2,
		    		    		ruleReasonUrl3 : ruleReasonUrl3,
		    		    		ruleReasonUrl4 : ruleReasonUrl4,
		    		    		ruleReasonUrl5 : ruleReasonUrl5,
		    		    		ruleReasonUrl1Name : ruleReasonUrl1Name,
		    		    		ruleReasonUrl2Name : ruleReasonUrl2Name,
		    		    		ruleReasonUrl3Name : ruleReasonUrl3Name,
		    		    		ruleReasonUrl4Name : ruleReasonUrl4Name,
		    		    		ruleReasonUrl5Name : ruleReasonUrl5Name
		    				}, true),
		    				success: function(result){
		    					if(result.code == 200){
		    						$.successMsg("提交成功！");
		    						dlg.close().remove();
		    						$(meThis).parent().parent().remove();
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
				
			}
			
		}).show(meThis);
	});
});