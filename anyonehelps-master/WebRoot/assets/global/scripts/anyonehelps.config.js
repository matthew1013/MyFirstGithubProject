var project_base_name = "/";
var user_login_page_url = "/login.jsp";
var user_logout_url = "/user/logout";
var user_login_info_url = "/logininfo/get_self";
var user_profile_page_url = "/user/profile.jsp";
var user_get_self_url = "/user/get_self";
var user_save_info_url = "/user/save_info";
var user_pic_upload_url = "/user/pic_upload";
var user_get_by_sum_amount_url = "/user/by_sum_amount";
var user_code_phone = "/code/phone";
var user_verify_telphone_url = "/user/verify_telphone";
var user_verify_email_url = "/user/verify_email";
var user_verify_email2_url = "/user/verify_email2";
var user_modify_password_url = "/user/reset_pwd_byuser";
var user_modify_pic_url = "/user/modify_pic";
var user_youwenbida_page_url = "/user/youwenbida.jsp";
var account_detail_search_url = "/account/detail/search";
var account_withdrawals_add_url = "/account/withdrawals/add";
var account_withdrawals_search_url = "/account/withdrawals/search";
var user_quxianshenqing_page_url = "/user/quxianshenqing.jsp";
var user_caiwujilu_page_url = "/user/caiwujilu.jsp";
var user_chongzi_page_url = "/user/chongzi.jsp";
var user_yingbiaoguanli_url = "/user/yingbiaoguanli.jsp";
var user_quxianjilu_page_url = "/user/quxianjilu.jsp";
var user_zhuanchang_page_url = "/user/zhuanchang.jsp";

var user_fb_login_url = "/user/fb_login";

var message_user_search_url = "/message_user/search";
var message_user_read_url = "/message_user/read";

var message_system_search_url = "/message_system/search";
var message_system_read_url = "/message_system/read";

var user_demand_add_page_url ="/user/demandAdd.jsp";
var user_demand_add_url ="/demand/add";
var user_demand_list_page_url = "/user/demandList.jsp";
var user_demand_search_url = "/demand/search";
var user_demand_receive_add_url ="/demand/receive_add";
var user_demand_receive_search_url ="/demand/receive_search";

var user_demand_search_by_writer_url = "/demand/search_by_writer";
var user_demand_get_one_by_writer_url = "/demand/get_one_by_writer";
var user_demand_get_one_url = "/demand/get_one";
var user_demand_modify_page_url = "/user/demandModify.jsp";
var user_demand_receive_list_page_url = "/user/demandReceiveList.jsp";
var user_demand_receive_url = "/demand/receive_get_by_demandid";
var user_demand_start_url = "/demand/start";
var user_demand_start_stage_two_url = "/demand/start_stage_two";
var user_demand_start_stage_three_url = "/demand/start_stage_three";
var user_demand_finish_url = "/demand/finish";
var user_demand_evaluate_page_url ="/user/demandEvaluate.jsp";
var user_demand_evaluate_url = "/demand/evaluate";
var demand_sum_amount = "/demand/get_sum_amount";
var demand_message_add_url = "/demand/demand_msg_add";
var demand_sum_amount_count_finish = "/demand/sum_amount_and_count_finish";
var demand_all_region = "/demand/all_region";
//var demand_ip_info_url = "/demand/get_ip_info";
var demand_modify_url ="/demand/modify";

var stripe_recharge_url = "/stripe/recharge";
var alipay_recharge_url ="/alipay/recharge";

var message_search_url = "/message/search";
var message_add_url = "/message/add";

var specialty_type_all_url = "/specialty/get_specialty_type_all";
var specialty_user_all_url = "/specialty/get_user_specialty";
var specialty_user_add_url = "/specialty/user_specialty_add";
var specialty_user_delete_url = "/specialty/user_specialty_delete";
var specialty_user_auth_url = "/specialty/user_specialty_auth";
var specialty_user_by_user_url = "/specialty/get_specialty_user";
var specialty_user_modify_alias_url = "/specialty/modify_alias";

var bonus_point_search_url = "/bonus_point/search";

var friend_search_url = "/friend/search";
var friend_delete_url = "/friend/delete";

