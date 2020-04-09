package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.FileWriter


var mainContentResolver : ContentResolver? = null
var wifi : WifiManager? = null


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainContentResolver = applicationContext.contentResolver

        if (!Settings.System.canWrite(this))         // проверяем можно ли делать то что нам надо (записывать настройки)
        {
            // если нельзя то создаем окно и запрашиваем у пользователя разрешения в нем
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.setData(Uri.parse("package:" + this.packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?

        val cog = findViewById<ImageView>(R.id.imageView)
        cog.setOnClickListener(View.OnClickListener
        {
            val settings_intent = Intent(".settingsActivity")
            startActivity(settings_intent)
        })

    }


}


//class WebAppInterface(private val mContext: Context) {
//
//    @JavascriptInterface
//    fun showToast(toast: String)
//    {
//        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
//    }
//
//    @JavascriptInterface
//    fun bluetoothOn()
//    {
//        val btAdapter = BluetoothAdapter.getDefaultAdapter()
//        if (!btAdapter.isEnabled) btAdapter.enable()
//    }
//
//    @JavascriptInterface
//    fun brightness_half()
//    {
//        Settings.System.putInt(mainContentResolver, Settings.System.SCREEN_BRIGHTNESS, 255/2)
//    }
//
//    @JavascriptInterface
//    fun bluetoothOff()
//    {
//        val btAdapter = BluetoothAdapter.getDefaultAdapter()
//        if (btAdapter.isEnabled) btAdapter.disable()
//    }
//
//
//    @JavascriptInterface
//    fun wifiOff()
//    {
//        wifi?.isWifiEnabled = false
//    }
//
//}