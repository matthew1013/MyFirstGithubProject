package com.anyonehelps.common.util.aent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import net.authorize.Environment;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.CreditCardType;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionResponse;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.PropertiesReader;
import com.twilio.sdk.TwilioRestException;

public class AentUtil {
	public static void main(String[] args) throws TwilioRestException {
		CreateTransactionResponse response = payCreditcCard("","","","",0.6);
	      if (response!=null) {
	        	// If API Response is ok, go ahead and check the transaction response
	        	if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
	        		TransactionResponse result = response.getTransactionResponse();
	        		if(result.getMessages() != null){
	        			System.out.println("Successfully created transaction with Transaction ID: " + result.getTransId());
	        			System.out.println("Response Code: " + result.getResponseCode());
	        			System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
	        			System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
	        			System.out.println("Auth Code: " + result.getAuthCode());
	        		}
	        		else {
	        			System.out.println("Failed Transaction.");
	        			if(response.getTransactionResponse().getErrors() != null){
	        				System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
	        				System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
	        			}
	        		}
	        	}
	        	else {
	        		System.out.println("Failed Transaction.");
	        		if(response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null){
	        			System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
	        			System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
	        		}
	        		else {
	        			System.out.println("Error Code: " + response.getMessages().getMessage().get(0).getCode());
	        			System.out.println("Error message: " + response.getMessages().getMessage().get(0).getText());
	        		}
	        	}
	        }
	        else {
	        	System.out.println("Null Response.");
	        }
	        
	}
	private static String apiLoginId;
	private static String transactionKey;
	static { 
		Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
		apiLoginId = props.getProperty("authorize.loginid");
		transactionKey = props.getProperty("authorize.transactionkey");
	} 
    //
    // Run this sample from command line with:
    //                 java -jar target/ChargeCreditCard-jar-with-dependencies.jar
    //
    public static CreateTransactionResponse payCreditcCard(String creditNo,String securityCode,String expireYear,String expireMonth,Double amount) {

        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.PRODUCTION);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(creditNo);
        creditCard.setExpirationDate(expireMonth+expireYear);
        creditCard.setCardCode(securityCode);
        paymentType.setCreditCard(creditCard);

        // Create the payment transaction request
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setPayment(paymentType);
        txnRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));

        // Make the API Request
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(txnRequest);
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();


        CreateTransactionResponse response = controller.getApiResponse();
        return response;
        /*if (response!=null) {
        	// If API Response is ok, go ahead and check the transaction response
        	if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
        		TransactionResponse result = response.getTransactionResponse();
        		if(result.getMessages() != null){
        			System.out.println("Successfully created transaction with Transaction ID: " + result.getTransId());
        			System.out.println("Response Code: " + result.getResponseCode());
        			System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
        			System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
        			System.out.println("Auth Code: " + result.getAuthCode());
        		}
        		else {
        			System.out.println("Failed Transaction.");
        			if(response.getTransactionResponse().getErrors() != null){
        				System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        				System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
        			}
        		}
        	}
        	else {
        		System.out.println("Failed Transaction.");
        		if(response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null){
        			System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        			System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
        		}
        		else {
        			System.out.println("Error Code: " + response.getMessages().getMessage().get(0).getCode());
        			System.out.println("Error message: " + response.getMessages().getMessage().get(0).getText());
        		}
        	}
        }
        else {
        	System.out.println("Null Response.");
        }
        
		return response;*/

    }
    
    
}
