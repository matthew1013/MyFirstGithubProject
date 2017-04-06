package com.anyonehelps.common.constants;


public class Constant {    
	public static final String PHONE_SEND_CODE = "phone_validate_code";
	public static final String PHONE_KEY = "phone_key";
	public static final String PHONE_AREA_CODE = "phone_area_code";
	public static final String PHONE_SEND_CODE_TIME = "phone_send_code_time";
	public static final String SECURITY_QUESTION_EMAIL = "security_question_email"; 
	public static final String SECURITY_QUESTION_AREA_CODE = "security_question_area_code";
	public static final String SECURITY_QUESTION_PHONE = "security_question_phone"; 
	public static final String SECURITY_QUESTION = "security_question"; //密保是否输入正确
	public static final String SECURITY_QUESTION_TIME = "security_question_time"; //密保是否输入正确过期时间
	
	public static final String SECURITY_QUESTION_RESETPW_USERID = "security_question_reset_userid"; //密保重置密码userId
	
	public static final String PHONE_SEND_RESET_PWD_CODE = "phone_reset_pwd_code";
	public static final String PHONE_RESET_PWD_KEY = "phone_reset_pwd_key";
	public static final String PHONE_SEND_RESET_PWD_CODE_TIME = "phone_send_reset_pwd_code_time";
	
	public static final String EMAIL_SEND_CODE = "email_validate_code";
	public static final String EMAIL_KEY = "email_key";
	
	public static final String SECURITY_CODE_KEY = "";
	public static final String LOGIN_ACCOUNT_TYPE = "login_account_type";
	
	public static final String USER_WEIXIN_SCAN_PAY_SESSION_KEY = "user_weixin_scan_pay_session_key";

	// user
	public static final String USER_NICK_NAME_SESSION_KEY = "user_username_session_key";
	public static final String USER_ID_SESSION_KEY = "user_id_session_key";
	public static final String USER_TYPE_SESSION_KEY = "user_type_session_key";
	public static final String USER_EMAIL_SESSION_KEY = "user_email_session_key";
	public static final String USER_PIC_URL_SESSION_KEY = "user_pic_url_session_key";
	
	
	public static final String USER_TYPE_NORMAL = "0"; // 普通用户
	
	public static final String USER_EMAIL_STATE0="0";//邮箱注册待验证状态
	public static final String USER_EMAIL_STATE1="1";//邮箱注册已验证状态
	
	public static final String USER_TELPHONE_STATE0="0";//电话注册待验证状态
	public static final String USER_TELPHONE_STATE1="1";//电话注册已验证状态
	

	public static final String USER_BLOCK_STATE0="0";//正常用户
	public static final String USER_BLOCK_STATE1="1";//黑名单用户

	public static final String EMAIL_PROPERTIES_FILE = "/email.properties";
	public static final long RESET_PWD_TOKEN_TIME_OF_HOUR = 24L;
	public static final long RESET_PWD_TOKEN_TIME = RESET_PWD_TOKEN_TIME_OF_HOUR * 60 * 60; // 一天，多少秒
	public static final long VERIFY_EMAIL_TOKEN_TIME_OF_HOUR = 24L;
	public static final long VERIFY_EMAIL_TOKEN_TIME = VERIFY_EMAIL_TOKEN_TIME_OF_HOUR * 60 * 60; // 一天，多少秒
	public static final String TOKEN_TYPE_PWD0="0";//修改密码Token type
	public static final String TOKEN_TYPE_EMAIL1="1";//验证邮箱Token type
	public static final String SYSTEM_PROPERTIES_FILE = "/system.properties";
	public static final String SYSTEM_CN_PROPERTIES_FILE = "/system_cn.properties";
	public static final String SYSTEM_US_PROPERTIES_FILE = "/system_us.properties";
	public static final String PROPERTIES_FILE = "/anyonehepls.properties";
	
	public static final String COUNTRY_CODE_US = "US";
	public static final String COUNTRY_CODE_CN = "CN";

