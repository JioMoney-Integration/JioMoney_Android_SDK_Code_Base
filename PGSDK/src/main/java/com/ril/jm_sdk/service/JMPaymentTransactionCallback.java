package com.ril.jm_sdk.service;

import com.ril.jm_sdk.model.JMPaymentResponse;

/**
 * Created by RIL
 */
public interface JMPaymentTransactionCallback {
    void onResponse(JMPaymentResponse JMPaymentResponse, String rawResponse);
    void onError(int errorCode, String error);
}
