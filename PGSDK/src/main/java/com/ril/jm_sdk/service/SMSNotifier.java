package com.ril.jm_sdk.service;

import java.util.Map;

/**
 * Created by RIL.
 * Notify when receives SMS
 */
public interface SMSNotifier {
    void onReceiveSMS(Map<String, String> smsData);
}
