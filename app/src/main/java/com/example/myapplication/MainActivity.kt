package com.example.myapplication


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

//    private lateinit var mainContentResolver : ContentResolver
//    private lateinit var wifi : WifiManager
    private lateinit var list_of_mods : ListView

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mainContentResolver = applicationContext.contentResolver

        // проверяем можно ли делать то что нам надо (записывать настройки)
        if (!Settings.System.canWrite(this))
        {
            // если нельзя то создаем окно и запрашиваем у пользователя разрешения в нем
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.setData(Uri.parse("package:" + this.packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

//        wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // инициализация меню которое создано для создание модов
        val cog = findViewById<ImageView>(R.id.imageView)
        cog.setOnClickListener(View.OnClickListener
        {
            val settings_intent = Intent(".settingsActivity")
            startActivity(settings_intent)
        })

        print_mods()

    }

    @ExperimentalStdlibApi
    private fun print_mods()
    {
        var mods : MutableList<String> =
            openFileInput("config.cfg").bufferedReader().readLines().joinToString(separator = "\n").split("\n\n")
                .toMutableList()
        if (mods.size > 1) mods.removeLast()
        val linear : TableLayout = findViewById(R.id.list_of_mods)

        var temp_mode = ""
        var name_mode : TextView = TextView(this)
        name_mode.text = "Название режима"
        var attrs : TextView = TextView(this)
        attrs.text = "Атрибуты режима"
        var temp_row : TableRow = TableRow(this)
        temp_row.orientation = LinearLayout.HORIZONTAL
        temp_row.addView(name_mode)
        temp_row.addView(attrs)
        linear.addView(temp_row)

        for (mode in mods)
        {
            temp_mode = mode.substring(mode.indexOf("N") + 1)   //режем строку для норм вывода
            temp_row = TableRow(this)                             // создаем новый макет под каждую кнопку
            name_mode = TextView(this)                                  // название режима
            name_mode.text = temp_mode.substring(temp_mode.indexOf(":") + 1, temp_mode.indexOf("\n"))
            temp_row.addView(name_mode)                                       // добавляем в лайаут название
            attrs = TextView(this)
            attrs.text = temp_mode.substring(startIndex = temp_mode.indexOf("\n") + 1)
            temp_row.addView(attrs)

            temp_row.setOnClickListener(View.OnClickListener
            {
                val test_ = it as TableRow
                val text_ : TextView = test_.getVirtualChildAt(0) as TextView
                println(text_.text)
                println()
            })
            linear.addView(temp_row)
        }

//        list_of_mods = findViewById(R.id.list_of_mods)
//        list_of_mods.adapter = ArrayAdapter(this, R.layout.activity_main, R.id.textView, mods)

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