package com.ril.jm_sdk.model;

/**
 * Created by RIL on 23-08-2017.
 */
public class JMLocalNote {
    private String sno;
    private String descrition;
    private String paymentOption;

    public JMLocalNote(String sno, String descrition, String paymentOption) {
        this.sno = sno;
        this.descrition = descrition;
        this.paymentOption = paymentOption;
    }

    public String getSno() {
        return sno;
    }

    public String getDescrition() {
        return descrition;
    }

    public String getPaymentOption() {
        return paymentOption;
    }
}
