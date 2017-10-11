package com.ril.jm_sdk.constant;


import com.ril.jm_sdk.config.JMEnvironment;
import com.ril.jm_sdk.util.Log;

/**
 * Created by COMPAQ on 07-07-2016.
 */
public class UrlConstants {

    private static final String TAG = UrlConstants.class.getSimpleName();
    public static JMEnvironment jmEnvironment = JMEnvironment.PRE_PROD;

    private static final String BASE_URL_ST = "https://stpay.rpay.co.in/";
    private static final String BASE_URL_PROD = "https://pp2pay.jiomoney.com/";
    private static final String BASE_URL_PRE_PROD = "https://testpg.rpay.co.in/";
    private static final String BASE_URL_CIV = "https://testpg.rpay.co.in/";

    private static String BASE_URL = BASE_URL_ST;

    private static final String JIOMONEY_PAYMENT_ENDPOINT = "reliance-webpay/v1.0/jiopayments";


    public static void setJmEnvironment(JMEnvironment jmEnvironment){
        UrlConstants.jmEnvironment = jmEnvironment;
        Log.d(TAG, "Enviroment :" + UrlConstants.jmEnvironment);
        setBaseURI();
    }


    public static void setBaseURI(){

        if(jmEnvironment.equals(JMEnvironment.CIV)){
            BASE_URL = BASE_URL_CIV;
        }else if(jmEnvironment.equals(JMEnvironment.ST)){
            BASE_URL = BASE_URL_ST;
        }else if(jmEnvironment.equals(JMEnvironment.PRE_PROD)){
            BASE_URL = BASE_URL_PRE_PROD;
        }else if(jmEnvironment.equals(JMEnvironment.PROD)){
            BASE_URL = BASE_URL_PROD;
        }
        Log.d(TAG, "BASE_URL :"+BASE_URL);
    }


    public static String getJioMoneyPaymentAPI(){
        return BASE_URL + JIOMONEY_PAYMENT_ENDPOINT;
    }

}
