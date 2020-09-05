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

    @ExperimentalStdlibApi
    private fun print_mods()
    {
        val db = FeedReaderContract.FeedReaderDbHelper(this)
        db.getData()
        var mods : MutableList<String>
        try {
             mods =
                openFileInput("config.cfg").bufferedReader().readLines().joinToString(separator = "\n").split(
                    "\n\n"
                )
                    .toMutableList()
        }
        catch (e: Exception)
        {
            Toast.makeText(this, "Создайте режим", Toast.LENGTH_LONG).show()
            val settings_intent = Intent(".settingsActivity")
            startActivity(settings_intent)
            return
        }
        mods.forEach { println(it) }
        if (mods.size > 1) mods.removeLast()
        val linear : TableLayout = findViewById(R.id.list_of_mods)

        var temp_mode = ""
        var name_mode : TextView = TextView(this)
        name_mode.text = "Название режима"
        var attrs : TextView = TextView(this)
        attrs.text = "Атрибуты режима"
        var temp_row : TableRow = TableRow(this)
        temp_row.orientation = LinearLayout.HORIZONTAL
        modify_text_view(name_mode, attrs).forEach { temp_row.addView(it) }
        linear.addView(temp_row)
        linear.addView(hor_divider())

        for (mode in mods)
        {
            temp_mode = mode.substring(mode.indexOf("N") + 1)   //режем строку для норм вывода
            temp_row = TableRow(this)                             // создаем новый макет под каждую кнопку
            name_mode = TextView(this)                                  // название режима
            name_mode.text = temp_mode.substring(
                temp_mode.indexOf(":") + 1,
                temp_mode.indexOf("\n")
            )
            attrs = TextView(this)
            attrs.text = temp_mode.substring(startIndex = temp_mode.indexOf("\n") + 1)

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
        println(textView.text)
        val changes = textView.text.toString()
        if (changes.indexOf("wifi") >= 0)
        {
//            val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//            wifi.isWifiEnabled = """wifi:(true|false)""".toRegex().find(changes)?.value.toString().substring(5).toBoolean()
//            wifi.wifiState
            val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
            startActivityForResult(panelIntent, 1)
        }
        if (changes.indexOf("bluetooth") >= 0)
        {
            val btAdapter = BluetoothAdapter.getDefaultAdapter()
            if ("""bluetooth:(true|false)""".toRegex().find(changes)?.value.toString().substring(10).toBoolean()) btAdapter.enable()
            else btAdapter.disable()
        }
        if (changes.indexOf("brightness") >= 0)
        {
            val level = """brightness:\d{0,3}""".toRegex().find(changes)?.value.toString().substring(
                11
            ).toInt()
            Settings.System.putInt(
                applicationContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                level
            )
        }
    }

}
