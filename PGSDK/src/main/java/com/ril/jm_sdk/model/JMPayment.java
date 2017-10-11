package com.ril.jm_sdk.model;


import android.support.annotation.NonNull;

import com.ril.jm_sdk.config.JMPaymentConfig;
import com.ril.jm_sdk.util.CommonUtility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RIL
 * Simple Payment model to handle parameters which needs to send
 * request
 */
public class JMPayment{

    private static final String TAG = JMPayment.class.getSimpleName();

    public static final String PAYMENT_CONTENT = JMPayment.class.getSimpleName();

    public static final String TRANSACTION_TXNTYPE = "transaction.txntype";
    public static final String VERSION = "version";
    public static final String CLIENT_ID = "clientid";
    public static final String MERCHANT_ID = "merchantid";
    public static final String SUB_MERCHANT_ID = "submerchantid";
    public static final String SUB_TERMINAL_ID = "subterminalid";
    public static final String MERCHANT_NAME = "merchantname";
    public static final String CHECKSUM = "checksum";
    public static final String TOKEN = "token";
    public static final String CHANNEL = "channel";
    public static final String TRANSACTION_EXTREF = "transaction.extref";
    public static final String TRANSACTION_TIMESTAMP = "transaction.timestamp";
    public static final String TRANSACTION_CURRENCY = "transaction.currency";
    public static final String TRANSACTION_AMOUNT = "transaction.amount";
    public static final String RETURN_URL = "returl";
    public static final String SUBSCRIBER_CUSTOMER_ID = "subscriber.customerid";
    public static final String SUBSCRIBER_MOBILENUMBER = "subscriber.mobilenumber";
    public static final String SUBSCRIBER_CUSTOMER_NAME = "subscriber.customername";
    public static final String SUBSCRIBER_EMAIL = "subscriber.email";
    public static final String SUBSCRIBER_ADD1 = "subscriber.addline1";
    public static final String SUBSCRIBER_ADD2 = "subscriber.addline2";
    public static final String SUBSCRIBER_CITY = "subscriber.city";
    public static final String SUBSCRIBER_STATE = "subscriber.state";
    public static final String SUBSCRIBER_ZIPCODE = "subscriber.zipcode";
    public static final String PRODUCT_DESCRIPTION = "productdescription";
    public static final String UDF1 = "udf1";
    public static final String UDF2 = "udf2";
    public static final String UDF3 = "udf3";
    public static final String UDF4 = "udf4";
    public static final String UDF5 = "udf5";
    public static final String CARDINFO_PAN = "cardinfo.pan";
    public static final String CARDINFO_EXPMON = "cardinfo.expmon";
    public static final String CARDINFO_EXPYR = "cardinfo.expyr";
    public static final String CARDINFO_CVV = "cardinfo.cvv2";
    public static final String CARDINFO_BANKID = "cardinfo.bankid";
    public static final String CARDINFO_CARDTYPE = "cardinfo.cardtype";
    public static final String CARDINFO_NICKNAME = "cardinfo.nickname";

    public static final String NETBANKING_BANKID = "netbanking.bankid";
    public static final String NETBANKING_NICKNAME = "netbanking.nickname";
    public static final String NETBANKING_CARDTYPE = "netbanking.cardtype";

    public static final String TRANSACTION_TYPE = "transaction_type";
    private static String DESCRIPTION = "description";
    private static String COST = "cost";
    private static String QUANTITY = "quantity";

    private static String CLIENT_MAC_ADDRESS = "client.macaddress";
    private static String CLIENT_DEVICE_ID = "client.deviceid";

    private static String PREFERENCES_INCLUDE_PAY_MODES = "preferences.includepaymodes";
    private static String PREFERENCES_EXCLUDE_PAY_MODES = "preferences.excludepaymodes";
    private static String PREFERENCES_DEFAULT_PAY_MODES = "preferences.defaultpaymodes";

    private static String LOCALNOTE = "localnote";
    private static String LOCALNOTE_SNO = "sno";
    private static String LOCALNOTE_DESCRIPTION = "description";
    private static String LOCALNOTE_PAYMENT_OPTION = "paymentoption";

    private HashMap<String, String> paymentMap;

    public HashMap<String, String> getPaymentMap() {
        return paymentMap;
    }