	public static final String DEMAND_STATE_READY = "0"; //等待接单
	public static final String DEMAND_STATE_RECEIVE = "1"; // 已有接单
	public static final String DEMAND_STATE_SELECT = "2"; // 选取写手 
	public static final String DEMAND_STATE_START = "3"; // 任务开始
	public static final String DEMAND_STATE_CLOSE = "4"; // 关闭
	public static final String DEMAND_STATE_FINISH = "5"; // 完成
	public static final String DEMAND_STATE_PAY = "6"; // 支付
	public static final String DEMAND_STATE_ARBITRATION = "7"; // 接任务人提出仲裁
	public static final String DEMAND_STATE_END = "8"; // 完成结束
	
	public static final String DEMAND_ENCLOSURE = "demand_enclosure";
	
	public static final String DEMAND_PAY_STATE0="0";//正常用户
	public static final String DEMAND_PAY_STATE1="1";//黑名单用户
	public static final int DEMAND_NO_PAY_COUNT= 10;//允许后付款的发任务最大数

	public static final String ENCLOSURE_PDF_STATE0 = "0";
	public static final String ENCLOSURE_PDF_STATE1 = "1";
	public static final String ENCLOSURE_PDF_STATE2 = "2";
	
	public static final String ACCOUNT_DETAIL_TYPE11 = "11"; // Stripe信用卡充值
	public static final String ACCOUNT_DETAIL_TYPE12 = "12"; // 微信扫码支付
	public static final String ACCOUNT_DETAIL_TYPE13 = "13"; // 支付宝充值
	public static final String ACCOUNT_DETAIL_TYPE14 = "14"; // paypal 美元充值
	public static final String ACCOUNT_DETAIL_TYPE15 = "15"; // ANet信用卡充值
	public static final String ACCOUNT_DETAIL_TYPE16 = "16"; // 信用卡充值赠送
	public static final String ACCOUNT_DETAIL_TYPE17 = "17"; // 收到转账付款费用   --新：银行转账充值  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE18 = "18"; // 任务金额冲正   ---新：冻结金额返还（任务） 2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE19 = "19"; // 附加任务金额冲正  ----新：   冻结金额返还（附加任务）  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE20 = "20"; // 修改任务金额，金额返回     --新：冻结金额返还（任务修改）  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE21 = "21"; // 关闭任务金额冲正     --新：冻结金额返还（任务关闭）  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE22 = "22"; // 完成任务获取任务报酬     --新：任务完成报酬  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE23 = "23"; // 任务部分付款，返还金额     --新：冻结金额返还（部分支付）  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE24 = "24"; // 提现冲正
	public static final String ACCOUNT_DETAIL_TYPE25 = "25"; // 任务奖励
	public static final String ACCOUNT_DETAIL_TYPE26 = "26"; //返还完成任务扣取收款手续费     --新：任务报酬手续费返还  2017/03/09
	
	public static final String ACCOUNT_DETAIL_TYPE31 = "31"; //转账
	public static final String ACCOUNT_DETAIL_TYPE32 = "32"; //信用卡充值手续费
	public static final String ACCOUNT_DETAIL_TYPE33 = "33"; //任务金额冻结     --新：任务金额冻结  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE34 = "34"; //技能认证费用   --新：技能认证费  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE35 = "35"; //转账付款手续费
	public static final String ACCOUNT_DETAIL_TYPE36 = "36"; //附加任务金额冻结
	public static final String ACCOUNT_DETAIL_TYPE37 = "37"; //关闭任务扣取手续费
	public static final String ACCOUNT_DETAIL_TYPE38 = "38"; //完成任务扣取收款手续费     --新：收取任务报酬手续费  2017/03/09
	public static final String ACCOUNT_DETAIL_TYPE39 = "39"; //修改任务余额冻结
	public static final String ACCOUNT_DETAIL_TYPE40 = "40"; //提现
	public static final String ACCOUNT_DETAIL_TYPE41 = "41"; //任务支付
	
	
	
