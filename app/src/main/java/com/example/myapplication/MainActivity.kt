package com.example.myapplication

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.ContentResolver
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService


var mainContentResolver : ContentResolver? = null


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val myWebView = WebView(this)
        myWebView.loadDataWithBaseURL(null, html_code,"text/html", "UTF-8", null);
        myWebView.settings.javaScriptEnabled = true
        myWebView.addJavascriptInterface(WebAppInterface(this), "Android")
        setContentView(myWebView)
        mainContentResolver = applicationContext.contentResolver
    }
}


class WebAppInterface(private val mContext: Context) {

    @JavascriptInterface
    fun showToast(toast: String)
    {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun bluetoothOn()
    {
        val btAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!btAdapter.isEnabled) btAdapter.enable()
    }

    @JavascriptInterface
    fun brightness_half()
    {
        Settings.System.putInt(mainContentResolver, Settings.System.SCREEN_BRIGHTNESS, 255/2)
    }

    @JavascriptInterface
    fun bluetoothOff()
    {
        val btAdapter = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter.isEnabled) btAdapter.disable()
    }


    @JavascriptInterface
    fun wifiOff()
    {

//        WifiManager.WIFI_STATE_ENABLING
    }

}