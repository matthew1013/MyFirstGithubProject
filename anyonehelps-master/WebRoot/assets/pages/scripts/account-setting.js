$.views.tags({
        "education": function(education){
        	switch (true){
        	case education == 1:
        		return '高中毕业或其他同等学历';
        	case education == 2:
        		return '大专学历';
        	case education == 3:
        		return '大学本科学历';
        	case education == 4:
        		return '硕士研究生学历';
        	case education == 5:
        		return '博士学位';
        	default:
        		return '未知';
        	}
        },
        "substrOfTitle": function(title){
        	if(title.length>128) return title.substr(0,128)+ '...';
        	else  return title;
        },
        "displayUploadBtn": function(dataList){
        	if(dataList.length<9) return "";
        	else  return 'class="hide"';
        },
        "setOptionSelected": function(optionValue,result){
        	if(result){
        		if(optionValue==result){
        			return "selected";
        		}
        	}
        	return "";
        },
        "questionFormat": function(index){
        	var result = "";
        	if(index == "1"){
        		result = '您母亲的姓名是？'
        	}else if(index == "2"){
        		result = '您父亲的姓名是？'
        	}else if(index == "3"){
        		result = '您配偶的姓名是？'
        	}else if(index == "4"){
        		result = '您的出生地是？'
        	}else if(index == "5"){
        		result = '您高中班主任的名字是？'
        	}else if(index == "6"){
        		result = '您初中班主任的名字是？'
        	}else if(index == "7"){
        		result = '您小学班主任的名字是？'
        	}else if(index == "8"){
        		result = '您的小学校名是？'
        	}else if(index == "9"){
        		result = '您父亲的生日是？'
        	}else if(index == "10"){
        		result = '您母亲的生日是？'
        	}else if(index == "11"){
        		result = '您配偶的生日是？'
        	}else if(index == "12"){
        		result = '您的学号（或工号）是？'
        	}
        	return result;
        },
     	 "fileFormat":function(file){
       		var format=/\.[^\.]+$/.exec(file); 
       		var result = "/assets/global/img/file-extension/unknow.png";
       		if(format==".ppt"||format==".pptx"||format==".PPT"||format==".PPTX"){
       			result = "/assets/global/img/file-extension/ppt.png";
       		}else if(format==".png"||format==".bmp"||format==".jpg"||format==".gif"||format==".jpeg"
       			||format==".PNG"||format==".BMP"||format==".JPG"||format==".GIF"||format==".JPEG"){
       			result = "/assets/global/img/file-extension/text.png";
       		}else if(format==".xls"||format==".xlsx"||format==".XLS"||format==".XLSX"){
       			result = "/assets/global/img/file-extension/xls.png";
       		}else if(format==".pdf"||format==".PDF"){
       			result = "/assets/global/img/file-extension/pdf.png";
       		}else if(format==".doc"||format==".docx"||format==".DOC"||format==".DOCX"){
       			result = "/assets/global/img/file-extension/doc.png";
       		}
       		return result;
       	 }
 });

$.views.helpers({
    "isPdf":function(file){
    	var result = false;
   		var format=/\.[^\.]+$/.exec(file); 
   		if(format==".pdf"||format==".PDF"){
   			result = true;
   		}
   		return result;
   	 }
});



