package com.ril.jm_sdk.webcomponent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ril.jm_sdk.config.JMPaymentConfig;
import com.ril.jm_sdk.constant.Error;
import com.ril.jm_sdk.model.JMPaymentResponse;
import com.ril.jm_sdk.service.SMSNotifier;
import com.ril.jm_sdk.service.SMSReceiver;
import com.ril.jm_sdk.util.CommonUtility;
import com.ril.jm_sdk.util.Log;

import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Generic WebView
 * Customized to manage payment related posting
 */
public class GenericWebView extends WebView {
    private static final String TAG = "GenericWebView";
    private static final String JS_CLASS_MAPPER = "MobileClient";
    Context mContext;
    WebViewListener webViewListener;
    public static final int WEBVIEW_TIMEOUT = 1;
    public static final int OTP_SUBMIT_TIMEOUT = 15;
    private boolean otpSubmitFlag;
    private com.ril.jm_sdk.service.JMPaymentService JMPaymentService;
    private Thread otpThread;


    public GenericWebView(Context context) {
        super(context);
        init(context);
    }

    public GenericWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public GenericWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }


    /**
     * initialise all the web view components
     * initialise setting
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        JMPaymentService = com.ril.jm_sdk.service.JMPaymentService.getInstance();
        if (mContext instanceof WebViewListener) {
            webViewListener = (WebViewListener) mContext;
        }
        WebSettings webSettings = getSettings();
        webSettings.setCacheMode(2);
        webSettings.setDomStorageEnabled(true);
        clearHistory();
        clearCache(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(false);
        super.setWebViewClient(new PaymentWebViewClient());
        super.setWebChromeClient(new WebChromeClient());
        super.addJavascriptInterface(new PaymentJavaScriptInterface(), JS_CLASS_MAPPER);

        Log.d(TAG, "JMPaymentService :" + com.ril.jm_sdk.service.JMPaymentService.getInstance());
    }

    /**
     * To stop the thread which is started for otp submittion
     */
    public void stopTimer() {
        if (null != otpThread) {
            Log.d(TAG, "stopTimer");
            otpThread.interrupt();
        }
    }

    /**
     * Custom webview client
     */
    private class PaymentWebViewClient extends WebViewClient {
        boolean timeout;

        /**
         * Constructor
         * Receives OTP
         */
        private PaymentWebViewClient() {
            timeout = true;
            if (null != JMPaymentService) {
                JMPaymentService.setSmsNotifier(new SMSNotifier() {
                    @Override
                    public void onReceiveSMS(Map<String, String> smsData) {
                        Log.d(TAG, "smsData :" + smsData);
                        injectSetOtp(CommonUtility.getNumbersFromString(smsData.get(SMSReceiver.SMS_BODY)));
                        if (JMPaymentConfig.getInstance().isAutoSubmitOTP()) {
                            otpSubmitTimer();
                        } else {
                            Log.e(TAG, "isAutoSubmitOTP enabled :" + JMPaymentConfig.getInstance().isAutoSubmitOTP());
                        }
                    }
                });
            } else {

                Log.e(TAG, "JMPaymentService :is not initialized, can not able to read SMS!!");
            }
        }

        /**
         * Automatic submittion after fetching OTP
         */
        public void otpSubmitTimer() {
            webViewListener.otpProgressStart(1, OTP_SUBMIT_TIMEOUT);
            otpThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * OTP_SUBMIT_TIMEOUT);
                        injectSubmit();
                    } catch (InterruptedException e) {
                        Log.d(TAG, "InterruptedException");
                        Log.d(TAG, "Timeout exception :" + e);
                    }
                }
            });
            otpThread.start();
        }


        /**
         * webview client overriden method
         *
         * @param view instance of webview
         * @param url  url which
         * @return
         */
        @Override
        public synchronized boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "RedirectUrl :" + url);
            return false;
        }

        @Override
        public synchronized void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d(TAG, "onLoadResource inUrl :" + url);
            /*on change url otp footer will hide*/
            webViewListener.otpProgressStop(0);
        }


        /**
         * Default webview client method
         * Timeout managed when page takes too long to load
         *
         * @param inView
         * @param inUrl
         * @param inFavicon
         */
        @Override
        public synchronized void onPageStarted(final WebView inView, String inUrl, Bitmap inFavicon) {
            Log.d(TAG, "onPageStarted inUrl :" + inUrl);
            webViewListener.startProgress();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 60 * WEBVIEW_TIMEOUT);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "Timeout exception :" + e);
                    }
                    if (timeout) {
                        webViewListener.stopProgerss();
                        com.ril.jm_sdk.service.JMPaymentService.getInstance().JMPaymentTransactionCallback.onError(Error.PAYMENT_TIMEOUT.getErrorCode(), Error.PAYMENT_TIMEOUT.getErrorMessage());
                        finishWebActivity((Activity) mContext);
                    }
                }
            }).start();
        }

        /**
         * Default webviewclient method
         * reset values when page loads
         *
         * @param view
         * @param url
         */
        @Override
        public synchronized void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webViewListener.stopProgerss();
            Log.d(TAG, "onPageFinished inUrl :" + url);
            timeout = false;

        }

        @Override
        public synchronized void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webViewListener.stopProgerss();
            Log.d(TAG, "onReceivedError inUrl :");


        }


        @Override
        public synchronized void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            webViewListener.stopProgerss();
            Log.d(TAG, "onReceivedError inUrl :" + errorResponse.toString());
        }


        public synchronized void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            webViewListener.stopProgerss();
            Log.d(TAG, "onReceivedSslError inUrl :" + error.toString());

        }
    }


    /**
     * Javascript interface
     * Callback method from Javascript
     * Manages response and sends back to payment service
     */
    private class PaymentJavaScriptInterface {
        private PaymentJavaScriptInterface() {
            Log.d(TAG, "PaymentJavaScriptInterface constructor()");
        }

        /**
         * Callback method which is mapped in the return URL's javascript
         * Handles response and return to Payment service using callback method
         *
         * @param response
         */
        @JavascriptInterface
        public synchronized void onResponse(String response) {
            finishWebActivity((Activity) mContext);
            try {
                Log.d(TAG, "inResponse : " + response);
                JMPaymentResponse JMPaymentResponse = parseResponse(response);
                Log.d(TAG, "JMPaymentResponse : " + JMPaymentResponse);
                if (null != JMPaymentResponse) {
                    com.ril.jm_sdk.service.JMPaymentService.getInstance().JMPaymentTransactionCallback.onResponse(parseResponse(response), response);
                } else {
                    com.ril.jm_sdk.service.JMPaymentService.getInstance().JMPaymentTransactionCallback.onError(Error.INVALID_RESPONSE.getErrorCode(), Error.INVALID_RESPONSE.getErrorMessage());
                }
            } catch (Exception e) {
                Log.d(TAG, "error : " + e);
                com.ril.jm_sdk.service.JMPaymentService.getInstance().JMPaymentTransactionCallback.onError(Error.INVALID_RESPONSE.getErrorCode(), Error.INVALID_RESPONSE.getErrorMessage());
            }
        }


        /**
         * Parses response that comes from Javascript
         *
         * @param response
         * @return
         */
        private JMPaymentResponse parseResponse(String response) {
            try {
                StringTokenizer items = new StringTokenizer(response, "|");
                Log.d(TAG, "Splited String: " + items.countTokens());
                JMPaymentResponse JMPaymentResponse = null;
                switch (items.countTokens()) {
                    case 13:
                        JMPaymentResponse = getResponseforError(items);
                        break;
                    case 14:
                        JMPaymentResponse = getResponse(items);
                        break;
                    case 15:
                        JMPaymentResponse = getResponseforV2(items);
                        break;
                    case 18:
                        JMPaymentResponse = getErrorV2WithUDF(items);
                        break;
                    case 19:
                        JMPaymentResponse = getResponseWithUDF(items);
                        break;
                    case 20:
                        JMPaymentResponse = getResponseV2WithUDF(items);
                        break;
                    case 21:
                        JMPaymentResponse = getResponseV2WithPDandUDF(items);
                        break;
                    default:

                }
                return JMPaymentResponse;
            } catch (Exception e) {
                return null;
            }
        }


        /**
         * map response according to number of parameters for version 1.0
         *
         * @param items
         * @return
         */
        private JMPaymentResponse getResponse(StringTokenizer items) {
            return new JMPaymentResponse(items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken());
        }

        /**
         * map response according to number of parameters for handling error response without checksum
         *
         * @param items
         * @return
         */
        private JMPaymentResponse getResponseforError(StringTokenizer items) {
            return new JMPaymentResponse(items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken());
        }

        /**
         * map response according to number of parameters for version 2.0
         *
         * @param items
         * @return
         */
        private JMPaymentResponse getResponseforV2(StringTokenizer items) {
            return new JMPaymentResponse(items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken());
        }

        /**
         * map response according to number of parameters with udf for version 1.0
         *
         * @param items
         * @return
         */
        private JMPaymentResponse getResponseWithUDF(StringTokenizer items) {
            return new JMPaymentResponse(items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken());
        }

        private JMPaymentResponse getErrorV2WithUDF(StringTokenizer items) {
            return new JMPaymentResponse(items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken());
        }

        /**
         * map response according to number of parameters with udf for version 1.0
         *
         * @param items
         * @return
         */
        private JMPaymentResponse getResponseV2WithUDF(StringTokenizer items) {
            return new JMPaymentResponse(items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken());
        }

        private JMPaymentResponse getResponseV2WithPDandUDF(StringTokenizer items) {
            return new JMPaymentResponse(items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken(),
                    items.nextToken());
        }


    }

    /**
     * WebView posting util
     *
     * @param url
     * @param postDataMap
     */
    public synchronized void customPostUrl(String url,
                                           Map<String, String> postDataMap) {
        Log.d(TAG, "customPostUrl");
        Collection<Map.Entry<String, String>> postData = postDataMap.entrySet();
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='paymentForm.submit()'>");
        sb.append(String.format("<form id='paymentForm' action='%s' method='%s'>",
                url, "post"));
        for (Map.Entry<String, String> item : postData) {
            String value = "";
            if (null != item.getValue()) {
                value = item.getValue();
            }
            sb.append(String.format(
                    "<input name='%s' type='hidden' value='%s' />",
                    item.getKey(), value));
        }
        sb.append("</form></body></html>");
        Log.d(TAG, "customPostUrl called");
        loadData(sb.toString(), "text/html", "utf-8");
    }

    /**
     * for injecting Javascript for setting OTP value
     *
     * @param otp
     */
    private void injectSetOtp(String otp) {
        Log.d(TAG, "otp: " + otp);
        this.loadUrl("javascript: " +
                "var otpTxt = document.getElementById('otpns');" +
                "console.log('otps'+otpTxt);" +
                "otpTxt.value = '" + otp + "';" +
                "$('#jmsignup-btn').removeClass('disableClick')");
    }

    /**
     * for injecting Javascript to submit
     *
     * @param
     */
    private void injectSubmit() {
        this.post(new Runnable() {
            @Override
            public void run() {
                loadUrl("javascript: " +
                        "$('#jmsignup-btn').click();");
            }
        });
    }


    private synchronized void finishWebActivity(Activity activity) {
        activity.finish();
    }

    public interface WebViewListener {
        void startProgress();

        void stopProgerss();

        void otpProgressStart(int count, int maxCount);

        void otpProgressStop(int count);
    }


}
