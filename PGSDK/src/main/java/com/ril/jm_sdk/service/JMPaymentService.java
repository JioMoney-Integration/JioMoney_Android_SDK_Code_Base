package com.ril.jm_sdk.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ril.jm_sdk.activity.JMWebActivity;
import com.ril.jm_sdk.config.JMPaymentConfig;
import com.ril.jm_sdk.constant.Error;
import com.ril.jm_sdk.constant.UrlConstants;
import com.ril.jm_sdk.model.FormData;
import com.ril.jm_sdk.model.JMPayment;
import com.ril.jm_sdk.util.ConnectionUtility;
import com.ril.jm_sdk.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * JMPaymentService is service layer includes business logic to manage
 * payment flow
 * Created by RIL.
 */
public class JMPaymentService {

    private static final String TAG = JMPaymentService.class.getSimpleName();

    public JMPaymentTransactionCallback JMPaymentTransactionCallback;
    private Context context;
    private com.ril.jm_sdk.service.SMSNotifier SMSNotifier;

    private static JMPaymentService service;

    public JMPaymentService() {

    }

    /**
     * Singleton instance to manage one instance throughout the application
     *
     * @return reference of type JMPaymentService
     */
    public static synchronized JMPaymentService getInstance() {
        if (null == service) {
            service = new JMPaymentService();
        }
        return service;
    }

    /**
     * makePayment method to initiate JM wallet payment transaction
     * Asynchronus method to make payment transaction thread safe
     *
     * @param context                      android context
     * @param jioMoneyPayment              of type JMPayment contains parameters required for JioMoney wallet payment transaction
     * @param JMPaymentTransactionCallback of type JMPaymentTransactionCallback to return result of transaction through callback method
     */
    public synchronized void makePayment(Context context, JMPayment jioMoneyPayment, JMPaymentTransactionCallback JMPaymentTransactionCallback) {
        if (null != JMPaymentConfig.getInstance() && JMPaymentConfig.isConfig()) {
            this.context = context;
            if (ConnectionUtility.isConnected(context)) {
                this.JMPaymentTransactionCallback = JMPaymentTransactionCallback;
                HashMap<String, String> paymentModel = jioMoneyPayment.getPaymentMap();
                String requestUrl = UrlConstants.getJioMoneyPaymentAPI();
                if (null == requestUrl) {
                    JMPaymentTransactionCallback.onError(Error.BAD_REQUEST.getErrorCode(), Error.BAD_REQUEST.getErrorMessage());
                    return;
                } else {
                    displayWeb(paymentModel, requestUrl);
                }
                Log.d(TAG, "Request = " + paymentModel);
            } else {
                JMPaymentTransactionCallback.onError(Error.NETWORK_ERROR.getErrorCode(), Error.NETWORK_ERROR.getErrorMessage());
            }
        } else {
            JMPaymentTransactionCallback.onError(Error.CONFIG_ERROR.getErrorCode(), Error.CONFIG_ERROR.getErrorMessage());
        }
    }


    /**
     * displayWeb method navigates application to JioMoney Wallet webview screen
     *
     * @param paymentModel HashMap includes parameters passed by application
     * @param requestUrl   jmEnvironment specific JioMoney payment URL
     */
    private synchronized void displayWeb(Map<String, String> paymentModel, String requestUrl) {
        Log.d(TAG, "requestUrl = " + requestUrl);
        Intent intent = new Intent(context, JMWebActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable(JMWebActivity.REQUEST_PARAMS, (HashMap)paymentModel);
        bundle.putParcelable(JMWebActivity.REQUEST_PARAMS, new FormData(paymentModel));
        bundle.putString(JMWebActivity.REQUEST_URL, requestUrl);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * setSmsNotifier setter method manages notification came from SMS Receiver
     *
     * @param notifier contains method to notify with smsdata
     */
    public void setSmsNotifier(com.ril.jm_sdk.service.SMSNotifier notifier) {
        SMSNotifier = notifier;
    }


    /**
     * onSmsReceiver notifies SMS data
     *
     * @param smsData Map includes SMS data with sender and message content
     */
    public void onSmsReceiver(Map<String, String> smsData) {
        if (null != SMSNotifier) {
            SMSNotifier.onReceiveSMS(smsData);
        } else {
            Log.w(TAG, "SMSNotifier: null, can not able to read SMS!!");
        }

    }


}