	/**
	 * Account detail state 待处理
	 */
	public static final String ACCOUNT_DETAIL_STATE1 = "0"; // 待处理
	/**
	 * Account detail state 完成
	 */
	public static final String ACCOUNT_DETAIL_STATE2 = "1"; // 完成
	/**
	 * Account detail state 操作失败
	 */
	public static final String ACCOUNT_DETAIL_STATE3 = "2"; // 操作失败
	
	public static final String MESSAGE_STATE_EMP_NOT_DEAL = "0"; // 管理员未处理的留言状态
	public static final String MESSAGE_STATE_USER_NOT_DEAL = "1"; // 用户未处理的留言状态
	
	//specialty user
	public static final String SPECIALTY_STATE_NOT_AUTH = "0";  //待认证
	public static final String SPECIALTY_STATE_AUTH_ING = "1"; //认证审核中
	public static final String SPECIALTY_STATE_AUTH_SUCC = "2"; //认证成功
	
	public static final String SPECIALTY_TYPE0 = "0";  //系统技能
	public static final String SPECIALTY_TYPE1 = "1"; //自定义技能
	
	public static final String MESSAGE_STATE_UNREAD = "0"; //未读
	public static final String MESSAGE_STATE_READ = "1"; //已读
	public static final String MESSAGE_STATE_DELETE = "2"; //删除
	
	public static final String MESSAGEUSER_STATE_UNREAD = "0"; //未读
	public static final String MESSAGEUSER_STATE_READ = "1"; //已读
	public static final String MESSAGEUSER_STATE_DELETE = "2"; //删除
	
	public static final String MESSAGESYSTEM_STATE_UNREAD = "0"; //未读
	public static final String MESSAGESYSTEM_STATE_READ = "1"; //已读
	public static final String MESSAGESYSTEM_STATE_DELETE = "2"; //删除
	
	public static final String MESSAGESYSTEM_LEVEL_DEFAULT = "1"; 
	public static final String MESSAGESYSTEM_LEVEL_PRIMARY = "2"; 
	public static final String MESSAGESYSTEM_LEVEL_SUCCESS = "3"; 
	public static final String MESSAGESYSTEM_LEVEL_INFO = "4"; 
	public static final String MESSAGESYSTEM_LEVEL_WARNING = "5"; 
	public static final String MESSAGESYSTEM_LEVEL_DANGER = "6"; 
	
	public static final double usd2rmb = 6.5; // 美元转人民币
	public static final double rmb2usd = 0.14; // 人民币转美元
	
	public static final String RECEIVEDEMAND_STATE_UNCHECKED = "0"; //未选中 
	public static final String RECEIVEDEMAND_STATE_CHECKED = "1"; // 选中
	public static final String RECEIVEDEMAND_STATE_TEMP_CHECKED = "2"; // 临时选中
	
	public static final String RECEIVEDEMAND_PAY_STATE0 = "0"; //未付款
	public static final String RECEIVEDEMAND_PAY_STATE1 = "1"; //全部付款
	public static final String RECEIVEDEMAND_PAY_STATE2 = "2"; //部分付款，等待接任务方确认
	public static final String RECEIVEDEMAND_PAY_STATE3 = "3"; //接任务方确认
	public static final String RECEIVEDEMAND_PAY_STATE4 = "4"; //接单人反对，提交平台裁决
	public static final String RECEIVEDEMAND_PAY_STATE5 = "5"; //平台裁决中
	public static final String RECEIVEDEMAND_PAY_STATE6 = "6"; //平台裁决结束
	public static final String RECEIVEDEMAND_PAY_STATE7 = "7"; //发单人同意接单人付款要求
	
	public static final String RECEIVEDEMAND_PAY_PERCENT0 = "0"; //百分比为0
	public static final String RECEIVEDEMAND_PAY_PERCENT30 = "30"; //百分比
	public static final String RECEIVEDEMAND_PAY_PERCENT60 = "60"; //百分比
	public static final String RECEIVEDEMAND_PAY_PERCENT100 = "100"; //百分比
	