    private HashMap<String, String> readConfig(HashMap<String, String> paymentMap) {
        JMPaymentConfig paymentConfig = JMPaymentConfig.getInstance();
        paymentMap.put(TRANSACTION_TXNTYPE, paymentConfig.getTransType());
        paymentMap.put(VERSION, paymentConfig.getVersion().getName());
        paymentMap.put(CLIENT_ID, paymentConfig.getClientId());
        paymentMap.put(MERCHANT_ID, paymentConfig.getMerchantId());
        paymentMap.put(RETURN_URL, paymentConfig.getReturnUrl());
        paymentMap.put(TOKEN, "");
        paymentMap.put(CHANNEL, paymentConfig.getChannel());
        paymentMap.put(TRANSACTION_CURRENCY, paymentConfig.getCurrency().name());
        return paymentMap;
    }

    /**
     * Make JioMoney Payment using this this Constructor
     */
    public JMPayment(@NonNull String externalRefNo, @NonNull String timestamp, float amount,
                     @NonNull String mobileNumber, @NonNull String checkSumHash) {
        paymentMap = new HashMap<>();
        paymentMap = readConfig(paymentMap);
        paymentMap.put(TRANSACTION_EXTREF, externalRefNo);
        paymentMap.put(TRANSACTION_TIMESTAMP, timestamp);
        paymentMap.put(TRANSACTION_AMOUNT, CommonUtility.round(amount));
        paymentMap.put(SUBSCRIBER_MOBILENUMBER, mobileNumber);
        paymentMap.put(TRANSACTION_TYPE, PaymentMode.JIOMONEY_WALLET.name());
        paymentMap.put(CHECKSUM, checkSumHash);
    }

    public void setCartItems(@NonNull ArrayList<JMCartItem> JMCartItems) {
        paymentMap = addCartItems(paymentMap, JMCartItems);
    }


    public void setClientMacAddress(@NonNull String clientMacAddress) {
        paymentMap.put(CLIENT_MAC_ADDRESS, clientMacAddress);
    }

    public void setClientDeviceId(@NonNull String clientDeviceId) {
        paymentMap.put(CLIENT_DEVICE_ID, clientDeviceId);
    }

    public void setPreferencesIncludePayModes(@NonNull String preferencesIncludePayModes) {
        paymentMap.put(PREFERENCES_INCLUDE_PAY_MODES, preferencesIncludePayModes);
    }

    public void setPreferencesExcludePayModes(@NonNull String preferencesExcludePayModes) {
        paymentMap.put(PREFERENCES_EXCLUDE_PAY_MODES, preferencesExcludePayModes);
    }

    public void setPreferencesDefaultPayModes(@NonNull String preferencesDefaultPayModes) {
        paymentMap.put(PREFERENCES_DEFAULT_PAY_MODES, preferencesDefaultPayModes);
    }

    private void setLocalNotes(@NonNull ArrayList<JMLocalNote> JMLocalNotes) {
        paymentMap = addLocalNotes(paymentMap, JMLocalNotes);
    }

    public void setCardType(PaymentMode paymentMode) {
        paymentMap.put(NETBANKING_CARDTYPE, paymentMode.name());
    }

    public void setSubMerchantId(String subMerchantId) {
        paymentMap.put(SUB_MERCHANT_ID, subMerchantId);
    }

    public void setSubTerminalId(String subTerminalId) {
        paymentMap.put(SUB_TERMINAL_ID, subTerminalId);
    }

    public void setMerchantId(String merchantName) {
        paymentMap.put(MERCHANT_NAME, merchantName);
    }

    public void setBankId(String bankId) {
        paymentMap.put(NETBANKING_BANKID, bankId);
    }

    public void setNetbankingNickname(@NonNull String nickName) {
        paymentMap.put(NETBANKING_NICKNAME, nickName);
    }

    public void setProductDescription(@NonNull String productDescription) {
        paymentMap.put(PRODUCT_DESCRIPTION, productDescription);
    }
    public void setUdf1(@NonNull String udf1) {
        paymentMap.put(UDF1, udf1);
    }

    public void setUdf2(@NonNull String udf2) {
        paymentMap.put(UDF2, udf2);
    }

    public void setUdf3(@NonNull String udf3) {
        paymentMap.put(UDF3, udf3);
    }

    public void setUdf4(@NonNull String udf4) {
        paymentMap.put(UDF4, udf4);
    }

    public void setUdf5(@NonNull String udf5) {
        paymentMap.put(UDF5, udf5);
    }

    public void setSubscriber(@NonNull JMSubscriber JMSubscriber) {
        paymentMap = addSubscriber(paymentMap, JMSubscriber);
    }

    public void setCardinfoPan(String cardNumber) {
        paymentMap.put(CARDINFO_PAN, cardNumber);
    }

