
$(function() {
	$("title").html("成为大牛 | 大牛社区 | AnyoneHelps");
	$(".nav5").addClass("active open");
	$(".nav51").addClass("active open");
	/*性别初始化  */
	$('input[name="sex"]').iCheck({
		checkboxClass: 'icheckbox_minimal-blue',
	    radioClass: 'iradio_minimal-blue',
	   	 increaseArea: '20%', // optional
	   	 check:function(){
	   		alert('Well done, Sir');
	   	 }
	});
	/*服务条款  */
	$('input[name="pro-service"]').iCheck({
		checkboxClass: 'icheckbox_minimal-blue',
		radioClass: 'iradio_minimal-blue',
		increaseArea: '20%', // optional
		check:function(){
			alert('Well done, Sir');
		}
	});
		
	/*身份证上传 */
	$('.file-upload input').fileupload({
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
	    	var obj = $(this).parent().parent();
	    	var code = data.result.code;
	    	if(code=="200"){
	    		if(data.result.data!=null){
	        		obj.hide();
	        		obj.parent().find('.upload-list').html($('#upload-item-tmpl').render(data.result.data));
	        		obj.parent().find('.delete').unbind("click"); //移除click
	        		obj.parent().find('.delete').click(function(){
	        			$(this).parent().parent().remove();
	        			obj.show();
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
		    	obj.find('.progress').css("display","none");
		  	}else{
		      	obj.find('.progress').css("display","block");
		 	}
		 	obj.find('.progress .bar').css(
		     	'width',
		    	progress + '%'
		 	);
	   	},
	});
	
	$(".submit-btn").click(function(){
		var realname = encodeURI($(":text[name='realname']").val());
		var sex = $(":radio[name='sex']:checked").val();
		var idUpload = $(".base-info .upload-list .item .title").data("id");
		var idUploadName = $(".base-info .upload-list .item .title").html();
		var proType = $("select[name='proType']").val();
		var pro = $("select[name='pro']").val();
		var proName = $("select[name='pro']").find("option:selected").text();
		var reason = encodeURI($("textarea[name='reason']").val());
		var educationUpload = $(".education-upload .upload-list .item .title").data("id");
		var educationUploadName = $(".education-upload .upload-list .item .title").html();
		var otherUpload = $(".other-upload .upload-list .item .title").data("id");
		var otherUploadName = $(".other-upload .upload-list .item .title").html();
		if(!$('input[name="pro-service"]').is(':checked')) {  
			$.errorMsg("您还未接受《服务条款》");
			return false;
		}
		if(idUpload==""||idUpload==null) {  
			$.errorMsg("请上传您的身份证明！");
			return false;
		}
		if(realname==""||realname==null) {  
			$.errorMsg("请填写您的真实姓名！");
			return false;
		}
		if(proType==""||proType==null) {  
			$.errorMsg("请选择申请专业领域类型！");
			return false;
		}
		if(pro==""||pro==null) {  
			$.errorMsg("请选择领域！");
			return false;
		}
		if(reason==""||reason==null) {  
			$.errorMsg("请填写您的申请理由！");
			return false;
		}
		if(educationUpload==""||educationUpload==null) {  
			$.errorMsg("请上传您的学历证明！");
			return false;
		}
		
		$.ajax({
			type : "post",
			url : "/pro_user/add",
			data : $.param({
				realname : realname,
				sex : sex,
				idUpload : idUpload,
				idUploadName : idUploadName,
				proTypeId : proType,
				proId : pro,
				reason : reason,
				educationUpload : educationUpload,
				educationUploadName : educationUploadName,
				otherUpload : otherUpload,
				otherUploadName : otherUploadName,
			}, true),
			success : function(response) {
				var code = response.code;
				if ("200" == code) {
					//$.successMsg("提交成功");
					var dlg = dialog({
						title : '',
						skin:"dlg-pay",
						/*width : 660,*/
						padding:"20px 20px 60px 40px",
						onshow : function() {
							var me = this;
							me.content($('#pay-tmpl').render({proName:proName}));
							$(".wclose,.ok").click(function(){
		    		    		dlg.close().remove();
		    		    		window.location.href= "/dashboard/Pro/list.jsp";
		    		    		return false;
		    		    	})
						}
					}).show();
				}else {
					$.errorMsg(response.message);
				}
			}
		});
	})
	
	$('select[name="proType"]').change(function(){
		$('select[name="pro"]').html($('#pro-tmpl').render({type: $(this).val()}));
	});
		

});