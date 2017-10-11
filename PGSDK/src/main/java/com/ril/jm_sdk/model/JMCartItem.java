package com.ril.jm_sdk.model;

/**
 * Created by RIL on 22-08-2017.
 */
public class JMCartItem {
    private String cost;
    private String quantity;
    private String sno;
    private String description;

    public JMCartItem(String cost, String quantity, String sno, String description) {
        this.cost = cost;
        this.quantity = quantity;
        this.sno = sno;
        this.description = description;
    }

    public String getCost() {

        return cost;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSno() {
        return sno;
    }

    public String getDescription() {
        return description;
    }
}