	public static final String RECEIVEDEMAND_EVALUATE_STATE_UNCHECKED = "0"; //未评价
	public static final String RECEIVEDEMAND_EVALUATE_STATE_CHECKED = "1";   //已评价
	
	public static final String RECEIVEDEMAND_EVALUATE_STATE_PUBLISH_UNCHECKED = "0"; //未评价
	public static final String RECEIVEDEMAND_EVALUATE_STATE_PUBLISH_CHECKED = "1";   //已评价
	
	public static final String EVALUATE_DIRECTION0 = "0";   //做任务者向发布者评价
	public static final String EVALUATE_DIRECTION1 = "1";   //发布者向做任务者评价
	
	public static final double EVALUATE_QUALITY_PERCENT = 0.50;    //任务质量评分比重
	public static final double EVALUATE_EVALUATE_PERCENT = 0.25;   //任务诚信评分比重
	public static final double EVALUATE_PUNCTUAL_PERCENT = 0.25;   //任务准时评分比重
	public static final double EVALUATE_PLATFORM_PERCENT = 0;   //任务平台评分比重
	
	public static final String RECOMMEND_STATE0 = "0";     //未接受
	public static final String RECOMMEND_STATE1 = "1";     //邀请成功
	public static final String RECOMMEND_STATE2 = "2";     //邀请失败
	public static final String RECOMMEND_STATE3 = "3";     //邀请过期
	public static final String RECOMMEND_STATE4 = "4";     //未关联
	public static final String RECOMMEND_STATE5 = "5";     //关联成功
	public static final String RECOMMEND_STATE6 = "6";     //关联失败
	
	public static final String RECOMMEND_SMS_STATE0 = "0";   
	public static final String RECOMMEND_SMS_STATE1 = "1";
	
	public static final String BONUSPOINT_SUBLEVEL1 = "1";   
	public static final String BONUSPOINT_SUBLEVEL2 = "2";  
	public static final String BONUSPOINT_SUBLEVEL3 = "3";   
	/**
	 * 0表示发任务者发起仲裁
	 * 1表示做任务者同意仲裁
	 * 2表示做任务者不同意仲裁
	 * 3表示平台后台开始审理仲裁
	 * 4表示平台后台已经作出裁决
	 */
	public static final String ARBITRATION_STATE_0 = "0"; 
	/**
	 * 0表示发任务者发起仲裁
	 * 1表示做任务者同意仲裁
	 * 2表示做任务者不同意仲裁
	 * 3表示平台后台开始审理仲裁
	 * 4表示平台后台已经作出裁决
	 */
	public static final String ARBITRATION_STATE_1 = "1";
	/**
	 * 0表示发任务者发起仲裁
	 * 1表示做任务者同意仲裁
	 * 2表示做任务者不同意仲裁
	 * 3表示平台后台开始审理仲裁
	 * 4表示平台后台已经作出裁决
	 */
	public static final String ARBITRATION_STATE_2 = "2";
	/**
	 * 0表示发任务者发起仲裁
	 * 1表示做任务者同意仲裁
	 * 2表示做任务者不同意仲裁
	 * 3表示平台后台开始审理仲裁
	 * 4表示平台后台已经作出裁决
	 */
	public static final String ARBITRATION_STATE_3 = "3"; 
	/**
	 * 0表示发任务者发起仲裁
	 * 1表示做任务者同意仲裁
	 * 2表示做任务者不同意仲裁
	 * 3表示平台后台开始审理仲裁
	 * 4表示平台后台已经作出裁决
	 */
	public static final String ARBITRATION_STATE_4 = "4"; 
	
	// admin
	public static final String ADMIN_ID_SESSION_KEY = "admin_id_session_key";
	public static final String ADMIN_EMAIL_SESSION_KEY = "admin_email_session_key";
		
	public static final String PRO_REQUEST_AMOUNT = "50";
	