    public void setCardinfoExpmon(String cardExpMonth) {
        paymentMap.put(CARDINFO_EXPMON, cardExpMonth);
    }

    public void setCardinfoExpyr(String cardExpYear) {
        paymentMap.put(CARDINFO_EXPYR, cardExpYear);
    }

    public void setCardinfoCvv(String cardCvv) {
        paymentMap.put(CARDINFO_CVV, cardCvv);
    }

    public void setCardinfoNickname(String nickName) {
        paymentMap.put(CARDINFO_NICKNAME, nickName);
    }

    public void setCardInfoBankId(String bankId) {
        paymentMap.put(CARDINFO_BANKID, bankId);
    }

    private HashMap<String, String> addCartItems(@NonNull HashMap<String, String> paymentMap, @NonNull ArrayList<JMCartItem> JMCartItems) {
        for (int i = 0; i < JMCartItems.size(); i++) {
            String SNO = "sno";
            paymentMap.put("items[" + i + "]." + SNO, JMCartItems.get(i).getSno());
            paymentMap.put("items[" + i + "]." + DESCRIPTION, JMCartItems.get(i).getDescription());
            paymentMap.put("items[" + i + "]." + COST, JMCartItems.get(i).getCost());
            paymentMap.put("items[" + i + "]." + QUANTITY, JMCartItems.get(i).getQuantity());
        }
        return paymentMap;
    }

    private HashMap<String, String> addSubscriber(@NonNull HashMap<String, String> paymentMap, @NonNull JMSubscriber subcriber) {
        paymentMap.put(SUBSCRIBER_CUSTOMER_ID, subcriber.getCustomerId());
        paymentMap.put(SUBSCRIBER_MOBILENUMBER, subcriber.getMobileNumber());
        paymentMap.put(SUBSCRIBER_CUSTOMER_NAME, subcriber.getCustomerName());
        paymentMap.put(SUBSCRIBER_EMAIL, subcriber.getEmail());
        paymentMap.put(SUBSCRIBER_ADD1, subcriber.getAdd1());
        paymentMap.put(SUBSCRIBER_ADD2, subcriber.getAdd2());
        paymentMap.put(SUBSCRIBER_CITY, subcriber.getCity());
        paymentMap.put(SUBSCRIBER_STATE, subcriber.getState());
        paymentMap.put(SUBSCRIBER_ZIPCODE, subcriber.getZipcode());
        return paymentMap;
    }

    private HashMap<String, String> addLocalNotes(@NonNull HashMap<String, String> paymentMap, @NonNull ArrayList<JMLocalNote> JMLocalNotes) {
        for (int i = 0; i < JMLocalNotes.size(); i++) {
            paymentMap.put(LOCALNOTE + "[" + i + "]." + LOCALNOTE_SNO, JMLocalNotes.get(i).getSno());
            paymentMap.put(LOCALNOTE + "[" + i + "]." + LOCALNOTE_DESCRIPTION, JMLocalNotes.get(i).getDescrition());
            paymentMap.put(LOCALNOTE + "[" + i + "]." + LOCALNOTE_PAYMENT_OPTION, JMLocalNotes.get(i).getPaymentOption());
        }
        return paymentMap;
    }

    public String getProductDescription() {
        return paymentMap.get(PRODUCT_DESCRIPTION);
    }

    public String getTranType() {
        return paymentMap.get(TRANSACTION_TXNTYPE);
    }

    public String getVersion() {
        return paymentMap.get(VERSION);
    }

    public String getClientId() {
        return paymentMap.get(CLIENT_ID);
    }

    public String getMerchantId() {
        return paymentMap.get(MERCHANT_ID);
    }

    public String getChecksum() {
        return paymentMap.get(CHECKSUM);
    }

    public String getToken() {
        return paymentMap.get(TOKEN);
    }

    public String getChannel() {
        return paymentMap.get(CHANNEL);
    }

    public String getExtRef() {
        return paymentMap.get(TRANSACTION_EXTREF);
    }

    public String getTimestamp() {
        return paymentMap.get(TRANSACTION_TIMESTAMP);
    }

    public String getCurrency() {
        return paymentMap.get(TRANSACTION_CURRENCY);
    }

    public String getAmount() {
        return paymentMap.get(TRANSACTION_AMOUNT);
    }

    public String getReturnUrl() {
        return paymentMap.get(RETURN_URL);
    }

    public String getMobile() {
        return paymentMap.get(SUBSCRIBER_MOBILENUMBER);
    }

    public enum PaymentMode {
        CREDITCARD,
        DEBITCARD,
        NETBANKING,
        JIOMONEY_WALLET
    }
}
