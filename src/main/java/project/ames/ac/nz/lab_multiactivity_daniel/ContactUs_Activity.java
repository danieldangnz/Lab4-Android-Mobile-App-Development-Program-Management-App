package project.ames.ac.nz.lab_multiactivity_daniel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ContactUs_Activity extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Declare variables
    private WebView webview;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_layout);

        //Find reference and do casting for the webview
        webview = (WebView) findViewById(R.id.webView);

        //Create a variable WebSettings, called webSettings, and assign it to "myWV" setting
        WebSettings webSettings = webview.getSettings();
        ///Set javascript enabled in your WebView
        webSettings.setJavaScriptEnabled(true);
        //Set Zoom properties
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //setClientClient() method is used to display website and other url link within
        //Webview, not open a new browser window.
        //If I click on a link in the webview, the resulting page will also be
        //loaded into the same webview.
        webview.setWebViewClient(new WebViewClient());
        //Set WebChromeClient to get the progress of page loading
        webview.setWebChromeClient(new MyWebViewClient());

        //Load the google map address of the college
        webview.loadUrl("https://www.google.co.nz/maps/place/385+Queen+St,+Auckland,+1010/@-36.8562355,174.7616089,18.75z/data=!4m5!3m4!1s0x6d0d47e8bbf193f3:0x7d2cc8e3ccf8ff98!8m2!3d-36.8560585!4d174.7619698");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Create MyWebViewClient class here
    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }
}
