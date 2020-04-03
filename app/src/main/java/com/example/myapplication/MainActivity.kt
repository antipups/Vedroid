package com.example.myapplication

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.html_code


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val myWebView = WebView(this)
        setContentView(myWebView)
        myWebView.loadDataWithBaseURL(null, html_code,"text/html", "UTF-8", null);
        myWebView.settings.javaScriptEnabled = true
        myWebView.addJavascriptInterface(WebAppInterface(this), "Android")
    }

    fun testJS(webView: WebView)
    {
//        WebView.addJavascriptInterface(WebAppInterface(this), "Android")
//        val btAdapter = BluetoothAdapter.getDefaultAdapter()
//        btAdapter.enable()
//        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_SETTINGS), 1);
//        Settings.System.putInt(applicationContext.contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, 100)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)     // проверяем версию устроиства (доступно только с 6-ого андройда)
//        {
//            if (Settings.System.canWrite(this))         // проверяем можно ли делать то что нам надо (записывать настройки)
//            {
//                println("Доступ есть")
//            }
//            else                                                // если нельзя то создаем окно и запрашиваем у пользователя разрешения в нем
//            {
//                val intent = Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)     // action_manage... ниже объясню
//                intent.setData(Uri.parse("package:" + this.packageName));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(intent);
//            }
//        }

    }
}


class WebAppInterface(private val mContext: Context) {

    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}