var demand_follow_add_url = "/demand_follow/add";
var demand_follow_search_url = "/demand_follow/search";
var demand_follow_delete_url = "/demand_follow/delete";

var education_add_url = "/education/add";
var education_delete_url = "/education/delete";
var education_upload_url = "/education/upload";
var work_experience_add_url = "/work_experience/add";
var work_experience_delete_url = "/work_experience/delete";

var works_delete_url = "/works/delete";
	
var arbitration_add_url ="/arbitration/add";
var arbitration_oppose_url = "/arbitration/oppose";
var arbitration_agree_url = "/arbitration/agree";
var arbitration_get_one_url = "/arbitration/get_one";
var arbitration_search_url = "/arbitration/search";

var admin_arbitration_search_url = "/admin/arbitration_search";
var admin_arbitration_get_one_url = "/admin/arbitration_get_one";
var admin_arbitration_start_url = "/admin/arbitration_start";
var admin_arbitration_end_url = "/admin/arbitration_end";

var tag_get_all_url = "/tag/get_all";

function firstSetUrl(baseUrl) {
	if(baseUrl==null||baseUrl==""){
		project_base_name = "/";
	}else{
		project_base_name = baseUrl;
	}
	
	user_login_page_url = baseUrl + user_login_page_url;
	user_login_info_url = baseUrl + user_login_info_url;
	user_logout_url = baseUrl + user_logout_url;
	user_profile_page_url = baseUrl + user_profile_page_url;
	user_save_info_url = baseUrl + user_save_info_url;
	user_code_phone = baseUrl + user_code_phone;
	user_get_self_url = baseUrl + user_get_self_url;
	user_pic_upload_url = baseUrl + user_pic_upload_url;
	user_get_by_sum_amount_url = baseUrl + user_get_by_sum_amount_url;
	user_verify_telphone_url = baseUrl + user_verify_telphone_url;
	user_verify_email_url = baseUrl + user_verify_email_url;
	user_verify_email2_url = baseUrl + user_verify_email2_url;
	user_modify_password_url = baseUrl + user_modify_password_url;
	user_modify_pic_url = baseUrl + user_modify_pic_url;
	user_youwenbida_page_url = baseUrl + user_youwenbida_page_url;
	account_detail_search_url = baseUrl + account_detail_search_url;
	account_withdrawals_add_url = baseUrl + account_withdrawals_add_url;
	account_withdrawals_search_url = baseUrl + account_withdrawals_search_url;
	user_quxianshenqing_page_url = baseUrl + user_quxianshenqing_page_url;
	user_caiwujilu_page_url = baseUrl + user_caiwujilu_page_url;
	user_chongzi_page_url = baseUrl + user_chongzi_page_url;
	user_yingbiaoguanli_url = baseUrl + user_yingbiaoguanli_url;
	user_quxianjilu_page_url = baseUrl + user_quxianjilu_page_url;
	user_zhuanchang_page_url = baseUrl + user_zhuanchang_page_url;
	
	user_fb_login_url = baseUrl + user_fb_login_url;
	
	message_user_search_url = baseUrl + message_user_search_url;
	message_user_read_url = baseUrl + message_user_read_url;
	
	message_system_search_url = baseUrl + message_system_search_url;
	message_system_read_url = baseUrl + message_system_read_url;
	
	user_demand_add_page_url = baseUrl + user_demand_add_page_url;
	user_demand_add_url = baseUrl + user_demand_add_url;
	user_demand_list_page_url = baseUrl + user_demand_list_page_url;
	user_demand_search_url = baseUrl + user_demand_search_url;
	user_demand_search_by_writer_url = baseUrl + user_demand_search_by_writer_url;
	user_demand_get_one_by_writer_url = baseUrl + user_demand_get_one_by_writer_url;
	user_demand_get_one_url = baseUrl + user_demand_get_one_url;
	user_demand_modify_page_url = baseUrl + user_demand_modify_page_url;
	user_demand_receive_add_url = baseUrl + user_demand_receive_add_url;
	user_demand_receive_search_url = baseUrl + user_demand_receive_search_url;
	demand_sum_amount_count_finish = baseUrl + demand_sum_amount_count_finish;
	user_demand_receive_list_page_url = baseUrl + user_demand_receive_list_page_url;
	user_demand_receive_url = baseUrl + user_demand_receive_url;
	user_demand_start_url = baseUrl + user_demand_start_url;
	user_demand_start_stage_two_url = baseUrl + user_demand_start_stage_two_url;
	user_demand_start_stage_three_url = baseUrl + user_demand_start_stage_three_url;
	user_demand_finish_url = baseUrl + user_demand_finish_url;
	user_demand_evaluate_page_url = baseUrl + user_demand_evaluate_page_url;
	user_demand_evaluate_url = baseUrl + user_demand_evaluate_url;
	demand_sum_amount = baseUrl + demand_sum_amount;
	demand_message_add_url = baseUrl + demand_message_add_url;
	demand_all_region = baseUrl + demand_all_region;
	//demand_ip_info_url = baseUrl + demand_ip_info_url;
	demand_modify_url = baseUrl + demand_modify_url;
	
	stripe_recharge_url = baseUrl + stripe_recharge_url;
	alipay_recharge_url = baseUrl + alipay_recharge_url;
	
	message_search_url = baseUrl + message_search_url;
	message_add_url = baseUrl + message_add_url;
	
	specialty_type_all_url = baseUrl + specialty_type_all_url;
	specialty_user_all_url = baseUrl + specialty_user_all_url;
	specialty_user_add_url = baseUrl + specialty_user_add_url;
	specialty_user_delete_url = baseUrl + specialty_user_delete_url;
	specialty_user_auth_url = baseUrl + specialty_user_auth_url;
	specialty_user_by_user_url = baseUrl + specialty_user_by_user_url;
	specialty_user_modify_alias_url = baseUrl + specialty_user_modify_alias_url;
	
	bonus_point_search_url = baseUrl + bonus_point_search_url;
	
	friend_search_url = baseUrl + friend_search_url;
	friend_delete_url = baseUrl + friend_delete_url;
	
	demand_follow_add_url = baseUrl + demand_follow_add_url;
	demand_follow_search_url = baseUrl + demand_follow_search_url;
	demand_follow_delete_url = baseUrl + demand_follow_delete_url;
	
	education_add_url = baseUrl + education_add_url;
	work_experience_add_url = baseUrl + work_experience_add_url;
	
	works_delete_url = baseUrl + works_delete_url;
	
	arbitration_add_url = baseUrl + arbitration_add_url;
	arbitration_oppose_url = baseUrl + arbitration_oppose_url;
	arbitration_agree_url = baseUrl + arbitration_agree_url;
	arbitration_get_one_url = baseUrl + arbitration_get_one_url;
	arbitration_search_url = baseUrl + arbitration_search_url;
	
	admin_arbitration_search_url = baseUrl + admin_arbitration_search_url;
	admin_arbitration_get_one_url = baseUrl + admin_arbitration_get_one_url;
	admin_arbitration_start_url = baseUrl + admin_arbitration_start_url;
	admin_arbitration_end_url = baseUrl + admin_arbitration_end_url;
	
	tag_get_all_url = baseUrl + tag_get_all_url;
}

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

function logout() {
	$.get(user_logout_url, function(response) {
		window.location.href = project_base_name;
	});
	//return false;
}

function createPaginationHtmlStr(rowCount, pageSize, pageNow, pageCount) {
	var prePage = pageNow - 1;
	prePage = prePage < 1 ? 1 : prePage;
	var nextPage = pageNow + 1;
	nextPage = nextPage > pageCount ? pageCount : nextPage;
	var info = "<lable >共" + rowCount + "条/共" + pageCount + "页&nbsp;";
	info += "当前第" + pageNow + "页&nbsp;&nbsp;</lable>";
	info += "<a href='1' id='a_first_page'>首页</a><lable>|</lable>";
	info += "<a href='" + prePage + "' id='a_pre_page'>上一页</a>";
	info += "<input style='width: 30px;' type='text' name='pageNow' />";
	info += "<a href='" + nextPage + "' id='a_next_page'>下一页</a><lable>|</lable>";
	info += "<a href='" + pageCount + "' id='a_last_page'>尾页</a>";
	return info;
}