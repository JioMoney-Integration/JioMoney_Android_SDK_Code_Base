
package com.ril.jm_sdk.model;

import java.io.Serializable;

/**
 * Created by COMPAQ on 12-07-2016.
 */
public class JMPaymentResponse implements Serializable {

    private String status;
    private String clientId;
    private String merchantId;
    private String customerId;
    private String txnRefNo;
    private String jioTxnRefNo;
    private String txnAmount;
    private String errorCode;
    private String responseMessage;
    private String txnTimestamp;
    private String cardNumber;
    private String txnType;
    private String cardType;
    private String mobileNumber;
    private String checksum;
    private String productDescription;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
    private String udf5;

    JMPaymentResponse(){

    }

    /**
     * Response model for version 1.0 without UDFs
     * @param status
     * @param clientId
     * @param merchantId
     * @param customerId
     * @param txnRefNo
     * @param jioTxnRefNo
     * @param txnAmount
     * @param errorCode
     * @param responseMessage
     * @param txnTimestamp
     * @param cardNumber
     * @param txnType
     * @param cardType
     * @param checksum
     */
    public JMPaymentResponse(String status, String clientId, String merchantId, String customerId,
                             String txnRefNo, String jioTxnRefNo, String txnAmount, String errorCode, String responseMessage,
                             String txnTimestamp, String cardNumber, String txnType, String cardType, String checksum) {
        this.status = status;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.txnRefNo = txnRefNo;
        this.jioTxnRefNo = jioTxnRefNo;
        this.txnAmount = txnAmount;
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
        this.txnTimestamp = txnTimestamp;
        this.cardNumber = cardNumber;
        this.txnType = txnType;
        this.cardType = cardType;
        this.checksum = checksum;
    }


    /**
     * Response model for version error with checksum value
     * @param status
     * @param clientId
     * @param merchantId
     * @param customerId
     * @param txnRefNo
     * @param jioTxnRefNo
     * @param txnAmount
     * @param errorCode
     * @param responseMessage
     * @param txnTimestamp
     * @param cardNumber
     * @param txnType
     * @param cardType
     */
    public JMPaymentResponse(String status, String clientId, String merchantId, String customerId,
                             String txnRefNo, String jioTxnRefNo, String txnAmount, String errorCode, String responseMessage,
                             String txnTimestamp, String cardNumber, String txnType, String cardType) {
        this.status = status;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.txnRefNo = txnRefNo;
        this.jioTxnRefNo = jioTxnRefNo;
        this.txnAmount = txnAmount;
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
        this.txnTimestamp = txnTimestamp;
        this.cardNumber = cardNumber;
        this.txnType = txnType;
        this.cardType = cardType;
    }

    /**
     * Response model for version 2.0 without UDF
     * @param status
     * @param clientId
     * @param merchantId
     * @param customerId
     * @param txnRefNo
     * @param jioTxnRefNo
     * @param txnAmount
     * @param errorCode
     * @param responseMessage
     * @param txnTimestamp
     * @param cardNumber
     * @param txnType
     * @param cardType
     * @param mobileNumber
     * @param checksum
     */
    public JMPaymentResponse(String status, String clientId, String merchantId, String customerId,
                             String txnRefNo, String jioTxnRefNo, String txnAmount, String errorCode, String responseMessage,
                             String txnTimestamp, String cardNumber, String txnType, String cardType, String mobileNumber, String checksum) {
        this.status = status;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.txnRefNo = txnRefNo;
        this.jioTxnRefNo = jioTxnRefNo;
        this.txnAmount = txnAmount;
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
        this.txnTimestamp = txnTimestamp;
        this.cardNumber = cardNumber;
        this.txnType = txnType;
        this.cardType = cardType;
        this.mobileNumber = mobileNumber;
        this.checksum = checksum;
    }

    /**
     * Response model for version 2.0 with UDF
     * @param status
     * @param clientId
     * @param merchantId
     * @param customerId
     * @param txnRefNo
     * @param jioTxnRefNo
     * @param txnAmount
     * @param errorCode
     * @param responseMessage
     * @param txnTimestamp
     * @param cardNumber
     * @param txnType
     * @param cardType
     * @param mobileNumber
     * @param udf1
     * @param udf2
     * @param udf3
     * @param udf4
     * @param udf5
     * @param checksum
     */
    public JMPaymentResponse(String status, String clientId, String merchantId, String customerId,
                             String txnRefNo, String jioTxnRefNo, String txnAmount, String errorCode,
                             String responseMessage, String txnTimestamp, String cardNumber, String txnType,
                             String cardType, String mobileNumber, String udf1,
                             String udf2, String udf3, String udf4, String udf5, String checksum) {
        this.status = status;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.txnRefNo = txnRefNo;
        this.jioTxnRefNo = jioTxnRefNo;
        this.txnAmount = txnAmount;
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
        this.txnTimestamp = txnTimestamp;
        this.cardNumber = cardNumber;
        this.txnType = txnType;
        this.cardType = cardType;
        this.mobileNumber = mobileNumber;
        this.checksum = checksum;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
        this.udf4 = udf4;
        this.udf5 = udf5;
    }

