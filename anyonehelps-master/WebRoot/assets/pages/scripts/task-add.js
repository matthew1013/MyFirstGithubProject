$.views.tags({
     "SpecialtyFormat":function(id,specialtys){
    	 console.log("spec:"+specialtys)
    	 var result = "";
    	 if (specialtys != null) {
    		 $.each(specialtys, function(e) {
    			 if(id==this){
    				 result = "active";
    			 }
    		 })
    	 }
    	 return result;
     }
});

var taskAdd = function() {
	var taskAddInit = function(){
		$("title").html("新发任务 | 任务中心| AnyoneHelps");
		$(".nav2").addClass("active open");
		$(".nav21").addClass("active open");
		
		/*完成时间*/
		$('#expire-date').datetimepicker({
            language: 'zh-CN',
            autoclose: 1,
            todayBtn: 1,
            pickerPosition: "bottom-left",
            weekStart: 1,  
            startView: 2,  
            minView: 1,  
            format: 'yyyy-mm-dd hh:ii:ss'
           
        });
		
		/*紧急程序初始化  */
		$('input[name="urgent"]').iCheck({
	  	  	checkboxClass: 'icheckbox_minimal-blue',
	    	radioClass: 'iradio_minimal-blue',
	   	 	increaseArea: '20%', // optional
	   	 	check:function(){
	   	 	  alert('Well done, Sir');
	   	 	}
	  	});
		/*任务是否公开初始化 */
		$('input[name="private-task"]').iCheck({
	  	  	checkboxClass: 'icheckbox_minimal-blue',
	    	radioClass: 'iradio_minimal-blue ',
	   	 	increaseArea: '20%', // optional
	   	 	check:function(){
	   	 	  alert('Well done, Sir');
	   	 	}
	  	});
		var initMap = false;
		/*分享位置初始化  */
		$('input[name="location-open"]').on('ifChecked', function(event){  
			$('#map-region').removeClass('hide');
			if(!initMap){
				loadIPInfo();
				initMap = true;
			}
		}).on('ifUnchecked', function(event){  
			$('#map-region').addClass('hide');
			$('#map-region').html('');
		}).iCheck({
	  	  	checkboxClass: 'icheckbox_minimal-blue',
	    	radioClass: 'iradio_minimal-blue',
	   	 	increaseArea: '20%', // optional
	   	 	check:function(){
	   	 	  alert('Well done, Sir');
	   	 	}
	  	});
		/*地理位置初始化  */
		$('input[name="nationality"]').iCheck({
	  	  	checkboxClass: 'icheckbox_minimal-blue',
	    	radioClass: 'iradio_minimal-blue',
	   	 	increaseArea: '20%', // optional
	   	 	check:function(){
	   	 	  alert('Well done, Sir');
	   	 	}
	  	});
		
		/*任务成果要求得失焦点提示*/
        $('textarea.achieve').focus(function(){
			$(this).attr("placeholder","");
        })
        $('textarea.achieve').blur(function(){
        	if($(this).val()==""){
        		$(this).attr("placeholder","注意：请明确任务成果要求，如发生纠纷，平台将根据交易达成时所述要求进行仲裁。 一旦匹配后，将无法更改。\n如有多个成果要求，请按如下格式填写：\n1、成果要求\n2、成果要求2");
            }
        })
        
		/*任务描述初始化*/
		//placeholder:'请描述您的任务内容并清晰给出任务交接需满足的所有任务要求。如果需要进一步描述或提供更详细的细节，请使用附件功能。',
		
		var $placeholder = $('.placeholder');
		$('#summernote_1').summernote({
			height: 300,
			onInit: function() {
				$placeholder.show();
			},
			onFocus: function() {
				$placeholder.hide();
			},
			onBlur: function() {
				var $self = $(this);
			  	setTimeout(function() {
			  		if ($self.summernote('isEmpty') ) {
			  			$placeholder.show();
			  		}
			  	}, 300);
			},
			onImageUpload: function(files) {  
				sendFile(files[0]);  
	        },
	        airPopover: [
	                     ['color', ['color']],
	                     ['font', ['bold', 'underline', 'clear']],
	                     ['para', ['ul', 'paragraph']],
	                     ['table', ['table']],
	                     ['insert', ['link', 'picture']]
	                   ]
			
		}).code("");
		
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
		        	console.log(response);
		        	$("#summernote_1").summernote('insertImage', response.data.saveFileName, response.data.fileName); 
		        }  
		    });  
		} 
		
		/*显示高级选项*/
		$(".show-detail").click(function(){
			if($(".detail").hasClass("hide")){
				$(".detail").removeClass("hide");
				$(this).html('<i class="glyphicon glyphicon-minus"></i><span class="desc">高级选项</span>');
			}else{
				$(".detail").addClass("hide");
				$(this).html('<i class="glyphicon glyphicon-plus"></i><span class="desc">高级选项</span>');
			}
			
		})
		
		/*标题字数提醒*/
		$("input[name='title']").bind('input propertychange', function() {
		    //$('#result').html($(this).val().length + ' characters');
		   	$(".title-limit-count").html(60-$(this).val().length);
		});
		
		/*替换中文＃号*/
		/*$("input[name='tag']").bind('input propertychange', function() {
		    $(this).val($(this).val().replace(/＃/g,"#"));
		});*/
	}
    var taskInit = function() {
		if($('#type').val() == ""){              //haokun added 2017/02/17
			$("#typeSecond").hide();             //haokun added 2017/02/17
		}else{                                   //haokun added 2017/02/17
			$("#typeSecond").show();             //haokun added 2017/02/17
		}                                        //haokun added 2017/02/17
    	$('#type').change(function(){            //haokun added 2017/02/17
			if($('#type').val() == ""){          //haokun added 2017/02/17
				$("#typeSecond").hide();         //haokun added 2017/02/17
			}else{                               //haokun added 2017/02/17
			    $("#typeSecond").show();         //haokun added 2017/02/17
			}                                    //haokun added 2017/02/17
    		$('#typeSecond').html($('#type-second-tmpl').render({type: $(this).val()}));
    		if($("#typeSecond").val()!=""){
    			$(":text[name='title']").val($("#typeSecond").find("option:selected").text());
    		}
    	});
    	$('#typeSecond').change(function(){
    		$(":text[name='title']").val($(this).find("option:selected").text());
    	});
		$('#task-form').validate({
            submitHandler: function(form) {
                //form.submit(function()); // form validation success, call ajax form submit
            	//记住用户名密码
            	submitTask();
            }
        });
		
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
	        	console.log(data);
	        	if(code=="200"){
	        		if(data.result.data!=null){
	    	            $.each(data.result.data, function (index, file) {
	    	            	console.log(file);
	    	            	if( $('.enclosure-list').children().length >=5){
	    	            		$(".enclosure-upload").hide();
		        				$('#enclosure-format').hide();
	    	            		return;
	    	            	}
	    	            	$('.enclosure-list').append($('#enclosure-item-tmpl').render(file));
		        			if($('.enclosure-list').children().length>=5){
		        				$(".enclosure-upload").hide();
		        				$('#progress').hide();
		        				$('#enclosure-format').hide();
		        			}
		        			$('.enclosure-delete').unbind("click"); //移除click
	        				$('.enclosure-delete').click(function(){
	        					$(this).parent().parent().remove();
	        					$(".enclosure-upload").show();
		        				$('#enclosure-format').show();
	        				});
	    	            }); 
	        		}
	        	}else{
	        		alert(data.result.message);
	        	}
	        },
	        
	        progressall: function (e, data) {
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
	   		},
	    });
		
    }
    
    $(".specialty-add").click(function(){
    	var specialtyId = [];
    	var specialty = $(".specialty-list").children();
    	if(specialty!=null){
    		specialty.each(function(){ 
    			specialtyId.push($(this).data("id"));
    		})
    	}
    	var dlg = dialog({
			title : '',
			align: 'left top',
			skin: 'dlg-radius0',
			width : 750,
			padding:"0px",
			quickClose : true,
			onclose: function () {
				$(".specialty-list").html("");
				$(".specialty-btn").each(function(){ 
					if($(this).hasClass("active")){
						$(".specialty-list").append($('#specialty-item-tmpl').render({id:$(this).data("id"),typeid:$(this).data("typeid"),name:$(this).data("name")}))
					}
					
				}); 
				$(".specialty-remove").click(function(){
					$(this).parent().parent().remove();
					return false;
				})
		    },
			onshow : function() {
				var me = this;
				$.ajax({
		    		type:"get",
		    		url:"/specialty/get_specialty_type_all",
		    		success:function(response) {
		    			var code = response.code;
		    			if (code == "200") {
		    				var data = response.data;
		    				console.log(data);
		    				me.content($('#specialty-tmpl').render({dataList:data,specialtyId:specialtyId}));
		    				//showSpecialtyType(data);
		    				$(".dlg-radius0 .specialty-item .search").unbind('input propertychange')
		    				$(".dlg-radius0 .specialty-item .search").bind('input propertychange', function() {
		    					var key = $(this).val();
		    					$(".dlg-radius0 .specialty-item .tab-content .specialty-btn").each(function(){
		    						if($(this).data("name").indexOf(key)>=0){
										$(this).parent().addClass("active");   //haokun added 2017/02/17
		    							$(this).removeClass("hide");
		    						}else{
		    							if(!$(this).hasClass("active")){
		    								$(this).addClass("hide");
		    							}
		    						}
		    					});
		    				});
		    				
		    				
		    				$(".wclose").click(function(){
		    		    		dlg.close().remove();
		    		    		return false;
		    		    	})
		    		    	
		    			} else if ("607" == code) {
		    				alert("您尚未登录或登录已失效！");
		    				logout();
		    			} 
		    		}
				
		    	});
			}
		}).show(this);
    	
    	return false;
    })
    
    var getTag = function() {
    	//系统标签
    	$.ajax({
    		type:"get",
    		url:"/tag/get_all",
    		success:function(response) {
    			var code = response.code;
    			if (code == "200") {
    				var data = response.data;
    				var tag = $('.system-tag-div').append($('#tag-items-tmpl').render({dataList: data}));
    				
    				$('.system-tag-span').click(function(){
    					
    	    			var tag = $(":text[name='tag']").val();
    	    			if(tag.indexOf("#"+$(this).data("value")) > -1 )  
    	    			{//已经有了
    	    				tag = tag.replace(new RegExp("#"+$(this).data("value")), "");
	    					$(":text[name='tag']").val(tag);
	    					$(this).removeClass("active"); 
    	    			}else{
    	    				$(this).addClass("active");
    	    				if(tag.charAt(tag.length - 1)=='#'){
    	    					$(":text[name='tag']").val(tag+$(this).data("value"));
    	    				}else{
    	    					$(":text[name='tag']").val(tag+"#"+$(this).data("value"));
    	    				}
    	    					
    	    			}
    	    			
    	    		});
    				
    			} else if ("607" == code) {
    				alert("您尚未登录或登录已失效！");
    				logout();
    			} 
    		}
    	});
    }
    
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
    		$("#pageSplit").twbsPagination({
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
    
    var submitTask = function() {
    	var title = encodeURI($(":text[name='title']").val());
		var content = encodeURI($('#summernote_1').code());
		var achieve = encodeURI($('.achieve').val());
		var secretAchieve = encodeURI($('.secret-achieve').val());    //haokun add 2017/03/06   增加私密成果要求
		var nationality = $(":radio[name='nationality']:checked").val();
		var typeSecond = $("#typeSecond").val();
		var type = $("#type").val();
		var proUserId = $(".pro-user").data("userid");
		if(proUserId==null||proUserId=="")
			proUserId = "";
		var tag = encodeURI($(":text[name='tag']").val().replace(/＃/g,"#"));
		//暂时设定一个人 
		var bidCount = 1;//$(":radio[name='bid-count']:checked").val();
		//var stageCount = 1;
		var urgent = 0;//$(":radio[name='urgent']:checked").val();
		var open = 1;
		if($(":checkbox[name='private-task']").is(":checked")){
			open = 0;
		}else{
			open = 1;
		}
		
		var locationOpen = 0;
		var locationName = "";
		var locationCountry = "";
		var locationProvince = "";
		var locationDistrict = "";
		var latitude = 0;
		var longitude = 0;
		if($(":checkbox[name='location-open']").is(":checked")){
			locationOpen = 1;
			locationName = $("#map-region").val();
			locationCountry = $("#map-country").val();
			locationProvince = $("#map-province").val();
			locationDistrict = $("#map-district").val();
			latitude = $("#map-latitude").val();
			longitude = $("#map-longitude").val();
		}else{
			locationOpen = 0;
		}
		amount = $("#amount").val();
		expireDate = $("#expire-date").val();
		if(urgent=='1'&&amount<'10'){
			$.errorMsg("提交失败:加急任务金额不能少于10美金!请修改金额或者任务紧急程度");
			return false;
		}
		if(urgent=='2'&&amount<'20'){
			$.errorMsg("提交失败:加急任务金额不能少于20美金!请修改金额或者任务紧急程度");
			return false;
		}
		
		/*haokun added 2017/03/01 start 增加表格检测若错误改输入样式改变*/
		if(title == ""){
			$(":text[name='title']").addClass("border-red");
			$(".task-title-alert").removeClass("hide");
		}else{
			$(":text[name='title']").removeClass("border-red");
			$(".task-title-alert").addClass("hide");
		}
	    if(achieve == ""){
			$('.achieve').addClass("border-red");
			$(".task-achieve-alert").removeClass("hide");
		}else{
			$('.achieve').removeClass("border-red");
			$(".task-achieve-alert").addClass("hide");
		}
		if(amount ==""){
			$("#amount").addClass("border-red");
			$(".task-amount-alert").removeClass("hide");
		}else{
			$("#amount").removeClass("border-red");
			$(".task-amount-alert").addClass("hide");
		}
		if(expireDate ==""){
			$("#expire-date").addClass("border-red");
			$(".task-expire-alert").removeClass("hide");
		}else{
			$("#expire-date").removeClass("border-red");
			$(".task-amount-alert").addClass("hide");
		}	
		/*haokun added 2017/03/01 end 增加表格检测若错误改输入样式改变*/
		
		var str = '"title":"' + title + '",' + 
		'"content":"' + content + '",' +
		'"achieve":"' + achieve + '",' +
		'"secretAchieve":"' + secretAchieve + '",' +      //haokun added 2017/03/06 增加私密成果要求
		'"nationality":"' + nationality + '",' +
		'"type":"' + type + '",' +
		'"typeSecond":"' + typeSecond + '",' +
		'"tag":"' + tag + '",' +
		'"bidCount":"' + bidCount + '",' +
		'"urgent":"' + urgent + '",' +
		'"open":"' + open + '",' +
		'"proUserId":"' + proUserId + '",' +
		
		'"locationOpen":"' + locationOpen + '",' +
		'"locationName":"' + locationName + '",' +
		'"locationCountry":"' + locationCountry + '",' +
		'"locationProvince":"' + locationProvince + '",' +
		'"locationDistrict":"' + locationDistrict + '",' +
		'"latitude":"' + latitude + '",' +
		'"longitude":"' + longitude + '",' +
		
		'"amount":"' + amount + '",' +
		'"expireDate":"' + expireDate + '"' ;

		$(".enclosure-list .enclosure-item .title").each(function(index){
			if(index<5){
				i = index+1;
				str += ',"enclosure'+i+'":"' + $( this ).data("id") + '"';
				str += ',"enclosure'+i+'Name":"' + $( this ).html() + '"';
			}
		});
		
		var specialty = $(".specialty-list").children();
    	if(specialty!=null){
    		specialty.each(function(index){ 
    			str += ',"ds['+index+'].specialtyId":"' + $( this ).data("id") + '"';
    			str += ',"ds['+index+'].specialtyTypeId":"' + $( this ).data("typeid") + '"';
    			
    		})
    	}
		
		var obj = $.parseJSON("{" + str + "}");
		
		$.ajax({
			type : "post",
			url : "/demand/add",
			data : $.param(obj, true),
			success : function(response) {
				var code = response.code;
				var demandId = response.data;
				if ("200" == code) {
					$.successMsg("任务提交成功");
					if(open==0){
						inviteDlgCancolse = false;
					}
					
					var dlg = dialog({
						title : '',
						skin:"dlg-radius0 dlg-invite-user",
						/*width : 720,*/
						padding:"0px",
						onclose: function () {
							if(inviteDlgCancolse){
								window.location.href= "/dashboard/Task/publishedList.jsp?view=tab_1_1";
							}
							else {
								return false;
							}
					    },
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
											me.content($('#friend-tmpl').render({dataList:data.datas,open:open,demandId:demandId}));
											showFriendLink(data);
										}else{
											me.content($('#friend-tmpl').render({dataList:null,open:open,demandId:demandId}));
											
											$(".invite-user-list").html('<div class="col-md-2 col-sm-2 col-xs-2"></div> <div class="col-md-8 col-sm-8 col-xs-8" style="margin-top: 50px;"><span style="margin-top: 100px;border-top-width: 10px;">没有关注人物,现在去<a target="_blank" href="/user/famousMore.jsp">关注</a></span></div>');
										}
										$(".wclose").click(function(){
					    					if(inviteDlgCancolse){
					    						dlg.close().remove();
					    					}else{
					    						$.errorMsg("您发布的是不公开任务，必须邀请您关注的用户来接单！");
					    					}
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
					
					
				}else if ("15005"==code){
					var d = dialog({
						title: '提示',
						skin:"dlg-radius0 dlg-balance-no",
					    content: '<span>帐户余额不足,请充值后再提交任务</span></br><span>你当前余额:$</span><span class="font-red-flamingo" id="usd">'+response.data.usd+'</span><span>,冻结金额</span><span class="font-red-flamingo" id="freezeUsd">'+response.data.freezeUsd+'</span>',
					    okValue: '去充值',
					    ok: function () {
					    	window.open("/dashboard/Finance/recharge.jsp");
					    	return false;
					    },
					    cancelValue: '返回',
					    cancel: function () {}
					}).show($(".submit-btn")[0]);
					setTimeout(loadAccount,10*1000) 
				} else {
					$.errorMsg(response.message);
				}
			}
		});
	}
    
    function loadUserInfo(){
    	$.ajax({
			type:"get",
			url:"/user/get_self",
			success:function(response) {
				var code = response.code;
				if (code == "200") {
					$(".usd-amount").html(response.data.usdBalance);
				} 
			},
		});
    }
    loadUserInfo();
    
    function loadAccount(){
    	$.ajax({
			type:"get",
			url:"/user/get_self",
			success:function(response) {
				var code = response.code;
				if (code == "200") {
					$("#usd").html(response.data.usdBalance);
					$("#freezeUsd").html(response.data.freezeUsd);
					$(".usd-amount").html(response.data.usdBalance);
					
					if ( $("#usd").length > 0 ){
						setTimeout(loadAccount,10*1000) 
					}
				} 
			},
			error:function(response){
				setTimeout(loadAccount,10*1000);
			}
		});
    }

    
    $("#amount").blur(function(){
    	var me = this; 
    	var myAmount = parseFloat($(".usd-amount").html());
    	var amount = parseFloat($(this).val());
    	
    	if (isNaN(amount))
    	{
    		var d = dialog({
				title: '提示',
				skin:"dlg-radius0 dlg-balance-no",
				content: '<span>悬赏金额输入错误！<span>',
				ok: function () {
				},
				okValue: '确定',
			}).show(me);
    		return false;
    	}
    	if (!isNaN(myAmount))
    	{
    		if(amount<10){
    			var d = dialog({
    				title: '提示',
    				skin:"dlg-radius0 dlg-balance-no",
    				content: '<span>悬赏金额不能低于10美元！<span>',
    				ok: function () {
    				},
    				okValue: '确定',
    			}).show(me);
        		return false;
    		}
    		/*if(amount>myAmount){
    			var d = dialog({
    				title: '提示',
    				skin:"dlg-radius0 dlg-balance-no",
    				width : 650,
    				content: '<span>帐户余额不足,请充值后再提交任务</span></br><span>你当前余额:$</span><span class="font-red-flamingo" id="usd">'+myAmount+'</span><span>',
    				okValue: '去充值',
    				ok: function () {
    					window.open("/dashboard/Finance/recharge.jsp");
    					return false;
    				},
    				cancelValue: '返回',
    				cancel: function () {}
    			}).show(me);
    			setTimeout(loadAccount,10*1000);
    		}*/
    	}
    	
		
	})
    
    
    var loadMap = function(latitude,longitude,locationName) {
    	$('#map').locationpicker({
		    location: {latitude: latitude, longitude: longitude},
		    locationName: locationName,
		    radius: 0,
		    zoom: 9,
		    inputBinding: {
			latitudeInput: $('#map-latitude'),
			longitudeInput: $('#map-longitude'),
			locationNameInput: $('#map-region')        
		    },
		    enableAutocomplete: true,
		    onchanged: function (currentLocation, radius, isMarkerDropped) {
		        var addressComponents = $(this).locationpicker('map').location.addressComponents;
		        if(addressComponents){
		        	$('#map-district').val(addressComponents.district);
		        	$('#map-province').val(addressComponents.stateOrProvince);
		        	$('#map-country').val(addressComponents.country);
					console.log(addressComponents);
		        }
		    },
		    oninitialized: function(component) {
		        var addressComponents = $(component).locationpicker('map').location.addressComponents;
		        if(addressComponents){
		        	$('#map-district').val(addressComponents.district);
		        	$('#map-province').val(addressComponents.stateOrProvince);
		        	$('#map-country').val(addressComponents.country);
		        	console.log(addressComponents);
		        }
		        
		    }
		});
    }
    ////latitude: 34.0522342, longitude: -118.2436849 "102 W 1st St, Los Angeles, CA 90012, USA"
    var loadIPInfo = function(){
    	var lat = 34.0522342;
    	var lon = -118.2436849;
    	var regionName = "102 W 1st St, Los Angeles, CA 90012, USA";
    	try {
        	$.ajaxSetup({
                error: function (x, e) {
                	loadMap(lat,lon,regionName);
                }
            });
        	var ip = $(":hidden[name='ip']").val();
            $.getJSON("//pro.ip-api.com/json/"+ip+"?key=evuD3jbO1gaBBR1&callback=?", function(data) {
        		if(data!=null){
        			if(data.lat !=null ){
        				lat = data.lat;
        			}
        			if(data.lon !=null ){
        				lon = data.lon;
        			}
        			if(data.regionName !=null ){
        				regionName = data.regionName;
        			}
        			loadMap(lat,lon,regionName);
        		}else{
        			loadMap(lat,lon,regionName);
        		}
            });
        }
        catch (ex) {
        	loadMap(lat,lon,regionName);
        }
    }
    
    function loadProUser(){
    	var proUserId = $("input[name='proUserId']").val();
    	if(proUserId==null||proUserId=="")
    		return false;
    	// 获取用户数据
     	$.ajax({
     		type:"get",
     		url:"/user/open_info",
     		data : {
				id : proUserId
			},
     		success:function(response) {
     			var code = response.code;
     			if (code == "200") {
     				var data = response.data;
     				console.log(response.data);
     				
     				$("#task-form").prepend($('#pro-user-tmpl').render(response.data.user));
					
     				/*if(data.user!=null){
     					$(".profile-userpic .img-responsive").attr("src",data.user.picUrl);
         				$(".user-occupation").html(data.user.occupation);
         			}*/
     			}  else {
     				// 失败
     				$.errorMsg("获取大牛信息失败");
     			}
     		}
     	});
    }
    return {
        //main function to initiate the module
        init: function() {
        	taskAddInit();
        	taskInit();
        	getTag();
        	//getSpecialty();
        	loadProUser();
        }

    };

}();

var inviteDlgCancolse = true;

jQuery(document).ready(function() {
	taskAdd.init();
	//点击弹出技能框中的技能
	$(window.document).on('click', '.specialty-btn', function() {
		if($(this).hasClass("active")){ //
			$(this).removeClass("active")
		}else{
			var obj = $('.specialty-btn.active');
			if(obj.length>=4){
				$.errorMsg("技能最多选择4个!");
			}else{
				$(this).addClass("active");
			}
			
		}
	}).on('click', '.invite-btn', function() {
		var me = $(this);
		me.attr("disabled","disabled");
		var id = me.data("id");
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
					inviteDlgCancolse = true;
				} else{
					$.errorMsg(response.message);
					me.attr("disabled",false);
				}
			},
			error : function() {
				me.attr("disabled",false);
			}
		});
	}).on('click','.enclosure-edit',function(){
		if($(this).hasClass("glyphicon-pencil")){
			$(this).next().addClass("hide");
			$(this).next().next().removeClass("hide");
			$(this).next().next().val($(this).next().html());
			$(this).removeClass("glyphicon-pencil");
			$(this).addClass("glyphicon-ok");
		}else if($(this).hasClass("glyphicon-ok")){
			var obj = $(this);
			var title = $(this).next().next().val();
			obj.next().removeClass("hide");
			obj.next().next().addClass("hide");
			obj.next().html(title);
			obj.addClass("glyphicon-pencil");
			obj.removeClass("glyphicon-ok");
		}
	})
	
	
});