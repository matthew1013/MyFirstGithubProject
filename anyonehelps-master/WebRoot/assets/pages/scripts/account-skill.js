 $.views.tags({
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
    "specialtyStateFormat": function(state){
        switch (true){
        case state == '1':
        	return '审核中';
        case state == '2':
        	return '认证成功';
        case state == '3':
        	return '认证失败';
        default:
        	return '未知';
        }
    }
 });
var myskills = function() {
	var myskillsInit = function(){
		$("title").html("技能管理 | 用户中心 | AnyoneHelps");
		$(".nav1").addClass("active open");
		$(".nav12").addClass("active open");
		$('#auth-content').summernote({
			height: 300
		});
		
		$("#submit-div").hide();
		$("#content-div").hide();
		$(".enclosure-div").hide();
		
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
	        	var code = data.result.code;
	        	if(code=="200"){
	        		if(data.result.data!=null){
	    	            $.each(data.result.data, function (index, file) {
	    	            	
	    	            	if( $('.enclosure-list').children().length > 4){
	    	            		$(".enclosure-upload").hide();
		        				$('.enclosure-format').hide();
	    	            		return;
	    	            	}
	    	            	$('.enclosure-list').append($('#enclosure-item-tmpl').render(file));
		        			if($('.enclosure-list').children().length>=5){
		        				$(".enclosure-upload").hide();
		        				$('.enclosure-format').hide();
		        			}
		        			$('.enclosure-delete').unbind("click"); //移除click
	        				$('.enclosure-delete').click(function(){
	        					$(this).parent().parent().remove();
	        					$(".enclosure-upload").show();
	        					$('.enclosure-format').show();
	        				});
	    	            }); 
	        		}
	        	}else{
	        		$.errorMsg(data.result.message);
	        	}
	        },
	        
	        /*progressall: function (e, data) {
		        var progress = parseInt(data.loaded / data.total * 100, 10);
		        if(progress=100){
		        	$('#progress').hide();
		        }else{
		        	$('#progress').show();
		        }
		        $('#progress .bar').css(
		            'width',
		            progress + '%'
		        );
	   		},*/
	    });
		
		$('#auth-specialty-form').validate({
            submitHandler: function(form) {
            	
            	if(!$(form).find("input[name='isAgree']").is(':checked')){
            		$.errorMsg("您还未同意技能认证使用条款！");
            		return false;
            	}
            	var id = $("select[name='auth-specialty']").val();
            	var content = encodeURI($('textarea[name="content"]').val());
            	if(id==null||id==""||id=="-1"){
            		$.errorMsg("请选择要认证的技能！！");
            		return false;
            	}
            	
            	var str = '"id":"' + id + '",' + '"authContent":"' + content + '"' ;
            	
            	$(".enclosure-list .enclosure-item .title").each(function(index){
        			if(index<5){
        				i = index+1;
        				str += ',"enclosure'+i+'":"' + $( this ).data("id") + '"';
        				str += ',"enclosure'+i+'Name":"' + $( this ).html() + '"';
        			}
        		});
            	var obj = $.parseJSON("{" + str + "}");
            	console.log(obj);
            	var d = dialog({
    			    title: '提示',
    			    /*width:'400px',*/
    			    skin: 'tip-dlg',
    			    content: '需要扣费$50认证费用，您确定提交吗?',
    			    okValue: '确定',
    			    ok: function () {
    			        this.title('提交中…');
    			        
    			        $.ajax({
    	            		type:"post",
    	            		url:"/specialty/user_specialty_auth",
    	            		data : $.param(obj, true),
    	            		success:function(response) {
    	            			var code = response.code;
    	            			if (code == "200") {
    	            				$.successMsg("提交成功，我们将于48小时内审核完毕!");
    	            				window.location.reload(); 
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
            	
            	/*var option = $("select[name='auth-specialty']").find("option:selected");
            	if(option.hasClass("custom")){   //自定义技能认证提交
            		$.ajax({
                		type:"post",
                		url:"/specialty_custom/auth",
                		data : $.param(objcustom, true),
                		success:function(response) {
                			var code = response.code;
                			if (code == "200") {
                				$.successMsg("提交成功，我们将于48小时内审核完毕!");
                				window.location.href=window.location.href;
                			} else if ("607" == code) {
                				$.errorMsg("您尚未登录或登录已失效！");
                				logout();
                			} else {
                				// 失败
                				$.errorMsg(response.message);
                			}
                		}
                	});
            	}else{                           //系统技能认证提交
            		
            	}*/
            	
            }
        });
		
	}
    //载入系统所有技能
    var loadSpecialty = function() {
    	$.ajax({
    		type:"get",
    		url:"/specialty/get_specialty_type_all",
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") {
    				var data = response.data;
    				showSpecialtyType(data);
    				loadSpecialtyUser();
    			} else if ("607" == code) {
    				$.errorMsg("您尚未登录或登录已失效！");
    				logout();
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
    }
    //显示系统所有技能
    function showSpecialtyType(list) {
    	$(".specialty-list").html($('#specialty-tmpl').render({dataList:list}));
    	/*展开技能*/
    	$('.specialty-list .specialty-item .tab-content .tab-pane .middle .zoom').click(function() {
    		$(this).parent().parent().parent().find(">div>.right").addClass("hide");
    		$(this).parent().parent().parent().find(">div>.middle").removeClass("hide");
    		$(this).parent().addClass("hide");
    		$(this).parent().next().removeClass("hide");
    	})
    	
    	/*系统技能点击*/
    	$('.specialty-list .specialty-item .special-label').click(function() {
    		var id = $(this).data("id");
    		var stid = $(this).data("typeid");
    		var name = $(this).data("name");
    		var obj = $( this );
    		addSpecialtyUser(0, stid, id, name, obj);
    	})
    	
    	/*技能下拉框改变*/
    	$("select[name='auth-specialty']").change(function(){
    		var id = $(this).val();
    		if(id =="-1"||id==""){
    			return false;
    		}
    		var option = $(this).find("option:selected");
    		//var stId = $("select[name='auth-specialty-type']").val();
    		
    		$.ajax({
        		type:"get",
        		url:"/specialty/user_specialty_get_one",
        		data : $.param({
        			"id" : id
        		}, true),
        		success:function(response) {
        			var code = response.code;
        			if (code == "200") {
        				if(response.data!=null){
        					if(response.data.state==1){
        						$(".auth-state span").html("认证中，请等待审核结果（48小时内）");
        						$("#submit-div").hide();
        		    			$("#content-div").hide();
        		    			$(".enclosure-div").hide();
        					}else if(response.data.state==2){
        						$(".auth-state span").html("已经认证通过");
        						$("#submit-div").hide();
        		    			$("#content-div").hide();
        		    			$(".enclosure-div").hide();
        					}else {
        						$(".auth-state span").html("未认证成功，请提交认证凭证");
        						$("#submit-div").show();
        		    			$("#content-div").show();
        		    			$(".enclosure-div").show();
        					}
        				}else{
        					$(".auth-state span").html("未认证，请提交认证凭证");
        					$("#submit-div").show();
        	    			$("#content-div").show();
    		    			$(".enclosure-div").show();
        				}
        			} else if ("607" == code) {
        				$.errorMsg("您尚未登录或登录已失效！");
        				logout();
        			} 
        		}
        	});
    	});
    }
    //载入用户技能
    function loadSpecialtyUser(){
    	$.ajax({
    		type:"get",
    		url:"/specialty/get_user_specialty",
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") {
    				var data = response.data;
    				showSpecialtyUser(data);
    			} else if ("607" == code) {
    				$.errorMsg("您尚未登录或登录已失效！");
    				logout();
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
    }
    
    var showRemark = function(){
    	var remark = $(this).data("remark");
    	dialog({
    		align:'bottom right',
		    /*width:'400px',*/
		    quickClose : true,
		    skin: 'special-remark-dlg',
		    content: remark
		}).show(this);
    }
    
    //显示用户拥有技能
    function showSpecialtyUser(list) {
    	if (list != null) {
    		$.each(list, function() {
    			if(this.type==0){
    				var id = this.specialtyId;
        			var typeId = this.specialtyTypeId;
        			var specialty = $(".tab-pane .special-label");
        			$.each(specialty, function() {
        				var specialtyTypeId = $(this).data("typeid");
        				var specialtyId = $(this).data("id");
        				if(specialtyTypeId==typeId&&specialtyId==id){
        					$(this).addClass("active");
        				}
        			})
    			}else if(this.type==1){
    				$('.specialty-custom .specialty-custom-submit').after($('#specialty-custom-item').render({id: this.id, name:this.name}))
    			}
    		});
    		$(".specialty-my .scroll").append($('#specialty-my-tmpl').render({dataList: list}));
    		
    		$(".auth-record tbody").append($('#auth-item-tmpl').render({dataList: list}));
    		$(".auth-record tbody .show-remark").click(showRemark);
    		
    		$('select[name="auth-specialty"]').html($('#specialty-item-tmpl').render({dataList: list}));
    		
    		/*点击自定义技能*/
        	$('.specialty-custom .specialty-custom-submit').click(function() {
        		var type = "1";
        		var name = $(".specialty-custom .specialty-custom-add").val();
        		if(name==""||name==null){
        			$.errorMsg("请输入自定义技能后再添加");
        			return false;
        		}
        		var me = $(this)
    			$.ajax({
    				url: '/specialty/user_specialty_add',
    				type:'post',
    				dataType: 'json',
    				data:$.param({
    					"type" : type,
    					"name" : name
    				}, true),
    				success: function(result){
    					console.log(result.data);
    					if(result.code == 200){
    						$.successMsg("添加成功！");
    						if(result.data !=null){
    							//me.after('<button class="btn specialty-custom-item" type="button" data-id="'+result.data.id+'" data-typeid="'+result.data.specialtyTypeId+'" data-name="'+result.data.name+'">'+result.data.name+'</button>')
    							me.after($('#specialty-custom-item').render({id: result.data.id, name:name}))
    							$(".specialty-my .scroll").append($('#specialty-my-item-tmpl').render({type: type, id: result.data.id, name:name}))

    							$("select[name='auth-specialty'] option[value='-1']").remove();
    							$("select[name='auth-specialty']").append('<option value="'+result.data.id+'" class="custom">'+result.data.name+'</option>');
    						}
    					}else{
    						$.errorMsg(result.message);
    					}
    				},
    				error: function(result){
    					$.errorMsg("无网络连接！请重试");
    				}
    			});
        	})
        	
    	} 
    }
    //选中增加技能
    function addSpecialtyUser(type, stid, sid,name, obj) {
    	if(obj.hasClass("active")){
    		return false;
    	}
    	$.ajax({
    		type:"post",
    		url:"/specialty/user_specialty_add",
    		data : $.param({
    			"type" : type,
    			"specialtyId" : sid,
    			"specialtyTypeId" : stid
    		}, true),
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") {
    				obj.addClass("active");
    				//response.data.name = name;
    				$(".specialty-my .scroll").append($('#specialty-my-item-tmpl').render({type: type, id: response.data.id, specialtyId: sid, specialtyTypeId: stid, name:name}))
    				$("select[name='auth-specialty'] option[value='-1']").remove();
					$("select[name='auth-specialty']").append('<option value="'+response.data.id+'">'+name+'</option>');
    			} else if ("607" == code) {
    				$.errorMsg("您尚未登录或登录已失效！");
    				logout();
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
    }

    return {
        //main function to initiate the module
        init: function() {
        	myskillsInit();
        	loadSpecialty();
        }

    };

}();

jQuery(document).ready(function() {
	myskills.init();
	$(window.document).on('click', '.specialty-my .scroll .special-label i.delete', function() {
		var type = $(this).parent().data("type");
		var id = $(this).parent().data("id");
		var sId = $(this).parent().data("specialtyid");
		var obj = $(this).parent();
		if(type!= "0"&& type!= "1"){
			$.errorMsg("参数不正确，请刷新页面再操作");
			return false;
		}
		$.ajax({
    		type:"post",
    		url:"/specialty/user_specialty_delete",
    		data:{
    			"id":id
    		},
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") { 
    				$.successMsg("删除成功！");
    				obj.remove();
    				if(type == "0"){
    					var specialty = $(".tab-pane .special-label");
            			$.each(specialty, function() {
            				var specialtyId = $(this).data("id");
            				if(specialtyId==sId){
            					$(this).removeClass("active");
            				}
            			})
    				}else if(type == "1"){
    					var specialty = $('.specialty-custom .right>a');
    					$.each(specialty, function() {
            				if($(this).data("id")==id){
            					$(this).remove();
            				}
            			})
    				}
    				$("select[name='auth-specialty'] option[value='"+id+"']").remove();
    			} else if ("607" == code) {
    				$.errorMsg("您尚未登录或登录已失效！");
    				logout();
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
	})
	
});