package com.cookandroid.dietcontrol.shop;

import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.shop.pp_PurchaseProduct;

public class addressAPI extends AppCompatActivity {

    private static final String TAG = "addressAPI((";

    public FrameLayout mContainer;
    private WebView webView;
    private WebView webViewPop;
    public WebSettings webSettings;
    private String address1_str;
    private String address2_str;


    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_api);

        init_webView();

        handler = new Handler();
    }

    public void init_webView() {
        webView = (WebView) findViewById(R.id.api_webView);
        //webViewPop = (WebView) findViewById(R.id.api_webView);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        webView.addJavascriptInterface(new AndroidBridge(), "hsdiet");
        //webViewPop.addJavascriptInterface(new AndroidBridge(), "hsdiet");

        this.webView.setWebChromeClient(new myWebChromeClient());
        this.webView.setWebViewClient(new WebViewClient());

        this.webView.loadUrl(SERVER_URL+"addressAPI.php");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    address1_str = getIntent().getStringExtra("address1");
                    address2_str = getIntent().getStringExtra("address2");

                    address1_str = arg1;
                    address2_str = arg2 + " " + arg3;

                    Intent goPP = new Intent(getApplicationContext(), pp_PurchaseProduct.class);
                    goPP.putExtra("result1", address1_str);
                    goPP.putExtra("result2", address2_str);

                    setResult(RESULT_OK, goPP);
                    finish();

                    //init_webView();
                }
            });
        }
    }


    private class myWebChromeClient extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

           webViewPop = new WebView(view.getContext());
           WebSettings webSettings = webViewPop.getSettings();
           webSettings.setJavaScriptEnabled(true);
           webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
           webSettings.setSupportMultipleWindows(true);

           final Dialog dialog = new Dialog(view.getContext());
           dialog.setContentView(webViewPop);

           ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
           params.width = ViewGroup.LayoutParams.MATCH_PARENT;
           params.height = ViewGroup.LayoutParams.MATCH_PARENT;
           dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

           dialog.show();

           webViewPop.setWebViewClient(new WebViewClient());
           webViewPop.setWebChromeClient(new myWebChromeClient() {
               @Override
               public void onCloseWindow(WebView window) {
                   dialog.dismiss();
               }
           });

           WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
           transport.setWebView(webViewPop);
           resultMsg.sendToTarget();
           return true;
        }
    }



}