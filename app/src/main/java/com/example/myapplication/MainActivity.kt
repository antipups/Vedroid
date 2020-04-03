package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(WebView(this))
    }

    @SuppressLint("SetTextI18n")
    fun toastMe(viev: View)
    {
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