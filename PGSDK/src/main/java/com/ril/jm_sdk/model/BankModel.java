package com.ril.jm_sdk.model;

/**
 * Created by RIL on 22-08-2017.
 */
public class BankModel {

    private String bankId;
    private String bankName;

    public BankModel(String bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public String getBankName() {
        return bankName;
    }

    @Override
    public String toString() {
        return "BankModel{" +
                "CARDINFO_BANKID='" + bankId + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
