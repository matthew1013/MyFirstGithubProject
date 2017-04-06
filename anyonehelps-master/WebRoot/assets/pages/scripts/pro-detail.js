
$(function() {
	$("title").html("申请大牛详情 | 大牛社区 | AnyoneHelps");
	$(".nav5").addClass("active open");
	$(".nav53").addClass("active open");
	
	function load(id) {
		$.ajax({
			type : "get",
			url : "/pro_user/get_one",
			data : {
				"id" : id
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					if (response.data != null) {
						$(".portlet-body").html($('#item-tmpl').render(response.data));
						
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
						
						$('select[name="proType"]').val(response.data.proTypeId);
						$('select[name="pro"]').html($('#pro-tmpl').render({type: $('select[name="proType"]').val()}));
						$('select[name="pro"]').val(response.data.proId);
						$('select[name="proType"]').change(function(){
							$('select[name="pro"]').html($('#pro-tmpl').render({type: $(this).val()}));
						});
					} 
				} else if ("607" == code) {
					alert("您尚未登录或登录已失效！");
					logout();
				} 
			}
		});
	}
	
	
	var id = $("#id").val();
	load(id);
	
		

});