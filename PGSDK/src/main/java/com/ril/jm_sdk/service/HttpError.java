package com.ril.jm_sdk.service;

/**
 * Created by RIL on 29-07-2016.
 * To create HTTP specific error codes
 */
public class HttpError {
    //Success Code
    public static final int CODE_HTTP_OK = 200;

    //Error Code
    public static final int CODE_COMMON = 1000;
    public static final int CODE_MALFORMED_URL = 1001;
    public static final int CODE_SOCKET_TIMEOUT = 1002;
    public static final int CODE_IO = 1003;
    public static final int CODE_NETWORK_ERROR = 1004;

    //Error Message
    public static  final String MSG_NETWORK_ERROR = "No Internet";
}
