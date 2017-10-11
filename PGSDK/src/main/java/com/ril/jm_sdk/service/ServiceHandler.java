package com.ril.jm_sdk.service;

/**
 * Created by RIL
 * Callback methods for the services
 */
public interface ServiceHandler {
    void onStart();
    void onStop();
    void onSuccess(int responseCode, String responseMessage, String response);
    void onFailure(int responseCode, String responseMessage, String response);
    void onNetworkConnectionError(int responseCode, String responseMessage, String response);
}