	//pro
	//0表示未支付，1表示已支付，2表示认证通过，3表示认证未通过
	public static final String PROUSER_STATE0 = "0"; // 未支付
	public static final String PROUSER_STATE1 = "1"; // 已支付
	public static final String PROUSER_STATE2 = "2"; // 认证通过
	public static final String PROUSER_STATE3 = "3"; // 认证未通过
	
	//invoice 发票
	public static final String INVOICE_TYPE0 = "0"; //充值金额发票类型
	public static final String INVOICE_TYPE1 = "1"; //任务冻结金额发票类型
	public static final String INVOICE_TYPE2 = "2"; //任务支付金额发票类型
	public static final String INVOICE_TYPE3 = "3"; //任务返还金额发票类型
	public static final String INVOICE_TYPE4 = "4"; //任务收款金额发票类型
	public static final String INVOICE_TYPE5 = "5"; //提现金额发票类型
	public static final String INVOICE_TYPE6 = "6"; //技能认证金额发票类型
	public static final String INVOICE_TYPE7 = "7"; //完成任务扣取收款手续费
	
	//withdrawals
	public static final String WITHDRAWALS_TYPE_BNK = "1"; //取现类型：银行卡
	public static final String WITHDRAWALS_TYPE_PAYPAL = "2"; //取现类型：Paypal
	public static final String WITHDRAWALS_ACCOUNT_TYPE0 = "0"; //个人
	public static final String WITHDRAWALS_ACCOUNT_TYPE1 = "1"; //商业
	public static final String WITHDRAWALS_BNK_TYPE0 = "0"; //储蓄账号
	public static final String WITHDRAWALS_BNK_TYPE1 = "1"; //支票账号

	public static final String WITHDRAWALS_STATE0 = "0"; //未处理
	public static final String WITHDRAWALS_STATE1 = "1"; //处理中
	public static final String WITHDRAWALS_STATE2 = "2"; //完成
	public static final String WITHDRAWALS_STATE3 = "3"; //失败
	
	//ip record
	public static final String IPRECORD_TYPE0 = "0"; //0表示未知 
	public static final String IPRECORD_TYPE1 = "1"; //1表示登录
	public static final String IPRECORD_TYPE2 = "2"; //2表示邮箱注册
	public static final String IPRECORD_TYPE3 = "3"; //3表示手机验证码
	
	//email send 
	public static final String EMAILSEND_STATE0 = "0"; //0表示未发送 
	public static final String EMAILSEND_STATE1 = "1"; //1表示发送中
	public static final String EMAILSEND_STATE2 = "2"; //2表示发送成功
	public static final String EMAILSEND_STATE3 = "3"; //3表示发送失败
	
	public static final String EMAILSEND_FAIL_COUNT0 = "0"; //0表示失败次数0
	public static final String EMAILSEND_FAIL_COUNT1 = "1"; //1表示失败次数1
	public static final String EMAILSEND_FAIL_COUNT2 = "2"; //2表示失败次数2
	public static final String EMAILSEND_FAIL_COUNT3 = "3"; //3表示失败次数3
	
	public static final String SMSSEND_STATE0 = "0"; //0表示未发送 
	public static final String SMSSEND_STATE1 = "1"; //1表示发送中
	public static final String SMSSEND_STATE2 = "2"; //2表示发送成功
	public static final String SMSSEND_STATE3 = "3"; //3表示发送失败
	
	public static final String SMSSEND_FAIL_COUNT0 = "0"; //0表示失败次数0
	public static final String SMSSEND_FAIL_COUNT1 = "1"; //1表示失败次数1
	public static final String SMSSEND_FAIL_COUNT2 = "2"; //2表示失败次数2
	public static final String SMSSEND_FAIL_COUNT3 = "3"; //3表示失败次数3
	
	//demand attach
	public static final String DEMAND_ATTACH_STATE0 = "0"; //0表示发布
	public static final String DEMAND_ATTACH_STATE1 = "1"; //1表示接单人接受
	public static final String DEMAND_ATTACH_STATE2 = "2"; //2表示接单人拒绝
	
}
