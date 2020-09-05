package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class settingsActivity : AppCompatActivity() {

    private lateinit var checkBox_wifi : CheckBox
    private lateinit var checkBox_bluetooth : CheckBox
    private lateinit var checkBox_brightness : CheckBox
    private lateinit var checkBox_dist : CheckBox
    private lateinit var edit_title : EditText
    private lateinit var button_ok : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        checkBox_wifi = findViewById(R.id.checkBox_wifi)
        checkBox_bluetooth = findViewById(R.id.checkBox_bluetooth)
        checkBox_brightness = findViewById(R.id.checkBox_brigtness)
        checkBox_dist = findViewById(R.id.checkBox_dist)
        edit_title = findViewById(R.id.edit_title)
        button_ok = findViewById(R.id.button_ok)
        addActionListeners()
    }

    private fun addActionListeners() {
        /**
         * Функиця ставящая прослушки на все чекбоксы, при нажатии которых включается
         * редактирование нужного элемента
        */


        checkBox_wifi.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_wifi).isEnabled = checkBox_wifi.isChecked
        })

        checkBox_bluetooth.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_bluetooth).isEnabled = checkBox_bluetooth.isChecked
        })

        checkBox_brightness.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.seekBar_brigtness).isInvisible = !checkBox_brightness.isChecked
        })

        checkBox_dist.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_dist).isEnabled = checkBox_dist.isChecked
        })

        button_ok.setOnClickListener(View.OnClickListener {
            var result_property_mode = ""

            if (edit_title.text.isNotEmpty())
            {
//                result_property_mode += "//\n\tName_Mode:" + edit_title.text.toString() + "\n"
//                if (switch_wifi.isEnabled) result_property_mode += "\twifi:" + switch_wifi.isChecked.toString() + "\n"
//                if (switch_bluetooth.isEnabled) result_property_mode += "\tbluetooth:" + switch_bluetooth.isChecked.toString() + "\n"
//                if (switch_dist.isEnabled) result_property_mode += "\tdist:" + switch_dist.isChecked.toString() + "\n"
//                if (!seekBar_brigtness.isInvisible) result_property_mode += "\tbrightness:" + seekBar_brigtness.progress.toString() + "\n"
//
//                var older_mods : String
//                try {
//                    older_mods =  openFileInput("config.cfg").bufferedReader().readLines().joinToString ( separator="\n" )
//                }
//                catch (e: Exception)
//                {
//                    older_mods = ""
//                }
//
//                val writer_to_config : FileOutputStream = openFileOutput("config.cfg", Context.MODE_PRIVATE)
//                writer_to_config.write(("${older_mods + result_property_mode}\n//\n\n").toByteArray())
//                writer_to_config.close()
                val db = FeedReaderContract.FeedReaderDbHelper(this)
                db.insertData(conveter())
                finish()
            }
            else Toast.makeText(this, "Введите название режима", Toast.LENGTH_LONG).show()

        })


//        val sex1 = openFileInput("config.cfg").bufferedReader()
//        sex1.readLines().forEach{println(it)}
//        println()
    }

        fun conveter(): Setting {
            val title = edit_title.text.toString()
            var wifi: Int = 2
            var bluetooth: Int = 2
            var brightness: Int = -1
            if (switch_wifi.isEnabled) wifi =  if (switch_wifi.isChecked) 1 else 0
            if (switch_bluetooth.isEnabled) bluetooth =  if (switch_bluetooth.isChecked) 1 else 0
            if (!seekBar_brigtness.isInvisible) brightness = seekBar_brigtness.progress
            return Setting(title, wifi, bluetooth, brightness)
        }



}