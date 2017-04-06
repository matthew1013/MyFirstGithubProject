package com.anyonehelps.common.constants;

/**
 * 返回的code类<br/>
 * 描述的是后台给层次之间调用返回的code值。
 * 
 */
public class ResponseCode {
	public static final String SUCCESS_CODE = "200";
	public static final String SHOW_EXCEPTION = "600";
	public static final String REQUEST_ALL_EXCEPTION = "601";
	public static final String PARAMETER_ERROR = "603";
	public static final String VALIDATE_CODE_ERROR = "604";
	public static final String TOKEN_ERROR = "605";
	public static final String TOKEN_INSERT_ERROR = "606";
	public static final String NEED_LOGIN="607";
	
	// user
	
	public static final String USER_ID_EMPTY = "12000";
	public static final String USER_ID_ERROR = "12001";
	public static final String USER_NICK_NAME_ERROR = "12002";
	public static final String USER_EMAIL_ERROR = "12003";
	public static final String USER_RECOMMENDER_ERROR = "12004";
	public static final String USER_PASSWORD_ERROR = "12005";
	public static final String USER_PHONE_ERROR = "12006";
	public static final String USER_ID_EXISTS = "12007";
	public static final String USER_NAME_EXISTS = "12008";
	public static final String USER_NAME_NOT_EXISTS = "12009";
	public static final String USER_LOGIN_ACCOUNT_EMPTY = "12010";
	public static final String USER_LOGIN_ACCOUNT_ERROR = "12011";
	public static final String USER_INSERT_ERROR = "12012";
	public static final String USER_NAME_ERROR = "12013";
	public static final String USER_DELETE_ERROR = "12014";
	public static final String USER_MODIFY_FAILURE = "12015";
	public static final String USER_INSERT_FAILURE = "12016";
	public static final String USER_EMAIL_EXISTS = "12017";
	public static final String USER_PHONE_EXISTS = "12018";
	public static final String USER_PHONE_NOT_EXISTS = "12019";
	public static final String USER_EMAIL_NOT_EXISTS = "12020";
	public static final String USER_OTHER_CONTACT_ERROR = "12021";
	public static final String USER_PIC_ERROR = "12022";
	public static final String USER_PIC_UPDATE_ERROR = "12023";
	public static final String USER_BLOCK_STATE1 = "12024";

	public static final String USER_PHONE_NEEL_VALIDATE = "12024";
	public static final String USER_EMAIL_NEEL_VALIDATE = "12025";

	public static final String USER_LOGIN_NEEL_CODE = "12026";  //登录登录需要验证码
	//demand
	public static final String DEMAND_TITLE_ERROR = "13000";
	public static final String DEMAND_INSERT_ERROR = "13001";
	public static final String DEMAND_ID_EXISTS = "13002";
	public static final String DEMAND_NOT_EXISTS = "13003";
	public static final String DEMAND_MODIFY_STATE_ERROR = "13004";
	public static final String DEMAND_ENCLOSURE_ERROR = "13005";
	public static final String DEMAND_STATE_ERROR = "13006";
	public static final String DEMAND_AMOUNT_ERROR = "13007";
	public static final String DEMAND_EXPIRE_DATE_ERROR = "13008";
	public static final String DEMAND_MODIFY_ERROR = "13001";
	
	//demand receive 
	public static final String RECEIVEDEMAND_INSERT_ERROR = "14000";
	public static final String RECEIVEDEMAND_ID_ERROR = "14001";
	public static final String RECEIVEDEMAND_ID_EXISTS = "14002";
	public static final String RECEIVEDEMAND_NOT_EXISTS = "14003";
	public static final String RECEIVEDEMAND_STAGETPYE_ERROR = "14004";
	public static final String RECEIVEDEMAND_MODIFY_EVALUATE_ERROR = "14005";
	public static final String RECEIVEDEMAND_HAS_BENN_RECEIVE = "14006";
	//account
	public static final String ACCOUNT_INSERT_FAILURE = "15001";
	public static final String ACCOUNT_RECHARGE_RMB_FAILURE = "15002";
	public static final String ACCOUNT_SCAN_PAY_NOT_COMPLETE = "15003";//仅仅表示没检测到用户付款,并非出错
	public static final String ACCOUNT_SCAN_PAY_NOT_EXISTS = "15004";//找不到对应要添加的扫码充值信息session里面
	public static final String ACCOUNT_INSUFFICIENT_BALANCE = "15005";//余额不足
	
