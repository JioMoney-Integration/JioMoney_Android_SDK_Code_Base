package com.ril.jm_sdk.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * SMSReceiver is BroadCastReceiver receives SMS broadcast
 * <p/>
 * Created by RIL on 27-07-2017.
 */
public class SMSReceiver extends BroadcastReceiver {

    public static final String SMS_ADDRESS = "address";
    public static final String SMS_BODY = "body";
    public static final String JIO_ADDRESS = "jio";
    private static final String TAG = "SMSReceiver";

    /**
     * Generic onReceive callback method os SMSReceiver
     * Parse's specific SMS data from bundler and stores it in MAP which then shared with the payment server
     * Also notifies it to payment service wjhen receives SMS
     *
     * @param context Android standard context
     * @param intent  contains receiver data in the form of bundle
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive");
        Bundle bundle = intent.getExtras();


        Map<String, String> smsMap = new HashMap<>();
        SmsMessage[] msgs;

        String str = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (null == pdus) {
                Log.e(TAG, "Unable to read received message ");
                return;
            }
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i = 0; i < msgs.length; i++) {
                // Convert Object array

                //noinspection deprecation
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Sender's phone number
                str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                Log.d(TAG, str);
                smsMap.put(SMS_ADDRESS, msgs[i].getOriginatingAddress());
                // Fetch the text message
                str += msgs[i].getMessageBody();
                Log.d(TAG, str);
                smsMap.put(SMS_BODY, msgs[i].getMessageBody());
                // Newline <img draggable="false" class="emoji" alt="🙂" src="https://s.w.org/images/core/emoji/72x72/1f642.png">
                Log.d(TAG, "paymentService :" + JMPaymentService.getInstance());
                Log.d(TAG, "containes :" + smsMap.get(SMS_ADDRESS).contains(JIO_ADDRESS));

                if (null != JMPaymentService.getInstance() && smsMap.get(SMS_ADDRESS).toLowerCase().contains(JIO_ADDRESS)) {
                    JMPaymentService.getInstance().onSmsReceiver(smsMap);
                } else {
                    com.ril.jm_sdk.util.Log.e(TAG, "paymentService :" + JMPaymentService.getInstance() + ", can not able to read SMS!!");
                }
            }
        }

    }


}
