$.views.tags({
     "datetimeFormat":function(datetime){
    	 if(datetime == null){
    		 return "";
    	 }
    	 return datetime.slice(0,datetime.indexOf(" "));
     }
});


$(function() {
	$("title").html("新闻 | AnyoneHelps");
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
	}
	
	
	var loadNewDetail = function(newId) {
		$.ajax({
			type : "get",
			url : "/news/get_one",
			data : {
				"id" : newId,
			},
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					if(response.data!=null){
						$(".new-detail").html($("#new-detail-tmpl").render(response.data))
						
						$("title").html(response.data.title+"| "+$("title").html());
         				$("meta[name='keywords']").attr("content",response.data.title);
         				$("meta[name='description']").attr("content",response.data.title);
						
					}
				}
			}
		});
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
		}
	}
	
	
	var newComment = function(){
 		
 		var loadReply = function(){
 			$(".reply").unbind("click");
 			$(".reply").bind("click",function(){
 				$(".reply-content").html("");
 				$(this).next().html($('#reply-tmpl').render());
 				loadReplyBtn();
 			})
 		} 
 		
 		var loadReplyBtn = function(){
 			$('.comment-reply-form').validate({
 	            submitHandler: function(form) {
 	            	var content = $(form).find("textarea").val();
 	     			var parentId = $(form).parent().data("parentid");
 	     			$.ajax({
 	     				type:"post",
 	     				url:"/new_comment/add",
 	     				data:$.param({
 	     					content:content,
 	     					newId:newId,
 	     					parentId : parentId
 	     				},true),
 	     				success:function(response){
 	     					var code = response.code;
 	     					if ("200" == code) {
 	     						$.successMsg("发表评论成功");
 	     						$(form).parent().parent().parent().find(".reply-list").append($('#comment-reply-tmpl').render(response.data));
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

 	        $('.comment-reply-form textarea').keypress(function(e) {
 	            if (e.which == 13) {
 	                if ($('.comment-reply-form').validate().form()) {
 	                    $('.comment-reply-form').submit();       //form validation success, call ajax form submit
 	                }
 	                return false;
 	            }
 	        });
 		} 
 		
 		function showNewCommentlist(list) {
 	 		if(list!=null){
 	 			console.log(list);
 	     		$('.comment-list').html($('#comment-tmpl').render({dataList: list}));
 	     		loadReply();
 	     	}
 	 	}

 	 	function showNewCommentlink(pageSplit) {
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
 	                		loadNewComment(newId, pageSize, page);
 	                	}
 	                }
 	             });
 	     	}
 	     }
 		
 		$('.comment-form').validate({
            submitHandler: function(form) {
            	var content = $(form).find("textarea").val();
     			$.ajax({
     				type:"post",
     				url:"/new_comment/add",
     				data:$.param({
     					content:content,
     					newId:newId
     				},true),
     				success:function(response){
     					var code = response.code;
     					if ("200" == code) {
     						$.successMsg("发表评论成功");
     						$(form).find("textarea").val("");
     						loadNewComment(newId,10,1);
     					} else if ("607" == code) {
     						$.errorMsg("您还没登录，或者登录已失效,请登录后再发表您的评论！");
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

        $('.comment-form textarea').keypress(function(e) {
            if (e.which == 13) {
                if ($('.comment-form').validate().form()) {
                    $('.comment-form').submit();       //form validation success, call ajax form submit
                }
                return false;
            }
        });
 		
 		var loadNewComment = function(newId, size, index) {
  			$.ajax({
  				type:"get",
  				url:"/new_comment/search",
  				data:{
  					id : newId,
  					pageIndex : index,
  					pageSize : size
  				},
  				success:function(response){
  					var code = response.code;
  					if ("200" == code) {
  						if (response.data != null) {
  							showNewCommentlist(response.data.datas);
  							showNewCommentlink(response.data);
  						} else {
  							showNewCommentlist(null);
  							showNewCommentlink(null);
  						}
  					} else if ("607" == code) {
  						//跳至登录处
  						//logout();
  					} 
  					return false;
  				}
  			});
  		}
 		var newId = $(".new-detail").data("id");
 		loadNewComment(newId,10,1);
    }
	
	var newId = $(".new-detail").data("id");
	loadNewDetail(newId);
	loadNewsList();
	loadActivityList();
	newComment();
	
});
