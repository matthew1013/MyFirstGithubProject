/**
 * 
 */

$(function() {
	
	$.ajaxSetup({
		global: true,
		beforeSend: function(){
			App.blockUI({animate: true});
		},
		complete:function(){
			App.unblockUI();
		}
		
	});
	$.msg = function(option) {
		var option = $.extend({
			ui : 'toastr',
			title : '',
			msg : '',
			type : 'error',
			life : 3000
		}, option);

		if (option.ui == "toastr" && toastr) {
			toastr[option.type](option.msg);
		} else if (option.ui == "notific" && $.notific8) {
			$.notific8('zindex', 11500);
			$.notific8(option.msg, {
				theme : option.type == 'error' ? 'ruby' : 'lime',
				life : option.life
			});
		} else {
			alert(option.msg);
		}
	};

	$.successMsg = function(msg) {
		$.msg({
			title : '成功',
			msg : msg,
			type : 'success'
		});
	}

	$.errorMsg = function(msg) {
		$.msg({
			title : '错误',
			msg : msg,
			type : 'error'
		});
	}
	
	$.followFriend = function(id, elm) {
		var chk_value = [];
		chk_value.push(id);

		$.ajax({
			type : 'post',
			url : '/friend/add',
			data :$.param({
				"ids" : chk_value
			}, true)
,
			success : function(response) {
				console.log(response);
				var code = response.code;
				if (code=="200") {
					$(elm).html("取消关注");
					$(elm).removeClass("friend-follow");
					$(elm).removeClass("red"); 
					$(elm).addClass("friend-delete");
					$(elm).addClass("green");
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！请登录后再关注！");
					logout();
				} else {
					alert("失败，原因是:" + response.message);
				}

			},
		});
	};
	$.followFriendDelete = function(id, elm) {
		var chk_value = [];
		chk_value.push(id);

		$.ajax({
			type : 'post',
			url : '/friend/delete',
			data :$.param({
				"ids" : chk_value
			}, true)
,
			success : function(response) {
				console.log(response);
				var code = response.code;
				if (code=="200") {
					$(elm).html("+关注");
					$(elm).removeClass("friend-delete");
					$(elm).addClass("friend-follow");
					$(elm).removeClass("green"); 
					$(elm).addClass("red");
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！请登录后再取消关注！");
					logout();
				} else {
					alert("失败，原因是:" + response.message);
				}

			},
		});
	};

	$.showUserDesc = function(id, elm) {

		dialog({
			title : '',
			width : 500,
			quickClose : true,
			onshow : function() {
				var me = this;
				$.ajax({
					url : '/user/open_info',
					data : {
						id : id
					},
					success : function(data) {
						console.log(data);
						if (data) {
							me.content($('#userinfo-tmpl').render(data.data));
						} else {
						}
					},
				});

			}
		}).show(elm);

	};
	$.demandStart = function(taskid, userid) {
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : '/demand/start',
			data : {
				id : userid,
				demandId : taskid
			},
			success : function(data) {
				console.log(data);
				if (data) {

				} else {
				}
			},
			error : function() {

			}
		});

	};

	$(window.document).on('click', '.user-pic', function() {
		var id = $(this).data().id;
		$.showUserDesc(id, this);

	}).on('click','.friend-follow',function(){
		var id = $(this).data().id;
		$.followFriend(id,this);
	}).on('click','.friend-delete',function(){
		var id = $(this).data().id;
		$.followFriendDelete(id,this);
	}).on('click', '.demond-evaluate-btn', function(){
		var me = $(this);
		var data = me.data();
		
		dialog({
			title : '评价',
			width : 600,
			content: $('#post-evaluate-tmpl').html(),
			quickClose : true,
			okValue: '提交评价',
			ok: function(){
				var evaluate = $(':checked[name="evaluate"]', this.node).val();
				var describe = $('#evaluate-describe', this.node).val();
				$.ajax({
					url: '/demand/evaluate',
					type:'post',
					dataType: 'json',
					data:$.param({
						"demandId" : data.demandid,   
						"userId": data.userid,
						"evaluate" : evaluate,
						"describe" : describe
					}, true),
					success: function(result){
						if(result.code == 200){
							$.successMsg(result.message);
							me.remove();  //add by chenkanghua
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

		
	}).on('click', '.show-receiveDemands-btn', function(){
		var parentTr = $(this).parents('tr');
		if(parentTr.find('td:first').prop('rowspan') == 2){
			parentTr.find('td:first').prop('rowspan', 1);
			parentTr.next('tr').hide();
		}else{
			parentTr.find('td:first').prop('rowspan', 2);
			parentTr.next('tr').show();
		}
		
	}).on('click', '.message-link', function(){
		var data = $(this).data();
		$.ajax({
			type:"get",
			url: "/message_system/read",
			data:{
				id:data.id
			},
			success:function(result){
				if(result.code == "200"){
				}else{
					$.errorMsg(result.message);
				}
				var code = response.code;
				if (result.code == "607") {
					//跳至登录处
					$.errorMsg("您尚未登录，或者登录失败，请重新登录！");
					logout();
				} 
			}
		});

		
	});
	setTimeout(function(){
		$('#all-user-messages,#all-system-messages').prop('href', '/dashboard/Account/messages.jsp');
	}, 1000);
	try {
		$.views
				.tags({
					"Url" : function(url) {
						return  url;
					},
					"Grade" : function(grade) {
						switch (grade) {
						case "1":
							return '<i class="fa fa-star"></i>';
						case "2":
							return '<i class="fa fa-star"></i><i class="fa fa-star"></i>';
						case "3":
							return '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>';
						case "4":
							return '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>';
						case "5":
							return '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>';
						default:
							return "";
						}

					},
					"TaskState" : function(state) {
						switch (state) {
						case "0":
							return '<span class="font-red">等待接单</span>';
						case "2":
							return '<span class="font-blue">进行中</span>';
						case "3":
							return '<span class="font-grey">结束</span>';
						case "4":
							return '<span class="font-green">完成</span>';
						default:
							return "--";
						}

					},
					"Degree" : function(degree) {
						switch (degree) {
						case "1":
							return "高中毕业或其他同等学力(High School diploma or equivalent)";
						case "2":
							return "大专学历(College degree)";
						case "3":
							return "大学本科学历(Undergraduate degree)";
						case "4":
							return "硕士研究生学历(Master's degree)";
						case "5":
							return "博士学位(Doctoral or professional degree)";
						default:
							return "";
						}

					}
				});
	} catch (e) {
	}
});