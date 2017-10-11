package com.ril.jm_sdk.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ril.jm_sdk.constant.UrlConstants;
import com.ril.jm_sdk.util.Log;

/**
 * Created by COMPAQ on 11-07-2016.
 * SingleTon Class used to manage SDK configuration
 */
public class JMPaymentConfig {
    private static final String TAG = JMPaymentConfig.class.getSimpleName();

    private static JMPaymentConfig paymentConfig;
    private static Boolean isConfig = false;

    boolean enableLog;
    JMEnvironment JMEnvironment = com.ril.jm_sdk.config.JMEnvironment.PRE_PROD;

    //Fields are using to make payment
    private String clientId;

    private String merchantId;

    private String returnUrl;

    private JMVersion version = JMVersion.VERSION2_0;

    private JMCurrency currency = JMCurrency.INR;



    private String transType;

    private String channel;

    private boolean autoSubmitOTP;



    private JMPaymentConfig() {
    }

    /**
     * Method to create single instance of configuration
     * @return paymentConfig instance
     */
    public static synchronized JMPaymentConfig getInstance() {
        if (null == paymentConfig) {
            paymentConfig = new JMPaymentConfig();
            paymentConfig.setChannel(Channel.MOBILE.name()).setTransType(TransactionType.PURCHASE.name());
        }
        return paymentConfig;
    }

    /**
     * To switch logs
     * @param enableLog enables log
     * @return JMPaymentConfig
     */
    public JMPaymentConfig setEnableLog(boolean enableLog) {
        this.enableLog = enableLog;
        Log.ENABLE_DEBUG_LOG = this.enableLog;
        System.out.println("Log.ENABLE_DEBUG_LOG :" + Log.ENABLE_DEBUG_LOG);
        return this;
    }

    /**
     * To set jmEnvironment
     * @param JMEnvironment Pass the value of JM ENviroment to set value
     * @return JMPaymentConfig
     */
    public synchronized JMPaymentConfig setJMEnvironment(@NonNull JMEnvironment JMEnvironment) {
        this.JMEnvironment = JMEnvironment;
        Log.d(TAG, "jmEnvironment :" + this.JMEnvironment);
        UrlConstants.setJmEnvironment(JMEnvironment);
        return paymentConfig;
    }


    public String getClientId() {
        return clientId;
    }

    /**
     *
     * @param clientId Client Id provided with merchant details
     * @return JMPaymentConfig
     */
    public JMPaymentConfig setClientId(@NonNull String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public JMPaymentConfig setMerchantId(@NonNull String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public JMPaymentConfig setReturnUrl(@NonNull String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public JMVersion getVersion() {
        return version;
    }

    public JMPaymentConfig setVersion(@NonNull JMVersion version) {
        this.version = version;
        return this;
    }

    public JMCurrency getCurrency() {
        return currency;
    }

    public JMPaymentConfig setCurrency(@NonNull JMCurrency currency) {
        this.currency = currency;
        return this;
    }

    public String getTransType() {
        return transType;
    }

    private JMPaymentConfig setTransType(String transType) {
        this.transType = transType;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    private JMPaymentConfig setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    /**
     * @param autoSubmitOTP type boolean
     * @return JMPaymentConfig
     */
    public JMPaymentConfig setAutoSubmitOTP(boolean autoSubmitOTP){
        this.autoSubmitOTP = autoSubmitOTP;
        return this;
    }
    public boolean isAutoSubmitOTP() {
        return autoSubmitOTP;
    }

    public synchronized JMPaymentConfig init(Context context) {
        if (null != context) {
            setStringConfig();
        } else {
            Log.d(TAG, "Failed to initialize");
        }
        return paymentConfig;
    }

     /**
     * Check validation before initiating transaction
     */
    private void setStringConfig() {
        if ((null != getTransType() && getTransType().length() > 0) && (null != getChannel() && getChannel().length() > 0) &&
                (null != getClientId() && getClientId().length() > 0) && (null != getMerchantId() && getMerchantId().length() > 0)
                  && (null != getReturnUrl() && getReturnUrl().length() > 0)) {
            isConfig = true;
        }
    }

    /**
     * Returning value for isConfig
     * @return returns value of isConfig
     */
    public static Boolean isConfig() {
        return isConfig;
    }

    enum TransactionType{
        PURCHASE
    }

    enum Channel{
        MOBILE,
        WEB
    }

    /**
     * Enuration to maintain version for API
     */
    public enum JMVersion{
        VERSION1_0("1.0"),
        VERSION2_0("2.0"),
        VERSION3_0("3.0");
        String version;
        JMVersion(String verson){
            this.version = verson;
        }

        public String getName(){
            return version;
        }
    }


    /**
     * Supported versions by JM Wallet
     */
    public enum JMCurrency{
        INR
    }

}
