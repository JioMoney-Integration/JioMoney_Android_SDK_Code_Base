package com.ril.jm_sdk.model;

/**
 * Created By RIL.
 * JMSubscriber Model for JM Model
 */
public class JMSubscriber {

    private String customerId;
    private String mobileNumber;
    private String customerName;
    private String email;
    private String add1;
    private String add2;
    private String city;
    private String state;
    private String zipcode;

    public JMSubscriber(String customerId, String mobileNumber, String customerName, String email, String add1, String add2, String city, String state, String zipcode) {
        this.customerId = customerId;
        this.mobileNumber = mobileNumber;
        this.customerName = customerName;
        this.email = email;
        this.add1 = add1;
        this.add2 = add2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public String getAdd1() {
        return add1;
    }

    public String getAdd2() {
        return add2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }
}