	// message
	public static final String MESSAGE_INSERT_FAILURE = "16001";
	public static final String MESSAGE_CONTENT_ERROR = "16002";
	public static final String MESSAGE_FATHER_ID_ERROR = "16003";
	public static final String MESSAGE_DELETE_FAILURE = "16004";
	//Withdrawals
	public static final String WITHDRAWALS_INSERT_FAILURE = "17001";
	
	//specialty 
	public static final String SPECIALTY_DELETE_FAILURE = "18001";
	public static final String SPECIALTY_INSTER_FAILURE = "18002";
	public static final String SPECIALTY_AUTH_FAILURE = "18003";
	public static final String SPECIALTY_ALIAS_UPDATE_FAILURE = "18004";
	
	//demand message 
	public static final String EDEMANDMESSAGE_INSERT_ERROR = "19000";
	public static final String EDEMANDMESSAGE_CONTENT_ERROR = "19001";
	public static final String EDEMANDMESSAGE_FATHER_ID_ERROR = "19002";
	public static final String EDEMANDMESSAGE_DEMAND_ID_ERROR = "19003";
	//message user
	public static final String MESSAGEUSER_UPDATE_ERROR = "20000";
	public static final String MESSAGEUSER_INSERT_FAILURE = "20001";
	
	//message system
	public static final String MESSAGESYSTEM_UPDATE_ERROR = "21000";
	public static final String MESSAGESYSTEM_INSERT_FAILURE = "21001";
	
	//recommend
	public static final String RECOMMEND_UPDATE_ERROR = "22000";
	public static final String RECOMMEND_INSERT_FAILURE = "22001";
	//works
	public static final String WORKS_URL_NOT_EXISTS = "23000";
	public static final String WORKS_URL_ERROR = "23001";
	public static final String WORKS_MAX_LIMIT = "23002";
	public static final String WORKS_NOT_EXISTS = "23003";
	public static final String WORKs_MOFIFY_FAIL = "23004";
	
	
	//demand attach
	public static final String DEMANDATTACH_INSTER_ERROR = "24000";
	public static final String DEMANDATTACH_NOT_EXISTS = "24001";
	public static final String DEMANDATTACH_DELETE_ERROR = "24002";
	public static final String DEMANDATTACH_ACHIEVE_ERROR = "24003";
	public static final String DEMANDATTACH_MODIFY_ERROR = "24004";
	
	//arbitration
	public static final String ARBITRATION_EXISTS = "25000";
	public static final String ARBITRATION_NOT_EXISTS = "25001";
	public static final String ARBITRATION_STATE_ERROR = "25002";
	
	//education
	public static final String EDUCATION_NOT_EXISTS = "26001";
	
	//work experience
	public static final String WORK_EXPERIENCE_NOT_EXISTS = "27001";
	
	//demand follow
	public static final String DEMAND_FOLLOW_EXISTS = "28000";
	public static final String DEMAND_FOLLOW_FAIL = "28001";
	
	// blacklist
	public static final String MESSAGE_BLACKLIST_INSERT_FAILURE = "29000";
	public static final String MESSAGE_BLACKLIST_DELETE_ERROR = "29001";
	//ANet recharge
	public static final String ANET_RECHARGE_ERROR = "30000";
	
	//ip record
	public static final String IPRECORD_INSERT_FAILURE = "31000";

	
}
