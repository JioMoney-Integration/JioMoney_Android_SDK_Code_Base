package com.ril.jm_sdk.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ril.jm_sdk.R;
import com.ril.jm_sdk.constant.Error;
import com.ril.jm_sdk.model.FormData;
import com.ril.jm_sdk.service.JMPaymentService;
import com.ril.jm_sdk.service.SMSReceiver;
import com.ril.jm_sdk.util.Log;
import com.ril.jm_sdk.webcomponent.GenericWebView;

import java.util.Map;

/**
 * Created by RIL .
 * Thos activity inflatesJMPaymentWebView into the application and getes
 * request params and send it to webview
 */
public class JMWebActivity extends Activity implements GenericWebView.WebViewListener {
    private static final String TAG = JMWebActivity.class.getSimpleName();
    public static final String REQUEST_PARAMS = "request_params";
    public static final String REQUEST_URL = "request_url";
    private GenericWebView webView;
    private ProgressBar progressBar, webFooterProgress;
    //    private Button webFooterButton;
    private TextView webFotterText;
    private SMSReceiver smsReceiver;
    private int timerCount;
    private CountDownTimer countDownTimer;

    /**
     * Default activity lifecycle method on activity start
     * Method is used to initialize all the components
     * Permission access
     * Register SMSReceiver
     * initialize GenericWebView
     *
     * @param savedInstanceState default params
     */
    @Override
    public synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_rpg_skip_header);
        /*access permission for RECEIVE_SMS*/
        accessPermisssion(Manifest.permission.RECEIVE_SMS);

        /*Register SMSReceiver*/
        IntentFilter smsReceiverFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        smsReceiverFilter.setPriority(999);
        smsReceiver = new SMSReceiver();
        registerReceiver(smsReceiver, smsReceiverFilter);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        webFooterProgress = (ProgressBar) findViewById(R.id.web_view_progress);
//        webFooterButton = (Button) findViewById(R.id.web_btn);
        webFotterText = (TextView) findViewById(R.id.web_txt);

        Bundle bundle = getIntent().getExtras();
        FormData formData = bundle.getParcelable(REQUEST_PARAMS);
        Map<String, String> requestMap;
        if (null != formData) {
            requestMap = formData.getFormData();
            Log.e(TAG, "requestMap:" + requestMap.toString());
        } else {
            Log.e(TAG, "requestMap: is null, unable create form data, please check the implementation !!!");
            return;
        }

        String requestUrl = bundle.getString(REQUEST_URL);

       /* webFooterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick");
                if (null != countDownTimer) {
                    Log.d(TAG, "onclick");
                    countDownTimer.onFinish();
                    countDownTimer.cancel();
                    webView.stopTimer();
                }

            }
        });*/

        Log.d(TAG, "requestUrl: " + requestUrl);
        webView = (GenericWebView) findViewById(R.id.web_view);
        webView.customPostUrl(requestUrl, requestMap);
    }


    /**
     * Implemented method to handle progress bar(loader)
     */
    @Override
    public synchronized void startProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Implemented method to handle progress bar(loader)
     */
    @Override
    public synchronized void stopProgerss() {
        progressBar.setVisibility(View.GONE);
    }


    /**
     * Implemented method to manage business logic for otp progress bar
     *
     * @param count    starting value
     * @param maxCount max value till the progress works
     */
    @Override
    public void otpProgressStart(int count, final int maxCount) {
        Log.d(TAG, "otpProgressStart :");
        timerCount = count;
        visibleFooter();
        webFooterProgress.setMax(maxCount);
        countDownTimer = new CountDownTimer(maxCount * 1000, 1000 * count) {
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick :" + (int) millisUntilFinished / 100);
                showTimerCount(++timerCount);
            }

            public void onFinish() {
                hideFooter();
                Log.d(TAG, "finished");
            }
        };
        countDownTimer.start();
    }


    /**
     * Shows the footer for progress bar
     */
    private void visibleFooter() {
        webFooterProgress.setVisibility(View.VISIBLE);
        webFotterText.setVisibility(View.VISIBLE);
//        webFooterButton.setVisibility(View.VISIBLE);
    }

    /**
     * Shows the footer for progress bar
     */
    private void hideFooter() {
        webFooterProgress.setVisibility(View.GONE);
        webFotterText.setVisibility(View.GONE);
//        webFooterButton.setVisibility(View.GONE);
    }


    /**
     * Method can be used to set progressbar value dynamically
     *
     * @param count value of current progress
     */
    private void showTimerCount(final int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webFooterProgress.setProgress(count);
            }
        });
    }

    @Override
    public void otpProgressStop(int count) {
        hideFooter();
    }


    /**
     * Default activity lifecycle callback method before activity removed from activity stack
     * All the garbage collection of resources are done here.
     */
    @Override
    protected synchronized void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
        webView.destroy();
        webView = null;
    }


    /**
     * Default back button implementation on click of toolbar back button
     */
    @Override
    public synchronized void onBackPressed() {
        cancelAlert();
    }

    /**
     * Method show the alert dialog box with reason and a button for user action
     * After positive button selection payment activity finishes and payment service callback onError called
     * with specific error message
     */
    private void cancelAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.back_title);
        alert.setMessage(R.string.back_message);
        alert.setPositiveButton(R.string.back_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                JMPaymentService.getInstance().JMPaymentTransactionCallback.onError(Error.TRANSACTION_CANCELLED.getErrorCode(), "Transaction cancelled !!!");
                JMWebActivity.this.finish();
            }
        });
        alert.setNegativeButton(R.string.back_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alert.show();
    }


    /**
     * Access permssion for API level >= 23(Marshmallow), at the time of using feature
     *
     * @param permission string can be any valid permission string
     */
    private void accessPermisssion(String permission) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
//                return ApplicationUtil.getDeviceIMEI((Activity) getContext());
                Log.d(TAG, "SMS Permission already granted");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        } else {
            Log.d(TAG, "else SMS Permission already granted");
        }
    }


    /**
     * Activity Callback method after user responds on permission dialog
     *
     * @param requestCode  passed while requesting permission
     * @param permissions  array which is requested for
     * @param grantResults array with user approved permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "permission granted by user");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {
                    Log.d(TAG, "permission denied by user");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }

        }
    }


}
