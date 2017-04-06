$(function() {
	var isLogin = $(":hidden[name='is_login']").val();
	if(isLogin=="0"){
		$(".mbblack").removeClass("hide");
		$(".user-login").removeClass("hide");
	}
	$(".subscribe-btn").click(function(){
		var subscribe = $(this).data("subscribe");
		$.ajax({
			type : "post",
			url : "email/subscribe",
			data : {
				"subscribe" : subscribe
			},
			success : function(response) {
				var code = response.code;
				if (code == "200") {
					var tip = "";
					if(subscribe=="1"){
						tip = "订阅成功！";
					}else{
						tip = "退订成功！";
					}
					var d = dialog({
					    title: '提示',
					    width:'400px',
					    skin: 'tip-dlg',
					    content: tip,
					    okValue: '确定',
					    ok: function () {
					    }
					});
					d.showModal();
				}else if(code == "607"){
					alert("请先登录");
					$(".mbblack").removeClass("hide");
					$(".user-login").removeClass("hide");
				}else{
					alert(response.message);
				}
				
			}
		});
		return false;
	})
});