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
		},
		error: function () {
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
	
});