    /**
     *
     * @param status
     * @param clientId
     * @param merchantId
     * @param customerId
     * @param txnRefNo
     * @param jioTxnRefNo
     * @param txnAmount
     * @param errorCode
     * @param responseMessage
     * @param txnTimestamp
     * @param cardNumber
     * @param txnType
     * @param cardType
     * @param mobileNumber
     * @param productDescription
     * @param udf1
     * @param udf2
     * @param udf3
     * @param udf4
     * @param udf5
     * @param checksum
     */
    public JMPaymentResponse(String status, String clientId, String merchantId, String customerId,
                             String txnRefNo, String jioTxnRefNo, String txnAmount, String errorCode,
                             String responseMessage, String txnTimestamp, String cardNumber, String txnType,
                             String cardType, String mobileNumber,String productDescription, String udf1,
                             String udf2, String udf3, String udf4, String udf5, String checksum) {
        this.status = status;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.txnRefNo = txnRefNo;
        this.jioTxnRefNo = jioTxnRefNo;
        this.txnAmount = txnAmount;
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
        this.txnTimestamp = txnTimestamp;
        this.cardNumber = cardNumber;
        this.txnType = txnType;
        this.cardType = cardType;
        this.mobileNumber = mobileNumber;
        this.checksum = checksum;
        this.productDescription = productDescription;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
        this.udf4 = udf4;
        this.udf5 = udf5;
    }

    /**
     * Response model for version 1.0 with UDFs
     * @param status
     * @param clientId
     * @param merchantId
     * @param customerId
     * @param txnRefNo
     * @param jioTxnRefNo
     * @param txnAmount
     * @param errorCode
     * @param responseMessage
     * @param txnTimestamp
     * @param cardNumber
     * @param txnType
     * @param cardType
     * @param udf1
     * @param udf2
     * @param udf3
     * @param udf4
     * @param udf5
     * @param checksum
     */
    public JMPaymentResponse(String status, String clientId, String merchantId, String customerId,
                             String txnRefNo, String jioTxnRefNo, String txnAmount, String errorCode,
                             String responseMessage, String txnTimestamp, String cardNumber, String txnType,
                             String cardType, String udf1,
                             String udf2, String udf3, String udf4, String udf5, String checksum) {
        this.status = status;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.txnRefNo = txnRefNo;
        this.jioTxnRefNo = jioTxnRefNo;
        this.txnAmount = txnAmount;
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
        this.txnTimestamp = txnTimestamp;
        this.cardNumber = cardNumber;
        this.txnType = txnType;
        this.cardType = cardType;
        this.checksum = checksum;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
        this.udf4 = udf4;
        this.udf5 = udf5;
    }



    /**
     *
     * @param status
     * @param clientId
     * @param merchantId
     * @param customerId
     * @param txnRefNo
     * @param jioTxnRefNo
     * @param txnAmount
     * @param errorCode
     * @param responseMessage
     * @param txnTimestamp
     * @param cardNumber
     * @param txnType
     * @param cardType
     * @param udf1
     * @param udf2
     * @param udf3
     * @param udf4
     * @param udf5
     */
    public JMPaymentResponse(String status, String clientId, String merchantId, String customerId,
                             String txnRefNo, String jioTxnRefNo, String txnAmount, String errorCode,
                             String responseMessage, String txnTimestamp, String cardNumber, String txnType,
                             String cardType, String udf1,
                             String udf2, String udf3, String udf4, String udf5) {
        this.status = status;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.txnRefNo = txnRefNo;
        this.jioTxnRefNo = jioTxnRefNo;
        this.txnAmount = txnAmount;
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
        this.txnTimestamp = txnTimestamp;
        this.cardNumber = cardNumber;
        this.txnType = txnType;
        this.cardType = cardType;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
        this.udf4 = udf4;
        this.udf5 = udf5;
    }


    public String getStatus() {
        return status;
    }

    public String getClientId() {
        return clientId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getTxnRefNo() {
        return txnRefNo;
    }

    public String getJioTxnRefNo() {
        return jioTxnRefNo;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getTxnTimestamp() {
        return txnTimestamp;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getTxnType() {
        return txnType;
    }

    public String getCardType() {
        return cardType;
    }

    public String getChecksum() {
        return checksum;
    }

    @Override
    public String toString() {
        return "JMPaymentResponse{" +
                "status='" + status + '\'' +
                ", clientId='" + clientId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", txnRefNo='" + txnRefNo + '\'' +
                ", jioTxnRefNo='" + jioTxnRefNo + '\'' +
                ", txnAmount='" + txnAmount + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", txnTimestamp='" + txnTimestamp + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", txnType='" + txnType + '\'' +
                ", cardType='" + cardType + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", checksum='" + checksum + '\'' +
                ", udf1='" + udf1 + '\'' +
                ", udf2='" + udf2 + '\'' +
                ", udf3='" + udf3 + '\'' +
                ", udf4='" + udf4 + '\'' +
                ", udf5='" + udf5 + '\'' +
                '}';
    }
}
