package com.ril.jm_sdk.constant;

/**
 * Created by RIL
 * Enumeration type for Error messages
 */
public enum Error {
    PAYMENT_TIMEOUT(99000, "Transaction timed out !!!"),
    BAD_REQUEST(90400, "The request could not be understood !!!"),
    INVALID_RESPONSE(12152, "Unable to parse response !!!"),
    TRANSACTION_CANCELLED(999, "Transaction cancelled !!!"),
    CONFIG_ERROR(998, "Configuration is not proper !!!"),
    NETWORK_ERROR(999, "Please check your network connection !!!"),
    NOT_FOUND(404, "Request not found !!!"),
    METHOD_NOT_ALLOWED(405, "Method not allowed !!!"),
    NETWORK_TIMEOUT(408, "Connection timed out !!!"),
    CHECKSUM_ERROR(997, "Checksum generation error from server");

    private int errorCode;
    private String errorMessage;

    /**
     * Parameterized enum constructor
     * @param errorCode
     * @param errorMessage
     */
    Error(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * returns error code for corresponding error
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * returns error message for corresponding error
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * To to convert enum into string
     * @return
     */
    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
