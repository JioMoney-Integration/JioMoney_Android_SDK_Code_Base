package com.ril.jm_sdk.config;

public enum JMEnvironment {
    CIV,
    ST,
    PROD,
    PRE_PROD;
    public static String[] names() {
        JMEnvironment[] tpArray = values();
        String[] names = new String[tpArray.length];

        for (int i = 0; i < tpArray.length; i++) {
            names[i] = tpArray[i].name();
        }
        return names;
    }

}