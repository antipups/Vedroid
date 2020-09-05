package com.example.myapplication


import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // проверяем можно ли делать то что нам надо (записывать настройки)
        if (!Settings.System.canWrite(this))
        {
            // если нельзя то создаем окно и запрашиваем у пользователя разрешения в нем
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.setData(Uri.parse("package:" + this.packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        // инициализация меню которое создано для создание модов
        findViewById<ImageView>(R.id.imageView).setOnClickListener(View.OnClickListener
        {
            val settings_intent = Intent(".settingsActivity")
            startActivity(settings_intent)
        })

        print_mods()
    }

    fun output_pre_menu(linear: LinearLayout, temp_mode: String, name_mode: TextView, attrs: TextView, temp_row: TableRow)
    {
        name_mode.text = "Название режима"
        attrs.text = "Атрибуты режима"
        temp_row.orientation = LinearLayout.HORIZONTAL
        modify_text_view(name_mode, attrs).forEach { temp_row.addView(it) }
        linear.addView(temp_row)
        linear.addView(hor_divider())
    }

    @ExperimentalStdlibApi
    private fun print_mods()
    {
        val db = FeedReaderContract.FeedReaderDbHelper(this)
        val mods: MutableList<Setting> = db.getData()

        if (mods.size == 0){
            Toast.makeText(this, "Создайте режим", Toast.LENGTH_LONG).show()
            startActivity(Intent(".settingsActivity"))
            return
        }

//        mods.forEach { println(it) }
//        if (mods.size > 1) mods.removeLast()
        val linear : TableLayout = findViewById(R.id.list_of_mods)

        var temp_mode = ""
        var name_mode : TextView = TextView(this)
        var attrs : TextView = TextView(this)
        var temp_row : TableRow = TableRow(this)

        output_pre_menu(linear, temp_mode, name_mode, attrs, temp_row)

        for (mode in mods)
        {
            temp_row = TableRow(this)                             // создаем новый макет под каждую кнопку
            name_mode = TextView(this)                                  // название режима
            name_mode.text = mode.title
            attrs = TextView(this)
            attrs.text = (if (mode.wifi != 2) "WiFi : ${mode_to_string(mode.wifi)}\n" else "") +
                    (if (mode.bluetooth != 2) "Bluetooth : ${mode_to_string(mode.bluetooth)}\n" else "") +
                    (if (mode.brightness != -1) "Яркость : ${mode.brightness}\n" else "")
            attrs.text = attrs.text.slice(0..attrs.text.length - 2)

            temp_row.setOnClickListener(View.OnClickListener
            {
                val test_ = it as TableRow
                apply_changes(test_.getVirtualChildAt(2) as TextView)
            })
            modify_text_view(name_mode, attrs).forEach { temp_row.addView(it) }
            linear.addView(temp_row)
            linear.addView(hor_divider())
        }

    }

    fun mode_to_string(code: Int): String{
        return if (code == 1)  "вкл." else "выкл."
    }

    @SuppressLint("WrongConstant")
    private fun modify_text_view(name: TextView, attrs: TextView): Array<TextView> {

        attrs.textSize = 20f
        attrs.textAlignment = 4
        attrs.setBackgroundColor(Color.DKGRAY)

        val text = TextView(this)
        text.setBackgroundColor(Color.BLACK)
        text.setTextColor(Color.BLACK)
        text.textSize = 20f
        var count = 0
        for (sym in attrs.text) if (sym == ':') count++
        text.text = "a".padStart(count, '\n')

        name.textSize = 20f
        name.textAlignment = 4
        name.setBackgroundColor(Color.DKGRAY)

        return arrayOf(name, text, attrs)
    }

    private fun hor_divider(): TableRow
    {
        val table_row = TableRow(this)
        for (i in 1..2)
        {
            val t1 = TextView(this)
            t1.textSize = 2f
            t1.setTextColor(Color.BLACK)
            t1.text = "a"
            table_row.addView(t1)
        }
        table_row.setBackgroundColor(Color.BLACK)
        return table_row
    }

    private fun apply_changes(textView: TextView)
    {
        val changes = textView.text.toString()
        if (changes.indexOf("WiFi") >= 0)
        {
            val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//            wifi.isWifiEnabled = """wifi:(true|false)""".toRegex().find(changes)?.value.toString().substring(5).toBoolean()
            if ("""WiFi : (вкл|выкл).""".toRegex().find(changes)?.value.toString().substring(7) != if (wifi.wifiState == 1) "выкл." else "вкл."){
                startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), 1)
            }
        }
        if (changes.indexOf("Bluetooth") >= 0)
        {
            val btAdapter = BluetoothAdapter.getDefaultAdapter()
            if ("""Bluetooth : (вкл|выкл).""".toRegex().find(changes)?.value.toString().substring(12) == "вкл.") btAdapter.enable()
            else btAdapter.disable()
        }
        if (changes.indexOf("Яркость") >= 0)
        {
            val level = """Яркость : \d{0,3}""".toRegex().find(changes)?.value.toString().substring(10).toInt()
            Settings.System.putInt(
                applicationContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                level
            )
        }
    }

}
