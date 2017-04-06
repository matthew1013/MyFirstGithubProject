$.views.tags({
});

$(function() {
	
	var msSearchCondition = {
		key : "",
		state : "",
		pageIndex : 1,
		pageSize : 10
	};
	var muSearchCondition = {
		key : "",
		state : "",
		pageIndex : 1,
		pageSize : 10
	};
	
	/*var isPC = function(){
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
	}*/
	
	var messagesInit = function(){
		$("title").html("消息中心 | 用户中心 | AnyoneHelps");
		$(".nav1").addClass("active open");
		$(".nav13").addClass("active open");

		$('input[name="ms-select"]').on('ifChecked', function(event){  
			$('input[name="ms-delete"]').iCheck('check');
		}).on('ifUnchecked', function(event){  
			$('input[name="ms-delete"]').iCheck('uncheck');
		}).iCheck({
    		checkboxClass: 'icheckbox_square-blue',
    		radioClass: 'iradio_square-blue',
    		increaseArea: '20%' // optional
    	}).parent().addClass("hide");
		
		$('input[name="mu-select"]').on('ifChecked', function(event){  
			$('input[name="mu-delete"]').iCheck('check');
		}).on('ifUnchecked', function(event){  
			$('input[name="mu-delete"]').iCheck('uncheck');
		}).iCheck({
    		checkboxClass: 'icheckbox_square-blue',
    		radioClass: 'iradio_square-blue',
    		increaseArea: '20%' // optional
    	}).parent().addClass("hide");
		$('.operate .right .ms-select-delete').click(function(){
			
			if($(this).hasClass("delete")){
				
				var isSelect = false;
				$('input[name="ms-delete"]').each(function(){
		    		if($(this).is(':checked')) {
		    			isSelect = true;
		    		}
		    	});
				if(!isSelect){ 
					$.errorMsg("请先选择要删除的消息！");
					return false;
				}
				var d = dialog({
				    title: '提示',
				    width:'300px',
				    skin: 'blacklist-add-tip-dlg',
				    content: '确认要删除所选消息记录吗？',
				    okValue: '确定',
				    ok: function () {
				    	var ids = [];
				    	$('input[name="ms-delete"]').each(function(){
				    		if($(this).is(':checked')) {
				    			ids.push($(this).val());
				    		}
				    	});
				    	
						$.ajax({
							type:"post",
							url:"/message_system/delete_ids",
							data:$.param({ 
								"ids":ids
							},true),
							success:function(response){
								var code = response.code;
								if ("200" == code) {
									$.successMsg("删除成功");
									$('input[name="ms-delete"]').each(function(){
							    		if($(this).is(':checked')) {
							    			$(this).parent().parent().parent().remove();
							    			loadMessagesSystem();
							    		}
							    	}); 
								}else {
									$.errorMsg(response.message);
								}
							}
						});
				    },
				    cancelValue: '取消',
				    cancel: function () {}
				});
				d.showModal();
			}else{
				$(this).addClass("delete");
				$(this).children().html("删除");
				$('input[name="ms-select"]').parent().removeClass('hide');
				$(".ms-item>.left").removeClass("hide");
				$(".ms-item>.right").removeClass("full");
				$(".operate .right .ms-select-cancel").removeClass("hide");
			}
			
			
			
			return false;
		})
		$('.operate .right .mu-select-delete').click(function(){
			if($(this).hasClass("delete")){
				var isSelect = false;
				$('input[name="mu-delete"]').each(function(){
		    		if($(this).is(':checked')) {
		    			isSelect = true;
		    		}
		    	});
				if(!isSelect){ 
					$.errorMsg("请先选择要删除的留言！");
					return false;
				}
				var d = dialog({
				    title: '提示',
				    width:'300px',
				    skin: 'blacklist-add-tip-dlg',
				    content: '确认要删除所选消息记录吗？',
				    okValue: '确定',
				    ok: function () {
				    	var ids = [];
				    	$('input[name="mu-delete"]').each(function(){
				    		if($(this).is(':checked')) {
				    			ids.push($(this).val());
				    		}
				    	});
						$.ajax({
							type:"post",
							url:"/message_user/delete_ids",
							data:$.param({ 
								"ids":ids
							},true),
							success:function(response){
								var code = response.code;
								if ("200" == code) {
									$.successMsg("删除成功");
									$('input[name="mu-delete"]').each(function(){
							    		if($(this).is(':checked')) {
							    			$(this).parent().parent().parent().remove();
							    			loadMessagesUser();
							    		}
							    	});
								}else {
									$.errorMsg(response.message);
								}
							}
						});
				    },
				    cancelValue: '取消',
				    cancel: function () {}
				});
				d.showModal();
			}else{
				$(this).addClass("delete");
				$(this).children().html("删除");
				$('input[name="mu-select"]').parent().removeClass('hide');
				$(".mu-item>.left").removeClass("hide");
				$(".mu-item>.right").removeClass("full");
				$(".operate .right .mu-select-cancel").removeClass("hide");
			}
			
			return false;
		})
		
		$('.operate .right .ms-select-cancel').click(function(){
			$('input[name="ms-select"]').iCheck('uncheck');
			$('input[name="ms-delete"]').iCheck('uncheck');
			
			$('input[name="ms-select"]').parent().addClass('hide');
			$('.operate .right .ms-select-delete').removeClass("delete");
			$('.operate .right .ms-select-delete').children().html("选择");
			$(".ms-item>.left").addClass("hide");
			$(".ms-item>.right").addClass("full");
			$(".operate .right .ms-select-cancel").addClass("hide");
			return false;
		})
		$('.operate .right .mu-select-cancel').click(function(){
			$('input[name="mu-select"]').iCheck('uncheck');
			$('input[name="mu-delete"]').iCheck('uncheck');
			
			$('input[name="mu-select"]').parent().addClass('hide');
			$('.operate .right .mu-select-delete').removeClass("delete");
			$('.operate .right .mu-select-delete').children().html("选择");
			$(".mu-item>.left").addClass("hide");
			$(".mu-item>.right").addClass("full");
			$(".operate .right .mu-select-cancel").addClass("hide");
			return false;
		})
	}
	
	$(".blacklist-add").click(function(){
		var d = dialog({
		    title: '提示',
		    width:'300px',
		    skin: 'blacklist-add-tip-dlg',
		    content: '确认要加入黑名单吗？',
		    okValue: '确定',
		    ok: function () {
		        this.title('提交中…');
		        var friendId = $('.friend-nick-name').data("id");
				$.ajax({
					type:"post",
					url:"/blacklist/add",
					data:{
						friendId:friendId
					},
					success:function(response){
						var code = response.code;
						if ("200" == code) {
							$.successMsg("加入黑名单成功");
							var count = ($(".contact-user-count").data("count") - 1);
							
							$(".contact-user-count").data("count", count);
							$(".contact-user-count").html(count);
							$(".contact-list ul").find("a.active").parent().remove();
			    			$('.friend-nick-name').html("");
			    			$('.friend-nick-name').removeClass("user-pic");
			    			$('.friend-nick-name').data("id","");
			    			$('.message-content-list ul').html("");
			    			$('.widget-container.scrollable.chat.chat-page>.heading,.widget-container.scrollable.chat.chat-page .post-message').addClass("hide");
						}else {
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
	})
	$(".message-all-delete").click(function(){
		var d = dialog({
		    title: '提示',
		    width:'300px',
		    skin: 'blacklist-add-tip-dlg',
		    content: '确认要删除所有私信记录吗？',
		    okValue: '确定',
		    ok: function () {
		    	var friendId = $('.friend-nick-name').data("id");
				$.ajax({
					type:"get",
					url:"/message/delete_all",
					data:{
						friendId:friendId
					},
					success:function(response){
						var code = response.code;
						if ("200" == code) {
							$.successMsg("删除成功");
							$(".contact-list ul").find("a.active").parent().remove();
			    			$('.friend-nick-name').html("");
			    			$('.friend-nick-name').removeClass("user-pic");
			    			$('.friend-nick-name').data("id","");
			    			$('.message-content-list ul').html("");
			    			$('.widget-container.scrollable.chat.chat-page>.heading,.widget-container.scrollable.chat.chat-page .post-message').addClass("hide");
						}else {
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
	})
	
	$(".cancel-delete").click(function(){
		$(".tab-content .tab-pane.fade .widget-content ul li.current-user .bubble").css("margin-left","0px");
		$(".tab-content .tab-pane.fade .widget-content ul li.current-user>img").css("margin-right","0px");
		$(".tab-content .tab-pane.fade .widget-content ul li .delete").remove();
		$(".message-curr-delete").attr("src","/assets/pages/img/message/show-delete.png;");
		$(".message-curr-delete").data("show","1");
		$(this).hide();
	})
	
	$(".return").click(function(){
		$('.friend-nick-name').html("");
		$('.friend-nick-name').removeClass("user-pic");
		$('.friend-nick-name').data("id","");
		$('.message-content-list ul').html("");
		$('.widget-container.scrollable.chat.chat-page>.heading,.widget-container.scrollable.chat.chat-page .post-message').addClass("hide");
		$(".widget-content.padded.message-content-list").addClass("phone-style");
		$('.contact-list').removeClass("hide");
		return false;
	})
		
		
	$(".message-curr-delete").click(function(){
		if($(this).data("show")==1){
			$(".tab-content .tab-pane.fade .widget-content ul li.current-user .bubble").css("margin-left","-30px");
			$(".tab-content .tab-pane.fade .widget-content ul li.current-user>img").css("margin-right","30px");
			$(".tab-content .tab-pane.fade .widget-content ul li .bubble").after('<div class="delete"><input type="checkbox" name="delete"></div>');
			$('input[name="delete"]').iCheck({
	    		checkboxClass: 'icheckbox_square-blue',
	    		radioClass: 'iradio_square-blue',
	    		increaseArea: '20%' // optional
	    	});
			$(this).attr("src","/assets/pages/img/message/chat-delete.png;");
			$(this).data("show","0");
			$(".cancel-delete").show();
		}else if($(this).data("show")==0){
			var obj = $(this);
			$(".cancel-delete").hide();
			var ids = [];
	    	var deleteObj = $("input[name='delete']");
	    	if(deleteObj!=null){
	    		$.each(deleteObj, function(index) {
	    			if($(this).is(':checked')) {
	    				ids.push($(this).parent().parent().parent().data("id"));
	    			}
	    		});
	   		}
	    	if(ids.length<=0){
	    		$(".tab-content .tab-pane.fade .widget-content ul li.current-user .bubble").css("margin-left","0px");
				$(".tab-content .tab-pane.fade .widget-content ul li.current-user>img").css("margin-right","0px");
				$(".tab-content .tab-pane.fade .widget-content ul li .delete").remove();
				obj.attr("src","/assets/pages/img/message/show-delete.png;");
				obj.data("show","1");
	    	}else{
	    		var d = dialog({
				    title: '提示',
				    width:'300px',
				    skin: 'blacklist-add-tip-dlg',
				    content: '确认要删除所选私信记录吗？',
				    okValue: '删除',
				    ok: function () {
				    	
						$.ajax({
							type:"post",
							url:"/message/delete",
							data:$.param({ 
								"ids":ids
							},true),
							success:function(response){
								var code = response.code;
								if ("200" == code) {
									$.successMsg("删除成功");
									$.each(deleteObj, function(index) {
							    		if($(this).is(':checked')) {
							    			$(this).parent().parent().parent().remove();
							    		}
							    	});
									
									//$(".tab-content .tab-pane.fade .widget-content ul li.current-user .bubble").css("margin-left","0px");
									//$(".tab-content .tab-pane.fade .widget-content ul li.current-user>img").css("margin-right","0px");
									//$(".tab-content .tab-pane.fade .widget-content ul li .delete").remove();
									obj.attr("src","/assets/pages/img/message/show-delete.png;");
									obj.data("show","1");
									
							    	
								}else {
									$.errorMsg(response.message);
								}
							}
						});
				    },
				    cancelValue: '取消',
				    cancel: function () {
				    	$(".cancel-delete").show(); 
				    }
				});
				d.showModal();
	    	}
		}
		return false;
	})
	
    
	
	function loadMessagesSystem() {
		$.ajax({
			type : "get",
			url : "/message_system/search",
			data : {
				"key" : msSearchCondition.key,
				"state" : msSearchCondition.state,
				"pageSize" : msSearchCondition.pageSize,
				"pageIndex" : msSearchCondition.pageIndex
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showMessagesSystemlist(response.data.datas);
						showMessagesSystemlink(response.data);
					} else {
						showMessagesSystemlist(null);
						showMessagesSystemlink(null);
					}
					//afterloadcwjllist();
				} else if ("607" == code) {
					$.errorMsg("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
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
					$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	
	var deleteMessageSystem = function(id,me) {
		$.ajax({
			type:"get",
			url:"/message_system/delete",
			data:{
				id:id
			},
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					$.successMsg("删除成功");
					me.parent().parent().parent().remove();
					loadMessagesSystem();
				}else if ("607" == code) {
					//跳至登录处
					$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
					logout();
				}else {
					$.errorMsg(response.message);
				}
				return false;
			}
		});
	}
	
	function showMessagesSystemlist(list) {
		var display = 0;
		if(!$(".operate .right .ms-select-delete").hasClass("delete")){
			display = 1
		}
		var obj = $('.ms-list').html($('#ms-items-tmpl').render({display:display,dataList: list}));
		obj.find('.ms-read').click(function(){
			if($(this).children().hasClass("unread")){
				var id = $(this).data().id;
				modifyMessageSystemState(id); 
				$(this).parent().parent().parent().find(".state").remove();
				$(this).children().removeClass("unread");
			}
			
		});
		
		obj.find('.delete').click(function(){
			var id = $(this).data().id;
			var me = $(this);
			deleteMessageSystem(id,me); 
			
		});
		
		obj.find('input[name="ms-delete"]').iCheck({
    		checkboxClass: 'icheckbox_square-blue',
    		radioClass: 'iradio_square-blue',
    		increaseArea: '20%' // optional
    	});
		
		obj.find('.recommend-operation.accepted').click(function(){
			var btnObj = $(this);
			$.ajax({
				type:"get",
				url:"/recommend/accepted",
				data:{
					recommender:$(this).data("recommender")
				},
				success:function(response){
					var code = response.code;
					if ("200" == code) {
						$.successMsg("接受成功");
						btnObj.next().remove();
						btnObj.after('<span class="recommend-operation">已接受</span>');
						btnObj.remove();
					} else if ("607" == code) {
						//跳至登录处
						$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
						logout();
					} else{
						$.errorMsg(response.message);
					}
				}
			});		
		});
		obj.find('.recommend-operation.reject').click(function(){
			var btnObj = $(this);
			$.ajax({
				type:"get",
				url:"/recommend/reject",
				data:{
					recommender:$(this).data("recommender")
				},
				success:function(response){
					var code = response.code;
					if ("200" == code) {
						$.successMsg("拒绝成功");
						btnObj.prev().remove();
						btnObj.after('<span class="recommend-operation">已拒绝</span>');
						btnObj.remove();
					} else if ("607" == code) {
						//跳至登录处
						$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
						logout();
					} else{
						$.errorMsg(response.message);
					}
				}
			});
		});
	}

	function showMessagesSystemlink(pageSplit) {
    
		if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$("#pageSplit-system").parent().html('<ul class="pagination" id="pageSplit-system"></ul>');
    		$("#pageSplit-system").twbsPagination({
                totalPages: pageCount,
                startPage: pageNow,  
                first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
                //href: '?a=&page={{number}}&c=d' ,
                onPageClick: function (event, page) {
                	$('input[name="ms-select"]').iCheck('uncheck');
                	if(page!=pageNow){
        				msSearchCondition.pageIndex = page;
            		    loadMessagesSystem();
                	}
                }
            });
    	}
    }
	
	function loadMessagesUser() {
		
		$.ajax({
			type : "get",
			url : "/message_user/search",
			data : {
				"key" : muSearchCondition.key,
				"state" : muSearchCondition.state,
				"pageSize" : muSearchCondition.pageSize,
				"pageIndex" : muSearchCondition.pageIndex
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showMessagesUserlist(response.data.datas);
						showMessagesUserlink(response.data);
					} else {
						showMessagesUserlist(null);
						showMessagesUserlink(null);
					}
					//afterloadcwjllist();
				} else if ("607" == code) {
					$.errorMsg("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
	}
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
					$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	
	var deleteMessageUser = function(id,me) {
		$.ajax({
			type:"get",
			url:"/message_user/delete",
			data:{
				id:id
			},
			success:function(response){
				var code = response.code;
				if ("200" == code) {
					$.successMsg("删除成功");
					me.parent().parent().parent().remove();
					loadMessagesUser();
				}else if ("607" == code) {
					//跳至登录处
					$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
					logout();
				}else {
					$.errorMsg(response.message);
				}
				return false;
			}
		});
	}
	
	function showMessagesUserlist(list) {
		var display = 0;
		if(!$(".operate .right .mu-select-delete").hasClass("delete")){
			display = 1
		}
    	var obj = $('.mu-list').html($('#mu-items-tmpl').render({display:display,dataList: list}));
    	obj.find('.mu-read').click(function(){
    		if($(this).children().hasClass("unread")){
				var id = $(this).data().id;
				modifyMessageUserState(id); 
				$(this).parent().parent().parent().find(".state").remove();
				$(this).children().removeClass("unread");
    		}
		});
    	
    	obj.find('.delete').click(function(){
			var id = $(this).data().id;
			var me = $(this);
			deleteMessageUser(id,me); 
			
		});
    	obj.find('input[name="mu-delete"]').iCheck({
    		checkboxClass: 'icheckbox_square-blue',
    		radioClass: 'iradio_square-blue',
    		increaseArea: '20%' // optional
    	});
		
	}

	function showMessagesUserlink(pageSplit) {
    	
    	if (pageSplit != null) {
    		var rowCount = pageSplit.rowCount;
    		var pageSize = pageSplit.pageSize;
    		var pageNow = pageSplit.pageNow;
    		var pageCount = pageSplit.pageCount;
    		$("#pageSplit-user").parent().html('<ul class="pagination" id="pageSplit-user"></ul>');
    		
    		$("#pageSplit-user").twbsPagination({
                totalPages: pageCount,
                startPage: pageNow,  
                first: g_first,
               	prev: g_prev,
              	next: g_next,
               	last: g_last,
                //href: '?a=&page={{number}}&c=d' ,
                onPageClick: function (event, page) {
                	$('input[name="mu-select"]').iCheck('uncheck');
                	if(page!=pageNow){
                		muSearchCondition.pageIndex = page;
                		loadMessagesUser();
                	}
        		    
                }
            });
    	}
    	
    }
	
	function loadMessages(key,state,size,pn) {
		$.ajax({
			type : "get",
			url : "/message/search",
			data : {
				"key" : key,
				"state" : state,
				"pageSize" : size,
				"pageIndex" : pn
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						showMessageslist(response.data.datas);
					} else {
						showMessageslist(null);
					}
					//afterloadcwjllist();
				} else if ("607" == code) {
					$.errorMsg("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
	}
	var modifyMessageState = function(id) {
		$.ajax({
			type:"get",
			url:"/message/read",
			data:{
				id:id
			},
			success:function(response){
				var code = response.code;
				if ("200" == code) {
				} else if ("607" == code) {
					//跳至登录处
					$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
				return false;
			}
		});
	}
	function showMessageslist(list) {
		if(list!=null){
    		$('.contact-list').append($('#contact-tmpl').render({dataList: list}));
    		var userId = $('#userId').val();
    		if(userId!=null&&userId!=""){
    			$('.contact-list ul li a').each(function() {
    				if(!isPC()){
						$(this).parent().parent().parent().addClass("hide");
						$(".widget-content.padded.message-content-list").removeClass("phone-style");
					}
       			 	if($(this).data("friendid")==base64Decode(userId)){
	       			 	$(".message-curr-delete").attr("src","/assets/pages/img/message/show-delete.png;");
	    				$(".message-curr-delete").data("show","1");
	    				$(".cancel-delete").hide();
	    				
	        			$('.message-detail').removeClass("active");
	        			$(this).addClass("active");
	        			me = $(this);
	        			var friendId = $(this).data("friendid");
	        			var friendnickname = $(this).data("friendnickname");
	        			$('.friend-nick-name').html(friendnickname);
	        			$('.friend-nick-name').addClass("user-pic");
	        			$('.friend-nick-name').data("id",friendId);
	        			$('.message-content-list ul').html("");
	        			$('.widget-container.scrollable.chat.chat-page .heading,.widget-container.scrollable.chat.chat-page .post-message').removeClass("hide");
	        			var key = "";
	        			var size = 20;
	        			var pn = 1;
	        			$('.message-content-list ul').html("");
	        			loadMessagesContent(key,friendId,size,pn,me);
	        			return false;
       			 	}
       		 	})
    			
    			
    		}
    		$('.show-blacklist').click(function(){
    			var searchCondition = {
    				key : "",
    				pageIndex : 1,
    				pageSize : 5
    			};
    			var g_page_size = 0;
    	        var g_flag = false; 
    			function showBlacklistLink(pageSplit) {
    		    	if (pageSplit != null) {
    		    		var rowCount = pageSplit.rowCount;
    		    		var pageSize = pageSplit.pageSize;
    		    		var pageNow = pageSplit.pageNow;
    		    		var pageCount = pageSplit.pageCount;
    		    		if(g_page_size != pageCount && g_flag){  
    		    			$(".blacklist-dlg").find(".search-pagination").html('<ul class="pagination"></ul>');  
    	        		    g_flag = false;
    	        		}  
    	        		g_page_size = pageCount;
    	        		
    		    		$(".blacklist-dlg").find(".pagination").twbsPagination({
    		                totalPages: pageCount,
    		                first: g_first,
    	                   	prev: g_prev,
    	                  	next: g_next,
    	                   	last: g_last,
    		                onPageClick: function (event, page) {
    		                	var key = $(".blacklist-dlg").find(".search-key").val();
    		                	searchCondition.pageIndex = page;
    		                	loadBlacklist(false, null);
    		                }
    		            });
    		    	}
    		    }
    			function loadBlacklist(init, obj) {
    				$.ajax({
						type : "get",
						url : "/blacklist/search",
						data : {
							"key" : searchCondition.key,
							"pageSize" : searchCondition.pageSize,
							"pageIndex" : searchCondition.pageIndex
						},
						success : function(response) {
							var code = response.code;
							if (code == "200") {
								var data = response.data;
								if(data!=null){
									if(init) {
										obj.content($('#blacklist-tmpl').render({dataList:data.datas}));
									}else{
										$(".blacklist-list").html($('#blacklist-item-tmpl').render({dataList:data.datas}));
									}
									
								}else{
									obj.content("<div style='text-align: center;border: 1px solid #dddddd;padding: 20px;'>您暂未将任务用户列入黑名单</div>");
								}
								showBlacklistLink(data);
								
								$(".blacklist-dlg").find(".search-start").unbind("click");
								$(".blacklist-dlg").find(".search-start").click(function(){
									g_flag = true;
									searchCondition.key = $(".blacklist-dlg").find(".search-key").val();
	    		                	searchCondition.pageIndex = 1;
			    		    		loadBlacklist(false, null);
			    		    		return false;
			    		    	})
			    		    	
			    		    	$(".blacklist-dlg").find(".remove-btn").unbind("click");
								$(".blacklist-dlg").find(".remove-btn").click(function(){
									var me = $(this);
									var friendId = $(this).data("id");
									$.ajax({
										type : "get",
										url : "/blacklist/delete",
										data : {
											"friendId" : friendId
										},
										success : function(response) {
											var code = response.code;
											if (code == "200") {
												$.successMsg("删除成功！");
												me.parent().parent().parent().remove();
												$('.contact-list ul').append($('#contact-item-tmpl').render(response.data));
												var count = ($(".contact-user-count").data("count") + 1);
												$(".contact-user-count").data("count", count);
												$(".contact-user-count").html(count);
												$('.message-detail').unbind("click")
												$('.message-detail').click(function(){
													if(!isPC()){
														$(this).parent().parent().parent().addClass("hide");
														$(".widget-content.padded.message-content-list").removeClass("phone-style");
													}
													$(".message-curr-delete").attr("src","/assets/pages/img/message/show-delete.png;");
													$(".message-curr-delete").data("show","1");
													$(".cancel-delete").hide();
													
									    			$('.message-detail').removeClass("active");
									    			$(this).addClass("active");
									    			me = $(this);
									    			var friendId = $(this).data("friendid");
									    			var friendnickname = $(this).data("friendnickname");
									    			$('.friend-nick-name').html(friendnickname);
									    			$('.friend-nick-name').addClass("user-pic");
									    			$('.friend-nick-name').data("id",friendId);
									    			$('.message-content-list ul').html("");
									    			$('.widget-container.scrollable.chat.chat-page .heading,.widget-container.scrollable.chat.chat-page .post-message').removeClass("hide");
									    			var key = "";
									    			var size = 20;
									    			var pn = 1;
									    			$('.message-content-list ul').html("");
									    			loadMessagesContent(key,friendId,size,pn,me);
									    			return false;
									    		})
											} else {
												$.errorMsg(response.message);
											}
										}
									});
			    		    	})
			    		    	
			    		    	
							} else if ("607" == code) {
								$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
							}
						}
					});
    		    }
    			var d = dialog({
        		    title: '黑名单列表',
        		    /*width:'600px',*/
        		    skin: 'blacklist-dlg',
        		    onshow : function() {
        		    	var me = this;
        		    	loadBlacklist(true, me);
        		    	/*$.ajax({
							type : "get",
							url : "/blacklist/search",
							data : {
								"key" : "",
								"pageSize" : 5,
								"pageIndex" : 1
							},
							success : function(response) {
								var code = response.code;
								if (code == "200") {
									var data = response.data;
									if(data!=null){
										me.content($('#blacklist-tmpl').render({dataList:data.datas}));
										showBlacklistLink(data);
									}

								} else if ("607" == code) {
									$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
								}
							}
						});*/
						
					},
        		});
        		d.showModal();
    		})
    		
    		
    		$('.message-detail').click(function(){
    			if(!isPC()){
    				$(this).parent().parent().parent().addClass("hide");
    				$(".widget-content.padded.message-content-list").removeClass("phone-style");
				}
				$(".message-curr-delete").attr("src","/assets/pages/img/message/show-delete.png;");
				$(".message-curr-delete").data("show","1");
				$(".cancel-delete").hide();
				
    			$('.message-detail').removeClass("active");
    			$(this).addClass("active");
    			me = $(this);
    			var friendId = $(this).data("friendid");
    			var friendnickname = $(this).data("friendnickname");
    			$('.friend-nick-name').html(friendnickname);
    			$('.friend-nick-name').addClass("user-pic");
    			$('.friend-nick-name').data("id",friendId);
    			$('.message-content-list ul').html("");
    			$('.widget-container.scrollable.chat.chat-page .heading,.widget-container.scrollable.chat.chat-page .post-message').removeClass("hide");
    			var key = "";
    			var size = 20;
    			var pn = 1;
    			$('.message-content-list ul').html("");
    			loadMessagesContent(key,friendId,size,pn,me);
    			return false;
    		})
    		$('.widget-container.scrollable.chat.chat-page .post-message>.submit-message').click(function(){
    			$(".tab-content .tab-pane.fade .widget-content ul li.current-user .bubble").css("margin-left","0px");
				$(".tab-content .tab-pane.fade .widget-content ul li.current-user>img").css("margin-right","0px");
				$(".tab-content .tab-pane.fade .widget-content ul li .delete").remove();
				
    			$(".message-curr-delete").attr("src","/assets/pages/img/message/show-delete.png;");
				$(".message-curr-delete").data("show","1");
				$(".cancel-delete").hide();
				
    			var contentObj = $(this).prev();
    			var content = $(this).prev().val();
    			var friendId = $('.friend-nick-name').data("id");
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
    						//$('.user-message-content').val("");
    						$('.message-content-list ul').append($('#message-content-item-tmpl').render({content:content}));
    						contentObj.val("");
    						
    					} else if ("607" == code) {
    						$.errorMsg("您还没登录，或者登录已失效,请登录后再留言！");
    						window.location.href = "/login.jsp";
    					} else {
    						$.errorMsg(response.message);
    					}
    				}
    			});
    			return false;
    		});
    		/*$('a[name="message-user-content"]').click(function(){
    			var id = $(this).data().id;
    			modifyMessageState(id);
    			$(this).parent().prev().html("已读");
    		});*/
		}else{
			var str = '<div class="m-list">';
			str += '<div class="m-item">';
			str += '<div class="row" style="text-align:center;">暂未收到私信~~</div>';
			str += '</div> ';
			str += '</div>';
			$('#tab_1_3').html(str);
    	}
	}
	
	function loadMessagesContent(key,friendId,size,pn,me) {
		$.ajax({
			type : "get",
			url : "/message/search_by_friend_id",
			data : {
				"key" : key,
				"friendId" : friendId,
				"pageSize" : size,
				"pageIndex" : pn
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if(me){
						me.find("i").remove(); //删除未读标志
					}
					if (response.data != null) {
						//more-heading
						console.log(response.data);
						//alert(response.data.datas.length);
						if (response.data.datas.length < size) {
							$(".more-heading").addClass("hide");
						} else {
							$(".more-heading").removeClass("hide");
							$(".more-heading>a").unbind("click");
							$(".more-heading>a").bind("click",function(){
								loadMessagesContent(key,friendId,size,pn+1,null);
							});
						}
						$('.message-content-list ul').prepend($('#message-content-tmpl').render({friendId:friendId,dataList: response.data.datas}));
					} else {
						//showMessageslist(null);
						$(".more-heading").addClass("hide");
					}
				} else if ("607" == code) {
					$.errorMsg("您尚未登录或登录已失效！");
					logout();
				} 
			}
			
		});
	}

	
	messagesInit();
	loadMessagesSystem();
	loadMessagesUser();
	loadMessages("","",299,1);
});