
var taskArbitration = function() {
	var taskArbitrationInit = function(){
    	$("title").html("任务仲裁 | 任务管理 | AnyoneHelps");
    	$(".nav2").addClass("active open");
		$(".nav21").addClass("active open");
		
		
	}
    var taskLoad = function() {
    	
    	function loadArbitration(id) {
    	    $.ajax({
    	    	type:"post",
    	    	url:admin_arbitration_get_one_url,
    	    	data:{
    	    		id:id
    	    	},
    	    	success:function(response){
    	    		console.log(response)
    	    		var code = response.code;
    	    		if ("200" == code) {
    	    			showArbitration(response.data);
    	    		} else if ("607" == code) {
    	    			alert("您尚未登录或登录已失效！");
    	    			logout();
    	    		} 
    	    		return false;
    	    	}
    	    });
    	}
    	function showArbitration(data){
    		if(data!=null){
    			$('.form-body').html($('#arbitration-tmpl').render({data:data,baseUrl:baseUrl}));
    			$("#ok").click(function(){
    				var id = $('#task-arbitration-form').data().arbitrationid;
    				//请求同意
    				$.ajax({
    					type : "post",
    					url : admin_arbitration_start_url,
    					dataType: 'json',
    					data : {
    						id : id
    					},
    					success : function(response) {
    						var code = response.code;
    						if ("200" == code) {
    							$.successMsg("提交成功");
    							$("#myviewpoint").show();
    							$("#viewpoint").hide();
    						} else {
    							$.errorMsg("操作失败:" + response.message);
    						}
    					}
    				});
    			});
    			$('#task-arbitration-form').validate({
    	            submitHandler: function(form) {
    	            	submitArbitration(form);
    	            }
    	        });
    			
    			var submitArbitration = function(form) {
    				var id = $(form).data().arbitrationid;
    				var ruleReason = $("#reason").val();
    				var rulePercentage = $("select[name='percentage']").val();
    				$.ajax({
    					type : "post",
    					url : admin_arbitration_end_url,
    					data : {
    						id:id,
    						reason:ruleReason,
    						percentage:rulePercentage
    					},
    					success : function(response) {
    						var code = response.code;
    						if ("200" == code) {
    							$.successMsg("提交成功");
    							window.location.href= baseUrl+"/admin/dashboard/Task/arbitrationList.jsp";
    						} else {
    							$.errorMsg("操作失败:" + response.message);
    						}
    					}
    				});
    			}
    			
    		}
    	}
    	loadArbitration($('#task-arbitration-form').data().arbitrationid);
    	
	}
    return {
        //main function to initiate the module
        init: function() {
        	taskArbitrationInit();
        	taskLoad();
        }

    };

}();

jQuery(document).ready(function() {
	taskArbitration.init();
});