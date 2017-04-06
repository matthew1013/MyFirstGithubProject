package com.anyonehelps.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.AccountDao;
import com.anyonehelps.dao.AccountDetailDao;
import com.anyonehelps.dao.InvoiceNoDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.AccountDetail;
import com.anyonehelps.model.InvoiceNo;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.AccountService;
import com.anyonehelps.service.WeixinService;

@Service("weixinService")
public class WeixinServiceImpl implements WeixinService{
	@Resource(name = "accountService")
	private AccountService accountService;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private InvoiceNoDao invoiceNoDao;

	public ResponseObject<String> scanRecharge(String userId, String amount, String flag) throws ServiceException {
		ResponseObject<String> responseObject = new ResponseObject<String>();
		/*try{
			if(ResponseCode.ACCOUNT_SCAN_PAY_NOT_COMPLETE.equals(this.checkIfScanPay(userId, amount, flag).getCode())){//未添加付款数据
				int result = this.accountDetailDao.updateStateByPaySuccess(userId, flag);
				if(result > 0){
					result = this.accountDao.rechargeRmb(userId, amount, DateUtil.date2String(new Date()));
					if(result > 0){
						responseObject.setCode(ResponseCode.SUCCESS_CODE);
					}else{
						responseObject.setCode(ResponseCode.ACCOUNT_RECHARGE_RMB_FAILURE);
						responseObject.setMessage("账户人民币充值失败，请联系客服");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw ExceptionUtil.handle2ServiceException("进行账户支付出现异常", e);
		}*/
		return responseObject;
	}

	public ResponseObject<Object> checkIfScanPay(String userId, String amount,
			String flag) throws ServiceException {
		ResponseObject<Object> responseObject = new ResponseObject<Object>();
		if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(amount) || StringUtil.isEmpty(flag)){
			responseObject.setCode(ResponseCode.PARAMETER_ERROR);
			responseObject.setMessage("参数错误");
		}else{
			try{
				String state = this.accountDetailDao.checkIfScanPayDetail(userId, amount, flag);
				if(StringUtil.isEmpty(state)){//找不到记录
					responseObject.setCode(ResponseCode.ACCOUNT_SCAN_PAY_NOT_EXISTS);
					responseObject.setMessage("没有待充值的信息");
				}else if(state.equals(Constant.ACCOUNT_DETAIL_STATE2)){//已付款
					responseObject.setCode(ResponseCode.SUCCESS_CODE);
				}else{//未付款
					responseObject.setCode(ResponseCode.ACCOUNT_SCAN_PAY_NOT_COMPLETE);
					responseObject.setMessage("未付款");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw ExceptionUtil.handle2ServiceException("进行账户支付查询出现异常", e);
			}
		}
		return responseObject;
	}

	public ResponseObject<Object> addPreRecharge(String userId, double amount, String flag)
			throws ServiceException {
		ResponseObject<Object> responseObject = new ResponseObject<Object>();
		String date = DateUtil.date2String(new Date());
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setAmount(String.valueOf(amount));
		accountDetail.setRealAmount(accountDetail.getAmount());
		accountDetail.setUserId(userId);
		accountDetail.setCurrency("人民币");
		accountDetail.setName("微信扫码支付");
		accountDetail.setType(Constant.ACCOUNT_DETAIL_TYPE12);
		accountDetail.setState(Constant.ACCOUNT_DETAIL_STATE1);//未扫码付款
		accountDetail.setRemark(flag);
		accountDetail.setDemandId("");
		accountDetail.setThirdNo(flag);
		accountDetail.setCreateDate(date);
		accountDetail.setModifyDate(date);
		
		try{
			//发票号
			InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
			if(in != null){
				if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
					accountDetail.setInvoiceNo(in.getInvoiceNo());
				}
			}
			
			String result = this.accountService.addAccountDetail(accountDetail).getCode();
			if(result.equals(ResponseCode.SUCCESS_CODE)){
				responseObject.setCode(ResponseCode.SUCCESS_CODE);
			}else{
				responseObject.setCode(ResponseCode.ACCOUNT_INSERT_FAILURE);
				responseObject.setMessage("账户充值记录加载");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw ExceptionUtil.handle2ServiceException("进行账户充值加载出现异常", e);
		}
		return responseObject;
	}

	/*public boolean setMapFromDataBase(Map<String, String> map) throws ServiceException {
		boolean result = false;
		String key = "";
		String appid = "";
		String mchId = "";
		try {
			//key = this.globalargsDao.getcontentbyflag("weixin_scan_pay_key");
			//appid = this.globalargsDao.getcontentbyflag("weixin_scan_pay_appid");
			//mchId = this.globalargsDao.getcontentbyflag("weixin_scan_pay_mch_id");
			key = "XW56zlrXLbjZ97R9IocxkGUfqvtbPHyF";
			appid = "wxe1dd8f26dd6189a8";
			mchId = "1295576401";
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtil.isEmpty(key) || StringUtil.isEmpty(appid) || StringUtil.isEmpty(mchId)){
		}else{
			WeixinConfig.key = key;//key的计算方式不一样，要计算sign后才能添加
			map.put("appid", appid);
			map.put("mch_id", mchId);
			result = true;
		}
		
		return result;
	}*/
}