/*金额整数每三位加一个逗号，可以有小数点*/
function balanceFormat(balance){
	balance = balance.toString();
	if(/[^0-9\.]/.test(balance)) return "$ 0.00";
	balance=balance.replace(/^(\d*)$/,"$1.");
	balance=(balance+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	balance=balance.replace(".",",");
	var re=/(\d)(\d{3},)/;
	while(re.test(balance)) balance=balance.replace(re,"$1,$2");
	balance=balance.replace(/,(\d\d)$/,".$1");
	return balance.replace(/^\./,"0.")
}


var accountSetting= function() {
	
	
	function  setOptionRadio(value,result){
    	if(result){
    		if(value==result){
    			return "checked";
    		}
    	}
    	return "";
    }
	
	//倒计时countdown 秒
    function settime(obj,countdown) { 
    	//var obj = $(".security-question-dlg .right .send-message");
    	if (countdown == 0) {   
    		 obj.data('enable','1');
    		 obj.html("获取验证码"); 
    		//countdown = 60; 
    	} else { 
    		obj.html("重新发送(" + countdown + ")"); 
    		//countdown--; 
    		setTimeout(function() { settime(obj,countdown-1) },1000) 
    	}
    } 
	
	function editWorkExperienceLink(){
		var obj = $(this).parent().parent();
		var id = $(this).data().id;
		var startDate =  obj.find(".start-date").html();
		var endDate = obj.find(".end-date").data("data");
		var company = obj.find(".company").html();
		var jobTitle = obj.find(".job-title").html();
		var jobDesc = obj.find(".job-desc").html();
		var enclosure = null;
		var enclosureName = null;
		var enclosureObj = obj.find(".enclosure");
		if(enclosureObj!=null){
			enclosure = enclosureObj.attr('href');
			enclosureName = enclosureObj.html();
		}
		$(this).parent().parent().next().remove();
		$(this).parent().parent().after($('#work-experience-edit-item').render({id:id,startDate:startDate,endDate:endDate,company:company,jobTitle:jobTitle,jobDesc:jobDesc,enclosure:enclosure,enclosureName:enclosureName}));
		
		$('a[name="editWorkExperience"]').unbind("click"); //移除click
		$('a[name="editWorkExperience"]').click(editWorkExperience);
		$('a[name="cancelWorkExperience"]').unbind("click");
		$('a[name="cancelWorkExperience"]').click(function(){
			$(this).parent().parent().before($('#work-experience-items').render({id:id,startDate:startDate,endDate:endDate,company:company,jobTitle:jobTitle,jobDesc:jobDesc,enclosure:enclosure,enclosureName:enclosureName}))
        	$('.work-experience-item').unbind("mouseover"); //移除mouseover
        	$('.work-experience-item').mouseover(function(){
        		$(this).css('border','1px solid #676767');
        		$(this).find('.btnOperation').css('display','block');
        	});
        	$('.work-experience-item').unbind("mouseout"); //移除mouseout
        	$('.work-experience-item').mouseout(function(){
        		$(this).css('border','');
        		 $(this).find('.btnOperation').css('display','none');
        	});
        	$('.work-experience-item').find('.delete').unbind("click"); //移除click
        	$('.work-experience-item').find('.delete').click(deleteWorkExperience);
        	
        	$('.work-experience-item').find('.edit').unbind("click"); //移除click
			$('.work-experience-item').find('.edit').click(editWorkExperienceLink);
			
			$(this).parent().parent().remove();
		});
		
		$(':file[name="work_experience_upload"]').fileupload({
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
	        		$.successMsg("上传成功");
	        		if(data.result.data!=null){
	        			$(this).parent().next().next().html(data.result.data.fileName);
	        			$(this).parent().next().next().data("link",data.result.data.saveFileName);
	        			$(this).parent().next().next().show();
	        			$(this).parent().hide();
	        			$(this).parent().next().hide();
	        			$(this).parent().next().next().next().show();
	        			//删除
	        			$(this).parent().next().next().next().click(function(){
	        				$(this).prev().prev().prev().show();
	        				$(this).prev().prev().show();
	        				$(this).prev().data("link","");
	        				$(this).prev().html("");
	        				$(this).next().find('.bar').css('width','0%');
	        				$(this).next().show();
	        				$(this).hide();
	        			});
	        			
	        		}
	        	}else{
	        		$.errorMsg(data.result.message);
	        	}
	        },
	        
	        progressall: function (e, data) {
		        var progress = parseInt(data.loaded / data.total * 100, 10);
		        if(progress=100){
		        	$(this).parent().next().next().next().next().hide();
		        }else{
		        	$(this).parent().next().next().next().next().show();
		        }
		        $(this).parent().next().next().next().next().find('.bar').css(
		            'width',
		            progress + '%'
		        );
	   		},
	    });
		
		$(':text[name="startDate"]').datetimepicker({
            language: 'zh-CN',
            autoclose: true,
            todayBtn: 1,
            weekStart: 1,  
            startView: 3,  
            minView: 3,  
            format: 'yyyy-mm'
           
        });
		$(':text[name="endDate"]').datetimepicker({
            language: 'zh-CN',
            autoclose: 1,
            todayBtn: 1,
            weekStart: 1,  
            startView: 3,  
            minView: 3,  
            format: 'yyyy-mm'
           
        });
		
		$(".we-enclosure-remove").click(function(){
			$(this).prev().prev().prev().show();
			$(this).prev().prev().show();
			$(this).prev().data("link","");
			$(this).prev().html("");
			$(this).next().find('.bar').css('width','0%');
			$(this).next().show();
			$(this).hide();
		});
		$(this).parent().parent().remove();
	}
	
	function editWorkExperience(){
		var obj = $(this).parent().parent();
		
		var id = obj.data("id");
		var startDate = obj.find("input[name='startDate']").val();
		var endDate = obj.find("input[name='endDate']").val();
		var company = obj.find("input[name='company']").val();
		
		var jobTitle = obj.find("input[name='jobTitle']").val();
		var jobDesc = obj.find("textarea[name='jobDesc']").val();
		var enclosure = obj.find("span[name='work-experience-file-name']").data('link');
		var enclosureName = obj.find("span[name='work-experience-file-name']").html();
		$.ajax({
    		type:"post",
    		url:"/work_experience/modify", 
    		data:$.param({
    			"id":id,
				"startDate":startDate,
				"endDate":endDate,
				"company":company,
				"jobTitle":jobTitle,
				"jobDesc":jobDesc,
				"enclosure":enclosure,
				"enclosureName":enclosureName
			},true),
    		success:function(response) {
    			var code = response.code;
    			console.log(response.data);
    			if (code == "200") {
    				$.successMsg("修改成功");
    				//alert($(this).parent().parent().html());
    				obj.before($('#work-experience-items').render({id:id,startDate:startDate,endDate:endDate,company:company,jobTitle:jobTitle,jobDesc:jobDesc,enclosure:enclosure,enclosureName:enclosureName}))
    	        	$('.work-experience-item').unbind("mouseover"); //移除mouseover
    	        	$('.work-experience-item').mouseover(function(){
    	        		$(this).css('border','1px solid #676767');
    	        		$(this).find('.btnOperation').css('display','block');
    	        	});
    	        	$('.work-experience-item').unbind("mouseout"); //移除mouseout
    	        	$('.work-experience-item').mouseout(function(){
    	        		$(this).css('border','');
    	        		 $(this).find('.btnOperation').css('display','none');
    	        	});
    	        	$('.work-experience-item').find('.delete').unbind("click"); //移除click
    	        	$('.work-experience-item').find('.delete').click(deleteWorkExperience);
    	        	
    	        	$('.work-experience-item').find('.edit').unbind("click"); //移除click
    				$('.work-experience-item').find('.edit').click(editWorkExperienceLink);
    				obj.remove();
    			} else if ("607" == code) {
    				$.errorMsg("您尚未登录或登录已失效！");
    				logout();
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
		return false;
	};
	

	function editEducationLink(){
		var id = $(this).data().id;
		var obj = $(this).parent().parent();
		var education = obj.find(".education").data("education");
		var startDate = obj.find(".start-date").html();
		var endDate = obj.find(".end-date").data("data");
		var school = obj.find(".school").html();
		var major = obj.find(".major").html();
		var majorDesc = obj.find(".major-desc").html();
		var enclosure = null;
		var enclosureName = null;
		var enclosureObj = obj.find(".enclosure");
		if(enclosureObj!=null){
			enclosure = enclosureObj.attr('href');
			enclosureName = enclosureObj.html();
		}
		$(this).parent().parent().next().remove();
		$(this).parent().parent().after($('#education-edit-item').render({id:id,startDate:startDate,endDate:endDate,education:education,school:school,major:major,majorDesc:majorDesc,enclosure:enclosure,enclosureName:enclosureName}));
		
		$('a[name="edit"]').unbind("click"); //移除click
		$('a[name="edit"]').click(editEducation);
		$('a[name="cancel"]').unbind("click");
		$('a[name="cancel"]').click(function(){
			$(this).parent().parent().before($('#education-items').render({id:id,education:education,startDate:startDate,endDate:endDate,school:school,major:major,majorDesc:majorDesc,enclosure:enclosure,enclosureName:enclosureName}))
        	$('.education-item').unbind("mouseover"); //移除mouseover
        	$('.education-item').mouseover(function(){
        		$(this).css('border','1px solid #676767');
        		$(this).find('.btnOperation').css('display','block');
        	});
        	$('.education-item').unbind("mouseout"); //移除mouseout
        	$('.education-item').mouseout(function(){
        		$(this).css('border','');
        		 $(this).find('.btnOperation').css('display','none');
        	});
        	$('.education-item').find('.delete').unbind("click"); //移除click
        	$('.education-item').find('.delete').click(deleteEducation);
        	
        	$('.education-item').find('.edit').unbind("click"); //移除click
			$('.education-item').find('.edit').click(editEducationLink);
			
			$(this).parent().parent().remove();
		});
		
		$(':file[name="education_upload"]').fileupload({
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
	        		$.successMsg("上传成功");
	        		if(data.result.data!=null){
	        			$(this).parent().next().next().html(data.result.data.fileName);
	        			$(this).parent().next().next().data("link",data.result.data.saveFileName);
	        			$(this).parent().next().next().show();
	        			$(this).parent().hide();
	        			$(this).parent().next().hide();
	        			//alert($(this).parent().next().data("link"));
	        			$(this).parent().next().next().next().show();
	        			//删除
	        			$(this).parent().next().next().next().click(function(){
	        				$(this).prev().prev().prev().show();
	        				$(this).prev().prev().show();
	        				$(this).prev().data("link","");
	        				$(this).prev().html("");
	        				$(this).next().find('.bar').css('width','0%');
	        				$(this).next().show();
	        				$(this).hide();
	        			});
	        			
	        		}
	        	}else{
	        		$.errorMsg(response.message);
	        	}
	        },
	        
	        progressall: function (e, data) {
		        var progress = parseInt(data.loaded / data.total * 100, 10);
		        if(progress=100){
		        	$(this).parent().next().next().next().next().hide();
		        }else{
		        	$(this).parent().next().next().next().next().show();
		        }
		        $(this).parent().next().next().next().next().find('.bar').css(
		            'width',
		            progress + '%'
		        );
	   		},
	    });
		
		$(':text[name="startDate"]').datetimepicker({
            language: 'zh-CN',
            autoclose: true,
            todayBtn: 1,
            weekStart: 1,  
            startView: 3,  
            minView: 3,  
            format: 'yyyy-mm'
           
        });
		$(':text[name="endDate"]').datetimepicker({
            language: 'zh-CN',
            autoclose: 1,
            todayBtn: 1,
            weekStart: 1,  
            startView: 3,  
            minView: 3,  
            format: 'yyyy-mm'
           
        });
		
		$(".enclosure-remove").click(function(){
			$(this).prev().prev().prev().show();
			$(this).prev().prev().show();
			$(this).prev().data("link","");
			$(this).prev().html("");
			$(this).next().find('.bar').css('width','0%');
			$(this).next().show();
			$(this).hide();
		});
		
		$(this).parent().parent().remove();
	}
	function editEducation(){
		var obj = $(this).parent().parent();
		var id = obj.data("id");
		var education = obj.find("select[name='education']").val();
		var startDate = obj.find("input[name='startDate']").val();
		var endDate = obj.find("input[name='endDate']").val();
		var school = obj.find("input[name='school']").val();
		var major = obj.find("input[name='major']").val();
		var majorDesc = obj.find("textarea[name='majorDesc']").val();
		var enclosure = obj.find("span[name='education-file-name']").data('link');
		var enclosureName = obj.find("span[name='education-file-name']").html();
		$.ajax({
    		type:"post",
    		url:"/education/modify", 
    		data:$.param({
    			"id":id,
				"education":education,
				"startDate":startDate,
				"endDate":endDate,
				"school":school,
				"major":major,
				"majorDesc":majorDesc,
				"enclosure":enclosure,
				"enclosureName":enclosureName
			},true),
    		success:function(response) {
    			var code = response.code;
    			console.log(response.data);
    			if (code == "200") {
    				$.successMsg("修改成功");
    				//alert($(this).parent().parent().html());
    				obj.before($('#education-items').render({id:id,education:education,startDate:startDate,endDate:endDate,school:school,major:major,majorDesc:majorDesc,enclosure:enclosure,enclosureName:enclosureName}))
    	        	$('.education-item').unbind("mouseover"); //移除mouseover
    	        	$('.education-item').mouseover(function(){
    	        		$(this).css('border','1px solid #676767');
    	        		$(this).find('.btnOperation').css('display','block');
    	        	});
    	        	$('.education-item').unbind("mouseout"); //移除mouseout
    	        	$('.education-item').mouseout(function(){
    	        		$(this).css('border','');
    	        		 $(this).find('.btnOperation').css('display','none');
    	        	});
    	        	$('.education-item').find('.delete').unbind("click"); //移除click
    	        	$('.education-item').find('.delete').click(deleteEducation);
    	        	
    	        	$('.education-item').find('.edit').unbind("click"); //移除click
    				$('.education-item').find('.edit').click(editEducationLink);
    				obj.remove();
    			} else if ("607" == code) {
    				$.errorMsg("您尚未登录或登录已失效！");
    				logout();
    			} else {
    				// 失败
    				$.errorMsg(response.message);
    			}
    		}
    	});
		return false;
	};
	
	function deleteEducation(){
		var obj = $(this).parent().parent();
		var id = $(this).data().id;
		
		var d = dialog({
		    title: '提示',
		    width:'400px',
		    skin: 'delete-tip-dlg',
		    content: '您确定在删除该项吗?',
		    okValue: '确定',
		    ok: function () {
		        this.title('提交中…');
		        $.ajax({
		    		type:"post",
		    		url:"/education/delete",
		    		data:$.param({
						"id":id
					},true),
		    		success:function(response) {
		    			var code = response.code;
		    			if (code == "200") {
		    				$.successMsg("教育经验删除成功");
		    				obj.next().remove();
		    				obj.remove();
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
		
		return false;
	};
	
	function deleteWorkExperience(){
		var obj = $(this).parent().parent();
		var id = $(this).data().id;
		var d = dialog({
		    title: '提示',
		    width:'400px',
		    skin: 'delete-tip-dlg',
		    content: '您确定在删除该项吗?',
		    okValue: '确定',
		    ok: function () {
		        this.title('提交中…');
		        $.ajax({
		    		type:"post",
		    		url:"/work_experience/delete",
		    		data:$.param({
						"id":id
					},true),
		    		success:function(response) {
		    			var code = response.code;
		    			if (code == "200") {
		    				$.successMsg("工作经历删除成功");
		    				obj.next().remove();
		    				obj.remove();
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
		
	};
	
	var accountSettingInit = function(){
		$("title").html("我的资料 | 用户中心 | AnyoneHelps");
		$(".nav1").addClass("active open");
		$(".nav11").addClass("active open");
		
		$(".show-base-info").click(function(){
			if($(this).find("i").hasClass("icon-minus")){
				$(".base-info").addClass("hide");
				$(this).find("i").removeClass("icon-minus");
				$(this).find("span").html("填写基本资料");
			}else{
				$(".base-info").removeClass("hide");
				$(this).find("i").addClass("icon-minus"); 
				$(this).find("span").html("收起基本资料");
			}
			
		})
		
		$(".show-high-info").click(function(){
			$(".high-info").removeClass("hide");
			$(".hide-high-info").parent().parent().removeClass("hide");
			$(".show-high-info").parent().parent().addClass("hide");
		})
		$(".hide-high-info").click(function(){
			$(".high-info").addClass("hide");
			$(".show-high-info").parent().parent().removeClass("hide");
			$(".hide-high-info").parent().parent().addClass("hide");
		})
		
		function saveEducation(){
			var obj = $(this).parent().parent();
			var education = obj.find("select[name='education']").val();
			var startDate = obj.find("input[name='startDate']").val();
			var endDate = obj.find("input[name='endDate']").val();
			var school = obj.find("input[name='school']").val();
			var major = obj.find("input[name='major']").val();
			var majorDesc = obj.find("textarea[name='majorDesc']").val();
			var enclosure = obj.find("span[name='education-file-name']").data('link');
			var enclosureName = obj.find("span[name='education-file-name']").html();
			$.ajax({
        		type:"post",
        		url:"/education/add",
        		data:$.param({
    				"education":education,
    				"startDate":startDate,
    				"endDate":endDate,
    				"school":school,
    				"major":major,
    				"majorDesc":majorDesc,
    				"enclosure":enclosure,
    				"enclosureName":enclosureName
    			},true),
        		success:function(response) {
        			var code = response.code;
        			console.log(response.data);
        			if (code == "200") {
        				$.successMsg("保存成功");
        				obj.remove();
        				$('#education-list').append($('#education-items').render({id:response.data.id,education:education,startDate:startDate,endDate:endDate,school:school,major:major,majorDesc:majorDesc,enclosure:enclosure,enclosureName:enclosureName}));
        				$('.education-item').unbind("mouseover"); //移除mouseover
        				$('.education-item').mouseover(function(){
        					 $(this).css('border','1px solid #676767');
        					 $(this).find('.btnOperation').css('display','block');
        				});
        				$('.education-item').unbind("mouseout"); //移除mouseout
        				$('.education-item').mouseout(function(){
        					 $(this).css('border','');
        					 $(this).find('.btnOperation').css('display','none');
        				});
        				$('.education-item').find('.delete').unbind("click"); //移除click
        				$('.education-item').find('.delete').click(deleteEducation);
        			} else if ("607" == code) {
        				$.errorMsg("您尚未登录或登录已失效！");
        				logout();
        			} else {
        				// 失败
        				$.errorMsg(response.message);
        			}
        		}
        	});
			return false;
		};
		
		
		
		$('#education-link').click(function(){
			$('#education-add').append($('#education-add-item').render());
			$('a[name="saveEducation"]').unbind("click"); //移除click
			$('a[name="saveEducation"]').click(saveEducation);
			$('a[name="cancelEducation"]').click(function(){
				$(this).parent().parent().remove();
			});
			
			$(':file[name="education_upload"]').fileupload({
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
		        		$.successMsg("上传成功");
		        		if(data.result.data!=null){
		        			$(this).parent().next().next().html(data.result.data.fileName);
		        			$(this).parent().next().next().data("link",data.result.data.saveFileName);
		        			$(this).parent().next().next().show();
		        			$(this).parent().hide();
		        			$(this).parent().next().hide();
		        			//alert($(this).parent().next().data("link"));
		        			$(this).parent().next().next().next().show();
		        			//删除
		        			$(this).parent().next().next().next().click(function(){
		        				$(this).prev().prev().prev().show();
		        				$(this).prev().prev().show();
		        				$(this).prev().data("link","");
		        				$(this).prev().html("");
		        				$(this).next().find('.bar').css('width','0%');
		        				$(this).next().show();
		        				$(this).hide();
		        			});
		        			
		        		}
		        	}else{
		        		$.errorMsg(data.result.message);
		        	}
		        },progressall: function (e, data) {
			        var progress = parseInt(data.loaded / data.total * 100, 10);
			        if(progress=100){
			        	$(this).parent().next().next().next().next().hide();
			        }else{
			        	$(this).parent().next().next().next().next().show();
			        }
			        $(this).parent().next().next().next().next().find('.bar').css(
			            'width',
			            progress + '%'
			        );
		   		},
		    });
			
			$(':text[name="startDate"]').datetimepicker({
	            language: 'zh-CN',
	            autoclose: true,
	            todayBtn: 1,
	            weekStart: 1,  
	            startView: 3,  
	            minView: 3,  
	            format: 'yyyy-mm'
	           
	        });
			$(':text[name="endDate"]').datetimepicker({
	            language: 'zh-CN',
	            autoclose: 1,
	            todayBtn: 1,
	            weekStart: 1,  
	            startView: 3,  
	            minView: 3,  
	            format: 'yyyy-mm'
	           
	        });
		});
		
		function saveWorkExperience(){
			var obj = $(this).parent().parent();
			var startDate = obj.find("input[name='startDate']").val();
			var endDate = obj.find("input[name='endDate']").val();
			var company = obj.find("input[name='company']").val();
			var jobTitle = obj.find("input[name='jobTitle']").val();
			var jobDesc = obj.find("textarea[name='jobDesc']").val();
			var enclosure = obj.find("span[name='work-experience-file-name']").data('link');
			var enclosureName = obj.find("span[name='work-experience-file-name']").html();
			$.ajax({
        		type:"post",
        		url:"/work_experience/add",
        		data:$.param({
    				"startDate":startDate,
    				"endDate":endDate,
    				"company":company,
    				"jobTitle":jobTitle,
    				"jobDesc":jobDesc,
    				"enclosure":enclosure,
    				"enclosureName":enclosureName
    			},true),
        		success:function(response) {
        			var code = response.code;
        			if (code == "200") {
        				$.successMsg("保存成功");
        				$('#work-experience-list').append($('#work-experience-items').render({id:response.data.id,startDate:startDate,endDate:endDate,company:company,jobTitle:jobTitle,jobDesc:jobDesc,enclosure:enclosure,enclosureName:enclosureName}));
        				obj.remove();
        				$('.work-experience-item').unbind("mouseover"); //移除mouseover
        				$('.work-experience-item').mouseover(function(){
        					 $(this).css('border','1px solid #676767');
        					 $(this).find('.btnOperation').css('display','block');
        				});
        				$('.work-experience-item').unbind("mouseout"); //移除mouseout
        				$('.work-experience-item').mouseout(function(){
        					 $(this).css('border','');
        					 $(this).find('.btnOperation').css('display','none');
        				});
        				$('.work-experience-item').find('.delete').unbind("click"); //移除click
        				$('.work-experience-item').find('.delete').click(deleteWorkExperience);
        				
        			} else if ("607" == code) {
        				$.errorMsg("您尚未登录或登录已失效！");
        				logout();
        			} else {
        				// 失败
        				$.errorMsg(response.message);
        			}
        		}
        	});
			return false;
		};
		
		$('#work-experience-link').click(function(){
			$('#work-experience-add').append($('#work-experience-add-item').render());
			$('a[name="saveWorkExperience"]').unbind("click"); //移除click
			$('a[name="saveWorkExperience"]').click(saveWorkExperience);
			$('a[name="cancelWorkExperience"]').click(function(){
				$(this).parent().parent().remove();
			});
			
			$(':file[name="work_experience_upload"]').fileupload({
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
		        		$.successMsg("上传成功");
		        		if(data.result.data!=null){
		        			$(this).parent().next().next().html(data.result.data.fileName);
		        			$(this).parent().next().next().data("link",data.result.data.saveFileName);
		        			$(this).parent().next().next().show();
		        			$(this).parent().hide();
		        			$(this).parent().next().hide();
		        			$(this).parent().next().next().next().show();
		        			//删除
		        			$(this).parent().next().next().next().click(function(){
		        				$(this).prev().prev().prev().show();
		        				$(this).prev().prev().show();
		        				$(this).prev().data("link","");
		        				$(this).prev().html("");
		        				$(this).next().find('.bar').css('width','0%');
		        				$(this).next().show();
		        				$(this).hide();
		        			});
		        			
		        		}
		        	}else{
		        		$.errorMsg(data.result.message);
		        	}
		        },
		        
		        progressall: function (e, data) {
			        var progress = parseInt(data.loaded / data.total * 100, 10);
			        if(progress=100){
			        	$(this).parent().next().next().next().next().hide();
			        }else{
			        	$(this).parent().next().next().next().next().show();
			        }
			        $(this).parent().next().next().next().next().find('.bar').css(
			            'width',
			            progress + '%'
			        );
		   		},
		    });
			
			$(':text[name="startDate"]').datetimepicker({
	            language: 'zh-CN',
	            autoclose: 1,
	            todayBtn: 1,
	            weekStart: 1,  
	            startView: 3,  
	            minView: 3,  
	            format: 'yyyy-mm'
	           
	        });
			$(':text[name="endDate"]').datetimepicker({
	            language: 'zh-CN',
	            autoclose: 1,
	            todayBtn: 1,
	            weekStart: 1,  
	            startView: 3,  
	            minView: 3,  
	            format: 'yyyy-mm'
	           
	        });
		});
		
		
		
	}
    var accountInfo = function() {
    	
    	function deleteWorks(){
			var obj = $(this).parent().parent();
			var id = $(this).data().id;
			
			var d = dialog({
			    title: '提示',
			    width:'400px',
			    skin: 'delete-tip-dlg',
			    content: '您确定在删除该项吗?',
			    okValue: '确定',
			    ok: function () {
			        this.title('提交中…');
			        $.ajax({
		        		type:"post",
		        		url:"/works/delete",
		        		data:$.param({
		    				"id":id
		    			},true),
		        		success:function(response) {
		        			var code = response.code;
		        			if (code == "200") {
		        				$.successMsg("删除成功");
		        				$('#works-add').removeClass("hide");
		        				$('#work-format').removeClass("hide");
		        				obj.remove();
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
			return false;
		};
		
		
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
        				//alert("您尚未登录或登录已失效！");
        				//logout();
        			} else {
        				// 失败
        				$.errorMsg("获取用户技能信息失败，原因是:" + response.message);
        			}
        		}
        	});
        }
        //显示用户拥有技能
        function showSpecialtyUser(list) {
        	var str = '';
        	if (list != null) {
        		str += '<li><div class="" style="padding-left: 10px;color: #676767;">我的技能:</div></li>';
        		$.each(list, function(index) {
        			if(this.type==0){
        				if(this.specialty!=null){
            				str += '<li><div class="specialty">'+this.specialty.name +'</div></li>';
            			} 
        			}else if(this.type==1){
        				str += '<li><div class="specialty">'+this.name +'</div></li>';
        			}
        			
        		});
        	} 
        	if(str==''){
        		str += '<li><div class="" style="padding-left: 10px;">我的技能:</div></li>';
        		str += '<li><div class="specialty">暂无技能...</div></li>';
        	}
        	$(".specialty-list").html(str);
        }
        function loadUserInfo(){
        	// 获取用户数据 
        	$.ajax({
        		type:"get",
        		url:"/user/get_self",
        		success:function(response) {
        			var code = response.code;
        			if (code == "200") {
        				var data = response.data;
        				console.log(response.data);
        				$("#email").val(data.email);
        				$(".area-code").val(data.areaCode);
        				$("#telphone").val(data.telphone);
        				$("select[name='nationality'] option[value='"+ data.country + "']").attr("selected", true);
        				$("select[name='degree']").val(data.degree);
        				$("#province").val(data.province);
        				$("#city").val(data.city);
        				$("#otherContact").val(data.otherContact);
        				$("#brief").val(data.brief);
        				//$("#user-grade").html(calculateGrade(data.grade));
        				//$("#amount").html(balanceFormat(data.usdBalance));    //haokun modified 2017/03/03
						$("#amount").html(data.usdBalance);    //haokun modified 2017/03/03
        				$("#occupation").val(data.occupation);
        				$("#wechat").val(data.wechat);
        				$("#zipCode").val(data.zipCode);
        				$("#user-occupation").html(data.occupation);
        				$("#user-grade").html("Lv."+calculateGrade(data.grade));
        				
        				var sexStr = '<div class="sex">';
        				sexStr += '<input type="radio" name="sex" value="1" '+setOptionRadio("1",data.sex)+'/>';
        				sexStr += '<span>男性</span>';
        				sexStr += '</div>';
        				sexStr += '<div class="sex margin-left-21">';
        				sexStr += '<input type="radio" name="sex" value="2" '+setOptionRadio("2",data.sex)+'/>';
        				sexStr += '<span>女性</span>';
        				sexStr += '</div>';
        				sexStr += '<div class="sex margin-left-21">';
        				sexStr += '<input type="radio" name="sex" value="3" '+setOptionRadio("3",data.sex)+'/>';
        				sexStr += '<span>其他</span>';
        				sexStr += '</div>';
        				sexStr += '<div class="sex margin-left-21">';
        				sexStr += '<input type="radio" name="sex" value="0" '+setOptionRadio("0",data.sex)+'/>';
        				sexStr += '<span>保密</span>';
        				sexStr += '</div>';
        				
        				$('.sex-list').html(sexStr);
        				/*性别初始化*/ 
        				$('input[name="sex"]').iCheck({
        					checkboxClass: 'icheckbox_minimal-blue',
        			    	radioClass: 'iradio_minimal-blue',
        			   	 	increaseArea: '20%', // optional
        			   	 	check:function(){
        			   	 	  alert('Well done, Sir');
        			   	 	}
        				}); 
        				
        				var telphoneState = data.telphoneState
        				if(data.securityQuestion>0){
        					$(".show-security").addClass("hide");
        					$(".set-security-question").removeClass("hide");
        				}else{
        					$(".show-security").click(function(){
        						if(telphoneState==1){
        							var d = dialog({
            						    title: '设置保密问题',
            						    width:'500px',
            						    skin: 'security-question-dlg',
            						    content: $('#security-question-tmpl').render({question:$('#question-tmpl').render({index1:"",index2:"",value:""})}),
            						    okValue: '提交',
            						    ok: function () {
            						    	var vcode = $(".security-question-dlg .security-question .phonecode").val();
            						    	var index1 = "";
            						    	var index2 = "";
            						    	var index3 = "";
            						    	var answer1 = "";
            						    	var answer2 = "";
            						    	var answer3 = "";
            						    	$(".security-question-dlg .security-question .question select").each(function(index,item){
            						    		if(index==0){
            						    			index1 = $(item).val();
            						    		}
            						    		if(index==1){
            						    			index2 = $(item).val();
            						    		}
            						    		if(index==2){
            						    			index3 = $(item).val();
            						    		}
            						    	});
            						    	$(".security-question-dlg .security-question .question input").each(function(index,item){
            						    		if(index==0){
            						    			answer1 = $(item).val();
            						    		}
            						    		if(index==1){
            						    			answer2 = $(item).val();
            						    		}
            						    		if(index==2){
            						    			answer3 = $(item).val();
            						    		}
            						    	});
            						    	$.ajax({
            					    			type : "post",
            					    			url : "/asq/add",
            					    			data : {
            					    				"index1" : index1,
            					    				"index2" : index2,
            					    				"index3" : index3,
            					    				"answer1" : answer1,
            					    				"answer2" : answer2,
            					    				"answer3" : answer3,
            					    				"vcode" : vcode
            					    			},
            					    			success : function(response) {
            					    				var code = response.code;
            					    				if ("200" == code) {
            					    					$.successMsg("提交成功");
            					    					$(".show-security").addClass("hide");
            				        					$(".set-security-question").removeClass("hide");
            				        					d.close();
            					    				} else {
            					        				$.errorMsg(response.message);
            					    				}
            					    			}
            					    		});
            						    	return false;
            						    },
            						    cancelValue: '取消',
            						    cancel: function () {}
            						});
            						d.showModal();
            						
            						$(".security-question-dlg .security-question .question select").click(function() {
                			        	var me = $(this);
                			        	var index1;
                				    	var index2;
                				    	var index3;
                				    	$(".security-question-dlg .security-question .question select").each(function(index,item){
                			        		if(index==0){
                				    			index1 = $(item);
                				    		}
                				    		if(index==1){
                				    			index2 = $(item);
                				    		}
                				    		if(index==2){
                				    			index3 = $(item);
                				    		}
                				    	});
                			        	if(index1.is(me)){
                			        		$(index2).html($('#question-tmpl').render({index1:index1.val(),index2:index3.val(),value:index2.val()}));
                			        		$(index3).html($('#question-tmpl').render({index1:index1.val(),index2:index2.val(),value:index3.val()}));
                			        	}else if(index2.is(me)){
                			        		$(index1).html($('#question-tmpl').render({index1:index2.val(),index2:index3.val(),value:index1.val()}));
                			        		$(index3).html($('#question-tmpl').render({index1:index1.val(),index1:index2.val(),value:index3.val()}));
                			        	}else if(index3.is(me)){ 
                			        		$(index1).html($('#question-tmpl').render({index1:index2.val(),index2:index3.val(),value:index1.val()}));
                			        		$(index2).html($('#question-tmpl').render({index1:index1.val(),index2:index3.val(),value:index2.val()}));
                			        	}
                			        });
            						
            						$(".security-question-dlg .right .send-message").click(function(){
            							var enable = $(this).data("enable");
            				        	if(enable=="0"){
            				        		return false;
            				        	}
            				    		me = $(this);
            							$.ajax({
            				        		type:"get",
            				        		url:"/asq/get_phone_code",
            				        		success:function(response) {
            				        			var code = response.code;
            				        			if (code == "200") {
            				        				$.successMsg("验证码发送成功,请查看手机,并填入验证码");
            				        				//按钮不可用
            				    		    		me.data('enable','0');
            				    		    		settime(me,60);
            				        			} else if ("607" == code) {
            				        				alert("您尚未登录或登录已失效！");
            				        				logout();
            				        			} else {
            				        				$.errorMsg(response.message);
            				        			}
            				        		}
            				        	});
            						})
        						}else{
        							$.errorMsg("请验证后手机后刷新页面再设置保密问题！");
        						}
        						
        					})
        				}
        				//$("#profile-userpic").attr('src',baseUrl+data.picUrl); 
        				if(data.telphoneState==1)//电话状态已经验证
        				{
        					 $("#telphone").attr("readonly","true");
        					 $(".area-code").attr("disabled","disabled");
        					 $("#phoneflag").removeClass("hide");
        					 $(".phoneModify").removeClass("hide");
        					 $(".phoneModify").click(showModifyPhoneDlg);
        					 
        				}else{
        					$("#phoneverify").removeClass("hide");
        				}
        				
        				if(data.emailState==1)//邮箱状态已经验证
        				{
        					 $("#email").attr("readonly","true");
        					 $("#emailflag").removeClass("hide");
        					 $(".emailModify").removeClass("hide");
        					 $(".emailModify").click(showModifyEmailDlg);
        				}else{
       					 	$("#emailverify").removeClass("hide");
        				}
        				
        				$("#emailverify").click(verifyflag);
        				$("#phoneverify").click(verifyflag);
        				
        				//$("#emailverifycode").click(function() {
        				//	verifycodeflag(1);
        				//});
        				$("#phoneverifycode").click(verifycodeflag);
        				$("#saveInfo").click(function() {
        					saveInfo();
        				});
        				//alert($('#education-items-tmpl').render({dataList: data.education}));
        				$('#education-list').html($('#education-items-tmpl').render({dataList: data.education}));
        				$('#work-experience-list').html($('#work-experience-items-tmpl').render({dataList: data.we}));
        				$('#works').html($('#works-items-tmpl').render({dataList: data.works}));
        				$('a[name="delete-works"]').unbind("click"); //移除click
        				$('a[name="delete-works"]').click(deleteWorks);
        				
        				$('.education-item').unbind("mouseover"); //移除mouseover
        				$('.education-item').mouseover(function(){
        					 $(this).css('border','1px solid #676767');
        					 $(this).find('.btnOperation').css('display','block');
        				});
        				$('.education-item').unbind("mouseout"); //移除mouseout
        				$('.education-item').mouseout(function(){
        					 $(this).css('border','');
        					 $(this).find('.btnOperation').css('display','none');
        				});
        				$('.education-item').find('.delete').unbind("click"); //移除click
        				$('.education-item').find('.delete').click(deleteEducation);
        				
        				$('.education-item').find('.edit').unbind("click"); //移除click
        				$('.education-item').find('.edit').click(editEducationLink);
        				
        				//
        				$('.work-experience-item').unbind("mouseover"); //移除mouseover
        				$('.work-experience-item').mouseover(function(){
        					 $(this).css('border','1px solid #676767');
        					 $(this).find('.btnOperation').css('display','block');
        				});
        				$('.work-experience-item').unbind("mouseout"); //移除mouseout
        				$('.work-experience-item').mouseout(function(){
        					 $(this).css('border','');
        					 $(this).find('.btnOperation').css('display','none');
        				});
        				$('.work-experience-item').find('.delete').unbind("click"); //移除click
        				$('.work-experience-item').find('.delete').click(deleteWorkExperience);
        				
        				$('.work-experience-item').find('.edit').unbind("click"); //移除click
        				$('.work-experience-item').find('.edit').click(editWorkExperienceLink);
        				
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
        			        		$.successMsg("上传成功");
        			        		if(data.result.data!=null){
        			        			$('#works-list').append($('#works-tmpl').render({dataList: data.result.data}));
        			        			if($('#works-list').children().length>=9){
        			        				$('#works-add').addClass("hide");
        			        				$('#works-progress').addClass("hide");
        			        				$('#work-format').addClass("hide");
        			        			}
        			        			$('a[name="delete-works"]').unbind("click"); //移除click
        		        				$('a[name="delete-works"]').click(deleteWorks);
        			        		}
        			        	}else{
        			        		$.errorMsg(data.result.message);
        			        	}
        			        },
        			        
        			        progressall: function (e, data) {
        				        var progress = parseInt(data.loaded / data.total * 100, 10);
        				        if(progress=100){
        				        	$('#works-progress').addClass("hide");
        				        }else{
        				        	$('#works-progress').removeClass("hide");
        				        }
        				        $('#progress .bar').css(
        				            'width',
        				            progress + '%'
        				        );
        			   		},
        			    });
        				
        			} else if ("607" == code) {
        				//alert("您尚未登录或登录已失效！");
        				//logout();
        			} else {
        				// 失败
        				$.errorMsg("获取我的资料信息失败，原因是:" + response.message);
        			}
        		}
        	});
        }	
        
        var showModifyPhoneDlg = function(){
        	$.ajax({
        		type:"get",
        		url:"/asq/get_asq",
        		success:function(response) {
        			var code = response.code;
        			if (code == "200") {
        				if(response.data!=null){
        					var d = dialog({
        					    title: '修改手机号码',
        					    width:'500px',
        					    skin: 'modify-phone-dlg',
        					    content: $('#modify-phone-tmpl').render({asq:response.data}),
        					    okValue: '提交',
        					    ok: function () {
        					    	var areaCode = $(".modify-phone-dlg .modify-phone .area-code").val();
        					    	var phone = $(".modify-phone-dlg .modify-phone .phone").val();
        					    	var vcode = $(".modify-phone-dlg .modify-phone .phonecode").val();
        					    	var answer1 = "";
        					    	var answer2 = "";
        					    	var answer3 = "";
        					    	$(".modify-phone-dlg .modify-phone .question input").each(function(index,item){
        					    		if(index==0){
        					    			answer1 = $(item).val();
        					    		}
        					    		if(index==1){
        					    			answer2 = $(item).val();
        					    		}
        					    		if(index==2){
        					    			answer3 = $(item).val();
        					    		}
        					    	});
        					    	$.ajax({
        				    			type : "post",
        				    			url : "/asq/phone_modify",
        				    			data : {
        				    				"answer1" : answer1,
        				    				"answer2" : answer2,
        				    				"answer3" : answer3,
        				    				"vcode" : vcode,
        				    				"areaCode" : areaCode,
        				    				"phone" : phone
        				    			},
        				    			success : function(response) {
        				    				var code = response.code;
        				    				if ("200" == code) {
        				    					$.successMsg("修改成功");
        				    					location=location;
        				    				} else {
        				        				$.errorMsg(response.message);
        				    				}
        				    			}
        				    		});
        					    	return false;
        					    },
        					    cancelValue: '取消',
        					    cancel: function () {}
        					});
        					d.showModal();
        					
        					$(".modify-phone-dlg .right .send-message").click(function(){
    							
        						var enable = $(this).data("enable");
    				        	if(enable=="0"){
    				        		return false;
    				        	}
    				    		me = $(this);
    							$.ajax({
    				        		type:"post",
    				        		url:"/code/phone",
    			        			data:{
    			        			"areaCode" : me.prev().prev().val(),
    			        			"phone":me.prev().val()
    			        			},
    				        		success:function(response) {
    				        			var code = response.code;
    				        			if (code == "200") {
    				        				$.successMsg("验证码发送成功");
    				        				//按钮不可用
    				        				me.data('enable','0');
    				    		    		settime(me,60);
    				        			} else if ("607" == code) {
    				        				alert("您尚未登录或登录已失效！");
    				        				logout();
    				        			} else {
    				        				$.errorMsg(response.message);
    				        			}
    				        		}
    				        	});
    						})
        					
        				}else{
        					$.errorMsg("您还没设置密保问题，请设置密保问题后再修改邮箱！");
        				}
        			}else{
        				$.errorMsg(response.message);
        			}
        		}
        	});
        }
        
        var showModifyEmailDlg = function(){
        	
        	$.ajax({
        		type:"get",
        		url:"/asq/get_asq",
        		success:function(response) {
        			var code = response.code;
        			if (code == "200") {
        				if(response.data!=null){
        					var d = dialog({
        					    title: '修改邮箱',
        					    width:'500px',
        					    skin: 'modify-email-dlg',
        					    content: $('#modify-email-tmpl').render({asq:response.data}),
        					    okValue: '提交',
        					    ok: function () {
        					    	var email = $(".modify-email-dlg .modify-email .email").val();
        					    	
        					    	var answer1 = "";
        					    	var answer2 = "";
        					    	var answer3 = "";
        					    	$(".modify-email-dlg .modify-email .question input").each(function(index,item){
        					    		if(index==0){
        					    			answer1 = $(item).val();
        					    		}
        					    		if(index==1){
        					    			answer2 = $(item).val();
        					    		}
        					    		if(index==2){
        					    			answer3 = $(item).val();
        					    		}
        					    	});
        					    	$.ajax({
        				    			type : "post",
        				    			url : "/asq/email_modify",
        				    			data : {
        				    				"answer1" : answer1,
        				    				"answer2" : answer2,
        				    				"answer3" : answer3,
        				    				"email" : email
        				    			},
        				    			success : function(response) {
        				    				var code = response.code;
        				    				if ("200" == code) {
        				    					$.successMsg("修改成功,旧邮箱不能再登录，请使用新邮箱登录！");
        				    					location=location;
        				    				} else {
        				        				$.errorMsg(response.message);
        				    				}
        				    			}
        				    		});
        					    	return false;
        					    },
        					    cancelValue: '取消',
        					    cancel: function () {}
        					});
        					d.showModal();

        				}else{
        					$.errorMsg("您还没设置密保问题，请设置密保问题后再修改邮箱！");
        				}
        			}else{
        				$.errorMsg(response.message);
        			}
        		}
        	});
        	
        }
        
        
        function loadDemandSumAmountCountFinish(){
        	$.ajax({
        		type:"get",
        		url:"/demand/sum_amount_and_count_finish",
        		success:function(response) {
        			var code = response.code;
        			if (code == "200") {
        				var data = response.data;
        				$("#finish-sum-amount").html(balanceFormat(data.countAmount));
        				$("#finish-count").html(data.finishCount);
        				$("#publish-count").html(data.countPublishDemand);
        			} else if ("607" == code) {
        				alert("您尚未登录或登录已失效！");
        				logout();
        			} else {
        				// 失败
        				//alert("获取我们资料信息失败，原因是:" + response.message);
        			}
        		}
        	});
        }	
        
        /*
        //倒计时60s
        var countdown = 60; 
        function refreshEmailVerify() { 
        	var obj = $("#emailverify");
        	if (countdown == 0) {   
        		obj.data('enable','1');
        		obj.html("重新发送"); 
        		countdown = 60; 
        	} else { 
        		obj.html("重新发送(" + countdown + ")"); 
        		countdown--; 
        		setTimeout(function() { refreshEmailVerify() },1000) 
        	}
        } 
        
        //倒计时60s
        var countdownphone = 60; 
        function refreshPhoneVerify() { 
        	var obj = $("#phoneverify");
        	if (countdownphone == 0) {   
        		obj.data('enable','1');
        		obj.html("重新发送"); 
        		countdownphone = 60; 
        	} else { 
        		obj.html("重新发送(" + countdownphone + ")"); 
        		countdownphone--; 
        		setTimeout(function() { refreshPhoneVerify() },1000) 
        	}
        } */
        
        /*chenkanghua 20151116
         * phone 15818853022 
         * qq 846645133
         * 验证按钮
         */
        function verifyflag()
        {
        	var obj = $(this);
        	var type = obj.data("type");
        	var enable = obj.data("enable");
        	if(enable=="0"){
        		return false;
        	}
        	//按钮不可用
        	obj.data('enable','0');
        	var value="";
        	if(type==0)//电话
        	{
        		value=$("#telphone").val();
        		$.ajax({
        			type:"post",
        			url:"/code/phone",
        			data:{
        			"areaCode" : obj.parent().parent().parent().find(".area-code").val(),
        			"phone":value
        			},
        			success:function(response) {
        				var code = response.code;
        				if(code == "200") {
        					settime(obj,60);    
        					$("#phoneverifycode").removeClass("hide");
        					$(":text[name='phonecode']").removeClass("hide");
        					$("#telphone").attr("readonly","true");
        					$("#email").attr("readonly","true");
        					$.successMsg("发送成功，请查看手机收到的验证码,并填入验证！");
        				}else{
        					obj.data('enable','1');
        					$.errorMsg(response.message);
        				}
        			},
        			error:function(response) {
        				obj.data('enable','1');
        				$.errorMsg("无网络");
        			}
        		});
        	}
        	else if(type==1)//邮箱
        	{	
        		value=$("#email").val();
        		$.ajax({
        			type:"post",
        			url:"/user/verify_email2",
        			data:{
        			"email":value,
        			"vcode":1234
        			},
        			success:function(response) {
        				var code = response.code;
        				if (code == "200") {
        					settime(obj,60);         					
        					$("#email").attr("readonly","true");
        					$.successMsg("发送成功，请登录邮箱进行验证！");
        				}else{
        					obj.data('enable','1');
        					$.errorMsg(response.message);
        				}
        			},
        			error:function(response) {
        				obj.data('enable','1');
        				$.errorMsg("无网络");
        			}
        		});
        		
        	}
        	else
        	{
        		return false;
        	}
        	
        }
        
        /*chenkanghua 20151116
         * phone 15818853022 
         * qq 846645133
         * 验证用户提交的验证码
         */
        function verifycodeflag(type)
        {
        	var value=$(":text[name='phonecode']").val();
        	var phone=$("#telphone").val(); 
        		
        	$.ajax({
        		type:"post",
        		url:"/user/verify_telphone",
        		data:{
        			"vcode":value,
        			"areaCode" : $(this).parent().parent().parent().find(".area-code").val(),
        			"phone":phone
        		},
        		success:function(response){
        			var code = response.code;
        			if (code == "200") {
        				$.successMsg("验证成功！");
        				location=location;
        			} else if ("607" == code) {
        				$.errorMsg("您尚未登录或登录已失效！");
        				logout();
        			} else {
        				// 失败
        				$.errorMsg("验证失败，原因是:" + response.message);
        			}
        		}
        	});
        }
        
        function saveInfo() {
        	var country = $("select[name='nationality']").val();
        	var province = $("#province").val();
        	var city = $("#city").val();
        	var otherContact = $("#otherContact").val();
        	var brief = $("#brief").val();
        	var occupation = $("#occupation").val();
			var wechat = $("#wechat").val();
			var degree = $("select[name='degree']").val();
        	var zipCode = $("#zipCode").val();
        	var sex = $('input[name="sex"]').val();
        	$.ajax({
        		type:"post",
        		url:"/user/save_info",
        		data:{
        			"country":country,
        			"province":province,
        			"city":city,
        			"zipCode":zipCode,
        			"otherContact":otherContact,
        			"brief":brief,
        			"occupation":occupation,
        			"wechat":wechat,
        			"degree":degree,
        			"sex":sex
        		},
        		success:function(response){
        			var code = response.code;
        			if (code == "200") {
        				$.successMsg("修改成功");
        				location=location;
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
		loadUserInfo();
		loadSpecialtyUser();
		loadDemandSumAmountCountFinish();
		$("#friend-link").click(function() {
			window.location.href = "/dashboard/Account/friend.jsp";
		});
		$("#message-link").click(function() {
			window.location.href = "/dashboard/Account/messages.jsp";
		});
    }
    
    var handlePassword = function() {
    	function modifyPass() {
    		var old_pass = $("#old_pass").val();
    		var new_pass_1 = $("#new_pass_1").val();
    		var new_pass_2 = $("#new_pass_2").val();
    		if(old_pass=="")
    		{
    			$.errorMsg("必须输入旧密码！");
    			return false;
    		}
    		if(new_pass_1=="")
    		{
    			$.errorMsg("必须输入新密码！");
    			return false;
    		}
    		if(new_pass_1!=new_pass_2)
    		{
    			$.errorMsg("新密码输入不一致，请重新输入!");
    			$("#new_pass_1").val("");
    			$("#new_pass_2").val("");
    			return false;
    		}
    		if(new_pass_1==old_pass)
    		{
    			$.errorMsg("旧密码与新密码相同，无需修改!");
    			return false;
    		}
    		
    		$.ajax({
    			type:"get",
    			url:"/user/reset_pwd_byuser",
    			data:{
    				"oldpass":old_pass,
    				"newpass":new_pass_1,
    			},
    			success:function(response) {
    				var code = response.code;
    				if (code == "200") {
    					var d = dialog({
    					    skin: 'tip-dlg',    
    					    onshow : function() {
    					    	this.content($('#tip-dlg').render());    
    					    	$(".tip-dlg .tip-dlg-content .operate button").click(function(){   
    					    		d.close();
    					    		//window.location.href = "/";
    					    		logout();
    					    	})
    					    	
    					    }
    					});
    					d.showModal();
    					
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
    	
    	$('#modifyPass').validate({
    		submitHandler: function(form) {
    			modifyPass();  
    		}
    	});
    }
    
   
    var handleUserPic = function() {
    	$('.system-pic').click(function(){
    		var picSrc = $(this).data("src");
    		//$(".fileinput-preview").html('<img src="'+picSrc+'" style="max-height: 140px;">');
    		//$("input[name='picFile']").val(picSrc);
    		$.ajax({
    			type:"post",
    			url:"/user/modify_pic",
    			data:{
    				"picUrl":picSrc
    			},
    			success:function(response) {
    				var code = response.code;
    				if (code == "200") {
    					window.location.href="/dashboard/Account/setting.jsp";
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} 
    			}
    		});
    	});
    }
   

    
    return {
        //main function to initiate the module
        init: function() {
        	accountSettingInit();
        	accountInfo();
        	handlePassword();
        	handleUserPic();
        }
    };

}();

jQuery(document).ready(function() {
	accountSetting.init();
	doc = $(window.document);
	doc.on('click','.works-enclosure-edit',function(){
		if($(this).hasClass("glyphicon-pencil")){
			$(this).next().addClass("hide");
			$(this).next().next().removeClass("hide");
			$(this).next().next().val($(this).next().find('span').html());
			$(this).removeClass("glyphicon-pencil");
			$(this).addClass("glyphicon-ok");
		}else if($(this).hasClass("glyphicon-ok")){
			var obj = $(this);
			var id = $(this).data("id");
			var title = $(this).next().next().val();
			$.ajax({
    			type:"post",
    			url:"/works/modify_title",
    			data:{
    				"title" : title,
    				"id" : id
    			},
    			success:function(response) {
    				var code = response.code;
    				if (code == "200") {
    					obj.next().removeClass("hide");
    					obj.next().next().addClass("hide");
    					obj.next().find('span').html(title);
    					obj.addClass("glyphicon-pencil");
    					obj.removeClass("glyphicon-ok");
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} else {
    					$.errorMsg(response.message);
    				}
    			}
    		});
		}
	}).on('click','.education-enclosure-edit',function(){
		if($(this).hasClass("glyphicon-pencil")){
			$(this).next().addClass("hide");
			$(this).next().next().removeClass("hide");
			$(this).next().next().val($(this).next().html());
			$(this).removeClass("glyphicon-pencil");
			$(this).addClass("glyphicon-ok");
		}else if($(this).hasClass("glyphicon-ok")){
			var obj = $(this);
			var id = $(this).data("id");
			var enclosureName = $(this).next().next().val();
			$.ajax({
    			type:"post",
    			url:"/works/modify_enclosure_name",
    			data:{
    				"enclosureName" : enclosureName,
    				"id" : id
    			},
    			success:function(response) {
    				var code = response.code;
    				if (code == "200") {
    					obj.next().removeClass("hide");
    					obj.next().next().addClass("hide");
    					obj.next().html(enclosureName);
    					obj.addClass("glyphicon-pencil");
    					obj.removeClass("glyphicon-ok");
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} else {
    					$.errorMsg(response.message);
    				}
    			}
    		});
		}
	}).on('click','.work-experience-enclosure-edit',function(){
		if($(this).hasClass("glyphicon-pencil")){
			$(this).next().addClass("hide");
			$(this).next().next().removeClass("hide");
			$(this).next().next().val($(this).next().html());
			$(this).removeClass("glyphicon-pencil");
			$(this).addClass("glyphicon-ok");
		}else if($(this).hasClass("glyphicon-ok")){
			var obj = $(this);
			var id = $(this).data("id");
			var enclosureName = $(this).next().next().val();
			$.ajax({
    			type:"post",
    			url:"/work_experience/modify_enclosure_name",
    			data:{
    				"enclosureName" : enclosureName,
    				"id" : id
    			},
    			success:function(response) {
    				var code = response.code;
    				if (code == "200") {
    					obj.next().removeClass("hide");
    					obj.next().next().addClass("hide");
    					obj.next().html(enclosureName);
    					obj.addClass("glyphicon-pencil");
    					obj.removeClass("glyphicon-ok");
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} else {
    					$.errorMsg(response.message);
    				}
    			}
    		});
		}
	})
	
});